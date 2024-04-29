package br.com.magnasistemas.filme.dtos;

import br.com.magnasistemas.filme.models.PaisModel;

public class PaisRetornoDto {
	
	private Long id;
	
	private String nome;
	
	private String capital;
	
	private String continente;
	
	private String lingua;

	
	
	
	public PaisRetornoDto() {
	}
	
	public PaisRetornoDto(Long id, String nome, String capital, String continente, String lingua) {
		this.id = id;
		this.nome = nome;
		this.capital = capital;
		this.continente = continente;
		this.lingua = lingua;
	}

	public PaisRetornoDto(PaisModel paisModel) {
		this(paisModel.getId(), 
				paisModel.getNome(), 
				paisModel.getCapital(), 
				paisModel.getContinente(), 
				paisModel.getLingua());
	}

	
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

