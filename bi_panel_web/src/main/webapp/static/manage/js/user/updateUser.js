jQuery(function($) {
	$(document).ready(function() {
	    $.userModify.init();
	});
	
	$.userModify={
		init:function(){
			$.userModify.initEvents();
			$.userModify.initDatas();
		},
		initEvents:function(){
			$("input[name='entity.loginName']").change(function(){
				if($("input[name='entity.loginName']").val() == $("#loginName").val()){
					$("#loginNameMes").empty().hide();
				}else{
					$.userUtil.checkLoginName();
				}
			});
			
			$("#submitButton").click($.userUtil.submit);
		},
		initDatas:function(){
			var userGames = $("#userGameList").val();
			if(userGames != undefined || userGames.length > 0){
				var userGamesArr = userGames.split(',');
				if(userGamesArr.length > 0){
				   $.each(userGamesArr,function(i,n){
					   $("input[name=userGames][value="+n+"]").attr("checked", 'checked');
				   });
				}
			}
			
			var userGroups = $("#userGroupList").val();
			if(userGroups != undefined || userGroups.length > 0){
				var userGroupsArr = userGroups.split(',');
				if(userGroupsArr.length > 0){
					$.each(userGroupsArr,function(i,n){
						$("input[name=userGroups][value="+n+"]").attr("checked", 'checked');
					});
				}
			}
			
			var isChecked = $('#userGroups6').prop("checked");
			if(isChecked){
				$('#userGroups1').prop("checked", false).prop("disabled", isChecked);
				$('#userGroups2').prop("checked", false).prop("disabled", isChecked);
				$('#userGroups3').prop("checked", false).prop("disabled", isChecked);
				$('#userGroups4').prop("checked", false).prop("disabled", isChecked);
				$('#userGroups5').prop("checked", false).prop("disabled", isChecked);
			}
			
			$('#userGroups3').click(function(){
				var groups1 = $('#userGroups1').prop("checked");
				var groups2 = $('#userGroups2').prop("checked");
				var groups3 = $('#userGroups3').prop("checked");
				var groups4 = $('#userGroups4').prop("checked");
				var groups5 = $('#userGroups5').prop("checked");
				var groups6 = $('#userGroups6').prop("checked");
				if(groups3){
					$(':checkbox').prop("checked", true);
				}
				$('#userGroups1').prop("checked", groups1);
				$('#userGroups2').prop("checked", groups2);
				$('#userGroups3').prop("checked", groups3);
				$('#userGroups4').prop("checked", groups4);
				$('#userGroups5').prop("checked", groups5);
				$('#userGroups6').prop("checked", groups6);
			});
			
			$('#userGroups6').click(function(){
				var groups6 = $('#userGroups6').prop("checked");
				if(groups6){
					$(':checkbox').prop("checked", true);
				}
				$('#userGroups1').prop("checked", false).prop("disabled", groups6);
				$('#userGroups2').prop("checked", false).prop("disabled", groups6);
				$('#userGroups3').prop("checked", false).prop("disabled", groups6);
				$('#userGroups4').prop("checked", false).prop("disabled", groups6);
				$('#userGroups5').prop("checked", false).prop("disabled", groups6);
				$('#userGroups6').prop("checked", groups6);
			});
		}
	};
});
