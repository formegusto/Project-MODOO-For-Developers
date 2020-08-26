function onCheck(button) {
	button.className="agreeCheck active";
}

function onSubmit() {
	let btnGrp = document.getElementsByClassName('agreeCheck active');
	let agreeCheck = btnGrp.length;
	
	if(agreeCheck === 2) {
		document.form.submit();
	} else {
		alert("모든 내용을 확인하시고 동의해주세요!");
	}
}