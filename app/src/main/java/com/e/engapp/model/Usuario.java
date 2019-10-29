package com.e.engapp.model;

import java.util.Date;

public class Usuario {
    private String ID;
    private String nome;
    private String email;
    private String senha;
    private Date cadastro;

    public Usuario(String nome, String email, String senha, Date cadastro) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cadastro = cadastro;
    }


    public Usuario(String ID, String nome, String email, String senha, Date cadastro) {
        this.ID = ID;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cadastro = cadastro;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getCadastro() {
        return cadastro;
    }

    public void setCadastro(Date cadastro) {
        this.cadastro = cadastro;
    }
}
