package br.com.classificados.mt.dto;

import java.io.Serializable;

public class SolicitacaoRenovacaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	
	private String arquivo;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

}
