<#include "cabecalho.ftlh">

<h1>Corrigir livro</h1>

<#if erro??>
    <p style="color: red;">${erro}</p>
</#if>

<form method="post" action="/livros/${livro.id?c}">
    <p>
        <label>Título:
            <input type="text" name="titulo" value="${livro.titulo}">
        </label>
    </p>
    <p>
        <label>Autor:
            <input type="text" name="autor" value="${livro.autor}">
        </label>
    </p>
    <p>
        <label>Ano:
            <input type="text" name="ano" value="${livro.ano?c}">
        </label>
    </p>
    <p>
        <label>Preço:
            <input type="text" name="preco" value="${livro.preco?c}">
        </label>
    </p>
    <button type="submit">Salvar</button>
</form>

<a href="/livros/${livro.id?c}">Cancelar</a>