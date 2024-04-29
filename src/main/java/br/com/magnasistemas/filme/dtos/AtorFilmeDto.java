package br.com.magnasistemas.filme.dtos;

import jakarta.validation.constraints.NotNull;

public class AtorFilmeDto {
	
	@NotNull(message = "O id do ator é obrigatório")
	private Long idAtor;
	
	@NotNull(message = "O id do filme é obrigatório")
	private Long idFilme;

	
	
	
	
	public Long getIdAtor() {
		return idAtor;
	}

	public void setIdAtor(Long idAtor) {
		this.idAtor = idAtor;
	}

	public Long getIdFilme() {
		return idFilme;
	}

	public void setIdFilme(Long idFilme) {
		this.idFilme = idFilme;
	}
	
	

}
