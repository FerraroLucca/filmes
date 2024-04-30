package br.com.magnasistemas.filme.controllers;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magnasistemas.filme.dtos.DiretorCadastroDto;
import br.com.magnasistemas.filme.dtos.FilmeCadastroDto;
import br.com.magnasistemas.filme.dtos.PaisCadastroDto;
import br.com.magnasistemas.filme.dtos.ProdutoraCadastroDto;
import br.com.magnasistemas.filme.dtos.TrailerCadastroDto;
import br.com.magnasistemas.filme.models.DiretorModel;
import br.com.magnasistemas.filme.models.FilmeModel;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.models.TrailerModel;
import br.com.magnasistemas.filme.repositories.DiretorRepository;
import br.com.magnasistemas.filme.repositories.FilmeRepository;
import br.com.magnasistemas.filme.repositories.PaisRepository;
import br.com.magnasistemas.filme.repositories.ProdutoraRepository;
import br.com.magnasistemas.filme.repositories.TrailerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TrailerControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private FilmeRepository filmeRepository;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private ProdutoraRepository produtoraRepository;

	@Autowired
	private DiretorRepository diretorRepository;
	
	@Autowired
	private TrailerRepository trailerRepository;
	
	@BeforeEach
	public void inicializar() {
		criarPais();
		criarDiretor();
		criarProdutora();
		criarFilme();
		criarTrailer();
	}

	@AfterEach
	public void finalizar() {
		trailerRepository.LimparDadosERedefinirSequence();
		filmeRepository.LimparDadosERedefinirSequence();
		diretorRepository.LimparDadosERedefinirSequence();
		produtoraRepository.LimparDadosERedefinirSequence();
		paisRepository.LimparDadosERedefinirSequence();
	}

	public void criarPais() {
		PaisCadastroDto pais = new PaisCadastroDto("Brasil", "Brasília", "América", "Português");
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/pais/create", pais, PaisModel.class);
	}

	public void criarProdutora() {
		ProdutoraCadastroDto produtora = new ProdutoraCadastroDto("Marvel", 1923, 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/produtora/create", produtora,
				PaisModel.class);
	}

	public void criarDiretor() {
		DiretorCadastroDto diretor = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(1992, 10, 10), "Masculino",
				1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/diretor/create", diretor,
				DiretorModel.class);
	}

	public void criarFilme() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, 1l, 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/filme/create", filme, FilmeModel.class);
	}
	
	public void criarTrailer() {
		TrailerCadastroDto trailer = new TrailerCadastroDto("http://www.youtube.com/trailer", 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/trailer/create", trailer, TrailerModel.class);
	}
	
	
	// TESTES POST

}
