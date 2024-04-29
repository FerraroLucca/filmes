package br.com.magnasistemas.filme.dtos;

import br.com.magnasistemas.filme.models.FilmeModel;
import br.com.magnasistemas.filme.models.TrailerModel;

public class TrailerRetornoDto {
	
	private Long id;

	private String link;
	
	private FilmeModel filme;

	
	
	public TrailerRetornoDto() {
	}

	public TrailerRetornoDto(Long id,String link, FilmeModel filme) {
		this.id = id;
		this.link = link;
		this.filme = filme;
	}
	
	public TrailerRetornoDto(TrailerModel trailerModel) {
		this(	trailerModel.getId(),
				trailerModel.getLink(),
				trailerModel.getFilme());
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public FilmeModel getFilme() {
		return filme;
	}

	public void setFilme(FilmeModel filme) {
		this.filme = filme;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
