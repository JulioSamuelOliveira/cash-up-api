package br.com.fiap.cash_up_api.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import br.com.fiap.cash_up_api.model.Category;
import br.com.fiap.cash_up_api.repository.CategoryRepository;

@RestController // component
@RequestMapping("/categories") // para não gerar repetição no url

public class CategoryController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired // injeção de dependência
    private CategoryRepository repository;
    
    
    //listar todas as categorias
    // GET :8080/categories -> json
    @GetMapping
    public List<Category> index(){ //mochy
        log.info("Buscando todas as categorias");
        return repository.findAll();
    }

    //cadastrar categorias
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // @ResponseStatus(code = HttpStatus.CREATED)
    public Category create(@RequestBody Category category){
        log.info("Cadastrando categoria " + category.getName());
        return repository.save(category);
    }

    //retornar uma categoria
    @GetMapping("/{id}")
    public Category get(@PathVariable Long id){
        log.info("Buscando categoria " + id);
        return (getCategory(id));
    }

    //endpoint para apagar categoria
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id){
        log.info("Apagando categoria " + id);
        repository.delete(getCategory(id));//noContenet status 204 
    }

    //editar categorias
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category){
        log.info("Atualizando categoria " + id + " " + category);
        
        getCategory(id);
        category.setId(id); //para não alterar o Id
        return repository.save(category);
    }

    private Category getCategory(Long id) {
        return repository.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada") 
        );
    }
}
