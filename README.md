# EstanteWeb

Livraria online de livros usados (Programação para Web 3).
Spring Boot 4.1, Java 21, Freemarker, JdbcTemplate, Flyway e MySQL.

## Como rodar

1. Tenha o MySQL ligado em `localhost:3306`.
2. Em `src/main/resources/application.properties`, ajuste `spring.datasource.password` para a senha do seu MySQL (usuário `root`). O banco `estanteweb` é criado sozinho.
3. Na raiz do projeto, rode:
   - Windows: `mvnw.cmd spring-boot:run`
   - Linux/macOS: `./mvnw spring-boot:run`
   - ou use o botão de executar do VS Code (`EstantewebApplication`).
4. Abra http://localhost:8080/livros/html

As migrações `V1` a `V4` (livro, sugestão, livros iniciais e usuário) rodam sozinhas na primeira subida.

## Login (atividade Sessão e Login)

- E-mail: `marta@estanteweb.com`
- Senha: `estante123`

Sem login, o visitante vê o acervo e envia sugestões. Cadastrar, editar e excluir livros exige login.

## Rotas principais

| Rota | Acesso |
|---|---|
| `/livros/html`, `/livros/{id}`, `/sugestoes`, `/sugestoes/nova`, `/contato` | público |
| `/login` (GET e POST), `/logout` (POST) | público |
| `/livros/novo`, `POST /livros`, `/livros/{id}/editar`, `POST /livros/{id}`, `/livros/{id}/excluir` (GET e POST) | só logado |

## Se a aplicação não subir (erro de banco)

O erro aparece como `HikariPool-1 - Starting...` seguido de uma pilha de erros (`HikariPool`, `Flyway`). A causa fica na linha `Caused by:` mais acima. Os casos mais comuns:

| Mensagem | O que fazer |
|---|---|
| `Communications link failure` / `Connection refused` | O MySQL não está ligado. Rode `sudo systemctl start mysql` (ou `mariadb`) e confira com `systemctl status mysql`. |
| `Access denied for user 'root'` | Usuário ou senha errados. Ajuste `DB_USER` e `DB_PASSWORD` (ou o `application.properties`). Teste com `mysql -u root -p`. |
| `Unknown database` / sem permissão para criar | Crie o banco: `CREATE DATABASE estanteweb;` |

Para ver só a causa, rode no terminal:

    ./mvnw spring-boot:run 2>&1 | grep -E "Caused by|Access denied|refused"
