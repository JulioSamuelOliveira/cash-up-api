package br.com.fiap.cash_up_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.cash_up_api.model.Category;

@RestController // component
public class CategoryController {
    private List<Category> repository = new ArrayList<>();
    // GET :8080/categories -> json
    @GetMapping("/categories")
    public List<Category> index(){ //mochy
        return repository;
    }

    //cadastrar categorias
    @PostMapping("/categories")
    // @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Category> create(@RequestBody Category category){
        System.out.println("Cadastrando Categoria" + category.getName());
        repository.add(category);
        return ResponseEntity.status(201).body(category);
    }

    //retornar uma categoria
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id){
        System.out.println("Buscando Categoria " + id);
        var category = repository.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst();

        //nunca chamar um metodo get sem antes ter verificado se ele esta presente
        if (category.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(category.get());
    }


    //apagar categoriaa
    @DeleteMapping("/categories")
    public void delete(){
        System.out.println("Deletando Categoria");
    }

    //editar categorias
}
