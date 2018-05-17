jQuery(function($) {
	$(document).ready(function() {
	    $.user.init();
	});
	
	$.user={
		init:function(){
			$.user.initEvents();
			$.user.initDatas();
		},
		initEvents:function(){
			$("input[name='entity.loginName']").change($.userUtil.checkLoginName);
			$("#submitButton").click($.userUtil.submit);
		},
		initDatas:function(){
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
