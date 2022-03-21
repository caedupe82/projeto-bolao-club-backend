package br.com.classificados.mt.security;

import br.com.classificados.mt.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	private Perfil permissao;
	private Boolean usuarioPagou;
	
	public UserSS() {
	}
	
	public UserSS(Integer id, String email, String senha, List<Perfil> listPermissao, Boolean usuarioPagou) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = listPermissao.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
		this.permissao = listPermissao.get(0);
		this.usuarioPagou = usuarioPagou;
	}

	public Integer getId() {
		return id;
	}
	
	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Perfil getPermissao() {
		return permissao;
	}

	public void setPermissao(Perfil permissao) {
		this.permissao = permissao;
	}

	public boolean isUsuarioPagou() {
		return usuarioPagou;
	}

	public void setUsuarioPagou(boolean usuarioPagou) {
		this.usuarioPagou = usuarioPagou;
	}
	
}