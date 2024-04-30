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

import br.com.magnasistemas.filme.dtos.DiretorCadastroDto;
import br.com.magnasistemas.filme.dtos.DiretorRetornoDto;
import br.com.magnasistemas.filme.dtos.PaisCadastroDto;
import br.com.magnasistemas.filme.dtos.PaisRetornoDto;
import br.com.magnasistemas.filme.dtos.ProdutoraCadastroDto;
import br.com.magnasistemas.filme.dtos.ProdutoraRetornoDto;
import br.com.magnasistemas.filme.models.DiretorModel;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.repositories.DiretorRepository;
import br.com.magnasistemas.filme.repositories.PaisRepository;
import br.com.magnasistemas.filme.repositories.ProdutoraRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DiretorControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private DiretorRepository diretorRepository;

	@BeforeEach
	public void inicializar() { 
		criarPais();
		criarDiretor();
	}

	@AfterEach
	public void finalizar() {
		diretorRepository.LimparDadosERedefinirSequence();
		paisRepository.LimparDadosERedefinirSequence();
	}

	public void criarPais() {
		PaisCadastroDto pais = new PaisCadastroDto("Brasil", "Brasília", "América", "Português");
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/pais/create", pais, PaisModel.class);
	}

	public void criarDiretor() {
		DiretorCadastroDto diretor = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(1992, 10, 10), "Masculino",
				1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/diretor/create", diretor,
				DiretorModel.class);
	}

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar um Diretor já que as informações estão validas")
	public void testCadastrarUmaDiretorComInformcoesValidas() {
		DiretorCadastroDto diretor = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(1992, 10, 10), "Masculino",
				1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretor, headers);

		ResponseEntity<DiretorRetornoDto> response = restTemplate.postForEntity(
				"http://localhost:" + randomServerPort + "/diretor/create", request, DiretorRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Diretor já que o nome não pode ser nulo")
	public void testCadastrarUmaDiretorComNomeNulo() {
		DiretorCadastroDto diretor = new DiretorCadastroDto(null, LocalDate.of(2003, 9, 1), "Masculino", 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretor, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/diretor/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O nome do diretor é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Diretor já que a Data de Nascimento não pode ser nulo")
	public void testCadastrarUmaDiretorComDataNascimentoNulo() {
		DiretorCadastroDto diretor = new DiretorCadastroDto("Chistopher Nolan", null, "Masculino", 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretor, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/diretor/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("A data de nascimento do diretor é obrigatória"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Diretor já que o genero não pode ser nulo")
	public void testCadastrarUmaDiretorComGeneroNulo() {
		DiretorCadastroDto diretor = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(2003, 9, 1), null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretor, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/diretor/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O genero do diretor é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Diretor já que o Id do Pais não pode ser nulo")
	public void testCadastrarUmaDiretorComIdPaisNulo() {
		DiretorCadastroDto diretor = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(2003, 9, 1), "Masculino",
				null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretor, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/diretor/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O id do país é obrigatório"));
	}
	
	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Diretor já que o Id do Pais invalido")
	public void testCadastrarUmaDiretorComIdPaisInvalido() {
		DiretorCadastroDto diretor = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(2003, 9, 1), "Masculino",
				100l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretor, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/diretor/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("Não foi possível encontrar o país com o ID indicado"));
	}

	// TESTES GET

	@Test
	@DisplayName("Deveria listar todos os diretores")
	public void testListarTodosOsDiretoresDoSistema() {
		criarPais();
		criarDiretor();
		ResponseEntity<List<DiretorRetornoDto>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DiretorRetornoDto>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria listar o diretor com id valido")
	public void testListarUmDiretorComIdValido() {
		criarPais();
		criarDiretor();
		ResponseEntity<DiretorRetornoDto> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/diretor/1", DiretorRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou o diretor com id invalido")
	public void testListarUmDiretorComIdInvalido() {
		criarPais();
		criarDiretor();
		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/diretor/100", String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertTrue(response.getBody().contains("Não foi possivel encontrar o diretor com o ID indicado"));
	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retortar o Diretor de ID 1 alterado com sucesso")
	public void testAtualizarUmaDiretorComIdValido() {
		criarPais();
		criarDiretor();
		DiretorCadastroDto diretorAtualizado = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(1992, 10, 10),
				"Masculino", 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretorAtualizado, headers);

		ResponseEntity<DiretorRetornoDto> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/update/1", HttpMethod.PUT, request,
				DiretorRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(diretorAtualizado.getNome(), response.getBody().getNome());
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Diretor já que seu nome não pode ser nulo")
	public void testAtualizarUmDiretorComNomeNulo() {
		criarPais();
		criarDiretor();
		DiretorCadastroDto diretorAtualizado = new DiretorCadastroDto(null, LocalDate.of(1992, 10, 10), "Masculino",
				1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Diretor já que sua data não pode ser nulo")
	public void testAtualizarUmDiretorComDataNascimentoNulo() {
		criarPais();
		criarDiretor();
		DiretorCadastroDto diretorAtualizado = new DiretorCadastroDto("Chistopher Nolan", null, "Masculino", 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Diretor já que seu genero não pode ser nulo")
	public void testAtualizarUmDiretorComGeneroNulo() {
		criarPais();
		criarDiretor();
		DiretorCadastroDto diretorAtualizado = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(1992, 10, 10),
				null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um Diretor já que seu Id Pais não pode ser nulo")
	public void testAtualizarUmDiretorComIdPaisNulo() {
		criarPais();
		criarDiretor();
		DiretorCadastroDto diretorAtualizado = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(1992, 10, 10),
				"Masculino", null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Diretor já que seu Id Pais é invalido")
	public void testAtualizarUmDiretorComIdPaisInvalido() {
		criarPais();
		criarDiretor();
		DiretorCadastroDto diretorAtualizado = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(1992, 10, 10),
				"Masculino", 100l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Diretor já que seu Id é invalido")
	public void testAtualizarUmDiretorComIdInvalido() {
		criarPais();
		criarDiretor();
		DiretorCadastroDto diretorAtualizado = new DiretorCadastroDto("Chistopher Nolan", LocalDate.of(1992, 10, 10),
				"Masculino", 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DiretorCadastroDto> request = new HttpEntity<>(diretorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/update/100", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}

	// TESTES DELETE

	@Test
	@DisplayName("Deveria retornar no Content já que o diretor foi excluido com sucesso")
	public void testDeletaUmDiretorComIdValido() {
		criarPais();
		criarDiretor();
		ResponseEntity<DiretorRetornoDto> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/1", HttpMethod.DELETE, null,
				DiretorRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria retornar not found já que o diretor não foi encontrado")
	public void testDeletaUmDiretorComIdInvalido() {
		criarPais();
		criarDiretor();
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/diretor/100", HttpMethod.DELETE, null,
				String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
	}

}
