package br.com.classificados.mt.dto;

import java.io.Serializable;

public class UsuarioPalpiteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String apelido;
	
	private Long quantidadeAcerto;
	
	private String email;
	
	private String nome;

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public Long getQuantidadeAcerto() {
		return quantidadeAcerto;
	}

	public void setQuantidadeAcerto(Long quantidadeAcerto) {
		this.quantidadeAcerto = quantidadeAcerto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public UsuarioPalpiteDTO(String apelido, Long quantidadeAcerto, String email, String nome) {
		super();
		this.apelido = apelido;
		this.quantidadeAcerto = quantidadeAcerto;
		this.email = email;
		this.nome = nome;
	}
	
	

}
