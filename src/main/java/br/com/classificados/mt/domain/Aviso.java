package br.com.classificados.mt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_aviso")
public class Aviso implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Integer codigo;
	
	@NotNull
	@Column(name = "titulo", length = 25)
	private String titulo;
	
	@NotNull
	@Column(name = "descricao", length = 120)
	private String descricao;
	
	@NotNull
	@Column(name = "data")
	private LocalDateTime data;

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

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	public Aviso() {}

	public Aviso(Integer codigo, String titulo, String descricao) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	
	
}
