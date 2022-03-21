package br.com.classificados.mt.domain;

import br.com.classificados.mt.enums.Perfil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Integer codigo;
	
	@NotNull
	@Column(name = "nome", length = 50)
	private String nome;
	
	@NotNull
	@Column(name = "apelido", length = 15)
	private String apelido;
	
	@NotNull
	@Column(name = "telefone", length = 15)
	private String telefone;
	
	@NotNull
	@Column(name = "email", length = 40)
	private String email;
	
	@NotNull
	@Column(name = "senha", length = 20)
	private String senha;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "permissao", length = 20)
	private Perfil permissao;
	
	@Column(name = "pagou_ultima_mensalidade")
	private Boolean pagouUltimaMensalidade;
	
	@Column(name = "data_ultimo_pagamento")
	private LocalDate dataUltimoPagamento;
	
	@Column(name = "termos_aceito")
	private Boolean termosAceito;

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
	
	public Perfil getPermissao() {
		return permissao;
	}

	public void setPermissao(Perfil permissao) {
		this.permissao = permissao;
	}
	
	public Boolean getPagouUltimaMensalidade() {
		return pagouUltimaMensalidade;
	}

	public void setPagouUltimaMensalidade(Boolean pagouUltimaMensalidade) {
		this.pagouUltimaMensalidade = pagouUltimaMensalidade;
	}
	
	public LocalDate getDataUltimoPagamento() {
		return dataUltimoPagamento;
	}

	public void setDataUltimoPagamento(LocalDate dataUltimoPagamento) {
		this.dataUltimoPagamento = dataUltimoPagamento;
	}
	
	public Boolean getTermosAceito() {
		return termosAceito;
	}

	public void setTermosAceito(Boolean termosAceito) {
		this.termosAceito = termosAceito;
	}

	public Usuario(String nome, String apelido, String telefone, String email, String senha) {
		super();
		this.nome = nome;
		this.apelido = apelido;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
	}
	
	public Usuario() {}
	
}
