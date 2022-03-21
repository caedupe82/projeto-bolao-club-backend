package br.com.classificados.mt.dto;

public class RankingDTO {

	private String nome;
	private String numeros;
	private Long quantidadeAcerto;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNumeros() {
		return numeros;
	}
	public void setNumeros(String numeros) {
		this.numeros = numeros;
	}
	public Long getQuantidadeAcerto() {
		return quantidadeAcerto;
	}
	public void setQuantidadeAcerto(Long quantidadeAcerto) {
		this.quantidadeAcerto = quantidadeAcerto;
	}
	public RankingDTO(String nome, String numeros, Long quantidadeAcerto) {
		super();
		this.nome = nome;
		this.numeros = numeros;
		this.quantidadeAcerto = quantidadeAcerto;
	}

}
