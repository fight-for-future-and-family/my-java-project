$(function(){
	
	var color= [
		        '#2ec7c9','#b6a2de','#5ab1ef','#ffb980','rgba(216,122,128,0.85)',
		        'rgba(141,152,179,0.5)','rgba(229,207,13,0.5)','rgba(151,181,82,0.81)',
		        'rgba(220,105,170,0.9)',
		        'rgba(7,162,164,0.9)','#DDA0DD',
		        '#F5DEB3','rgba(123,104,238,0.65)','rgba(255,0,0,0.35)',
		        'rgba(60,179,113,0.90)','rgba(201,171,0,0.85)',
		        'rgba(126,176,10,0.85)','rgba(160,82,45,0.4)','#FA8072'
		    ];

	var len = color.length;
	
	var main_div_width_game = $(".theme-popover").width();
	var game_div_width_game = $(".game").width();
	var margin_left_width_game = Number($(".game").css('margin-left').replace('px',''));
	
	var num_game = Math.floor(main_div_width_game / (game_div_width_game + margin_left_width_game + 4));//加上2px的误差值
	var game_all_width_game = game_div_width_game * num_game + margin_left_width_game * num_game;//所有游戏模块+间隔
	var margin_left_offerseth_game = (margin_left_width_game / 2) + (main_div_width_game - game_all_width_game) / 2 - 15;//需要平摊的宽度-滚动条的宽度
	
	var main_div_width = $(".data-container-main").first().width();
	var game_div_width = $(".gameTypeCls").width();
	var margin_left_width = Number($(".gameTypeCls").css('margin-left').replace('px',''));
	
	var num = Math.floor(main_div_width / (game_div_width + margin_left_width + 4));//加上2px的误差值
	var game_all_width = game_div_width * num + margin_left_width * num;//所有游戏模块+间隔
	var  margin_left_offerset = (margin_left_width / 2) + (main_div_width - game_all_width) / 2 - 4;//需要平摊的宽度
	
	$(".gameTypeCls").each(function(i,n){
		if(i % num == 0){
			$(n).css('margin-left',margin_left_offerset+'px');
		}
		$(n).css('backgroundColor',color[i%len]);
	});
	
	$(".gameTypeCls").each(function(i,n){
		$(n).click(function(){
			
			var gameTypeId=$(n).attr("gvalue");
			var gameTypeName=$(n).attr("gname");
			$("#gameTypeName").text(gameTypeName+' 系列');
			
			$.ajax({
				  type: "POST",
				  url: "/panel_manage/getGames.ac?parentId="+gameTypeId,
				  dataType: "json",
				  success: function(data){
					  var chGames = data.chGames;
					 // var  reportThree = data.reportThree;
					 // var Three_days = reportThree[0].paymentAmount;
					  
					 // var reportSeven = data.reportSeven;
					//  var Seven_days = reportSeven[0].paymentAmount;
					  
					  if(chGames){
						$(".data-container-main-fuceng .game").remove();
						$(".data-container-main-fuceng .games").remove();
						
						var isOutSideUser = $("#isOutSideUser").val();
						var isAdmin = $("#isAdmin").val();
						var isLeader = $("#isLeader").val();
						var isPM = $("#isPM").val();
						if(isOutSideUser=="false"&&(isAdmin=="true"||isLeader=="true"||isPM=="true")){
							var gameTemp1 = $(".gameTemp .game").clone(true);
							gameTemp1.attr("tips", "系列总览");
							gameTemp1.attr("class", "games");
							var urTemp1 = $("ul", gameTemp1);
							$("li.name span", urTemp1).text("系列总览");
							//$("li.install", urTemp1).text("3天收入:"+Three_days+"元");
							//$("li.payment", urTemp1).text("7天收入:"+Seven_days+"元");
							$(".theme-popover .data-container-main-fuceng").append(gameTemp1);
							$(".theme-popover .games").css('margin-left',margin_left_offerseth_game+'px');
							$(".theme-popover .games").css("border","3px solid #669900");
							$(".theme-popover .games").css('backgroundColor','aqua');
							$(".theme-popover .games").click(function(){
								$("#gameTypeId").val(gameTypeId);
								$("#submitForm").attr("action","/panel_bi/gameRealTime/toGameSeries.ac");
								$("#submitForm").submit();
							});
						}
						
						$.each(chGames,function(i,gameVo){
							//if(i==6){
							//	return false;
							//}
							var game = gameVo.entity;
							if(gameVo.yesterdayPayAmount!=-1)
								var install = Math.round(gameVo.yesterdayPayAmount / game.rate);
							else
								var install = "计算中";
							var payment = Math.round(game.payAmount / game.rate);
							
							var gameTemp = $(".gameTemp .game").clone(true);
							gameTemp.attr("gvalue", game.id);
							gameTemp.attr("tips", game.name);
							
							var urTemp = $("ul", gameTemp);
							$("li.name span", urTemp).text(game.name);
							if(gameVo.yesterdayPayAmount!=-1)
								$("li.install", urTemp).text('昨日收入：'+install+game.currency);
							else
								$("li.install", urTemp).text('昨日收入：'+install);
							$("li.payment", urTemp).text('月收入：'+payment+game.currency);
						
							$(".theme-popover .data-container-main-fuceng").append(gameTemp);
							
							$(".theme-popover .game").each(function(i,n){
								if(i % num == 0){
									$(n).css('margin-left',margin_left_offerseth_game+'px');
								}
								$(n).css('backgroundColor',color[i%len]);
							});
							
							$(".theme-popover .game").each(function(i,n){
								$(n).click(function(){
									var gameId=$(n).attr("gvalue");
									if(gameId==null||gameId==undefined){
										alert("gameId is error! gameId:"+gameId);
										return false;
									}
									$("#gameId").val(gameId);
									$("#submitForm").submit();
								});
								$(n).mouseover(function(){
									$(this).css("border","3px solid #669900");
								});
								$(n).mouseout(function(){
									$(this).css("border","2px solid #ebebeb");
								});
							});
						});
					}
					  
					$('.theme-popover-mask').fadeIn(100);
					$('.theme-popover').slideDown(200);
				  }
			});
		});
		$(n).mouseover(function(){
			  $(this).css("border","3px solid #669900");
		});
		$(n).mouseout(function(){
			$(this).css("border","2px solid #ebebeb");
		});
	});
	
	$('.theme-poptit .close').click(function(){
		$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
	})

});
	                    