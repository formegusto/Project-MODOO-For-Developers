let iseqList = [];

function pushIseq(item, iseq) {
	item.className = "infoItem active";
	item.setAttribute("onclick", "popIseq(this," + iseq + ")");
	
	iseqList.push(iseq);
}

function popIseq(item, iseq) {
	item.className = "infoItem";
	item.setAttribute("onclick", "pushIseq(this," + iseq + ")");
	
	iseqList = iseqList.filter(popIseq => popIseq != iseq);
}

function onMessage(){
	if(iseqList.length == 0) {
		alert("보내실 데이터를 선택해주세요!");
		return;
	}
	var accept = document.getElementsByClassName('acceptDiv')[0];
	accept.className = "acceptDiv active";
	
	var iseqListInput = document.getElementsByClassName('iseqList')[0];
	iseqListInput.value = iseqList;
}

function onCancle() {
	var accept = document.getElementsByClassName('acceptDiv active')[0];
	accept.className = "acceptDiv";
	
	var iseqListInput = document.getElementsByClassName('iseqList')[0];
	iseqListInput.value = "";
}