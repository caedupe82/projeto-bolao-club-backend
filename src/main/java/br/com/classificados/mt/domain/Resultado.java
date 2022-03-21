package br.com.classificados.mt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_resultado")
public class Resultado implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Integer codigo;
	
	@NotNull
	@Column(name = "primeiro_numero", length = 2)
	private String primeiroNumero;
	
	@NotNull
	@Column(name = "segundo_numero", length = 2)
	private String segundoNumero;
	
	@NotNull
	@Column(name = "terceiro_numero", length = 2)
	private String terceiroNumero;
	
	@NotNull
	@Column(name = "quarto_numero", length = 2)
	private String quartoNumero;
	
	@NotNull
	@Column(name = "quinto_numero", length = 2)
	private String quintoNumero;
	
	@NotNull
	@Column(name = "numero_concurso", length = 6)
	private String numeroConcurso;
	
	@Column(name = "data")
	private LocalDate data;

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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Resultado() {}

	public Resultado(String primeiroNumero, String segundoNumero, String terceiroNumero, String quartoNumero,
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
