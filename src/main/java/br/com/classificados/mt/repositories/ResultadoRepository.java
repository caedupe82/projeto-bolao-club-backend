package br.com.classificados.mt.repositories;

import br.com.classificados.mt.domain.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Integer> {

	List<Resultado> findAllByData(LocalDate data);
	
	@Query("SELECT r FROM Resultado r WHERE MONTH(r.data) = :mes AND YEAR(r.data) = :ano ORDER BY r.numeroConcurso ASC ")
	List<Resultado> findAllByMesAndAno(@Param("mes") Integer mes, @Param("ano") Integer ano);
	
}
