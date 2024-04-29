package br.com.magnasistemas.filme.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FilmeCadastroDto {

	@NotBlank(message = "O nome do filme é obrigatório")
	private String nome;
	
	@NotBlank(message = "O nome da categoria é obrigatório")
	private String categoria;
	
	@NotBlank(message = "A classificação do filme é obrigatório")
	private String classificacao;
	
	@NotBlank(message = "A sinopse é obrigatório")
	private String sinopse;
	
	@NotBlank(message = "O poster é obrigatório")
	private String poster;
	
	@NotNull(message = "O tempo de duração do filme é obrigatório")
	private Integer tempoDuracao;
	
	@NotNull(message = "O id da proddutora não foi localizado")
	private Long produtoraId;

	@NotNull(message = "O id do diretor não foi localizado")
	private Long diretorId;

	
	
	public FilmeCadastroDto() {
	}

	public FilmeCadastroDto(String nome, String categoria, String classificacao, String sinopse, String poster, Integer tempoDuracao,Long produtoraId,Long diretorId) {
		this.nome = nome;
		this.categoria = categoria;
		this.classificacao = classificacao;
		this.sinopse = sinopse;
		this.poster = poster;
		this.tempoDuracao = tempoDuracao;
		this.produtoraId = produtoraId;
		this.diretorId = diretorId;
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

	public Long getProdutoraId() {
		return produtoraId;
	}

	public void setProdutoraId(Long produtoraId) {
		this.produtoraId = produtoraId;
	}

	public Long getDiretorId() {
		return diretorId;
	}

	public void setDiretorId(Long diretorId) {
		this.diretorId = diretorId;
	}
	
	
}
