$(function(){
	
	optionCumuli = {
			title:{text:'消费额',x:70,y:15},
		    tooltip : {trigger: 'axis',axisPointer : {type : 'shadow'}},
		    legend: {data:['邮件营销','联盟广告'],x:'14%',y:19},
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
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            data : ['周一','周二','周三','周四','周五','周六','周日']
		        }
		    ],
		    yAxis : [{type : 'value',boundaryGap: [0, 0.01]}],
		    series : [
		        {
		            name:'邮件营销',
		            type:'line',
		            stack: '总量',
		            itemStyle: {normal: {areaStyle: {type: 'default'}}},
		            data:[120, 132, 101, 134, 90, 230, 210]
		        }
		    ]
		};
		                    
	
	var dateArr = null;
	var itemArr = new Array();
    var itemAmountArr = new Array();
    var jq_table = null;
    var isBrokenBar = false;
    var itemCountArr = new Array();
    var isHaveData = null;
    
	$(document).ready(function() {
	    $.economyItemData.init();
	    $.timeZone.showTimeZone();
	});
		
	$.economyItemData={
			init:function(){
				$.economyItemData.initEvent();
				$.economyItemData.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
					isBrokenBar = false;
					dateArr = null;
					itemArr = new Array();
				    itemAmountArr = new Array();
				    jq_table = null;
				    $('#caption button')[0].disabled = false;
				    if(window.win_brokenBar!=null){
						window.clearInterval(window.win_brokenBar);    
					}
				    
					$.economyItemData.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.economyItemData.redict($(n).attr("id"));
					});
				});
				
				$("#indicators").change(function(){
					$.economyItemData.change();
				});
				
				$("#caption span").each(function(i,n){
					$(n).click(function(e){
//						e.preventDefault();
						
						var val = $(this).attr("val");
						$("#queryType").val(val);
						
						$("#caption span").removeClass();
						$(this).addClass("ui-selected");
						$("#caption span").last().addClass("last");
						
						$.economyItemData.queryData();
					});
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/economy/toGameEconomy.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/economy/toGameEconomy.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			queryData:function(){
				var ps = $.economyItemData.checkParam();
				if(ps == null){
					return;
			    }
				
				var selected = [];
				dateArr = $.gameUtil.getDay('day',$("input[name='ec_beginTime']").val(),$("input[name='ec_endTime']").val());
				
				$('#dt_ajax_wrapper').remove();
				
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var columnsArr = new Array();//列定义数组
				var columnsDefArr = new Array();//列值定义数组
				columnsArr.push({"data": "consumeItem","title":"消费道具","defaultContent":""});
	        	var index = dateArr[1].length;
	        	for(var i=1;i<=index;i++){
	        		columnsArr.push({"data": null,"title":dateArr[1][i-1],"defaultContent": ""});
	        	}
	        	
	        	columnsDefArr.push({ orderable: false, targets: 0 });
	        	for(var k=1;k<=index;k++){
	        		columnsDefArr.push({
                        targets: k,
                        render: function (a, b, v, d) {
                        	       return $.economyItemData.processCellData(d.col,v);
                        	}
                    });
	        	}
	        	
				jq_table = $('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
				          data: function(d){
				        	  d.indicators=ps.indicators;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.beginClientid=ps.beginClientid;
				        	  d.endClientid=ps.endClientid;
				        	  d.view=ps.view;
				        	  d.queryType=ps.queryType;
				        	  d.groupType=ps.groupType;
				        	  d.snid=ps.snid;
				        	  d.gameId=ps.gameId;
				          },
						  type: "POST",
						  url:"/panel_bi/economy/getGameEconomy.ac"
//							  ,
//						  success : function(data,callback,settings){
//							  callback(
//								  $.economyItemData.initChartData(data)
//							  );
//						  }
		              },
			        "pageLength": 10,
				    "language":{
				    	"url": "/dataTables/chinese.json"
				    },
			        "deferRender": true,
				    "sPaginationType" : "full_numbers",
			        "dom" : '<"clear"><"top">frt<"bottom"ip><"clear">' ,
					"bSort" : true,
				    "bFilter" : true,
				    "paging": true, //翻页功能
				    "lengthChange": false, //改变每页显示数据数量
				    "searching": true, //过滤功能
				    "ordering": true, //排序功能
				    "order": [[ 1, "desc" ]],
				    rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                    },
				    "columns":columnsArr,
				    "columnDefs":columnsDefArr
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				$('#caption button').unbind(); 
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
				
				$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
				
				
				if(!isBrokenBar){
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/economy/getGameEconomy.ac?isCountRequest=1",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
							    game = data.game;
							    itemCountArr = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
							    
							    var v = data.dataCount;
							    if(v != null){
							    	itemCountArr[0]=(v.day1);
							    	itemCountArr[1]=(v.day2);
							    	itemCountArr[2]=(v.day3);
							    	itemCountArr[3]=(v.day4);
							    	itemCountArr[4]=(v.day5);
							    	itemCountArr[5]=(v.day6);
							    	itemCountArr[6]=(v.day7);
							    	itemCountArr[7]=(v.day8);
							    	itemCountArr[8]=(v.day9);
							    	itemCountArr[9]=(v.day10);
							    	itemCountArr[10]=(v.day11);
							    	itemCountArr[11]=(v.day12);
							    	itemCountArr[12]=(v.day13);
							    	itemCountArr[13]=(v.day14);
							    	itemCountArr[14]=(v.day15);
							    	
							    	$.economyItemData.initChartData(data.chartData);
							    	isHaveData = 1;
							    }else{
							    	isHaveData = 2;
							    }
						  }
					});
					
					window.win_brokenBar=setInterval(function(){
						$.economyItemData.win_brokenBar();
					  },500);
					isBrokenBar = true;
				}else{
//					window.win_maodian=setInterval(function(){
//						var test_tr = $("#dt_ajax tbody tr");
//						if(test_tr.length<1){
//							return ;
//						}
//						
//						if(window.win_maodian!=null){
//							window.clearInterval(window.win_maodian);    
//						}
//						var t = $("#mao").offset().top;
//						$(window).scrollTop(t);//滚动到锚点位置
//					  },100);
				}
			},
			win_brokenBar:function(){
				var test_tr = $("#dt_ajax tbody tr");
				if(test_tr.length<1 || isHaveData == null){
					return ;
				}
				
				if(window.win_brokenBar!=null){
					window.clearInterval(window.win_brokenBar);    
				}
				
				$.economyItemData.brokenBar();
			},
			processCellData:function(index,v){
				
				var value = $.economyItemData.getItemValue(index,v)
			    
				if(value == 0){
					return 0;
				}else if(value == null || value == ''){
					return '';
				}
				
				var queryType = $("#queryType").val();
				if("amount" == queryType){
					var amount_point = Math.round((value / itemCountArr[index-1]) * 10000)/100;
					return '<span title="'+amount_point+'%">'+value+'</span>';
				}else{
					var point = Math.round((value / itemCountArr[index-1]) * 10000)/100;
					return '<span title="'+point+'%">'+value+'</span>';
				}
			},
			checkParam:function(){
				var ps={
					     indicators:$('#indicators').val(),
						 beginTime:$("input[name='ec_beginTime']").val(),
						 endTime:$("input[name='ec_endTime']").val(),
						 beginClientid:$('#b_s_c').val(),
						 endClientid:$('#e_s_c').val(),
						 view:$('#view').val(),
						 queryType:$('#queryType').val(),
						 groupType:$('#groupType').val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val()
					};
			      
			      if(ps.indicators == null || ps.indicators == ''){
			    	  ps.indicators = 'all'; 
			       }else if(ps.indicators == 'client'){
			    	   if(ps.beginClientid != null && ps.beginClientid == '-1'){
			    		   return null;
			    	   }
			    	   if(ps.endClientid != null && ps.endClientid == '-1'){
			    		   return null;
			    	   }
			       }
			      
			      if(ps.queryType == null || ps.queryType == ''){
			    	  ps.queryType = 'amount';
			      }
			      if(ps.groupType == null || ps.groupType == ''){
			    	  ps.groupType = 'first';
			      }
			      
			      var dateArr = $.economyItemData.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='ec_beginTime']").val(dateArr[0]);
			      $("input[name='ec_endTime']").val(dateArr[1]);
			      
			      return ps;
			},
			getItemValue:function(index,v){
				var tempValue = null;
	    		switch(index){
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
	    		return tempValue;
			},
			initChartData:function(data){
				itemArr = new Array();
			    itemAmountArr = new Array();
			    var countTemp = itemCountArr.slice(0);
			    
		    	$.each(data,function(k,v){
			    	itemArr.push(v.consumeItem);
			    	
			    	var temp = new Array();
			    	var index = 1;
			    	do{
				    	var tempValue = $.economyItemData.getItemValue(index,v);
				    		
			    		if(tempValue == undefined || tempValue == null){
		    				tempValue = 0;
		    			}
			    		countTemp[index-1] = countTemp[index-1] - tempValue;
			    		temp.push(tempValue);
			    		index ++;
			    	}while(index <= dateArr[1].length);
			    	
			    	itemAmountArr.push(temp);
			    });
		    	
		    	itemArr.push("其他");
		    	itemAmountArr.push(countTemp.slice(0,dateArr[1].length));
			},
			processDate:function(beginTime,endTime){
			    if((endTime == null || endTime == '') && (beginTime == null || beginTime == '')){
			    	  var d = new Date();
			    	  d.setDate(d.getDate() - 1);
			    	  endTime = $.date.getDateFullStr(d);
			    	  
			    	  d = new Date(endTime);
			    	  d.setDate(d.getDate()- 7);
			    	  beginTime = $.date.getDateFullStr(d);
			    	  
			       }else if((endTime == null || endTime == '') && (beginTime != null && beginTime != '')){
			    	  var d = new Date(beginTime);
			    	  d.setDate(d.getDate()+ 7);
			    	  
			    	  endTime = $.date.getDateFullStr(d);
			       }else if((endTime != null && endTime != '') && (beginTime == null || beginTime == '')){
			    	  var d = new Date(endTime);
			    	  d.setDate(d.getDate()- 7);
			    	  
			    	  beginTime = $.date.getDateFullStr(d);
			      }
			    
			    var d = new Date(endTime);
			    var b = new Date(beginTime);
			    if(d.getTime() < b.getTime()){
			         alert('结束时间不能小于开始时间');
		    	     return null;
			     }
			    
			    b.setDate(b.getDate() + 15);
			    if(b.getTime() <= d.getTime()){
			    	alert('日期区间不能大于15天！');
			    	return null;
			    }
			      
				return [beginTime,endTime];
			},
			change:function(){
				var ps = $.economyItemData.checkParam();
				var value = $("#indicators").val();
				if(value == 'all'){
					$("#s_c_span").text('');
					$.economyItemData.submit();
				} else if(value == 'client'){
					$("#s_c_span").text('');
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/economy/getGameClient.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str_b = '<select id="b_s_c" name="b_s_c"';
						    var str_e = '<select id="e_s_c" name="e_s_c"';
						    var str = ' style="max-width:240px"><option value="-1">请选择...</option>';
						    $.each(data.gameClients,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str_b + str);
						    span.append(" 至  ");
						    span.append(str_e + str);
						  }
					});
				}
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
						var myChart = ec.init($("#economy_chart")[0],tarTheme);
						
						var series = new Array();
							
			            $.each(itemAmountArr,function(i,v){
			            	 series.push({
			            		name:itemArr[i],
					            type:'line',
					            stack: '总量',
					            itemStyle: {normal: {areaStyle: {type: 'default'}}},
					            data:v
			            	 });
		            	 });
						
						optionCumuli.series = series;
			            optionCumuli.legend.data = itemArr;
			            optionCumuli.xAxis[0].data = dateArr[0];
			            
						myChart.setOption(optionCumuli,true);
			        });
				}
			}
	};	
});
	                    