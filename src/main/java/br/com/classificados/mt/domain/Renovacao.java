package br.com.classificados.mt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_renovacao")
public class Renovacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Integer codigo;
	
	@NotNull
	@Column(name = "usuario")
	private Integer usuario;
	
	@NotNull
	@Column(name = "data_solicitacao")
	private LocalDate dataSolicitacao;
	
	@Column(name = "aprovado")
	private Boolean aprovado;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public LocalDate getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(LocalDate dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	public Renovacao() {}

	public Renovacao(Integer codigo, Integer usuario, LocalDate dataSolicitacao, Boolean aprovado) {
		super();
		this.codigo = codigo;
		this.usuario = usuario;
		this.dataSolicitacao = dataSolicitacao;
		this.aprovado = aprovado;
	}
	
}
