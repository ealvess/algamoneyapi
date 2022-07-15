package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController //controlador rest, o retornor já é facilitado, converte para json
@RequestMapping("/categorias") //faz o mapeamento da requisição
public class CategoriaResource {
	
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
}
