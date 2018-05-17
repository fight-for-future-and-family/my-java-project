$(function(){
	
	optionCumuli = {
			title:{text:'消费额',subtext:'单位（'+$("#gameCurrency").val()+'）',x:70,y:15},
		    tooltip : {trigger: 'axis',axisPointer : {type : 'shadow'}},
		    legend: {data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎'],x:'47%',y:19},
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
		                    
	
	var list = new Array();
	var dateArr = null;
	var countArr = new Array();
	var esAmountCount = new Array();
	$(document).ready(function() {
	    $.economyData.init();
	    $.timeZone.showTimeZone();
	});
		
	$.economyData={
			init:function(){
				$.economyData.initEvent();
				$.economyData.submit();
			},
			initEvent:function(){
				$("#query").click(function(){
					$.economyData.submit();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.economyData.redict($(n).attr("id"));
					});
				});
				
				$("#indicators").change(function(){
					$.economyData.change();
				});
				
			},
			redict:function(view){
				window.location.href='/panel_bi/economy/toGameEconomy.ac?view='+view;
			},
			submit:function(){
				var ps = $.economyData.checkParam();
				if(ps == null){
					return;
			    }
				
				$('#data').show();
				
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/economy/getGameEconomy.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  
					    dateArr = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 
					    list = new Array();
					    esAmountCount = new Array();
					    
						for(var c=0;c<dateArr[1].length;c++){
							countArr[c] = [0,0,0,0];
						}
						
						$.each(data.consumePointList,function(k,es){
							var dataTemp = new Array();
							dataTemp.push(es);
							
							$.each(dateArr[1],function(i,date){
								var isHavEs = false;
								$.each(data.reports,function(j,data){
									if(es == data.consumePoint){
										if(data.ds == date){
											isHavEs = true;
											
											// 计算同一天所有消费点的总和，用以计算次消费点在此天的消费比例
											countArr[i][0] = countArr[i][0] + data.amount;
											countArr[i][1] = countArr[i][1] + data.users;
											countArr[i][2] = countArr[i][2] + data.times;
											countArr[i][3] = countArr[i][3] + data.itemNum;
											
											// 消费点不分天总和，用以计算前10个消费点.不足10个的全部展示
											if(true){
												$.economyData.addEsAmountCount(es,data.amount);
											}
											
											//以消费点为基准，每一天的指标数值
											dataTemp.push([data.amount,data.users,data.times,data.itemNum]);
											return false;
										}
									}
								});
								if(!isHavEs){
									dataTemp.push(['-','-','-','-']);
								}
							});
							
							list.push(dataTemp);
						});
						    	
						$.economyData.brokenBar();
					    $.economyData.brokenTable(1);
					  }
				});
			},
			addEsAmountCount:function(es,data){
				var isHave= false;
				$.each(esAmountCount,function(j,esObj){
					if(esObj.es == es){
						esObj.data += data;
						isHave = true;
					}
				});
				if(!isHave){
					esAmountCount.push({es:es,data:data});
				}
			},
			checkParam:function(){
				var ps={
					     indicators:$('#indicators').val(),
						 beginTime:$("input[name='beginTime']").val(),
						 endTime:$("input[name='endTime']").val(),
						 beginClientid:$('#b_s_c').val(),
						 endClientid:$('#e_s_c').val(),
						 view:$('#view').val()
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
			      
			      var dateArr = $.economyData.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='beginTime']").val(dateArr[0]);
			      $("input[name='endTime']").val(dateArr[1]);
			      
			      return ps;
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
			    if(b.getTime() < d.getTime()){
			    	alert('日期区间不能大于15天！');
			    	return null;
			    }
			      
				return [beginTime,endTime];
			},
			change:function(){

				var value = $("#indicators").val();
				if(value == 'all'){
					$("#s_c_span").text('');
					$.economyData.submit();
				} else if(value == 'client'){
					$("#s_c_span").text('');
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/economy/getGameClient.ac",
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
			brokenTable:function(index){
				var rate = $("#gameRate").val();
				
				$('#dt_wrapper').remove();
				
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt");
				$('#data').append(table);
				
				var thTemp = $("tr", table).first();
				$.each(dateArr[1],function(j,date){
					var tdTemp = $("td", thTemp).first().clone(true);
					tdTemp.text(date);
					thTemp.append(tdTemp);
				});
				
				$.each(list,function(i,data){
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.es", trTemp).text(data[0]);
					
					for(var k=1;k<data.length;k++){
						switch(index){
						case 1:
							var amountTemp = $(".amount", trTemp).first().clone(true);
							
							var amount_number = data[k][0] == '-' ? '-' : Math.round((data[k][0] / rate) * 100)/100;
							var amount_point = amount_number == '-' ? '-' : Math.round((amount_number / countArr[k-1][0]) * 10000)/100;
							amountTemp.html('<span title="'+amount_point+'%">'+amount_number+'</span>');
							
							trTemp.append(amountTemp);
							break;
						case 2:
							var usersTemp = $(".users", trTemp).first().clone(true);
							
							var users_number = data[k][1];
							var users_point = users_number == '-' ? '-' : Math.round(users_number / countArr[k-1][1] * 10000)/100;
							usersTemp.html('<span title="'+users_point+'%">'+users_number+'</span>');
							
							trTemp.append(usersTemp);
							break;
						case 3:
							var timesTemp = $(".times", trTemp).first().clone(true);
							
							var times_number = data[k][2];
							var times_point = times_number == "-" ? '-' : Math.round(times_number / countArr[k-1][2] * 10000)/100;
							timesTemp.html('<span title="'+times_point+'%">'+times_number+'</span>');
							
							trTemp.append(timesTemp);
							break;
						case 4:
							var itemNumTemp = $(".itemNum", trTemp).first().clone(true);
							
							var itemNum_number = data[k][3];
							var itemNum_point = itemNum_number == "-" ? '-' : Math.round(itemNum_number / countArr[k-1][3] * 10000)/100;
							itemNumTemp.html('<span title="'+itemNum_point+'%">'+itemNum_number+'</span>');
							
							trTemp.append(itemNumTemp);
							break;
						}
					}
					
					$(".amount", trTemp).first().remove();
					$(".users", trTemp).first().remove();
					$(".times", trTemp).first().remove();
					$(".itemNum", trTemp).first().remove();
					
					$('tbody', table).append(trTemp);
				});
				$("tbody tr", table).first().remove();
				
				var table = $('#dt').DataTable({
					"sPaginationType" : "full_numbers",
//		            "aoColumnDefs": [ { "bSortable": false, "aTargets": [1] }] ,
		            "dom" : '<"clear"><"top">frt<"bottom"ip><"clear">' ,
		            "bDestroy" : true,
		            "bPaginate" : true,
		            "bInfo" : true,
					"bSort" : true,
					"bScrollCollapse" : true,
					"bScrollInfinite" : true,
				    "bFilter" : true,
				    "paging": true, //翻页功能
				    "lengthChange": false, //改变每页显示数据数量
				    "searching": true, //过滤功能
				    "bSearchable" : false,
				    "ordering": true, //排序功能
				    "order": [[ 0, "asc" ]],
				    "processing": true,
				    "language":{
				    	"url": "/dataTables/chinese.json"
				    }
				});
				
				$('#dt').removeClass();
				$('#dt').addClass('table table-striped');
					
				$("#caption span").removeClass();
				switch(index){
				case 1:
					$("#t_amount").addClass("ui-selected");
					break;
				case 2:
					$("#t_users").addClass("ui-selected");
					break;
				case 3:
					$("#t_times").addClass("ui-selected");
					break;
				case 4:
					$("#t_itemNum").addClass("ui-selected");
					break;
				}
				
				$("#caption span").each(function(i,n){
					$(n).click(function(e){
						//e.preventDefault();
						 
						$("#caption span").removeClass();
						$(this).addClass("ui-selected");
						
						var val = $(this).attr("val");
						if(val=='amount'){
							index = 1;
						}else if(val=='users'){
							index = 2;
						}else if(val=='times'){
							index = 3;
						}else if(val=='itemNum'){
							index = 4;
						}
						$("#caption span").unbind("click");
						$.economyData.brokenTable(index);
					});
				});
				
				$('#dt tbody tr').click(function (){
				     $(this).toggleClass('highlight');
				 });
				
				$('#caption button').click(function (){
			        $(this)[0].disabled = true;
			        $("#downLoadForm").submit();
			    });
			},
			showAndHiddenCol:function(table,dateLength,index,isShow){
				return false;
				
				if(index == -1){
					for(i=1;i<=dateLength*4;i++){
						var column = table.column(i);
				        column.visible(isShow);
					}
				}else{
					var col = new Array();
					
					col.push(index);
					for(var i=2;i<=dateLength;i++){
						col.push((i-1)*4+index);
					}
					
					$.each(col,function(j,colIndex){
						var column = table.column(colIndex);
				        column.visible(isShow);
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
						var rate = $("#gameRate").val();
						
						var series = new Array();
			            var esArr = new Array();
			            
						if(list.length > 10){
							
							var compare = function (prop) {
							    return function (obj1, obj2) {
							        var val1 = obj1[prop];
							        var val2 = obj2[prop];
							        if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
							            val1 = Number(val1);
							            val2 = Number(val2);
							        }
							        if (val1 < val2) {
							            return -1;
							        } else if (val1 > val2) {
							            return 1;
							        } else {
							            return 0;
							        }            
							    } 
							};
							
							esAmountCount.sort(compare("data"));
							for(var i=esAmountCount.length-1; i>=0;--i){
								esArr.push(esAmountCount[i].es);
								if(i==(esAmountCount.length - 10)){
									esArr.push('其他');
									break;
								}
							}
							
				            var other = new Array();
				            for(var o=0;o<dateArr[0].length;o++){
				            	other[o] = 0;
				            }
				            $.each(list,function(i,v){
				            	var isNotHave = true;
				            	 $.each(esArr,function(i,es){
				            		 if(es == v[0]){
				            			 var data = new Array();
							             for(var k=1;k<v.length;k++){
							            	data.push(v[k][0] == '-' ? 0 : Math.round((v[k][0] / rate) * 100)/100);
										 }
												
						            	 series.push({
						            		name:v[0],
								            type:'line',
								            stack: '总量',
								            itemStyle: {normal: {areaStyle: {type: 'default'}}},
								            data:data
						            	 });
						            	 isNotHave = false;
				            		 }
				            	 });
				            	 
				            	 if(isNotHave){
					            		for(var k=1;k<v.length;k++){
					            			var other_data = v[k][0] == '-' ? '-' : Math.round((v[k][0] / rate) * 100)/100;
					            			if(other_data != '-'){
					            				if(other[k-1] != undefined && other[k-1] != 0){
					            					other[k-1] = other[k-1] + other_data;
					            				}else{
					            					other[k-1] = other_data;
					            				}
					            			}
										}
					            	 }
							});
				           
				            series.push({
				            	name:'其他',
					            type:'line',
					            stack: '总量',
					            itemStyle: {normal: {areaStyle: {type: 'default'}}},
					            data:other	 
				            });
							
						}else{
				            
				            $.each(list,function(i,v){
		            			 var data = new Array();
					             for(var k=1;k<v.length;k++){
					            	data.push(v[k][0] == '-' ? 0 : Math.round((v[k][0] / rate) * 100)/100);
								 }
										
					             esArr.push(v[0]);
				            	 series.push({
				            		name:v[0],
						            type:'line',
						            stack: '总量',
						            itemStyle: {normal: {areaStyle: {type: 'default'}}},
						            data:data
				            	 });
							});
				            
						}
						
						optionCumuli.series = series;
			            optionCumuli.legend.data = esArr;
			            optionCumuli.xAxis[0].data = dateArr[0];
			            
						myChart.setOption(optionCumuli,true);
			        });
				}
			}
	};	
});
	                    