package br.com.classificados.mt.dto;

import java.io.Serializable;

public class ResultadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	private String primeiroNumero;
	
	private String segundoNumero;
	
	private String terceiroNumero;
	
	private String quartoNumero;
	
	private String quintoNumero;
	
	private String numeroConcurso;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getPrimeiroNumero() {
		return primeiroNumero;
	}

	public void setPrimeiroNumero(String primeiroNumero) {
		this.primeiroNumero = primeiroNumero;
	}

	public String getSegundoNumero() {
		return segundoNumero;
	}

	public void setSegundoNumero(String segundoNumero) {
		this.segundoNumero = segundoNumero;
	}

	public String getTerceiroNumero() {
		return terceiroNumero;
	}

	public void setTerceiroNumero(String terceiroNumero) {
		this.terceiroNumero = terceiroNumero;
	}

	public String getQuartoNumero() {
		return quartoNumero;
	}

	public void setQuartoNumero(String quartoNumero) {
		this.quartoNumero = quartoNumero;
	}

	public String getQuintoNumero() {
		return quintoNumero;
	}

	public void setQuintoNumero(String quintoNumero) {
		this.quintoNumero = quintoNumero;
	}

	public String getNumeroConcurso() {
		return numeroConcurso;
	}

	public void setNumeroConcurso(String numeroConcurso) {
		this.numeroConcurso = numeroConcurso;
	}

	public ResultadoDTO() {}

	public ResultadoDTO(String primeiroNumero, String segundoNumero, String terceiroNumero, String quartoNumero,
			String quintoNumero, String numeroConcurso) {
		super();
		this.primeiroNumero = primeiroNumero;
		this.segundoNumero = segundoNumero;
		this.terceiroNumero = terceiroNumero;
		this.quartoNumero = quartoNumero;
		this.quintoNumero = quintoNumero;
		this.numeroConcurso = numeroConcurso;
	}
	
}
