package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.filme.dtos.DiretorCadastroDto;
import br.com.magnasistemas.filme.dtos.DiretorRetornoDto;
import br.com.magnasistemas.filme.exceptions.ValidacaoException;
import br.com.magnasistemas.filme.models.DiretorModel;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.repositories.DiretorRepository;
import br.com.magnasistemas.filme.repositories.PaisRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DiretorService {
	
	private static final Logger log = LoggerFactory.getLogger(DiretorService.class);

	private final DiretorRepository diretorRepository;
	private final PaisRepository paisRepository;
	
	
	
	public DiretorService(DiretorRepository diretorRepository, PaisRepository paisRepository) {
		this.diretorRepository = diretorRepository;
		this.paisRepository = paisRepository;
	}
	

	
	public List<DiretorRetornoDto> listarDiretores(){
		log.info("Buscando todos os registros de diretores");
		var diretores = diretorRepository.findAll();
		List<DiretorRetornoDto> dtos = new ArrayList<>();
		
		for(DiretorModel diretor : diretores) {
			DiretorRetornoDto dto = new DiretorRetornoDto(diretor);
			dtos.add(dto);
		}
		log.info("Encontrados {} diretores", dtos.size());
		return dtos;
	}
	
	public DiretorRetornoDto getDiretorById(Long id) {
		log.info("Buscando registro do diretor de ID: {}", id);
		
		Optional<DiretorModel> diretorOptional = diretorRepository.findById(id);
		
		if(diretorOptional.isPresent()) {
			DiretorModel diretorModel = diretorOptional.get();
			log.info("Diretor de ID: {}, encontrado com sucesso!", id);
			return new DiretorRetornoDto(diretorModel);
		}
		log.warn("Não foi possivel encontrar o diretor com o ID: {}", id);
		throw new ValidacaoException("Não foi possivel encontrar o diretor com o ID indicado");
	}
	
	public DiretorRetornoDto cadastraDiretor(DiretorCadastroDto dados) {
		log.info("Cadastrando novo diretor");
		Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
		
		if(paisOptional.isPresent()) {
			PaisModel pais = paisOptional.get();
			DiretorModel diretor = new DiretorModel(dados);
			diretor.setPais(pais);
			diretorRepository.save(diretor);
			
			log.info("Novo diretor cadastrado com sucesso!");
			return new DiretorRetornoDto(diretor);
		}
		log.warn("Não foi possivel encontrar o país com o ID: {}", dados.getPaisId());
		throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
	}
	
	public DiretorRetornoDto putDiretor(DiretorCadastroDto dados, Long id) {
		log.info("Iniciando a atualização de dados do diretor de ID: {}", id);
		Optional<DiretorModel> diretorOptional = diretorRepository.findById(id);
		
		if(diretorOptional.isPresent()) {
			DiretorModel diretor = diretorOptional.get();
			
			Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
			
			if(paisOptional.isPresent()) {
				PaisModel pais = paisOptional.get();
				diretor.atualizaDiretor(dados);
				diretor.setPais(pais);
				
				diretorRepository.save(diretor);
				
				log.info("Dados do diretor de ID: {}, atualizados com sucesso!", id);
				return new DiretorRetornoDto(diretor);
			}
			log.warn("Não foi possivel encontrar o país com o ID: {}", id);
			throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
		}
		log.warn("Não foi possivel encontrar o diretor com o ID: {}", id);
		throw new ValidacaoException("Não foi possível encontrar o diretor com o ID indicado");
	}
	
	public void deletaDiretor(Long id) {
		log.info("Iniciando a exclusão do diretor de ID: {}", id);
		Optional<DiretorModel> diretorOptional = diretorRepository.findById(id);
		
		if(diretorOptional.isPresent()) {
			diretorRepository.deleteById(id);
			log.info("Diretor de ID {}, foi deletado com sucesso!", id);
		}else {
			log.warn("Diretor de ID {} não foi encontrado", id);
			throw new ValidacaoException("Não foi possível encontrar o diretor com o ID indicado");			
		}
		
		
	}
}









