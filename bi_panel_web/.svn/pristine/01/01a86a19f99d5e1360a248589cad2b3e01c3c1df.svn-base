$(function(){
	
	$(document).ready(function() {
	    $.updatePlatforms.init();
	    $.timeZone.showTimeZone();
	});
	
	$.updatePlatforms={
		init:function(){
			$.updatePlatforms.initEvent();
			$.updatePlatforms.initData();
		},
		initEvent:function(){
			
			$("#add").click(function(){
				$.updatePlatforms.submit();
			});
			
		},
		initData:function(){
			var list = $.parseJSON($("#paramsList").text());
			
			$.each(list, function(i,obj){
				if(obj.mapperColumn == 'mac'){
					if(obj.mapperType == 1){
						$("input[name='mac']").val(obj.paramsCode);
					}else{
						$("input[name='cb.mac']").val(obj.paramsCode)
					}
				}else if(obj.mapperColumn == 'macMd5'){
					if(obj.mapperType == 1){
						$("input[name='macMd5']").val(obj.paramsCode);
					}else{
						$("input[name='cb.macMd5']").val(obj.paramsCode)
					}
				}else if(obj.mapperColumn == 'ifa'){
					if(obj.mapperType == 1){
						$("input[name='ifa']").val(obj.paramsCode);
					}else{
						$("input[name='cb.ifa']").val(obj.paramsCode)
					}
				}else if(obj.mapperColumn == 'ifaMd5'){
					if(obj.mapperType == 1){
						$("input[name='ifaMd5']").val(obj.paramsCode);
					}else{
						$("input[name='cb.ifaMd5']").val(obj.paramsCode)
					}
				}else if(obj.mapperColumn=='id'){
					if(obj.mapperType==2){
						$("input[name='cb.id']").val(obj.paramsCode)
					}
				}else if(obj.mapperColumn == 'ip'){
					if(obj.mapperType == 1){
						$("input[name='ip']").val(obj.paramsCode);
					}else{
						$("input[name='cb.ip']").val(obj.paramsCode)
					}
				}else if(obj.mapperColumn == 'userAgent'){
					if(obj.mapperType == 1){
						$("input[name='userAgent']").val(obj.paramsCode);
					}else{
						$("input[name='cb.userAgent']").val(obj.paramsCode)
					}
				}else{
					if(obj.mapperType == 1){
						var temp = $("input[name='extra']").val();
						temp += obj.mapperColumn + ":" +obj.paramsCode+",";
						$("input[name='extra']").val(temp);
					}else{
						var temp = $("input[name='cb.extra']").val();
						temp += obj.mapperColumn + ":" +obj.paramsCode+",";
						$("input[name='cb.extra']").val(temp);
					}
				}
			});
		},
		submit:function(){
			var ps = $.updatePlatforms.check();
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
				return false;
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
	                    