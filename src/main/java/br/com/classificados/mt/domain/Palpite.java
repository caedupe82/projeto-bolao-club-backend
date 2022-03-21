package br.com.classificados.mt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_palpite")
public class Palpite implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Integer codigo;
	
	@NotNull
	@Column(name = "numeros", length = 60)
	private String numeros;
	
	@NotNull
	@Column(name = "usuario")
	private Integer usuario;
	
	@NotNull
	@Column(name = "data")
	private LocalDate data;
	
	@Column(name = "acertos", length = 60)
	private String acertos;
	
	@Column(name = "numeros_sorteados", length = 200)
	private String numerosSorteados;
	
	@Column(name = "quantidade_acerto")
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

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
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

	public Palpite() {}

	public Palpite(Integer codigo, String numeros, Integer usuario, LocalDate data) {
		super();
		this.codigo = codigo;
		this.numeros = numeros;
		this.usuario = usuario;
		this.data = data;
	}
	
}
