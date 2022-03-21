package br.com.classificados.mt.repositories;

import br.com.classificados.mt.domain.Aviso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Integer> {

	List<Aviso> findAllByTitulo(String titulo);
	
	List<Aviso> findTop100ByOrderByDataDesc();
	
}
