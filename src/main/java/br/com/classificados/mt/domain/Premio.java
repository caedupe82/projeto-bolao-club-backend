package br.com.classificados.mt.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_premio")
public class Premio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name = "vencedor_primeiro", length = 15)
	private String vencedorPrimeiro;
	
	@Column(name = "vencedor_segundo", length = 15)
	private String vencedorSegundo;
	
	@Column(name = "vencedor_pe_frio", length = 15)
	private String vencedorPeFrio;
	
	@Column(name = "vencedor_acumulado", length = 15)
	private String vencedorAcumulado;
	
	@Column(name = "premio_primeiro")
	private Double premioPrimeiro;
	
	@Column(name = "premio_segundo")
	private Double premioSegundo;
	
	@Column(name = "premio_pe_frio")
	private Double premioPeFrio;
	
	@Column(name = "premio_acumulado")
	private Double premioAcumulado;
	
	@Column(name = "data")
	private LocalDate data;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

}
