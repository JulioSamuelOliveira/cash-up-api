package br.com.fiap.cash_up_api.model;

import java.util.Random;

public class Category {
    private Long id;
    private String name;
    private String icon;

    //construtores
    public Category(Long id, String name, String icon) {
        this.id = Math.abs(new Random().nextLong());
        this.name = name;
        this.icon = icon;
    }

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
