package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(ProdutoraService.class);

	private final ProdutoraRepository produtoraRepository;
	private final PaisRepository paisRepository;
	
	
	
	public ProdutoraService(ProdutoraRepository produtoraRepository, PaisRepository paisRepository) {
		this.produtoraRepository = produtoraRepository;
		this.paisRepository = paisRepository;
	}
	
	
	
	public List<ProdutoraRetornoDto> listarProdutoras(){
		log.info("Buscando todos os registros de produtoras");
		var produtoras = produtoraRepository.findAll();
		List<ProdutoraRetornoDto> dtos = new ArrayList<>();
		
		for (ProdutoraModel produtora : produtoras) {
			ProdutoraRetornoDto dto = new ProdutoraRetornoDto(produtora);
			dtos.add(dto);
		}
		
		log.info("Encontradas {} produtoras", dtos.size());
		return dtos;
	}
	
	public ProdutoraRetornoDto getProdutoraById(Long id) {
		log.info("Buscando registro do produtoras de ID: {}", id);
		Optional<ProdutoraModel> produtoraOptional = produtoraRepository.findById(id);
		
		if(produtoraOptional.isPresent()) {
			ProdutoraModel produtoraModel = produtoraOptional.get();
			log.info("Produtora de ID: {}, encontrada com sucesso!", id);
			return new ProdutoraRetornoDto(produtoraModel);
		}
		
		log.warn("Não foi possivel encontrar a produtoras com o ID: {}", id);
		throw new ValidacaoException("Não foi possivel encontrar o diretor com o ID indicado");
	}
	
	public ProdutoraRetornoDto cadastraProdutora(ProdutoraCadastroDto dados) {
		log.info("Cadastrando nova produtora");
		Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
		
		if(paisOptional.isPresent()) {
			PaisModel pais = paisOptional.get();
			ProdutoraModel produtora = new ProdutoraModel(dados);
			produtora.setPais(pais);
			produtoraRepository.save(produtora);
			
			log.info("Nova produtora cadastrada com sucesso!");
			return new ProdutoraRetornoDto(produtora);
		}
		
		log.warn("Não foi possivel encontrar o país com o ID: {}", dados.getPaisId());
		throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
	}
	
	public ProdutoraRetornoDto putProdutora(ProdutoraCadastroDto dados, Long id) {
		log.info("Iniciando a atualização de dados da produtoras de ID: {}", id);
		Optional<ProdutoraModel> produtoraOptional = produtoraRepository.findById(id);
		
		if(produtoraOptional.isPresent()) {
			
			ProdutoraModel produtora = produtoraOptional.get();
			
			Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
			
			if(paisOptional.isPresent()) {
				PaisModel pais = paisOptional.get();
				produtora.atualizaProdutora(dados);
				produtora.setPais(pais);
				
				produtoraRepository.save(produtora);
				
				log.info("Dados da produtora de ID: {}, atualizados com sucesso!", id);
				return new ProdutoraRetornoDto(produtora);
			}
			
			log.warn("Pais com ID: {}, não foi encontrado", id);
			throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
		}
		
		log.warn("Produtoras com ID: {}, não foi encontrada", id);
		throw new ValidacaoException("Não foi possível encontrar a produtora com o ID indicado");
	}
	
	public void deletaProdutora(Long id) {
		log.info("Iniciando a exclusão da produtora de ID: {}", id);
		Optional<ProdutoraModel> produtoraOptional = produtoraRepository.findById(id);
		
		if(produtoraOptional.isPresent()) {
			produtoraRepository.deleteById(id);
			log.info("Produtora de ID {}, foi deletada com sucesso!", id);
		}else {
			log.warn("Produtoras de ID {} não foi encontrada", id);
			throw new ValidacaoException("Não foi possível encontrar a produtora com o ID indicado");			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
