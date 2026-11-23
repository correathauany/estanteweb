<#include "cabecalho.ftlh">

<h1>Entrar</h1>

<#if erro??>
    <p style="color: red;">${erro}</p>
</#if>

<form method="post" action="/login">
    <p>
        <label>E-mail:
            <input type="email" name="email" value="${email!}" required>
        </label>
    </p>
    <p>
        <label>Senha:
            <input type="password" name="senha" required>
        </label>
    </p>
    <button type="submit">Entrar</button>
</form>
