package br.com.classificados.mt.services;

import br.com.classificados.mt.domain.Painel;
import br.com.classificados.mt.domain.Premio;
import br.com.classificados.mt.domain.Renovacao;
import br.com.classificados.mt.dto.PremioDTO;
import br.com.classificados.mt.dto.UsuarioPalpiteDTO;
import br.com.classificados.mt.repositories.PainelRepository;
import br.com.classificados.mt.repositories.PremioRepository;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import br.com.classificados.mt.utils.DateUtil;
import br.com.classificados.mt.utils.TypeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PremioService {
	
	@Autowired
	private PremioRepository premioRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PainelRepository painelRepository;
	
	@Autowired
	private RenovacaoService renovacaoService;
	
	public Premio insert(Premio premio) {
		
		return this.premioRepository.save(premio);
	}
	
	public List<PremioDTO> findAll() {
		List<Premio> list = this.premioRepository.findAllByOrderByDataDesc();
		
		if (list != null && !list.isEmpty()) {
			List<PremioDTO> listPremio = new ArrayList<>();
			
			for (Premio p : list) {
				listPremio.add(new PremioDTO(TypeUtil.validarString(p.getVencedorPrimeiro()), 
						TypeUtil.validarString(p.getVencedorSegundo()), 
						TypeUtil.validarString(p.getVencedorPeFrio()), 
						TypeUtil.validarString(p.getVencedorAcumulado()), 
						TypeUtil.validarDouble(p.getPremioPrimeiro()), 
						TypeUtil.validarDouble(p.getPremioSegundo()), 
						TypeUtil.validarDouble(p.getPremioPeFrio()), 
						TypeUtil.validarDouble(p.getPremioAcumulado()), 
						(DateUtil.getMesExtenso(p.getData().getMonth()) + "/" + p.getData().getYear())));
			}
			
			return listPremio;
		}
		
		return new ArrayList<>();
	}
	
	public Premio findFirstByDate(LocalDate data) {
		return this.premioRepository.findFirstByData(data);
	}
	
	public void chamarPremioPorData(LocalDate data) {
		
		Premio premioValidar = this.premioRepository.findFirstByData(data);
		
		if (premioValidar != null) {
			Logger.getInstance(PremioService.class).error("O prêmio desse mês já saiu.");
			throw new DataIntegrityException("O prêmio desse mês já saiu.");
		}
		
		List<Renovacao> listRenovacao = this.renovacaoService.findAllByMesAndAno(data.getMonthValue(), data.getYear());
		
		Integer quantidadeUsuario = 0;
		
		if (listRenovacao != null && !listRenovacao.isEmpty()) {
			quantidadeUsuario = listRenovacao.size();
		}
		
		Painel painel = this.painelRepository.findAllByMesAndAno(data.getMonthValue(), data.getYear()).get(0);
		
		Premio premio = new Premio();
		
		Double valorAcumulado = painel.getPremioAcumulado();
		Double valorPrincipal = (quantidadeUsuario * painel.getValorCota()) * (painel.getPremioPrincipal() / 100);
		Double valorSecundario = (quantidadeUsuario * painel.getValorCota()) * (painel.getPremioSecundario() / 100);
		Double valorPeFrio = (quantidadeUsuario * painel.getValorCota()) * (painel.getPremioPeFrio() / 100);
		
		premio.setData(data);
		premio.setPremioAcumulado(valorAcumulado);
		premio.setPremioPeFrio(valorPeFrio);
		premio.setPremioPrimeiro(valorPrincipal);
		premio.setPremioSegundo(valorSecundario);
		
		LocalDate now = LocalDate.now();
		
		List<UsuarioPalpiteDTO> listUsuario = this.usuarioService.findAllByMesAndAno(now.getMonthValue(), now.getYear());
	
		if (listUsuario != null) {
			for (UsuarioPalpiteDTO u : listUsuario) {
				
				if (u.getQuantidadeAcerto() >= 20 && premio.getVencedorPrimeiro() == null) {
					premio.setVencedorPrimeiro(u.getApelido());
				} else if (u.getQuantidadeAcerto() >= 19 && premio.getVencedorSegundo() == null) {
					premio.setVencedorSegundo(u.getApelido());
				}
				
				if (!u.getApelido().equals(premio.getVencedorPrimeiro()) || !u.getApelido().equals(premio.getVencedorSegundo())) {
					premio.setVencedorPeFrio(u.getApelido());
				}
				
				if (premio.getVencedorPrimeiro()  != null) {
					premio.setVencedorAcumulado(u.getApelido());
				}
				
			}
		}
		
		this.premioRepository.save(premio);
		
		try {
			this.emailService.chamarEmailPremioDoMes(listUsuario, premio);
		} catch (Exception e) {
			Logger.getInstance(PremioService.class).error("Falha ao notificar os usuários sobre os prêmios.");
		}
		
	}
}
