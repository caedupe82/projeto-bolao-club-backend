package br.com.classificados.mt.cron;

import br.com.classificados.mt.domain.Painel;
import br.com.classificados.mt.domain.Palpite;
import br.com.classificados.mt.domain.Resultado;
import br.com.classificados.mt.domain.Usuario;
import br.com.classificados.mt.enums.Perfil;
import br.com.classificados.mt.repositories.PalpiteRepository;
import br.com.classificados.mt.repositories.ResultadoRepository;
import br.com.classificados.mt.repositories.UsuarioRepository;
import br.com.classificados.mt.services.EmailService;
import br.com.classificados.mt.services.PainelService;
import br.com.classificados.mt.services.PremioService;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import br.com.classificados.mt.utils.DateUtil;
import br.com.classificados.mt.utils.TypeUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.JavaNetCookieJar;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class CronService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ResultadoRepository resultadoRepository;

	@Autowired
	private PalpiteRepository palpiteRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PremioService premioService;

	@Autowired
	private PainelService painelService;

	@Scheduled(cron = "0 0/20 11,14 * * *")
	public void validarPagamentoUsuario() {

		List<Usuario> listUsuario = this.usuarioRepository.findAllByPermissao(Perfil.USUARIO);
		List<Usuario> listUsuarioParaAtualizar = new ArrayList<>();

		LocalDate now = LocalDate.now();

		for (Usuario usuario : listUsuario) {
			if (usuario.getDataUltimoPagamento() != null
					&& usuario.getDataUltimoPagamento().getMonthValue() != now.getMonthValue()) {
				usuario.setPagouUltimaMensalidade(Boolean.FALSE);

				listUsuarioParaAtualizar.add(usuario);
			}
		}

		this.usuarioRepository.save(listUsuarioParaAtualizar);
	}

	@Scheduled(cron = "0 0/10 * * * *")
	public void atualizarConcursoQuina() {
		
		Logger.getInstance(CronService.class).info("Tentando chamar o resultado na quina...");

		LocalDateTime now = LocalDateTime.now().minusHours(3L);
		
		List<Painel> listPainel = this.painelService.findAllByMesAndAno(now.toLocalDate());

		Painel painel = new Painel();

		if (listPainel != null && !listPainel.isEmpty()) {
			painel = listPainel.get(0);
		}

		if (painel.getPrimeiroSorteio() == null
				|| (painel.getPrimeiroSorteio().getMonthValue() != now.toLocalDate().getMonthValue()
						|| now.toLocalDate().getDayOfMonth() < painel.getPrimeiroSorteio().getDayOfMonth())) {
			throw new DataIntegrityException("O concurso ainda não foi aberto para sorteios.");
		}

		List<Resultado> listResultado = this.resultadoRepository.findAllByMesAndAno(now.toLocalDate().getMonthValue(),
				now.toLocalDate().getYear());

		int quantidadeResultado = listResultado == null || listResultado.isEmpty() ? 0 : listResultado.size();

		if (quantidadeResultado >= 20 || now.toLocalDate().getDayOfMonth() == now.toLocalDate().lengthOfMonth()) {
			throw new DataIntegrityException("O último resultado do mês já foi gerado.");
		}

		try {
			String url = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/quina/!ut/p/a1/jc69DoIwAATgZ_EJepS2wFgoaUswsojYxXQyTfgbjM9vNS4Oordd8l1yxJGBuNnfw9XfwjL78dmduIikhYFGA0tzSFZ3tG_6FCmP4BxBpaVhWQuA5RRWlUZlxR6w4r89vkTi1_5E3CfRXcUhD6osEAHA32Dr4gtsfFin44Bgdw9WWSwj/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_61L0H0G0J0VSC0AC4GLFAD20G6/res/id=buscaResultado/c=cacheLevelPage/=/?timestampAjax="
					.concat(String.valueOf(new Date(System.currentTimeMillis()).getTime()));

			CookieHandler cookieHandler = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
			OkHttpClient client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(cookieHandler)).build();

			Request request = new Request.Builder().url(url).build();
			Response response = null;

			try {
				response = client.newCall(request).execute();
			} catch (Exception e) {
				throw new Exception("Falha ao integrar com a caixa economica federal");
			}

			String jsonData = response.body().string();
			JSONObject jsonObject = new JSONObject(jsonData);

			LocalDate dataSorteio = LocalDate
					.parse(DateUtil.convertBrDateToEnglishDate(jsonObject.getString("dataStr")));

			if (dataSorteio.getDayOfMonth() != now.toLocalDate().getDayOfMonth()
					|| dataSorteio.getMonthValue() != now.toLocalDate().getMonthValue()
					|| dataSorteio.getYear() != now.toLocalDate().getYear()) {

				throw new DataIntegrityException("A data do resultado é diferente da data atual");

			}

			List<Resultado> list = this.resultadoRepository.findAllByData(now.toLocalDate());

			if (list != null && !list.isEmpty()) {
				throw new DataIntegrityException("Já foi criado um resultado para o dia de hoje!");
			}

			String numero = String.valueOf(jsonObject.getInt("concurso"));
			String[] dezenas = jsonObject.getString("resultadoOrdenado").split("-");

			String primeiroNumero = dezenas[0];
			String segundoNumero = dezenas[1];
			String terceiroNumero = dezenas[2];
			String quartoNumero = dezenas[3];
			String quintoNumero = dezenas[4];

			Resultado resultado = new Resultado();
			resultado.setNumeroConcurso(numero);
			resultado.setPrimeiroNumero(primeiroNumero);
			resultado.setSegundoNumero(segundoNumero);
			resultado.setTerceiroNumero(terceiroNumero);
			resultado.setQuartoNumero(quartoNumero);
			resultado.setQuintoNumero(quintoNumero);

			atualizarPalpitesUsuario(resultado);

			resultado.setData(dataSorteio);

			this.resultadoRepository.save(resultado);

			if ((quantidadeResultado + 1) < 20 || !(now.getDayOfMonth() == now.toLocalDate().lengthOfMonth())) {
				try {
					this.emailService.chamarEmailResultadoDoDia();
					Logger.getInstance(CronService.class).info("Chamada no resultado do dia realizada com sucesso");
				} catch (Exception e) {
					Logger.getInstance(CronService.class).error("Falha ao notificar os usuários sobre o resultado");
				}
			} else {
				this.premioService.chamarPremioPorData(now.toLocalDate());
				Logger.getInstance(CronService.class).info("Chamada no resultado do premio realizada com sucesso");
			}

		} catch (Exception e) {
			Logger.getInstance(CronService.class).error("Falha ao intregrar com o sistema da quina.");
		}
	}
	
	private void atualizarPalpitesUsuario(Resultado resultado) {
		LocalDate now = LocalDate.now();

		List<Palpite> listPalpite = this.palpiteRepository.findAllByMesAndAno(now.getMonthValue(), now.getYear());

		List<Palpite> listPalpiteParaAtualizar = new ArrayList<>();

		for (Palpite palpite : listPalpite) {

			validarAcerto(palpite, resultado.getPrimeiroNumero());
			validarAcerto(palpite, resultado.getSegundoNumero());
			validarAcerto(palpite, resultado.getTerceiroNumero());
			validarAcerto(palpite, resultado.getQuartoNumero());
			validarAcerto(palpite, resultado.getQuintoNumero());

			palpite.setNumerosSorteados(TypeUtil.validarStringComTracoProximo(palpite.getNumerosSorteados())
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()),
							resultado.getPrimeiroNumero()))
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()),
							resultado.getSegundoNumero()))
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()),
							resultado.getTerceiroNumero()))
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()),
							resultado.getQuartoNumero()))
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()),
							resultado.getQuintoNumero())));

			palpite.setNumerosSorteados(
					palpite.getNumerosSorteados().substring(0, palpite.getNumerosSorteados().length() - 1));

			listPalpiteParaAtualizar.add(palpite);
		}

		if (!listPalpiteParaAtualizar.isEmpty()) {

			this.palpiteRepository.save(listPalpiteParaAtualizar);

		}
	}

	private void validarAcerto(Palpite palpite, String numero) {
		if (!TypeUtil.validarString(palpite.getAcertos()).contains(numero) && palpite.getNumeros().contains(numero)) {
			palpite.setAcertos(TypeUtil.validarStringComTracoProximo(palpite.getAcertos()).concat(numero));
			palpite.setQuantidadeAcerto(TypeUtil.validarNumero(palpite.getQuantidadeAcerto()) + 1);
		}
	}
}
