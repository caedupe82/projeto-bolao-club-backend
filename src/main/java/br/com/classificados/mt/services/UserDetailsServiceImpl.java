package br.com.classificados.mt.services;

import br.com.classificados.mt.domain.Usuario;
import br.com.classificados.mt.enums.Perfil;
import br.com.classificados.mt.repositories.UsuarioRepository;
import br.com.classificados.mt.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario cli = repo.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		
		List<Perfil> listPermissao = new ArrayList<>();
		listPermissao.add(cli.getPermissao());
		
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		
		return new UserSS(cli.getCodigo(), cli.getEmail(), encode.encode(cli.getSenha()), listPermissao, cli.getPagouUltimaMensalidade());
	}
}
