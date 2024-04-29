package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	private final TrailerRepository trailerRepository;
	private final FilmeRepository filmeRepository;

	
	
	public TrailerService(TrailerRepository trailerRepository, FilmeRepository filmeRepository) {
		this.trailerRepository = trailerRepository;
		this.filmeRepository = filmeRepository;
	}
	
	

	public List<TrailerRetornoDto> listarTrailers(){
		var traileres = trailerRepository.findAll();
		List<TrailerRetornoDto> dtos = new ArrayList<>();
		
		for(TrailerModel trailer : traileres) {
			TrailerRetornoDto dto = new TrailerRetornoDto(trailer);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	public TrailerRetornoDto gettrailerById(Long id) {
		
		Optional<TrailerModel> trailerOptional = trailerRepository.findById(id);
		
		if(trailerOptional.isPresent()) {
			TrailerModel trailerModel = trailerOptional.get();
			return new TrailerRetornoDto(trailerModel);
		}
		
		throw new ValidacaoException("Não foi possivel encontrar o trailer com o ID indicado");
	}
	
	public TrailerRetornoDto cadastraTrailer(TrailerCadastroDto dados) {
		
		Optional<FilmeModel> filmeOptional = filmeRepository.findById(dados.getFilmeId());
		
		if(filmeOptional.isPresent()) {
			FilmeModel filme = filmeOptional.get();
			TrailerModel trailer = new TrailerModel(dados);
			trailer.setFilme(filme);
			trailerRepository.save(trailer);
			
			return new TrailerRetornoDto(trailer);
		}
		
		throw new ValidacaoException("Não foi possível encontrar o filme com o ID indicado");
	}
	
	public TrailerRetornoDto putTrailer(TrailerCadastroDto dados, Long id) {
		
		Optional<TrailerModel> trailerOptional = trailerRepository.findById(id);
		
		if(trailerOptional.isPresent()) {
			TrailerModel trailer = trailerOptional.get();
			
			Optional<FilmeModel> filmeOptional = filmeRepository.findById(dados.getFilmeId());
			
			if(filmeOptional.isPresent()) {
				FilmeModel filme = filmeOptional.get();
				trailer.atualizaTrailer(dados);
				trailer.setFilme(filme);
				
				trailerRepository.save(trailer);
				
				return new TrailerRetornoDto(trailer);
			}
			throw new ValidacaoException("Não foi possível encontrar o filme com o ID indicado");
		}
		
		throw new ValidacaoException("Não foi possível encontrar o trailer com o ID indicado");
	}
	
	public void deletaTrailer(Long id) {
		
		Optional<TrailerModel> trailerOptional = trailerRepository.findById(id);
		
		if(trailerOptional.isPresent()) {
			trailerRepository.deleteById(id);
		}else {
			throw new ValidacaoException("Não foi possível encontrar o trailer com o ID indicado");			
		}
		
		
	}
}
