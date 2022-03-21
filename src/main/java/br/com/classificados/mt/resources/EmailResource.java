package br.com.classificados.mt.resources;

import br.com.classificados.mt.dto.FaleConoscoDTO;
import br.com.classificados.mt.dto.SolicitacaoRenovacaoDTO;
import br.com.classificados.mt.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/emails")
public class EmailResource {
	
	@Autowired
	private EmailService service;
	
	@RequestMapping(method=RequestMethod.POST, value = "/fale-conosco")
	public ResponseEntity<Void> faleConosco(@Valid @RequestBody FaleConoscoDTO faleConoscoDTO) {
		this.service.enviarEmailContato(faleConoscoDTO);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/renovacao")
	public ResponseEntity<Object> renovacao(@Valid @RequestBody SolicitacaoRenovacaoDTO solicitacaoRenovacaoDTO) {
		try {
		return ResponseEntity.ok().body(this.service.enviarEmailSolicitacaoRenovacao(solicitacaoRenovacaoDTO));
		} catch (Exception e) {
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(e.getMessage());
		}
	}
	
}
