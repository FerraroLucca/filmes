package br.com.magnasistemas.filme.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.filme.dtos.ProdutoraCadastroDto;
import br.com.magnasistemas.filme.dtos.ProdutoraRetornoDto;
import br.com.magnasistemas.filme.services.ProdutoraService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtora")
public class ProdutoraController {

	private final ProdutoraService service;

	public ProdutoraController(ProdutoraService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ProdutoraRetornoDto>> listaProdutoras(){
		var produtoras = service.listarProdutoras();
		return ResponseEntity.ok(produtoras);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoraRetornoDto> getProdutoraById(@PathVariable Long id){
		var produtoras = service.getProdutoraById(id);
		return ResponseEntity.ok(produtoras);
	}
	
	@PostMapping("/create")
	public ResponseEntity<ProdutoraRetornoDto> createProdutora(@RequestBody @Valid ProdutoraCadastroDto produtora){
		var dto = service.cadastraProdutora(produtora);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ProdutoraRetornoDto> putProdutora(
			@RequestBody @Valid ProdutoraCadastroDto produtora,
			@PathVariable Long id){
		var dto = service.putProdutora(produtora, id);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletaProdutora(@PathVariable Long id){
		service.deletaProdutora(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
