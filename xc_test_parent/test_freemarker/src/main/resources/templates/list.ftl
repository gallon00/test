<#list allUser as user>
    ${user_index + 1} -- ${user.username} -- ${user.password} -- ${user.age} <br/>
</#list>