package br.com.classificados.mt.resources;

import br.com.classificados.mt.domain.Aviso;
import br.com.classificados.mt.dto.AvisoDTO;
import br.com.classificados.mt.services.AvisoService;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/avisos")
public class AvisoResource {
	
	@Autowired
	private AvisoService service;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> insert(@Valid @RequestBody AvisoDTO avisoDTO) {
		Aviso obj = this.service.fromEntity(avisoDTO);
		try {
			Aviso aviso = this.service.insert(obj);
			return ResponseEntity.ok().body(aviso);
		} catch (DataIntegrityException e) {
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(e.getMessage());
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/find-all")
	public ResponseEntity<List<AvisoDTO>> findAll() {
		return ResponseEntity.ok().body(this.service.findAll());
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/excluir/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
		this.service.excluir(id);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/find-one/{codigo}")
	public ResponseEntity<AvisoDTO> findOne(@PathVariable("codigo") Integer codigo) {
		return ResponseEntity.ok().body(this.service.findOne(codigo));
	}
	
}
