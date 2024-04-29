package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.magnasistemas.filme.dtos.ProdutoraCadastroDto;
import br.com.magnasistemas.filme.dtos.ProdutoraRetornoDto;
import br.com.magnasistemas.filme.exceptions.ValidacaoException;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.models.ProdutoraModel;
import br.com.magnasistemas.filme.repositories.PaisRepository;
import br.com.magnasistemas.filme.repositories.ProdutoraRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProdutoraService {

	private final ProdutoraRepository produtoraRepository;
	private final PaisRepository paisRepository;
	
	public ProdutoraService(ProdutoraRepository produtoraRepository, PaisRepository paisRepository) {
		this.produtoraRepository = produtoraRepository;
		this.paisRepository = paisRepository;
	}
	
	
	
	public List<ProdutoraRetornoDto> listarProdutoras(){
		var produtoras = produtoraRepository.findAll();
		List<ProdutoraRetornoDto> dtos = new ArrayList<>();
		
		for (ProdutoraModel produtora : produtoras) {
			ProdutoraRetornoDto dto = new ProdutoraRetornoDto(produtora);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	public ProdutoraRetornoDto getProdutoraById(Long id) {
		
		Optional<ProdutoraModel> produtoraOptional = produtoraRepository.findById(id);
		
		if(produtoraOptional.isPresent()) {
			ProdutoraModel produtoraModel = produtoraOptional.get();
			return new ProdutoraRetornoDto(produtoraModel);
		}
		
		throw new ValidacaoException("Não foi possivel encontrar o diretor com o ID indicado");
	}
	
	public ProdutoraRetornoDto cadastraProdutora(ProdutoraCadastroDto dados) {
	
		Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
		
		if(paisOptional.isPresent()) {
			PaisModel pais = paisOptional.get();
			ProdutoraModel produtora = new ProdutoraModel(dados);
			produtora.setPais(pais);
			produtoraRepository.save(produtora);
			
			return new ProdutoraRetornoDto(produtora);
		}
		
		throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
	}
	
	public ProdutoraRetornoDto putProdutora(ProdutoraCadastroDto dados, Long id) {
		
		Optional<ProdutoraModel> produtoraOptional = produtoraRepository.findById(id);
		
		if(produtoraOptional.isPresent()) {
			
			ProdutoraModel produtora = produtoraOptional.get();
			
			Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
			
			if(paisOptional.isPresent()) {
				PaisModel pais = paisOptional.get();
				produtora.atualizaProdutora(dados);
				produtora.setPais(pais);
				
				produtoraRepository.save(produtora);
				
				return new ProdutoraRetornoDto(produtora);
			}
			throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
		}
		throw new ValidacaoException("Não foi possível encontrar a produtora com o ID indicado");
	}
	
	public void deletaProdutora(Long id) {
		
		Optional<ProdutoraModel> produtoraOptional = produtoraRepository.findById(id);
		
		if(produtoraOptional.isPresent()) {
			produtoraRepository.deleteById(id);
		}else {
			throw new ValidacaoException("Não foi possível encontrar a produtora com o ID indicado");			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
