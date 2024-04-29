package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.filme.dtos.AtorCadastroDto;
import br.com.magnasistemas.filme.dtos.AtorRetornoDto;
import br.com.magnasistemas.filme.exceptions.ValidacaoException;
import br.com.magnasistemas.filme.models.AtorModel;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.repositories.AtorRepository;
import br.com.magnasistemas.filme.repositories.PaisRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AtorService {
	
	private static final Logger log = LoggerFactory.getLogger(AtorService.class);

	private final AtorRepository atorRepository;
	private final PaisRepository paisRepository;
	
	
	
	public AtorService(AtorRepository atorRepository, PaisRepository paisRepository) {
		this.atorRepository = atorRepository;
		this.paisRepository = paisRepository;
	}
	
	
	
	public List<AtorRetornoDto> listarAtores(){
		log.info("Buscando todos os registros de atores");
		var atores = atorRepository.findAll();
		List<AtorRetornoDto> dtos = new ArrayList<>();
		
		for(AtorModel ator : atores) {
			AtorRetornoDto dto = new AtorRetornoDto(ator);
			dtos.add(dto);
		}
		
		log.info("Encontrados {} atores", dtos.size());
		return dtos;
	}
	
	public AtorRetornoDto getAtorById(Long id) {
		log.info("Buscando registro do ator de ID: {}", id);
		Optional<AtorModel> atorOptional = atorRepository.findById(id);
		
		if(atorOptional.isPresent()) {
			AtorModel atorModel = atorOptional.get();
			log.info("Ator de ID: {}, encontrado com sucesso!", id);
			return new AtorRetornoDto(atorModel);
		}
		
		log.warn("Não foi possivel encontrar o ator com o ID: {}", id);
		throw new ValidacaoException("Não foi possivel encontrar do(a) ator/atriz com o ID indicado");
	}
	
	public AtorRetornoDto cadastraAtor(AtorCadastroDto dados) {
		log.info("Cadastrando novo ator");
		Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
		
		if(paisOptional.isPresent()) {
			PaisModel pais = paisOptional.get();
			AtorModel ator = new AtorModel(dados);
			ator.setPais(pais);
			atorRepository.save(ator);
			
			log.info("Novo(a) ator/atriz cadastrado(a) com sucesso!");			
			return new AtorRetornoDto(ator);
		}
		
		log.warn("Não foi possivel encontrar o país com o ID: {}", dados.getPaisId());
		throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
	}
	
	public AtorRetornoDto putAtor(AtorCadastroDto dados, Long id) {
		log.info("Iniciando a atualização de dados do ator de ID: {}", id);
		Optional<AtorModel> atorOptional = atorRepository.findById(id);
		
		if(atorOptional.isPresent()) {
			AtorModel ator = atorOptional.get();
			Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
			
			if(paisOptional.isPresent()) {
				PaisModel pais = paisOptional.get();
				ator.atualizaAtor(dados);
				ator.setPais(pais);
				
				atorRepository.save(ator);
				
				log.info("Dados do ator/atriz de ID: {}, atualizados com sucesso!", id);
				return new AtorRetornoDto(ator);
			}
			log.warn("Não foi possivel encontrar o país com o ID: {}", dados.getPaisId());
			throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
		}
		log.warn("Não foi possivel encontrar o ator/atriz com o ID: {}", id);
		throw new ValidacaoException("Não foi possivel encontrar do(a) ator/atriz com o ID indicado");
	}
	
	public void deletaAtor(Long id) {
		log.info("Iniciando a exclusão do ator/atriz de ID: {}", id);
		Optional<AtorModel> atorOptional = atorRepository.findById(id);
		
		if(atorOptional.isPresent()) {
			atorRepository.deleteById(id);
			log.info("Ator/Atriz de ID {}, foi deletado com sucesso", id);
		}else {
			log.warn("Ator/Atriz de ID {} não foi encontrado", id);
			throw new ValidacaoException("Não foi possível encontrar o ator com o ID indicado");			
		}
		
		
	}
	
}
