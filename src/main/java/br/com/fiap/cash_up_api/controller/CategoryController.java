package br.com.fiap.cash_up_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import br.com.fiap.cash_up_api.model.User;
import br.com.fiap.cash_up_api.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @GetMapping
    @Cacheable("categories")
    @Operation(description = "Listar todas as categorias", tags = "categories", summary = "Lista de categorias")
    public List<Category> index(@AuthenticationPrincipal User user) {
        log.info("Buscando todas categorias");
        return repository.findByUser(user);
    }

    @PostMapping
    @CacheEvict(value = "categories", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")
    })
    public Category create(@RequestBody @Valid Category category, @AuthenticationPrincipal User user) {
        log.info("Cadastrando categoria " + category.getName());
        category.setUser(user);
        return repository.save(category);
    }

    @GetMapping("{id}")
    public Category get(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Buscando categoria " + id);
        checkPermission(id, user);
        return getCategory(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Apagando categoria " + id);
        checkPermission(id, user);
        repository.delete(getCategory(id));
    }

    @PutMapping("{id}")
    public Category update(@PathVariable Long id, @RequestBody @Valid Category category, @AuthenticationPrincipal User user) {
        log.info("Atualizando categoria " + id + " " + category);
        checkPermission(id, user);
        category.setId(id);
        category.setUser(user);
        return repository.save(category);
    }

    private void checkPermission(Long id, User user) {
        var categoryOld = getCategory(id);
        if(!categoryOld.getUser().equals(user)) 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    private Category getCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Categoria não encontrada"));
    }

}