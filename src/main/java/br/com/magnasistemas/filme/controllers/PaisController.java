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

import br.com.magnasistemas.filme.dtos.PaisCadastroDto;
import br.com.magnasistemas.filme.dtos.PaisRetornoDto;
import br.com.magnasistemas.filme.services.PaisService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pais")
public class PaisController {
	
	private final PaisService service;
	
	public PaisController (PaisService service) {
		this.service = service;
	}

	
	
	
	@GetMapping("/")
	public ResponseEntity<List<PaisRetornoDto>> listaPaises(){
		var paises = service.listarPaises();
		return ResponseEntity.ok(paises);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PaisRetornoDto> getPaisById(@PathVariable Long id){
		var pais = service.getPaisById(id);
		return ResponseEntity.ok(pais);
	}
	
	@PostMapping("/create")
	public ResponseEntity<PaisRetornoDto> createPais(@RequestBody @Valid PaisCadastroDto pais){
		var dto = service.cadastraPais(pais);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletaPais(@PathVariable Long id){
		service.deletaPais(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<PaisRetornoDto> putPais(
			@RequestBody @Valid PaisCadastroDto pais,
			@PathVariable Long id){
		var dto = service.putPais(pais, id);
		return ResponseEntity.ok(dto);
	}	
	
}
