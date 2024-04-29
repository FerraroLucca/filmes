package br.com.magnasistemas.filme.dtos;

import jakarta.validation.constraints.NotBlank;

public class PaisCadastroDto {
	
	@NotBlank(message = "O nome do país é obrigatório")
	private String nome;
	
	@NotBlank(message = "O nome da capital é obrigatório")
	private String capital;
	
	@NotBlank(message = "O nome do continente é obrigatório")
	private String continente;
	
	@NotBlank(message = "O nome da lingua é obrigatório")
	private String lingua;

	
	
	
	
	public PaisCadastroDto() {
	}
	
	public PaisCadastroDto(String nome, String capital, String continente, String lingua) {
		this.nome = nome;
		this.capital = capital;
		this.continente = continente;
		this.lingua = lingua;
	}

	
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getContinente() {
		return continente;
	}

	public void setContinente(String continente) {
		this.continente = continente;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	
	
	
	
	
	
	
}
