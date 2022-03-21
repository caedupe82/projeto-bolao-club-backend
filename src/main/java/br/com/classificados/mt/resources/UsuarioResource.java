package br.com.classificados.mt.resources;

import br.com.classificados.mt.domain.Usuario;
import br.com.classificados.mt.dto.EsqueceuSenhaDTO;
import br.com.classificados.mt.dto.UsuarioDTO;
import br.com.classificados.mt.dto.UsuarioListDTO;
import br.com.classificados.mt.services.UsuarioService;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService service;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> insert(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		Usuario obj = this.service.fromEntity(usuarioDTO);
		try {
			Usuario usuario = this.service.insert(obj);
			return ResponseEntity.ok().body(usuario);
		} catch (DataIntegrityException e) {
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(e.getMessage());
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/esqueceu-senha")
	public ResponseEntity<Object> esqueceuSenha(@Valid @RequestBody EsqueceuSenhaDTO esqueceuSenhaDTO) {
		try {
			Usuario usuario = this.service.esqueceuSenha(esqueceuSenhaDTO);
			return ResponseEntity.ok().body(usuario);
		} catch (DataIntegrityException e) {
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(e.getMessage());
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/find-all")
	public ResponseEntity<List<UsuarioListDTO>> findAll() {
		return ResponseEntity.ok().body(this.service.findAll());
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/usuario-pagou")
	public ResponseEntity<Boolean> usuarioPagou(@Valid @RequestBody String email) {
			return ResponseEntity.ok().body(this.service.usuarioPagou(email));
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/aceitar-termo")
	public ResponseEntity<Object> aceitarTermo(@Valid @RequestBody String email) {
			this.service.aceitarTermo(email);
			return ResponseEntity.ok().body(email);
	}
	
}
