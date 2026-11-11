<#include "cabecalho.ftlh">

<h1>Remover livro</h1>

<p>Tem certeza que deseja remover este livro?</p>
<p><strong>${livro.titulo}</strong>, de ${livro.autor} (${livro.ano?c})</p>

<form method="post" action="/livros/${livro.id?c}/excluir">
    <button type="submit">Sim, remover</button>
</form>

<a href="/livros/${livro.id?c}">Não, voltar</a>