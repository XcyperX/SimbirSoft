<#assign
known = Session.SPRING_SECURITY_CONTEXT??
isAdmin = false
isModerator = false
isUser = false
name = ""
role = ""
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    >
    <#if user.isAdmin()>
        <#assign
        isAdmin = user.isAdmin()
        name = user.getUserName()
        role = user.getUserRole()
        >
    <#elseif user.isModerator()>
        <#assign
        isModerator = user.isModerator()
        name = user.getUserName()
        role = user.getUserRole()
        >
    <#elseif user.isUser()>
        <#assign
        isUser = user.isUser()
        name = user.getUserName()
        role = user.getUserRole()
        >
    </#if>
<#else>
    <#assign
    name = ""
    role = ""
    isAdmin = false
    >
</#if>