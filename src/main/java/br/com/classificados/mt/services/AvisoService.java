package br.com.classificados.mt.services;

import br.com.classificados.mt.domain.Aviso;
import br.com.classificados.mt.dto.AvisoDTO;
import br.com.classificados.mt.repositories.AvisoRepository;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvisoService {
	
	@Autowired
	private AvisoRepository avisoRepository;
	
	public Aviso insert(Aviso aviso) {
		
		Aviso avisoParaSalvar = aviso;
		
		List<Aviso> list = this.avisoRepository.findAllByTitulo(avisoParaSalvar.getTitulo());
		
		if (aviso.getCodigo() != null) {
			avisoParaSalvar = this.avisoRepository.findOne(aviso.getCodigo());
			avisoParaSalvar.setDescricao(aviso.getDescricao());
			avisoParaSalvar.setTitulo(aviso.getTitulo());
		}
		
		if (avisoParaSalvar.getData() == null && avisoParaSalvar.getCodigo() == null) {
			avisoParaSalvar.setData(LocalDateTime.now().minusHours(3L));
		}
		
		if (list != null && !list.isEmpty() && list.get(0).getData().equals(avisoParaSalvar.getData()) && !list.get(0).getCodigo().equals(avisoParaSalvar.getCodigo())) {
			throw new DataIntegrityException("Já existe um Aviso cadastrado com esse Título!");
		}
		
		return this.avisoRepository.save(avisoParaSalvar);
	}
	
	public List<AvisoDTO> findAll() {
		List<Aviso> list = this.avisoRepository.findTop100ByOrderByDataDesc();
		
		if (list != null && !list.isEmpty()) {
			return fromListEntityToListDTO(list);
		}
		
		return new ArrayList<>();
	}
	
	public void excluir(Integer codigo) {
		this.avisoRepository.delete(codigo);
	}
	
	public AvisoDTO findOne(Integer codigo) {
		Aviso aviso = this.avisoRepository.findOne(codigo);
		
		return this.fromDTO(aviso);
	}

	private List<AvisoDTO> fromListEntityToListDTO(List<Aviso> list) {
		List<AvisoDTO> listDTO = new ArrayList<>();
		for (Aviso aviso : list) {
			listDTO.add(new AvisoDTO(aviso.getCodigo(), aviso.getTitulo(), aviso.getDescricao(), aviso.getData().toString()));
		}
		return listDTO;
	}
	
	public Aviso fromEntity(AvisoDTO avisoDTO) {
		return new Aviso(avisoDTO.getCodigo(), avisoDTO.getTitulo(), avisoDTO.getDescricao());
	}
	
	public AvisoDTO fromDTO(Aviso aviso) {
		return new AvisoDTO(aviso.getCodigo(), aviso.getTitulo(), aviso.getDescricao(), aviso.getData().toString());
	}
}
