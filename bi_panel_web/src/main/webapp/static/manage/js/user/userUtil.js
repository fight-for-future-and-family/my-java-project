$(function(){
	$.userUtil={
	checkFormDatas:function(){
		var loginName=$("input[name='entity.loginName']").val();
		if(loginName==null||loginName==""){
			alert("用户名不能为空");
			return false;
		}
		
		var namesuc = $("#namesuc").val();
		if(namesuc == '1'){
			alert("用户名不可用，请重新输入");
			return false;
		}
		
		var email=$("input[name='entity.email']").val();
		if(email==null||email==""){
			alert("邮箱不能为空");
			return false;
		}
		
		var telepone=$("input[name='entity.telepone']").val();
		if(telepone==null||telepone==""){
			alert("手机不能为空");
			return false;
		}
		
		if($("input[name='entity.password']").length > 0){
			var password=$("input[name='entity.password']").val();
			if(password==null||password==""){
				alert("密码不能为空");
				return false;
			}
		}
		return true;
	},
	submit:function(){
		var check=$.user.checkFormDatas();
		if(!check){
			return ;
		}
		var submitForm=$("#submitForm");
		if(submitForm.length>0){
			submitForm.attr("target" , '') ;
			submitForm.submit();
			return true;
		} 
		return false;
	},
	checkLoginName:function(){
		$.post("/panel_manage/user/checkLoginName.ac",
			    {
			      loginName:$("input[name='entity.loginName']").val()
			    },
			    function(data,status){
			    	if(status == 'success'){
			    		var datatemp = "";
			    		if(data == '1'){
			    			datatemp = "此用户名已使用";
			    			$("#nameSuc").val('1');
			    		}else{
			    			datatemp = "恭喜，用户名可以使用";
			    			$("#nameSuc").val('2');
			    		}
			    		$("#loginNameMes").empty().show();
			    		$("#loginNameMes").text(datatemp);
			    	}else{
			    		$("#loginNameMes").empty().show();
			    		$("#loginNameMes").text('验证用户名失败...');
			    		$("#nameSuc").val('3');
			    	}
			    });
	}
	}
});