package br.com.classificados.mt.repositories;

import br.com.classificados.mt.domain.Renovacao;
import br.com.classificados.mt.dto.RenovacaoListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RenovacaoRepository extends JpaRepository<Renovacao, Integer> {
	
	@Query("SELECT r FROM Renovacao r WHERE (r.aprovado = :aprovado OR r.aprovado = null) AND r.usuario = :usuario")
	List<Renovacao> findAllRenovacaoByAprovadoAndUsuario(@Param("aprovado") Boolean aprovado, @Param("usuario") Integer usuario);

    @Query("SELECT NEW RenovacaoListDTO(r.codigo, u.nome, u.email) FROM Renovacao r, Usuario u WHERE u.codigo = r.usuario AND r.aprovado = null")
	List<RenovacaoListDTO> findAllRenovacao();
	
	@Query("SELECT r FROM Renovacao r WHERE MONTH(r.dataSolicitacao) = :mes AND YEAR(r.dataSolicitacao) = :ano AND r.aprovado = true")
	List<Renovacao> findAllByMesAndAno(@Param("mes") Integer mes, @Param("ano") Integer ano);
}
