package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController //controlador rest, o retorno já é facilitado, converte para json
@RequestMapping("/categorias") //faz o mapeamento da requisição
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Categoria> categorias = categoriaRepository.findAll();
		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
		//se a lista não estiver vazia, responde com 200 ok passando as categorias, caso esteja vazia, retorna
		//nocontent status code 204, ou seja, deu certo no servidor mas não tem nada para mostrar. 
		//o build é colocado para gerar um responseentity
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva); //retorna a categoria criada
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
	Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
	return categoria.isPresent() ? 
	        ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	//ao invés de ficarmos checando manualmente de objeto é null ou não, o Optional nos dá algumas facilidades.
	//Neste caso utilizamos o método isPresent, que nada mais é que uma comparação “obj != null”, e finalizamos com um ternário
	}
}
