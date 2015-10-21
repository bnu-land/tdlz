<#list prjList as r>
项目名称:${r.projectName!"无"}
监测时间:${r.time!"无"}
土地平整:实际进度:${r.ppercentPz!"无"}  预期进度:${r.yqPpercentPz!"无"} 
农田水利:实际进度:${r.ppercentSl!"无"}  预期进度:${r.yqPpercentSl!"无"}
田间道路:实际进度:${r.ppercentDl!"无"}  预期进度:${r.yqPpercentDl!"无"}
</#list>

