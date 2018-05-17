jQuery(function($) {
	$(document).ready(function() {
	    $.syncDatas.init();
	});
	
	$.syncDatas={
			init:function(){
				$.syncDatas.initEvents();
			},
			initEvents:function(){
				
				if($("#beginDate").length > 0){
					laydate({
				    	elem: '#beginDate'
					});
				}
				if($("#endDate").length > 0){
					laydate({
				    	elem: '#endDate'
					});
				}
				
				$("#add").click($.syncDatas.submit);
			},	
			checkFormDatas:function(){
				
				var msg="";
				
				var checkedGamesLength=$(":checkbox[name='gameids']:checked").length;
				if(checkedGamesLength<1){
					msg="请选择游戏\r\n";
				}
				
				var beginDateValid=$.syncDatas.checkIsNull($("#beginDate"));
				if(beginDateValid){
					msg+="请选择开始时间\r\n";
				}
				
				var endDateValid=$.syncDatas.checkIsNull($("#endDate"));
				if(endDateValid){
					msg+="请选择结束时间\r\n";
				}
				
				if(msg!=''){
					alert(msg);
					return false;
				}
				
				return true;
			},
			checkIsNull:function(element){
				if(element.val() == null || $.trim(element.val()) == ''){
					return true;
				}
				return false;
			},
			submit:function(){
				var check=$.syncDatas.checkFormDatas();
				if(!check){
					return;
				}
				var submitForm=$("#submitForm");
				if(submitForm.length>0){
					submitForm.attr("target" , '') ;
					submitForm.submit();
					return true;
				} 
				return false;
			}
	}
	
});