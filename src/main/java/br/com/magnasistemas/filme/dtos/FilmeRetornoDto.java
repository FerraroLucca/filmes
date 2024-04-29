package br.com.magnasistemas.filme.dtos;

import java.util.List;

import br.com.magnasistemas.filme.models.AtorModel;
import br.com.magnasistemas.filme.models.DiretorModel;
import br.com.magnasistemas.filme.models.FilmeModel;
import br.com.magnasistemas.filme.models.ProdutoraModel;

public class FilmeRetornoDto {

	private Long id;
	
	private String nome;
	
	private String categoria;
	
	private String classificacao;
	
	private String sinopse;
	
	private String poster;
	
	private Integer tempoDuracao;
	
	private ProdutoraModel produtora;
	
	private DiretorModel diretor;
	
	private List<AtorModel> atores;
	
	

	

	public FilmeRetornoDto() {
	}

	public FilmeRetornoDto(Long id, String nome, String categoria, String classificacao, String sinopse, String poster,
			Integer tempoDuracao, ProdutoraModel produtora, DiretorModel diretor, List<AtorModel> atores) {
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
		this.classificacao = classificacao;
		this.sinopse = sinopse;
		this.poster = poster;
		this.tempoDuracao = tempoDuracao;
		this.produtora = produtora;
		this.diretor = diretor;
		this.atores = atores; 
	}
	
	public FilmeRetornoDto(FilmeModel filmeModel) {
		this(	filmeModel.getId(),
				filmeModel.getNome(),
				filmeModel.getCategoria(),
				filmeModel.getClassificacao(),
				filmeModel.getSinopse(),
				filmeModel.getPoster(),
				filmeModel.getTempoDuracao(),
				filmeModel.getProdutora(),
				filmeModel.getDiretor(),
				filmeModel.getAtores());
	}

	
	
	
	
	public List<AtorModel> getAtores() {
		return atores;
	}

	public void setAtores(List<AtorModel> atores) {
		this.atores = atores;
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
	
}
