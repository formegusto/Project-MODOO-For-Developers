function onChange(type) {
	var div = document.getElementById(type);
	var nav = document.getElementById(type+'Nav');
	var unacdiv = document.getElementsByClassName('divGrp active')[0];
	var unacnav = document.getElementsByClassName('memNavMem active')[0];
	
	div.className = "divGrp active";
	nav.className = "memNavMem active";
	unacdiv.className = "divGrp";
	unacnav.className = "memNavMem";
}