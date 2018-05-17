$(function(){
	
	
	
	$('#Download_reg button').click(function (){
        $(this).attr('disabled',true);
        $(this).val(2);
        window.btnTimeout=setInterval(function(){
			$.gameUtil.btnTimeout($('#Download_reg button'),window.btnTimeout);
		  },1000);
        $("#stats").val("1");
       var source = $('#s_c').val();
       $("#source2").val(source);
        $("#downLoadForm").submit();
    });

	$('#Download_money button').click(function (){
        $(this).attr('disabled',true);
        $(this).val(2);
        $("#stats").val("2");
        var source = $('#s_c').val();
        $("#source2").val(source);
        window.btnTimeout=setInterval(function(){
			$.gameUtil.btnTimeout($('#Download_money button'),window.btnTimeout);
		  },1000);
        $("#downLoadForm").submit();
    });
	
	
		
	
	option_line = {
		    tooltip : {trigger: 'axis'},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }
		};
	
	option_bar = {
		    tooltip : {trigger: 'axis'},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true
		};
	
	option_funnel = {
		    title : {text: '注收比',subtext: '平均值('+$("#gameCurrency").val()+')'}, calculable : true,
		    tooltip : {trigger: 'item',formatter: "{a} <br/>{b}"},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    series : [
		        {
		            name:'注收比',type:'funnel',
		            x : '20%',sort : 'ascending',minSize: '0%',maxSize: '100%',width:'50%',min:0,max:100,
		            itemStyle: {normal: {label: {position: 'left',formatter: "{b}"}}},
		            data:[]
		        }
		    ]
		};
	
	
	var tab_funnel_dataArr = null,tab_dateArr = null;
	var tab_D0 = null, D1 = null,D3 = null,D7 = null,D30 = null;
	var D0_all = null, D1_all = null,D3_all = null,D7_all = null,D30_all = null;
	var tab_install_arr = null;
	$(document).ready(function() {
	    $.gameLife.init();
	    $.timeZone.showTimeZone();
	   
	});
	
	
	
	$.gameLife={
			init:function(){
				$.gameLife.initEvent();
				$.gameLife.submit();
			},
			
			show_hide:function(){
				
				$('#Download_money').unbind();
				$("#data_money").hide();
				$("#Download_money").hide();
				$("#Download_reg").show();
				$("#registered").css("background-color","#99f21b");
				
				
				$("#registered").click(function(){
					$("#Download_reg").show();
					$("#data_money").hide();
					$("#data").show();
					$("#Download_money").hide();
					$("#registered").css("background-color","#99f21b");
					$("#money").css("background-color","");
					
			    });
				
				$("#money").click(function(){
					$('.DTTT_container').remove();
					$("#Download_reg").hide();
					$("#data").hide();
					$("#data_money").show();
					$("#Download_money").show();
					$("#money").css("background-color","#99f21b");
					$("#registered").css("background-color","");
			    });
				
			},
			
			initEvent:function(){
				$("#query").click(function(){
					$.gameLife.submit();
			    });
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gameLife.redict($(n).attr("id"));
					});
				});
				$("#indicators").change(function(){
					$.gameUtil.change($.gameLife);
				});
				$("#queryType").change(function(){
					if($("#queryType").val() == 'roleChoice'){
						$("#indicators option:last").hide();
					}else{
						$("#indicators option:last").show();
					}
					$("#indicators").val('all');
					$("#s_c_span").text('');
					$.gameLife.submit();
				});
				
				$("#life_funnel_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#life_funnel_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#life_funnel").fadeIn();
							$("#life_funnel_data").fadeOut();
							$("#life_funnel_chart .dataShap ul").css('right','80px');
							$("#life_funnel_chart .dataShap ul").css('top','19px');
						}else{
							$("#life_funnel").fadeOut();
							$("#life_funnel_chart .dataShap ul").css('right','85px');
							$("#life_funnel_chart .dataShap ul").css('top','28px');
							if($("#dt_lf_wrapper").length > 0){
								$("#life_funnel_data").fadeIn();
							}else{
								$.gameLife.brokenFunnelTable();
								$("#life_funnel_data").fadeIn();
							}
						}
					});
				});
				
				$("#life_line_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#life_line_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#life_line").fadeIn();
							$("#life_line_data").fadeOut();
							$("#life_line_chart .dataShap ul").css('right','80px');
							$("#life_line_chart .dataShap ul").css('top','19px');
						}else{
							$("#life_line").fadeOut();
							$("#life_line_chart .dataShap ul").css('right','85px');
							$("#life_line_chart .dataShap ul").css('top','28px');
							if($("#dt_ll_wrapper").length > 0){
								$("#life_line_data").fadeIn();
							}else{
								$.gameLife.brokenLineTable();
								$("#life_line_data").fadeIn();
							}
						}
					});
			     });
				
				$("#life_bar_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#life_bar_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						var indicators = $('#indicators').val();
						if($("em",n).attr("class") == 'columnar'){
							$("#life_bar").fadeIn();
							$("#life_bar_data").fadeOut();
							$("#life_bar_chart .dataShap ul").css('right','80px');
							$("#life_bar_chart .dataShap ul").css('top','6px');
						}else{
							$("#life_bar").fadeOut();
							$("#life_bar_chart .dataShap ul").css('right','85px');
							$("#life_bar_chart .dataShap ul").css('top','28px');
							if($("#dt_lb_wrapper").length > 0){
								$("#life_bar_data").fadeIn();
							}else{
								$.gameLife.brokenBarTable();
								$("#life_bar_data").fadeIn();
							}
						}
					});
				});
			},
			
//			redict:function(view){
//				window.location.href='/panel_bi/gamePlayer/toGameplayerAnalysis.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/gamePlayer/toGameplayerAnalysis.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			submit:function(){
				var ps = $.gameUtil.checkParam();
				
				if(ps == null){
					return;
			    }
				
				if($("#s_c").val() == '-99'){
	    			$.gameLife.brokenAjaxTable(ps);
	    			return;
				}
				$("#Download_money").show();
				$("#Download_reg").show();
				$("#registered").show();
				$("#money").show();
				
				$('#ajax_data').hide();
				$('#life_funnel_chart').show();
				$('#life_line_chart').show();
				$('#life_bar_chart').show();
				$('#data').show();
				$.gameLife.show_hide();
				$("#registered").css("background-color","#99f21b");
				$("#money").css("background-color","");
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/gamePlayer/getGameplayerAnalysis.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  
					    	var life_funnel = document.getElementById('life_funnel');
					    	var life_line = document.getElementById('life_line');
					    	var life_bar = document.getElementById('life_bar');
					    	var indicators = $('#indicators').val();
					    	
					    	var dateArr = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 

					    	tab_dateArr = dateArr;
					    	$.gameLife.pageCssControl();
					    	$.gameLife.brokenFunnel(life_funnel,data.ltvsCharts);
					    	$.gameLife.brokenLineAndBar(life_line,life_bar,data.ltvs,dateArr);
					    	$.gameLife.brokenTable(data.ltvs,data.totals);
					    	$.gameLife.moneyPayTable(data.money,data.money_totals);
					    	
					  }
				});
			},
			pageCssControl:function(){
	    		$("#life_funnel").show();
	    		$("#life_line").show();
	    		$("#life_bar").show();
	    		
	    		$("#life_funnel_data").hide();
	    		$("#life_line_data").hide();
	    		$("#life_bar_data").hide();
				
				$('#dt_lf_wrapper').remove();
				$('#dt_ll_wrapper').remove();
				$('#dt_lb_wrapper').remove();
				
				$(".dataShap li").removeClass("onChoose");
				$(".dataShap li:eq(0)").addClass("onChoose");
				$(".dataShap li:eq(2)").addClass("onChoose");
				$(".dataShap li:eq(4)").addClass("onChoose");
			},
			brokenFunnel:function(cc,ltvsCharts){
				var rate = $("#gameRate").val();
				
				require.config({
					paths: {
						echarts: '/js/echarts/dist/',
						theme:'/js/echarts/theme/'
					}
				});
				require(
					[
						'echarts',
						'echarts/chart/funnel',  
					],
					DrawEChart
				);
				
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var myChart = ec.init(cc,tarTheme);
						
						var dataArr = new Array();
						var max = 0;
						$.each(ltvsCharts,function(i,v){
							if(v.entity.retentionDay == 0 || v.entity.retentionDay == 1 || v.entity.retentionDay == 3 
									|| v.entity.retentionDay == 7 || v.entity.retentionDay == 30){
								var value = Math.round(v.avgLtv / rate * 100)/100;
								var temp = {name:'D'+v.entity.retentionDay+' : '+value,value:value};
								dataArr.push(temp);
								max = Math.max(max,value);
							}
						});
						
						option_funnel.series[0].data = dataArr;
						option_funnel.series[0].max = max;
						
						myChart.setOption(option_funnel,true);
						
						tab_funnel_dataArr = dataArr;
			        });
				}
			},
			brokenTable:function(reports,totals){
				
				$("#data").empty();
				var rate = $("#gameRate").val();
				var currency = $("#gameCurrency").val();
	
				$("#dt_wrapper").remove();
	
				var table = $(".template_cache .dataTable").clone(true);
				
				table.attr("id", "dt");
	
				var headTemp = $("thead tr", table).first();
				if ($("#queryType").val() == 'roleChoice') {
					$("td.head_install", headTemp).text('创角数');
				} else {
					$("td.head_install", headTemp).text('新注册');
				}
				
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
				$.gameLife.dataTables2($('#dt'));
				/*
				$('.top').remove();
				$('.bottom').remove();
				$('.clear').remove();
				$('.DTTT_container').remove();
				
				
				$('#dt').DataTable({
					"sPaginationType" : "full_numbers",
		            "dom" : 'T<"top">rt<"bottom"ip><"clear">' ,
		            "bDestroy" : true,
		            "bPaginate" : true,
		            "bInfo" : true,
					"bSort" : true,
					"bScrollCollapse" : true,
					"bScrollInfinite" : true,
				    "bFilter" : false,
				    "paging": true, //翻页功能
				    "lengthChange": false, //改变每页显示数据数量
				    "searching": false, //过滤功能
				    "ordering": true, //排序功能
				    "order": [[ 0, "desc" ]],
				    "processing": false,
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
					}
				  );
				*/
				$('#dt').removeClass();
				$('#dt').addClass('table table-striped');
				
				$('#dt tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
				
			},
			
			dataTables2:function(div2){
				$('.top').remove();
				$('.bottom').remove();
				$('.clear').remove();
				$('.DTTT_container').remove();
				
				var table2 = div2.DataTable({
					"sPaginationType" : "full_numbers",
		            "dom" : 'T<"top">rt<"bottom"ip><"clear">' ,
		            "bDestroy" : true,
		            "bPaginate" : true,
		            "bInfo" : true,
					"bSort" : true,
					"bScrollCollapse" : true,
					"bScrollInfinite" : true,
				    "bFilter" : false,
				    "paging": true, //翻页功能
				    "lengthChange": false, //改变每页显示数据数量
				    "searching": false, //过滤功能
				    "ordering": true, //排序功能
				    "order": [[ 0, "desc" ]],
				    "processing": false,
		            "lengthMenu": [ 10, 20, 50, 100],
				    "pageLength": 10,
				    "language":{
				    	"url": "/dataTables/chinese.json"
				    },
				    tableTools: {
				    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
		                                        "aButtons":[ 
		                            ]
			        }
				});
				
			    return table2;
			},
			moneyPayTable:function(report,totals){
				$("#data_money").empty();
				var rate = $("#gameRate").val();
				var currency = $("#gameCurrency").val();
	
				$("#dt_wrapper").remove();
	
				var table = $(".template_cache_money .dataTable2").clone(true);
				table.attr("id", "dt1");
	
				var headTemp = $("thead tr", table).first();
				if ($("#queryType").val() == 'roleChoice') {
					$("td.head_install", headTemp).text('创角数');
				} else {
					$("td.head_install", headTemp).text('新注册');
				}
				
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
				
				
				$.each(report,function(i,n){
				
					var trTemp = $("tbody tr",table).first().clone(true);
					var v = n.gameLtv;
					
					if(v.installDay == null || v.installDay == undefined || v.installDay == '-'){
						$("td.installDay",trTemp).text(v.installDay);
						$("td.install",trTemp).text("-");
						for(var j=0;j<=90;j++){
							$("td.D"+(j)+"",trTemp).text('-');
						}
					}else{
						$("td.installDay",trTemp).text(v.installDay);
						$("td.install",trTemp).text(v.install);
						$("td.D0",trTemp).text(Math.round(v.d0/rate*100)/100);
						$("td.D1",trTemp).text(Math.round(v.d1/rate*100)/100);
						$("td.D2",trTemp).text(Math.round(v.d2/rate*100)/100);
						$("td.D3",trTemp).text(Math.round(v.d3/rate*100)/100);
						$("td.D4",trTemp).text(Math.round(v.d4/rate*100)/100);
						$("td.D5",trTemp).text(Math.round(v.d5/rate*100)/100);
						$("td.D6",trTemp).text(Math.round(v.d6/rate*100)/100);
						$("td.D7",trTemp).text(Math.round(v.d7/rate*100)/100);
						$("td.D8",trTemp).text(Math.round(v.d8/rate*100)/100);
						$("td.D9",trTemp).text(Math.round(v.d9/rate*100)/100);
						$("td.D10",trTemp).text(Math.round(v.d10/rate*100)/100);
						$("td.D11",trTemp).text(Math.round(v.d11/rate*100)/100);
						$("td.D12",trTemp).text(Math.round(v.d12/rate*100)/100);
						$("td.D13",trTemp).text(Math.round(v.d13/rate*100)/100);
						$("td.D14",trTemp).text(Math.round(v.d14/rate*100)/100);
						$("td.D15",trTemp).text(Math.round(v.d15/rate*100)/100);
						$("td.D16",trTemp).text(Math.round(v.d16/rate*100)/100);
						$("td.D17",trTemp).text(Math.round(v.d17/rate*100)/100);
						$("td.D18",trTemp).text(Math.round(v.d18/rate*100)/100);
						$("td.D19",trTemp).text(Math.round(v.d19/rate*100)/100);
						$("td.D20",trTemp).text(Math.round(v.d20/rate*100)/100);
						$("td.D21",trTemp).text(Math.round(v.d21/rate*100)/100);
						$("td.D22",trTemp).text(Math.round(v.d22/rate*100)/100);
						$("td.D23",trTemp).text(Math.round(v.d23/rate*100)/100);
						$("td.D24",trTemp).text(Math.round(v.d24/rate*100)/100);
						$("td.D25",trTemp).text(Math.round(v.d25/rate*100)/100);
						$("td.D26",trTemp).text(Math.round(v.d26/rate*100)/100);
						$("td.D27",trTemp).text(Math.round(v.d27/rate*100)/100);
						$("td.D28",trTemp).text(Math.round(v.d28/rate*100)/100);
						$("td.D29",trTemp).text(Math.round(v.d29/rate*100)/100);
						$("td.D30",trTemp).text(Math.round(v.d30/rate*100)/100);
						$("td.D31",trTemp).text(Math.round(v.d31/rate*100)/100);
						$("td.D32",trTemp).text(Math.round(v.d32/rate*100)/100);
						$("td.D33",trTemp).text(Math.round(v.d33/rate*100)/100);
						$("td.D34",trTemp).text(Math.round(v.d34/rate*100)/100);
						$("td.D35",trTemp).text(Math.round(v.d35/rate*100)/100);
						$("td.D36",trTemp).text(Math.round(v.d36/rate*100)/100);
						$("td.D37",trTemp).text(Math.round(v.d37/rate*100)/100);
						$("td.D38",trTemp).text(Math.round(v.d38/rate*100)/100);
						$("td.D39",trTemp).text(Math.round(v.d39/rate*100)/100);
						$("td.D40",trTemp).text(Math.round(v.d40/rate*100)/100);
						$("td.D41",trTemp).text(Math.round(v.d41/rate*100)/100);
						$("td.D42",trTemp).text(Math.round(v.d42/rate*100)/100);
						$("td.D43",trTemp).text(Math.round(v.d43/rate*100)/100);
						$("td.D44",trTemp).text(Math.round(v.d44/rate*100)/100);
						$("td.D45",trTemp).text(Math.round(v.d45/rate*100)/100);
						$("td.D46",trTemp).text(Math.round(v.d46/rate*100)/100);
						$("td.D47",trTemp).text(Math.round(v.d47/rate*100)/100);
						$("td.D48",trTemp).text(Math.round(v.d48/rate*100)/100);
						$("td.D49",trTemp).text(Math.round(v.d49/rate*100)/100);
						$("td.D50",trTemp).text(Math.round(v.d50/rate*100)/100);
						$("td.D51",trTemp).text(Math.round(v.d51/rate*100)/100);
						$("td.D52",trTemp).text(Math.round(v.d52/rate*100)/100);
						$("td.D53",trTemp).text(Math.round(v.d53/rate*100)/100);
						$("td.D54",trTemp).text(Math.round(v.d54/rate*100)/100);
						$("td.D55",trTemp).text(Math.round(v.d55/rate*100)/100);
						$("td.D56",trTemp).text(Math.round(v.d56/rate*100)/100);
						$("td.D57",trTemp).text(Math.round(v.d57/rate*100)/100);
						$("td.D58",trTemp).text(Math.round(v.d58/rate*100)/100);
						$("td.D59",trTemp).text(Math.round(v.d59/rate*100)/100);
						$("td.D60",trTemp).text(Math.round(v.d60/rate*100)/100);
						$("td.D61",trTemp).text(Math.round(v.d61/rate*100)/100);
						$("td.D62",trTemp).text(Math.round(v.d62/rate*100)/100);
						$("td.D63",trTemp).text(Math.round(v.d63/rate*100)/100);
						$("td.D64",trTemp).text(Math.round(v.d64/rate*100)/100);
						$("td.D65",trTemp).text(Math.round(v.d65/rate*100)/100);
						$("td.D66",trTemp).text(Math.round(v.d66/rate*100)/100);
						$("td.D67",trTemp).text(Math.round(v.d67/rate*100)/100);
						$("td.D68",trTemp).text(Math.round(v.d68/rate*100)/100);
						$("td.D69",trTemp).text(Math.round(v.d69/rate*100)/100);
						$("td.D70",trTemp).text(Math.round(v.d70/rate*100)/100);
						$("td.D71",trTemp).text(Math.round(v.d71/rate*100)/100);
						$("td.D72",trTemp).text(Math.round(v.d72/rate*100)/100);
						$("td.D73",trTemp).text(Math.round(v.d73/rate*100)/100);
						$("td.D74",trTemp).text(Math.round(v.d74/rate*100)/100);
						$("td.D75",trTemp).text(Math.round(v.d75/rate*100)/100);
						$("td.D76",trTemp).text(Math.round(v.d76/rate*100)/100);
						$("td.D77",trTemp).text(Math.round(v.d77/rate*100)/100);
						$("td.D78",trTemp).text(Math.round(v.d78/rate*100)/100);
						$("td.D79",trTemp).text(Math.round(v.d79/rate*100)/100);
						$("td.D80",trTemp).text(Math.round(v.d80/rate*100)/100);
						$("td.D81",trTemp).text(Math.round(v.d81/rate*100)/100);
						$("td.D82",trTemp).text(Math.round(v.d82/rate*100)/100);
						$("td.D83",trTemp).text(Math.round(v.d83/rate*100)/100);
						$("td.D84",trTemp).text(Math.round(v.d84/rate*100)/100);
						$("td.D85",trTemp).text(Math.round(v.d85/rate*100)/100);
						$("td.D86",trTemp).text(Math.round(v.d86/rate*100)/100);
						$("td.D87",trTemp).text(Math.round(v.d87/rate*100)/100);
						$("td.D88",trTemp).text(Math.round(v.d88/rate*100)/100);
						$("td.D89",trTemp).text(Math.round(v.d89/rate*100)/100);
						$("td.D90",trTemp).text(Math.round(v.d90/rate*100)/100);
					}
					
					$('tbody',table).append(trTemp); 
			    });
				
				$("tbody tr",table).first().remove();
				$('#data_money').append(table); 
				$.gameLife.dataTables2($('#dt1'));
//				var hiddenColumn = [33,34,35,36,37,38,39,40,
//				                    41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,
//				                    56,57,58,59,60,61,63,64,65,66,67,68,69,70,
//				                    71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,
//				                    86,87,88,89,90,91];
				/*
				$('#dt1').DataTable({
					"sPaginationType" : "full_numbers",
//		            "aoColumnDefs": [{  "bSearchable": false, 
//		            					"bVisible": false, 
//		            					"aTargets": hiddenColumn }] ,
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
				*/
				$('#dt1').removeClass();
				$('#dt1').addClass('table table-striped');
				
				$('#dt1 tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
				
				
				
			},
			brokenLineAndBar:function(cc,dd,ltvs,dateArr){

				require.config({
					paths: {
						echarts: '/js/echarts/dist/',
						theme:'/js/echarts/theme/'
					}
				});
				require(
					[
						'echarts',
						'echarts/chart/bar',  
						'echarts/chart/line',
					],
					DrawEChart
				);
				
				
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var myChart = ec.init(cc,tarTheme);
						option_line.legend={data:['D0','D1','D3','D7','D30']};
						option_line.xAxis = [{data:dateArr[0],type:'category'}];
						option_line.yAxis = [{type:'value'}];
						
						var rate = $("#gameRate").val();
						var currency = $("#gameCurrency").val();
						option_line.title = {text:'注收比', subtext:'单位('+currency+')'};
						
						var D0 = new Array(), D1 = new Array(),D3 = new Array(),D7 = new Array(),D30 = new Array();
						var D0_all = new Array(), D1_all = new Array(),D3_all = new Array(),D7_all = new Array(),D30_all = new Array();
						var install_arr = new Array();
						$.each(dateArr[1],function(i,d){
							var isNotExist = true;
							$.each(ltvs,function(j,ltv){
								if(d == ltv.gameLtv.installDay){
									if(ltv.gameLtv.install== null || ltv.gameLtv.install == undefined
											|| ltv.gameLtv.install == 0 || ltv.gameLtv.install == '-'){
										return false;
									}
									D0.push(Math.round((ltv.gameLtv.d0 / ltv.gameLtv.install / rate) * 100)/100);
									D1.push(Math.round((ltv.gameLtv.d1 / ltv.gameLtv.install / rate) * 100)/100);
									D3.push(Math.round((ltv.gameLtv.d3 / ltv.gameLtv.install / rate) * 100)/100);
									D7.push(Math.round((ltv.gameLtv.d7 / ltv.gameLtv.install / rate) * 100)/100);
									D30.push(Math.round((ltv.gameLtv.d30 / ltv.gameLtv.install / rate) * 100)/100);
									D0_all.push(Math.round((ltv.gameLtv.d0 / rate) * 100)/100);
									D1_all.push(Math.round((ltv.gameLtv.d1 / rate) * 100)/100);
									D3_all.push(Math.round((ltv.gameLtv.d3 / rate) * 100)/100);
									D7_all.push(Math.round((ltv.gameLtv.d7 / rate) * 100)/100);
									D30_all.push(Math.round((ltv.gameLtv.d30 / rate) * 100)/100);
									install_arr.push(ltv.gameLtv.install);
									isNotExist = false;
								}
							});
							if(isNotExist){
								D0.push(0);
								D1.push(0);
								D3.push(0);
								D7.push(0);
								D30.push(0);
								
								D0_all.push(0);
								D1_all.push(0);
								D3_all.push(0);
								D7_all.push(0);
								D30_all.push(0);
								install_arr.push(0);
							}
						});
						
						
						option_line.series = [
						                 {name:'D0',type:'line',data:D0},
						                 {name:'D1',type:'line',data:D1},
						                 {name:'D3',type:'line',data:D3},
						                 {name:'D7',type:'line',data:D7},
						                 {name:'D30',type:'line',data:D30}
						                 ];
						
						var formatterStr = "{b} <br/>{a} : {c} "+currency+"<br/>{a1} : {c1} "+currency;
						formatterStr +="<br/>{a2} : {c2} "+currency+"<br/>{a3} : {c3} "+currency+"<br/>{a4} : {c4} "+currency;
						option_line.tooltip = {trigger: 'axis',
								formatter: formatterStr};
						myChart.setOption(option_line,true);
						
						
						var myChart_bar = ec.init(dd,tarTheme);
						var install_name='注册';
						if ($("#queryType").val() == 'roleChoice') {
							install_name = '创角';
						}
						
						option_bar.legend={data:['D0','D1','D3','D7','D30',install_name]};
						option_bar.xAxis = [{data:dateArr[0],type:'category'}];
						option_bar.yAxis = [
						                    {type : 'value', name : '累计收入',axisLabel : {formatter: '{value} '+currency+''}},
							                {type : 'value',name : install_name,axisLabel : {formatter: '{value} 个'}}
							                ];
						option_bar.series = [
						                         {name:'D0',type:'bar',data:D0_all},
								                 {name:'D1',type:'bar',data:D1_all},
								                 {name:'D3',type:'bar',data:D3_all},
								                 {name:'D7',type:'bar',data:D7_all},
								                 {name:'D30',type:'bar',data:D30_all},
								                 {name:install_name,yAxisIndex:1,type:'line',data:install_arr}
								                 ];
						//option_bar.dataZoom = {show : true,realtime: true,start : 0,end : 10/dateArr[1].length*100,zoomLock:true};
						option_bar.dataZoom = {show : true,realtime: true,start : 80,end : 100,zoomLock:true};
						
						var formatterStr1 = "{b} <br/>{a} : {c} "+currency+"<br/>{a1} : {c1} "+currency;
						formatterStr1 += "<br/>{a2} : {c2} "+currency+"<br/>{a3} : {c3} "+currency+"<br/>{a4} : {c4} "+currency+"<br/>{a5} : {c5} 个";
						option_bar.tooltip = {trigger: 'axis',
								formatter: formatterStr1};
						myChart_bar.setOption(option_bar,true);
						
						tab_D0 = D0,tab_D1 = D1,tab_D3 = D3,tab_D7 = D7,tab_D30 = D30;
						tab_D0_all = D0_all,tab_D1_all = D1_all,tab_D3_all = D3_all,tab_D7_all = D7_all,tab_D30_all = D30_all;
						
						tab_install_arr = install_arr;
			        });
				}
			},
			brokenFunnelTable:function(){
				$('#dt_lf_wrapper').remove();
				var table = $(".template_cache .life_funnel_table").clone(true);
				table.attr("id", "dt_lf");
				$('#life_funnel_data').append(table);
				
				var headTemp = $("thead tr",table).first();
				var value = $("#queryType").val();
				if(value == 'roleChoice'){
					$("td.head_install",headTemp).text('按创角-注收比');
				}else{
					$("td.head_install",headTemp).text('按注册-注收比');
				}
				
				$.each(tab_funnel_dataArr,function(i,v){
					if(v == '-'){
						return true;
					}
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.install", trTemp).text(v.name.split(":")[0]);
					$("td.rate", trTemp).text(v.value+'%');

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("life_funnel_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_lf'));
				$('#dt_lf tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
			},
			brokenLineTable:function(){
				$('#dt_ll_wrapper').remove();
				var table = $(".template_cache .life_line_table").clone(true);
				table.attr("id", "dt_ll");
				$('#life_line_data').append(table);
				
				var headTemp = $("thead tr",table).first();
				var value = $("#queryType").val();
				if(value == 'roleChoice'){
					$("td.head_install",headTemp).text('日期（按创角）');
				}else{
					$("td.head_install",headTemp).text('日期（按注册）');
				}
				
				for(var i=0;i<tab_dateArr[1].length;i++){
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.installDay", trTemp).text(tab_dateArr[1][i]);
					$("td.D0", trTemp).text(tab_D0[i]);
					$("td.D1", trTemp).text(tab_D1[i]);
					$("td.D3", trTemp).text(tab_D3[i]);
					$("td.D7", trTemp).text(tab_D7[i]);
					$("td.D30", trTemp).text(tab_D30[i]);

					$('tbody', table).append(trTemp);
				}
				
				table.removeClass("life_line_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_ll'));
				$('#dt_ll tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
			},
			brokenBarTable:function(){
				$('#dt_lb_wrapper').remove();
				var table = $(".template_cache .life_bar_table").clone(true);
				table.attr("id", "dt_lb");
				$('#life_bar_data').append(table);
				
				var headTemp = $("thead tr",table).first();
				var value = $("#queryType").val();
				if(value == 'roleChoice'){
					$("td.head_install",headTemp).text('创角数');
				}else{
					$("td.head_install",headTemp).text('新注册');
				}
				
				for(var i=0;i<tab_dateArr[1].length;i++){
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.installDay", trTemp).text(tab_dateArr[1][i]);
					$("td.install", trTemp).text(tab_install_arr[i]);
					$("td.D0", trTemp).text(tab_D0_all[i]);
					$("td.D1", trTemp).text(tab_D1_all[i]);
					$("td.D3", trTemp).text(tab_D3_all[i]);
					$("td.D7", trTemp).text(tab_D7_all[i]);
					$("td.D30", trTemp).text(tab_D30_all[i]);

					$('tbody', table).append(trTemp);
				}
				
				table.removeClass("life_bar_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_lb'));
				$('#dt_lb tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
			},
			brokenAjaxTable:function(ps){
				$("#Download_reg").hide();
				var colorArr = ['#D9FFFD','#97FFF4','#01E7FA',
				                '#01D6E7','#01D1F5','#01C8EB','#39A8F9',
				                '#269FF9','#1899F8','#078EF1','#FC324B'];
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				
				var type = $("#indicators").val();
				var value = $("#queryType").val();
				var rate = $("#gameRate").val();
				$('#ajax_data').show();
				$('#life_funnel_chart').hide();
				$('#life_line_chart').hide();
				$('#life_bar_chart').hide();
				$('#data').hide();
				
				var table = $(".template_cache .ajax_table").clone(true);
				var headTemp = $("thead tr",table).first();
				$("#Download_money").hide();
				$("#Download_reg").hide();
				$("#registered").hide();
				$("#money").hide();
				if("source" == type){
					
					$("td.head_type",headTemp).text('渠道');
				}else{
					$("td.head_type",headTemp).text('服务器');
				}
				if(value == 'roleChoice'){
					$("td.head_install",headTemp).text('创角数');
				}else{
					$("td.head_install",headTemp).text('新注册');
				}
				table.attr("id", "dt_ajax");
				$('#ajax_data').append(table);
				
				var columns = new Array();
				var columnDefs = new Array();
				columns.push({
					"data": "gameLtv.installDay"
				});
				columns.push({
					"data": "gameLtv.sourceOrClientName"
				});
				columns.push({
					"data": "gameLtv.install"
				});
				for(var i=3;i<=90;i++){
					columns.push({
						"data": null
					});
				}
				for(var j=0;j<=90;j++){
					columnDefs.push({
	               	 targets: j+3,
	            	 render: function (a, b, c, d) {
	            		 return c.gameLtv.install == 0 ? 0 : Math.round((c.gameLtv.gameltvs[d.col-3]/c.gameLtv.install/rate) * 100)/100;
	            	 }
	             });
				}
				
				$('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
					          data: function(d){
					        	  d.id=ps.id;
					        	  d.snid=ps.snid;
					        	  d.gameId=ps.gameId;
					        	  d.indicators=ps.indicators;
					        	  d.queryType=ps.queryType;
					        	  d.beginTime=ps.beginTime;
					        	  d.endTime=ps.endTime;
					        	  d.source=ps.source;
					        	  d.clientid=ps.clientid;
					        	  d.view=ps.view;
					          },
							  type: "POST",
							  url:"/panel_bi/gamePlayer/getGameplayerAnalysis.ac"
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
                        $.each(row.children,function(k,td){
                    		if(k<3){
                    			return true;
                    		}
                    		var val = data.gameLtv.gameltvs[k-2]*100;
                			if(val >= 100){
                				$(td).css('background-color',colorArr[colorArr.length-1]);
    						}else if(val > 0 && val < 100){
    							var index = Math.floor(val / 10);
    							$(td).css('background-color',colorArr[index]);
    						}
                        });
                    },
                    columns: columns,
			        columnDefs: columnDefs
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				
				$('#dt_ajax button').click(function (){
			        $("#downType").val("source");
			        $(this).attr('disabled',true);
			        $(this).val(2);
			        var source = $('#s_c').val();
			        $("#source2").val("");
			        window.btnTimeout=setInterval(function(){
						$.gameUtil.btnTimeout($('#dt_ajax button'),window.btnTimeout);
					  },1000);
			        $("#downLoadForm").submit();
			    });
				
				$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
			}
	};	
	
	
});
	                    