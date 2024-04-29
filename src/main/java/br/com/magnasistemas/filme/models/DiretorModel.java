package br.com.magnasistemas.filme.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magnasistemas.filme.dtos.DiretorCadastroDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_DIRETOR")
public class DiretorModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private LocalDate nascimento;
	
	private String genero;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	@JoinColumn (name = "FK_PAIS", foreignKey = @ForeignKey(name = "FK_PAIS_DIRETOR"))
	@JsonIgnore
	private PaisModel pais;
	
	@OneToMany(mappedBy = "diretor", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<FilmeModel> filme;
	

	
	public DiretorModel() {
	}

	public DiretorModel(DiretorCadastroDto diretor) {
		this.nome = diretor.getNome();
		this.nascimento = diretor.getData();
		this.genero = diretor.getGenero();
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
		return nascimento;
	}



	public void setData(LocalDate data) {
		this.nascimento = data;
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
	
	
	public void atualizaDiretor(DiretorCadastroDto diretor) {
		this.nome = diretor.getNome();
		this.nascimento = diretor.getData();
		this.genero = diretor.getGenero();
	}
	
	
	
}
