jQuery(function($) {
	$(document).ready(function() {
	    $.core.init();
	});
	
	$.core={
		init:function(){
			$.core.initEvents();
			$.core.initDatas();
		},
		initEvents:function(){
			$(".recordable").click($.core.changeNav);
		},
		initDatas:function(){
			$(".nav .nav_item").removeClass("selected");
			var href=window.location.href;
			$(".nav .nav_item").each(function(i,n){
				var url=$("a",this).attr("href");
				if(href.indexOf(url)>0){
					$(this).addClass("selected");
					return false;
				}
			});
		},
		changeNav:function(){
			var isOpen=$(this).prop("open");
			if(isOpen==undefined){
				isOpen=true;
			}
			if(isOpen){
				$(this).removeClass("open").addClass("close");
				$(this).next().hide();
				$(this).prop("open",false);
			}else{
				$(this).removeClass("close").addClass("open");
				$(this).next().show();
				$(this).prop("open",true);
			}
		}
	};
});
