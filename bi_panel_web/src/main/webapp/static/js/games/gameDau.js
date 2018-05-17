$(function(){
	
	option = {
		    title :{ text : '玩家数据',subtext : '新注册'},
		    tooltip : {trigger: 'axis',formatter: '{b}\n{c}个'},
		    toolbox: {
		    	show : true,y:'top',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }
		};
	
	option_pie = {
			title:{text:'分渠道活跃',subtext:'单位（%）',x:10,y:20},
		    tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                    	x: '15%',
		                    	y:'28%',
		                        width: '60%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'数据来源',
		            type:'pie',
		            radius : '55%',
		            center: ['45%', '60%'],
		            data:[{value:335, name:'渠道1'}]
		        }
		    ]
		};
	
	optionCumuli = {
			title:{text:'新老玩家活跃对比',subtext:'单位（个）',x:70,y:15},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    dataZoom : {
		        show : true,
		        realtime: true,
		        start : 80,
		        end : 100,
		        zoomLock:true
		    },
		    legend: { data:['老玩家', '新玩家'],x:'47%',y:19},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['stack', 'tiled']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [{type : 'category',data:['1','2','3','4','5','6','7']}],
		    yAxis : [{type : 'value',boundaryGap: [0, 0.01]}],
		    series : [
		        {
		            name:'老玩家',
		            type:'bar',
		            stack: '总量',
		            itemStyle : { normal: {
		            	label : {
		            		show: true, 
		            		position: 'insideRight',
		            		textStyle:{color:'#000'}
		            			}}},
		            data:[320, 302, 301, 334, 390, 330, 320]
		        },
		        {
		            name:'新玩家',
		            type:'bar',
		            stack: '总量',
		            itemStyle : { normal: {label : {show: true, position: 'insideRight',textStyle:{color:'#000'}}}},
		            data:[120, 132, 101, 134, 90, 230, 210]
		        }
		    ]
		};
		                    
	
	optionMashup = {
			tooltip : {
		        trigger: 'axis'
		    },
		    toolbox: {
		        show : true,orient:'horizontal',x:'right',y:'top',
		        feature : {
		            mark : {show: true},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    legend: {
		        data:['最高在线人数','平均在线人数','平均在线时长']
		    },
		    dataZoom : {
		        show : true,
		        realtime: true,
		        start : 80,
		        end : 100,
		        zoomLock:true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : '人数',
		            axisLabel : {
		                formatter: '{value} 个'
		            }
		        },
		        {
		            type : 'value',
		            name : '时长',
		            axisLabel : {
		                formatter: '{value} 分'
		            }
		        }
		    ],
		    series : [

		        {
		            name:'最高在线人数',
		            type:'bar',
		            data:[20, 49, 70, 232, 256, 767, 1356, 1622, 326, 200, 64, 33]
		        },
		        {
		            name:'平均在线人数',
		            type:'bar',
		            data:[16, 29, 50, 204, 217, 707, 1156, 1222, 287, 188, 60, 23]
		        },
		        {
		            name:'平均在线时长',
		            type:'line',
		            yAxisIndex: 1,
		            data:[200, 220, 330, 450, 630, 102, 203, 234, 230, 165, 120, 62]
		        }
		    ]
		};
	
	
	var tab_dateArr = null;
	var tab_reports = null;
	var tab_pie_data = null;
	var tab_newArr = null;
	var tab_oldArr = null;
	var tab_pie_count = 0;
	$(document).ready(function() {
	    $.gameDau.init();
	});
		
	$.gameDau={
			init:function(){
				$.gameDau.initEvent();
				$.timeZone.showTimeZone();
				$.gameDau.submit();
			},
			initEvent:function(){
				$("#query").click(function(){
					$.gameDau.submit();
			    });
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gameDau.redict($(n).attr("id"));
					});
				});
				$("#indicators").change(function(){
					$.gameUtil.change($.gameDau);
				});
				
				$("#dau_sc_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#dau_sc_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#dau_sc").fadeIn();
							$("#dau_sc_data").fadeOut();
							$("#dau_sc_chart .dataShap ul").css('right','80px');
							$("#dau_sc_chart .dataShap ul").css('top','19px');
						}else{
							$("#dau_sc").fadeOut();
							$("#dau_sc_chart .dataShap ul").css('right','85px');
							$("#dau_sc_chart .dataShap ul").css('top','28px');
							if($("#dt_dau_sc_wrapper").length > 0){
								$("#dau_sc_data").fadeIn();
							}else{
								$.gameDau.brokenPieTable();
								$("#dau_sc_data").fadeIn();
							}
						}
					});
				});
				$("#dau_sb_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#dau_sb_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						var indicators = $('#indicators').val();
						
						if($("em",n).attr("class") == 'columnar'){
							$("#dau_sb").fadeIn();
							$("#dau_sb_data").fadeOut();
							if(indicators == 'all'){
								$("#dau_sb_chart .dataShap ul").css('right','80px');
								$("#dau_sb_chart .dataShap ul").css('top','19px');
							}else{
								$("#dau_sb_chart .dataShap ul").css('right','80px');
								$("#dau_sb_chart .dataShap ul").css('top','15px');
							}
						}else{
							$("#dau_sb").fadeOut();
							$("#dau_sb_chart .dataShap ul").css('right','85px');
							$("#dau_sb_chart .dataShap ul").css('top','28px');
							if($("#dt_dau_sb_wrapper").length > 0){
								$("#dau_sb_data").fadeIn();
							}else{
								$.gameDau.brokenCumuliTable();
								$("#dau_sb_data").fadeIn();
							}
						}
					});
				});
				$("#dau_cu_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#dau_cu_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						var indicators = $('#indicators').val();
						if($("em",n).attr("class") == 'columnar'){
							$("#dau_cu").fadeIn();
							$("#dau_cu_data").fadeOut();
							if(indicators=='all'){
								$("#dau_cu_chart .dataShap ul").css('right','180px');
								$("#dau_cu_chart .dataShap ul").css('top','6px');
							}else{
								$("#dau_cu_chart .dataShap ul").css('right','60px');
								$("#dau_cu_chart .dataShap ul").css('top','6px');
							}
						}else{
							$("#dau_cu").fadeOut();
							$("#dau_cu_chart .dataShap ul").css('right','85px');
							$("#dau_cu_chart .dataShap ul").css('top','28px');
							if($("#dt_ct_wrapper").length > 0){
								$("#dau_cu_data").fadeIn();
							}else{
								$.gameDau.brokenMashupTable();
								$("#dau_cu_data").fadeIn();
							}
						}
					});
				});
				$("#dau_count_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#dau_count_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						var indicators = $('#indicators').val();
						if($("em",n).attr("class") == 'columnar'){
							$("#dau_count").fadeIn();
							$("#dau_count_data").fadeOut();
							$("#dau_count_chart .dataShap ul").css('right','180px');
							$("#dau_count_chart .dataShap ul").css('top','6px');
						}else{
							$("#dau_count").fadeOut();
							$("#dau_count_chart .dataShap ul").css('right','85px');
							$("#dau_count_chart .dataShap ul").css('top','28px');
							if($("#dt_dau_count_wrapper").length > 0){
								$("#dau_count_data").fadeIn();
							}else{
								$.gameDau.brokenLineTable();
								$("#dau_count_data").fadeIn();
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
	    			$.gameDau.brokenAjaxTable(ps);
	    			return;
				}
				
				$('#ajax_data').hide();
				$('#dau_sc_chart').show();
				$('#dau_sb_chart').show();
				$('#dau_cu_chart').show();
				$('#dau_count_chart').show();
				$('#data').show();
				
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/gamePlayer/getGameplayerAnalysis.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  
					    	var div_count = document.getElementById('dau_count');
					    	var dau_sc = document.getElementById('dau_sc');
					    	var dau_sb = document.getElementById('dau_sb');
					    	var dau_cu = document.getElementById('dau_cu');
					    	var indicators = $('#indicators').val();
					    	
					    	var dateArr = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 
					    	var reports;
					    	if(indicators == 'all'){
					    		reports = $.gameUtil.proReportData(data.reports,'day',dateArr);
					    	}else{
					    		reports = $.gameUtil.proReportData(data.scReports,'day',dateArr);
					    	}
					    	
					    	var dauArr = reports.dau_wau_mau_arr; 
					    	var installArr = reports.dnu_wnu_mnu_arr; 
					    	
					    	tab_dateArr = dateArr;
					    	tab_reports = reports;
					    	
					    	$.gameDau.pageCssControl(indicators);
					    	if(indicators == 'all'){
					    		$.gameDau.brokenLine(div_count,dauArr,dateArr);
						    	$.gameDau.brokenMashup(dau_cu,dateArr[0],reports.acu_arr,reports.pcu_arr,reports.act_arr,indicators);
						    	$.gameDau.brokenPie(dau_sc,data.sdreports,data.sdreportsCount,dateArr[0].length);
						    	$.gameDau.brokenCumuli(dau_sb,dateArr[0],dauArr,installArr);
						    	$.gameDau.brokenTable(data.reports);
					    	}else{
					    		
					    		$.gameDau.brokenLine(div_count,dauArr,dateArr);
						    	$.gameDau.brokenMashup(dau_cu,dateArr[0],reports.acu_arr,reports.pcu_arr,reports.act_arr,indicators);
						    	$.gameDau.brokenCumuli(dau_sb,dateArr[0],dauArr,installArr);
						    	$.gameDau.brokenTable(data.scReports);
					    	}
						  
					    	
					  }
				});
			},
			pageCssControl:function(page){
				if(page == 'all'){
		    		$("#dau_sc_chart").show();
		    		$("#dau_sb_chart").css("float","right");
					$("#dau_cu_chart").css("width","100%");
				}else{
					$("#dau_sc_chart").hide();
					$("#dau_sb_chart").css("float","left");
					$("#dau_cu_chart").css("width","49%");
					$("#dau_cu_chart").css("float","right");
				}
	    		$("#dau_count").show();
	    		$("#dau_cu").show();
	    		$("#dau_sb").show();
	    		$("#dau_sc").show();
	    		
	    		$("#dau_sc_data").hide();
	    		$("#dau_sb_data").hide();
				$("#dau_cu_data").hide();
				$("#dau_count_data").hide();
				
				$('#dt_dau_sc_wrapper').remove();
				$('#dt_dau_sb_wrapper').remove();
				$('#dt_ct_wrapper').remove();
				$('#dt_dau_count_wrapper').remove();
				
				$(".dataShap li").removeClass("onChoose");
				if(page == 'all'){
					$(".dataShap li:eq(0)").addClass("onChoose");
				}
				$(".dataShap li:eq(2)").addClass("onChoose");
				$(".dataShap li:eq(4)").addClass("onChoose");
				$(".dataShap li:eq(6)").addClass("onChoose");
				
				if(page != 'all'){
					$("#dau_cu_chart .dataShap ul").css('right','60px');
					$("#dau_cu_chart .dataShap ul").css('top','6px');
					
					$("#dau_count_chart .dataShap ul").css('right','180px');
					$("#dau_count_chart .dataShap ul").css('top','6px');
				}else{
				    $("#dau_cu_chart .dataShap ul").css('right','180px');
				    $("#dau_cu_chart .dataShap ul").css('top','6px');
				    
				    $("#dau_count_chart .dataShap ul").css('right','180px');
					$("#dau_count_chart .dataShap ul").css('top','6px');
				}
			},
			brokenTable:function(reports){
				$('#dt_wrapper').remove();
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt");
				$('#data').append(table);
				
				$.each(reports,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.date", trTemp).text(v.day);
					$("td.dau", trTemp).text(v.dau);
					$("td.dnu", trTemp).text(v.dnu);
					$("td.oldDau", trTemp).text((v.dau - v.dnu));
					$("td.pcu", trTemp).text(v.pcu);
					$("td.acu", trTemp).text(Math.round(v.acu*10)/10);
					$("td.avgUserTime", trTemp).text(Math.round(v.avgUserTime/60*10)/10+'分');

					$('tbody', table).append(trTemp);
					
				});
				table.removeClass("view_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables($('#dt'));
				$('#dt tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
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
						'echarts/chart/line',
						'echarts/chart/scatter',
						'echarts/chart/k',
						'echarts/chart/pie',
						'echarts/chart/radar',
						'echarts/chart/force',
						'echarts/chart/chord',
						'echarts/chart/gauge',
						'echarts/chart/funnel',
						'echarts/chart/eventRiver'
					],
					DrawEChart
				);
				
				
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var myChart = ec.init(cc,tarTheme);
						
						option.xAxis = [{data:dateArr[0],type:'category'}];
						option.yAxis = [{type:'value'}];
						
						option.title.text = '活跃趋势';
						option.title.subtext = '单位（个）';
						
						option.series = [{name:'活跃',type:'line',data:dataArr}];
						myChart.setOption(option,true);
			        });
				}
			},
			brokenMashup:function(cc,dateArr,acu_arr,pcu_arr,act_arr,indicators){

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
						'echarts/chart/scatter',
						'echarts/chart/k',
						'echarts/chart/pie',
						'echarts/chart/radar',
						'echarts/chart/force',
						'echarts/chart/chord',
						'echarts/chart/gauge',
						'echarts/chart/funnel',
						'echarts/chart/eventRiver'
					],
					DrawEChart
				);
				
				
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var myChart = ec.init(cc,tarTheme);
						
						if(indicators == 'all'){
				    		optionMashup.toolbox.orient = 'horizontal';
				    		optionMashup.toolbox.x='right';
				    		optionMashup.toolbox.y='top';
						}else{
				    		optionMashup.toolbox.orient = 'vertical';
				    		optionMashup.toolbox.x='right';
				    		optionMashup.toolbox.y='center';
						}
						
						optionMashup.xAxis = [{type:'category',data:dateArr}];
						
						optionMashup.series[0].data = pcu_arr;
						optionMashup.series[1].data = acu_arr;
						optionMashup.series[2].data = act_arr;
						
						myChart.setOption(optionMashup,true);
			        });
				}
			},
			brokenPie:function(cc,data,count,len){

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
						'echarts/chart/scatter',
						'echarts/chart/k',
						'echarts/chart/pie',
						'echarts/chart/radar',
						'echarts/chart/force',
						'echarts/chart/chord',
						'echarts/chart/gauge',
						'echarts/chart/funnel',
						'echarts/chart/eventRiver'
					],
					DrawEChart
				);
				
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var myChart = ec.init(cc,tarTheme);
						if(data == null){
							option_pie.series[0].data = [{value:100, name:'无数据'}];
							option_pie.toolbox.feature.magicType.option.funnel.max = 100;
						}else{
							var pie_data = new Array();
							var otherCount = 0;
							var max = 100;
							$.each(data, function(i,d){
								var value = Math.round(d.dauSum / len);
								var td={name:d.entity.source,value:value};
								pie_data.push(td);
								otherCount += d.dauSum;
								max = Math.max(value,max);
							}); 
							var other={name:'其他',value:Math.round((count - otherCount)/len)};
							pie_data.push(other);
							
							option_pie.series[0].data = pie_data;
							option_pie.toolbox.feature.magicType.option.funnel.max = max;
			    		}
						myChart.setOption(option_pie,true);
						
						tab_pie_data = pie_data;
						tab_pie_count = count;
			        });
				}
			},
			brokenCumuli:function(cc,dateArr,dauArr,installArr){

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
						'echarts/chart/scatter',
						'echarts/chart/k',
						'echarts/chart/pie',
						'echarts/chart/radar',
						'echarts/chart/force',
						'echarts/chart/chord',
						'echarts/chart/gauge',
						'echarts/chart/funnel',
						'echarts/chart/eventRiver'
					],
					DrawEChart
				);
				
				
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var myChart = ec.init(cc,tarTheme);
						
						optionCumuli.xAxis = [{type:'category',data:dateArr}];
						
						var newArr = installArr;
						var oldArr = new Array();
						$.each(dauArr,function(i,v){
							oldArr.push(v - newArr[i]);
						});
						
						for(var i=0;i<newArr.length;i++){
							if(newArr[i] == 0){
								newArr[i] = {value:'0',
				                         itemStyle: {
				                        	 normal: 
				                        	 {label :{
				                        		 show: true, 
				                        		 position: 'insideRight',
				                        		 textStyle:{color:'#fff'}}}}};
							}
						}
						
						for(var i=0;i<oldArr.length;i++){
							if(oldArr[i] == 0){
								oldArr[i] = {value:'0',
										     itemStyle: {
					                        	 normal: 
					                        	 {label :{
					                        		 show: true, 
					                        		 position: 'insideRight',
					                        		 textStyle:{color:'#fff'}}}}};
							}
						}
						
						
						optionCumuli.series[0].data = oldArr;
						optionCumuli.series[1].data = newArr;
						
						myChart.setOption(optionCumuli,true);
						
						tab_newArr = newArr;
						tab_oldArr = oldArr;
			        });
				}
			},
			brokenLineTable:function(){
				$('#dt_dau_count_wrapper').remove();
				var table = $(".template_cache .dau_count_table").clone(true);
				table.attr("id", "dt_dau_count");
				$('#dau_count_data').append(table);
				
				var dataArr = tab_reports.dau_wau_mau_arr;

				for(var i=0;i<tab_dateArr[1].length;i++){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.date", trTemp).text(tab_dateArr[1][i]);
					$("td.dau", trTemp).text(dataArr[i]);

					$('tbody', table).append(trTemp);
				}
				
				table.removeClass("dau_count_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_dau_count'));
				$('#dt_dau_count tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			brokenMashupTable:function(){
				$('#dt_ct_wrapper').remove();
				var table = $(".template_cache .acu_table").clone(true);
				table.attr("id", "dt_ct");
				$('#dau_cu_data').append(table);
				
				var pcu_arr = tab_reports.pcu_arr;
				var acu_arr = tab_reports.acu_arr;
				var act_arr = tab_reports.act_arr;
				for(var i=0;i<tab_dateArr[1].length;i++){
					var trTemp = $("tbody tr", table).first().clone(true);
					
					$("td.date", trTemp).text(tab_dateArr[1][i]);
					$("td.pcu", trTemp).text(pcu_arr[i]);
					$("td.acu", trTemp).text(acu_arr[i]);
					$("td.avgUserTime", trTemp).text(act_arr[i]+' 分');

					$('tbody', table).append(trTemp);
				}
				
				table.removeClass("acu_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_ct'));
				$('#dt_ct tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
			},
			brokenCumuliTable:function(){
				$('#dt_dau_sb_wrapper').remove();
				var table = $(".template_cache .new_old_table").clone(true);
				table.attr("id", "dt_dau_sb");
				$('#dau_sb_data').append(table);
				
				var dauArr = tab_reports.dau_wau_mau_arr;
				
				for(var i=0;i<tab_dateArr[1].length;i++){
					var trTemp = $("tbody tr", table).first().clone(true);
					
					$("td.date", trTemp).text(tab_dateArr[1][i]);
					$("td.dau", trTemp).text(dauArr[i]);
					if(!isNaN(tab_newArr[i])){
						$("td.dnu", trTemp).text(tab_newArr[i]); 
					}else{      
						$("td.dnu", trTemp).text(0);   
					}
					if(!isNaN(tab_oldArr[i])){
						$("td.oldDau", trTemp).text(tab_oldArr[i]);
					}else{      
						$("td.oldDau", trTemp).text(0);   
					}

					$('tbody', table).append(trTemp);
				}
				
				table.removeClass("new_old_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_dau_sb'));
				$('#dt_dau_sb tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
			},
			brokenPieTable:function(){
				$('#dt_dau_sc_wrapper').remove();
				var table = $(".template_cache .source_table").clone(true);
				table.attr("id", "dt_dau_sc");
				$('#dau_sc_data').append(table);
				
				$.each(tab_pie_data,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.source", trTemp).text(v.name);
					
					$("td.dau", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/tab_pie_count*tab_dateArr[0].length)*10000)/100 + '%');

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("source_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_dau_sc'));
				$('#dt_dau_sc tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
			},
			brokenAjaxTable:function(ps){
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				
				var type = $("#indicators").val();
				var rate = $("#gameRate").val();
				$('#ajax_data').show();
				$('#dau_sc_chart').hide();
				$('#dau_sb_chart').hide();
				$('#dau_cu_chart').hide();
				$('#dau_count_chart').hide();
				$('#data').hide();
				
				var table = null;
				if("source" == type){
					table= $(".template_cache .source_ajax_table").clone(true);
				}else{
					table= $(".template_cache .client_ajax_table").clone(true);
				}
				table.attr("id", "dt_ajax");
				$('#ajax_data').append(table);
				
				$('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
					          data: function(d){
					        	  d.id=ps.id;
					        	  d.gameId=ps.gameId;
					        	  d.snid=ps.snid;
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
				    "ordering": true, //排序功能
				    "order": [[ 0, "desc" ]],
				    rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                    },
			        columns: [
			                      {"data": "day"},
			                      {"data": type == "source" ? "source" : "clientid"},
			                      {"data": "dau"},
			                      {"data": "dnu"},
			                      {"data": null},
			                      {"data": "pcu"},
			                      {"data": null},
			                      {"data": null}
			                  ],
			        columnDefs: [
									{
										 targets: 1,
										 orderable:  type == "source" ? false : true
									},
			                      {
			                        targets: 4,
			                        render: function (a, b, c, d) {
			                             return c.dau-c.dnu;
			                          }
			                       },
				                    {
				                        targets: 6,
				                        render: function (a, b, c, d) {
				                             return Math.round(c.acu*10)/10;
				                          }
				                     },
				                     {
					                     targets: 7,
					                     render: function (a, b, c, d) {
					                         return Math.round(c.avgUserTime/60*10)/10+'分';
					                      }
					                  }
			                     ]
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
			}
	};	
});
	                    