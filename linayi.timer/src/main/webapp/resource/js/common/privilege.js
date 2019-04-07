//重构权限UI显示
function filterView( content ){
	var review = $( "<div>"+content+"</div>" );
	var privilegeList = review.find( "[privilege-code]" );
	for( var i = 0 ;i < privilegeList.length; i++ ){
		//privilegeCodeList在commonJs.jsp中初始化
		if( privilegeCodeList.indexOf( privilegeList[i].getAttribute("privilege-code")+"" ) < 0 ){
			$(privilegeList[i]).css("display","none");
		}
	}
	return review.html();
}
