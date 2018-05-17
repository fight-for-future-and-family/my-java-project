$(function(){
	
	option_funnel = {
		    title : {text: '留存',subtext: '平均值(%)',x:15,y:8},
		    tooltip : {trigger: 'item', formatter: "{a} <br/>{b} : {c}%"},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : { mark : {show: true},restore : {show: true},saveAsImage : {show: true} }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'留存',
		            type:'funnel',
		            x: '2%',y: 60,
		            y2: 60, width: '100%',
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
	var colorArr = ['#D9FFFD','#97FFF4','#01E7FA',
	                '#01D6E7','#01D1F5','#01C8EB','#39A8F9',
	                '#269FF9','#1899F8','#078EF1','#FC324B'];
	$(document).ready(function() {
	    $.newEquipRetention.init();
	    $.timeZone.showTimeZone();
	});
		
	$.newEquipRetention={
			init:function(){
				$.newEquipRetention.initEvent();
				$.newEquipRetention.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
				    $('#caption button')[0].disabled = false;
					$.newEquipRetention.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.newEquipRetention.redict($(n).attr("id"));
					});
				});
				
				$("#indicators").change(function(){
					$.newEquipRetention.change();
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
								$.newEquipRetention.brokenFunnelTable('ir_funnel');
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
									$.newEquipRetention.brokenFunnelTable('ir_x');
									$("#ir_x_data").fadeIn();
								}
							}
						});
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/equip/toEuipment.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/equip/toEuipment.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			queryData:function(){
				var ps = $.newEquipRetention.checkParam();
				if(ps == null){
					return;
			    }
				
				if(ps.indicators == 'all'){
					$('#ir_funnel_chart').show();
					$('#ir_x_chart').show();
					$('#ir_y').show();
					
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/equip/getEquipmentData.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
							    var dateArr = $.gameUtil.getDay('day',ps.beginTime,ps.endTime); 
								
								$.newEquipRetention.brokenCharts($('#ir_funnel')[0],$('#ir_x')[0],data.ltvChart,dateArr);
								$.newEquipRetention.brokenTableAndLine($('#ir_y')[0],data.data,dateArr);
							 }
						  });
					
					
				}else{
					$('#ir_funnel_chart').hide();
					$('#ir_x_chart').hide();
					$('#ir_y').hide();
					$.newEquipRetention.pageRequest(ps);
				}
			},
			pageRequest:function(ps){
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				
				var columnsArr = new Array();//列定义数组
				columnsArr.push({"data": "installDate","title":"日期","defaultContent":""});
				columnsArr.push({"data": "newEquip","title":"新增设备","defaultContent":""});
				if(ps.indicators == 'source'){
					columnsArr.push({"data": "sourceOrModel","title":"渠道","defaultContent":""});
				}else if(ps.indicators == 'model'){
					columnsArr.push({"data": "sourceOrModel","title":"机型","defaultContent":""});
				}
				for(var i=1;i<=7;i++){
					columnsArr.push({"data": null,"title":"D"+i,"defaultContent":""});
				}
				
				var table = $(".template_cache .view_table_1").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var columnsDefArr = new Array();//列值定义数组
				for(var i=1;i<=7;i++){
					columnsDefArr.push({
	                    targets: i+2,
	                    render: function (a, b, v, d) {
	                    	var temArr = [v.d1,v.d2,v.d3,v.d4,v.d5,v.d6,v.d7];
	                    	var val = temArr[d.col-3];
	                    	if(val == null){
	                    		return "-%";
	                    	}else{
	                    		return Math.round(val*10000)/100+'%';
	                    	}
	                    }
	                });
				}
				
				var jq_table = $('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
				          data: function(d){
				        	  d.indicators=ps.indicators;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.source=ps.source;
				        	  d.model=ps.model;
				        	  d.view=ps.view;
				        	  d.snid=ps.snid;
				        	  d.gameId=ps.gameId;
				          },
						  type: "POST",
						  url:"/panel_bi/equip/getEquipmentData.ac?isPageRequest=1"
		              },
			        "pageLength": 40,
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
				    "ordering": false, //排序功能
				    "order": [[ 0, "desc" ]],
				    rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                        var dataArr = [data.d1,data.d2,data.d3,data.d4,data.d5,data.d6,data.d7];
                        $.each(row.children,function(k,td){
                    		if(k<3){
                    			return true;
                    		}
                    		var val = dataArr[k-3]*100;
                			if(val >= 100){
                				$(td).css('background-color',colorArr[colorArr.length-1]);
    						}else if(val > 0 && val < 100){
    							var index = Math.floor(val / 10);
    							$(td).css('background-color',colorArr[index]);
    						}
                        });
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

						var dataArr = [ '-', '-', '-', '-', '-', '-', '-'];
						var max = 0;
						$.each(irFunnelList, function(i, v) {
							var value = v.avgRetentionRate * 100;
							value = Math.round(value * 100) / 100;
							max = Math.max(max,value);
							var temp = {
								name : 'D' + v.entity.retentionDay,
								value : value
							};
							
							dataArr[v.entity.retentionDay -1] = temp;
						});

						option_funnel.series[0].data = dataArr;
						option_funnel.series[0].max = max;
						
						option_x.xAxis = [ {
							type : 'category',
							data : [ 'D1', 'D2', 'D3', 'D4', 'D5', 'D6', 'D7' ]
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
				var data = ["-","-","-","-","-","-","-"];
				$.each(dateArr[1],function(i,d){
					var isHaveData = false;
					$.each(reports,function(j,v){
						if(v.installDate == d){
							if(v.newEquip == null || v.newEquip == undefined){
								var date_install_temp = [v.installDate,"-"];
								ir_arr.push(data);
								date_install_arr.push(date_install_temp);
							}else{
								var data = [Math.round(v.d1 * 10000) / 100,
											Math.round(v.d2 * 10000) / 100,
											Math.round(v.d3 * 10000) / 100,
											Math.round(v.d4 * 10000) / 100,
											Math.round(v.d5 * 10000) / 100,
											Math.round(v.d6 * 10000) / 100,
											Math.round(v.d7 * 10000) / 100,
								            ];
								
								var date_install_temp = [v.installDate,v.newEquip];
								
								ir_arr.push(data);
								date_install_arr.push(date_install_temp);
							}
							isHaveData = true;
						}
					});
					if(!isHaveData){
						var date_install_temp = [d,"-"];
						ir_arr.push(data);
						date_install_arr.push(date_install_temp);
					}
				});
				
	            //画趋势表
	            $.newEquipRetention.brokenLine(ir_y,ir_arr,dateArr[0]);
	            
				//画数据表
	            $.newEquipRetention.brokenTable(date_install_arr,ir_arr);
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
						
						var option_y_legend = ['D1','D2','D3','D4','D5','D6','D7'];
						var option_indi = [0,1,2,3,4,5,6];
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
								"<br/>{a6} : {c6} %";
						option_y.tooltip = {trigger: 'axis',
								formatter: formatterStr};
						myChart.setOption(option_y,true);
			        });
				}
			},
			brokenTable:function(date_install_arr,ir_arr){
				
				 $("#dt_ajax_wrapper").remove();
					
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id","dt_ajax");
				
				$.each(ir_arr,function(i,v){
					if(isNaN(date_install_arr[i][1])){
						return true;
					}
					var trTemp = $("tbody tr",table).first().clone(true);
					
					$("td.ds",trTemp).text(date_install_arr[i][0]);
					$("td.newEquip",trTemp).text(date_install_arr[i][1]);
					
					for(var j=0;j<7;j++){
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
				
				
				$('#dt_ajax').DataTable({
					"sPaginationType" : "full_numbers",
		            "dom" : '<"clear"><"top">rt<"bottom"ip><"clear">' ,
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
				    "pageLength": 20,
				    "language":{
				    	"url": "/dataTables/chinese.json"
				    }
				});
				
				$('#dt_ajax').removeClass();
				$('#dt_ajax').addClass('table table-striped');
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
				
				$('#dt_ajax tbody tr').click(function (){
			        $('td',this).toggleClass('highlight');
			    });
			},
			checkParam:function(){
				var ps={
					     indicators:$('#indicators').val(),
						 beginTime:$("input[name='beginTime']").val(),
						 endTime:$("input[name='endTime']").val(),
						 model:$('#s_c').val(),
						 source:$('#s_c').val(),
						 view:$('#view').val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val()
					};
			      
			      if(ps.indicators == null || ps.indicators == ''){
			    	  ps.indicators = 'all'; 
			       }
			      
			      var dateArr = $.gameUtil.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='beginTime']").val(dateArr[0]);
			      $("input[name='endTime']").val(dateArr[1]);
			      
			      return ps;
			},
			change:function(){
				var ps = $.newEquipRetention.checkParam();
				var value = $("#indicators").val();
				if(value == 'all'){
					$("#s_c_span").text('');
					$.newEquipRetention.queryData();
				} else if(value == 'source'){
					$("#s_c_span").text('');
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/equip/getGameSource.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" name="source" style="max-width:240px"><option value="-1">请选择...</option><option value="-99">所有渠道</option>';
						    $.each(data.gameSources,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	$.newEquipRetention.queryData();
							});
						  }
					});
				} else if(value == 'model'){
					$("#s_c_span").text('');
					var span = $("#s_c_span");
				    var str = '<select id="s_c" name="model" style="max-width:240px"><option value="-1">请选择...</option><option value="-99">所有机型</option></select>';
				    span.append(str);
				    
				    $("#s_c").change(function(){
				    	$.newEquipRetention.queryData();
					});
				}
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
	};	
});
	                    