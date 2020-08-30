function onNegative(rseq){
	var negative = document.getElementsByClassName('negativeDiv')[0];
	negative.className = "negativeDiv active";
	
	var seq = document.getElementById('seq');
	seq.value = rseq;
}

function onCancle() {
	var negative = document.getElementsByClassName('negativeDiv active')[0];
	negative.className = "negativeDiv";
}