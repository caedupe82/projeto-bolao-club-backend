package br.com.classificados.mt.services;

import br.com.classificados.mt.domain.Painel;
import br.com.classificados.mt.domain.Palpite;
import br.com.classificados.mt.domain.Resultado;
import br.com.classificados.mt.dto.ResultadoDTO;
import br.com.classificados.mt.repositories.PalpiteRepository;
import br.com.classificados.mt.repositories.ResultadoRepository;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import br.com.classificados.mt.utils.DateUtil;
import br.com.classificados.mt.utils.TypeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultadoService {
	
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
	
	public Resultado insert(Resultado resultado) {
		
		LocalDateTime now = LocalDateTime.now().minusHours(3L);
		
		List<Painel> listPainel = this.painelService.findAllByMesAndAno(now.toLocalDate());
		
		Painel painel = new Painel();
		
		if (listPainel != null && !listPainel.isEmpty()) {
			painel = listPainel.get(0);
		}
		
		if (painel.getPrimeiroSorteio() == null || 
				(painel.getPrimeiroSorteio().getMonthValue() != now.getMonthValue() ||
				now.getDayOfMonth() < painel.getPrimeiroSorteio().getDayOfMonth())) {
			throw new DataIntegrityException("O concurso ainda não foi aberto para sorteios.");
		}

		List<Resultado> listResultado = this.resultadoRepository.findAllByMesAndAno(now.getMonthValue(),
				now.getYear());

		int quantidadeResultado = listResultado == null || listResultado.isEmpty() ? 0 : listResultado.size();

		if (quantidadeResultado >= 20 || now.getDayOfMonth() == now.toLocalDate().lengthOfMonth()) {
			throw new DataIntegrityException("O último resultado do mês já foi gerado.");
		}
		
		List<Resultado> list = this.resultadoRepository.findAllByData(now.toLocalDate());
		
		if (list != null && !list.isEmpty()) {
			throw new DataIntegrityException("Já foi criado um resultado para o dia de hoje!");
		}
		
		Boolean primeiroNumeroRepetido = false;
		Boolean segundoNumeroRepetido = false;
		Boolean terceiroNumeroRepetido = false;
		Boolean quartoNumeroRepetido = false;
		Boolean quintoNumeroRepetido = false;
		
		if (resultado.getPrimeiroNumero().equals(resultado.getSegundoNumero()) || 
				resultado.getPrimeiroNumero().equals(resultado.getTerceiroNumero()) ||
				resultado.getPrimeiroNumero().equals(resultado.getQuartoNumero()) ||
				resultado.getPrimeiroNumero().equals(resultado.getQuintoNumero())) {
			primeiroNumeroRepetido = true;
		}
		
		if (resultado.getSegundoNumero().equals(resultado.getPrimeiroNumero()) || 
				resultado.getSegundoNumero().equals(resultado.getTerceiroNumero()) ||
				resultado.getSegundoNumero().equals(resultado.getQuartoNumero()) ||
				resultado.getSegundoNumero().equals(resultado.getQuintoNumero())) {
			segundoNumeroRepetido = true;
		}
		
		if (resultado.getTerceiroNumero().equals(resultado.getPrimeiroNumero()) || 
				resultado.getTerceiroNumero().equals(resultado.getSegundoNumero()) ||
				resultado.getTerceiroNumero().equals(resultado.getQuartoNumero()) ||
				resultado.getTerceiroNumero().equals(resultado.getQuintoNumero())) {
			terceiroNumeroRepetido = true;
		}
		
		if (resultado.getQuartoNumero().equals(resultado.getPrimeiroNumero()) || 
				resultado.getQuartoNumero().equals(resultado.getSegundoNumero()) ||
				resultado.getQuartoNumero().equals(resultado.getTerceiroNumero()) ||
				resultado.getQuartoNumero().equals(resultado.getQuintoNumero())) {
			quartoNumeroRepetido = true;
		}
		
		if (resultado.getQuintoNumero().equals(resultado.getPrimeiroNumero()) || 
				resultado.getQuintoNumero().equals(resultado.getSegundoNumero()) ||
				resultado.getQuintoNumero().equals(resultado.getTerceiroNumero()) ||
				resultado.getQuintoNumero().equals(resultado.getQuartoNumero())) {
			quintoNumeroRepetido = true;
		}
		
		if (primeiroNumeroRepetido || segundoNumeroRepetido || terceiroNumeroRepetido || quartoNumeroRepetido || quintoNumeroRepetido) {
			throw new DataIntegrityException("Os números estão repetidos! Por favor validar.");
		}
		
		atualizarPalpitesUsuario(resultado);

		LocalDateTime servidor = LocalDateTime.now().minusHours(3L);
		
		resultado.setData(servidor.toLocalDate());
		
		this.resultadoRepository.save(resultado);
		
		if ((quantidadeResultado + 1) < 20 || !(now.getDayOfMonth() == now.toLocalDate().lengthOfMonth())) {
			try {
				this.emailService.chamarEmailResultadoDoDia();
			} catch (Exception e) {
				Logger.getInstance(ResultadoService.class).error("Falha ao notificar os usuários sobre o resultado");
			}
		} else {
			this.premioService.chamarPremioPorData(now.toLocalDate());
		}
		
		return resultado;
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
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()), resultado.getPrimeiroNumero()))
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()), resultado.getSegundoNumero()))
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()), resultado.getTerceiroNumero()))
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()), resultado.getQuartoNumero()))
					.concat(TypeUtil.possuiString(TypeUtil.validarString(palpite.getNumerosSorteados()), resultado.getQuintoNumero())));
			
			palpite.setNumerosSorteados(palpite.getNumerosSorteados().substring(0, palpite.getNumerosSorteados().length() - 1));
			
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
	
	public List<Resultado> findAllByMesAndAno() {
		LocalDate now = LocalDate.now();
		
		List<Resultado> list = this.resultadoRepository.findAllByMesAndAno(now.getMonthValue(), now.getYear());
		
		if (list == null || list.isEmpty()) {
			return new ArrayList<>();
		}
		
		for (Resultado resultado : list) {
			resultado.setNumeroConcurso(resultado.getNumeroConcurso().concat(" - ").concat(DateUtil.converterDataPorFormato(resultado.getData())));
		}
		
		return list;
	}
	
	public Resultado fromEntity(ResultadoDTO resultadoDTO) {
		return new Resultado(resultadoDTO.getPrimeiroNumero(), resultadoDTO.getSegundoNumero(), 
				resultadoDTO.getTerceiroNumero(), resultadoDTO.getQuartoNumero(),
				resultadoDTO.getQuintoNumero(), resultadoDTO.getNumeroConcurso());
	}
	
}
