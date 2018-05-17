$(function(){
	
	$(document).ready(function() {
	    $.addPlatforms.init();
	    $.timeZone.showTimeZone();
	});
	
	$.addPlatforms={
		init:function(){
			$.addPlatforms.initEvent();
		},
		initEvent:function(){
			
			$("#add").click(function(){
				$.addPlatforms.submit();
			});
			
		},
		submit:function(){
			var ps = $.addPlatforms.check();
			if(ps == null){
				return false;
			}
			$("#paramValues").val(JSON.stringify(ps));
			var submitForm=$("#submitForm");
			if(submitForm.length>0){
				submitForm.attr("target" , '') ;
				submitForm.submit();
				return true;
			} 
			return false;
		},
		check:function(){
			var name = $("input[name='entity.name']").val();
			var code = $("input[name='entity.code']").val();
			if(name == null || name.trim() == ''){
				alert("平台名称不能为空！");
				return null;
			}
			if(code == null || code.trim() == ''){
				alert("平台代码不能为空！");
				return null;
			}
			
			var paramObj = {
				macMd5:$("input[name='macMd5']").val(),
				mac:$("input[name='mac']").val(),
				ifa:$("input[name='ifa']").val(),
				ifaMd5:$("input[name='ifaMd5']").val(),
				ip:$("input[name='ip']").val(),
				userAgent:$("input[name='userAgent']").val(),
				extra:$("input[name='extra']").val()
			};
			
			var callbackObj = {
					id:$("input[name='cb.id']").val(),
					macMd5:$("input[name='cb.macMd5']").val(),
					mac:$("input[name='cb.mac']").val(),
					ifa:$("input[name='cb.ifa']").val(),
					ifaMd5:$("input[name='cb.ifaMd5']").val(),
					ip:$("input[name='cb.ip']").val(),
					userAgent:$("input[name='cb.userAgent']").val(),
					extra:$("input[name='cb.extra']").val()
				};
			
			var ps = {
				paramObj:paramObj,
				callbackObj:callbackObj
			};
			return ps;
		}
	};
});
	                    