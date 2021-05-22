<#import "login.ftlh" as l>
<#include "security.ftlh">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand">Чатик</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
        </ul>
        <div class="navbar-text mr-3">${name}</div>
        <div class="navbar-text mr-3">${role}</div>
        <form action="/logout" method="post">
            <#if isAdmin || isModerator || isUser>
                <input type="submit" class="btn btn-primary" value="Выйти"/>
            <#else>
                <input type="submit" class="btn btn-primary" value="Войти"/>
            </#if>
        </form>
    </div>
</nav>