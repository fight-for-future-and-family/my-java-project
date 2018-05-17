jQuery(function($) {
	$(document).ready(function() {
	    $.etlEngineCleanup.init();
	});
	
	$.etlEngineCleanup={
			init:function(){
				$.etlEngineCleanup.initEvents();
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
				
				$("#add").click($.etlEngineCleanup.submit);
				
				$("#beginDate").val("");
				$("#endDate").val("");
				$(":checkbox[name='gameids']").attr("checked",false);
			},	
			checkFormDatas:function(){
				
				var msg="";
				var checkedGamesLength=$(":checkbox[name='gameids']:checked").length;
				if(checkedGamesLength<1){
					msg="请选择游戏\r\n";
				}
				
				var beginDateValid=$.etlEngineCleanup.checkIsNull($("#beginDate"));
				if(beginDateValid){
					msg+="请选择开始时间\r\n";
				}
				
				var endDateValid=$.etlEngineCleanup.checkIsNull($("#endDate"));
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
			confirmTips:function(){
				var gameNames = '';
				var checks = $(":checkbox[name='gameids']:checked");
	            $.each(checks,function(){
	            	gameNames = gameNames + '   ' + $(this).parent().text();
	            });
	            var beginDateValid = $("#beginDate").val();
	            var endDateValid = $("#endDate").val();
	            if(confirm("你确定要清除 “" + gameNames + "   ” \r\n " + beginDateValid + "----"+ endDateValid + "之间的数据吗？")){ 
	            	return true;
	            }
				return false;
			},
			submit:function(){
				var check=$.etlEngineCleanup.checkFormDatas();
				if(!check){
					return false;
				}
				
				var confirmT = $.etlEngineCleanup.confirmTips();
				if(!confirmT){
					return false;
				}
				var submitForm=$("#submitForm");
				if(submitForm.length>0){
					$("#add").attr('disabled',true); 
					$("#resultMsg").html("");
					$("#submitTips").html("正在执行，请勿操作...");
					$("#submitTips").after("<img src=\"/static/images/loading.gif\"/>");
					submitForm.attr("target" , '') ;
					submitForm.submit();
					return true;
				} 
				return false;
			}
	}
	
});