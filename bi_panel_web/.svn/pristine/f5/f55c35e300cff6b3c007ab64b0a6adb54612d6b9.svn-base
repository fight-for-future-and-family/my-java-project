jQuery(function($) {
	$(document).ready(function() {
	    $.modifyPass.init();
	});
	
	$.modifyPass={
		init:function(){
			$.modifyPass.initEvents();
		},
		initEvents:function(){
			$("#submitButton").click($.modifyPass.submit);
			$("#modifyPasswordBtn").click(function(){
				$("#password_p").toggle();
				$("#password_ul").toggle();
				if($("#op").val() == 1){
					$(this).text('修改密码');
					$("#op").val(0);
				}else{
					$(this).text('隐藏密码');
					$("#op").val(1);
				}
				return false;
			});
			$("input[name='entity.loginName']").change(function(){
				if($("input[name='entity.loginName']").val() == $("#loginName").val()){
					$("#loginNameMes").empty().hide();
				}else{
					$.userUtil.checkLoginName();
				}
			});
		},
		checkFormDatas:function(){
			var loginName=$("input[name='entity.loginName']").val();
			var realName=$("input[name='entity.realName']").val();
			var telepone=$("input[name='entity.telepone']").val();
			
			if(loginName == undefined || loginName == ''){
				$("#loginNameMes").text('用户名不能为空').show();
				return false;
			}
			if(realName == undefined || realName == ''){
				$("#loginNameMes").text('真实姓名不能为空').show();
				return false;
			}
			if(telepone == undefined || telepone == ''){
				$("#loginNameMes").text('手机号码不能为空').show();
				return false;
			}
			
			if($("#op").val() == 1){
				var password=$("input[name='entity.password']").val();
				if(password==undefined||password==""){
					$("#passwordMes").text('请输入原始密码').show();
					return false;
				}
				
				if($("input[name='repassword']").length > 0){
					var newPassword=$("input[name='newPassword']").val();
					var repassword=$("input[name='repassword']").val();
					if(newPassword == null || newPassword ==''){
						$("#passwordMes").text('请输入新密码').show();
						return false;
					}
					
					if(repassword==null||repassword==""){
						$("#passwordMes").text('请确认密码').show();
						return false;
					}
					
					if(newPassword != repassword){
						$("#passwordMes").text('新密码与确认密码不一致').show();
						return false;
					}
					
					var regUpper = /[A-Z]/;
			        var regLower = /[a-z]/;
			        var regNum = /[0-9]/;
			        var complex = 0;
			         if (regNum.test(newPassword)) {
			             ++complex;
			         }
			         if (regLower.test(newPassword)) {
			             ++complex;
			         }
			         if (regUpper.test(newPassword)) {
			             ++complex;
			         }
			         if (complex < 3 || str.length < 8|| str.length >20) {
			         	$("input[name='newPassword']").val("");
			         	$("input[name='repassword']").val("");
			         	$("input[name='newPassword']").focus();
			   			alert("密码必须包含大小写字母+数字,长度要求8-20位！");
			   			return false;
			          }
				}
			}
			
			return true;
		},
		submit:function(){
			var check=$.modifyPass.checkFormDatas();
			if(!check){
				return false;
			}
			var submitForm=$("#submitForm");
			if(submitForm.length>0){
				submitForm.attr("target" , '') ;
				submitForm.submit();
				return true;
			} 
			return false;
		}
	};
});
