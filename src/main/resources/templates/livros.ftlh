<#include "cabecalho.ftlh">

<h1>Acervo Estante Web: </h1>

<#if usuarioLogado??>
    <p><a href="/livros/novo">Cadastrar livro novo</a></p>
</#if>

<#if livros?size == 0>
    <p>Nenhum livro foi encontrado no acervo.</p>
<#else>
<ul>
    <#list livros as livro>
        <li><a href="/livros/${livro.id?c}">${livro.titulo}</a>, de ${livro.autor} (R$ ${livro.preco?c} ) |</li>
    </#list>
</ul>
</#if>
