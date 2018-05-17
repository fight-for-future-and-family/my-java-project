$(function(){
	
	var option = {
		    tooltip:{
		    	show : true,
		    	position:[200,20]
		    },
		    toolbox: {
		        show : false,
		        itemSize: 12,
		        x:'center',
		        //orient: 'vertical',
		        feature : {
		            mark : {show: true},
		            //dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line','bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }
		};
	var optionIR = {
		    tooltip:{
		    	show : true,
		    	trigger: 'axis'
		    },
		    toolbox: {
		        show : true,
		        itemSize: 12,
		        x:'right',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line','bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true
		};
	
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
		},
		checkParam:function(){
		  var ps={
			     id:$("input[name='gamesId']").val(),
			     gameId:$("input[name='gameId']").val(),
			     snid:$("input[name='snid']").val(),
			     indicators:$('#indicators').val(),
				 beginTime:$("input[name='beginTime']").val(),
				 endTime:$("input[name='endTime']").val()
			};
	      if(ps.id == null || ps.id == ''){
				return null;
			}
	      if(ps.gameId == null || ps.gameId == ''){
	    	  return null;
	      }
	      if(ps.snid == null || ps.snid == ''){
	    	  return null;
	      }
	      if(ps.indicators == null || ps.indicators == ''){
	    	  ps.indicators = 'day';
	      }
	      if(ps.endTime == null || ps.endTime == ''){
	    	  var d = new Date();
	    	  ps.endTime = d.getFullYear()+"-" +(d.getMonth()+1) + "-" + d.getDate();
	      }
	      if(ps.beginTime == null || ps.beginTime == ''){
	    	  var d = new Date(ps.endTime);
	    	  d.setTime(d.getTime()- 7*24*60*60*1000);
	    	  ps.beginTime = d.getFullYear()+"-" +(d.getMonth()+1) + "-" + d.getDate();
	      }
	      return ps;
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
					    var pay_count = document.getElementById('pay_count');
				    	var install_count = document.getElementById('install_count');
				    	var pay_user_count = document.getElementById('pay_user_count');
				    	var dau_mau_count = document.getElementById('dau_mau_count');
				    	var indicators = $('#indicators').val();
				    	
				    	var pay_count_arr = new Array(); 
				    	var install_count_arr = new Array(); 
				    	var pay_user_count_arr = new Array(); 
				    	var dau_mau_count_arr = new Array(); 
				    	var dateArr = $.gameView.getDay(indicators,$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 
				    	
				    	var max_pay_count = 0;
				    	var max_install_count = 0;
				    	var max_pay_user_count = 0;
				    	var max_dau_mau_count = 0;
				    	
				    	var reports = data.reports;
				    	
				    	$.each(dateArr[1], function(i,date){
				    		var isExist = false;
				    		$.each(reports, function(j,rp){
				    			if(rp.day == date){
    				    			max_pay_count = Math.max(rp.paymentAmount,max_pay_count);
    				    			max_install_count = Math.max(rp.install,max_install_count);
    				    			max_pay_user_count = Math.max(rp.pu,max_pay_user_count);
    				    			if(indicators == 'day'){
    				    				max_dau_mau_count = Math.max(rp.dau,max_dau_mau_count);
    				    			}else{
    				    				max_dau_mau_count = Math.max(rp.mau,max_dau_mau_count);
    				    			}
    				    			
    				    			 pay_count_arr.push(rp.paymentAmount);
						    		 install_count_arr.push(rp.install);
						    		 pay_user_count_arr.push(rp.pu);
						    		 dau_mau_count_arr.push(rp.dau);
						    		
						    		isExist = true;
    				    		}
					    	}); 
				    		if(!isExist){
	 				    		   pay_count_arr.push('0');
	 					    	   install_count_arr.push('0');
	 					    	   pay_user_count_arr.push('0');
	 					    	   dau_mau_count_arr.push('0');
	 				    		}
				    	});
				    	
				    	$.gameView.brokenLine(install_count,install_count_arr,dateArr[0],max_install_count,'新注册','单位（个）');
                        $.gameView.brokenLine(pay_count,pay_count_arr,dateArr[0],max_pay_count,'付费','单位（元）');
                        $.gameView.brokenLine(pay_user_count,pay_user_count_arr,dateArr[0],max_pay_user_count,'付费人数','单位（个）');
                        if(indicators == 'day'){
                        	$.gameView.brokenLine(dau_mau_count,dau_mau_count_arr,dateArr[0],max_dau_mau_count,'DAU','单位（个）');
                        }else{
                        	$.gameView.brokenLine(dau_mau_count,dau_mau_count_arr,dateArr[0],max_dau_mau_count,'MAU','单位（个）');
                        }
                        
                        $.gameView.brokenInstallDiv(indicators,data.installRetentions,dateArr);
				    	$.gameView.brokenTable(reports);
				  }
				});
		},
		brokenLine:function(mainDiv,data,xAxisData,yAxisData,titleText,subtext){

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
					var myChart = ec.init(mainDiv,tarTheme);
					
					var max = Math.round(yAxisData * 1.2);
					var yu = max % 5;
					
					option.yAxis = [{type:'value',scale:true,splitNumber:5,min:0,max:max-yu}];
					option.xAxis = [{type:'category',data:xAxisData}];
					option.series = [{data:data,type:'line'}];
					option.title = {text:titleText,subtext:subtext};
					
					myChart.setOption(option,true);
		        });
			}
		},
		brokenTable:function(reports){
			var str = '<table id=\"dt\" class="display" cellspacing="0" width="100%"><thead>';
				str += '<tr id="t_titile"><td>日期</td><td>新注册</td><td>活跃</td><td>收入</td>';
				str += '<td>付费用户数</td></tr></thead><tbody>';
			$.each(reports,function(i,v){
				str += '<tr pv="'+(i+1)+'"><td>'+v.day+'</td><td>'+v.install+'</td><td>'+v.dau+'</td><td>'+v.paymentAmount+'</td><td>'+v.pu+'</td></tr>';
			});
			str += '</tbody></table>';
			$("#dt").remove();
			$("#data").append(str);
			
			$.biDataTables.dataTables($('#dt'));
			$('#dt tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},
		brokenInstallDiv:function(indicators,installRetentions,dateArr){
			if(indicators == 'day'){
			   document.getElementById('ir').style.display = '';
			}else{
				document.getElementById('ir').style.display = 'none';
			}
			
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
					var myChart = ec.init(document.getElementById('ir'),tarTheme);
					var dataArr = proData();
					optionIR.legend = {data:['次日留存率','三日留存率','七日留存率'],selectedMode:true,selected:{'次日留存率':true,'三日留存率':true,'七日留存率':true}};
					optionIR.yAxis = [{type:'value',scale:false,splitNumber:5,min:0,max:50}];
					optionIR.xAxis = [{type:'category',data:dateArr[0]}];
					optionIR.series = [{name:'次日留存率',data:dataArr[0],type:'line'},{name:'三日留存率',data:dataArr[1],type:'line'},{name:'七日留存率',data:dataArr[2],type:'line'}];
					optionIR.title = {text:'玩家留存',subtext:'单位（%）'};
					
					myChart.setOption(optionIR,true);
		        });
			}
			
			function proData(){
				var ir_1 = new Array();
				var ir_3 = new Array();
				var ir_7 = new Array();
				$.each(dateArr[1], function(i,date){
					var isExist_1 = false;
					var isExist_3 = false;
					var isExist_7 = false;
					$.each(installRetentions, function(j,ir){
						if(ir.installDay == date && ir.retentionDay == 1){
							ir_1.push(ir.retentionRate * 100);
							isExist_1 = true;
						}
						if(ir.ds == date && ir.retentionDay == 3){
							ir_3.push(ir.retentionRate * 100);
							isExist_3 = true;
						}
						if(ir.ds == date && ir.retentionDay == 7){
							ir_7.push(ir.retentionRate * 100);
							isExist_7 = true;
						}
			    	}); 
					if(!isExist_1){
						ir_1.push(0);
					}
					if(!isExist_3){
						ir_3.push(0);
					}
					if(!isExist_7){
						ir_7.push(0);
					}
				});
				return [ir_1,ir_3,ir_7];
			}
			
		},
		getDay:function(indicators,beginDay,endDay){
			var d = new Date(beginDay);
			var dateArr = new Array();
			var dateArr_year = new Array();
			
			if(indicators == 'day'){
				var s = '';
				dateArr.push((d.getMonth()+1) + "-" + d.getDate());
				dateArr_year.push(d.getFullYear()+"-" +(d.getMonth()+1) + "-" + d.getDate());
				var index = 0;
				
			    while(beginDay != endDay && s != endDay && index != 90){
			    	d.setDate(d.getDate()+1);
				    s = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
				    var dd =(d.getMonth()+1) + "-" + d.getDate();
				    dateArr.push(dd);
				    dateArr_year.push(s);
				    index ++;
			    }
			    return [dateArr,dateArr_year];
			}else{
				var s = '';
				var end = new Date(endDay);
				dateArr.push(d.getFullYear()+"-" +(d.getMonth()+1));
				dateArr_year.push(d.getFullYear()+"-" +(d.getMonth()+1) + "-" + d.getDate());
				var index = 0;
				
			    while(d.getMonth() != end.getMonth() && s != endDay && index != 12){
			    	d.setMonth(d.getMonth()+1);
				    s = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
				    var dd =(d.getMonth()+1)+"月";
				    dateArr.push(dd);
				    dateArr_year.push(s);
				    index ++;
			    }
			    return [dateArr,dateArr_year];
			}
		}
	};
});
	                    