package br.com.classificados.mt.resources;

import br.com.classificados.mt.dto.PremioDTO;
import br.com.classificados.mt.services.PremioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/premios")
public class PremioResource {
	
	@Autowired
	private PremioService service;
	
	@RequestMapping(method=RequestMethod.GET, value = "/find-all")
	public ResponseEntity<List<PremioDTO>> findAll() {
		return ResponseEntity.ok().body(this.service.findAll());
	}

}
