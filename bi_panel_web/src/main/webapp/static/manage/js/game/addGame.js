jQuery(function($) {
	$(document).ready(function() {
	    $.addGame.init();
	});
	
	$.addGame={
			init:function(){
				$.addGame.initEvents();
				$.addGame.initDatas();
			},
			initEvents:function(){
				$("#upImage").click($.addGame.upImage);
				
				$("#add").click(function(){
					$.addGame.submit();
				});
			},	
			initDatas:function(){
				var gameGroups = $("#gameGroupList").val();
				if(gameGroups != undefined && gameGroups.length > 0){
					var gameGroupsArr = gameGroups.split(',');
					if(gameGroupsArr.length > 0){
					   $.each(gameGroupsArr,function(i,n){
						   if(n==''){
							   return true;
						   }
						   $("input[name=gameManageUsers][value="+n+"]").attr("checked", 'checked');
					   });
					}
				}
				
				var timeZone = $("#timeZone");
				if(timeZone != undefined && timeZone.length > 0){
					var timeZones = $.timeZone.getTimeZones();
					$.each(timeZones,function(i,v){
						var optionTemp = $("option",timeZone).first().clone(true);
						$(optionTemp).val(v.time);
						$(optionTemp).text(v.name);
						$(timeZone).append(optionTemp); 
					});
					$("option",timeZone).first().remove();
					if($("#timeZoneVal").val() == undefined
							|| $("#timeZoneVal").val() == null
							|| $("#timeZoneVal").val() == ''
							|| ($("#timeZoneVal").val().indexOf('GMT')<0)){
						$("#timeZone").val('GMT+8');//东8时区
					}else{
						$("#timeZone").val($("#timeZoneVal").val());
					}
				}
			},
			checkFormDatas:function(){
				
				$(".check.errorMsg").each(function(i,n){
					isNull = $.addGame.checkIsNull($("input[name='"+$(this).attr("checkId")+"']"));
					if(isNull){
						alert($(this).text());
						return false;
					}
				});
			},
			checkIsNull:function(element){
				if(element.val() == null || $.trim(element.val()) == ''){
					return true;
				}
				return false;
			},
			submit:function(){
				var check=$.addGame.checkFormDatas();
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
			},
			upImage:function(){
				
				$.ajaxFileUpload({
					  url:"/panel_manage/gameManager/uf.ac?imageFile"+$("#imageFile").val()+"&oldImageFile="+$("#logo").val(),
					  secureuri:false,                       //是否启用安全提交,默认为false
				      fileElementId:'imageFile',           //文件选择框的id属性
				      dataType:'text',                       //服务器返回的格式,可以是json或xml等
				      success:function(data, status){        //服务器响应成功时的处理函数
				            data = data.replace("</PRE>", '');
				            data = data.replace("</pre>", ''); //本例中设定上传文件完毕后,服务端会返回给前台[0`filepath]
				            
				            var pos = data.indexOf(">");
				            if(data.substr(pos+1,1) == 0){
				            var str = data.substring(pos+2);
				            var jsonObj = eval('('+str+')');
				            $("#logo_img").attr("src", jsonObj.filePath);
				            $("#logo").val(jsonObj.filePath);
				            }else{
				               alert('图片上传失败，请重试！！');
				            }
				        },
				        error:function(data, status, e){ //服务器响应失败时的处理函数
				            alert('图片上传失败，请重试！！');
				        }
					});
				
			}
			
	}
	
});