function onChange(type) {
	var div = document.getElementById(type);
	var nav = document.getElementById(type+'Nav');
	
	div.className = "divGrp active";
	nav.className = "memNavMem active";
	
	var unacdiv;
	var unacnav;
	if(type==='info'){
		unacdiv = document.getElementById('frame');
		unacnav = document.getElementById('frameNav');
	} else {
		unacdiv = document.getElementById('info');
		unacnav = document.getElementById('infoNav');
	}
	
	unacdiv.className = "divGrp";
	unacnav.className = "memNavMem";
}