$(function() {
	$(document).ready(function() {
		$.monitorAdd.init();
	});
	
	$.monitorAdd = {
		init : function() {
			$.monitorAdd.initEvent();
		},
		initEvent:function() {
			$("#save").click(function(){
				var lowerLimit = $("#lowerLimit").val();
				var topLimit = $("#topLimit").val();
				if(!(($.monitorAdd.isNumber(lowerLimit)||lowerLimit=='')&&
						($.monitorAdd.isNumber(topLimit)||topLimit=='')
						)) {
					alert("参数不能为非数字");
					return ;
				}
				
				$.ajax({
					type: "POST",
					url: "/panel_bi/monitor_metric/selectMonitorMetricExists.ac",
					data: {
						snid:$("input[name='snid']").val(),
						gameId:$("input[name='gameId']").val(),
						columnName:$("#columnName").val()
					},
					success:function(data){
						if(data.count=='0'){
							$("#submitForm").submit();
						}else{
							alert("该指标已创建预警规则，请选择其他指标");
						}
					}
				});
			});
		},
		isNumber:function(str){
			var n = Number(str);
			if (!isNaN(n)){
			    return true;
			}else {
				return false;
			}
		}
	}
});