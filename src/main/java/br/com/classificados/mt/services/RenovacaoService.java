package br.com.classificados.mt.services;

import br.com.classificados.mt.domain.Renovacao;
import br.com.classificados.mt.dto.RenovacaoListDTO;
import br.com.classificados.mt.repositories.RenovacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RenovacaoService {
	
	@Autowired
	private RenovacaoRepository renovacaoRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public List<RenovacaoListDTO> findAll() {
		return this.renovacaoRepository.findAllRenovacao();
	}
	
	public List<Renovacao> findAllByMesAndAno(Integer mes, Integer ano) {
		return this.renovacaoRepository.findAllByMesAndAno(mes, ano);
	}

	public RenovacaoListDTO aprovarOuReprovar(RenovacaoListDTO renovacaoListDTO) {
		
		Renovacao renovacao = this.renovacaoRepository.findOne(renovacaoListDTO.getCodigo());
		
		if (renovacaoListDTO.getMotivo() != null) {
			renovacao.setAprovado(Boolean.FALSE);
		} else {
			renovacao.setAprovado(Boolean.TRUE);
			this.usuarioService.atualizarUsuarioPago(renovacaoListDTO.getEmailUsuario());
		}
		
		this.renovacaoRepository.save(renovacao);
		
		if (renovacaoListDTO.getMotivo() != null) {
			this.emailService.enviarEmailReprovacao(renovacaoListDTO.getEmailUsuario(), renovacaoListDTO.getNomeUsuario(), renovacaoListDTO.getMotivo());
		} else {
			this.emailService.enviarEmailAprovacao(renovacaoListDTO.getEmailUsuario(), renovacaoListDTO.getNomeUsuario());
		}
		
		return renovacaoListDTO;
	}
	
}
