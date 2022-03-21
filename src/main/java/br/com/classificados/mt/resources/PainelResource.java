package br.com.classificados.mt.resources;

import br.com.classificados.mt.domain.Painel;
import br.com.classificados.mt.dto.PainelDTO;
import br.com.classificados.mt.services.PainelService;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/paineis")
public class PainelResource {

	@Autowired
	private PainelService service;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> insert(@Valid @RequestBody PainelDTO painelDTO) {
		try {
			Painel obj = this.service.fromEntity(painelDTO);
			Painel painel = this.service.insert(obj);
			return ResponseEntity.ok().body(painel);
		} catch (DataIntegrityException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/find-one")
	public ResponseEntity<Object> findOne() {
		try {
			PainelDTO painelDTO = this.service.findOne();
			return ResponseEntity.ok().body(painelDTO);
		} catch (DataIntegrityException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/find-one-painel-usuario")
	public ResponseEntity<Object> findOnePainelUsuario() {
		try {
			return ResponseEntity.ok().body(this.service.findOnePainelUsuario());
		} catch (DataIntegrityException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/selecionar-painel")
	public ResponseEntity<Object> findOnePainelPorData(@RequestBody String data) {
		try {
			return ResponseEntity.ok().body(this.service.findOnePainelPorData(data));
		} catch (DataIntegrityException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
