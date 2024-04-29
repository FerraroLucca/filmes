package br.com.magnasistemas.filme.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AtorCadastroDto {
	
	@NotBlank(message = "O nome do(a) ator/atriz é obrigatório")
	private String nome;
	
	@NotNull(message = "A data de nascimento do(a) ator/atriz é obrigatória")
	private LocalDate nascimento;
	
	@NotBlank(message = "O genero do(a) ator/atriz é obrigatório")
	private String genero;
	
	@NotNull(message = "O numero da DRT do(a) ator/atriz é obrigatória")
	private Integer drt;
	
	@NotNull(message = "O id do país não foi localizado")
	private Long paisId;

	
	public AtorCadastroDto() {
	}

	
	
	public AtorCadastroDto(@NotBlank(message = "O nome do(a) ator/atriz é obrigatório") String nome,
			@NotNull(message = "A data de nascimento do(a) ator/atriz é obrigatória") LocalDate nascimento,
			@NotBlank(message = "O genero do(a) ator/atriz é obrigatório") String genero,
			@NotNull(message = "O numero da DRT do(a) ator/atriz é obrigatória") Integer drt,
			@NotNull(message = "O id do país não foi localizado") Long paisId) {
		this.nome = nome;
		this.nascimento = nascimento;
		this.genero = genero;
		this.drt = drt;
		this.paisId = paisId;
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


	public Long getPaisId() {
		return paisId;
	}


	public void setPaisId(Long paisId) {
		this.paisId = paisId;
	}
	
	
	
	
	
	
	

}
