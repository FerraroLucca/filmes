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

import br.com.magnasistemas.filme.dtos.AtorCadastroDto;
import br.com.magnasistemas.filme.dtos.AtorRetornoDto;
import br.com.magnasistemas.filme.services.AtorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ator")
public class AtorController {

	private final AtorService service;
	
	public AtorController (AtorService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<AtorRetornoDto>> listaAtores(){
		var ator = service.listarAtores();					
		return ResponseEntity.ok(ator);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AtorRetornoDto> getAtorById(@PathVariable Long id){
		var atores = service.getAtorById(id);
		return ResponseEntity.ok(atores);
	}
	
	@PostMapping("/create")
	public ResponseEntity<AtorRetornoDto> createAtor(@RequestBody @Valid AtorCadastroDto Ator){
		var dto = service.cadastraAtor(Ator);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<AtorRetornoDto> putAtor(
			@RequestBody @Valid AtorCadastroDto Ator,
			@PathVariable Long id){
		var dto = service.putAtor(Ator, id);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletaAtor(@PathVariable Long id){
		service.deletaAtor(id);
		return ResponseEntity.noContent().build();
	}
}
