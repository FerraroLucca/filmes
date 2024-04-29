package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	private final AtorRepository atorRepository;
	private final PaisRepository paisRepository;
	
	
	public AtorService(AtorRepository atorRepository, PaisRepository paisRepository) {
		this.atorRepository = atorRepository;
		this.paisRepository = paisRepository;
	}
	
	
	public List<AtorRetornoDto> listarAtores(){
		
		var atores = atorRepository.findAll();
		List<AtorRetornoDto> dtos = new ArrayList<>();
		
		for(AtorModel ator : atores) {
			AtorRetornoDto dto = new AtorRetornoDto(ator);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	public AtorRetornoDto getAtorById(Long id) {
		
		Optional<AtorModel> atorOptional = atorRepository.findById(id);
		
		if(atorOptional.isPresent()) {
			AtorModel atorModel = atorOptional.get();
			return new AtorRetornoDto(atorModel);
		}
		
		throw new ValidacaoException("Não foi possivel encontrar do(a) ator/atriz com o ID indicado");
	}
	
	public AtorRetornoDto cadastraAtor(AtorCadastroDto dados) {
		
		Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
		
		if(paisOptional.isPresent()) {
			PaisModel pais = paisOptional.get();
			AtorModel ator = new AtorModel(dados);
			ator.setPais(pais);
			atorRepository.save(ator);
			
			return new AtorRetornoDto(ator);
		}
		throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
	}
	
	public AtorRetornoDto putAtor(AtorCadastroDto dados, Long id) {
		
		Optional<AtorModel> atorOptional = atorRepository.findById(id);
		
		if(atorOptional.isPresent()) {
			AtorModel ator = atorOptional.get();
			Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
			
			if(paisOptional.isPresent()) {
				PaisModel pais = paisOptional.get();
				ator.atualizaAtor(dados);
				ator.setPais(pais);
				
				atorRepository.save(ator);
				
				return new AtorRetornoDto(ator);
			}
			
			throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
		}
		
		throw new ValidacaoException("Não foi possivel encontrar do(a) ator/atriz com o ID indicado");
	}
	
	public void deletaAtor(Long id) {
		
		Optional<AtorModel> atorOptional = atorRepository.findById(id);
		
		if(atorOptional.isPresent()) {
			atorRepository.deleteById(id);
		}else {
			throw new ValidacaoException("Não foi possível encontrar o ator com o ID indicado");			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
