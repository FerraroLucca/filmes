package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.filme.dtos.TrailerCadastroDto;
import br.com.magnasistemas.filme.dtos.TrailerRetornoDto;
import br.com.magnasistemas.filme.exceptions.ValidacaoException;
import br.com.magnasistemas.filme.models.FilmeModel;
import br.com.magnasistemas.filme.models.TrailerModel;
import br.com.magnasistemas.filme.repositories.FilmeRepository;
import br.com.magnasistemas.filme.repositories.TrailerRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TrailerService {
	
	private static final Logger log = LoggerFactory.getLogger(TrailerService.class);

	private final TrailerRepository trailerRepository;
	private final FilmeRepository filmeRepository;

	
	
	public TrailerService(TrailerRepository trailerRepository, FilmeRepository filmeRepository) {
		this.trailerRepository = trailerRepository;
		this.filmeRepository = filmeRepository;
	}
	
	

	public List<TrailerRetornoDto> listarTrailers(){
		log.info("Buscando todos os registros de trailers");
		var traileres = trailerRepository.findAll();
		List<TrailerRetornoDto> dtos = new ArrayList<>();
		
		for(TrailerModel trailer : traileres) {
			TrailerRetornoDto dto = new TrailerRetornoDto(trailer);
			dtos.add(dto);
		}
		
		log.info("Encontrados {} trailers", dtos.size());
		return dtos;
	}
	
	public TrailerRetornoDto gettrailerById(Long id) {
		log.info("Buscando registro do trailer de ID: {}", id);
		Optional<TrailerModel> trailerOptional = trailerRepository.findById(id);
		
		if(trailerOptional.isPresent()) {
			TrailerModel trailerModel = trailerOptional.get();
			log.info("Trailer com ID: {}, encontrado com sucesso!", id);
			return new TrailerRetornoDto(trailerModel);
		}
		
		log.warn("Não foi possivel encontrar o trailer com o ID: {}", id);
		throw new ValidacaoException("Não foi possivel encontrar o trailer com o ID indicado");
	}
	
	public TrailerRetornoDto cadastraTrailer(TrailerCadastroDto dados) {
		log.info("Cadastrando novo trailer");
		Optional<FilmeModel> filmeOptional = filmeRepository.findById(dados.getFilmeId());
		
		if(filmeOptional.isPresent()) {
			FilmeModel filme = filmeOptional.get();
			TrailerModel trailer = new TrailerModel(dados);
			trailer.setFilme(filme);
			trailerRepository.save(trailer);
			
			log.info("Novo trailer cadastrado com sucesso");
			return new TrailerRetornoDto(trailer);
		}
		
		log.warn("Não foi possivel encontrar o filme com o ID: {}", dados.getFilmeId());
		throw new ValidacaoException("Não foi possível encontrar o filme com o ID indicado");
	}
	
	public TrailerRetornoDto putTrailer(TrailerCadastroDto dados, Long id) {
		log.info("Iniciando a atualização de dados do trailer de ID: {}", id);
		Optional<TrailerModel> trailerOptional = trailerRepository.findById(id);
		
		if(trailerOptional.isPresent()) {
			TrailerModel trailer = trailerOptional.get();
			
			Optional<FilmeModel> filmeOptional = filmeRepository.findById(dados.getFilmeId());
			
			if(filmeOptional.isPresent()) {
				FilmeModel filme = filmeOptional.get();
				trailer.atualizaTrailer(dados);
				trailer.setFilme(filme);
				trailerRepository.save(trailer);
				
				log.info("Dados do trailer de ID: {}, atualizados com sucesso!", id);
				return new TrailerRetornoDto(trailer);
			}
			
			log.warn("Filme com ID: {}, não foi encontrado", id);
			throw new ValidacaoException("Não foi possível encontrar o filme com o ID indicado");
		}
		
		log.warn("Trailer com ID: {}, não foi encontrado", id);
		throw new ValidacaoException("Não foi possível encontrar o trailer com o ID indicado");
	}
	
	public void deletaTrailer(Long id) {
		log.info("Iniciando a exclusão do Trailer de ID: {}", id);
		Optional<TrailerModel> trailerOptional = trailerRepository.findById(id);
		
		if(trailerOptional.isPresent()) {
			trailerRepository.deleteById(id);
			log.info("Trailer de ID {}, foi deletado com sucesso!", id);
		}else {
			log.warn("Trailer de ID {} não foi encontrado", id);
			throw new ValidacaoException("Não foi possível encontrar o trailer com o ID indicado");			
		}
		
		
	}
}
