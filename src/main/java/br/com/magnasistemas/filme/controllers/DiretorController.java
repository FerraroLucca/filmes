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

import br.com.magnasistemas.filme.dtos.DiretorCadastroDto;
import br.com.magnasistemas.filme.dtos.DiretorRetornoDto;
import br.com.magnasistemas.filme.services.DiretorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/diretor")
public class DiretorController {
	
	private final DiretorService service;
	
	public DiretorController (DiretorService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<DiretorRetornoDto>> listaDiretores(){
		var diretores = service.listarDiretores();					
		return ResponseEntity.ok(diretores);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DiretorRetornoDto> getDiretorById(@PathVariable Long id){
		var diretores = service.getDiretorById(id);
		return ResponseEntity.ok(diretores);
	}
	
	@PostMapping("/create")
	public ResponseEntity<DiretorRetornoDto> createDiretor(@RequestBody @Valid DiretorCadastroDto diretor){
		var dto = service.cadastraDiretor(diretor);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<DiretorRetornoDto> putDiretor(
			@RequestBody @Valid DiretorCadastroDto diretor,
			@PathVariable Long id){
		var dto = service.putDiretor(diretor, id);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletaDiretor(@PathVariable Long id){
		service.deletaDiretor(id);
		return ResponseEntity.noContent().build();
	}
}
