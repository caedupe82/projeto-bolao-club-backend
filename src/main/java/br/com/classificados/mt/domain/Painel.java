package br.com.classificados.mt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_painel")
public class Painel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Integer codigo;
	
	@NotNull
	@Column(name = "valor_cota")
	private Double valorCota;
	
	@NotNull
	@Column(name = "premio_principal")
	private Double premioPrincipal;

	@NotNull
	@Column(name = "premio_secundario")
	private Double premioSecundario;
	
	@NotNull
	@Column(name = "premio_pe_frio")
	private Double premioPeFrio;
	
	@NotNull
	@Column(name = "premio_acumulado")
	private Double premioAcumulado;
	
	@NotNull
	@Column(name = "taxa_administrativa")
	private Double taxaAdministrativa;
	
	@NotNull
	@Column(name = "mes_referencia")
	private LocalDate mesReferencia;
	
	@NotNull
	@Column(name = "abertura_sistema")
	private LocalDate aberturaSistema;
	
	@NotNull
	@Column(name = "fechamento_sistema")
	private LocalDate fechamentoSistema;
	
	@NotNull
	@Column(name = "primeiro_sorteio")
	private LocalDate primeiroSorteio;

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

	public LocalDate getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(LocalDate mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public LocalDate getAberturaSistema() {
		return aberturaSistema;
	}

	public void setAberturaSistema(LocalDate aberturaSistema) {
		this.aberturaSistema = aberturaSistema;
	}

	public LocalDate getFechamentoSistema() {
		return fechamentoSistema;
	}

	public void setFechamentoSistema(LocalDate fechamentoSistema) {
		this.fechamentoSistema = fechamentoSistema;
	}

	public LocalDate getPrimeiroSorteio() {
		return primeiroSorteio;
	}

	public void setPrimeiroSorteio(LocalDate primeiroSorteio) {
		this.primeiroSorteio = primeiroSorteio;
	}
	
	public Double getTaxaAdministrativa() {
		return taxaAdministrativa;
	}
	
	public void setTaxaAdministrativa(Double taxaAdministrativa) {
		this.taxaAdministrativa = taxaAdministrativa;
	}

	public Painel() {}

	public Painel(Integer codigo, Double valorCota, Double premioPrincipal, Double premioSecundario,
			Double premioPeFrio, Double premioAcumulado, LocalDate mesReferencia, LocalDate aberturaSistema,
			LocalDate fechamentoSistema, LocalDate primeiroSorteio, Double taxaAdministrativa) {
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
		this.taxaAdministrativa = taxaAdministrativa;
	}
	
}
