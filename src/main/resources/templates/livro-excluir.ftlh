<#include "cabecalho.ftlh">

<h1>Excluir livro</h1>

<p>Tem certeza de que deseja excluir <strong>${livro.titulo}</strong>, de ${livro.autor}?</p>

<form method="post" action="/livros/${livro.id?c}/excluir">
    <button type="submit">Sim, excluir</button>
    <a href="/livros/${livro.id?c}">Cancelar</a>
</form>
