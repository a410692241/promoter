var urls = function(){
	var end = window.location.pathname.indexOf("/", 1);
	var ms = window.location.pathname.substring(0, end);
	return {
		"ms":ms,
		"testMs":ms
	}
}();

function BusinessException( message ){
	return {
		msg:message
	}
}