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

import br.com.magnasistemas.filme.dtos.AtorFilmeDto;
import br.com.magnasistemas.filme.dtos.FilmeCadastroDto;
import br.com.magnasistemas.filme.dtos.FilmeRetornoDto;
import br.com.magnasistemas.filme.services.FilmeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/filme")
public class FilmeController {
	
	private final FilmeService service;
	
	public FilmeController (FilmeService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<FilmeRetornoDto>> listafilmees(){
		var filme = service.listarFilmes();					
		return ResponseEntity.ok(filme);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FilmeRetornoDto> getfilmeById(@PathVariable Long id){
		var filmees = service.getFilmeById(id);
		return ResponseEntity.ok(filmees);
	}
	
	@PostMapping("/create")
	public ResponseEntity<FilmeRetornoDto> createfilme(@RequestBody @Valid FilmeCadastroDto filme){
		var dto = service.cadastrarFilme(filme);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<FilmeRetornoDto> putfilme(
			@RequestBody @Valid FilmeCadastroDto filme,
			@PathVariable Long id){
		var dto = service.putFilme(filme, id);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletafilme(@PathVariable Long id){
		service.deletaFilme(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/adicionaAtor")
	public ResponseEntity<FilmeRetornoDto> adicionaAtor(@RequestBody @Valid AtorFilmeDto atorFilme){
		var dto = service.adicionarAtor(atorFilme);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
}
