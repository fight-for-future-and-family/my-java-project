$(function(){
	
	
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
		        data:['新增玩家','激活设备','激活-注册转化率']
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
		            name : '数量',
		            axisLabel : {
		                formatter: '{value} 个'
		            }
		        },
		        {
		            type : 'value',
		            name : '百分比',
		            axisLabel : {
		                formatter: '{value} %'
		            }
		        }
		    ],
		    series : [

		        {
		            name:'新增玩家',
		            type:'bar',
		            data:[20, 49, 70, 232, 256, 767, 1356, 1622, 326, 200, 64, 33]
		        },
		        {
		            name:'激活设备',
		            type:'bar',
		            data:[16, 29, 50, 204, 217, 707, 1156, 1222, 287, 188, 60, 23]
		        },
		        {
		            name:'激活-注册转化率',
		            type:'line',
		            yAxisIndex: 1,
		            data:[200, 220, 330, 450, 630, 102, 203, 234, 230, 165, 120, 62]
		        }
		    ]
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
	option_bar2 = {
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

	var tab_dateArr = null;
	var tab_pie_data = null;
	var tab_pie_count = 0;
	var tab_reports = null;
	var myChart_pay_rate_copy = null,option_bar_copy = option_bar,ec_copy = null,curTheme_copy = null;
	$(document).ready(function() {
	    $.gameView.init();
	    $.timeZone.showTimeZone();
	});
	
	
	
	
	$.gameView={
		init:function(){
			$.gameView.initEvent();
			$.gameView.submit();
		},
		initEvent:function(){
			$("#query").click(function(){
				$.gameView.submit();
		    });
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.gameView.redict($(n).attr("id"));
				});
			});
			$("#indicators").change(function(){
				$.gameUtil.change($.gameView);
			});
			
			$("#dau_cu_chart .dataShap li").each(function(i,n){
				$(n).click(function(){
					$("#dau_cu_chart .dataShap li").removeClass("onChoose");
					$(n).addClass("onChoose");
					
					var indicators = $('#indicators').val();
					if($("em",n).attr("class") == 'columnar'){
						$("#dau_cu").fadeIn();
						$("#dau_cu_data").fadeOut();
						//$("#pay_rate").css('width','50%');
						///myChart_pay_rate_copy = ec_copy.init($("#pay_rate")[0], curTheme_copy);
						//myChart_pay_rate_copy.setOption(option_bar_copy, true);
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
							$.gameView.brokenMashupTable();
							$("#dau_cu_data").fadeIn();
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
							$.gameView.brokenBarTable();
							$("#life_bar_data").fadeIn();
						}
					}
				});
			});
			
			$("#life_bar_chart2 .dataShap li").each(function(i,n){
				$(n).click(function(){
					$("#life_bar_chart2 .dataShap li").removeClass("onChoose");
					$(n).addClass("onChoose");
					
					var indicators = $('#indicators').val();
					if($("em",n).attr("class") == 'columnar'){
						$("#life_bar2").fadeIn();
						$("#life_bar_data2").fadeOut();
						$("#life_bar_chart2 .dataShap ul").css('right','80px');
						$("#life_bar_chart2 .dataShap ul").css('top','6px');
					}else{
						$("#life_bar2").fadeOut();
						$("#life_bar_chart2 .dataShap ul").css('right','85px');
						$("#life_bar_chart2 .dataShap ul").css('top','28px');
						if($("#dt_lb2_wrapper").length > 0){
							$("#life_bar_data2").fadeIn();
						}else{
							$.gameView.brokenBarTable2();
							$("#life_bar_data2").fadeIn();
						}
					}
				});
			});
		},
//		redict:function(view){
//			window.location.href='/panel_bi/gameTestReport/toGameTestReport.ac?view='+view;
//		},
		
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/gameTestReport/toGameTestReport.ac?view='+view'&snid='+snid+'&gameId='+gameId;
		},
		checkParam:function(){

			  var ps={
					 id:$("input[name='gamesId']").val(),
					 gameId:$("input[name='gameId']").val(),
					 snid:$("input[name='snid']").val(),
					 indicators:$('#indicators').val(),
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 source:$('#s_c').val(),
					 view:$('#view').val(),
				};
			  
			  if(ps.id == null || ps.id == ''){ return null; }
		      if(ps.gameId == null || ps.gameId == ''){ return null; }
		      if(ps.snid == null || ps.snid == ''){ return null; }
		      
		      if(ps.indicators == null || ps.indicators == ''){
		    	  ps.indicators = 'all'; 
		       }else if(ps.indicators == 'source' && (ps.source != null && ps.source == '-1')){
		    	   return null;
		       }
		      
		      //var dateArr = $.gameUtil.processDate2(ps.beginTime,ps.endTime);
		      var dateArr = $.gameUtil.processDate2(ps.beginTime,ps.endTime);
		      if(dateArr ==  null || dateArr == ''){
		    	  return null;
		      }
		      ps.beginTime = dateArr[0];
		      ps.endTime = dateArr[1];
		      $("input[name='beginTime']").val(dateArr[0]);
		      $("input[name='endTime']").val(dateArr[1]);
			
		      return ps;
			
		},
	
		submit:function(){
			var ps = $.gameView.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/gameTestReport/getGameOverview.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
					  
					  
					
					  var dau_sc = document.getElementById('dau_sc');
					  var dau_sb = document.getElementById('dau_sb');
					  var pay_rate = document.getElementById('pay_rate');
					  var life_bar = document.getElementById('life_bar');
					  var life_bar2 = document.getElementById('life_bar2');
					  var indicators = $('#indicators').val();
					  var dateArr = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val());
				    
					  var reports = $.gameUtil.proReportData2(data.reports,'day',dateArr);
				    
				    	$(pay_rate).css("width","50%");
			    		$(pay_rate).css("margin-top","20px");
				    	var dauArr = reports.dau_wau_mau_arr; 
					    var installArr = reports.dnu_wnu_mnu_arr;
					      
				    	tab_dateArr = dateArr;
				    	tab_reports = reports;
				    	
				    	 if(indicators == 'all'){//总体(主要为基础数据)
							  $("#dau_cu_chart").show();
							  $("#data").show();
							  $("#life_bar_chart2").hide();
							  $("#data2").hide();
							  $("#life_bar_chart").hide();
							  $("#data4").hide();
							  $.gameView.brokenMashup(dau_cu,dateArr[0],reports.jhsb_arr,reports.xzwj_arr,reports.jhzczhl_arr,indicators);
		                      $.gameView.brokenTable(data.reports,indicators);
						  }else if(indicators == 'zsb'){ //总体(主要为注收比)
							  $("#dau_cu_chart").hide();
							  $("#data").hide();
							  $("#life_bar_chart2").hide();
							  $("#data2").hide();
							  $("#life_bar_chart").show();
							  $("#data4").show();
							  $.gameView.brokenLineAndBar(life_bar,data.reports4,dateArr);
			                   $.gameView.brokenTable4(data.reports4);
						  }else if(indicators == 'leave'){//总体(主要为留存数据)
							  $("#dau_cu_chart").hide();
							  $("#data").hide();
							  $("#life_bar_chart2").show();
							  $("#data2").show();
							  $("#life_bar_chart").hide();
							  $("#data4").hide();
							  $.gameView.brokenLineAndBar2(life_bar2,data.reports2,dateArr);
		                      $.gameView.brokenTable2(data.reports2);
						  }
						 
				   
				    
                     //设备维度
                      //$.gameView.brokenTable3(data.reports3);
                     
				  }
				});
		},
		//数据概览
		brokenMashup:function(cc,dateArr,jhsb_arr,xzwj_arr,jhzczhl_arr,indicators){

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
					
					optionMashup.series[0].data = xzwj_arr;
					optionMashup.series[1].data = jhsb_arr;
					optionMashup.series[2].data = jhzczhl_arr;
				   var formatterStr = "{b} <br/>{a} : {c} "+"<br/>{a1} : {c1} "+"<br/>{a2} : {c2} %";
				   optionMashup.tooltip = {trigger: 'axis',formatter: formatterStr};
					myChart.setOption(optionMashup,true);
		        });
			}
		},
		
		brokenLineAndBar:function(dd,ltvs,dateArr){

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
				
					
					var rate = $("#gameRate").val();
					var currency = $("#gameCurrency").val();
					
					var D0 = new Array(), D1 = new Array(),D3 = new Array(),D7 = new Array(),D30 = new Array();
					var D0_all = new Array(), D1_all = new Array(),D3_all = new Array(),D7_all = new Array(),D30_all = new Array();
					var install_arr = new Array();
					$.each(dateArr[1],function(i,d){
						var isNotExist = true;
						$.each(ltvs,function(j,ltv){
							if(d == ltv.install_date){
								if(ltv.install== null || ltv.install == undefined
										|| ltv.install == 0 || ltv.install == '-'){
									return false;
								}
								D0_all.push(Math.round((ltv.d0 / rate) * 100 * 100)/100);
								D1_all.push(Math.round((ltv.d1 / rate) * 100 * 100)/100);
								D3_all.push(Math.round((ltv.d3 / rate) * 100 * 100)/100);
								D7_all.push(Math.round((ltv.d7 / rate) * 100 * 100)/100);
								isNotExist = false;
							}
						});
						if(isNotExist){
							D0_all.push(0);
							D1_all.push(0);
							D3_all.push(0);
							D7_all.push(0);
						}
					});
					var myChart_bar = ec.init(dd,tarTheme);
					var install_name='注册';
					if ($("#queryType").val() == 'roleChoice') {
						install_name = '创角';
					}
						option_bar.legend={data:['D0','D1','D3','D7']};
						option_bar.xAxis = [{data:dateArr[0],type:'category'}];
						option_bar.yAxis = [
						                    {type : 'value', name : '累计收入',axisLabel : {formatter: '{value} '+currency+''}},
							                {type : 'value',name : install_name,axisLabel : {formatter: '{value} 个'}}
							                ];
						option_bar.series = [
						                         {name:'D0',type:'bar',data:D0_all},
								                 {name:'D1',type:'bar',data:D1_all},
								                 {name:'D3',type:'bar',data:D3_all},
								                 {name:'D7',yAxisIndex:1,type:'line',data:D7_all}
								               
								                 ];
						option_bar.dataZoom = {show : true,realtime: true,start : 80,end : 100,zoomLock:true};
						
						var formatterStr1 = "{b} <br/>{a} : {c} "+currency+"<br/>{a1} : {c1} "+currency;
						formatterStr1 += "<br/>{a2} : {c2} "+currency+"<br/>{a3} : {c3} "+currency;
						option_bar.tooltip = {trigger: 'axis',
								formatter: formatterStr1};
						myChart_bar.setOption(option_bar,true);
						tab_D0_all = D0_all,tab_D1_all = D1_all,tab_D3_all = D3_all,tab_D7_all = D7_all;
					
		        });
			}
		},
		brokenLineAndBar2:function(dd,ltvs,dateArr){

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
				
					
					var rate = $("#gameRate").val();
					var currency = $("#gameCurrency").val();
					
					var D0 = new Array(), D1 = new Array(),D3 = new Array(),D7 = new Array(),tab_install_date_all= new Array();
					var D0_all = new Array(), D1_all = new Array(),D3_all = new Array(),D7_all = new Array();
					var install_arr = new Array();
					$.each(dateArr[1],function(i,d){
						var isNotExist = true;
						$.each(ltvs,function(j,ltv){
							if(d == ltv.install_date){
								if(ltv.install== null || ltv.install == undefined
										|| ltv.install == 0 || ltv.install == '-'){
									return false;
								}
								
								D1_all.push(Math.round((ltv.d1 / rate) * 100 * 100)/100);
								D3_all.push(Math.round((ltv.d3 / rate) * 100 * 100)/100);
								D7_all.push(Math.round((ltv.d7 / rate) * 100 * 100)/100);
								isNotExist = false;
							}
						});
						if(isNotExist){
							D1_all.push(0);
							D3_all.push(0);
							D7_all.push(0);
						}
					});
					var myChart_bar = ec.init(dd,tarTheme);
					var install_name='注册';
					if ($("#queryType").val() == 'roleChoice') {
						install_name = '创角';
					}
						option_bar2.legend={data:['D1','D3','D7']};
						option_bar2.xAxis = [{data:dateArr[0],type:'category'}];
						option_bar2.yAxis = [
						                    {type : 'value', name : '累计收入',axisLabel : {formatter: '{value} '+currency+''}},
							                {type : 'value',name : '留存',axisLabel : {formatter: '{value} 个'}}
							                ];
						option_bar2.series = [
								                 {name:'D1',type:'bar',data:D1_all},
								                 {name:'D3',type:'bar',data:D3_all},
								                 {name:'D7',yAxisIndex:1,type:'line',data:D7_all}
								               
								                 ];
						option_bar2.dataZoom = {show : true,realtime: true,start : 80,end : 100,zoomLock:true};
						
						var formatterStr2 = "{b} <br/>{a} : {c} "+'%'+"<br/>{a1} : {c1} "+'%';
						formatterStr2 += "<br/>{a2} : {c2} "+'%';
						option_bar2.tooltip = {trigger: 'axis',
								formatter: formatterStr2};
						myChart_bar.setOption(option_bar2,true);
					
						tab_D0_all = D0_all,tab_D1_all = D1_all,tab_D3_all = D3_all,tab_D7_all = D7_all;
		        });
			}
		},
		brokenBarTable:function(){//注收比
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
				$("td.install_date", trTemp).text(tab_dateArr[1][i]);
				$("td.D0", trTemp).text(tab_D0_all[i]);
				$("td.D1", trTemp).text(tab_D1_all[i]);
				$("td.D3", trTemp).text(tab_D3_all[i]);
				$("td.D7", trTemp).text(tab_D7_all[i]);

				$('tbody', table).append(trTemp);
			}
			
			table.removeClass("life_bar_table").addClass("table table-striped");
			$("tbody tr", table).first().remove();
			
			$.biDataTables.dataTables_chart($('#dt_lb'));
			$('#dt_lb tbody tr').click(function (){
			     $(this).toggleClass('highlight');
			 });
		},
		brokenBarTable2:function(){ //留存
			$('#dt_lb_wrapper').remove();
			var table = $(".template_cache .life_bar_table2").clone(true);
			table.attr("id", "dt_lb2");
			$('#life_bar_data2').append(table);
			var headTemp = $("thead tr",table).first();
			var value = $("#queryType").val();
			for(var i=0;i<tab_dateArr[1].length;i++){
				var trTemp = $("tbody tr", table).first().clone(true);
				$("td.install_date", trTemp).text(tab_dateArr[1][i]);
				$("td.D1", trTemp).text(tab_D1_all[i]+'%');
				$("td.D3", trTemp).text(tab_D3_all[i]+'%');
				$("td.D7", trTemp).text(tab_D7_all[i]+'%');

				$('tbody', table).append(trTemp);
			}
			
			table.removeClass("life_bar_table2").addClass("table table-striped");
			$("tbody tr", table).first().remove();
			
			$.biDataTables.dataTables_chart($('#dt_lb2'));
			$('#dt_lb2 tbody tr').click(function (){
			     $(this).toggleClass('highlight');
			 });
		},
		
		brokenMashupTable:function(){//概览数据
			$('#dt_ct_wrapper').remove();
			var table = $(".template_cache .acu_table").clone(true);
			table.attr("id", "dt_ct");
			$('#dau_cu_data').append(table);
			
			var xzwj_arr = tab_reports.xzwj_arr;
			var jhsb_arr = tab_reports.jhsb_arr;
			var jhzczhl_arr = tab_reports.jhzczhl_arr;
			for(var i=0;i<tab_dateArr[1].length;i++){
				var trTemp = $("tbody tr", table).first().clone(true);
				
				$("td.ds", trTemp).text(tab_dateArr[1][i]);
				$("td.new_equip", trTemp).text(jhsb_arr[i]);
				$("td.dnu", trTemp).text(xzwj_arr[i]);
				$("td.jihuo_xinzeng_rate", trTemp).text(jhzczhl_arr[i]);

				$('tbody', table).append(trTemp);
			}
			
			table.removeClass("acu_table").addClass("table table-striped");
			$("tbody tr", table).first().remove();
			
			$.biDataTables.dataTables_chart($('#dt_ct'));
			$('#dt_ct tbody tr').click(function (){
			     $(this).toggleClass('highlight');
			 });
		},
		
		brokenTable : function(reports, indicators) {//数据概览
			$("#data").empty();
			$('#dt_wrapper').remove();
			var table = $(".template_cache .view_table").clone(true);
			table.attr("id", "dt");
			$('#data').append(table);

			var rate = $("#gameRate").val();
			$.each(reports, function(i, v) {
				var trTemp = $("tbody tr", table).first().clone(true);
				$("td.ds", trTemp).text(v.ds);
				$("td.dau", trTemp).text(v.dau);
				$("td.dnu", trTemp).text(v.dnu);
				$("td.install", trTemp).text(v.install);
				$("td.role_choice", trTemp).text(v.role_choice);
				$("td.payment_amount", trTemp).text(v.payment_amount);
				$("td.payer", trTemp).text(v.payer);
				$("td.pay_rate", trTemp).text(v.pay_rate);
				$("td.arpu", trTemp).text(v.arpu);
				$("td.arppu", trTemp).text(v.arppu);
				$("td.new_equip", trTemp).text(v.new_equip);
				$("td.install2", trTemp).text(v.install2);
				$("td.jihuo_xinzeng_rate", trTemp).text(v.jihuo_xinzeng_rate);
				$("td.install_role_rate", trTemp).text(v.install_role_rate);
				$('tbody', table).append(trTemp);
			});
			table.removeClass("view_table").addClass("table table-striped");
			$("tbody tr", table).first().remove();
			$.biDataTables.dataTables($('#dt'));
			$('#dt tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},

	
		
	
		brokenTable2 : function(reports2) {//留存
			$("#data2").empty();
			$('#dt2_wrapper').remove();
			var table = $(".template_cache2 .view_table2").clone(true);
			table.attr("id", "dt2");
			$('#data2').append(table);

			var rate = $("#gameRate").val();
			$.each(reports2, function(i, v) {
				var trTemp = $("tbody tr", table).first().clone(true);
				$("td.install_date", trTemp).text(v.install_date==null?0:v.install_date);
				$("td.install", trTemp).text(v.install==null?0:v.install);
				$("td.d1", trTemp).text(v.d1==null?0+'%':v.d1+'%');
				$("td.d2", trTemp).text(v.d2==null?0+'%':v.d2+'%');
				$("td.d3", trTemp).text(v.d3==null?0+'%':v.d3+'%');
				$("td.d4", trTemp).text(v.d4==null?0+'%':v.d4+'%');
				$("td.d5", trTemp).text(v.d5==null?0+'%':v.d5+'%');
				$("td.d6", trTemp).text(v.d6==null?0+'%':v.d6+'%');
				$("td.d7", trTemp).text(v.d7==null?0+'%':v.d7+'%');
		
				$('tbody', table).append(trTemp);
			});
			table.removeClass("view_table2").addClass("table table-striped");
			$("tbody tr", table).first().remove();
			$.biDataTables.dataTables($('#dt2'));
			$('#dt2 tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},
		
		brokenTable3 : function(reports3) {
			$("#data3").empty();
			$('#dt_wrapper').remove();
			var table = $(".template_cache3 .view_table3").clone(true);
			table.attr("id", "dt3");
			$('#data3').append(table);

			var rate = $("#gameRate").val();
			$.each(reports3, function(i, v) {
				var trTemp = $("tbody tr", table).first().clone(true);
				$("td.install_date", trTemp).text(v.install_date==null?0:v.install_date);
				$("td.new_equip", trTemp).text(v.new_equip==null?0:v.new_equip);
				$("td.d1", trTemp).text(v.d1==null?0:v.d1);
				$("td.d2", trTemp).text(v.d2==null?0:v.d2);
				$("td.d3", trTemp).text(v.d3==null?0:v.d3);
				$("td.d4", trTemp).text(v.d4==null?0:v.d4);
				$("td.d5", trTemp).text(v.d5==null?0:v.d5);
				$("td.d6", trTemp).text(v.d6==null?0:v.d6);
				$("td.d7", trTemp).text(v.d7==null?0:v.d7);
		
				$('tbody', table).append(trTemp);
			});
			table.removeClass("view_table3").addClass("table table-striped");
			$("tbody tr", table).first().remove();
			$.biDataTables.dataTables($('#dt3'));
			$('#dt3 tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},
		brokenTable4 : function(reports4) {//注收比
			$("#data4").empty();
			$('#dt4_wrapper').remove();
			var table = $(".template_cache4 .view_table4").clone(true);
			table.attr("id", "dt4");
			$('#data4').append(table);

			var rate = $("#gameRate").val();
			$.each(reports4, function(i, v) {
				var trTemp = $("tbody tr", table).first().clone(true);
				$("td.install_date", trTemp).text(v.install_date==null?0:v.install_date);
				$("td.install", trTemp).text(v.install==null?0:v.install);
				$("td.d0", trTemp).text(v.d0==null?0:v.d0);
				$("td.d1", trTemp).text(v.d1==null?0:v.d1);
				$("td.d2", trTemp).text(v.d2==null?0:v.d2);
				$("td.d3", trTemp).text(v.d3==null?0:v.d3);
				$("td.d4", trTemp).text(v.d4==null?0:v.d4);
				$("td.d5", trTemp).text(v.d5==null?0:v.d5);
				$("td.d6", trTemp).text(v.d6==null?0:v.d6);
				$("td.d7", trTemp).text(v.d7==null?0:v.d7);
		
				$('tbody', table).append(trTemp);
			});
			table.removeClass("view_table4").addClass("table table-striped");
			$("tbody tr", table).first().remove();
			$.biDataTables.dataTables($('#dt4'));
			$('#dt4 tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},
		
		
		
		/*
		brokenDataDiv:function(name,div,indicators,count,avg,max,min,dataArr,dateArr){
			
		},
		brokenPayDiv:function(pay_count,pay_cnt_count,div,indicators,arpu_count,arppu_count,pay_rate_count,dateLength){
			
		},
		brokenRollDiv:function(div,roll_users_count,roll_pay_users_count,roll_amount_count,dateLength){
          
		},
		brokenInstallDiv:function(indicators,installRetentions,dateArr){
		
		},
		brokenLtvDiv:function(indicators,ltvs){
	
		}*/
	};
});
	                    