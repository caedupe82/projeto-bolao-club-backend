package br.com.classificados.mt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

public class PalpiteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	private String numeros;
	
	@JsonFormat(pattern = "YYYY-MM-dd")
	private String data;
	
	private String email;
	
	private Boolean fechamentoSistema = Boolean.FALSE;
	
	private String acertos;
	
	private String numerosSorteados;
	
	private Long quantidadeAcerto;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNumeros() {
		return numeros;
	}

	public void setNumeros(String numeros) {
		this.numeros = numeros;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Boolean getFechamentoSistema() {
		return fechamentoSistema;
	}

	public void setFechamentoSistema(Boolean fechamentoSistema) {
		this.fechamentoSistema = fechamentoSistema;
	}
	
	public String getAcertos() {
		return acertos;
	}

	public void setAcertos(String acertos) {
		this.acertos = acertos;
	}
	
	public String getNumerosSorteados() {
		return numerosSorteados;
	}

	public void setNumerosSorteados(String numerosSorteados) {
		this.numerosSorteados = numerosSorteados;
	}
	
	public Long getQuantidadeAcerto() {
		return quantidadeAcerto;
	}

	public void setQuantidadeAcerto(Long quantidadeAcerto) {
		this.quantidadeAcerto = quantidadeAcerto;
	}
	
	public PalpiteDTO(Integer codigo, String numeros, String data, String email) {
		super();
		this.codigo = codigo;
		this.numeros = numeros;
		this.data = data;
		this.email = email;
	}
	
	public PalpiteDTO() {}
	
}