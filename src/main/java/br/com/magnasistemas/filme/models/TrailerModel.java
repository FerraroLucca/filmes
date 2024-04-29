package br.com.magnasistemas.filme.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magnasistemas.filme.dtos.TrailerCadastroDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_TRAILER")
public class TrailerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String link;
	
	@OneToOne (cascade = CascadeType.PERSIST)
	@JoinColumn (name = "FK_FILME", foreignKey = @ForeignKey(name = "FK_FILME_TRAILER"))
	@JsonIgnore
	private FilmeModel filme;

	
	
	public TrailerModel() {
	}


	public TrailerModel(TrailerCadastroDto trailer) {
		this.link = trailer.getLink();
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
	
	
	
	public void atualizaTrailer(TrailerCadastroDto trailer) {
		this.link = trailer.getLink();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
