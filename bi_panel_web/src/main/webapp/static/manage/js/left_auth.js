jQuery(function($) {
	$(document).ready(function() {
		$(".sidebar li").each(function(i,n){
			$(n).removeClass("active");
		});
		
		$("#"+$("#auth_page").val()+"").addClass("active");// 权限左侧页面
		
		$("#"+$("#game_page").val()+"").addClass("active");// 游戏分析左侧页面
	});
	 
});