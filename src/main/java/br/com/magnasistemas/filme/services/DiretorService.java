package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	private final DiretorRepository diretorRepository;
	private final PaisRepository paisRepository;

	
	public DiretorService(DiretorRepository diretorRepository, PaisRepository paisRepository) {
		this.diretorRepository = diretorRepository;
		this.paisRepository = paisRepository;
	}
	
	
	public List<DiretorRetornoDto> listarDiretores(){
		var diretores = diretorRepository.findAll();
		List<DiretorRetornoDto> dtos = new ArrayList<>();
		
		for(DiretorModel diretor : diretores) {
			DiretorRetornoDto dto = new DiretorRetornoDto(diretor);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	public DiretorRetornoDto getDiretorById(Long id) {
		
		Optional<DiretorModel> diretorOptional = diretorRepository.findById(id);
		
		if(diretorOptional.isPresent()) {
			DiretorModel diretorModel = diretorOptional.get();
			return new DiretorRetornoDto(diretorModel);
		}
		
		throw new ValidacaoException("Não foi possivel encontrar o diretor com o ID indicado");
	}
	
	public DiretorRetornoDto cadastraDiretor(DiretorCadastroDto dados) {
		
		Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
		
		if(paisOptional.isPresent()) {
			PaisModel pais = paisOptional.get();
			DiretorModel diretor = new DiretorModel(dados);
			diretor.setPais(pais);
			diretorRepository.save(diretor);
			
			return new DiretorRetornoDto(diretor);
		}
		
		throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
	}
	
	public DiretorRetornoDto putDiretor(DiretorCadastroDto dados, Long id) {
		
		Optional<DiretorModel> diretorOptional = diretorRepository.findById(id);
		
		if(diretorOptional.isPresent()) {
			DiretorModel diretor = diretorOptional.get();
			
			Optional<PaisModel> paisOptional = paisRepository.findById(dados.getPaisId());
			
			if(paisOptional.isPresent()) {
				PaisModel pais = paisOptional.get();
				diretor.atualizaDiretor(dados);
				diretor.setPais(pais);
				
				diretorRepository.save(diretor);
				
				return new DiretorRetornoDto(diretor);
			}
			throw new ValidacaoException("Não foi possível encontrar o país com o ID indicado");
		}
		
		throw new ValidacaoException("Não foi possível encontrar o diretor com o ID indicado");
	}
	
	public void deletaDiretor(Long id) {
		
		Optional<DiretorModel> diretorOptional = diretorRepository.findById(id);
		
		if(diretorOptional.isPresent()) {
			diretorRepository.deleteById(id);
		}else {
			throw new ValidacaoException("Não foi possível encontrar o diretor com o ID indicado");			
		}
		
		
	}
}









