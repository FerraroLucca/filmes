package br.com.magnasistemas.filme.dtos;

import java.time.LocalDate;

import br.com.magnasistemas.filme.models.DiretorModel;
import br.com.magnasistemas.filme.models.PaisModel;

public class DiretorRetornoDto {
	
	private Long id;
	
	private String nome;
	
	private LocalDate data;
	
	private String genero;
	
	private PaisModel pais;

	
	
	
	
	public DiretorRetornoDto() {
	}

	public DiretorRetornoDto(Long id, String nome, LocalDate data, String genero, PaisModel pais) {
		this.id = id;
		this.nome = nome;
		this.data = data;
		this.genero = genero;
		this.pais = pais;
	}
	
	public DiretorRetornoDto(DiretorModel diretorModel) {
		this(	diretorModel.getId(),
				diretorModel.getNome(),
				diretorModel.getData(),
				diretorModel.getGenero(),
				diretorModel.getPais());	
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


	public LocalDate getData() {
		return data;
	}


	public void setData(LocalDate data) {
		this.data = data;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}


	public PaisModel getPais() {
		return pais;
	}


	public void setPais(PaisModel pais) {
		this.pais = pais;
	}
}