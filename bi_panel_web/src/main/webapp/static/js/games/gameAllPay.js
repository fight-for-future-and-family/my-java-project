$(function(){
	
	option_line = {
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
	
	option_pie = {
			title : {text:'付费等级概览',x:10},
		    tooltip : { trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)" },
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['pie']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : false,
		    series : [
		        {
		            name:'付费人次（平均）',type:'pie',selectedMode: 'single',
		            radius : [0, 70],
		            itemStyle : { normal : {label : {position : 'outer'},labelLine : {show : true}}},
		            data:[ ]
		        },
		        {name:'付费额(累计)('+$("#gameCurrency").val()+')',type:'pie',radius : [100, 140], data:[],clockWise:false
		         ,itemStyle : { normal : {label : {position : 'outer'},labelLine : {show : true}}}
		        }
		    ]
		};
	
	
	var tab_pie_data_pay =null,tab_pie_data_payUser = null,tab_pc = 0,tab_puc = 0,tab_dateArr = null;
	var myChart_pay_rate_copy = null,option_bar_copy = option_bar,ec_copy = null,curTheme_copy = null;
	$(document).ready(function() {
	    $.gamePay.init();
	    $.timeZone.showTimeZone();
	});
		
	$.gamePay={
			init:function(){
				$.gamePay.initEvent();
				$.gamePay.submit();
			},
			initEvent:function(){
				$("#query").click(function(){
					$.gamePay.submit();
			    });
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gamePay.redict($(n).attr("id"));
					});
				});
				$("#indicators").change(function(){
					$.gameUtil.change($.gamePay);
				});
				
				$("#pay_level_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#pay_level_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#pay_level").fadeIn();
							$("#pay_level_data").fadeOut();
							$("#avg_pay_level_data").fadeOut();
							
							$("#pay_level_chart").css('width','49%');
							$("#pay_rate").css('width','50%');
							
							myChart_pay_rate_copy = ec_copy.init($("#pay_rate")[0], curTheme_copy);
							myChart_pay_rate_copy.setOption(option_bar_copy, true);
							
							$("#pay_level_chart .dataShap ul").css('right','80px');
							$("#pay_level_chart .dataShap ul").css('top','19px');
						}else{
							$("#pay_level").fadeOut();
							$("#pay_level_chart .dataShap ul").css('right','85px');
							$("#pay_level_chart .dataShap ul").css('top','28px');
							
							$("#pay_level_chart").css('width','100%');
							$("#pay_rate").css('width','100%');
							
							myChart_pay_rate_copy = ec_copy.init($("#pay_rate")[0], curTheme_copy);
							myChart_pay_rate_copy.setOption(option_bar_copy, true);
							
							if($("#dt_pl_wrapper").length > 0){
								$("#pay_level_data").fadeIn();
								$("#avg_pay_level_data").fadeIn();
							}else{
								$.gamePay.brokenPieTable();
								$("#pay_level_data").fadeIn();
								$("#avg_pay_level_data").fadeIn();
							}
						}
					});
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/gamePay/toGamePaymentAnalysis.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/gamePay/toGamePaymentAnalysis.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			submit:function(){
				var ps = $.gameUtil.checkParam();
				if(ps == null){
					return;
			    }
				
				if($("#s_c").val() == '-99'){
	    			$.gamePay.brokenAjaxTable(ps);
	    			return;
				}
				
				$('#ajax_data').hide();
				$('#pay_level_chart').show();
				$('#pay_rate').show();
				$('#pay_arppu').show();
				$('#pay_arpu').show();
				$('#data').show();
				
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/gamePay/getGamePaymentAnalysis.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  
					    	var pay_level = document.getElementById('pay_level');
					    	var pay_rate = document.getElementById('pay_rate');
					    	var pay_arppu = document.getElementById('pay_arppu');
					    	var pay_arpu = document.getElementById('pay_arpu');
					    	var indicators = $('#indicators').val();
					    	
					    	var dateArr = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 
					    	var reports;
					    	if(indicators == 'all'){
					    		reports = $.gameUtil.proReportData(data.reports,'day',dateArr);
					    	}else{
					    		reports = $.gameUtil.proReportData(data.scReports,'day',dateArr);
					    	}
					    	
					    	tab_dateArr = dateArr;
					    	$.gamePay.pageCssControl(indicators);
					    	if(indicators == 'all'){
					    		$('#pay_level').show();
					    		$("#pay_level_chart").css("width","49%");
					    		$(pay_rate).css("width","50%");
					    		$(pay_rate).css("margin-top","20px");
					    		
					    		$.gamePay.brokenLine(pay_rate,pay_arpu,pay_arppu,reports,dateArr[0]);
					    		$.gamePay.brokenPie(pay_level,data.pc,data.payList,data.puc,data.payUserList,dateArr[0].length);
						    	$.gamePay.brokenTable(data.reports);
					    	}else{
					    		$('#pay_level').hide();
					    		$(pay_rate).css("width","100%");
					    		$(pay_rate).css("margin-top","20px");
					    		
					    		$.gamePay.brokenLine(pay_rate,pay_arpu,pay_arppu,reports,dateArr[0]);
						    	$.gamePay.brokenTable(data.scReports);
					    	}
					    	
					  }
				});
			},
			pageCssControl:function(page){
				
				if(page == 'all'){
					$("#pay_level").show();
				}
	    		
	    		$("#pay_level_data").hide();
	    		$("#avg_pay_level_data").hide();
				
				$('#dt_apl_wrapper').remove();
				$('#dt_pl_wrapper').remove();
				
				$(".dataShap li").removeClass("onChoose");
				$(".dataShap li:eq(0)").addClass("onChoose");
			},
			brokenTable:function(reports){
				var rate = $("#gameRate").val();
				
				$('#dt_wrapper').remove();
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt");
				$('#data').append(table);
				
				$.each(reports,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.date", trTemp).text(v.day);
					$("td.paymentAmount", trTemp).text(Math.round(v.paymentAmount/rate*100)/100);
					$("td.pu", trTemp).text(v.pu);
					$("td.arpu", trTemp).text(Math.round(v.arpu/rate*100)/100);
					$("td.arppu", trTemp).text(Math.round(v.arppu/rate*100)/100);
					$("td.payRate", trTemp).text(Math.round(v.payRate * 100*100)/100+"%");

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("view_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables($('#dt'));
				$('#dt tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
			},
			brokenLine:function(pay_rate,pay_arpu,pay_arppu,reports,dateArr){

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
						var myChart_pay_arpu = ec.init(pay_arpu,tarTheme);
						var myChart_pay_arppu = ec.init(pay_arppu,tarTheme);
						var myChart_pay_rate = ec.init(pay_rate,tarTheme);
						
						var rate = $("#gameRate").val();
						var currency = $("#gameCurrency").val();
						
						var arpu_arr = new Array();
						var arppu_arr = new Array();
						var pay_arr = new Array();
						$.each(reports.arpu_arr,function(i,v){
							arpu_arr.push(Math.round(reports.arpu_arr[i]/rate*100)/100);
						});
						$.each(reports.arppu_arr,function(i,v){
							arppu_arr.push(Math.round(reports.arppu_arr[i]/rate*100)/100);
						});
						$.each(reports.pay_arr,function(i,v){
							pay_arr.push(Math.round(reports.pay_arr[i]/rate*100)/100);
						});
						
						option_line.xAxis = [{data:dateArr,type:'category'}];
						option_line.yAxis = [{type:'value',boundaryGap : [0, 0.01]}];
						option_line.title = { text : 'ARPU',subtext : '单位（'+currency+'）'},
						option_line.tooltip = {trigger: 'axis',formatter: '{b}\n{c}'+currency+''},
						option_line.series = [{name:'ARPU',type:'line',data:arpu_arr}];
						myChart_pay_arpu.setOption(option_line,true);
						
						option_line.title = { text : 'ARPPU',subtext : '单位（'+currency+'）'},
						option_line.tooltip = {trigger: 'axis',formatter: '{b}\n{c}'+currency+''},
						option_line.series = [{name:'ARPPU',type:'line',data:arppu_arr}];
						myChart_pay_arppu.setOption(option_line,true);
						
						option_bar.xAxis = [{data:dateArr,type:'category'}];
						option_bar.yAxis = [
						                 {type:'value',name:'付费额',axisLabel : {formatter: '{value} '+currency+''}},
				                         {type : 'value',name : '付费率', axisLabel : {formatter: '{value} %'}}
				                       ];
						option_bar.title = { text : '付费总览'},
						option_bar.tooltip = {trigger: 'axis'},
						option_bar.dataZoom = {
					        show : true,realtime: true,
					        //start : 0,end : (7/dateArr.length) * 100,
					        start : 80,end : 100,
					        zoomLock:true
					    };
					    option_bar.series = [{name:'付费额',type:'bar',data:pay_arr},
						                 {name:'付费率',yAxisIndex: 1,type:'line',data:reports.getRatePointValue(reports.payRate_arr)}];
					    var formatterStr = "{b} <br/>{a} : {c} "+currency+"<br/>{a1} : {c1} %";
						option_bar.tooltip = {trigger: 'axis',
								formatter: formatterStr};
					    
					    myChart_pay_rate.setOption(option_bar,true);
					    
					    curTheme_copy = tarTheme;
					    ec_copy = ec;
					    myChart_pay_rate_copy = myChart_pay_rate;
					    option_bar_copy = option_bar;
			        });
				}
			},
			brokenPie:function(pay_level,pc,paymentList,puc,payUserList,len){

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
						var myChart = ec.init(pay_level,tarTheme);
						if(pc == null || puc == null || pc == 0 || puc == 0){
							option_pie.series[0].data = [{value:100, name:'无数据'}];
						}else{
							var pie_data_pay = new Array();
							var pie_data_payUser = new Array();
							var dataCount_pay = 0;
							var dataCount_payUser = 0;
							
							$.each(paymentList, function(i,d){
								//if(i<5){
									var td={name:d.entity.level,value:d.payMentSum};
									pie_data_pay.push(td);
									dataCount_pay += d.payMentSum;
								//}else{
								//	return false;
								//}
							}); 
							$.each(payUserList, function(i,d){
								//if(i<5){
									var td={name:d.entity.level,value:Math.round(d.payUserSum / len)};
									pie_data_payUser.push(td);
									dataCount_payUser += d.payUserSum;
//								}else{
//									return false;
//								}
							}); 
							 
							var other={name:'其他',value:pc - dataCount_pay};
							pie_data_pay.push(other);
							var other={name:'其他',value:Math.round((puc - dataCount_payUser)/len),selected:true};
							pie_data_payUser.push(other);
							
							option_pie.series[1].data = pie_data_pay;
							option_pie.series[0].data = pie_data_payUser;
							
							tab_pie_data_pay = pie_data_pay;
							tab_pie_data_payUser = pie_data_payUser;
							tab_pc = pc;
							tab_puc = puc;
			    		}
						myChart.setOption(option_pie,true);
			        });
				}
			},
			brokenPieTable:function(){
				$('#dt_pl_wrapper').remove();
				var table = $(".template_cache .pay_level_table").clone(true);
				table.attr("id", "dt_pl");
				$('#pay_level_data').append(table);
				
				$.each(tab_pie_data_pay,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.level", trTemp).text(v.name);
					$("td.payCount", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/tab_pc)*10000)/100+'%');

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("pay_level_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_pl'));
				$('#dt_pl tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
				
				$('#dt_apl_wrapper').remove();
				var table = $(".template_cache .avg_pay_level_table").clone(true);
				table.attr("id", "dt_apl");
				$('#avg_pay_level_data').append(table);
				
				$.each(tab_pie_data_payUser,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.level", trTemp).text(v.name);
					$("td.avg_payCount", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/Math.round(tab_puc/tab_dateArr[0].length))*10000)/100+'%');
					
					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("avg_pay_level_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_apl'));
				$('#dt_apl tbody tr').click(function (){
					$(this).toggleClass('highlight');
				});
			},
			brokenAjaxTable:function(ps){
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				
				var type = $("#indicators").val();
				var rate = $("#gameRate").val();
				$('#ajax_data').show();
				$('#pay_level_chart').hide();
				$('#pay_rate').hide();
				$('#pay_arppu').hide();
				$('#pay_arpu').hide();
				$('#data').hide();
				
				var table = $(".template_cache .ajax_table").clone(true);
                var headTemp = $("thead tr",table).first();
				
				if("source" == type){
					$("td.head_type",headTemp).text('渠道');
				}else{
					$("td.head_type",headTemp).text('服务器');
				}
				
				table.attr("id", "dt_ajax");
				$('#ajax_data').append(table);
				
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
							  url:"/panel_bi/gamePay/getGamePaymentAnalysis.ac"
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
			                      {"data": null},
			                      {"data": "pu"},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null}
			                  ],
			        columnDefs: [
			                     {
			                    	 targets: 1,
			                    	 orderable:  type == "source" ? false : true
			                     },
			                     {
			                    	 targets: 2,
			                    	 render: function (a, b, c, d) {
			                    		 return Math.round((c.paymentAmount/rate)*100)/100;
			                    	 }
			                     },
			                      {
			                        targets: 4,
			                        render: function (a, b, c, d) {
			                             return Math.round((c.arpu/rate)*100)/100;
			                          }
			                       },
			                       {
				                        targets: 5,
				                        render: function (a, b, c, d) {
				                             return Math.round((c.arppu/rate)*100)/100;
				                          }
				                    },
				                    {
				                        targets: 6,
				                        render: function (a, b, c, d) {
				                             return Math.round(c.payRate * 100*100)/100+"%";
				                          }
				                     }
			                     ]
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				
				$('#dt_ajax button').click(function (){
			        $(this).attr('disabled',true);
			        $(this).val(2);
			        window.btnTimeout3=setInterval(function(){
						$.gameUtil.btnTimeout($('#dt_ajax button'),window.btnTimeout3);
					  },1000);
			        $("#downLoadForm").submit();
			    });
				
				$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
			}
	};	
});
	                    