function editHref(obj){
	var className = obj.className;
	var href = obj.href;
	var snid = $("input[name='snid']").val();
	var gameId = $("input[name='gameId']").val();
//	var a = document.getElementsByTagName(className);
	$("."+className).attr("href",href+ ((href.indexOf('?')>=0) ? "&snid="+snid+"&gameId="+gameId : "?snid="+snid+"&gameId="+gameId));
}