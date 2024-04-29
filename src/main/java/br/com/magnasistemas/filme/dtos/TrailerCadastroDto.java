package br.com.magnasistemas.filme.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrailerCadastroDto {

	@NotBlank(message = "O link do trailer do filme é obrigatório")
	private String link;
	
	@NotNull(message = "O id do filme é obrigatório")
	private Long filmeId;

	
	
	public TrailerCadastroDto() {
	}

	public TrailerCadastroDto(String link,Long filmeId) {
		this.link = link;
		this.filmeId = filmeId;
	}

	
	
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Long getFilmeId() {
		return filmeId;
	}

	public void setFilmeId(Long filmeId) {
		this.filmeId = filmeId;
	}
	
}
