package br.com.magnasistemas.filme.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DiretorCadastroDto {

	@NotBlank(message = "O nome do diretor é obrigatório")
	private String nome;
	
	@NotNull(message = "A data de nascimento do diretor é obrigatória")
	private LocalDate data;
	
	@NotBlank(message = "O genero do diretor é obrigatório")
	private String genero;
	
	@NotNull(message = "O id do país é obrigatório")
	private Long paisId;
	
	
	
	
	public DiretorCadastroDto() {
	}


	public DiretorCadastroDto(String nome, LocalDate data,String genero, Long paisId) {
		this.nome = nome;
		this.data = data;
		this.genero = genero;
		this.paisId = paisId;
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


	public Long getPaisId() {
		return paisId;
	}


	public void setPaisId(Long paisId) {
		this.paisId = paisId;
	}
	
	
	
}
