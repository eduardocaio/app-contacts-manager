package com.eduardocaio.controle_contatos.entidades;

import java.io.Serializable;

public class Contato implements Serializable {

    private int id;
    private String nome;
    private String telefone;
    private String email;

    public Contato(String email, String telefone, String nome) {
        this.email = email;
        this.telefone = telefone;
        this.nome = nome;
    }

    public Contato(int id, String email, String telefone, String nome) {
        this.email = email;
        this.telefone = telefone;
        this.nome = nome;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
