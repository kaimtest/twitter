<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <@l.login "/login" false/>
${message?ifExists}
</@c.page>