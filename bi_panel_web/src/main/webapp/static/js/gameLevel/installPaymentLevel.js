$(function(){
	
	option = {
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        formatter: '{b} <br/>'
		    },
		    legend: {
		        data:[]
		    },
		    toolbox: {
		    	show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    yAxis : [{
		    	type : 'value',
				axisLabel : {
					formatter : '{value} %'
				},
				boundaryGap : [ 0, 0.01 ]
				}
		    ],
		    xAxis : [ {
		            type : 'category',
		            data : []
		        }],
		    series : []
		};
		                    
	
	var dateArr = null;
	var itemArr = new Array();
    var itemAmountArr = new Array();
    var jq_table = null;
    var isBrokenBar = false;
    var itemCountArr = new Array();
    var isHaveData = null;
    var rate = 1;
    var optionToopTipIndex = 0;
    var colorArr = ['#D9FFFD','#97FFF4','#01E7FA',
                    '#01D6E7','#01D1F5','#01C8EB','#39A8F9',
                    '#269FF9','#1899F8','#078EF1','#FC324B'];
    
	$(document).ready(function() {
	    $.installPaymentLevel.init();
	    $.timeZone.showTimeZone();
	});
		
	$.installPaymentLevel={
			init:function(){
				$.installPaymentLevel.initEvent();
				$.installPaymentLevel.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
					dateArr = null;
				    jq_table = null;
				    $('#caption button').attr("disabled",false); 
				    option.legend.data = [];
					option.tooltip.formatter = '{b} <br/>';
					option.series = [];
					optionToopTipIndex = 0;
					
					$.installPaymentLevel.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.installPaymentLevel.redict($(n).attr("id"));
					});
				});
				
				$("#caption span").each(function(i,n){
					$(n).click(function(e){
//						e.preventDefault();
						
						$("#caption span").removeClass();
						$(this).addClass("ui-selected");
						$("#caption span").last().addClass('last');
						
						dateArr = null;
					    jq_table = null;
					    $('#caption button').attr("disabled",false); 
					    option.legend.data = [];
						option.tooltip.formatter = '{b} <br/>';
						option.series = [];
						
						var val = $(this).attr("val");
						$("#queryType").val(val);
						$.installPaymentLevel.queryData();
					});
				});
				
				$("#desc").click(function(){
					if($("#descText").text() == '展开说明'){
						$("#descText").text('关闭说明');
						$("#descImg").attr('src','/images/up.png');
					}else{
						$("#descText").text('展开说明');
						$("#descImg").attr('src','/images/down.png');
					}
					$("#equipDesc").fadeToggle();
				});
				
			},
//			redict:function(view){
//				window.location.href='/panel_bi/payerLevel/toGamePayerLevel.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/payerLevel/toGamePayerLevel.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			getLevelDateValue:function(count_10_arr,v){
				count_10_arr[0] += v.day0;
				count_10_arr[1] += v.day1;  
				count_10_arr[2] += v.day2;  
				count_10_arr[3] += v.day3;  
				count_10_arr[4] += v.day4;  
				count_10_arr[5] += v.day5;  
				count_10_arr[6] += v.day6;  
				count_10_arr[7] += v.day7;  
				count_10_arr[8] += v.day8;  
				count_10_arr[9] += v.day9;  
				count_10_arr[10] += v.day10; 
				count_10_arr[11] += v.day11;
				count_10_arr[12] += v.day12;
				count_10_arr[13] += v.day13;
				count_10_arr[14] += v.day14;
			},
			createTr:function(table,tbody,tempInterval,index,count_10_arr,count_all_arr,isPay){
				var pointArr = new Array();
				
				var sh = tempInterval;//tempLevel % 10 == 0 ? Math.floor(tempLevel / 10) :  Math.round(tempLevel / 10);
				var levelName = ((sh-1) * 10 + 1)+'~'+sh*10;
				var tr = $("tbody tr",table).eq(0).clone(true);
				$("td",tr).first().html('<span class="row-details row-details-close" data='+sh+'></span> '+levelName);
				
				for(var j=0;j<index;j++){
					var td = $("td",tr).first().clone(true);
					var point = '';
					if(isPay){
						point = $.installPaymentLevel.processCellData(Math.round(count_10_arr[j]/rate*100)/100,Math.round(count_all_arr[j]/rate*100)/100)
					}else{
						point = $.installPaymentLevel.processCellData(count_10_arr[j],count_all_arr[j])
					}
					
					td.html(point);
					tr.append(td);
					
					var number = Number(td.text().replace('%',''));
					pointArr.push(number);
					
					if(number >= 100){
						td.css('background-color',colorArr[colorArr.length-1]);
					}else if(number > 0 && number < 100){
						var colorIndex = Math.floor(number / 10);
						td.css('background-color',colorArr[colorIndex]);
					}
	        	}
				$("td",tr).first().addClass("table-striped row-details-td");
				tbody.append(tr);
				
				var obj = {
						name:levelName,
			            type:'bar',
			            stack: '总量',
			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
			            data:pointArr
				};
				
				option.legend.data.push(levelName);
				option.tooltip.formatter = option.tooltip.formatter + '{a'+optionToopTipIndex+'} : {c'+optionToopTipIndex+'} %<br/>';
				option.series.push(obj);
				
				optionToopTipIndex++;
			},
			queryData:function(){
				var ps = $.installPaymentLevel.checkParam();
				if(ps == null){
					return;
			    }
				
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/payerLevel/getGameLevelData.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  
							dateArr = $.gameUtil.getDay('day',ps.beginTime,ps.endTime);
							var reports = data.reports;
								
							$('#dt_ajax_wrapper').remove();
							var table = $(".template_cache .view_table").clone(true);
							table.attr("id", "dt_ajax");
							$('#data').append(table);
							
							rate = $("#gameRate").val();
							
							var index = dateArr[1].length;
							var th = $("thead tr",table);
							for(var i=0;i<index;i++){
								var td = $("td",th).first().clone(true);
								if(i == 0){
									td.text('当日');
								}else if(i == 1){
									td.text('次日');
								}else{
									td.text(i+'日');
								}
							  	
								th.append(td);
				        	}
						
							var tbody = $("tbody",table);
								
							var count_10_arr = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
							var count_all_arr = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
							var count_level_arr = new Array();
							var tempInterval = 1;
							
							if(reports.length > 1){
								$.installPaymentLevel.getLevelDateValue(count_all_arr,reports[reports.length - 1]);
								tempInterval = Math.ceil(reports[0].level / 10);
								
								var isPay = ps.queryType == 'install_payment';
								$.each(reports,function(i,v){
									if(v.level == -1){//不出意外情况这是数组的最后一个元素
										$.installPaymentLevel.createTr(table,tbody,tempInterval,index,count_10_arr,count_all_arr);
										
										var tr = $("tbody tr",table).first().clone(true);
										$("td",tr).text(isPay ? '总消费额('+$("#gameCurrency").val()+')' :'总人数');
										for(var j=0;j<index;j++){
											var td = $("td",tr).first().clone(true);
											td.text(isPay ? Math.round(count_all_arr[j]/rate*100)/100 : count_all_arr[j]);
											tr.append(td);
							        	}
										$("td",tr).first().addClass("table-striped row-details-td");
										tbody.prepend(tr);
									}else if(v.level >= ((tempInterval-1) * 10 + 1) && v.level <= (tempInterval * 10)){
										$.installPaymentLevel.getLevelDateValue(count_10_arr,v);
									}else{
										$.installPaymentLevel.createTr(table,tbody,tempInterval,index,count_10_arr,count_all_arr,isPay);
										
										tempInterval = Math.ceil(v.level / 10);
										count_10_arr = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
										
										$.installPaymentLevel.getLevelDateValue(count_10_arr,v);
									}
								});
								
								$("tbody tr", table).eq(1).remove();
							}else{
								$("tbody tr", table).eq(0).remove();
							}
							
							jq_table = $('#dt_ajax').dataTable( {
						        "processing": false,
						        "serverSide": false,
						        "pageLength": 20,
							    "language":{
							    	"url": "/dataTables/chinese.json"
							    },
						        "deferRender": true,
							    "sPaginationType" : "full_numbers",
						        "dom" : '<"clear"><"top">frt<"bottom"ip><"clear">' ,
								"bSort" : false,
							    "bFilter" : false,
							    "paging": false, //翻页功能
							    "lengthChange": false, //改变每页显示数据数量
							    "searching": false, //过滤功能
							    "ordering": false, //排序功能
							    "order": [[ 1, "desc" ]]
						    });

							$('#dt_ajax').removeClass().addClass('table table-striped');
							
							
							$('#dt_ajax').on('click', ' tbody td .row-details',
						               function() {
								           var tr = $(this).parents('tr');
						                   var nTr = tr[0];
						                   if (jq_table.fnIsOpen(nTr)) //判断是否已打开
						                   {
						                       /* This row is already open - close it */
						                       $(this).addClass("row-details-close").removeClass("row-details-open");
						                       jq_table.fnClose(nTr);
						                   } else {
						                       /* Open this row */
						                       $(this).addClass("row-details-open").removeClass("row-details-close");
						                       
						                       var data_index = Number($(this).attr("data"));
						                       
						                       var table =  $('<table class="table-striped details"> </table>');
						                       $.each(reports,function(i,v){
						                    	   if(v.level >= ((data_index-1) * 10 + 1) && v.level <= (data_index * 10)){
						                    		   var ctr = tr.clone(true);
								                       $("td",ctr).remove();
								                       
						                    		   var td = $("td",tr).eq(0).clone(true);
						                    		   //td.removeClass();
						                    		   td.text(v.level);
						                    		   ctr.append(td);
						                    		   for(var i=0;i<index;i++){
															var td = $("td",tr).eq(0).clone(true);
															var value = $.installPaymentLevel.getItemValue(i,v);
														  	td.html($.installPaymentLevel.processCellData((isPay ? Math.round(value/rate*100)/100 : value),(isPay ? Math.round(count_all_arr[i]/rate*100)/100 : count_all_arr[i])));
														  	ctr.append(td);
														  	
														  	var number = Number(td.text().replace('%',''));
															if(number >= 100){
																td.css('background-color',colorArr[colorArr.length-1]);
															}else if(number > 0 && number < 100){
																var colorIndex = Math.floor(number / 10);
																td.css('background-color',colorArr[colorIndex]);
															}
											        	}
						                    		   table.append(ctr);
						                    	   }
						                       });
						                       
						                       jq_table.fnOpen(nTr, table,'table-striped details-tr');
						                   }
						               });
							
							$('#caption button').click(function (){
								if(confirm("下载可能需要一段时间请耐心等待不要离开页面，是否确认下载？")){
							        $(this).attr('disabled',true);
							        $(this).val(2);
							        window.btnTimeout=setInterval(function(){
										$.gameUtil.btnTimeout($('#caption button'),window.btnTimeout);
									  },1000);
							        $("#downLoadForm").submit();
								}
						    });
							
							$.installPaymentLevel.brokenBar();
					  }
				});
				
			},
			processCellData:function(value,count){
				
				if(value == 0 || count == 0){
					return 0;
				}else if(value == null || value == ''){
					return '';
				}
				
			    var point = Math.round((value / count) * 10000)/100;
				return '<span class="fucengData" title="'+value+'">'+point+'%</span>';
			},
			checkParam:function(){
				var ps={
						 beginTime:$("input[name='ec_beginTime']").val(),
						 endTime:'',
						 view:$('#view').val(),
						 queryType:$('#queryType').val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val()
					};
			      
				if(ps.beginTime == null || ps.beginTime == ''){
					var b = new Date();
					ps.endTime = $.date.getDateFullStr(b);
			    	
					b.setDate(b.getDate() - 14);
			    	ps.beginTime = $.date.getDateFullStr(b);
				}else{
					
					var d = new Date();
				    var b = new Date(ps.beginTime);
				    if(d.getTime() < b.getTime()){
				         alert('日期不能大于当天！');
			    	     return null;
				     }
					
					b.setDate(b.getDate() + 14);
					
					var endCurStr = $.date.getDateFullStr(d);
					var endStr = $.date.getDateFullStr(b);
					if(endStr > endCurStr){
						ps.endTime = endCurStr;
					}else{
						ps.endTime = endStr;
					}
				}
			    
			   $("input[name='ec_beginTime']").val(ps.beginTime);
			      
			   return ps;
			},
			getItemValue:function(index,v){
				var tempValue = 0;
	    		switch(index){
	    		case 0:
	    			tempValue = v.day0;
	    			break;
	    		case 1:
	    			tempValue = v.day1;
	    			break;
	    		case 2:
	    			tempValue = v.day2;
	    			break;
	    		case 3:
	    			tempValue = v.day3;
	    			break;
	    		case 4:
	    			tempValue = v.day4;
	    			break;
	    		case 5:
	    			tempValue = v.day5;
	    			break;
	    		case 6:
	    			tempValue = v.day6;
	    			break;
	    		case 7:
	    			tempValue = v.day7;
	    			break;
	    		case 8:
	    			tempValue = v.day8;
	    			break;
	    		case 9:
	    			tempValue = v.day9;
	    			break;
	    		case 10:
	    			tempValue = v.day10;
	    			break;
	    		case 11:
	    			tempValue = v.day11;
	    			break;
	    		case 12:
	    			tempValue = v.day12;
	    			break;
	    		case 13:
	    			tempValue = v.day13;
	    			break;
	    		case 14:
	    			tempValue = v.day14;
	    			break;
	    		case 15:
	    			tempValue = v.day15;
	    			break;
	    		}
	    		return tempValue == null ? 0 : tempValue;
			},
			brokenBar:function(){
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
						var myChart = ec.init($("#dau_level_chart")[0],tarTheme);
						
						option.xAxis[0].data = dateArr[0];
			            
						myChart.setOption(option,true);
			        });
				}
			}
	};	
});
	                    