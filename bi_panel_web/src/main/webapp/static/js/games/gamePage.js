$(function(){
	
	$(document).ready(function() {
	    $.gamePage.init();
	});
		
	$.gamePage={
	  init:function(){
		//$.gamePage.initEvent();
	  },
	  initEvent:function(){
		  $(".pagination li").each(function(i,n){
				$(n).click(function(){
					$.gamePage.toPage($(n).attr("value"));
				});
			});
	  },
	  toPage:function(pageNo){
		  $(".detail-table tr").each(function(i,n){
			  if(i != 0){
				  var pv = i;
					 // pv = $(n).attr("pv");
					  if(pv > ((pageNo - 1) * 10) &&  pv <= (pageNo * 10)){
						  $(n).fadeIn("slow");
					  }else{
						  $(n).fadeOut();
					  }
			  }
			});
	  },
	  pagination:function(dataLength){
			var pageStr = '';
			if(dataLength > 10){
				var pageYu = dataLength % 10;
				var pageSize = pageYu == 0 ? dataLength / 10 : dataLength / 10 + 1;
				pageSize = Math.min(pageSize,10);
				pageStr += '<ul id="page_ul" class="pagination" pageSize="'+pageSize+'">';
				for(var i = 1;i<=pageSize;i++){
					pageStr += '<li id="page" value="'+i+'"><a>'+i+'</a></li>';
				}
				pageStr += '</ul>';
				
				$("#page_ul").remove();
				$("#page_nav").append(pageStr);
				
				$.gamePage.initEvent();
			}
		},
		pagination1:function(ulName,navName,dataLength){
			var pageStr = '';
			if(dataLength > 10){
				var pageYu = dataLength % 10;
				var pageSize = pageYu == 0 ? dataLength / 10 : dataLength / 10 + 1;
				pageSize = Math.min(pageSize,10);
				pageStr += '<ul id="'+ulName+'" class="pagination" pageSize="'+pageSize+'">';
				for(var i = 1;i<=pageSize;i++){
					pageStr += '<li id="page" value="'+i+'"><a>'+i+'</a></li>';
				}
				pageStr += '</ul>';
				
				$("#"+ulName).remove();
				$("#"+navName).append(pageStr);
				
				$.gamePage.initEvent();
			}
		}
	}
	
});