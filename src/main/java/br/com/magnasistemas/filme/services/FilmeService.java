package br.com.magnasistemas.filme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(FilmeService.class);
	
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
		log.info("Buscando todos os registros de filmes");
		var filmes = filmeRepository.findAll();
		List<FilmeRetornoDto> dtos= new ArrayList<>();
		
		for(FilmeModel filme : filmes) {
			FilmeRetornoDto dto = new FilmeRetornoDto(filme);
			dtos.add(dto);
		}
		
		log.info("Encontrados {} filmes", dtos.size());
		return dtos;
	}
	
	public FilmeRetornoDto getFilmeById(Long id) {
		log.info("Buscando registro do filme de ID: {}", id);
		Optional<FilmeModel> filmeOptional = filmeRepository.findById(id);
		
		if(filmeOptional.isPresent()) {
			FilmeModel filmeModel = filmeOptional.get();
			log.info("Filme de ID: {}, encontrado com sucesso!", id);
			return new FilmeRetornoDto(filmeModel);
		}
		
		log.warn("Não foi possivel encontrar o filme com o ID: {}", id);
		throw new ValidacaoException("Não foi possivel encontrar o filme com o ID indicado");
	}
	
	public FilmeRetornoDto cadastrarFilme(FilmeCadastroDto dados) {
		log.info("Cadastrando novo filme");
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
				
				log.info("Novo filme cadastrado com sucesso!");
				return new FilmeRetornoDto(filme);
			}
			
			log.warn("Não foi possivel encontrar a produtora com o ID: {}", dados.getProdutoraId());
			throw new ValidacaoException("Não foi possível encontrar a produtora com o ID indicado");
		}
		log.warn("Não foi possivel encontrar o diretor com o ID: {}", dados.getDiretorId());
		throw new ValidacaoException("Não foi possível encontrar o diretor com o ID indicado");
	}
	
	public FilmeRetornoDto putFilme(FilmeCadastroDto dados, Long id) {
		log.info("Iniciando a atualização de dados do filme de ID: {}", id);
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

					log.info("Dados do filme de ID: {}, atualizados com sucesso!", id);
					return new FilmeRetornoDto(filme);
				}
				
				log.warn("Não foi possivel encontrar a produtora com o ID: {}", dados.getProdutoraId());
				throw new ValidacaoException("Não foi possível encontrar a produtora com o ID indicado");
			}
			
			log.warn("Não foi possivel encontrar o diretor com o ID: {}", dados.getDiretorId());
			throw new ValidacaoException("Não foi possivel encontrar o diretor com o ID indicado");
		}
		
		log.warn("Não foi possivel encontrar o filme com o ID: {}", id);
		throw new ValidacaoException("Não foi possivel encontrar o filme com o ID indicado");
	}
	
	public FilmeRetornoDto adicionarAtor(AtorFilmeDto dados) {
		log.info("Adicionando ator de ID: {}, no filme de ID: {}", dados.getIdAtor(), dados.getIdFilme());
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
				
				log.info("Ator adicionado ao filme com sucesso!");
				return new FilmeRetornoDto(filme); 
			}else {
				
				log.warn("Não foi possivel encontrar o ator/atriz com o ID: {}", dados.getIdAtor());
				throw new ValidacaoException("Não foi possível encontrar o ator com o ID indicado");
			}
			
		}else {
			
			log.warn("Não foi possivel encontrar o filme com o ID: {}", dados.getIdFilme());
			throw new ValidacaoException("Não foi possível encontrar o filme com o ID indicado");
		}
	}
	
	public void deletaFilme(Long id) {
		log.info("Iniciando a exclusão do filme de ID: {}", id);
		Optional<FilmeModel> filmeOptional = filmeRepository.findById(id);
		
		if(filmeOptional.isPresent()) {
			filmeRepository.deleteById(id);
			log.info("Filme de ID {}, foi deletado com sucesso!", id);
		}else {
			
			log.warn("Filme de ID {} não foi encontrado", id);
			throw new ValidacaoException("Não foi possível encontrar o filme com o ID indicado");			
		}
		
	}
	
}
