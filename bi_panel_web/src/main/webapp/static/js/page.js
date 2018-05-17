$(function(){
	$(".pagination a").each(function(i,n){
	    if($(n).attr("id") == 'first'){
	    	$(n).click(function(){
				$("#submitForm").attr("action",$("#toFirst").val()+'.ac');
				$("#submitForm").submit();
			});
	    }
	    if($(n).attr("id") == 'page'){
	    	$(n).click(function(){
				$("#submitForm").attr("action",$("#toPage").val()+$(n).attr("value")+'.ac');
				$("#submitForm").submit();
			});
	    }
	});
	
	
	$(".detail-table a").each(function(i,n){
	    if($(n).attr("id") == 'opEdit'){
	    	$(n).click(function(){
				$("#submitForm").attr("action",$("#toEdit").val()+$(n).attr("value")+'_'+$(n).attr("page")+'.ac');
				$("#submitForm").submit();
			});
	    }
	    if($(n).attr("id") == 'opdel'){
	    	$(n).click(function(){
	    		if (confirm("确认要删除？")) {
	    			$("#submitForm").attr("action",$("#toDel").val()+$(n).attr("value")+'_'+$(n).attr("page")+'.ac');
					$("#submitForm").submit();
		         }
			});
	    }
	    if($(n).attr("id") == 'opStatus'){
	    	$(n).click(function(){
	    		var op = $(n).attr("op");
	    		var id = $(n).attr("value");
	    		if (confirm(op == 0 ? "确认要冻结ID为"+id+"的用户？" : "确认要解冻ID为"+id+"的用户？")) {
	    		   $("#submitForm").attr("action",$("#toStatus").val()+id+'_'+op+'_'+$(n).attr("page")+'.ac');
	    		   $("#submitForm").submit();
	    		}
	    	});
	    }
	});
});