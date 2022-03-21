package br.com.classificados.mt.dto;

import java.io.Serializable;

public class UsuarioListDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	private String nome;
	
	private String telefone;
	
	private Boolean pagouUltimaMensalidade;

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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Boolean getPagouUltimaMensalidade() {
		return pagouUltimaMensalidade;
	}

	public void setPagouUltimaMensalidade(Boolean pagouUltimaMensalidade) {
		this.pagouUltimaMensalidade = pagouUltimaMensalidade;
	}
	
	public UsuarioListDTO(Integer codigo, String nome, String telefone,
			Boolean pagouUltimaMensalidade) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.telefone = telefone;
		this.pagouUltimaMensalidade = pagouUltimaMensalidade;
	}
	
}
