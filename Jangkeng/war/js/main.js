function btnCliked(val) {
	document.makePose.GU.disabled = true;
	document.makePose.CHOKI.disabled = true;
	document.makePose.PA.disabled = true;
	var input = document.createElement('INPUT');
	input.type = 'hidden';
	input.name = val;
	document.makePose.appendChild(input);
	document.makePose.submit();
}
