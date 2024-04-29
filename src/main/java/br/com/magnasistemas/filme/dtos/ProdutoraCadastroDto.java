package br.com.magnasistemas.filme.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoraCadastroDto {
	
	@NotBlank(message = "O nome do produtora é obrigatório")
	private String nomeFantasia;
	
	@NotNull(message = "O ano de fundação é obrigatório")
	private Integer anoFundacao;
	
	@NotNull(message = "O id do país não foi localizado")
	private Long paisId;
	
	
	
	
	public ProdutoraCadastroDto() {
	}

	public ProdutoraCadastroDto(String nomeFantasia, Integer anoFundacao, Long paisId) {
		this.nomeFantasia = nomeFantasia;
		this.anoFundacao = anoFundacao;
		this.paisId = paisId;
	}

	
	
	
	
	

	public String getNomeFantasia() {
		return nomeFantasia;
	}


	public void setNomeFantasia(String nome) {
		this.nomeFantasia = nome;
	}


	public Integer getAnoFundacao() {
		return anoFundacao;
	}


	public void setAnoFundacao(Integer ano) {
		this.anoFundacao = ano;
	}


	public Long getPaisId() {
		return paisId;
	}


	public void setPaisId(Long paisId) {
		this.paisId = paisId;
	}
	
}
