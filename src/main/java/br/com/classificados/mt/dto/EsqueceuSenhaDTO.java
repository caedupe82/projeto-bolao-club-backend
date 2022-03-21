package br.com.classificados.mt.dto;

import java.io.Serializable;

public class EsqueceuSenhaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String apelido;
	
	private String telefone;
	
	private String email;
	
	private String senha;

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

	public EsqueceuSenhaDTO() {}

	public EsqueceuSenhaDTO(String apelido, String telefone, String email, String senha) {
		super();
		this.apelido = apelido;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
	}
	
}
