jQuery(function($) {
	$(document).ready(function() {
	    $.login.init();
	});
	
	$.login={
		init:function(){
			$.login.initEvents();
		},
		initEvents:function(){
			$("#fm-login-submit").click($.login.submit);
			$("#wfm-login-submit").click(function(){
				$("#loginType").val("no_chart");
				$.login.submit();
				$("#loginType").val("chart");
			   });
			
			$("input[name='entity.password']").keyup(function(event){
				var key = event.keyCode;
				if(key!=13){
					return false;
				}
				$.login.submit();
			});
			
			var header_w = $("#header").width();
			$("#headerRepeat").css('width',header_w-330+'px');
			
			//$("#yzm").attr("src","/panel_manage/random/codeGenerate.ac?now="+ new Date().getTime());
			$("#yzm").click(function(){
				$("#yzm").attr("src","/panel_manage/random/codeGenerate.ac?now="+ new Date().getTime());
			});
		},
		checkFormDatas:function(){
			var loginName=$("input[name='entity.loginName']").val();
			if(loginName==null||loginName==""){
				alert("请输入登录名");
				return false;
			}
			var password=$("input[name='entity.password']").val();
			if(password==null||password==""){
				alert("请输入密码");
				return false;
			}
			return true;
		},
		submit:function(){
			var check=$.login.checkFormDatas();
			if(!check){
				return ;
			}
			var submitForm=$("#submitForm");
			if(submitForm.length>0){
				submitForm.attr("target" , '') ;
				submitForm.submit() ;
				return true;
			} 
			return false;
		}
	};
});
