-- V4__criar_tabela_usuario.sql
-- Cria a tabela "usuario" (quem pode entrar na área administrativa) e
-- insere a dona da loja. A senha fica em texto puro SÓ nesta etapa didática;
-- guardar senha assim não é aceitável em um sistema real.
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

INSERT INTO usuario (nome, email, senha)
VALUES ('Marta', 'marta@estanteweb.com', 'estante123');
