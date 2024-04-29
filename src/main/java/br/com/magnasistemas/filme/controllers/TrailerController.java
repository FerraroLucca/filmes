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

import br.com.magnasistemas.filme.dtos.TrailerCadastroDto;
import br.com.magnasistemas.filme.dtos.TrailerRetornoDto;
import br.com.magnasistemas.filme.services.TrailerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/trailer")
public class TrailerController {

	private final TrailerService service;
	
	public TrailerController (TrailerService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TrailerRetornoDto>> listarTrailers(){
		var trailers = service.listarTrailers();					
		return ResponseEntity.ok(trailers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TrailerRetornoDto> getTrailerById(@PathVariable Long id){
		var trailers = service.gettrailerById(id);
		return ResponseEntity.ok(trailers);
	}
	
	@PostMapping("/create")
	public ResponseEntity<TrailerRetornoDto> createtrailer(@RequestBody @Valid TrailerCadastroDto trailer){
		var dto = service.cadastraTrailer(trailer);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<TrailerRetornoDto> putTrailer(
			@RequestBody @Valid TrailerCadastroDto trailer,
			@PathVariable Long id){
		var dto = service.putTrailer(trailer, id);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletatrailer(@PathVariable Long id){
		service.deletaTrailer(id);
		return ResponseEntity.noContent().build();
	}
}

