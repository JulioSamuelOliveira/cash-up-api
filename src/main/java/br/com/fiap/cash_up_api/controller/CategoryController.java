package br.com.fiap.cash_up_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cash_up_api.model.Category;

@RestController // component
public class CategoryController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Category> repository = new ArrayList<>();
    
    
    //listar todas as categorias
    // GET :8080/categories -> json
    @GetMapping("/categories")
    public List<Category> index(){ //mochy
        log.info("Buscando todas as categorias");
        return repository;
    }

    //cadastrar categorias
    @PostMapping("/categories")
    // @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Category> create(@RequestBody Category category){
        log.info("Cadastrando categoria " + category.getName());
        repository.add(category);
        return ResponseEntity.status(201).body(category);
    }

    //retornar uma categoria
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id){
        log.info("Buscando categoria " + id);
        return ResponseEntity.ok(getCategory(id));
    }

    //endpoint para apagar categoria
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        log.info("Apagando categoria " + id);
        repository.remove(getCategory(id));
        return ResponseEntity.noContent().build(); //noContenet status 204 
    }

    //editar categorias
    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category){
        log.info("Atualizando categoria " + id + " " + category);
        
        var categoryToUpdate = getCategory(id);
        repository.remove(categoryToUpdate);
        category.setId(id); //para não alterar o Id
        repository.add(category);
        return ResponseEntity.ok(category);
    }

    private Category getCategory(Long id) {
        return repository.stream()
        .filter(c -> c.getId().equals(id))
        .findFirst()
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada") 
        );
    }
}
