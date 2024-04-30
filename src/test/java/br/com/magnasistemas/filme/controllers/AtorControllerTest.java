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
import br.com.magnasistemas.filme.dtos.AtorRetornoDto;
import br.com.magnasistemas.filme.dtos.DiretorCadastroDto;
import br.com.magnasistemas.filme.dtos.DiretorRetornoDto;
import br.com.magnasistemas.filme.dtos.PaisCadastroDto;
import br.com.magnasistemas.filme.models.AtorModel;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.repositories.AtorRepository;
import br.com.magnasistemas.filme.repositories.PaisRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AtorControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private AtorRepository atorRepository;

	@BeforeEach
	public void inicializar() {
		criarPais();
		criarAtor();
	}

	@AfterEach
	public void finalizar() {
		atorRepository.LimparDadosERedefinirSequence();
		paisRepository.LimparDadosERedefinirSequence();
	}

	public void criarPais() {
		PaisCadastroDto pais = new PaisCadastroDto("Brasil", "Brasília", "América", "Português");
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/pais/create", pais, PaisModel.class);
	}

	public void criarAtor() {
		AtorCadastroDto ator = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino", 1534, 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/ator/create", ator, AtorModel.class);
	}

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar um Ator já que as informações estão validas")
	public void testCadastrarUmaAtorComInformcoesValidas() {
		AtorCadastroDto ator = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino", 1534, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(ator, headers);

		ResponseEntity<AtorRetornoDto> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/ator/create", request, AtorRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Ator já que o nome não pode ser nulo")
	public void testCadastrarUmaAtorComNomeNulo() {
		AtorCadastroDto ator = new AtorCadastroDto(null, LocalDate.of(1973, 10, 10), "Masculino", 1534, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(ator, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/ator/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O nome do(a) ator/atriz é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Ator já que a data de nascimento não pode ser nulo")
	public void testCadastrarUmaAtorComDataNascimentoNulo() {
		AtorCadastroDto ator = new AtorCadastroDto("Jhony Depp", null, "Masculino", 1534, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(ator, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/ator/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("A data de nascimento do(a) ator/atriz é obrigatória"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Ator já que o genero não pode ser nulo")
	public void testCadastrarUmaAtorComGeneroNulo() {
		AtorCadastroDto ator = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), null, 1534, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(ator, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/ator/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O genero do(a) ator/atriz é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Ator já que o DRT não pode ser nulo")
	public void testCadastrarUmaAtorComDRTNulo() {
		AtorCadastroDto ator = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino", null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(ator, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/ator/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O numero da DRT do(a) ator/atriz é obrigatória"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Ator já que o Id do pais não pode ser nulo")
	public void testCadastrarUmaAtorComIdPaisNulo() {
		AtorCadastroDto ator = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino", 1534, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(ator, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/ator/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O id do país não foi localizado"));
	}
	
	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Ator já que o Id do pais invalido")
	public void testCadastrarUmaAtorComIdPaisInvalido() {
		AtorCadastroDto ator = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino", 1534, 100l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(ator, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/ator/create", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("Não foi possível encontrar o país com o ID indicado"));
	}

	// TESTES GET

	@Test
	@DisplayName("Deveria listar todos os atores")
	public void testListarTodosOsAtoresDoSistema() {
		criarPais();
		criarAtor();
		ResponseEntity<List<AtorRetornoDto>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<AtorRetornoDto>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria listar o ator com id valido")
	public void testListarUmAtorComIdValido() {
		criarPais();
		criarAtor();
		ResponseEntity<AtorRetornoDto> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/ator/1", AtorRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou o ator com id invalido")
	public void testListarUmAtorComIdInvalido() {
		criarPais();
		criarAtor();
		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/ator/100", String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertTrue(response.getBody().contains("Não foi possivel encontrar do(a) ator/atriz com o ID indicado"));
	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retortar o Ator de ID 1 alterado com sucesso")
	public void testAtualizarUmaAtorComIdValido() {
		criarPais();
		criarAtor();
		AtorCadastroDto atorAtualizado = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino",
				1534, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(atorAtualizado, headers);

		ResponseEntity<AtorRetornoDto> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/update/1", HttpMethod.PUT, request,
				AtorRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(atorAtualizado.getNome(), response.getBody().getNome());
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Ator já que seu nome não pode ser nulo")
	public void testAtualizarUmAtorComNomeNulo() {
		criarPais();
		criarAtor();
		AtorCadastroDto atorAtualizado = new AtorCadastroDto(null, LocalDate.of(1973, 10, 10), "Masculino",
				1534, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(atorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Ator já que sua Data de Nascimento não pode ser nulo")
	public void testAtualizarUmAtorComDataNascimentoNulo() {
		criarPais();
		criarAtor();
		AtorCadastroDto atorAtualizado = new AtorCadastroDto("Jhony Depp", null, "Masculino",
				1534, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(atorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Ator já que seu genero não pode ser nulo")
	public void testAtualizarUmAtorComGeneroNulo() {
		criarPais();
		criarAtor();
		AtorCadastroDto atorAtualizado = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), null,
				1534, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(atorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Ator já que seu DRT não pode ser nulo")
	public void testAtualizarUmAtorComDRTNulo() {
		criarPais();
		criarAtor();
		AtorCadastroDto atorAtualizado = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino",
				null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(atorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Ator já que seu Id do Pais não pode ser nulo")
	public void testAtualizarUmAtorComIdPaisNulo() {
		criarPais();
		criarAtor();
		AtorCadastroDto atorAtualizado = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino",
				1534, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(atorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Ator já que seu Id do Pais invalido")
	public void testAtualizarUmAtorComIdPaisInvalido() {
		criarPais();
		criarAtor();
		AtorCadastroDto atorAtualizado = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino",
				1534, 1000l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(atorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Ator já que seu Id invalido")
	public void testAtualizarUmAtorComIdInvalido() {
		criarPais();
		criarAtor();
		AtorCadastroDto atorAtualizado = new AtorCadastroDto("Jhony Depp", LocalDate.of(1973, 10, 10), "Masculino",
				1534, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AtorCadastroDto> request = new HttpEntity<>(atorAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/update/1000", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}
	
	// TESTES DELETE
	
	@Test
	@DisplayName("Deveria retornar no Content já que o ator foi excluido com sucesso")
	public void testDeletaUmAtorComIdValido() {
		criarPais();
		criarAtor();
		ResponseEntity<AtorRetornoDto> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/1", HttpMethod.DELETE, null,
				AtorRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria retornar not found já que o ator não foi encontrado")
	public void testDeletaUmAtorComIdInvalido() {
		criarPais();
		criarAtor();
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/ator/100", HttpMethod.DELETE, null,
				String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
