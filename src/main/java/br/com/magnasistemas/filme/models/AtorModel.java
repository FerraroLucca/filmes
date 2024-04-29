package br.com.magnasistemas.filme.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magnasistemas.filme.dtos.AtorCadastroDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_ATOR")
public class AtorModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private LocalDate nascimento;
	
	private String genero;
	
	private Integer drt;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	@JoinColumn (name = "FK_PAIS", foreignKey = @ForeignKey(name = "FK_PAIS_ATOR"))
	@JsonIgnore
	private PaisModel pais;
	
	@ManyToMany(mappedBy = "atores", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FilmeModel> filmes = new ArrayList<>();

	
	
	public AtorModel() {
	}
	
	
	public AtorModel(AtorCadastroDto ator) {
		this.nome = ator.getNome();
		this.nascimento = ator.getNascimento();
		this.genero = ator.getGenero();
		this.drt = ator.getDrt();
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


	public void setPais(PaisModel  paisId) {
		this.pais = paisId;
	}
	
	
	public void atualizaAtor(AtorCadastroDto ator) {
		this.nome = ator.getNome();
		this.nascimento = ator.getNascimento();
		this.genero = ator.getGenero();
		this.drt = ator.getDrt();
	}
}
