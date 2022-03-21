package br.com.classificados.mt.resources;

import br.com.classificados.mt.domain.Resultado;
import br.com.classificados.mt.dto.ResultadoDTO;
import br.com.classificados.mt.services.ResultadoService;
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
@RequestMapping(value="/resultados")
public class ResultadoResource {
	
	@Autowired
	private ResultadoService service;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> insert(@Valid @RequestBody ResultadoDTO resultadoDTO) {
		Resultado obj = this.service.fromEntity(resultadoDTO);
		try {
			Resultado resultado = this.service.insert(obj);
			return ResponseEntity.ok().body(resultado);
		} catch (DataIntegrityException e) {
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(e.getMessage());
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/find-all-mes-ano")
	public ResponseEntity<List<Resultado>> findAllByMesAndAno() {
		return ResponseEntity.ok().body(this.service.findAllByMesAndAno());
	}
	
}
