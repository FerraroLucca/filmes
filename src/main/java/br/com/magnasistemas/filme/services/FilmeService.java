package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.magnasistemas.filme.dtos.AtorFilmeDto;
import br.com.magnasistemas.filme.dtos.FilmeCadastroDto;
import br.com.magnasistemas.filme.dtos.FilmeRetornoDto;
import br.com.magnasistemas.filme.exceptions.ValidacaoException;
import br.com.magnasistemas.filme.models.AtorModel;
import br.com.magnasistemas.filme.models.DiretorModel;
import br.com.magnasistemas.filme.models.FilmeModel;
import br.com.magnasistemas.filme.models.ProdutoraModel;
import br.com.magnasistemas.filme.repositories.AtorRepository;
import br.com.magnasistemas.filme.repositories.DiretorRepository;
import br.com.magnasistemas.filme.repositories.FilmeRepository;
import br.com.magnasistemas.filme.repositories.ProdutoraRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FilmeService {
	
	private final FilmeRepository filmeRepository;
	private final DiretorRepository diretorRepository;
	private final ProdutoraRepository produtoraRepository;
	private final AtorRepository atorRepository;
	
	
	public FilmeService(FilmeRepository filmeRepository, DiretorRepository diretorRepository,
			ProdutoraRepository produtoraRepository, AtorRepository atorRepository) {
		this.filmeRepository = filmeRepository;
		this.diretorRepository = diretorRepository;
		this.produtoraRepository = produtoraRepository;
		this.atorRepository = atorRepository;
	}

	public List<FilmeRetornoDto> listarFilmes(){
		
		var filmes = filmeRepository.findAll();
		List<FilmeRetornoDto> dtos= new ArrayList<>();
		
		for(FilmeModel filme : filmes) {
			FilmeRetornoDto dto = new FilmeRetornoDto(filme);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	public FilmeRetornoDto getFilmeById(Long id) {
		
		Optional<FilmeModel> filmeOptional = filmeRepository.findById(id);
		
		if(filmeOptional.isPresent()) {
			FilmeModel filmeModel = filmeOptional.get();
			return new FilmeRetornoDto(filmeModel);
		}
		
		throw new ValidacaoException("Não foi possivel encontrar o filme com o ID indicado");
	}
	
	public FilmeRetornoDto cadastrarFilme(FilmeCadastroDto dados) {
	
		Optional<DiretorModel> diretorOptional = diretorRepository.findById(dados.getDiretorId());
		
		if(diretorOptional.isPresent()) {
			DiretorModel diretor = diretorOptional.get();
			
			Optional<ProdutoraModel> produtoraOptional = produtoraRepository.findById(dados.getProdutoraId());			
			
			if(produtoraOptional.isPresent()) {
				ProdutoraModel produtora = produtoraOptional.get();
				
				FilmeModel filme = new FilmeModel(dados);
				filme.setDiretor(diretor);
				filme.setProdutora(produtora);
				filmeRepository.save(filme);
				
				return new FilmeRetornoDto(filme);
			}
			throw new ValidacaoException("Não foi possível encontrar a produtora com o ID indicado");
		}
		throw new ValidacaoException("Não foi possível encontrar o diretor com o ID indicado");
	}
	
	public FilmeRetornoDto putFilme(FilmeCadastroDto dados, Long id) {
		
		Optional<FilmeModel> filmeOptional = filmeRepository.findById(id);
		
		if(filmeOptional.isPresent()) {
			FilmeModel filme = filmeOptional.get();
			Optional<DiretorModel> diretorOptional = diretorRepository.findById(dados.getDiretorId());
			
			if(diretorOptional.isPresent()) {
				DiretorModel diretor = diretorOptional.get();
				Optional<ProdutoraModel> produtoraOptional = produtoraRepository.findById(dados.getProdutoraId());			
				
				if(produtoraOptional.isPresent()) {
					ProdutoraModel produtora = produtoraOptional.get();
					filme.atualizaFilme(dados);
					filme.setDiretor(diretor);
					filme.setProdutora(produtora);
					
					filmeRepository.save(filme);
					return new FilmeRetornoDto(filme);
				}
				
				throw new ValidacaoException("Não foi possível encontrar a produtora com o ID indicado");
			}
			throw new ValidacaoException("Não foi possivel encontrar o diretor com o ID indicado");
		}
		throw new ValidacaoException("Não foi possivel encontrar o filme com o ID indicado");
	}
	
	public FilmeRetornoDto adicionarAtor(AtorFilmeDto dados) {
		
		Optional<FilmeModel> filmeOptional = filmeRepository.findById(dados.getIdFilme());
		
		if(filmeOptional.isPresent()) {
			FilmeModel filme = filmeOptional.get();
			
			Optional<AtorModel> atorOptional = atorRepository.findById(dados.getIdAtor());
			
			if(atorOptional.isPresent()) {
				AtorModel ator = atorOptional.get();
				
				List<AtorModel> atores = filme.getAtores();
				atores.add(ator);
				
				filme.setAtores(atores);
				filmeRepository.save(filme);
				
				return new FilmeRetornoDto(filme); 
			}else {
				throw new ValidacaoException("Não foi possível encontrar o ator com o ID indicado");
			}
			
		}else {
			throw new ValidacaoException("Não foi possível encontrar o filme com o ID indicado");
		}
	}
	
	public void deletaFilme(Long id) {
		
		Optional<FilmeModel> filmeOptional = filmeRepository.findById(id);
		
		if(filmeOptional.isPresent()) {
			filmeRepository.deleteById(id);
		}else {
			throw new ValidacaoException("Não foi possível encontrar o filme com o ID indicado");			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
