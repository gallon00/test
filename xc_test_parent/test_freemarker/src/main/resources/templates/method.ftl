${list?size} <br/>

显示年月日: ${birthday?date} <br/>
显示时分秒：${birthday?time} <br/>
显示日期+时间：${birthday?datetime} <br>
自定义格式化：  ${birthday?string("yyyy年MM月")} <br/>

${money} <br/>
${money?c} <br/>

${text} <br/>
<#assign user=text?eval />
${user.username} <br/>