function createPanel(html){

	var newOuterDiv = document.createElement('div')
    newOuterDiv.setAttribute('class','panel panel-default');

    var newInnerDiv = document.createElement('div')
    newInnerDiv.setAttribute('class','panel-body');
	newInnerDiv.innerHTML = html;
    
    newOuterDiv.appendChild(newInnerDiv);
	
    return newOuterDiv;
}
