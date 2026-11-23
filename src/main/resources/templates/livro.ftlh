<#include "cabecalho.ftlh">

<h1>${livro.titulo}</h1>
<p>Autor: ${livro.autor}</p>
<p>Ano: ${livro.ano?c}</p>
<p>Preço: R$ ${livro.preco?c}</p>

<#if usuarioLogado??>
    <p>
        <a href="/livros/${livro.id?c}/editar">Editar</a>
        <a href="/livros/${livro.id?c}/excluir">Excluir</a>
    </p>
</#if>

<p><a href="/livros/html">Voltar ao acervo</a></p>
