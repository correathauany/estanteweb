package br.com.estanteweb;

import java.util.Map;

public class Usuario {
    private long id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }

    // Converte o Map de UMA linha da tabela usuario em um objeto Usuario
    public static Usuario deMap(Map<String, Object> linha) {
        Usuario usuario = new Usuario();
        usuario.setId(((Number) linha.get("id")).longValue());
        usuario.setNome((String) linha.get("nome"));
        usuario.setEmail((String) linha.get("email"));
        usuario.setSenha((String) linha.get("senha"));
        return usuario;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
