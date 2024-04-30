package br.com.magnasistemas.filme.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.magnasistemas.filme.models.AtorModel;
import br.com.magnasistemas.filme.models.PaisModel;


public class AtorRetornoDto {
	
	private Long id;
	
	private String nome;
	
	private LocalDate nascimento;
	
	private String genero;
	
	private Integer drt;
	
	private PaisModel pais;

	
	
	public AtorRetornoDto() {
	}

	public AtorRetornoDto(Long id, String nome, LocalDate nascimento, String genero, Integer drt, PaisModel pais) {
		this.id = id;
		this.nome = nome;
		this.nascimento = nascimento;
		this.genero = genero;
		this.drt = drt;
		this.pais = pais;
	}
	
	public AtorRetornoDto(AtorModel atorModel) {
		this(	atorModel.getId(),
				atorModel.getNome(),
				atorModel.getNascimento(),
				atorModel.getGenero(),
				atorModel.getDrt(),
				atorModel.getPais());	
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

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Integer getDrt() {
		return drt;
	}

	public void setDrt(Integer drt) {
		this.drt = drt;
	}

	public PaisModel getPais() {
		return pais;
	}

	public void setPais(PaisModel pais) {
		this.pais = pais;
	}
	
	
	

}
