package com.generation.farmacia.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.generation.farmacia.Model.ProdutoModel;
import com.generation.farmacia.repository.ProdutoRepository;


@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders ="*") 

public class ProdutoController {
	
	private ProdutoRepository repository;
	
	@Autowired 
	public void setRepository(ProdutoRepository repository) {
		this.repository=repository;
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoModel>> getAll(){
	return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<ProdutoModel> GetById(@PathVariable long id){
	    return repository.findById(id).map(resp -> ResponseEntity.ok(resp)) 
	    		.orElse(ResponseEntity.notFound().build());			       
	}
	
	@GetMapping("/titulo/{titulo}") 
	public ResponseEntity<List<ProdutoModel>> GetByTitulo(@PathVariable String titulo){ 
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	@PostMapping  
	public ResponseEntity <ProdutoModel>post(@RequestBody ProdutoModel titulo){ 
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(titulo)); 
	}

	@PutMapping 
	public ResponseEntity <ProdutoModel>put(@RequestBody ProdutoModel categoria){ 
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(categoria));
	}
	
	@DeleteMapping ("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
	
}

