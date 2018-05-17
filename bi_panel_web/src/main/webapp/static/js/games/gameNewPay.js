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
			title : {
		        text: '新付费与新注册对比'
		    },
		    tooltip : {trigger: 'axis',axisPointer : { type : 'shadow'}},
		    legend: {data:['新注册首日付费','非新注册首日付费','新注册首付人数','非新注册首付人数']},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true
		};
	
	option_pie = {
			title : {text:'新付费等级概览',x:10},
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
	
	
	var curTheme_copy = null,ec_copy = null,myChart_copy = null,option_copy = null,tab_dateArr = null;
	var tab_pie_data_pay =null,tab_pie_data_payUser = null,tab_pc = 0,tab_puc = 0;
	var tab_new_pay_arr = null,tab_not_new_pay_arr = null,tab_new_pu_arr = null,tab_not_new_pu_arr = null;
	$(document).ready(function() {
	    $.gameNewPay.init();
	    $.timeZone.showTimeZone();
	});
		
	$.gameNewPay={
			init:function(){
				$.gameNewPay.initEvent();
				$.gameNewPay.submit();
			},
			initEvent:function(){
				$("#query").click(function(){
					$.gameNewPay.submit();
			    });
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gameNewPay.redict($(n).attr("id"));
					});
				});
				$("#indicators").change(function(){
					$.gameUtil.change($.gameNewPay);
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
							$("#pay_arpu_arppu").css('width','50%');
							
							myChart_copy = ec_copy.init($("#pay_arpu_arppu")[0], curTheme_copy);
							myChart_copy.setOption(option_copy, true);
							
							$("#pay_level_chart .dataShap ul").css('right','80px');
							$("#pay_level_chart .dataShap ul").css('top','19px');
						}else{
							$("#pay_level").fadeOut();
							$("#pay_level_chart .dataShap ul").css('right','85px');
							$("#pay_level_chart .dataShap ul").css('top','28px');
							
							$("#pay_level_chart").css('width','100%');
							$("#pay_arpu_arppu").css('width','100%');
							
							myChart_copy = ec_copy.init($("#pay_arpu_arppu")[0], curTheme_copy);
							myChart_copy.setOption(option_copy, true);
							
							if($("#dt_pl_wrapper").length > 0){
								$("#pay_level_data").fadeIn();
								$("#avg_pay_level_data").fadeIn();
							}else{
								$.gameNewPay.brokenPieTable();
								$("#pay_level_data").fadeIn();
								$("#avg_pay_level_data").fadeIn();
							}
						}
					});
				});
				
				$("#pay_user_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#pay_user_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#pay_user").fadeIn();
							$("#pay_user_data").fadeOut();
							
							$("#pay_user_chart .dataShap ul").css('right','80px');
							$("#pay_user_chart .dataShap ul").css('top','19px');
						}else{
							$("#pay_user").fadeOut();
							$("#pay_user_chart .dataShap ul").css('right','85px');
							$("#pay_user_chart .dataShap ul").css('top','28px');
							
							if($("#dt_pu_wrapper").length > 0){
								$("#pay_user_data").fadeIn();
							}else{
								$.gameNewPay.brokenBarTable();
								$("#pay_user_data").fadeIn();
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
	    			$.gameNewPay.brokenAjaxTable(ps);
	    			return;
				}
				
				$('#ajax_data').hide();
				$('#pay_level_chart').show();
				$('#pay_arpu_arppu').show();
				$('#pay_user_chart').show();
				$('#data').show();
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/gamePay/getGamePaymentAnalysis.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  
					    	var pay_level = document.getElementById('pay_level');
					    	var pay_user = document.getElementById('pay_user');
					    	var pay_arpu_arppu = document.getElementById('pay_arpu_arppu');
					    	var indicators = $('#indicators').val();
					    	
					    	var dateArr = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 
					    	var reports;
					    	if(indicators == 'all'){
					    		reports = $.gameUtil.proReportData(data.reports,'day',dateArr);
					    	}else{
					    		reports = $.gameUtil.proReportData(data.scReports,'day',dateArr);
					    	}
					    	
					    	tab_dateArr = dateArr;
					    	$.gameNewPay.pageCssControl(indicators);
					    	if(indicators == 'all'){
					    		$('#pay_level').show();
					    		$("#pay_level_chart").css("width","49%");
					    		$(pay_arpu_arppu).css("width","50%");
					    		$(pay_arpu_arppu).css("margin-top","20px");
					    		
					    		$.gameNewPay.brokenLine(pay_user,pay_arpu_arppu,reports,dateArr[0]);
					    		$.gameNewPay.brokenPie(pay_level,data.pc,data.payList,data.puc,data.payUserList,dateArr[0].length);
						    	$.gameNewPay.brokenTable(data.reports);
					    	}else{
					    		$('#pay_level').hide();
					    		$(pay_arpu_arppu).css("width","100%");
					    		$(pay_arpu_arppu).css("margin-top","20px");;
					    		
					    		$.gameNewPay.brokenLine(pay_user,pay_arpu_arppu,reports,dateArr[0]);
						    	$.gameNewPay.brokenTable(data.scReports);
					    	}
					  }
				});
			},
            pageCssControl:function(page){
				
				if(page == 'all'){
					$("#pay_level").show();
				}
				$("#pay_user").show();
	    		
	    		$("#pay_level_data").hide();
	    		$("#avg_pay_level_data").hide();
	    		$("#pay_user_data").hide();
				
				$('#dt_apl_wrapper').remove();
				$('#dt_pl_wrapper').remove();
				$('#dt_pu_wrapper').remove();
				
				$(".dataShap li").removeClass("onChoose");
				$(".dataShap li:eq(0)").addClass("onChoose");
				$(".dataShap li:eq(2)").addClass("onChoose");
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
					$("td.newPayUser", trTemp).text(v.newPu);
					$("td.newPayAmount", trTemp).text(Math.round((v.newPayAmount/rate)*100)/100);
					$("td.newARPPU", trTemp).text(Math.round((v.newPu == 0 ? 0 : ( v.newPayAmount / v.newPu / rate))*100)/100);
					$("td.installPayUser", trTemp).text(v.installPu);
					$("td.installPayAmount", trTemp).text(Math.round((v.installPayAmount / rate)*100)/100);
					$("td.installARPPU", trTemp).text(Math.round((v.installPu == 0 ? 0 : (v.installPayAmount /  v.installPu / rate))*100)/100);

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("view_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt'));
				$('#dt tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
				
				
				
			},
			brokenLine:function(pay_rate,pay_arpu,reports,dateArr){

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
						var myChart_pay_rate = ec.init(pay_rate,tarTheme);
						
						var rate = $("#gameRate").val();
						var currency = $("#gameCurrency").val();
						
						var newPayAmount_arr = reports.newPayAmount_arr;
						var newPu_arr = reports.newPu_arr;
						var installPu_arr = reports.installPu_arr;
						var installPayAmount_arr = reports.installPayAmount_arr;
						var new_arppu = new Array();
						var install_arppu = new Array();
						var not_new_pay = new Array();
						var not_new_payUser = new Array();
						var x = 0;
						
						for(x in newPayAmount_arr){
							
							if(newPu_arr[x] == 0){
								new_arppu.push(0);
							}else{
								new_arppu.push((newPayAmount_arr[x] / newPu_arr[x] / rate).toFixed(0));
							}
							not_new_pay.push(((newPayAmount_arr[x] - installPayAmount_arr[x])/ rate).toFixed(0));
							not_new_payUser.push(newPu_arr[x] - installPu_arr[x]);
						}
						x = 0;
						for(x in installPayAmount_arr){
							if(installPu_arr[x] == 0){
								install_arppu.push(0);
							}else{
								install_arppu.push((installPayAmount_arr[x] / installPu_arr[x] / rate).toFixed(0));
							}
						}
						
						x=0;
						for(x in installPayAmount_arr){
							installPayAmount_arr[x] = (installPayAmount_arr[x]/rate).toFixed(0);
						}
						
						option_line.legend = {data:['新付费ARPPU','新注册ARPPU']};
						option_line.xAxis = [{data:dateArr,type:'category'}];
						option_line.yAxis = [{type:'value',boundaryGap : [0, 0.01], axisLabel : {formatter: '{value}'+currency+''}}];
						option_line.title = { text : 'ARPPU',subtext : '单位（'+currency+'）'},
						option_line.tooltip = {trigger: 'axis',formatter: '{b}<br>{a0}: {c0}元<br>{a1}: {c1}'+currency+''},
						option_line.series = [{name:'新付费ARPPU',type:'line',data:new_arppu},
						                      {name:'新注册ARPPU',type:'line',data:install_arppu}];
						myChart_pay_arpu.setOption(option_line,true);
						
						option_bar.xAxis = [{data:dateArr,type:'category'}];
						option_bar.yAxis = [
						                 {type:'value',name:'付费额',axisLabel : {formatter: '{value} '+currency+''}},
				                         {type : 'value',name : '付费人数', axisLabel : {formatter: '{value}个'}}
				                       ];
						option_bar.tooltip = {trigger: 'axis'},
						option_bar.dataZoom = {
					        show : true,realtime: true,
					        //start : 0,end : (4/dateArr.length) * 100,
					        start : 80,end : 100,
					        zoomLock:true
					    },
					    
					    $.gameNewPay.itemStyleChange(installPayAmount_arr);
						$.gameNewPay.itemStyleChange(not_new_pay);
						$.gameNewPay.itemStyleChange(installPu_arr);
						$.gameNewPay.itemStyleChange(not_new_payUser);
					    
					    option_bar.series = [{name:'新注册首日付费',type:'bar',stack: '付费额',
					    	                  data:installPayAmount_arr,barGap:0,
					    	                  itemStyle : { normal: {label : {show: true, position: 'insideRight',textStyle:{color:'#000'}}}}},
					                         {name:'非新注册首日付费',type:'bar',stack: '付费额',data:not_new_pay,barGap:0,
					    	                    itemStyle : { normal: {label : {show: true, position: 'insideRight',textStyle:{color:'#000'}}}}},
						                     {name:'新注册首付人数',yAxisIndex: 1,type:'bar',stack: '付费人数',data:installPu_arr,barGap:0,
					    	                	itemStyle : { normal: {label : {show: true, position: 'insideRight',textStyle:{color:'#000'}}}}},
						                     {name:'非新注册首付人数',yAxisIndex: 1,type:'bar',stack: '付费人数',data:not_new_payUser,barGap:0,
					    	                	itemStyle : { normal: {label : {show: true, position: 'insideRight',textStyle:{color:'#000'}}}}}];
						myChart_pay_rate.setOption(option_bar,true);
						
						
					    curTheme_copy = tarTheme;
						ec_copy = ec;
						myChart_copy = myChart_pay_arpu;
						option_copy = option_line;
						
						tab_new_pay_arr = installPayAmount_arr;
						tab_not_new_pay_arr = not_new_pay;
						tab_new_pu_arr = installPu_arr;
						tab_not_new_pu_arr = not_new_payUser;
			        });
				}
			},
			itemStyleChange:function(changeArr){
				for(var i=0;i<changeArr.length;i++){
					if(changeArr[i] == 0){
						changeArr[i] = {value:'0',
		                         itemStyle: {
		                        	 normal: 
		                        	 {label :{
		                        		 show: true, 
		                        		 position: 'insideRight',
		                        		 textStyle:{color:'#fff'}}}}};
					}
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
			    		}
						myChart.setOption(option_pie,true);
						
						tab_pie_data_pay = pie_data_pay;
						tab_pie_data_payUser = pie_data_payUser;
						tab_pc = pc,tab_puc = puc;
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
			brokenBarTable:function(){
				$('#dt_pu_wrapper').remove();
				var table = $(".template_cache .pay_user_table").clone(true);
				table.attr("id", "dt_pu");
				$('#pay_user_data').append(table);
				
				for(var i=0;i<tab_dateArr[1].length;i++){
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.date", trTemp).text(tab_dateArr[1][i]);
					
					if(isNaN(tab_new_pay_arr[i])){
						$("td.newPay", trTemp).text('-');
					}else{
						$("td.newPay", trTemp).text(tab_new_pay_arr[i]);
					}
					
					if(isNaN(tab_not_new_pay_arr[i])){
						$("td.notNewPay", trTemp).text('-');
					}else{
						$("td.notNewPay", trTemp).text(tab_not_new_pay_arr[i]);
					}
					
					if(isNaN(tab_new_pu_arr[i])){
						$("td.newPu", trTemp).text('-');
					}else{
						$("td.newPu", trTemp).text(tab_new_pu_arr[i]);
					}
					
					if(isNaN(tab_not_new_pu_arr[i])){
						$("td.notNewPu", trTemp).text('-');
					}else{
						$("td.notNewPu", trTemp).text(tab_not_new_pu_arr[i]);
					}
					
					$('tbody', table).append(trTemp);
				}
				
				table.removeClass("pay_user_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_pu'));
				$('#dt_pu tbody tr').click(function (){
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
				$('#pay_arpu_arppu').hide();
				$('#pay_user_chart').hide();
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
			                      {"data": "newPu"},
			                      {"data": null},
			                      {"data": null},
			                      {"data": "installPu"},
			                      {"data": null},
			                      {"data": null}
			                  ],
			        columnDefs: [
			                     {
			                    	 targets: 1,
			                    	 orderable:  type == "source" ? false : true
			                     },
			                     {
			                    	 targets: 3,
			                    	 render: function (a, b, c, d) {
			                    		 return Math.round((c.newPayAmount/rate)*100)/100;
			                    	 }
			                     },
			                      {
			                        targets: 4,
			                        render: function (a, b, c, d) {
			                             return Math.round((c.newPu == 0 ? 0 : ( c.newPayAmount / c.newPu / rate))*100)/100;
			                          }
			                       },
			                       {
				                        targets: 6,
				                        render: function (a, b, c, d) {
				                             return Math.round((c.installPayAmount / rate)*100)/100;
				                          }
				                    },
				                    {
				                        targets: 7,
				                        render: function (a, b, c, d) {
				                             return Math.round((c.installPu == 0 ? 0 : (c.installPayAmount /  c.installPu / rate))*100)/100;
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
	                    