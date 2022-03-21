package br.com.classificados.mt.resources;

import br.com.classificados.mt.domain.Palpite;
import br.com.classificados.mt.dto.PalpiteDTO;
import br.com.classificados.mt.dto.RankingDTO;
import br.com.classificados.mt.services.PalpiteService;
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
@RequestMapping(value = "/palpites")
public class PalpiteResource {

	@Autowired
	private PalpiteService service;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> insert(@Valid @RequestBody PalpiteDTO palpiteDTO) {
		Palpite obj = this.service.fromEntity(palpiteDTO);
		try {
			Palpite palpite = this.service.insert(obj);
			return ResponseEntity.ok().body(palpite);
		} catch (DataIntegrityException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/find-one")
	public ResponseEntity<Object> findOneByEmail(@Valid @RequestBody String email) {
		try {
			PalpiteDTO palpiteDTO = this.service.findOneByUsuario(email);
			return ResponseEntity.ok().body(palpiteDTO);
		} catch (DataIntegrityException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/find-all-ranking-mes-ano")
	public ResponseEntity<List<RankingDTO>> findAllRankingByMesAndAno() {
		return ResponseEntity.ok().body(this.service.findAllRankingByMesAndAno());
	}
	
}
