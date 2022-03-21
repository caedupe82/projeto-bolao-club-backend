package br.com.classificados.mt.dto;

import java.io.Serializable;

public class PainelDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	private Double valorCota;
	
	private Double premioPrincipal;

	private Double premioSecundario;
	
	private Double premioPeFrio;
	
	private Double premioAcumulado;
	
	private Double taxaAdministrativa;
	
	private String mesReferencia;
	
	private String aberturaSistema;
	
	private String fechamentoSistema;
	
	private String primeiroSorteio;
	
	private Long totalParticipantes;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Double getValorCota() {
		return valorCota;
	}

	public void setValorCota(Double valorCota) {
		this.valorCota = valorCota;
	}

	public Double getPremioPrincipal() {
		return premioPrincipal;
	}

	public void setPremioPrincipal(Double premioPrincipal) {
		this.premioPrincipal = premioPrincipal;
	}

	public Double getPremioSecundario() {
		return premioSecundario;
	}

	public void setPremioSecundario(Double premioSecundario) {
		this.premioSecundario = premioSecundario;
	}

	public Double getPremioPeFrio() {
		return premioPeFrio;
	}

	public void setPremioPeFrio(Double premioPeFrio) {
		this.premioPeFrio = premioPeFrio;
	}

	public Double getPremioAcumulado() {
		return premioAcumulado;
	}

	public void setPremioAcumulado(Double premioAcumulado) {
		this.premioAcumulado = premioAcumulado;
	}

	public String getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public String getAberturaSistema() {
		return aberturaSistema;
	}

	public void setAberturaSistema(String aberturaSistema) {
		this.aberturaSistema = aberturaSistema;
	}

	public String getFechamentoSistema() {
		return fechamentoSistema;
	}

	public void setFechamentoSistema(String fechamentoSistema) {
		this.fechamentoSistema = fechamentoSistema;
	}

	public String getPrimeiroSorteio() {
		return primeiroSorteio;
	}

	public void setPrimeiroSorteio(String primeiroSorteio) {
		this.primeiroSorteio = primeiroSorteio;
	}

	public Double getTaxaAdministrativa() {
		return taxaAdministrativa;
	}
	
	public void setTaxaAdministrativa(Double taxaAdministrativa) {
		this.taxaAdministrativa = taxaAdministrativa;
	}
	
	public Long getTotalParticipantes() {
		return totalParticipantes;
	}

	public void setTotalParticipantes(Long totalParticipantes) {
		this.totalParticipantes = totalParticipantes;
	}

	public PainelDTO() {}

	public PainelDTO(Integer codigo, Double valorCota, Double premioPrincipal, Double premioSecundario,
			Double premioPeFrio, Double premioAcumulado, String mesReferencia, String aberturaSistema,
			String fechamentoSistema, String primeiroSorteio, Double taxaAdministrativa) {
		super();
		this.codigo = codigo;
		this.valorCota = valorCota;
		this.premioPrincipal = premioPrincipal;
		this.premioSecundario = premioSecundario;
		this.premioPeFrio = premioPeFrio;
		this.premioAcumulado = premioAcumulado;
		this.mesReferencia = mesReferencia;
		this.aberturaSistema = aberturaSistema;
		this.fechamentoSistema = fechamentoSistema;
		this.primeiroSorteio = primeiroSorteio;
		this.setTaxaAdministrativa(taxaAdministrativa);
	}

	
}
