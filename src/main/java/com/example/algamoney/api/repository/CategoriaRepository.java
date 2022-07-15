package com.example.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	//criou-se essa interface CategoriaRepository que extende essa outra interface
	//JpaRepository, que oferece vários métodos como findall, save, delete, dentre outros
	//que ja vem prontos nessa interface e a implementação quem dá é o spring data jpa
	//então para varias criações, cruds é muito mais fácil trabalhar com essa implementações
}
