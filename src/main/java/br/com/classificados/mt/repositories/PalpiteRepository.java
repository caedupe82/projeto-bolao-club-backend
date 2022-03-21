package br.com.classificados.mt.repositories;

import br.com.classificados.mt.domain.Palpite;
import br.com.classificados.mt.dto.RankingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PalpiteRepository extends JpaRepository<Palpite, Integer> {

	List<Palpite> findAllByUsuario(Integer usuario);

	@Query("SELECT p FROM Palpite p WHERE MONTH(p.data) = :mes AND YEAR(p.data) = :ano")
	List<Palpite> findAllByMesAndAno(@Param("mes") Integer mes, @Param("ano") Integer ano);

    @Query("SELECT NEW RankingDTO(u.apelido, p.numeros, p.quantidadeAcerto) FROM Palpite p, Usuario u WHERE p.usuario =  u.codigo AND MONTH(p.data) = :mes AND YEAR(p.data) = :ano ORDER BY p.quantidadeAcerto DESC")
	List<RankingDTO> findAllRankingByMesAndAno(@Param("mes") Integer mes, @Param("ano") Integer ano);
}