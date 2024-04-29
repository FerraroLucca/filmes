package br.com.magnasistemas.filme.dtos;

import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.models.ProdutoraModel;

public class ProdutoraRetornoDto {

	private Long id;
	
	private String nomeFantasia;
	
	private Integer anoFundacao;
	
	private PaisModel pais;

	
	
	
	
	
	
	
	
	
	
	public ProdutoraRetornoDto() {
	}

	public ProdutoraRetornoDto(Long id, String nomeFantasia, Integer anoFundacao, PaisModel pais) {
		this.id = id;
		this.nomeFantasia = nomeFantasia;
		this.anoFundacao = anoFundacao;
		this.pais = pais;
	}
	
	public ProdutoraRetornoDto(ProdutoraModel produtoraModel) {
		this(	produtoraModel.getId(),
				produtoraModel.getNomeFantasia(),
				produtoraModel.getAnoFundacao(),
				produtoraModel.getPais());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public Integer getAnoFundacao() {
		return anoFundacao;
	}

	public void setAnoFundacao(Integer anoFundacao) {
		this.anoFundacao = anoFundacao;
	}

	public PaisModel getPais() {
		return pais;
	}

	public void setPais(PaisModel pais) {
		this.pais = pais;
	}
}