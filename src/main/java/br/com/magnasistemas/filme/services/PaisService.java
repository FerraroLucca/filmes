package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import br.com.magnasistemas.filme.dtos.PaisCadastroDto;
import br.com.magnasistemas.filme.dtos.PaisRetornoDto;
import br.com.magnasistemas.filme.exceptions.ValidacaoException;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.repositories.PaisRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaisService {
	
	private static final Logger log = LoggerFactory.getLogger(PaisService.class);
	
	private final PaisRepository paisRepository;
	
	
	
	public PaisService(PaisRepository paisRepository) {
		this.paisRepository = paisRepository;
	}
	
	
	
	public List<PaisRetornoDto> listarPaises(){
		log.info("Buscando todos os registros de paises");
		var paises = paisRepository.findAll();
		List<PaisRetornoDto> dtos = new ArrayList<>();
		
		for(PaisModel pais : paises) {
			PaisRetornoDto dto = new PaisRetornoDto(pais);
			dtos.add(dto);
		}
		
		log.info("Encontrados {} paises", dtos.size());
		return dtos;
	}
	
	public PaisRetornoDto getPaisById(Long id) {
		log.info("Buscando registro do país de ID: {}", id);
		Optional<PaisModel> paisOptional = paisRepository.findById(id);
		
		if(paisOptional.isPresent()) {
			PaisModel paisModel = paisOptional.get();
			log.info("País com ID {}, encontrado com sucesso!", id);
			return new PaisRetornoDto(paisModel);
		}
		log.warn("Não foi possivel encontrar o país com o ID: {}", id);
		throw new ValidacaoException("não foi possivel encontrar o país com o ID indicado");
	}
	
	@Transactional
	public PaisRetornoDto cadastraPais(PaisCadastroDto dados) {
		log.info("Cadastrando novo país");
		PaisModel pais = new PaisModel(dados);
		
		paisRepository.save(pais);
		
		log.info("Novo país cadastrado com sucesso");
		return new PaisRetornoDto(pais);
	}
	
	public PaisRetornoDto putPais(PaisCadastroDto dados, Long id) {
		log.info("Iniciando a atualização de dados do país de ID: {}", id);
		Optional<PaisModel> paisOptional = paisRepository.findById(id);

		if(paisOptional.isPresent()) {
			PaisModel pais = paisOptional.get();
			pais.atualizaPais(dados);
			paisRepository.save(pais);
			
			log.info("Dados do país de ID: {}, atualizados com sucesso!", id);
			return new PaisRetornoDto(pais);
		}
		
		log.warn("Pais com ID: {}, não foi encontrado", id);
		throw new ValidacaoException("Pais do id indicado não foi encontrado");

		
		
	}

	public void deletaPais(Long id) {
		log.info("Iniciando a exclusão do pais de ID: {}", id);
		Optional<PaisModel> paisOptional = paisRepository.findById(id);
		
		if(paisOptional.isPresent()) {
			paisRepository.deleteById(id);
			log.info("Pais de ID {}, foi deletado com sucesso!", id);
		}else {
			log.warn("Pais de ID {} não foi encontrado", id);
			throw new ValidacaoException("Pais do id indicado não foi encontrado");
		}
		
	}
}
