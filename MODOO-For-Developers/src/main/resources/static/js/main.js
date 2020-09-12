function onApiReIss(action) {
	var reIssueDiv;
	if(action === 'active') {
		reIssueDiv = document.getElementsByClassName('reIssueDiv')[0];
		reIssueDiv.className = 'reIssueDiv active';
	} else {
		reIssueDiv = document.getElementsByClassName('reIssueDiv active')[0];
		reIssueDiv.className = 'reIssueDiv';
	}
}