package br.com.classificados.mt.services;

import br.com.classificados.mt.domain.Painel;
import br.com.classificados.mt.domain.Premio;
import br.com.classificados.mt.domain.Renovacao;
import br.com.classificados.mt.domain.Usuario;
import br.com.classificados.mt.dto.FaleConoscoDTO;
import br.com.classificados.mt.dto.SolicitacaoRenovacaoDTO;
import br.com.classificados.mt.dto.UsuarioPalpiteDTO;
import br.com.classificados.mt.repositories.PainelRepository;
import br.com.classificados.mt.repositories.RenovacaoRepository;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import br.com.classificados.mt.utils.EmailUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RenovacaoRepository renovacaoRepository;
	
	@Autowired
	private PainelRepository painelRepository;

	private static final String EMAIL_BOLAO_CLUB = "bolaoclub20@gmail.com";

	public void enviarEmailBoasVindas(String emailUsuario, String nomeUsuario) {

		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(emailUsuario);
			helper.setSubject("Bem vindo(a) ao Bolão Club!");
			helper.setText(EmailUtil.templateEmailBoasVindas(nomeUsuario), true);
			this.mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object enviarEmailSolicitacaoRenovacao(SolicitacaoRenovacaoDTO solicitacaoRenovacaoDTO) {

		LocalDate now = LocalDate.now();

		List<Painel> listPainel = this.painelRepository.findAllByMesAndAno(now.getMonthValue(), now.getYear());

		Painel painelValidador = null;

		if (listPainel != null && !listPainel.isEmpty()) {
			painelValidador = listPainel.get(0);
		}

		if (painelValidador != null && (now.isAfter(painelValidador.getFechamentoSistema()) || 
				now.isEqual(painelValidador.getFechamentoSistema()))) {
			return new DataIntegrityException("Já foi encerrado o período de apostas desse mês.").getMessage();
		}

		Usuario usuario = this.usuarioService.findOneByEmail(solicitacaoRenovacaoDTO.getEmail());

		List<Renovacao> list = this.renovacaoRepository.findAllRenovacaoByAprovadoAndUsuario(Boolean.TRUE,
				usuario.getCodigo());

		Renovacao renovacao = validarJaPossuiRenovacaoNoMesAno(list);

		if (usuario.getPagouUltimaMensalidade() != null && usuario.getPagouUltimaMensalidade()) {
			return new DataIntegrityException(
					"Você já pagou esse mês. Aproveite e vá fazer jogos :D").getMessage();
		}
		
		if (renovacao != null) {
			return new DataIntegrityException(
					"Já existe um comprovante de pagamento nesse mês em análise. Por favor aguarde!").getMessage();
		}

		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(EMAIL_BOLAO_CLUB);
			helper.setSubject("Solicitação de renovação de pagamento do Bolão Club");
			helper.setText(EmailUtil.templateEmailSolicitacaoRenovacao(usuario.getTelefone(), usuario.getNome()), true);

			String extension = EmailUtil.getExtensao(solicitacaoRenovacaoDTO.getArquivo().split(",")[0]);

			if (extension.equals("invalido")) {
				return new DataIntegrityException(
						"Extensão inválida! Por favor anexar um arquivo PDF, JPG, JPEG ou PNG.").getMessage();
			}

			String fileBody = solicitacaoRenovacaoDTO.getArquivo().split(",")[1];

			byte[] data = Base64.decodeBase64(fileBody);

			File tempFile = File.createTempFile("comprovante", ".png", null);
			OutputStream os = new FileOutputStream(tempFile);

			os.write(data);

			// Close the file
			os.close();

			helper.addAttachment("comprovante.".concat(extension), tempFile);
			this.mailSender.send(mail);

			this.notificarUsuarioRenovacao(usuario.getEmail(), usuario.getNome());
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.renovacaoRepository.save(new Renovacao(null, usuario.getCodigo(), LocalDate.now(), null));

		return "Solicitação de renovação enviada com sucesso!";

	}
	
	public void chamarEmailResultadoDoDia() {
		List<Usuario> list = this.usuarioService.findAllWithEmail();
		
		if (list != null && !list.isEmpty()) {
			list.forEach(u -> this.enviarEmailResultadoDoDia(u));
		}
	}
	
	private void enviarEmailResultadoDoDia(Usuario usuario) {

		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(usuario.getEmail());
			helper.setSubject("Notificação do resultado do dia.");
			helper.setText(EmailUtil.templateResultadoDoDia(usuario.getNome()), true);
			this.mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void chamarEmailPremioDoMes(List<UsuarioPalpiteDTO> list, Premio premio) {
		
		if (list != null && !list.isEmpty()) {
			String vencedores = this.concatenarVencedores(premio);
			String participantes = this.concatenarParticipantes(list);
			list.forEach(u -> this.enviarEmailPremioDoMes(u, vencedores, participantes));
		}
	}
	
	private void enviarEmailPremioDoMes(UsuarioPalpiteDTO usuario, String vencedores, String participantes) {

		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(usuario.getEmail());
			helper.setSubject("Premiação do concurso do mês");
			helper.setText(EmailUtil.templatePremio(usuario.getNome(), vencedores, participantes), true);
			this.mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String concatenarParticipantes(List<UsuarioPalpiteDTO> list) {
		String participantes = "";
		
		if (list != null && !list.isEmpty()) {
			
			for (int i = 0; i < list.size(); i++) {
				
				participantes += "<p>".concat(list.get(i).getApelido()).concat(" - ")
						.concat(list.get(i).getQuantidadeAcerto().toString())
						.concat(" acertos</p>");
				
			}
			
		}
		
		return participantes;
	}
	
	private String concatenarVencedores(Premio premio) {
		String vencedorPrimeiro = premio.getVencedorPrimeiro() == null ? "Nenhum Vencedor" : premio.getVencedorPrimeiro();
		String vencedorSegundo = premio.getVencedorSegundo() == null ? "Nenhum Vencedor" : premio.getVencedorSegundo();
		String vencedorPeFrio = premio.getVencedorPeFrio() == null ? "Nenhum Vencedor" : premio.getVencedorPeFrio();
		String vencedorAcumulado = premio.getVencedorAcumulado() == null ? "Nenhum Vencedor" : premio.getVencedorAcumulado();
	
		String vencedores = "";
		
		vencedores += "<p> Vencedor 20 acertos: ".concat(vencedorPrimeiro).concat("</p>");
		vencedores += "<p> Vencedor 19 acertos: ".concat(vencedorSegundo).concat("</p>");
		vencedores += "<p> Vencedor Pé Frio: ".concat(vencedorPeFrio).concat("</p>");
		vencedores += "<p> Vencedor Acumulado: ".concat(vencedorAcumulado).concat("</p>");
		
		return vencedores;
	}

	public void enviarEmailReprovacao(String emailUsuario, String nomeUsuario, String motivo) {

		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(emailUsuario);
			helper.setSubject("Reprovação do comprovante de pagamento :(");
			helper.setText(EmailUtil.templateEmailReprovacaoRenovacao(nomeUsuario, motivo), true);
			this.mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviarEmailAprovacao(String emailUsuario, String nomeUsuario) {

		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(emailUsuario);
			helper.setSubject("Aprovação do comprovante de pagamento =D");
			helper.setText(EmailUtil.templateEmailAprovacaoRenovacao(nomeUsuario), true);
			this.mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Renovacao validarJaPossuiRenovacaoNoMesAno(List<Renovacao> list) {
		LocalDate now = LocalDate.now();

		if (list != null && !list.isEmpty()) {
			for (Renovacao renovacao : list) {
				if (renovacao.getDataSolicitacao().getYear() == now.getYear()
						&& renovacao.getDataSolicitacao().getMonth() == now.getMonth()) {
					return renovacao;
				}
			}
		}
		return null;
	}

	public void enviarEmailContato(FaleConoscoDTO faleConoscoDTO) {

		Usuario usuario = this.usuarioService.findOneByEmail(faleConoscoDTO.getEmail());

		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(EMAIL_BOLAO_CLUB);
			helper.setSubject(faleConoscoDTO.getAssunto());
			helper.setText(EmailUtil.templateEmailContato(faleConoscoDTO.getMensagem(), usuario.getTelefone(),
					usuario.getNome()), true);
			this.mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.notificarUsuarioEmailContato(faleConoscoDTO.getEmail(), usuario.getNome());
	}

	private void notificarUsuarioEmailContato(String emailUsuario, String nomeUsuario) {
		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(emailUsuario);
			helper.setSubject("E-mail de contato enviado com sucesso!");
			helper.setText(EmailUtil.templateNotificarUsuarioEmailContato(nomeUsuario), true);
			this.mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void notificarUsuarioRenovacao(String emailUsuario, String nomeUsuario) {
		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(emailUsuario);
			helper.setSubject("Comprovante de renovação recebido");
			helper.setText(EmailUtil.templateNotificarUsuarioEmailRenovacao(nomeUsuario), true);
			this.mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
