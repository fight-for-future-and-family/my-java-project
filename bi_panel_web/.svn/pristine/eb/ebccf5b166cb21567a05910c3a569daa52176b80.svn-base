$(function(){
	
	option_funnel = {
		    title : {text: '留存',subtext: '平均值(%)',x:15,y:8},
		    tooltip : {trigger: 'item', formatter: "{a} <br/>{b} : {c}%"},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : { mark : {show: true},restore : {show: true},saveAsImage : {show: true} }
		    },
//		    legend: {
//		        data : ['D1','D2','D3','D4','D5','D6','D7','D14','D21','D30']
//		    },
		    calculable : true,
		    series : [
		        {
		            name:'留存',
		            type:'funnel',
		            x: '2%',y: 60,
		            //x2: 80,
		            y2: 60, width: '100%',
		            // height: {totalHeight} - y - y2,
		            min: 0,max: 100,
		            minSize: '0%',maxSize: '70%',
		            sort : 'descending', 
		            gap : 10,
		            itemStyle: {
		                normal: {
		                    borderColor: '#fff',borderWidth: 1,
		                    label: {show: true,position: 'inside',formatter: "{b} : {c} (%)"},
		                    labelLine: {show: false,length: 10,lineStyle: {width: 1,type: 'solid'}}
		                },
		                emphasis: {
		                    borderColor: 'red',borderWidth: 5,
		                    label: {show: true, formatter: '{b}:{c}%', textStyle:{fontSize:20}},
		                    labelLine: {show: true}
		                }
		            },
		            data:[]
		        }
		    ]
		};
	
	option_x = {
		    title :{text : '留存生命周期',subtext : '可用辅助线预测测量'},
		    tooltip : {trigger: 'axis',formatter: '{b}\n{c}%'},
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
	
	option_y = {
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }
		};
	
	var tab_funnel_dataArr = null;
	$(document).ready(function() {
	    $.gameRetention.init();
	    $.timeZone.showTimeZone();
	    $.gameRetention.submit();
	});
	
	$.gameRetention={
		init:function(){
			$.gameRetention.initEvent();
		},
		initEvent:function(){
			$("#query").click(function(){
				$.gameRetention.submit();
		    });
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.gameRetention.redict($(n).attr("id"));
				});
			});
			
			$("#indicators").change(function(){
				$.gameUtil.change($.gameRetention);
			});
			
			$("#queryType").change(function(){
				if($("#queryType").val() == 'roleChoice'){
					$("#indicators option:last").hide();
				}else{
					$("#indicators option:last").show();
				}
				$("#indicators").val('all');
				$("#s_c_span").text('');
				$.gameRetention.submit();
			});
			
			$("#ir_funnel_chart .dataShap li").each(function(i,n){
				$(n).click(function(){
					$("#ir_funnel_chart .dataShap li").removeClass("onChoose");
					$(n).addClass("onChoose");
					
					if($("em",n).attr("class") == 'columnar'){
						$("#ir_funnel").fadeIn();
						$("#ir_funnel_data").fadeOut();
						$("#ir_funnel_chart .dataShap ul").css('right','80px');
						$("#ir_funnel_chart .dataShap ul").css('top','19px');
					}else{
						$("#ir_funnel").fadeOut();
						$("#ir_funnel_chart .dataShap ul").css('right','85px');
						$("#ir_funnel_chart .dataShap ul").css('top','28px');
						if($("#dt_ir_funnel_wrapper").length > 0){
							$("#ir_funnel_data").fadeIn();
						}else{
							$.gameRetention.brokenFunnelTable('ir_funnel');
							$("#ir_funnel_data").fadeIn();
						}
					}
				});
			});
				
				
				$("#ir_x_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#ir_x_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#ir_x").fadeIn();
							$("#ir_x_data").fadeOut();
							$("#ir_x_chart .dataShap ul").css('right','80px');
							$("#ir_x_chart .dataShap ul").css('top','19px');
						}else{
							$("#ir_x").fadeOut();
							$("#ir_x_chart .dataShap ul").css('right','85px');
							$("#ir_x_chart .dataShap ul").css('top','28px');
							if($("#dt_ir_x_wrapper").length > 0){
								$("#ir_x_data").fadeIn();
							}else{
								$.gameRetention.brokenFunnelTable('ir_x');
								$("#ir_x_data").fadeIn();
							}
						}
					});
			});
			
		},
//		redict:function(view){
//			window.location.href='/panel_bi/gamePlayer/toGameplayerAnalysis.ac?view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/gamePlayer/toGameplayerAnalysis.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		submit:function(){
			var ps = $.gameUtil.checkParam();
			if(ps == null){return;}
			
			if($("#s_c").val() == '-99'){
    			$.gameRetention.brokenAjaxTable(ps);
    			return;
			}
			
			$('#ajax_data').hide();
			$('#ir_funnel_chart').show();
			$('#ir_x_chart').show();
			$('#ir_y').show();
			$('#data').show();
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/gamePlayer/getGameplayerAnalysis.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
					  
				    	var ir_funnel = document.getElementById('ir_funnel');
				    	var ir_x = document.getElementById('ir_x');
				    	var ir_y = document.getElementById('ir_y');
				    	var indicators = $('#indicators').val();
				    	
				    	var dateArr = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 
				    	
				    	$.gameRetention.pageCssControl();
				    	$.gameRetention.brokenCharts(ir_funnel,ir_x,data.ltvsCharts,dateArr);
				    	$.gameRetention.brokenTableAndLine(ir_y,data.ltvs,dateArr);
				  }
			});
		},
		pageCssControl:function(){
    		$("#ir_funnel").show();
    		$("#ir_x").show();
    		
    		$("#ir_funnel_data").hide();
    		$("#ir_x_data").hide();
			
			$('#dt_ir_funnel_wrapper').remove();
			$('#dt_ir_x_wrapper').remove();
			
			$(".dataShap li").removeClass("onChoose");
			$(".dataShap li:eq(0)").addClass("onChoose");
			$(".dataShap li:eq(2)").addClass("onChoose");
		},
		brokenCharts:function(funnelDiv,optionxDiv,irFunnelList,dateArr){
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
					'echarts/chart/funnel'
				],
				DrawEChart
			);
			

				function DrawEChart(ec) {
					require([ 'theme/macarons' ], function(tarTheme) {
					var funnel = ec.init(funnelDiv, tarTheme);
					var line = ec.init(optionxDiv, tarTheme);

					var dataArr = [ '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' ];
					var max = 0;
					$.each(irFunnelList, function(i, v) {
						if(!((v.entity.retentionDay>=1 && v.entity.retentionDay <=7)
								|| v.entity.retentionDay == 7 || v.entity.retentionDay==14
								|| v.entity.retentionDay==21 ||v.entity.retentionDay==30)){
							return true;
						}
						var value = v.avgRetentionRate * 100;
						value = Math.round(value * 100) / 100;
						max = Math.max(max,value);
						var temp = {
							name : 'D' + v.entity.retentionDay,
							value : value
						};

						if (v.entity.retentionDay == 1) {
							dataArr[0] = temp
						} else if (v.entity.retentionDay == 2) {
							dataArr[1] = temp
						} else if (v.entity.retentionDay == 3) {
							dataArr[2] = temp
						} else if (v.entity.retentionDay == 4) {
							dataArr[3] = temp
						} else if (v.entity.retentionDay == 5) {
							dataArr[4] = temp
						} else if (v.entity.retentionDay == 6) {
							dataArr[5] = temp
						} else if (v.entity.retentionDay == 7) {
							dataArr[6] = temp
						} else if (v.entity.retentionDay == 14) {
							dataArr[7] = temp
						} else if (v.entity.retentionDay == 21) {
							dataArr[8] = temp
						} else if (v.entity.retentionDay == 30) {
							dataArr[9] = temp
						}
					});

					option_funnel.series[0].data = dataArr;
					option_funnel.series[0].max = max;
					
					option_x.xAxis = [ {
						type : 'category',
						data : [ 'D1', 'D2', 'D3', 'D4', 'D5', 'D6', 'D7',
								'D14', 'D21', 'D30' ]
					} ];
					option_x.yAxis = [ {
						type : 'value',
						axisLabel : {
							formatter : '{value} %'
						},
						boundaryGap : [ 0, 0.01 ]
					} ];
					option_x.series = [ {
						name : '留存',
						type : 'line',
						data : dataArr
					} ];

					funnel.setOption(option_funnel, true);
					line.setOption(option_x, true);
					
					tab_funnel_dataArr = dataArr;
				});
			}
		},
		brokenTableAndLine:function(ir_y,reports,dateArr){
			
			var ir_arr = new Array();
			var date_install_arr = new Array();
			
			var ir_arr = new Array();
			
			
			$.each(dateArr[1],function(i,d){
				var isHaveDate = false;
				$.each(reports,function(j,ltv){
					var gl = ltv.gameLtv;
					if(d == gl.installDay){
						if(gl.install== null || gl.install == undefined){
							var date_install_temp = [gl.installDay,"-"];
							var data = ["-","-","-","-","-","-","-","-","-","-",
							              "-","-","-","-","-","-","-","-","-","-",
							              "-","-","-","-","-","-","-","-","-","-",
							              "-","-","-","-","-","-","-","-","-","-",
							              "-","-","-","-","-","-","-","-","-","-",
							              "-","-","-","-","-","-","-","-","-","-",
							              "-","-","-","-","-","-","-","-","-","-",
							              "-","-","-","-","-","-","-","-","-","-",
							              "-","-","-","-","-","-","-","-","-","-"];
							ir_arr.push(data);
							date_install_arr.push(date_install_temp);
						}else{
							var data = [Math.round(ltv.gameLtv.d1 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d2 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d3 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d4 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d5 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d6 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d7 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d8 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d9 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d10 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d11 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d12 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d13 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d14 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d15 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d16 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d17 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d18 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d19 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d20 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d21 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d22 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d23 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d24 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d25 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d26 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d27 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d28 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d29 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d30 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d31 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d32 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d33 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d34 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d35 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d36 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d37 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d38 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d39 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d40 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d41 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d42 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d43 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d44 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d45 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d46 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d47 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d48 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d49 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d50 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d51 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d52 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d53 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d54 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d55 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d56 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d57 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d58 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d59 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d60 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d61 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d62 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d63 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d64 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d65 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d66 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d67 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d68 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d69 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d70 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d71 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d72 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d73 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d74 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d75 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d76 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d77 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d78 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d79 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d80 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d81 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d82 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d83 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d84 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d85 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d86 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d87 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d88 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d89 * 100 * 100) / 100,
										Math.round(ltv.gameLtv.d90 * 100 * 100) / 100
							            ];
							
							var date_install_temp = [gl.installDay,gl.install];
							
							ir_arr.push(data);
							date_install_arr.push(date_install_temp);
						}
						isHaveDate = true;
					}
				});
				if(!isHaveDate){
					var date_install_temp = [d,"-"];
					var data = ["-","-","-","-","-","-","-","-","-","-",
					              "-","-","-","-","-","-","-","-","-","-",
					              "-","-","-","-","-","-","-","-","-","-",
					              "-","-","-","-","-","-","-","-","-","-",
					              "-","-","-","-","-","-","-","-","-","-",
					              "-","-","-","-","-","-","-","-","-","-",
					              "-","-","-","-","-","-","-","-","-","-",
					              "-","-","-","-","-","-","-","-","-","-",
					              "-","-","-","-","-","-","-","-","-","-"];
					ir_arr.push(data);
					date_install_arr.push(date_install_temp);
				}
			});
			
            //画趋势表
            $.gameRetention.brokenLine(ir_y,ir_arr,dateArr[0]);
            
			//画数据表
            $.gameRetention.brokenTable(date_install_arr,ir_arr);
		},
		brokenLine:function(cc,dataArr,dateArr){

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
					'echarts/chart/line'
				],
				DrawEChart
			);
			
			
			function DrawEChart(ec){
				require(['theme/macarons'], function(tarTheme){
					var myChart = ec.init(cc,tarTheme);
					
					option_y.xAxis = [{type:'category',data:dateArr}];
					option_y.yAxis = [{type:'value',axisLabel : {formatter: '{value} %'},boundaryGap: [0, 0.01]}];
					
					option_y.title = {text:'玩家留存趋势',subtext:'单位（%）'};
					
					var option_y_legend = ['D1','D2','D3','D4','D5','D6','D7','D14','D21','D30'];
					var option_indi = [0,1,2,3,4,5,6,13,20,29];
					var option_y_series = new Array();
					
					for(var j=0;j<option_indi.length;j++){
						var data = new Array();
						for(var k=0;k<dataArr.length;k++){
							data.push(dataArr[k][option_indi[j]]);
						}
						var op = {
								name:option_y_legend[j],
								type:'line',
								data:data
						};
						option_y_series.push(op);
					}
					
					option_y.legend={data:option_y_legend};
					option_y.series = option_y_series;
					
					var formatterStr = "{b} <br/>{a} : {c} %<br/>{a1} : {c1} %<br/>{a2} : {c2} %";
					formatterStr += "<br/>{a3} : {c3} %<br/>{a4} : {c4} %<br/>{a5} : {c5} %" +
							"<br/>{a6} : {c6} %<br/>{a7} : {c7} %<br/>{a8} : {c8} %" +
							"<br/>{a9} : {c9} %";
					option_y.tooltip = {trigger: 'axis',
							formatter: formatterStr};
					myChart.setOption(option_y,true);
		        });
			}
		},
		brokenTable:function(date_install_arr,ir_arr){
			
			var colorArr = ['#D9FFFD','#97FFF4','#01E7FA',
            '#01D6E7','#01D1F5','#01C8EB','#39A8F9',
            '#269FF9','#1899F8','#078EF1','#FC324B'];
			
			 $("#dt_wrapper").remove();
				
			var table = $(".template_cache .view_table").clone(true);
			table.attr("id","dt");
			
			var headTemp = $("thead tr",table).first();
			var value = $("#queryType").val();
			if(value == 'roleChoice'){
				$("td.head_install",headTemp).text('创角数');
			}else{
				$("td.head_install",headTemp).text('新注册');
			}
			
			$.each(ir_arr,function(i,v){
				if(isNaN(date_install_arr[i][1])){
					return true;
				}
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.date",trTemp).text(date_install_arr[i][0]);
				$("td.install",trTemp).text(date_install_arr[i][1]);
				
				for(var j=0;j<90;j++){
					var td = $("td.d"+(j+1)+"",trTemp);
					td.text(v[j]+'%');
					
					if(v[j] >= 100){
						td.css('background-color',colorArr[colorArr.length-1]);
					}else if(v[j] > 0 && v[j] < 100){
						var index = Math.floor(v[j] / 10);
						td.css('background-color',colorArr[index]);
					}
				}
				
				$('tbody',table).append(trTemp); 
			});
			
			$("tbody tr",table).first().remove();
			$('#data').append(table); 
			
//			var hiddenColumn = [9,10,11,12,13,14,16,17,18,19,20,21,23,24,
//			                    25,26,27,28,29,30,32,33,34,35,36,37,38,39,40,
//			                    41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,
//			                    56,57,58,59,60,62,63,64,65,66,67,68,69,70,
//			                    71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,
//			                    86,87,88,89,90];
			
			$('#dt').DataTable({
				"sPaginationType" : "full_numbers",
//	            "aoColumnDefs": [{  "bSearchable": false, 
//	            					"bVisible": false, 
//	            					"aTargets": hiddenColumn }] ,
	            "dom" : 'T<"clear"><"top">rt<"bottom"ip><"clear">' ,
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
		brokenFunnelTable:function(div){
			$('#dt_'+div+'_wrapper').remove();
			var table = $(".template_cache .ir_funnel_table").clone(true);
			table.attr("id", "dt_"+div);
			$('#'+div+'_data').append(table);
			
			var headTemp = $("thead tr",table).first();
			var value = $("#queryType").val();
			if(value == 'roleChoice'){
				$("td.head_install",headTemp).text('创角留存');
			}else{
				$("td.head_install",headTemp).text('新注册留存');
			}
			
			$.each(tab_funnel_dataArr,function(i,v){
				if(v == '-'){
					return true;
				}
				var trTemp = $("tbody tr", table).first().clone(true);
				$("td.install", trTemp).text(v.name);
				$("td.rate", trTemp).text(v.value+'%');

				$('tbody', table).append(trTemp);
			});
			
			table.removeClass("ir_funnel_table").addClass("table table-striped");
			$("tbody tr", table).first().remove();
			
			$.biDataTables.dataTables_chart($('#dt_'+div));
			$('#dt_'+div+' tbody tr').click(function (){
			     $(this).toggleClass('highlight');
			 });
		},
		brokenAjaxTable:function(ps){
			var colorArr = ['#D9FFFD','#97FFF4','#01E7FA',
			                '#01D6E7','#01D1F5','#01C8EB','#39A8F9',
			                '#269FF9','#1899F8','#078EF1','#FC324B'];
			var selected = [];
			$('#dt_ajax_wrapper').remove();
			
			var type = $("#indicators").val();
			var value = $("#queryType").val();
			var rate = $("#gameRate").val();
			$('#ajax_data').show();
			$('#ir_funnel_chart').hide();
			$('#ir_x_chart').hide();
			$('#ir_y').hide();
			$('#data').hide();
			
			var table = $(".template_cache .ajax_table").clone(true);
			var headTemp = $("thead tr",table).first();
			
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
			for(var j=0;j<90;j++){
				columnDefs.push({
               	 targets: j+3,
            	 render: function (a, b, c, d) {
            		 return Math.round(c.gameLtv.gameltvs[d.col-2] * 100 * 100) / 100 + '%';
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
		        $(this).attr('disabled',true);
		        $(this).val(2);
		        window.btnTimeout=setInterval(function(){
					$.gameUtil.btnTimeout($('#dt_ajax button'),window.btnTimeout);
				  },1000);
		        $("#downLoadForm").submit();
		    });
			
			$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
		},
	}
	
	var downloadData={
		toServer:function(){
			var reportDatas=this.getData();
			var params={
				reportDatas:reportDatas
			};
			$.ajax({
				
			});
		},
		getData:function(){
			var tableData=$("#dt");
			var reportDatas=new Array();
			$("tr",tableData).each(function(i,n){
				var tdArr=new Array();
				$("td",n).each(function(j,m){
					tdArr.push($(n).text());
				});
				reportDatas.push(tdArr);
			});
		}
	};
});
	                    