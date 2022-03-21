package br.com.classificados.mt.resources;

import br.com.classificados.mt.dto.RenovacaoListDTO;
import br.com.classificados.mt.services.RenovacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/renovacoes")
public class RenovacaoResource {
	
	@Autowired
	private RenovacaoService service;
	
	@RequestMapping(method=RequestMethod.GET, value = "/find-all")
	public ResponseEntity<List<RenovacaoListDTO>> findAll() {
		return ResponseEntity.ok().body(this.service.findAll());
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/aprovar-ou-reprovar")
	public ResponseEntity<RenovacaoListDTO> aprovarOuReprovar(@Valid @RequestBody RenovacaoListDTO renovacaoListDTO) { 
	
		return ResponseEntity.ok().body(this.service.aprovarOuReprovar(renovacaoListDTO));
	}
}
