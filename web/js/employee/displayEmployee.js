/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function pagger(rid,id,pageindex,totalpage,gap)
{   var rid;
    var container = document.getElementById(id);
    var result = '';
    if(pageindex - gap > 1)
        result+= '<a href="employee?page=1&rid='+rid+'">' + 'First' + '</a>';
    
    for(var i=pageindex-gap;i<pageindex;i++)
        if(i>0)
            result+= '<a href="employee?page='+i+'&rid='+rid+'">' + i + '</a>';
    
    result+= '<span>' + pageindex + '</span>';
    
    for(var i=pageindex+1;i<=pageindex + gap;i++)
        if(i<=totalpage)
            result+= '<a href="employee?page='+i+'&rid='+rid+'">' + i + '</a>';
    
    if(pageindex + gap < totalpage)
        result+= '<a href="employee?page='+totalpage+'&rid='+rid+'">' + 'Last' + '</a>';
    
    container.innerHTML = result;
}

