$(function(){
	
	option = {
		    tooltip : {trigger: 'axis'},
		    grid: { borderWidth: 0,x:0,y: 0,y2: 30,x2:0},
		    xAxis : [{type : 'category',show : false}],
		    yAxis : [ {type : 'value',show:false}],
		    series : [{name:'成交',type:'line',smooth:true,symbol:'none',itemStyle: {normal: {areaStyle: {type: 'default'}}}}]
		};
	
	optionIR2 = {
		    title: {x: 70,y: 10,text: '玩家留存',textStyle:{fontSize: 14, color: '#333'}},
		    tooltip: {trigger: 'item',formatter: '{b}\n{c}%'},
		    calculable: true,
		    grid: { borderWidth: 0,x:10,y: 30,y2: 30,x2:110},
		    xAxis: [{type: 'value',show: false}],
		    yAxis: [{type: 'category',show: false,data: ['七日留存率', '三日留存率', '次日留存率']}],
		    series: [{name: '玩家留存', type: 'bar', barWidth:15,
		    	      data: [10,30,98],
		              itemStyle: {
		                 normal: {
		                    color: function(params) {
		                        var colorList = ['#FE8463','#F3A43B','#9BCA63'];
		                        return colorList[params.dataIndex]
		                    },
		                    label: {show: true, position: 'right', formatter: '{b} {c}%'}
		                }
		            }
		        }
		    ]
		};
	
	optionLtv = {
			title: {x: 70,y: 10,text: '注收比',textStyle:{fontSize: 14, color: '#333'}},
			tooltip: {trigger: 'item',formatter: '{b}\n{c}'+$("#gameCurrency").val()+''},
			calculable: true,
			grid: { borderWidth: 0,x:10,y: 30,y2: 30,x2:110},
			xAxis: [{type: 'value',show: false}],
			yAxis: [{type: 'category',show: false,data: ['D7注收比', 'D3注收比', 'D1注收比']}],
			series: [{name: '注收比', type: 'bar', barWidth:15,
				data: [10,30,98],
				itemStyle: {
					normal: {
						color: function(params) {
							var colorList = ['#9BCA63','#F3A43B','#FE8463'];
							return colorList[params.dataIndex]
						},
						label: {show: true, position: 'right', formatter: '{b} {c}'+$("#gameCurrency").val()+''}
					}
				}
			}
			]
	};
	
	$(document).ready(function() {
	    $.gameView.init();
	    $.timeZone.showTimeZone();
	});
	
	$.gameView={
		init:function(){
			$.gameView.initEvent();
			$.gameView.drawYMW();
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
			
			$("#dateDimension").change(function(){
				$.gameView.dateDimensionChange();
				$.gameView.submit();
		    });
		},
//		redict:function(view){
//			window.location.href='/panel_bi/gameView/toGameOverview.ac?view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/gameView/toGameOverview.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		dateDimensionChange:function(){
			if($("#dateDimension").val() != 'day'){
				
				if($("#dateDimension").val() == 'week'){
					$("#day_span").hide();
					$("#month_week_span").show();
					$("#week_begin").show();
				    $("#week_end").show();
				}else{
					$("#day_span").hide();
					$("#month_week_span").show();
					$("#week_begin").hide();
				    $("#week_end").hide();
				}
				
				$.gameView.dateSelectChange('year_begin', 'month_begin', 'week_begin', 'w_m_w_span_begin', 'm_beginTime', $("#dateDimension").val());
				$.gameView.dateSelectChange('year_end', 'month_end', 'week_end', 'w_m_w_span_end', 'm_endTime', $("#dateDimension").val());
			}else{
				$("#day_span").show();
				$("#month_week_span").hide();
			}
		},
		dateSelectChange:function(yearName,monthName,weekName,weekSpanName,dateName,dateDimension){
			var year = $("#"+yearName+"").val();
			var month = Number($("#"+monthName+"").val());
			
			var monthStr = (month+1) < 10 ? "-0" +(month+1) : "-"+(month+1);
	    	if(dateDimension == 'week'){
	    		var oldWeekIndex = $("#"+weekName+"").get(0).selectedIndex;
	    		$("#"+weekName+"").remove();
				$.gameView.darwW(year,month,''+weekSpanName+'',''+weekName+'');
				$("#"+weekName+" option").eq(oldWeekIndex).attr("selected",true);
				
				$("#"+weekName+"").change(function(){
					$.gameView.dateSelectChangeEvent(this);
			    	$.gameView.submit();
				});
				var week = $("#"+weekName+"").val();
				
				$("#"+dateName+"").val($.date.getWeekDate(year,month,week));
	    	}else{
	    		
				$("#"+dateName+"").val(year+ monthStr);
	    	}
		},
		darwW:function(year,month,spanName,selName){
			var str = 'style="width:66px">';
		    var week = $.date.getWeekCounts(year,month);
		    for(var i=1;i<=week;i++){
		    	str += '<option value="' + i +'">第' + i +'周</option>';
		    }
		    str += '</select>';
		    $("#"+spanName+"").append('<select class="dateSelect" id="'+selName+'" '+str);
		},
		drawYMW:function(){
			var d = new Date();
			var year = d.getFullYear();
		    var month = d.getMonth();
		    
		    var b = new Date();
		    var m = month - 4;
		    var y = year;
		    if(m<0){
		    	m = m+12;
		    	y = y -1;
		    }
		    b.setMonth(m);
	    	b.setYear(y);
		    
	    	$("#month_end").val(month);
		    $("#month_begin").val(m);
		    
		    $("#year_end").val(year);
		    $("#year_begin").val(y);
		    
		    $.gameView.darwW(year,m,'w_m_w_span_begin','week_begin');
		    $.gameView.darwW(year,month,'w_m_w_span_end','week_end');
		    
		    $("#month_week_span").hide();
		    
		    $(".dateSelect").change(function(){
		    	$.gameView.dateSelectChangeEvent(this);
		    	$.gameView.submit();
			});
		},
		dateSelectChangeEvent:function(n){
			if($(n).attr("id") == 'week_begin' 
	    		|| $(n).attr("id") == 'year_begin' 
	    		|| $(n).attr("id") == 'month_begin'){
	    		
	    		$.gameView.dateSelectChange('year_begin', 'month_begin', 'week_begin', 'w_m_w_span_begin', 'm_beginTime', $("#dateDimension").val());
	    	
	    	}else if($(n).attr("id") == 'week_end' 
	    		|| $(n).attr("id") == 'year_end' 
		    	|| $(n).attr("id") == 'month_end'){
	    		
	    		$.gameView.dateSelectChange('year_end', 'month_end', 'week_end', 'w_m_w_span_end', 'm_endTime', $("#dateDimension").val());
	    	
	    	}
		},
		checkParam:function(){

			  var ps={
					 id:$("input[name='gamesId']").val(),
					 gameId:$("input[name='gameId']").val(),
					 snid:$("input[name='snid']").val(),
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 view:$('#view').val(),
				     dateDimension:$("#dateDimension").val(),
					 m_beginTime:$("input[id='m_beginTime']").val(),
					 m_endTime:$("input[id='m_endTime']").val(),
				};
		      
			  if(ps.gameId == null || ps.gameId == '-1'){ return null; }
		      
		      if(ps.dateDimension == null || ps.dateDimension == ''){
		    	  ps.dateDimension = 'day'; 
		      }
		      
		      if(ps.dateDimension == 'day'){
		    	  var date = $.gameUtil.processDate(ps.beginTime,ps.endTime)
			      ps.beginTime = date[0];
			      ps.endTime = date[1];
			      
			      $("input[name='endTime']").val(ps.endTime);
			      $("input[name='beginTime']").val(ps.beginTime);
		      }else if(ps.dateDimension == 'month'){
		    	  $.gameView.processMonthDate(ps.m_beginTime,ps.m_endTime);
		      }else{
		    	  $.gameView.processWeekDate(ps.m_beginTime,ps.m_endTime);
		      }
		      
		      return ps;
			
		},
		processWeekDate:function(m_beginTime,m_endTime){
			var time1 = m_beginTime.split('_')[0];
			var time2 = m_endTime.split('_')[0];
			$.gameView.processMonthDate(time1,time2);
		},
		processMonthDate:function(m_beginTime,m_endTime){
			var m_beginTime = new Date(m_beginTime);
	    	  var m_endTime = new Date(m_endTime);
	    	  if(m_endTime.getTime() < m_beginTime.getTime()){
	    		  alert('结束时间不能小于开始时间');
	    		  return null;
	    	  }
		},
		submit:function(){
			var ps = $.gameView.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/gameView/getGameOverview.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
					    var div_pay_count = document.getElementById('pay_count');
				    	var div_install_count = document.getElementById('install_count');
				    	var div_pay_user_count = document.getElementById('pay_user_count');
				    	var div_dau_mau_count = document.getElementById('dau_mau_count');
				    	var div_pay = document.getElementById('pay_text');
				    	var div_roll = document.getElementById('roll_text');
				    	var indicators = ps.dateDimension;
				    	
				    	var dateArr = [];
				    	if(ps.dateDimension == 'day'){
				    		dateArr = $.gameUtil.getDay(indicators,$("#beginTime").val(),$("#endTime").val()); 
				    	}else if(ps.dateDimension == 'month'){
				    		dateArr = $.gameUtil.getDay(indicators,$("#m_beginTime").val(),$("#m_endTime").val()); 
				    	}else{
				    		dateArr = $.date.getWeekArr($("#m_beginTime").val(),$("#m_endTime").val(),$("#week_begin").val(),$("#week_end").val());
				    	}
				    	
				    	var reports =  $.gameUtil.proReportData(data.reports,indicators,dateArr);
				    	
				    	// 以下为整理处理后台返回的数据,求最大值、最小值、平均等
                        var pay_arr = reports.pay_arr, install_arr = reports.dnu_wnu_mnu_arr; 
                        pay_user_arr = reports.pay_user_arr, dau_wau_mau_arr = reports.dau_wau_mau_arr; 
                        
                        var pay = reports.getCalculateValue(pay_arr);
                        var install = reports.getCalculateValue(install_arr);
                        var pay_user = reports.getCalculateValue(pay_user_arr);
                        var dau_mau = reports.getCalculateValue(dau_wau_mau_arr);
                        
                        pay_count = pay.count, pay_avg = pay.avg, pay_max = pay.max, pay_min = pay.min;
                        install_count = install.count, install_avg = install.avg, install_max = install.max, install_min = install.min;
                        pay_user_count = pay_user.count, pay_user_avg = pay_user.avg, pay_user_max = pay_user.max,pay_user_min = pay_user.min;
                        dau_mau_count =dau_mau.count, dau_mau_avg = dau_mau.avg; dau_mau_max = dau_mau.max; dau_mau_min = dau_mau.min;
				    	
				    	pay_cnt_count = reports.getCalculateValue(reports.pay_cnt_arr).count;
				    	arpu_count = reports.getCalculateValue(reports.arpu_arr).count;
				    	arppu_count = reports.getCalculateValue(reports.arppu_arr).count;
				    	pay_rate_count = reports.getCalculateValue(reports.payRate_arr).count;

                        roll_users_count = reports.getCalculateValue(reports.rollUsers_arr).count;
				    	roll_pay_users_count = reports.getCalculateValue(reports.rollPayUsers_arr).count;
				    	roll_amount_count = reports.getCalculateValue(reports.rollAmount_arr).count;
				    	
				    	//页面表格
                        $.gameView.brokenTable(data.reports,indicators);
				    	
                        // 第一排文字描述模块
                        $.gameView.brokenDataDiv('payCount',div_pay_count,indicators,pay_count,pay_avg,pay_max,pay_min,pay_arr,dateArr[0]);
				    	$.gameView.brokenDataDiv('installCount',div_install_count,indicators,install_count,install_avg,install_max,install_min,install_arr,dateArr[0]);
				    	$.gameView.brokenDataDiv('payUserCount',div_pay_user_count,indicators,pay_user_count,pay_user_avg,pay_user_max,pay_user_min,pay_user_arr,dateArr[0]);
				    	$.gameView.brokenDataDiv('dauCount',div_dau_mau_count,indicators,dau_mau_count,dau_mau_avg,dau_mau_max,dau_mau_min,dau_wau_mau_arr,dateArr[0]);
				    	
				    	// 第二排文字描述模块
				    	$.gameView.brokenPayDiv(pay_count,pay_cnt_count,div_pay,indicators,arpu_count,arppu_count,pay_rate_count,dateArr[1].length);
				    	$.gameView.brokenRollDiv(div_roll,roll_users_count,roll_pay_users_count, roll_amount_count,dateArr[1].length);
				    	
				    	// 第二排图表展示模块
				    	$.gameView.brokenInstallDiv(indicators,data.installRetentions,dateArr);
				    	$.gameView.brokenLtvDiv(indicators,data.installLtv);
				  }
				});
		},
		brokenTable : function(reports, indicators) {
			$('#dt_wrapper').remove();
			var table = $(".template_cache .view_table").clone(true);
			table.attr("id", "dt");
			$('#data').append(table);

			var rate = $("#gameRate").val();
			$.each(reports, function(i, v) {
				var trTemp = $("tbody tr", table).first().clone(true);

				if (indicators == 'day') {
					$("td.day", trTemp).text(v.day);
					$("td.dnu", trTemp).text(v.dnu);
					$("td.dau", trTemp).text(v.dau);
				} else if (indicators == 'month') {
					$("td.day", trTemp).text(v.month);
					$("td.dnu", trTemp).text(v.mnu);
					$("td.dau", trTemp).text(v.mau);
				} else if (indicators == 'week') {
					$("td.day", trTemp).text(v.week);
					$("td.dnu", trTemp).text(v.wnu);
					$("td.dau", trTemp).text(v.wau);
				}
				$("td.roleChoice", trTemp).text(v.roleChoice);
				$("td.payMent", trTemp).text(Math.round(v.paymentAmount/rate*100)/100);
				$("td.pu", trTemp).text(v.pu);

				$('tbody', table).append(trTemp);
			});

			table.removeClass("view_table").addClass("table table-striped");
			$("tbody tr", table).first().remove();

			$.biDataTables.dataTables($('#dt'));
			
			$('#dt tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},
		brokenDataDiv:function(name,div,indicators,count,avg,max,min,dataArr,dateArr){
			
			var rate = $("#gameRate").val();
			var currency = $("#gameCurrency").val();
			
			if(name == 'payCount'){
				count = (count / rate);
				avg = (avg / rate);
				max = (max / rate);
				min = (min / rate);
			}
			if (!(/^(\+|-)?\d+$/.test(count))) {
				count = Math.round(count*100)/100;
			}
			if (!(/^(\+|-)?\d+$/.test(max))) {
				max = Math.round(max*100)/100;
			}
			if (!(/^(\+|-)?\d+$/.test(avg))) {
				if(name=='payCount')
				   avg = Math.round(avg*100)/100;
				else
				   avg = Math.round(avg);
			}
			if (!(/^(\+|-)?\d+$/.test(min))) {
				min = Math.round(min*100)/100
			}
			
			var unit = '  '+currency
			if(name == 'payCount'){
				count += unit;
				avg += unit;
				max += unit;
				min += unit;
			}
			
			$(".count",div).text(count);
			$(".avg",div).text('平均： '+avg);
			$(".max",div).text('最高： '+max);
			$(".min",div).text('最低： '+min);
			
			require.config({
				paths: {
					echarts: '/js/echarts/dist/',
					theme:'/js/echarts/theme/'
				}
			});
			require(
				[
					'echarts',
					'echarts/chart/line',
				],
				DrawEChart
			);
			function DrawEChart(ec){
				require(['theme/macarons'], function(tarTheme){
					var tuDiv = document.getElementById(name+"_tu");
					var myChart = ec.init(tuDiv,tarTheme);
					option.series[0].name=$(tuDiv).attr("tuname");
					option.series[0].data = dataArr;
					option.xAxis[0].data = dateArr;
					
					if(name=='payCount'){
						var payArr = new Array();
						$.each(dataArr,function(i,v){
							payArr.push(Math.round(v/rate*100)/100);
						});
						option.series[0].data = payArr;
						option.tooltip =  { trigger: 'axis',formatter: "{a} <br/>{b} : {c} "+currency+"" };
					}else{
						option.tooltip = { trigger: 'axis',formatter: "{a} <br/>{b} : {c}" };
					}
					
					myChart.setOption(option,true);
		        });
			}
		},
		brokenPayDiv:function(pay_count,pay_cnt_count,div,indicators,arpu_count,arppu_count,pay_rate_count,dateLength){
			var rate = $("#gameRate").val();
			
			pay_count = (pay_count / rate);
			arpu_count = (arpu_count / rate);
			arppu_count = (arppu_count / rate);
			
			$(".count",div).text(Math.round(pay_count*100)/100);
			$(".payCnt",div).text(pay_cnt_count);
			$(".avg_pay",div).text(Math.round(pay_count/pay_cnt_count*100)/100);
			$(".payRate",div).text(Math.round(pay_rate_count/dateLength*100*100)/100);
			$(".arpu",div).text(Math.round(arpu_count/dateLength*100)/100);
			$(".arppu",div).text(Math.round(arppu_count/dateLength*100)/100);
		},
		brokenRollDiv:function(div,roll_users_count,roll_pay_users_count,roll_amount_count,dateLength){
            var rate = $("#gameRate").val();
			
            roll_amount_count = (roll_amount_count / rate);
			
			$(".count",div).text(Math.round(roll_amount_count));
			$(".avg_user",div).text(Math.round(roll_users_count/dateLength));
			$(".avg_pay_user",div).text(Math.round(roll_pay_users_count/dateLength));
		},
		brokenInstallDiv:function(indicators,installRetentions,dateArr){
			document.getElementById('ir').style.display = '';
			document.getElementById('ir_text').style.display = '';
			
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
				],
				DrawEChart
			);
			function DrawEChart(ec){
				require(['theme/macarons'], function(tarTheme){
					var myChart = ec.init(document.getElementById('ir'),tarTheme);
					
					var dataTemp = [0,0,0];
					if(installRetentions != null){
						$.each(installRetentions,function(i,v){
							var rate = Math.round(v.avgRetentionRate*100*100)/100;
							if(v.entity.retentionDay == 1)dataTemp[2] = rate;
							if(v.entity.retentionDay == 3)dataTemp[1] = rate;
							if(v.entity.retentionDay == 7)dataTemp[0] = rate;
						});
					}
					
					optionIR2.series[0].data = dataTemp;
					myChart.setOption(optionIR2,true);
		        });
			}
		},
		brokenLtvDiv:function(indicators,ltvs){
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
				],
				DrawEChart
			);
			function DrawEChart(ec){
				require(['theme/macarons'], function(tarTheme){
					var myChart = ec.init(document.getElementById('ctl_text'),tarTheme);
					var dataTemp = [0,0,0];
					if(ltvs != null){
						$.each(ltvs,function(i,v){
							var pay = Math.round(v.avgLtv / $("#gameRate").val()*100)/100;
							if(v.entity.retentionDay == 1)dataTemp[2] = pay;
							if(v.entity.retentionDay == 3)dataTemp[1] = pay;
							if(v.entity.retentionDay == 7)dataTemp[0] = pay;
						});
					}
					
					optionLtv.series[0].data = dataTemp;
					myChart.setOption(optionLtv,true);
		        });
			}
		}
	};
});
	                    