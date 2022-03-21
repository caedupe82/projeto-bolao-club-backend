package br.com.classificados.mt.repositories;

import br.com.classificados.mt.domain.Painel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PainelRepository extends JpaRepository<Painel, Integer> {

	@Query("SELECT p FROM Painel p WHERE MONTH(p.mesReferencia) = :mes AND YEAR(p.mesReferencia) = :ano")
	List<Painel> findAllByMesAndAno(@Param("mes") Integer mes, @Param("ano") Integer ano);
	
	Painel findFirstByMesReferencia(LocalDate mesReferencia);
}
