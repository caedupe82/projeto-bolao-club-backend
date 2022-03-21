package br.com.classificados.mt.services;

import br.com.classificados.mt.domain.Usuario;
import br.com.classificados.mt.dto.EsqueceuSenhaDTO;
import br.com.classificados.mt.dto.UsuarioDTO;
import br.com.classificados.mt.dto.UsuarioListDTO;
import br.com.classificados.mt.dto.UsuarioPalpiteDTO;
import br.com.classificados.mt.enums.Perfil;
import br.com.classificados.mt.repositories.UsuarioRepository;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailService emailService;

	public Usuario insert(Usuario usuario) {

		if (this.usuarioRepository.findUsuarioByApelido(usuario.getApelido()) != null) {
			throw new DataIntegrityException("Já possui um usuário com esse apelido.");
		}

		if (this.usuarioRepository.findByEmail(usuario.getEmail()) != null) {
			throw new DataIntegrityException("Já possui um usuário com esse e-mail.");
		}

		if (usuario.getCodigo() == null) {
			usuario.setPagouUltimaMensalidade(Boolean.FALSE);
			usuario.setPermissao(Perfil.USUARIO);

			this.emailService.enviarEmailBoasVindas(usuario.getEmail(), usuario.getNome());
		}

		return this.usuarioRepository.save(usuario);
	}

	public void atualizarUsuarioPago(String email) {

		Usuario usuario = this.usuarioRepository.findByEmail(email);
		
		usuario.setPagouUltimaMensalidade(Boolean.TRUE);
		usuario.setDataUltimoPagamento(LocalDate.now());
		
		this.usuarioRepository.save(usuario);
	}
	
	public void aceitarTermo (String email) {
		Logger.getInstance(UsuarioService.class).info("E-mail do usuário:" + email);
		Usuario usuario = this.usuarioRepository.findByEmail(email);
		
		usuario.setTermosAceito(Boolean.TRUE);
		
		this.usuarioRepository.save(usuario);
	}

	public List<UsuarioListDTO> findAll() {

		return this.usuarioRepository.findAllUsuarioByPermissao(Perfil.USUARIO);
	}
	
	public List<UsuarioPalpiteDTO> findAllByMesAndAno(Integer mes, Integer ano) {
		
		return this.usuarioRepository.findAllByMesAndAno(mes, ano);
	}
	
	public List<Usuario> findAllWithEmail() {

		return this.usuarioRepository.findAll();
	}

	public Usuario findOneByEmail(String email) {
		return this.usuarioRepository.findByEmail(email);
	}
	
	public Usuario esqueceuSenha(EsqueceuSenhaDTO esqueceuSenhaDTO) {

		Usuario usuarioRetornado = this.usuarioRepository.findOneByApelidoAndTelefoneAndEmail(esqueceuSenhaDTO.getApelido(), esqueceuSenhaDTO.getTelefone(), esqueceuSenhaDTO.getEmail());
		
		if (usuarioRetornado == null || usuarioRetornado.getCodigo() == null) {
			throw new DataIntegrityException("Apelido, Telefone ou E-mail estão incorretos. Por favor validar!");
		}
		
		usuarioRetornado.setSenha(esqueceuSenhaDTO.getSenha());
		
		return this.usuarioRepository.save(usuarioRetornado);
	}

	public Usuario fromEntity(UsuarioDTO usuarioDTO) {
		return new Usuario(usuarioDTO.getNome(), usuarioDTO.getApelido(), usuarioDTO.getTelefone(),
				usuarioDTO.getEmail(), usuarioDTO.getSenha());
	}
	
	public Boolean usuarioPagou(String email) {
		Usuario usuario = this.usuarioRepository.findByEmail(email);
	
		return usuario.getPagouUltimaMensalidade();
	}

}
