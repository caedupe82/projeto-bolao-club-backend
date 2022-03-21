package br.com.classificados.mt.services;

import br.com.classificados.mt.domain.Painel;
import br.com.classificados.mt.domain.Premio;
import br.com.classificados.mt.domain.Usuario;
import br.com.classificados.mt.dto.PainelDTO;
import br.com.classificados.mt.dto.PainelUsuarioDTO;
import br.com.classificados.mt.repositories.PainelRepository;
import br.com.classificados.mt.repositories.UsuarioRepository;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import br.com.classificados.mt.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PainelService {
	
	@Autowired
	private PainelRepository painelRepository;
	
	@Autowired
	private PremioService premioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Painel insert(Painel painel) {
		
		LocalDate now = painel.getMesReferencia();
		
		this.validarPainelAdministrativo(painel, now);
		
		return this.painelRepository.save(painel);
	}

	private void validarPainelAdministrativo(Painel painel, LocalDate now) {
		
		if(painel.getAberturaSistema().isAfter(painel.getFechamentoSistema())) {
			throw new DataIntegrityException("A data da Abertura do Sistema não poderá ser maior que a data do Fechamento do Sistema.");
		}
		
		if(painel.getAberturaSistema().isAfter(painel.getPrimeiroSorteio())) {
			throw new DataIntegrityException("A data do Abertura do Sistema não poderá ser maior que a data do Primeiro Sorteio.");
		}
		
		if(painel.getFechamentoSistema().isAfter(painel.getPrimeiroSorteio())) {
			throw new DataIntegrityException("A data do Fechamento do Sistema não poderá ser maior que a data do Primeiro Sorteio.");
		}
	}
	
	public PainelDTO findOne() {
		LocalDate now = LocalDate.now();
		
		LocalDate mesReferencia = LocalDate.parse(now.getYear() + "-" + this.acrescentarZero(now.getMonthValue()) + "-01");
		
		List<Usuario> listRenovacao = this.usuarioRepository.findAllByPagouUltimaMensalidade(Boolean.TRUE);
		
		Long quantidadeUsuario = 0L;
		
		if (listRenovacao != null && !listRenovacao.isEmpty()) {
			quantidadeUsuario = (long) listRenovacao.size();
		}
		
		Premio premio = this.premioService.findFirstByDate(LocalDate.now().withMonth(LocalDate.now().getMonthValue() - 1));
		
		Double valorPremioPrincipal = premio != null && premio.getVencedorPrimeiro().isEmpty() ? premio.getPremioPrimeiro() : 0.0;
		Double valorPremioAcumulado = premio != null && premio.getVencedorAcumulado().isEmpty() ? premio.getPremioAcumulado() : 0.0;
		
		Painel painel = this.painelRepository.findFirstByMesReferencia(mesReferencia);
		
		if (painel != null) {
			painel.setMesReferencia(mesReferencia);
			PainelDTO painelDTO = this.fromDTO(painel);
			painelDTO.setTotalParticipantes(quantidadeUsuario);
			painelDTO.setPremioAcumulado(valorPremioAcumulado + valorPremioPrincipal);
			
			return painelDTO;
		}
		
		PainelDTO painelDTO = new PainelDTO();
		painelDTO.setMesReferencia(DateUtil.converterDataPorFormato(mesReferencia));
		painelDTO.setTotalParticipantes(quantidadeUsuario);
		painelDTO.setPremioAcumulado(valorPremioAcumulado + valorPremioPrincipal);
		
		return painelDTO;
	}
	
	private String acrescentarZero(int monthValue) {
		if (monthValue < 10) {
			return "0".concat(String.valueOf(monthValue));
		}
		
		return String.valueOf(monthValue);
	}

	public PainelDTO findOnePainelPorData(String data) {
		LocalDate mesReferencia = LocalDate.parse(DateUtil.convertBrDateToEnglishDate(data));
		
		List<Usuario> listRenovacao = this.usuarioRepository.findAllByPagouUltimaMensalidade(Boolean.TRUE);
		
		Long quantidadeUsuario = 0L;
		
		if (listRenovacao != null && !listRenovacao.isEmpty()) {
			quantidadeUsuario = (long) listRenovacao.size();
		}
		
		Premio premio = this.premioService.findFirstByDate(LocalDate.now().withMonth(LocalDate.now().getMonthValue() - 1));
		
		Double valorPremioPrincipal = premio != null && premio.getVencedorPrimeiro().isEmpty() ? premio.getPremioPrimeiro() : 0.0;
		Double valorPremioAcumulado = premio != null && premio.getVencedorAcumulado().isEmpty() ? premio.getPremioAcumulado() : 0.0;
		
		Painel painel = this.painelRepository.findFirstByMesReferencia(mesReferencia);
		
		if (painel != null) {
			painel.setMesReferencia(mesReferencia);
			PainelDTO painelDTO = this.fromDTO(painel);
			painelDTO.setTotalParticipantes(quantidadeUsuario);
			painelDTO.setPremioAcumulado(valorPremioAcumulado + valorPremioPrincipal);
			
			return painelDTO;
		}
		
		PainelDTO painelDTO = new PainelDTO();
		painelDTO.setMesReferencia(DateUtil.converterDataPorFormato(mesReferencia));
		painelDTO.setTotalParticipantes(quantidadeUsuario);
		painelDTO.setPremioAcumulado(valorPremioAcumulado + valorPremioPrincipal);
		
		return painelDTO;
	}
	
	public PainelUsuarioDTO findOnePainelUsuario() {
		PainelUsuarioDTO painelUsuarioDTO = new PainelUsuarioDTO();
		
		LocalDate now = LocalDate.now();
		
		List<Painel> list = this.painelRepository.findAllByMesAndAno(now.getMonthValue(), now.getYear());
		
		List<Usuario> listRenovacao = this.usuarioRepository.findAllByPagouUltimaMensalidade(Boolean.TRUE);
		
		Integer quantidadeUsuario = 0;
		
		if (listRenovacao != null && !listRenovacao.isEmpty()) {
			quantidadeUsuario = listRenovacao.size();
		}
		
		if (list == null || list.isEmpty()) {
			return painelUsuarioDTO;
		} else {
			Painel painel = list.get(0);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			painelUsuarioDTO.setAberturaSistema(painel.getAberturaSistema().format(formatter));
			painelUsuarioDTO.setFechamentoSistema(painel.getFechamentoSistema().format(formatter));
			painelUsuarioDTO.setPrimeiroSorteio(painel.getPrimeiroSorteio().format(formatter));
			painelUsuarioDTO.setTotalParticipante(quantidadeUsuario);
			painelUsuarioDTO.setPremioAcumulado(painel.getPremioAcumulado());
			painelUsuarioDTO.setPremioPrincipal((quantidadeUsuario * painel.getValorCota()) * (painel.getPremioPrincipal() / 100));
			painelUsuarioDTO.setPremioSecundario((quantidadeUsuario * painel.getValorCota()) * (painel.getPremioSecundario() / 100));
			painelUsuarioDTO.setPremioPeFrio((quantidadeUsuario * painel.getValorCota()) * (painel.getPremioPeFrio() / 100));
			painelUsuarioDTO.setValorCota(painel.getValorCota());
			painelUsuarioDTO.setMesReferencia(DateUtil.getMesExtenso(painel.getMesReferencia().getMonth()) + "/" + painel.getMesReferencia().getYear());
		}
		
		return painelUsuarioDTO;
	}
	
	public Painel fromEntity(PainelDTO painelDTO) {
		return new Painel(painelDTO.getCodigo(), painelDTO.getValorCota(), painelDTO.getPremioPrincipal(), painelDTO.getPremioSecundario(), 
				painelDTO.getPremioPeFrio(), painelDTO.getPremioAcumulado(), this.validarData(painelDTO.getMesReferencia(), "Mês Referência"), this.validarData(painelDTO.getAberturaSistema(), "Abertura do Sistema"),
				this.validarData(painelDTO.getFechamentoSistema(), "Fechamento do Sistema"), this.validarData(painelDTO.getPrimeiroSorteio(), "Primeiro Sorteio"), painelDTO.getTaxaAdministrativa());
	}
	
	public PainelDTO fromDTO(Painel painel) {
		return new PainelDTO(painel.getCodigo(), painel.getValorCota(), painel.getPremioPrincipal(), painel.getPremioSecundario(), 
				painel.getPremioPeFrio(), painel.getPremioAcumulado(), DateUtil.converterDataPorFormato(painel.getMesReferencia()), DateUtil.converterDataPorFormato(painel.getAberturaSistema()),
				DateUtil.converterDataPorFormato(painel.getFechamentoSistema()), DateUtil.converterDataPorFormato(painel.getPrimeiroSorteio()), painel.getTaxaAdministrativa());
	}
	
	private LocalDate validarData(String data, String nomeData) {
		try {
			return LocalDate.parse(DateUtil.convertBrDateToEnglishDate(data));
		} catch (Exception e) {
			throw new DataIntegrityException("O campo ".concat(nomeData).concat(" está no formato inválido."));
		}
	}
	
	public List<Painel> findAllByMesAndAno(LocalDate data) {
		
		return this.painelRepository.findAllByMesAndAno(data.getMonthValue(), data.getYear());
	}
}
