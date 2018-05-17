$(function(){
	$(document).ready(function() {
	    $.addShortUrl.init();
	    $.timeZone.showTimeZone();
	});
	
	$.addShortUrl={
		init:function(){
			$.addShortUrl.initEvent();
			$.addShortUrl.queryData();
		},
		initEvent:function(){
			$("#platformName").change(function(){
				var ps_data={
						platformName:$("#platformName").val(),
						snid:$("input[name='snid']").val(),
						gameId:$("input[name='gameId']").val()
				};
				if(ps_data.platformName==''){
					$("#appUrl").val('');
					$("#encryptKey").val('');
					$("#sign").val('');
					$("#status").val(1);
					$("#matchRule").val(0);
					$("#redirect").val(1);
					$("#terminalType").val(1);
					$("#systemType").val(0);
					$("#size").val('');
					$("#pcode").html('');
					return ;
				}
				
				$.ajax({
					"url":"/panel_bi/adTracking/getShortUrlByPlatform.ac",
	                "data": $.param(ps_data),
	                "type": "post",
					"dataType": "json",
					success: function(msg){
						if(msg.data!=null){
							var adShortUrl = msg.data;
							$("#appUrl").val(adShortUrl.appUrl);
							$("#encryptKey").val(adShortUrl.encryptKey);
							$("#sign").val(adShortUrl.sign);
							$("#matchRule").val(adShortUrl.matchRule);
							$("#redirect").val(adShortUrl.redirect);
							$("#terminalType").val(adShortUrl.terminalType);
							$("#systemType").val(adShortUrl.systemType);
							$("#pcode").html(adShortUrl.platformCode+"_");
						}else{
							var platformNameStr = $("#platformName").val();
							$("#appUrl").val('');
							$("#encryptKey").val('');
							$("#sign").val('');
							$("#status").val(1);
							$("#matchRule").val(0);
							$("#redirect").val(1);
							$("#terminalType").val(1);
							$("#systemType").val(0);
							$("#pcode").html(platformNameStr.split("_")[1]+"_");
						}
					}
				});
			});
			$("#save").click(function(){
				var ps_data={
						platformName:$("#platformName").val(),
						adPlace:$("#adPlace").val(),
						appUrl:$("#appUrl").val(),
						sign:$("#sign").val(),
						status:$("#status").val(),
						redirect:$("#redirect").val(),
						matchRule:$("#matchRule").val(),
						encryptKey:$("#encryptKey").val(),
						terminalType:$("#terminalType").val(),
						systemType:$("#systemType").val(),
						snid:$("input[name='snid']").val(),
						gameId:$("input[name='gameId']").val(),
						size:$("#size").val()
				};
				
				if($.addShortUrl.isEmpty(ps_data.platformName)
						|| $.addShortUrl.isEmpty(ps_data.adPlace)
						|| $.addShortUrl.isEmpty(ps_data.appUrl)
						|| $.addShortUrl.isEmpty(ps_data.sign)
						|| $.addShortUrl.isEmpty(ps_data.status)
						|| $.addShortUrl.isEmpty(ps_data.redirect)
						|| $.addShortUrl.isEmpty(ps_data.matchRule)
						|| $.addShortUrl.isEmpty(ps_data.encryptKey)
						|| $.addShortUrl.isEmpty(ps_data.terminalType)
						|| $.addShortUrl.isEmpty(ps_data.systemType)
						|| $.addShortUrl.isEmpty(ps_data.size)){
				    alert('内容不能为空，请重新输入！');//内容不能为空，请重新输入！
				    return false;
				}
				
				if(!ps_data.adPlace.match(/^[A-Za-z0-9]+$/)){
	        	   alert('广告位只能由英文字母与数字组成！');
	        	   return false;
	            }
				
				$.ajax({
					"url":"/panel_bi/adTracking/batchSaveShortUrl.ac",
		               "data":$.param(ps_data),
		               "type":"post",
		               "error":function(){
		                   alert("服务器未正常响应，请重试");
		               },
		               "success":function(data){
		            	   if(data.msg=='2'){
		            		   if(data.msgStr!=null){
		            			    alert(data.msgStr+"已存在，其余条数已创建成功");
		            		   }else{
		            			   	alert('保存成功！');
		            			   	$("#platformName").val('');
		    						$("#adPlace").val('');
		            			    $("#appUrl").val('');
			       					$("#encryptKey").val('');
			       					$("#sign").val('');
			       					$("#status").val(1);
			       					$("#matchRule").val(0);
			       					$("#redirect").val(1);
			       					$("#terminalType").val(1);
			       					$("#systemType").val(0);
			       					$("#size").val('');
			       					$("#pcode").html('');
		            		   }
		                	   
		                   }else if(data.msg == '1'){
		                	   alert('内容不能为空，请重新输入！');
		                   }
		               }
	           });
			});
		},
		isEmpty:function(str){
			return str == null || str == '';
		},
		queryData:function(){
			
		}
	};
});
