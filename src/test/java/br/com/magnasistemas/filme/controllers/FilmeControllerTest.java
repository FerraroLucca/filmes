package br.com.magnasistemas.filme.controllers;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magnasistemas.filme.dtos.AtorCadastroDto;
import br.com.magnasistemas.filme.dtos.AtorFilmeDto;
import br.com.magnasistemas.filme.dtos.DiretorCadastroDto;
import br.com.magnasistemas.filme.dtos.FilmeCadastroDto;
import br.com.magnasistemas.filme.dtos.FilmeRetornoDto;
import br.com.magnasistemas.filme.dtos.PaisCadastroDto;
import br.com.magnasistemas.filme.dtos.ProdutoraCadastroDto;
import br.com.magnasistemas.filme.models.AtorModel;
import br.com.magnasistemas.filme.models.DiretorModel;
import br.com.magnasistemas.filme.models.FilmeModel;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.repositories.AtorRepository;
import br.com.magnasistemas.filme.repositories.DiretorRepository;
import br.com.magnasistemas.filme.repositories.FilmeRepository;
import br.com.magnasistemas.filme.repositories.PaisRepository;
import br.com.magnasistemas.filme.repositories.ProdutoraRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FilmeControllerTest {

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
	private AtorRepository atorRepository;

	@BeforeEach
	public void inicializar() {
		criarPais();
		criarDiretor();
		criarProdutora();
		criarFilme();
		criarAtor();
	}

	@AfterEach
	public void finalizar() {
		atorRepository.LimparDadosERedefinirSequence();
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
	
	public void criarAtor() {
		AtorCadastroDto ator = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino", 1534, 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/ator/create", ator, AtorModel.class);
	}

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar um Filme já que as informações estão validas")
	public void testCadastrarUmaDiretorComInformcoesValidas() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<FilmeRetornoDto> response = restTemplate.postForEntity(
				"http://localhost:" + randomServerPort + "/filme/create", request, FilmeRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que o nome não pode ser nulo")
	public void testCadastrarUmaFilmeComNomeNulo() {
		FilmeCadastroDto filme = new FilmeCadastroDto(null, "Ação", "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O nome do filme é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que o genero não pode ser nulo")
	public void testCadastrarUmaFilmeComGeneroNulo() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", null, "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O nome da categoria é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que a classificação não pode ser nulo")
	public void testCadastrarUmaFilmeComClassificacaoNulo() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", null, "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("A classificação do filme é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que a sinopse não pode ser nulo")
	public void testCadastrarUmaFilmeComSinopseNulo() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", "+12", null, "www.fotofilme.com.br", 180,
				1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("A sinopse é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que o poster não pode ser nulo")
	public void testCadastrarUmaFilmeComPosterNulo() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", "+12", "Herois vencendo vilões", null, 180,
				1l, 1l);

		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O poster é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que a duração não pode ser nulo")
	public void testCadastrarUmaFilmeComDuracaoNulo() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", null, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O tempo de duração do filme é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que o Id da Produtora não pode ser nulo")
	public void testCadastrarUmaFilmeComIdProdutoraNulo() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O id da proddutora não foi localizado"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que o Id do Diretor não pode ser nulo")
	public void testCadastrarUmaFilmeComIdDiretorNulo() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, 1l, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O id do diretor não foi localizado"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que o Id da produtora é inválida")
	public void testCadastrarUmaFilmeComIdProdutoraInvalido() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, 1000l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("Não foi possível encontrar a produtora com o ID indicado"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Filme já que o Id do Diretor é inválida")
	public void testCadastrarUmaFilmeComIdDiretorInvalido() {
		FilmeCadastroDto filme = new FilmeCadastroDto("Vingadores", "Ação", "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, 1l, 1000l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filme, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/filme/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("Não foi possível encontrar o diretor com o ID indicado"));
	}
	
	@Test
	@DisplayName("Deveria cadastrar um Ator_Filme já que as informações estão validas")
	public void testCadastrarUmAtorFilmeComInformacoesValidas() {
		AtorFilmeDto atorFilme = new AtorFilmeDto();
		atorFilme.setIdAtor(1l);
		atorFilme.setIdFilme(1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorFilmeDto> request = new HttpEntity<>(atorFilme, headers);

		ResponseEntity<FilmeRetornoDto> response = restTemplate.postForEntity(
				"http://localhost:" + randomServerPort + "/filme/adicionaAtor", request, FilmeRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Ator_Filme já que o id ator não pode ser nulo")
	public void testCadastrarUmAtorFilmeComIdAtorNulo() {
		AtorFilmeDto atorFilme = new AtorFilmeDto();
		atorFilme.setIdAtor(null);
		atorFilme.setIdFilme(1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorFilmeDto> request = new HttpEntity<>(atorFilme, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + randomServerPort + "/filme/adicionaAtor", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O id do ator é obrigatório"));
	}
	
	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Ator_Filme já que o id filme não pode ser nulo")
	public void testCadastrarUmAtorFilmeComIdFilmeNulo() {
		AtorFilmeDto atorFilme = new AtorFilmeDto();
		atorFilme.setIdAtor(1l);
		atorFilme.setIdFilme(null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorFilmeDto> request = new HttpEntity<>(atorFilme, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + randomServerPort + "/filme/adicionaAtor", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O id do filme é obrigatório"));
	}
	
	@Test
	@DisplayName("Deveria dar erro cadastrar um Ator_Filme já que o id ator não pode ser invalido")
	public void testCadastrarUmAtorFilmeComIdAtorInvalido() {
		AtorFilmeDto atorFilme = new AtorFilmeDto();
		atorFilme.setIdAtor(100l);
		atorFilme.setIdFilme(1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorFilmeDto> request = new HttpEntity<>(atorFilme, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + randomServerPort + "/filme/adicionaAtor", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar erro cadastrar um Ator_Filme já que o id filme não pode ser invalido")
	public void testCadastrarUmAtorFilmeComIdFilmeInvalido() {
		AtorFilmeDto atorFilme = new AtorFilmeDto();
		atorFilme.setIdAtor(1l);
		atorFilme.setIdFilme(100l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorFilmeDto> request = new HttpEntity<>(atorFilme, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + randomServerPort + "/filme/adicionaAtor", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	// TESTES GET

	@Test
	@DisplayName("Deveria listar todos os filmes")
	public void testListarTodosOdFilmesDoSistema() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		ResponseEntity<List<FilmeRetornoDto>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<FilmeRetornoDto>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria listar o filme com id valido")
	public void testListarUmaFilmeComIdValido() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + randomServerPort + "/filme/1",
				String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou o filme com id invalido")
	public void testListarUmaFilmeComIdInvalido() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/filme/100", String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertTrue(response.getBody().contains("Não foi possivel encontrar o filme com o ID indicado"));
	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retortar o Filme de ID 1 alterado com sucesso")
	public void testAtualizarUmFilmeComIdValido() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", "+12",
				"Herois vencendo vilões", "www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<FilmeRetornoDto> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request,
				FilmeRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(filmeAtualizado.getNome(), response.getBody().getNome());
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que seu nome não pode ser nulo")
	public void testAtualizarUmFilmeComNomeNulo() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto(null, "Ação", "+12", "Herois vencendo vilões",
				"www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que seu genero não pode ser nulo")
	public void testAtualizarUmFilmeComGeneroNulo() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", null, "+12",
				"Herois vencendo vilões", "www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que sua classificação não pode ser nulo")
	public void testAtualizarUmFilmeComClassificacaoNulo() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", null,
				"Herois vencendo vilões", "www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que sua Sinopse não pode ser nulo")
	public void testAtualizarUmFilmeComSinopseNulo() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", "+12", null,
				"www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que sua poster não pode ser nulo")
	public void testAtualizarUmFilmeComPosterNulo() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", "+12",
				"Herois vencendo vilões", null, 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que sua duração não pode ser nulo")
	public void testAtualizarUmFilmeComDuracaoNulo() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", "+12",
				"Herois vencendo vilões", "www.fotofilme.com.br", null, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que seu Id Produtora não pode ser nulo")
	public void testAtualizarUmFilmeComIdProdutoraNulo() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", "+12",
				"Herois vencendo vilões", "www.fotofilme.com.br", 180, null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que seu Id Diretor não pode ser nulo")
	public void testAtualizarUmFilmeComDiretorNulo() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", "+12",
				"Herois vencendo vilões", "www.fotofilme.com.br", 180, 1l, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que seu Id Produtora é invalido")
	public void testAtualizarUmFilmeComIdProdutoraInvalido() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", "+12",
				"Herois vencendo vilões", "www.fotofilme.com.br", 180, 100l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que seu Id Diretor é invalido")
	public void testAtualizarUmFilmeComIdDiretorInvalido() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", "+12",
				"Herois vencendo vilões", "www.fotofilme.com.br", 180, 1l, 100l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Filme já que seu Id Filme é invalido")
	public void testAtualizarUmFilmeComIdFilmeInvalido() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		FilmeCadastroDto filmeAtualizado = new FilmeCadastroDto("Doutor Estranho", "Ação", "+12",
				"Herois vencendo vilões", "www.fotofilme.com.br", 180, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<FilmeCadastroDto> request = new HttpEntity<>(filmeAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/update/100", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}

	// TESTES DELETE

	@Test
	@DisplayName("Deveria retornar no Content já que o filme foi excluido com sucesso")
	public void testDeletaUmFilmeComIdValido() {
		criarPais();
		criarProdutora();
		criarDiretor();
		criarFilme();
		ResponseEntity<FilmeRetornoDto> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/filme/1", HttpMethod.DELETE, null, FilmeRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria retornar not found já que a produtora não foi encontrado")
	public void testDeletaUmaFilmeComIdInvalido() {
		criarPais();
		criarProdutora();
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/filme/100",
				HttpMethod.DELETE, null, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
	}

}
