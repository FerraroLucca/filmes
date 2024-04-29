package br.com.magnasistemas.filme.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magnasistemas.filme.dtos.PaisCadastroDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_PAIS")
public class PaisModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String capital;
	
	private String continente;
	
	private String lingua;
	
	@OneToMany(mappedBy = "pais", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<DiretorModel> diretor;
	
	@OneToMany(mappedBy = "pais", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<ProdutoraModel> produtora;

	public PaisModel() {
	}
	
	public PaisModel(PaisCadastroDto pais) {
		this.nome = pais.getNome();
		this.capital = pais.getCapital();
		this.continente = pais.getContinente();
		this.lingua = pais.getLingua();
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

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getContinente() {
		return continente;
	}

	public void setContinente(String continente) {
		this.continente = continente;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	
	
	public void atualizaPais(PaisCadastroDto dados) {
		this.nome = dados.getNome();
		this.capital = dados.getCapital();
		this.continente = dados.getContinente();
		this.lingua = dados.getLingua();
	}
	
}
