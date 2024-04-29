package br.com.magnasistemas.filme.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magnasistemas.filme.dtos.FilmeCadastroDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_FILME")
public class FilmeModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String categoria;
	
	private String classificacao;
	
	private String sinopse;
	
	private String poster;
	
	private Integer tempoDuracao;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	@JoinColumn (name = "FK_PRODUTORA", foreignKey = @ForeignKey(name = "FK_PAIS_PRODUTORA"))
	@JsonIgnore
	private ProdutoraModel produtora;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	@JoinColumn (name = "FK_DIRETOR", foreignKey = @ForeignKey(name = "FK_PAIS_DIRETOR"))
	@JsonIgnore
	private DiretorModel diretor;
	
	@OneToMany(mappedBy = "filme", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<TrailerModel> trailer;
	
	@ManyToMany
	@JoinTable(
			name = "TB_ATOR_FILME", 
			joinColumns = @JoinColumn(name = "FK_FILME"),
			inverseJoinColumns = @JoinColumn(name = "FK_ATOR"))
	private List<AtorModel> atores;

	
	
	
	
	public List<TrailerModel> getTrailer() {
		return trailer;
	}


	public void setTrailer(List<TrailerModel> trailer) {
		this.trailer = trailer;
	}


	public List<AtorModel> getAtores() {
		return atores;
	}


	public void setAtores(List<AtorModel> atores) {
		this.atores = atores;
	}


	public FilmeModel() {
	}
	
	
	public FilmeModel(FilmeCadastroDto filme) {
		this.nome = filme.getNome();
		this.categoria = filme.getCategoria();
		this.classificacao = filme.getClassificacao();
		this.sinopse = filme.getSinopse();
		this.poster = filme.getPoster();
		this.tempoDuracao = filme.getTempoDuracao();
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Integer getTempoDuracao() {
		return tempoDuracao;
	}

	public void setTempoDuracao(Integer tempoDuracao) {
		this.tempoDuracao = tempoDuracao;
	}

	public ProdutoraModel getProdutora() {
		return produtora;
	}

	public void setProdutora(ProdutoraModel produtora) {
		this.produtora = produtora;
	}

	public DiretorModel getDiretor() {
		return diretor;
	}

	public void setDiretor(DiretorModel diretor) {
		this.diretor = diretor;
	}
	
	
	public void atualizaFilme(FilmeCadastroDto filme) {
		this.nome = filme.getNome();
		this.categoria = filme.getCategoria();
		this.classificacao = filme.getClassificacao();
		this.sinopse = filme.getSinopse();
		this.poster = filme.getPoster();
		this.tempoDuracao = filme.getTempoDuracao();
	}
	
	
	
	
	

}
