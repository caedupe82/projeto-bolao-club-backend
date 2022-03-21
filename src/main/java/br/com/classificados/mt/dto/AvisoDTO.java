package br.com.classificados.mt.dto;

import java.io.Serializable;

public class AvisoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	private String titulo;
	
	private String descricao;
	
	private String data;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public AvisoDTO() {}

	public AvisoDTO(Integer codigo, String titulo, String descricao, String data) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.descricao = descricao;
		this.data = data;
	}
	
}
