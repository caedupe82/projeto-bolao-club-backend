package br.com.classificados.mt.dto;

import java.io.Serializable;

public class PremioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String vencedorPrimeiro;
	private String vencedorSegundo;
	private String vencedorPeFrio;
	private String vencedorAcumulado;
	private Double premioPrimeiro;
	private Double premioSegundo;
	private Double premioPeFrio;
	private Double premioAcumulado;
	private String data;
	
	public String getVencedorPrimeiro() {
		return vencedorPrimeiro;
	}
	public void setVencedorPrimeiro(String vencedorPrimeiro) {
		this.vencedorPrimeiro = vencedorPrimeiro;
	}
	public String getVencedorSegundo() {
		return vencedorSegundo;
	}
	public void setVencedorSegundo(String vencedorSegundo) {
		this.vencedorSegundo = vencedorSegundo;
	}
	public String getVencedorPeFrio() {
		return vencedorPeFrio;
	}
	public void setVencedorPeFrio(String vencedorPeFrio) {
		this.vencedorPeFrio = vencedorPeFrio;
	}
	public String getVencedorAcumulado() {
		return vencedorAcumulado;
	}
	public void setVencedorAcumulado(String vencedorAcumulado) {
		this.vencedorAcumulado = vencedorAcumulado;
	}
	public Double getPremioPrimeiro() {
		return premioPrimeiro;
	}
	public void setPremioPrimeiro(Double premioPrimeiro) {
		this.premioPrimeiro = premioPrimeiro;
	}
	public Double getPremioSegundo() {
		return premioSegundo;
	}
	public void setPremioSegundo(Double premioSegundo) {
		this.premioSegundo = premioSegundo;
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public PremioDTO(String vencedorPrimeiro, String vencedorSegundo, String vencedorPeFrio, String vencedorAcumulado,
			Double premioPrimeiro, Double premioSegundo, Double premioPeFrio, Double premioAcumulado, String data) {
		super();
		this.vencedorPrimeiro = vencedorPrimeiro;
		this.vencedorSegundo = vencedorSegundo;
		this.vencedorPeFrio = vencedorPeFrio;
		this.vencedorAcumulado = vencedorAcumulado;
		this.premioPrimeiro = premioPrimeiro;
		this.premioSegundo = premioSegundo;
		this.premioPeFrio = premioPeFrio;
		this.premioAcumulado = premioAcumulado;
		this.data = data;
	}

}
