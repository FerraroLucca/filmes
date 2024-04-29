package br.com.magnasistemas.filme.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magnasistemas.filme.dtos.ProdutoraCadastroDto;
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
@Table(name="TB_PRODUTORA")
public class ProdutoraModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private Integer anoFundacao;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	@JoinColumn (name = "FK_PAIS", foreignKey = @ForeignKey(name = "FK_PAIS_PRODUTORA"))
	@JsonIgnore
	private PaisModel pais;
	
	@OneToMany(mappedBy = "produtora", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<FilmeModel> filme;



	public ProdutoraModel() {
	}

	public ProdutoraModel(ProdutoraCadastroDto produtora) {
		this.nome = produtora.getNomeFantasia();
		this.anoFundacao = produtora.getAnoFundacao();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNomeFantasia() {
		return nome;
	}



	public void setNomeFantasia(String nomeFantasia) {
		this.nome = nomeFantasia;
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
	
	

	public void atualizaProdutora(ProdutoraCadastroDto produtora) {
		this.nome = produtora.getNomeFantasia();
		this.anoFundacao = produtora.getAnoFundacao();
	}
	
	
	
	
	
	
	
	
	
	
	
}
