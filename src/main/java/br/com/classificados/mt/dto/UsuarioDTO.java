package br.com.classificados.mt.dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	private String nome;
	
	private String apelido;
	
	private String telefone;
	
	private String email;
	
	private String senha;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsuarioDTO() {}

	public UsuarioDTO(Integer codigo, String nome, String apelido, String telefone, String email, String senha) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.apelido = apelido;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
	}
	
}
