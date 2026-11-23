<header>
    <h1>Loja EstanteWeb</h1>
    <a href="/"><button type="button">Voltar para Início</button></a>
    <#if usuarioLogado??>
        <span>Olá, ${usuarioLogado.nome}</span>
        <form method="post" action="/logout" style="display: inline;">
            <button type="submit">Sair</button>
        </form>
    <#else>
        <a href="/login">Entrar</a>
    </#if>
</header>
