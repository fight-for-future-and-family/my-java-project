$(function(){

 $("#modelType").change(function(){
  var value = $("#modelType").val();
  if(value == 1){
   $("#gameIdSpan").show();
  }else{
   $("#gameIdSpan").hide();
  }
 });
 

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
				$("#submitForm").attr("action",$("#toEdit").val()+$(n).attr("value")+'_'+$(n).attr("page")+'.ac?type='+$(n).attr("type"));
				$("#submitForm").submit();
			});
	    }
	    if($(n).attr("id") == 'opdel'){
	    	$(n).click(function(){
	    		if (confirm("确认要删除？")) {
	    			$("#submitForm").attr("action",$("#toDel").val()+$(n).attr("value")+'_'+$(n).attr("page")+'.ac?type='+$(n).attr("type"));
					$("#submitForm").submit();
		         }
			});
	    }
	});

 
});