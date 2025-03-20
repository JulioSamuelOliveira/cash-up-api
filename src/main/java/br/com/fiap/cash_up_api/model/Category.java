package br.com.fiap.cash_up_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity //comunica com o jpa

public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //Id seja a chave primária || O banco de dados que gera o id
    private Long id;
    private String name;
    private String icon;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public void setId(Long id) { //PARA NÃO ALTERAR O ID QUANDO UMA CATEGORIA FOR ALTERADA
        this.id = id;
    }

    @Override // TODA VEZ QUE CHAMAR UMA CATEGORIA ELE VAI APARECER DESSA FORMA
    public String toString() {
        return id + " - " + name + " - " + icon;
    }
}
