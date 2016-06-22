var req;
var isIE;
var completeField;
var completeTable;
var autoRow;
var pop
linkElement.className = "popupItem";

function init() {
    completeField = document.getElementById("complete-field");
    completeTable = document.getElementById("complete-table");
    autoRow = document.getElementById("auto-row");
    pop=document.getElementById("pop");
    
}

function doCompletion() {
        var url = "autocomplete?action=complete&SearchString="+completeField.value;
        req = initRequest();
        req.onreadystatechange = callback;
        req.open("GET", url, true);        
        req.send(null);       
}


function callback() 
{
   
    if ((req.readyState == 4) && (req.status == 200)) {
    
    	completeTable.style.display = 'table';
       	document.getElementById("complete-table").innerHTML = req.responseText;  	
        }
   
    }

function poop(id){
	
	
    var url = "AjaxMoviePopup?id="+id;
    req = initRequest();   
    req.onreadystatechange = populatePOP; 
    req.open("GET", url, true);
    req.send(null); 
	
	}


function populatePOP(){		
	if ((req.readyState == 4) && (req.status == 200)) {	
    document.getElementById('poop').innerHTML = req.responseText;
	document.getElementById('poop').style.display = 'block';    
    }
    }

function opo(){
	 document.getElementById('poop').style.display = "none";
	 }

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

