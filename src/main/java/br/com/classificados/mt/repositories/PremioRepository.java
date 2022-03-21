package br.com.classificados.mt.repositories;

import br.com.classificados.mt.domain.Premio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PremioRepository extends JpaRepository<Premio, Integer> {
	
	List<Premio> findAllByOrderByDataDesc();
	
	Premio findFirstByData(LocalDate data);
	
}
