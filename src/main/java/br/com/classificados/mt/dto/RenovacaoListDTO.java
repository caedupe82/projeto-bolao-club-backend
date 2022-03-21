package br.com.classificados.mt.dto;

import java.io.Serializable;

public class RenovacaoListDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	private String nomeUsuario;
	
	private String emailUsuario;
	
	private String motivo;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public RenovacaoListDTO() {}

	public RenovacaoListDTO(Integer codigo, String nomeUsuario, String emailUsuario) {
		super();
		this.codigo = codigo;
		this.nomeUsuario = nomeUsuario;
		this.emailUsuario = emailUsuario;
	}
	
}
