$(function(){
		
	$(document).ready(function() {
	    $.gameLTVReport.init();
	});
		
	var lastShow = 0;
	var currShow = 0;
	var jqTable = null;
	$.gameLTVReport={
			init:function(){
				$.gameLTVReport.initEvent();
				$.gameLTVReport.submit();
			},
			initEvent:function(){
				$("#query").click(function(){
					$.gameLTVReport.submit();
			    });
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gameLTVReport.redict($(n).attr("id"));
					});
				});
				$(".tab-hd span").click(function(){
					$.gameLTVReport.redict($(this).attr("view"));
				});
				$("#channel").change(function(){
					$.gameLTVReport.change($.gameLTVReport);
				});
				
				$("#data_caption span").each(function(i,n){
					$(n).click(function(){
						currShow = $(n).attr("val");
						if(lastShow == currShow){
							return false;
						}
						
						$("#data_caption span").removeClass("ui-selected");
						$(n).addClass("ui-selected");
						
						$.gameLTVReport.showhidecol('data');
					});
				});
				
				$("#ajax_data_caption span").each(function(i,n){
					$(n).click(function(){
						currShow = $(n).attr("val");
						if(lastShow == currShow){
							return false;
						}
						
						$("#ajax_data_caption span").removeClass("ui-selected");
						$(n).addClass("ui-selected");
						
						$.gameLTVReport.showhidecol('ajax');
					});
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			submit:function(){
				var ps = $.gameLTVReport.checkParam();
				if(ps == null){
					return;
			    }
				if($("#s_c").val() == '-99'){
					lastShow = 0;
					currShow = 1;
					$("#data_caption span").removeClass("ui-selected");
				    $("#data_caption span").first().addClass("ui-selected");
				    $("#ajax_data_caption span").removeClass("ui-selected");
				    $("#ajax_data_caption span").first().addClass("ui-selected");
					$.gameLTVReport.brokenAjaxTable(ps);
	    			return;
				}
				
				$("#query")[0].disabled = true;
				$('#ajax_data').hide();
				$('#data').show();
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/wap/gameView/getWapGameDatasView.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						    $.gameLTVReport.brokenTable(data.ltvs,data.game,data.totals);
						    
						    
						    
						    $("#query")[0].disabled = false;
						    
						    lastShow = 0;
							currShow = 1;
							$("#data_caption span").removeClass("ui-selected");
						    $("#data_caption span").first().addClass("ui-selected");
						    $("#ajax_data_caption span").removeClass("ui-selected");
						    $("#ajax_data_caption span").first().addClass("ui-selected");
					  }
				});
			},
			showhidecol:function(type){
				if(jqTable != null){
					if(type == 'data'){
						$.gameLTVReport.showOrHide(2,lastShow,false);
						$.gameLTVReport.showOrHide(2,currShow,true);
					}else{
						$.gameLTVReport.showOrHide(3,lastShow,false);
						$.gameLTVReport.showOrHide(3,currShow,true);
					}
					
					
					lastShow = currShow;
					jqTable.draw(false);
				}
			},
			showOrHide:function(begin,lastShow,visible){
				if(lastShow == 0 || lastShow == 1){
					for(var i=begin;i<=(begin+30);i++){
						jqTable.column(i).visible(visible);
					}
				}else if(lastShow == 2){
					for(var i=(begin+31);i<=(begin+60);i++){
						jqTable.column(i).visible(visible);
					}
				}else if(lastShow == 3){
					for(var i=(begin+61);i<=(begin+90);i++){
						jqTable.column(i).visible(visible);
					}
				}
			},
			brokenTable:function(reports,game,totals){
				
                $("#dt_wrapper").remove();
				
				var table = $(".template_cache .dataTable").clone(true);
				table.attr("id","dt");
				
				var headTemp = $("thead tr",table).first();
				var value = $("#channel").val().split('_');
				if(value.length > 0 && value[0] == 'role'){
					$("td.head_install",headTemp).text('创角数');
				}else{
					$("td.head_install",headTemp).text('新注册');
				}
				var rate = game.rate;
				
				if(totals!=null){
					
					var trSum = $("tbody tr",table).first().clone(true);
					$("td.installDay",trSum).text(totals.day);
					$("td.install",trSum).text(totals.gameLtv.install);
					$("td.D0",trSum).text(Math.round(totals.gameLtv.d0/rate*100)/100);
					$("td.D1",trSum).text(Math.round(totals.gameLtv.d1/rate*100)/100);
					$("td.D2",trSum).text(Math.round(totals.gameLtv.d2/rate*100)/100);
					$("td.D3",trSum).text(Math.round(totals.gameLtv.d3/rate*100)/100);
					$("td.D4",trSum).text(Math.round(totals.gameLtv.d4/rate*100)/100);
					$("td.D5",trSum).text(Math.round(totals.gameLtv.d5/rate*100)/100);
					$("td.D6",trSum).text(Math.round(totals.gameLtv.d6/rate*100)/100);
					$("td.D7",trSum).text(Math.round(totals.gameLtv.d7/rate*100)/100);
					$("td.D8",trSum).text(Math.round(totals.gameLtv.d8/rate*100)/100);
					$("td.D9",trSum).text(Math.round(totals.gameLtv.d9/rate*100)/100);
					$("td.D10",trSum).text(Math.round(totals.gameLtv.d10/rate*100)/100);
					$("td.D11",trSum).text(Math.round(totals.gameLtv.d11/rate*100)/100);
					$("td.D12",trSum).text(Math.round(totals.gameLtv.d12/rate*100)/100);
					$("td.D13",trSum).text(Math.round(totals.gameLtv.d13/rate*100)/100);
					$("td.D14",trSum).text(Math.round(totals.gameLtv.d14/rate*100)/100);
					$("td.D15",trSum).text(Math.round(totals.gameLtv.d15/rate*100)/100);
					$("td.D16",trSum).text(Math.round(totals.gameLtv.d16/rate*100)/100);
					$("td.D17",trSum).text(Math.round(totals.gameLtv.d17/rate*100)/100);
					$("td.D18",trSum).text(Math.round(totals.gameLtv.d18/rate*100)/100);
					$("td.D19",trSum).text(Math.round(totals.gameLtv.d19/rate*100)/100);
					$("td.D20",trSum).text(Math.round(totals.gameLtv.d20/rate*100)/100);
					$("td.D21",trSum).text(Math.round(totals.gameLtv.d21/rate*100)/100);
					$("td.D22",trSum).text(Math.round(totals.gameLtv.d22/rate*100)/100);
					$("td.D23",trSum).text(Math.round(totals.gameLtv.d23/rate*100)/100);
					$("td.D24",trSum).text(Math.round(totals.gameLtv.d24/rate*100)/100);
					$("td.D25",trSum).text(Math.round(totals.gameLtv.d25/rate*100)/100);
					$("td.D26",trSum).text(Math.round(totals.gameLtv.d26/rate*100)/100);
					$("td.D27",trSum).text(Math.round(totals.gameLtv.d27/rate*100)/100);
					$("td.D28",trSum).text(Math.round(totals.gameLtv.d28/rate*100)/100);
					$("td.D29",trSum).text(Math.round(totals.gameLtv.d29/rate*100)/100);
					$("td.D30",trSum).text(Math.round(totals.gameLtv.d30/rate*100)/100);
					$("td.D31",trSum).text(Math.round(totals.gameLtv.d31/rate*100)/100);
					$("td.D32",trSum).text(Math.round(totals.gameLtv.d32/rate*100)/100);
					$("td.D33",trSum).text(Math.round(totals.gameLtv.d33/rate*100)/100);
					$("td.D34",trSum).text(Math.round(totals.gameLtv.d34/rate*100)/100);
					$("td.D35",trSum).text(Math.round(totals.gameLtv.d35/rate*100)/100);
					$("td.D36",trSum).text(Math.round(totals.gameLtv.d36/rate*100)/100);
					$("td.D37",trSum).text(Math.round(totals.gameLtv.d37/rate*100)/100);
					$("td.D38",trSum).text(Math.round(totals.gameLtv.d38/rate*100)/100);
					$("td.D39",trSum).text(Math.round(totals.gameLtv.d39/rate*100)/100);
					$("td.D40",trSum).text(Math.round(totals.gameLtv.d40/rate*100)/100);
					$("td.D41",trSum).text(Math.round(totals.gameLtv.d41/rate*100)/100);
					$("td.D42",trSum).text(Math.round(totals.gameLtv.d42/rate*100)/100);
					$("td.D43",trSum).text(Math.round(totals.gameLtv.d43/rate*100)/100);
					$("td.D44",trSum).text(Math.round(totals.gameLtv.d44/rate*100)/100);
					$("td.D45",trSum).text(Math.round(totals.gameLtv.d45/rate*100)/100);
					$("td.D46",trSum).text(Math.round(totals.gameLtv.d46/rate*100)/100);
					$("td.D47",trSum).text(Math.round(totals.gameLtv.d47/rate*100)/100);
					$("td.D48",trSum).text(Math.round(totals.gameLtv.d48/rate*100)/100);
					$("td.D49",trSum).text(Math.round(totals.gameLtv.d49/rate*100)/100);
					$("td.D50",trSum).text(Math.round(totals.gameLtv.d50/rate*100)/100);
					$("td.D51",trSum).text(Math.round(totals.gameLtv.d51/rate*100)/100);
					$("td.D52",trSum).text(Math.round(totals.gameLtv.d52/rate*100)/100);
					$("td.D53",trSum).text(Math.round(totals.gameLtv.d53/rate*100)/100);
					$("td.D54",trSum).text(Math.round(totals.gameLtv.d54/rate*100)/100);
					$("td.D55",trSum).text(Math.round(totals.gameLtv.d55/rate*100)/100);
					$("td.D56",trSum).text(Math.round(totals.gameLtv.d56/rate*100)/100);
					$("td.D57",trSum).text(Math.round(totals.gameLtv.d57/rate*100)/100);
					$("td.D58",trSum).text(Math.round(totals.gameLtv.d58/rate*100)/100);
					$("td.D59",trSum).text(Math.round(totals.gameLtv.d59/rate*100)/100);
					$("td.D60",trSum).text(Math.round(totals.gameLtv.d60/rate*100)/100);
					$("td.D61",trSum).text(Math.round(totals.gameLtv.d61/rate*100)/100);
					$("td.D62",trSum).text(Math.round(totals.gameLtv.d62/rate*100)/100);
					$("td.D63",trSum).text(Math.round(totals.gameLtv.d63/rate*100)/100);
					$("td.D64",trSum).text(Math.round(totals.gameLtv.d64/rate*100)/100);
					$("td.D65",trSum).text(Math.round(totals.gameLtv.d65/rate*100)/100);
					$("td.D66",trSum).text(Math.round(totals.gameLtv.d66/rate*100)/100);
					$("td.D67",trSum).text(Math.round(totals.gameLtv.d67/rate*100)/100);
					$("td.D68",trSum).text(Math.round(totals.gameLtv.d68/rate*100)/100);
					$("td.D69",trSum).text(Math.round(totals.gameLtv.d69/rate*100)/100);
					$("td.D70",trSum).text(Math.round(totals.gameLtv.d70/rate*100)/100);
					$("td.D71",trSum).text(Math.round(totals.gameLtv.d71/rate*100)/100);
					$("td.D72",trSum).text(Math.round(totals.gameLtv.d72/rate*100)/100);
					$("td.D73",trSum).text(Math.round(totals.gameLtv.d73/rate*100)/100);
					$("td.D74",trSum).text(Math.round(totals.gameLtv.d74/rate*100)/100);
					$("td.D75",trSum).text(Math.round(totals.gameLtv.d75/rate*100)/100);
					$("td.D76",trSum).text(Math.round(totals.gameLtv.d76/rate*100)/100);
					$("td.D77",trSum).text(Math.round(totals.gameLtv.d77/rate*100)/100);
					$("td.D78",trSum).text(Math.round(totals.gameLtv.d78/rate*100)/100);
					$("td.D79",trSum).text(Math.round(totals.gameLtv.d79/rate*100)/100);
					$("td.D80",trSum).text(Math.round(totals.gameLtv.d80/rate*100)/100);
					$("td.D81",trSum).text(Math.round(totals.gameLtv.d81/rate*100)/100);
					$("td.D82",trSum).text(Math.round(totals.gameLtv.d82/rate*100)/100);
					$("td.D83",trSum).text(Math.round(totals.gameLtv.d83/rate*100)/100);
					$("td.D84",trSum).text(Math.round(totals.gameLtv.d84/rate*100)/100);
					$("td.D85",trSum).text(Math.round(totals.gameLtv.d85/rate*100)/100);
					$("td.D86",trSum).text(Math.round(totals.gameLtv.d86/rate*100)/100);
					$("td.D87",trSum).text(Math.round(totals.gameLtv.d87/rate*100)/100);
					$("td.D88",trSum).text(Math.round(totals.gameLtv.d88/rate*100)/100);
					$("td.D89",trSum).text(Math.round(totals.gameLtv.d89/rate*100)/100);
					$("td.D90",trSum).text(Math.round(totals.gameLtv.d90/rate*100)/100);
					trSum.css({"background-color":"rgba(255, 0, 0, 0.34902)"});
					$("tfoot",table).append(trSum);
					
				}

				
                $.each(reports,function(i,n){
				
					var trTemp = $("tbody tr",table).first().clone(true);
					var v = n.gameLtv;
					
					if(v.install == null || v.install == undefined || v.install == '-'){
						$("td.installDay",trTemp).text(v.installDay);
						$("td.install",trTemp).text("-");
						for(var j=0;j<=90;j++){
							$("td.D"+(j)+"",trTemp).text('-');
						}
					}else{
						$("td.installDay",trTemp).text(v.installDay);
						$("td.install",trTemp).text(v.install);
						$("td.D0",trTemp).text(Math.round(v.d0/v.install/rate*100)/100);
						$("td.D1",trTemp).text(Math.round(v.d1/v.install/rate*100)/100);
						$("td.D2",trTemp).text(Math.round(v.d2/v.install/rate*100)/100);
						$("td.D3",trTemp).text(Math.round(v.d3/v.install/rate*100)/100);
						$("td.D4",trTemp).text(Math.round(v.d4/v.install/rate*100)/100);
						$("td.D5",trTemp).text(Math.round(v.d5/v.install/rate*100)/100);
						$("td.D6",trTemp).text(Math.round(v.d6/v.install/rate*100)/100);
						$("td.D7",trTemp).text(Math.round(v.d7/v.install/rate*100)/100);
						$("td.D8",trTemp).text(Math.round(v.d8/v.install/rate*100)/100);
						$("td.D9",trTemp).text(Math.round(v.d9/v.install/rate*100)/100);
						$("td.D10",trTemp).text(Math.round(v.d10/v.install/rate*100)/100);
						$("td.D11",trTemp).text(Math.round(v.d11/v.install/rate*100)/100);
						$("td.D12",trTemp).text(Math.round(v.d12/v.install/rate*100)/100);
						$("td.D13",trTemp).text(Math.round(v.d13/v.install/rate*100)/100);
						$("td.D14",trTemp).text(Math.round(v.d14/v.install/rate*100)/100);
						$("td.D15",trTemp).text(Math.round(v.d15/v.install/rate*100)/100);
						$("td.D16",trTemp).text(Math.round(v.d16/v.install/rate*100)/100);
						$("td.D17",trTemp).text(Math.round(v.d17/v.install/rate*100)/100);
						$("td.D18",trTemp).text(Math.round(v.d18/v.install/rate*100)/100);
						$("td.D19",trTemp).text(Math.round(v.d19/v.install/rate*100)/100);
						$("td.D20",trTemp).text(Math.round(v.d20/v.install/rate*100)/100);
						$("td.D21",trTemp).text(Math.round(v.d21/v.install/rate*100)/100);
						$("td.D22",trTemp).text(Math.round(v.d22/v.install/rate*100)/100);
						$("td.D23",trTemp).text(Math.round(v.d23/v.install/rate*100)/100);
						$("td.D24",trTemp).text(Math.round(v.d24/v.install/rate*100)/100);
						$("td.D25",trTemp).text(Math.round(v.d25/v.install/rate*100)/100);
						$("td.D26",trTemp).text(Math.round(v.d26/v.install/rate*100)/100);
						$("td.D27",trTemp).text(Math.round(v.d27/v.install/rate*100)/100);
						$("td.D28",trTemp).text(Math.round(v.d28/v.install/rate*100)/100);
						$("td.D29",trTemp).text(Math.round(v.d29/v.install/rate*100)/100);
						$("td.D30",trTemp).text(Math.round(v.d30/v.install/rate*100)/100);
						$("td.D31",trTemp).text(Math.round(v.d31/v.install/rate*100)/100);
						$("td.D32",trTemp).text(Math.round(v.d32/v.install/rate*100)/100);
						$("td.D33",trTemp).text(Math.round(v.d33/v.install/rate*100)/100);
						$("td.D34",trTemp).text(Math.round(v.d34/v.install/rate*100)/100);
						$("td.D35",trTemp).text(Math.round(v.d35/v.install/rate*100)/100);
						$("td.D36",trTemp).text(Math.round(v.d36/v.install/rate*100)/100);
						$("td.D37",trTemp).text(Math.round(v.d37/v.install/rate*100)/100);
						$("td.D38",trTemp).text(Math.round(v.d38/v.install/rate*100)/100);
						$("td.D39",trTemp).text(Math.round(v.d39/v.install/rate*100)/100);
						$("td.D40",trTemp).text(Math.round(v.d40/v.install/rate*100)/100);
						$("td.D41",trTemp).text(Math.round(v.d41/v.install/rate*100)/100);
						$("td.D42",trTemp).text(Math.round(v.d42/v.install/rate*100)/100);
						$("td.D43",trTemp).text(Math.round(v.d43/v.install/rate*100)/100);
						$("td.D44",trTemp).text(Math.round(v.d44/v.install/rate*100)/100);
						$("td.D45",trTemp).text(Math.round(v.d45/v.install/rate*100)/100);
						$("td.D46",trTemp).text(Math.round(v.d46/v.install/rate*100)/100);
						$("td.D47",trTemp).text(Math.round(v.d47/v.install/rate*100)/100);
						$("td.D48",trTemp).text(Math.round(v.d48/v.install/rate*100)/100);
						$("td.D49",trTemp).text(Math.round(v.d49/v.install/rate*100)/100);
						$("td.D50",trTemp).text(Math.round(v.d50/v.install/rate*100)/100);
						$("td.D51",trTemp).text(Math.round(v.d51/v.install/rate*100)/100);
						$("td.D52",trTemp).text(Math.round(v.d52/v.install/rate*100)/100);
						$("td.D53",trTemp).text(Math.round(v.d53/v.install/rate*100)/100);
						$("td.D54",trTemp).text(Math.round(v.d54/v.install/rate*100)/100);
						$("td.D55",trTemp).text(Math.round(v.d55/v.install/rate*100)/100);
						$("td.D56",trTemp).text(Math.round(v.d56/v.install/rate*100)/100);
						$("td.D57",trTemp).text(Math.round(v.d57/v.install/rate*100)/100);
						$("td.D58",trTemp).text(Math.round(v.d58/v.install/rate*100)/100);
						$("td.D59",trTemp).text(Math.round(v.d59/v.install/rate*100)/100);
						$("td.D60",trTemp).text(Math.round(v.d60/v.install/rate*100)/100);
						$("td.D61",trTemp).text(Math.round(v.d61/v.install/rate*100)/100);
						$("td.D62",trTemp).text(Math.round(v.d62/v.install/rate*100)/100);
						$("td.D63",trTemp).text(Math.round(v.d63/v.install/rate*100)/100);
						$("td.D64",trTemp).text(Math.round(v.d64/v.install/rate*100)/100);
						$("td.D65",trTemp).text(Math.round(v.d65/v.install/rate*100)/100);
						$("td.D66",trTemp).text(Math.round(v.d66/v.install/rate*100)/100);
						$("td.D67",trTemp).text(Math.round(v.d67/v.install/rate*100)/100);
						$("td.D68",trTemp).text(Math.round(v.d68/v.install/rate*100)/100);
						$("td.D69",trTemp).text(Math.round(v.d69/v.install/rate*100)/100);
						$("td.D70",trTemp).text(Math.round(v.d70/v.install/rate*100)/100);
						$("td.D71",trTemp).text(Math.round(v.d71/v.install/rate*100)/100);
						$("td.D72",trTemp).text(Math.round(v.d72/v.install/rate*100)/100);
						$("td.D73",trTemp).text(Math.round(v.d73/v.install/rate*100)/100);
						$("td.D74",trTemp).text(Math.round(v.d74/v.install/rate*100)/100);
						$("td.D75",trTemp).text(Math.round(v.d75/v.install/rate*100)/100);
						$("td.D76",trTemp).text(Math.round(v.d76/v.install/rate*100)/100);
						$("td.D77",trTemp).text(Math.round(v.d77/v.install/rate*100)/100);
						$("td.D78",trTemp).text(Math.round(v.d78/v.install/rate*100)/100);
						$("td.D79",trTemp).text(Math.round(v.d79/v.install/rate*100)/100);
						$("td.D80",trTemp).text(Math.round(v.d80/v.install/rate*100)/100);
						$("td.D81",trTemp).text(Math.round(v.d81/v.install/rate*100)/100);
						$("td.D82",trTemp).text(Math.round(v.d82/v.install/rate*100)/100);
						$("td.D83",trTemp).text(Math.round(v.d83/v.install/rate*100)/100);
						$("td.D84",trTemp).text(Math.round(v.d84/v.install/rate*100)/100);
						$("td.D85",trTemp).text(Math.round(v.d85/v.install/rate*100)/100);
						$("td.D86",trTemp).text(Math.round(v.d86/v.install/rate*100)/100);
						$("td.D87",trTemp).text(Math.round(v.d87/v.install/rate*100)/100);
						$("td.D88",trTemp).text(Math.round(v.d88/v.install/rate*100)/100);
						$("td.D89",trTemp).text(Math.round(v.d89/v.install/rate*100)/100);
						$("td.D90",trTemp).text(Math.round(v.d90/v.install/rate*100)/100);
					}
					
					$('tbody',table).append(trTemp); 
			});
			
			$("tbody tr",table).first().remove();
			$('#data').append(table); 
			
			var hiddenColumn = [33,34,35,36,37,38,39,40,
			                    41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,
			                    56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,
			                    71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,
			                    86,87,88,89,90,91,92];
			
			jqTable = $('#dt').DataTable({
				"sPaginationType" : "full_numbers",
	            "aoColumnDefs": [{  "bSearchable": false, 
	            					"bVisible": false, 
	            					"aTargets": hiddenColumn }] ,
	            "dom" : 'T<"clear"><"top">rt<"bottom"ip><"clear">' ,
	            "bDestroy" : true,
	            "bPaginate" : true,
	            "bInfo" : true,
				"bSort" : true,
				"bScrollCollapse" : true,
				"bScrollInfinite" : true,
			    "bFilter" : false,
			    "paging": true, // 翻页功能
			    "lengthChange": false, // 改变每页显示数据数量
			    "searching": false, // 过滤功能
			    "ordering": true, // 排序功能
			    "order": [[ 0, "desc" ]],
			    "processing": true,
	            "lengthMenu": [ 10, 20, 50, 100],
			    "pageLength": 10,
			    "language":{
			    	"url": "/dataTables/chinese.json"
			    },
			    tableTools: {
			    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
			    				"aButtons":[ 
	                            { 
	                               "sExtends": "xls", 
	                               "sButtonText": "下载数据" 
	                             }]
		        }
			});
			
				$('#dt').removeClass();
				$('#dt').addClass('table table-striped');
				   
				$('#dt tbody tr').click(function (){
			        $('td',this).toggleClass('highlight');
			    });
			},
			changeCheck:function(){
				var ps={
					     id:$("input[name='gamesId']").val(),
					     gameId:$("input[name='gameId']").val(),
					     snid:$("input[name='snid']").val(),
					};
			      if(ps.id == null || ps.id == ''){ return null; }
			      if(ps.gameId == null || ps.gameId == ''){ return null; }
			      if(ps.snid == null || ps.snid == ''){ return null; }
			      return ps;
			},
			change:function(exclass){
				var ps = this.changeCheck();
				if(ps == null){
					return;
				}
				var value = $("#channel").val();
				if(value == 'install_all' || value == 'role_all'){
					$("#s_c").remove();
					exclass.submit();
				}else if(value == 'install_source' || value == 'role_source'){
					$("#s_c").remove();
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/game/getGameSource.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" style="max-width:200px"><option value="-1">请选择...</option><option value="-99">所有渠道</option>';
						    $.each(data.gameSources,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	exclass.submit();
							});
						  }
					});
				}else if(value == 'client'){
					$("#s_c").remove();
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/game/getGameClient.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" style="max-width:200px"><option value="-1">请选择...</option><option value="-99">所有服务器</option>';
						    $.each(data.gameClients,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	exclass.submit();
							});
						  }
					});
				}
			},
			checkParam:function(){
			  var ps={
				     id:$("input[name='gamesId']").val(),
				     gameId:$("input[name='gameId']").val(),
				     snid:$("input[name='snid']").val(),
				     channel:$("#channel").val(),
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 source:$('#s_c').val(),
					 clientid:$('#s_c').val(),
					 view:$('#view').val()
				};
		      if(ps.id == null || ps.id == ''){ return null; }
		      if(ps.gameId == null || ps.gameId == ''){ return null; }
		      if(ps.snid == null || ps.snid == ''){ return null; }
		      
		      if(ps.channel == null || ps.channel == ''){
		    	  ps.channel = 'all'; 
		       }else if(ps.channel == 'source' && (ps.source != null && ps.source == '-1')){
		    	   return null;
		       }else if(ps.channel == 'client' && (ps.clientid != null && ps.clientid == '-1')){
		    	   return null;
		       }
		      
		      if((ps.endTime == null || ps.endTime == '') && (ps.beginTime == null || ps.beginTime == '')){
		    	  var d = new Date();
		    	  ps.endTime = $.date.getDateFullStr(d);
		    	  $("input[name='endTime']").val(ps.endTime);
		    	  
		    	  // if(d.getDate() <= 3){
			    	  d.setDate(d.getDate()- 30);
		    	 // }else{
			    //	  d.setDate(1);
		    	//  }
		    	  ps.beginTime = $.date.getDateFullStr(d);
		    	  
		    	  $("input[name='beginTime']").val(ps.beginTime);
		      }else if((ps.endTime == null || ps.endTime == '') && (ps.beginTime != null && ps.beginTime != '')){
		    	  var d = new Date(ps.beginTime);
		    	  d.setDate(d.getDate()+ 30);
		    	  
		    	  ps.endTime = $.date.getDateFullStr(d);
		    	  $("input[name='endTime']").val(ps.endTime);
		      }else if((ps.endTime != null && ps.endTime != '') && (ps.beginTime == null || ps.beginTime == '')){
		    	  var d = new Date(ps.endTime);
		    	  d.setDate(d.getDate()- 30);
		    	  
		    	  ps.beginTime = $.date.getDateFullStr(d);
		    	  $("input[name='beginTime']").val(ps.beginTime);
		      }
		      
		      return ps;
			},
			getItemValue:function(index,v){
				switch(index){
				   case 3:
				      return v.gameLtv.d0;
				   case 4:
				      return v.gameLtv.d1;
				   case 5:
				      return v.gameLtv.d2;
				   case 6:
				      return v.gameLtv.d3;
				   case 7:
				      return v.gameLtv.d4;
				   case 8:
				      return v.gameLtv.d5;
				   case 9:
				      return v.gameLtv.d6;
				   case 10:
				      return v.gameLtv.d7;
				   case 11:
				      return v.gameLtv.d8;
				   case 12:
				      return v.gameLtv.d9;
				   case 13:
				      return v.gameLtv.d10;
				   case 14:
				      return v.gameLtv.d11;
				   case 15:
				      return v.gameLtv.d12;
				   case 16:
				      return v.gameLtv.d13;
				   case 17:
				      return v.gameLtv.d14;
				   case 18:
				      return v.gameLtv.d15;
				   case 19:
				      return v.gameLtv.d16;
				   case 20:
				      return v.gameLtv.d17;
				   case 21:
				      return v.gameLtv.d18;
				   case 22:
				      return v.gameLtv.d19;
				   case 23:
				      return v.gameLtv.d20;
				   case 24:
				      return v.gameLtv.d21;
				   case 25:
				      return v.gameLtv.d22;
				   case 26:
				      return v.gameLtv.d23;
				   case 27:
				      return v.gameLtv.d24;
				   case 28:
				      return v.gameLtv.d25;
				   case 29:
				      return v.gameLtv.d26;
				   case 30:
				      return v.gameLtv.d27;
				   case 31:
				      return v.gameLtv.d28;
				   case 32:
				      return v.gameLtv.d29;
				   case 33:
				      return v.gameLtv.d30;
				   case 34:
				      return v.gameLtv.d31;
				   case 35:
				      return v.gameLtv.d32;
				   case 36:
				      return v.gameLtv.d33;
				   case 37:
				      return v.gameLtv.d34;
				   case 38:
				      return v.gameLtv.d35;
				   case 39:
				      return v.gameLtv.d36;
				   case 40:
				      return v.gameLtv.d37;
				   case 41:
				      return v.gameLtv.d38;
				   case 42:
				      return v.gameLtv.d39;
				   case 43:
				      return v.gameLtv.d40;
				   case 44:
				      return v.gameLtv.d41;
				   case 45:
				      return v.gameLtv.d42;
				   case 46:
				      return v.gameLtv.d43;
				   case 47:
				      return v.gameLtv.d44;
				   case 48:
				      return v.gameLtv.d45;
				   case 49:
				      return v.gameLtv.d46;
				   case 50:
				      return v.gameLtv.d47;
				   case 51:
				      return v.gameLtv.d48;
				   case 52:
				      return v.gameLtv.d49;
				   case 53:
				      return v.gameLtv.d50;
				   case 54:
				      return v.gameLtv.d51;
				   case 55:
				      return v.gameLtv.d52;
				   case 56:
				      return v.gameLtv.d53;
				   case 57:
				      return v.gameLtv.d54;
				   case 58:
				      return v.gameLtv.d55;
				   case 59:
				      return v.gameLtv.d56;
				   case 60:
				      return v.gameLtv.d57;
				   case 61:
				      return v.gameLtv.d58;
				   case 62:
				      return v.gameLtv.d59;
				   case 63:
				      return v.gameLtv.d60;
				   case 64:
				      return v.gameLtv.d61;
				   case 65:
				      return v.gameLtv.d62;
				   case 66:
				      return v.gameLtv.d63;
				   case 67:
				      return v.gameLtv.d64;
				   case 68:
				      return v.gameLtv.d65;
				   case 69:
				      return v.gameLtv.d66;
				   case 70:
				      return v.gameLtv.d67;
				   case 71:
				      return v.gameLtv.d68;
				   case 72:
				      return v.gameLtv.d69;
				   case 73:
				      return v.gameLtv.d70;
				   case 74:
				      return v.gameLtv.d71;
				   case 75:
				      return v.gameLtv.d72;
				   case 76:
				      return v.gameLtv.d73;
				   case 77:
				      return v.gameLtv.d74;
				   case 78:
				      return v.gameLtv.d75;
				   case 79:
				      return v.gameLtv.d76;
				   case 80:
				      return v.gameLtv.d77;
				   case 81:
				      return v.gameLtv.d78;
				   case 82:
				      return v.gameLtv.d79;
				   case 83:
				      return v.gameLtv.d80;
				   case 84:
				      return v.gameLtv.d81;
				   case 85:
				      return v.gameLtv.d82;
				   case 86:
				      return v.gameLtv.d83;
				   case 87:
				      return v.gameLtv.d84;
				   case 88:
				      return v.gameLtv.d85;
				   case 89:
				      return v.gameLtv.d86;
				   case 90:
				      return v.gameLtv.d87;
				   case 91:
				      return v.gameLtv.d88;
				   case 92:
				      return v.gameLtv.d89;
				   case 93:
				      return v.gameLtv.d90;
				}
			},
			processCellData:function(index,v){
           	 
	            if(index == 0){
	            	return v.gameLtv.installDay;	 
	             }else if(index == 1){
	            	 return v.gameLtv.sourceOrClientName;
	             }else if(index == 2){
	            	 return v.gameLtv.install;
	             }
				
				var value = $.gameLTVReport.getItemValue(index,v)
			    
				if(value == 0){
					return '0';
				}else if(value == null || value == ''){
					return '-';
				}
				//c.gameLtv.d0
				var install = v.gameLtv.install;
				var rate = $("#gameRate").val();
				
				return install == 0 ? 0 : Math.round((value/install/rate) * 100)/100;
			},
            brokenAjaxTable:function(ps){
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				
				var type = $("#channel").val();
				var rate = $("#gameRate").val();
				$('#ajax_data').show();
				$('#life_funnel_chart').hide();
				$('#life_line_chart').hide();
				$('#life_bar_chart').hide();
				$('#data').hide();
				
				var table = $(".template_cache .ajax_table").clone(true);
				var headTemp = $("thead tr",table).first();
				
				if("client" == type){
					$("td.head_type",headTemp).text('服务器');
				}else{
					$("td.head_type",headTemp).text('渠道');
				}
				if(type == 'role_source'){
					$("td.head_install",headTemp).text('创角数');
				}else{
					$("td.head_install",headTemp).text('新注册');
				}
				
				table.attr("id", "dt_ajax");
				$('#ajax_data').append(table);
				
				var hiddenColumn = new Array();
				for(var i=34;i<=93;i++){
					hiddenColumn.push(i);
				}
				
				var columnsDefArr = new Array();//列值定义数组
				columnsDefArr.push(
						{  "bSearchable": false, 
							"bVisible": false, 
							"aTargets": hiddenColumn 
						}
						);
				for(var k=0;k<=93;k++){
	        		columnsDefArr.push({
                        targets: k,
                        render: function (a, b, v, d) {
                        	       return $.gameLTVReport.processCellData(d.col,v);
                        	}
                    });
	        	}
				
				jqTable = $('#dt_ajax').DataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
					          data: function(d){
					        	  d.id=ps.id;
					        	  d.gameId=ps.gameId;
					        	  d.channel=ps.channel;
					        	  d.beginTime=ps.beginTime;
					        	  d.endTime=ps.endTime;
					        	  d.source=ps.source;
					        	  d.clientid=ps.clientid;
					        	  d.view=ps.view;
					          },
							  type: "POST",
							  url:"/panel_bi/wap/gameView/getWapGameDatasView.ac"
			              },
			        "pageLength": 10,
				    "language":{
				    	"url": "/dataTables/chinese.json"
				    },
			        "deferRender": true,
				    "sPaginationType" : "full_numbers",
			        "dom" : '<"clear"><"top">frt<"bottom"ip><"clear">' ,
					"bSort" : false,
				    "bFilter" : true,
				    "paging": true, //翻页功能
				    "lengthChange": false, //改变每页显示数据数量
				    "searching": true, //过滤功能
				    "ordering": false, //排序功能
				    "order": [[ 0, "desc" ]],
				    rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                    },
			        columnDefs: columnsDefArr
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				
				$('#ajax_data_caption button').click(function (){
			        $(this).attr('disabled',true);
			        $(this).val(2);
			        window.btnTimeout=setInterval(function(){
						$.gameUtil.btnTimeout($('#ajax_data_caption button'),window.btnTimeout);
					  },1000);
			        $("#downLoadForm").submit();
			    });
				
				$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
			}
	};	
});
	                    