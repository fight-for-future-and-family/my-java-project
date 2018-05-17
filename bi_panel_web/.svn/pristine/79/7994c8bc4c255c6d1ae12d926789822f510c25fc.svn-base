jQuery(function($) {
	$(document).ready(function() {
	    $.etlEngineTrigger.init();
	});
	
	$.etlEngineTrigger={
			init:function(){
				$.etlEngineTrigger.initEvents();
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
				
				$("#add").click($.etlEngineTrigger.submit);
			},	
			checkFormDatas:function(){
				
				var msg="";
				
				var checkedGamesLength=$(":checkbox[name='gameids']:checked").length;
				if(checkedGamesLength<1){
					msg="请选择游戏\r\n";
				}
				
				var beginDateValid=$.etlEngineTrigger.checkIsNull($("#beginDate"));
				if(beginDateValid){
					msg+="请选择开始时间\r\n";
				}
				
				var endDateValid=$.etlEngineTrigger.checkIsNull($("#endDate"));
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
				var check=$.etlEngineTrigger.checkFormDatas();
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
	
	//多选框 全选/全不选
	$('#checkAll').click(function checkAll(){
		var status = $('#checkAll').prop("checked");
		$(":checkbox").prop("checked",status);
	})
	
	//全选按钮 选中/不选中
	$("input[name='gameids']").click(function removeCheck(){
		var checks = $("input[name='gameids']");
		var status = true;
		for(var num=0;num<checks.length;num++){
			if(!checks[num].checked==status)
				status = false;
		}
		$('#checkAll').prop("checked",status);
	})
});