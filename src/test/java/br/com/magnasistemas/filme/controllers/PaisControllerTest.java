package br.com.magnasistemas.filme.controllers;

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

import br.com.magnasistemas.filme.dtos.PaisCadastroDto;
import br.com.magnasistemas.filme.dtos.PaisRetornoDto;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.repositories.PaisRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PaisControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int randomServerPort;
	
	@Autowired
	private PaisRepository paisRepository;
	
	@BeforeEach
	public void inicializar() {
		criarPais();
	}
	
	@AfterEach
	public void finalizar() {
		paisRepository.LimparDadosERedefinirSequence();
	}
	
	public void criarPais() {
		PaisCadastroDto pais = new PaisCadastroDto("Brasil", "Brasília", "América", "Português");
		restTemplate.postForEntity("http://localhost:"+randomServerPort+"/pais/create", pais, PaisModel.class);
	}
	
	// TESTES POST
	
	@Test
	@DisplayName("Deveria cadastrar um Pais já que as informações estão validas")
	public void testCadastrarUmPaisComInformcoesValidas() {
		PaisCadastroDto pais = new PaisCadastroDto("Brasil", "Brasília", "América", "Português");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(pais, headers);
		
		ResponseEntity<PaisRetornoDto> response = restTemplate
				.postForEntity("http://localhost:"+randomServerPort+"/pais/create", request, PaisRetornoDto.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar erro cadastrar um Pais já que o nome do pais não pode ser nulo")
	public void testCadastrarUmPaisComNomeNulo() {
		PaisCadastroDto pais = new PaisCadastroDto(null, "Brasília", "América", "Português");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(pais, headers);
		
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:"+randomServerPort+"/pais/create", request, String.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O nome do país é obrigatório"));
	}
	
	@Test
	@DisplayName("Deveria dar erro cadastrar um Pais já que a capital do pais não pode ser nulo")
	public void testCadastrarUmPaisComCapitalNulo() {
		PaisCadastroDto pais = new PaisCadastroDto("Brasil", null, "América", "Português");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(pais, headers);
		
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:"+randomServerPort+"/pais/create", request, String.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O nome da capital é obrigatório"));
	}
	
	@Test
	@DisplayName("Deveria dar erro cadastrar um Pais já que o continente do pais não pode ser nulo")
	public void testCadastrarUmPaisComContinenteNulo() {
		PaisCadastroDto pais = new PaisCadastroDto("Brasil", "Brasilia", null, "Português");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(pais, headers);
		
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:"+randomServerPort+"/pais/create", request, String.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O nome do continente é obrigatório"));
	}
	
	@Test
	@DisplayName("Deveria dar erro cadastrar um Pais já que a lingua do pais não pode ser nulo")
	public void testCadastrarUmPaisComLinguaNulo() {
		PaisCadastroDto pais = new PaisCadastroDto("Brasil", "Brasilia", "America", null);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(pais, headers);
		
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:"+randomServerPort+"/pais/create", request, String.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().contains("O nome da lingua é obrigatório"));
	}
	
	
	// TESTES GET

	@Test
	@DisplayName("Deveria listar todos os paises")
	public void testListarTodosOsPaisesDoSistema() {
		criarPais();
		ResponseEntity<List<PaisRetornoDto>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/pais/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PaisRetornoDto>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria listar o pais com id valido")
	public void testListarUmPaisComIdValido() {
		criarPais();
		ResponseEntity<PaisRetornoDto> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/pais/1", PaisRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar not found pois não achou o pais com id invalido")
	public void testListarUmPaisComIdInvalido() {
		criarPais();
		ResponseEntity<String> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/pais/100", String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertTrue(response.getBody().contains("não foi possivel encontrar o país com o ID indicado"));
	}
	
	
	// TESTES PUT
	
	@Test
	@DisplayName("Deveria retortar o Pais de ID 1 alterado com sucesso")
	public void testAtualizarUmPaisComIdValido() {
		criarPais();
		PaisCadastroDto paisAtualizado = new PaisCadastroDto("China", "Pequim", "Asia", "Chines");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(paisAtualizado, headers);

		ResponseEntity<PaisRetornoDto> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/pais/update/1", HttpMethod.PUT, request, PaisRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(paisAtualizado.getNome(), response.getBody().getNome());
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Pais já que seu nome não pode ser nulo")
	public void testAtualizarUmPaisComNomeNulo() {
		criarPais();
		PaisCadastroDto paisAtualizado = new PaisCadastroDto(null, "Pequim", "Asia", "Chines");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(paisAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/pais/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Pais já que sua Capital não pode ser nulo")
	public void testAtualizarUmPaisComCapitalNulo() {
		criarPais();
		PaisCadastroDto paisAtualizado = new PaisCadastroDto("China", null, "Asia", "Chines");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(paisAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/pais/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Pais já que seu Continente não pode ser nulo")
	public void testAtualizarUmPaisComContinenteNulo() {
		criarPais();
		PaisCadastroDto paisAtualizado = new PaisCadastroDto("China", "Pequim", null, "Chines");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(paisAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/pais/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Pais já que sua Linguagem não pode ser nulo")
	public void testAtualizarUmPaisComLinguagemNulo() {
		criarPais();
		PaisCadastroDto paisAtualizado = new PaisCadastroDto("China", "Pequim", "Asia", null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(paisAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/pais/update/1", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Pais já que seu ID não pode ser nulo")
	public void testAtualizarUmPaisComIdInvalido() {
		criarPais();
		PaisCadastroDto paisAtualizado = new PaisCadastroDto("China", "Pequim", "Asia", "Chines");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PaisCadastroDto> request = new HttpEntity<>(paisAtualizado, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/pais/update/100", HttpMethod.PUT, request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}
	
	
	// TESTES DELETE
	
	@Test
	@DisplayName("Deveria retornar no Content já que o pais foi excluido com sucesso")
	public void testDeletaUmPaisComIdValido() {
		criarPais();
		ResponseEntity<PaisRetornoDto> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/pais/1", HttpMethod.DELETE, null, PaisRetornoDto.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria retornar not found já que o pais não foi encontrado")
	public void testDeletaUmPaisComIdInvalido() {
		criarPais();
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/pais/100", HttpMethod.DELETE, null, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNotNull(response.getBody());
	}

}
