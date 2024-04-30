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
import br.com.magnasistemas.filme.dtos.ProdutoraCadastroDto;
import br.com.magnasistemas.filme.dtos.ProdutoraRetornoDto;
import br.com.magnasistemas.filme.models.PaisModel;
import br.com.magnasistemas.filme.repositories.PaisRepository;
import br.com.magnasistemas.filme.repositories.ProdutoraRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProdutoraControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int randomServerPort;
	
	@Autowired
	private PaisRepository paisRepository;
	
	@Autowired
	private ProdutoraRepository produtoraRepository;
	
	@BeforeEach
	public void inicializar() {
		criarPais();
		criarProdutora();
	}
	
	@AfterEach
	public void finalizar() {
		produtoraRepository.LimparDadosERedefinirSequence();
		paisRepository.LimparDadosERedefinirSequence();
	}
	
	public void criarPais() {
		PaisCadastroDto pais = new PaisCadastroDto("Brasil", "Brasília", "América", "Português");
		restTemplate.postForEntity("http://localhost:"+randomServerPort+"/pais/create", pais, PaisModel.class);
	}
	
	public void criarProdutora() {
		ProdutoraCadastroDto produtora = new ProdutoraCadastroDto("Marvel", 1923, 1l);
		restTemplate.postForEntity("http://localhost:"+randomServerPort+"/produtora/create", produtora, PaisModel.class);
	}
	
	
	// TESTES POST
	
		@Test
		@DisplayName("Deveria cadastrar uma Produtora já que as informações estão validas")
		public void testCadastrarUmaProdutoraComInformcoesValidas() {
			ProdutoraCadastroDto produtora = new ProdutoraCadastroDto("Marvel", 1923, 1l);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtora, headers);
			
			ResponseEntity<ProdutoraRetornoDto> response = restTemplate
					.postForEntity("http://localhost:"+randomServerPort+"/produtora/create", request, ProdutoraRetornoDto.class);
			
			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.CREATED, statusCode);
			Assert.assertNotNull(response.getBody());
		}
		
		@Test
		@DisplayName("Deveria dar erro cadastrar uma Produtora já que o Nome Fantasia da produtora não pode ser nulo")
		public void testCadastrarUmProdutoraComNomeNulo() {
			ProdutoraCadastroDto produtora = new ProdutoraCadastroDto(null, 1923, 1l);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtora, headers);
			
			ResponseEntity<String> response = restTemplate
					.postForEntity("http://localhost:"+randomServerPort+"/produtora/create", request, String.class);
			
			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
			Assert.assertNotNull(response.getBody());
			Assert.assertTrue(response.getBody().contains("O nome do produtora é obrigatório"));
		}
		
		@Test
		@DisplayName("Deveria dar erro cadastrar uma Produtora já que o Ano de Fundação da produtora não pode ser nulo")
		public void testCadastrarUmProdutoraComAnoFundacaoNulo() {
			ProdutoraCadastroDto produtora = new ProdutoraCadastroDto("Marvel", null, 1l);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtora, headers);
			
			ResponseEntity<String> response = restTemplate
					.postForEntity("http://localhost:"+randomServerPort+"/produtora/create", request, String.class);
			
			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
			Assert.assertNotNull(response.getBody());
			Assert.assertTrue(response.getBody().contains("O ano de fundação é obrigatório"));
		}
	
		@Test
		@DisplayName("Deveria dar erro cadastrar uma Produtora já que o Id do Pais da produtora não pode ser nulo")
		public void testCadastrarUmProdutoraComIdPaisNulo() {
			ProdutoraCadastroDto produtora = new ProdutoraCadastroDto("Marvel", 1923, null);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtora, headers);
			
			ResponseEntity<String> response = restTemplate
					.postForEntity("http://localhost:"+randomServerPort+"/produtora/create", request, String.class);
			
			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
			Assert.assertNotNull(response.getBody());
			Assert.assertTrue(response.getBody().contains("O id do país não foi localizado"));
		}
		
		@Test
		@DisplayName("Deveria dar erro cadastrar uma Produtora já que o Id do Pais da produtora é inválido")
		public void testCadastrarUmProdutoraComIdPaisInvalido() {
			ProdutoraCadastroDto produtora = new ProdutoraCadastroDto("Marvel", 1923, 100l);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtora, headers);
			
			ResponseEntity<String> response = restTemplate
					.postForEntity("http://localhost:"+randomServerPort+"/produtora/create", request, String.class);
			
			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
			Assert.assertNotNull(response.getBody());
			Assert.assertTrue(response.getBody().contains("Não foi possível encontrar o país com o ID indicado"));
		}
	
	
		// TESTES GET

		@Test
		@DisplayName("Deveria listar todos as produtoras")
		public void testListarTodosAsProdutorasDoSistema() {
			criarPais();
			criarProdutora();
			ResponseEntity<List<ProdutoraRetornoDto>> response = restTemplate.exchange(
					"http://localhost:" + randomServerPort + "/produtora/", HttpMethod.GET, null,
					new ParameterizedTypeReference<List<ProdutoraRetornoDto>>() {
					});

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.OK, statusCode);
			Assert.assertNotNull(response.getBody());
		}
		
		@Test
		@DisplayName("Deveria listar a produtora com id valido")
		public void testListarUmaProdutoraComIdValido() {
			criarPais();
			criarProdutora();
			ResponseEntity<ProdutoraRetornoDto> response = restTemplate
					.getForEntity("http://localhost:" + randomServerPort + "/produtora/1", ProdutoraRetornoDto.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.OK, statusCode);
			Assert.assertNotNull(response.getBody());
		}
		
		@Test
		@DisplayName("Deveria dar not found pois não achou a produtora com id invalido")
		public void testListarUmaProdutoraComIdInvalido() {
			criarPais();
			criarProdutora();
			ResponseEntity<String> response = restTemplate
					.getForEntity("http://localhost:" + randomServerPort + "/produtora/100", String.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
			Assert.assertTrue(response.getBody().contains("Não foi possivel encontrar o diretor com o ID indicado"));
		}
	
	
		// TESTES PUT
	
		@Test
		@DisplayName("Deveria retortar a Produtora de ID 1 alterado com sucesso")
		public void testAtualizarUmaProdutoraComIdValido() {
			criarPais();
			criarProdutora();
			ProdutoraCadastroDto produtoraAtualizado = new ProdutoraCadastroDto("Marvel", 1923, 1l);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtoraAtualizado, headers);

			ResponseEntity<ProdutoraRetornoDto> response = restTemplate.exchange(
					"http://localhost:" + randomServerPort + "/produtora/update/1", HttpMethod.PUT, request, ProdutoraRetornoDto.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.OK, statusCode);
			Assert.assertNotNull(response.getBody());
			Assert.assertEquals(produtoraAtualizado.getNomeFantasia(), response.getBody().getNomeFantasia());
		}
		
		@Test
		@DisplayName("Deveria dar erro ao atualizar uma Produtora já que seu nome não pode ser nulo")
		public void testAtualizarUmaProdutoraComNomeNulo() {
			criarPais();
			criarProdutora();
			ProdutoraCadastroDto produtoraAtualizado = new ProdutoraCadastroDto(null, 1923, 1l);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtoraAtualizado, headers);

			ResponseEntity<String> response = restTemplate.exchange(
					"http://localhost:" + randomServerPort + "/produtora/update/1", HttpMethod.PUT, request, String.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		}
		
		@Test
		@DisplayName("Deveria dar erro ao atualizar uma Produtora já que seu ano de fundação não pode ser nulo")
		public void testAtualizarUmaProdutoraComAnoNulo() {
			criarPais();
			criarProdutora();
			ProdutoraCadastroDto produtoraAtualizado = new ProdutoraCadastroDto("Marvel", null, 1l);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtoraAtualizado, headers);

			ResponseEntity<String> response = restTemplate.exchange(
					"http://localhost:" + randomServerPort + "/produtora/update/1", HttpMethod.PUT, request, String.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		}
		
		@Test
		@DisplayName("Deveria dar erro ao atualizar uma Produtora já que seu Id de Pais não pode ser nulo")
		public void testAtualizarUmaProdutoraComPaisIdNulo() {
			criarPais();
			criarProdutora();
			ProdutoraCadastroDto produtoraAtualizado = new ProdutoraCadastroDto("Marvel", 1923, null);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtoraAtualizado, headers);

			ResponseEntity<String> response = restTemplate.exchange(
					"http://localhost:" + randomServerPort + "/produtora/update/1", HttpMethod.PUT, request, String.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		}
		
		@Test
		@DisplayName("Deveria dar erro ao atualizar uma Produtora já que seu Id de Pais é inválido")
		public void testAtualizarUmaProdutoraComPaisIdInvalido() {
			criarPais();
			criarProdutora();
			ProdutoraCadastroDto produtoraAtualizado = new ProdutoraCadastroDto("Marvel", 1923, 100l);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtoraAtualizado, headers);

			ResponseEntity<String> response = restTemplate.exchange(
					"http://localhost:" + randomServerPort + "/produtora/update/1", HttpMethod.PUT, request, String.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		}
		
		@Test
		@DisplayName("Deveria dar erro ao atualizar uma Produtora já que seu Id é inválido")
		public void testAtualizarUmaProdutoraComIdInvalido() {
			criarPais();
			criarProdutora();
			ProdutoraCadastroDto produtoraAtualizado = new ProdutoraCadastroDto("Marvel", 1923, 1l);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<ProdutoraCadastroDto> request = new HttpEntity<>(produtoraAtualizado, headers);

			ResponseEntity<String> response = restTemplate.exchange(
					"http://localhost:" + randomServerPort + "/produtora/update/100", HttpMethod.PUT, request, String.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		}
		
		// TESTES DELETE
		
		@Test
		@DisplayName("Deveria retornar no Content já que o pais foi excluido com sucesso")
		public void testDeletaUmPaisComIdValido() {
			criarPais();
			criarProdutora();
			ResponseEntity<ProdutoraRetornoDto> response = restTemplate.exchange(
					"http://localhost:" + randomServerPort + "/produtora/1", HttpMethod.DELETE, null, ProdutoraRetornoDto.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
			Assert.assertNull(response.getBody());
		}
		
		@Test
		@DisplayName("Deveria retornar not found já que a produtora não foi encontrado")
		public void testDeletaUmaProdutoraComIdInvalido() {
			criarPais();
			criarProdutora();
			ResponseEntity<String> response = restTemplate.exchange(
					"http://localhost:" + randomServerPort + "/produtora/100", HttpMethod.DELETE, null, String.class);

			HttpStatusCode statusCode = response.getStatusCode();
			Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
			Assert.assertNotNull(response.getBody());
		}
		
	
	
	
	
	
	
	
	
}
