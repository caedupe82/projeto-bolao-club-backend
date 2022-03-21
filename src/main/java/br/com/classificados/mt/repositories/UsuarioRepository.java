package br.com.classificados.mt.repositories;

import br.com.classificados.mt.domain.Usuario;
import br.com.classificados.mt.dto.UsuarioListDTO;
import br.com.classificados.mt.dto.UsuarioPalpiteDTO;
import br.com.classificados.mt.enums.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Usuario findUsuarioByApelido(String apelido);
	
	@Transactional(readOnly=true)
	Usuario findByEmail(String email);


    @Query("SELECT NEW UsuarioListDTO(u.codigo, u.nome, u.telefone, u.pagouUltimaMensalidade) FROM Usuario u WHERE u.permissao = :permissao")
	List<UsuarioListDTO> findAllUsuarioByPermissao(@Param("permissao") Perfil permissao);
	
	Usuario findOneByApelidoAndTelefoneAndEmail(String apelido, String telefone, String email);
	
	List<Usuario> findAllByPermissao(Perfil permissao);

    @Query("SELECT NEW UsuarioPalpiteDTO(u.apelido, p.quantidadeAcerto, u.email, u.nome) FROM Usuario u, Palpite p WHERE u.codigo = p.usuario AND MONTH(p.data) = :mes AND YEAR(p.data) = :ano ORDER BY p.quantidadeAcerto DESC, u.apelido")
	List<UsuarioPalpiteDTO> findAllByMesAndAno(@Param("mes") Integer mes, @Param("ano") Integer ano);
	
	List<Usuario> findAllByPagouUltimaMensalidade(Boolean pagouUltimaMensalidade);
}
