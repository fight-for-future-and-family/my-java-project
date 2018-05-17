$(function(){
	
	option = {
		    tooltip : {trigger: 'axis'},
		    grid: { borderWidth: 0,x:0,y: 0,y2: 30,x2:0},
		    xAxis : [{type : 'category',show : false}],
		    yAxis : [ {type : 'value',show:false}],
		    series : [{name:'成交',type:'line',smooth:true,symbol:'none',itemStyle: {normal: {areaStyle: {type: 'default'}}}}]
		};	  
	
	option_pie = {
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
		            name:'激活(累计)',type:'pie',selectedMode: 'single',
		            radius : [0, 50],
		            itemStyle : { normal : {label : {position : 'outer'},
		            	labelLine : {show : true}}},
		            data:[ ]
		        },
		        {name:'激活且注册(累计)',type:'pie',radius : [65, 95], data:[],
		         itemStyle : { normal : {label : {position : 'outer'},
		        	 labelLine : {show : true}}}
		        },
		        {name:'活跃(累计)',type:'pie',radius : [110, 145],data:[],
		         itemStyle : { normal : {label : {position : 'outer'},
		         labelLine : {show : true}}}
		        }
		    ]
		};
	
	optionCumuli = {
			title:{text:'活跃',subtext:'单位（个）',x:70,y:15},
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
		    legend: { data:['老玩家', '新玩家'],x:'27%',y:10},
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
		    xAxis : [{type : 'category',data:[]}],
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
		        }
		    ]
		};
	
	option_line = {
		    toolbox: {
		    	show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }
		};
	
	$(document).ready(function() {
	    $.equipDau.init();
	    $.timeZone.showTimeZone();
	});
	
	var dateArr = null;
		
	var bar_myChart_copy = null,bar_ec_copy = null,bar_curTheme_copy=null,bar_option_copy = null;

	var tab_pie_data_newEquip = null,tab_pie_data_install = null,tab_pie_data_dau = null;
	var tab_newEquip_count = 1,tab_install_count = 1,tab_dau_count = 1;
	
	var tab_bar_series = null;
	$.equipDau={
			init:function(){
				$.equipDau.initEvent();
				$.equipDau.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
				    $('#caption button')[0].disabled = false;
					$.equipDau.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.equipDau.redict($(n).attr("id"));
					});
				});
				
				$("#indicators").change(function(){
					$.equipDau.change();
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
//				window.location.href='/panel_bi/equip/toEuipment.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/equip/toEuipment.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			brokenDataDiv:function(name,dateArr,dataArr){
				
				var div = $(".template_cache .tongjitu").clone(true);
				$('#tu').append(div);
				
				var len = dataArr.length;
				var weekAvg = 0,monthAvg=0,count=0;
				var weeklen = 7,monthlen = 30,alllen=len;
				$.each(dataArr,function(i,v){
					if(isNaN(v)){
						if(i >= (len - 7)){
							weeklen -= 1;
						}
						if(i >= (len - 30)){
							monthlen -= 1;
						}
						alllen -= 1;
					}else{
						if(i >= (len - 7)){
							weekAvg += v;
						}
						if(i >= (len - 30)){
							monthAvg += v;
						}
						count+=v;
					}
				});
				
				
				if(name=='激活设备'){
					div.addClass('zhibiao_left');
				}else if(name=='激活注册数' ){
					div.addClass('zhibiao');
				}else if(name=='激活率'){
					div.addClass('zhibiao');
				}else if(name=='活跃人次'){
					div.addClass('zhibiao_right');
				}
				
				$(".name",div).text(name);
				if(name != '激活率'){
					$(".count",div).text(count);
					$(".yestoday",div).text('昨天： '+dataArr[dataArr.length-2]);
					$(".weekAvg",div).text('近一周平均： '+(weeklen > 0 ? Math.round(weekAvg/weeklen) : 0));
					$(".monthAvg",div).text('近一月平均： '+(monthlen > 0 ? Math.round(monthAvg/monthlen) : 0));
				}else{
					$(".count",div).text(alllen > 0 ? Math.round(count/alllen*100)/100+'%':'0%');
					$(".yestoday",div).text('昨天： '+dataArr[dataArr.length-2]+'%');
					$(".weekAvg",div).text('近一周平均： '+(weeklen > 0 ? Math.round(weekAvg/weeklen*100)/100 : 0)+'%');
					$(".monthAvg",div).text('近一月平均： '+(monthlen > 0 ? Math.round(monthAvg/monthlen*100)/100 : 0)+'%');
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
						'echarts/chart/line',
					],
					DrawEChart
				);
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var tuDiv = $(".tu",div)[0];
						var myChart = ec.init(tuDiv,tarTheme);
						option.series[0].name=name;
						option.series[0].data = dataArr;
						option.xAxis[0].data = dateArr;
						
						if(name=='激活率'){
							option.tooltip =  { trigger: 'axis',formatter: "{a} <br/>{b} : {c} %" };
						}else{
							option.tooltip = { trigger: 'axis',formatter: "{a} <br/>{b} : {c}" };
						}
						
						myChart.setOption(option,true);
			        });
				}
			},
			brokenPie:function(cc,type,installData,newEquipData,dauData,len){
				require.config({
					paths: {
						echarts: '/js/echarts/dist/',
						theme:'/js/echarts/theme/'
					}
				});
				require(
					[
						'echarts',
						'echarts/chart/pie',
						'echarts/chart/funnel'
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
							var pie_data_install = new Array();
							var pie_data_dau = new Array();
							var pie_data_newEquip = new Array();
							var dataCount_install = 0,dataCount_install_all=0;
							var dataCount_dau = 0,dataCount_dau_all=0;
							var dataCount_newEquip = 0,dataCount_newEquip_all=0;
							
							$.each(installData, function(i,d){
								var td={name:type=='source'?d.entity.source:d.entity.model,value:d.smSumData};
								pie_data_install.push(td);
								dataCount_install += d.smSumData
								dataCount_install_all = d.smTotalSumData;
							}); 
							$.each(newEquipData, function(i,d){
								var td={name:type=='source'?d.entity.source:d.entity.model,value:d.smSumData};
								pie_data_newEquip.push(td);
								dataCount_newEquip += d.smSumData;
								dataCount_newEquip_all = d.smTotalSumData;
							}); 
							$.each(dauData, function(i,d){
								var td={name:type=='source'?d.entity.source:d.entity.model,value:d.smSumData};
								pie_data_dau.push(td);
								dataCount_dau += d.smSumData;
								dataCount_dau_all = d.smTotalSumData;
							}); 
							
							
							var other={name:'其他',value:dataCount_install_all - dataCount_install};
							pie_data_install.push(other);
							var other={name:'其他',value:dataCount_dau_all-dataCount_dau};
							pie_data_dau.push(other);
							var other={name:'其他',value:dataCount_newEquip_all - dataCount_newEquip};
							pie_data_newEquip.push(other);
							
							option_pie.series[2].data = pie_data_newEquip;
							option_pie.series[1].data = pie_data_install;
							option_pie.series[0].data = pie_data_dau;
			    		}
						option_pie.title = {
								text:type=='source'?'分渠道激活概览':'分机型激活概览',
								x:10,y:5,
						       };
						myChart.setOption(option_pie,true);
						
						var tab_pie_copy_data = {
								tab_pie_data_newEquip:pie_data_newEquip,
								tab_pie_data_install:pie_data_install,
								tab_pie_data_dau:pie_data_dau,
								tab_install_count:dataCount_install_all,
								tab_dau_count:dataCount_dau_all,
								tab_newEquip_count:dataCount_newEquip_all
						}
						
						if(type=='source'){
							tab_pie_copy_source = tab_pie_copy_data;
						}else{
							tab_pie_copy_model = tab_pie_copy_data;
						}
					 });
				}
			},
			brokenPieTable:function(type){
				$('#dt_e'+type+'_wrapper').remove();
				$('#dt_i'+type+'_wrapper').remove();
				$('#dt_d'+type+'_wrapper').remove();
				
				var tab_data = type=='source' ? tab_pie_copy_source : tab_pie_copy_model;
				
				////////////////////////////////////////////////////////////////
				var table = $(".template_cache .pie_table_view").clone(true);
				table.attr("id", "dt_e"+type);
				$('#'+type+'_newEquip_data').append(table);

				$.each(tab_data.tab_pie_data_newEquip,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.sm", trTemp).text(v.name);
					$("td.value", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/tab_data.tab_newEquip_count)*10000)/100+'%');

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("pie_table_view").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart_no_page($('#dt_e'+type));
				
				$('tbody tr',table).click(function (){
			        $(this).toggleClass('highlight');
			    });
				
				/////////////////////////////////////////////////////////////////
				var table = $(".template_cache .pie_table_view").clone(true);
				table.attr("id", "dt_i"+type);
				$('#'+type+'_install_data').append(table);

				$.each(tab_data.tab_pie_data_install,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.sm", trTemp).text(v.name);
					$("td.value", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/tab_data.tab_install_count)*10000)/100+'%');

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("pie_table_view").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart_no_page($('#dt_i'+type));
				
				$('tbody tr',table).click(function (){
			        $(this).toggleClass('highlight');
			    });
				
				//////////////////////////////////////////////////////////////////
				var table = $(".template_cache .pie_table_view").clone(true);
				table.attr("id", "dt_d"+type);
				$('#'+type+'_dau_data').append(table);

				$.each(tab_data.tab_pie_data_dau,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.sm", trTemp).text(v.name);
					$("td.value", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/tab_data.tab_dau_count)*10000)/100+'%');

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("pie_table_view").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart_no_page($('#dt_d'+type));
				
				$('tbody tr',table).click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			brokenTables:function(data){
				$('#dt_ajax_wrapper').remove();
				$("#caption").hide();
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				$.each(data,function(j,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.ds", trTemp).text(v.ds);
					$("td.dau", trTemp).text(v.dau);
					$("td.newEquip", trTemp).text(v.newEquip);
					$("td.install", trTemp).text(v.install);
					$("td.olddau", trTemp).text(v.dau-v.install);
					$("td.uninstall", trTemp).text(v.uninstall);
					
					if(v.install == null || v.newEquip == null|| v.newEquip==0){
						$("td.installRate", trTemp).text('-%');
					}else if(v.install == 0 ){
						$("td.installRate", trTemp).text('0%');
					}else{
						$("td.installRate", trTemp).text(Math.round(v.install/v.newEquip*10000)/100+'%');
					}

					$('tbody', table).append(trTemp);
				  });
				
				table.removeClass("view_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables($('#dt_ajax'));
				$('#dt tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			queryData:function(){
				var ps = $.equipDau.checkParam();
				if(ps == null){
					return;
			    }
				
				$('#tu').html('');
				$('#dt_ajax_wrapper').remove()
				
				var indicators = ps.indicators;
				var isAll = indicators == 'all';
				dateArr = $.gameUtil.getDay('day',ps.beginTime,ps.endTime);
				if(isAll){
					$("#sourceTu").html('');
					$("#pirtu").html('');
					$("#pirtu").append($(".template_cache .pirtu_view").clone(true));
					$.equipDau.dataShapViewEvent();
					
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/equip/getEquipmentData.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
							  var equipData = data.equipData;
							  var installArr = new Array();
							  var dauArr = new Array();
							  var newEquipArr = new Array();
							  var installRate = new Array();
							  for(var i=0;i<dateArr[1].length;i++){
								  installArr.push('-');
								  dauArr.push('-');
								  newEquipArr.push('-');
								  installRate.push('-');
						    	}
							  
							  // 避免中间数据有遗漏
							  $.each(dateArr[1], function(i,date){
								  $.each(equipData,function(j,v){
									  if(v.ds==date){
										  installArr[i]=v.install;
										  dauArr[i]=v.dau;
										  newEquipArr[i]=v.newEquip;
										  if(v.install == null || v.newEquip == null|| v.newEquip==0){
											  installRate[i]='-';
											}else if(v.install == 0 ){
												installRate[i]=0;
											}else{
												installRate[i] = Math.round(v.install/v.newEquip*10000)/100;
											}
									  }
								  });
							  });
							  
							  // 顺序不能改变
							  $.equipDau.brokenDataDiv('激活设备',dateArr[0],newEquipArr);
							  $.equipDau.brokenDataDiv('激活注册数',dateArr[0],installArr);
							  $.equipDau.brokenDataDiv('激活率',dateArr[0],installRate);
							  $.equipDau.brokenDataDiv('活跃人次',dateArr[0],dauArr);
							  
							  //饼图
							  $.equipDau.brokenPie($("#model")[0],'model',data.modelInstallData,data.modelNewEquipData,data.modelDauData,dateArr[0].length);
							  $.equipDau.brokenPie($("#source")[0],'source',data.sourceInstallData,data.sourceNewEquipData,data.sourceDauData,dateArr[0].length);
							  
							  $.equipDau.brokenTables(equipData);
						  }
					});
				}else{
					$("#pirtu").html('');
					$("#sourceTu").html('');
					
					if(ps.model == '-99' || ps.source == '-99'){
						$("#sourceTu").append($(".template_cache .sourceTu_view").clone(true));
						$.equipDau.dataShapSourceEvent();
						$.ajax({
							  type: "POST",
							  url: "/panel_bi/equip/getEquipmentData.ac",
							  data: $.param(ps),
							  dataType: "json",
							  success: function(data){
								  if(indicators=='source'){
									  $.equipDau.brokenPie($("#source")[0],'source',data.sourceInstallData,data.sourceNewEquipData,data.sourceDauData,dateArr[0].length);
									  $.equipDau.brokenDauBar($("#dau_zhu")[0],dateArr[0], data.sourceDauBarData,indicators);
									  $.equipDau.brokenLine($("#newEquip_line")[0],'激活',indicators, data.sourceNewEquipLineData,dateArr[0]);
									  $.equipDau.brokenLine($("#newEquipRate_line")[0],'激活率',indicators,data.sourceNewEquipRateLineData,dateArr[0]);
								  }else{
									  $.equipDau.brokenPie($("#source")[0],'model',data.modelInstallData,data.modelNewEquipData,data.modelDauData,dateArr[0].length);
									  $.equipDau.brokenDauBar($("#dau_zhu")[0],dateArr[0], data.modelDauBarData,indicators);
									  $.equipDau.brokenLine($("#newEquip_line")[0],'激活',indicators, data.modelNewEquipLineData,dateArr[0]);
									  $.equipDau.brokenLine($("#newEquipRate_line")[0],'激活率',indicators,data.modelNewEquipRateLineData,dateArr[0]);
								  }
								  
								  $.equipDau.ajax_request(ps);
							  }
						});
					}else{
						$.equipDau.ajax_request(ps);
					}
				}
			},
			ajax_request:function(ps){
				var selected = [];
				var columnsArr = new Array();//列定义数组
				columnsArr.push({"data": "ds","title":"日期","defaultContent":""});
				if(ps.indicators == 'source'){
					columnsArr.push({"data": "source","title":"渠道","defaultContent":""});
				}else if(ps.indicators == 'model'){
					columnsArr.push({"data": "model","title":"机型","defaultContent":""});
				}
				columnsArr.push({"data": "dau","title":"活跃设备","defaultContent":""});
				columnsArr.push({"data": "newEquip","title":"激活设备","defaultContent":""});
				columnsArr.push({"data": "install","title":"激活且注册设备","defaultContent":""});
				columnsArr.push({"data": null,"title":"活跃老设备","defaultContent":""});
				columnsArr.push({"data": null,"title":"激活率","defaultContent":""});
				columnsArr.push({"data": "uninstall","title":"卸载设备","defaultContent":""});
				
				var table = $(".template_cache .view_table_1").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var columnsDefArr = new Array();//列值定义数组
				columnsDefArr.push({ orderable: false, targets: 1 });
				columnsDefArr.push({
                    targets: 5,
                    render: function (a, b, v, d) {
                    	if(v.dau == null || v.newEquip == null){
                    		return '-';
                    	}else{
                    		return v.dau-v.newEquip;
                    	}
                    }
                });
				columnsDefArr.push({
					targets: 6,
							render: function (a, b, v, d) {
								if(v.install == null || v.newEquip == null|| v.newEquip==0){
									return '-%';
								}else if(v.install == 0 ){
									return '0%';
								}else{
									return Math.round(v.install/v.newEquip*10000)/100+'%';
								}
							}
				});
	        	
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
				    "order": [[ 0, "desc" ]],
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
			       }else if(ps.indicators == 'model'){
			    	   if(ps.model == null || ps.model == '-1'){
			    		   return null;
			    	   }
			       }else if(ps.indicators == 'source'){
			    	   if(ps.source == null || ps.source == '-1'){
			    		   return null;
			    	   }
			       }
			      
			      var dateArr = $.gameUtil.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='beginTime']").val(dateArr[0]);
			      $("input[name='endTime']").val(dateArr[1]);
			      
			      return ps;
			},
			change:function(){

				var value = $("#indicators").val();
				if(value == 'all'){
					$("#s_c_span").text('');
					$.equipDau.queryData();
				} else if(value == 'source'){
					$("#s_c_span").text('');
					var ps = $.equipDau.checkParam();
					if(ps == null){
						ps={
								indicators:$('#indicators').val(),
								 beginTime:$("input[name='beginTime']").val(),
								 endTime:$("input[name='endTime']").val(),
								 model:$('#s_c').val(),
								 source:$('#s_c').val(),
								 view:$('#view').val(),
								 snid:$("input[name='snid']").val(),
								 gameId:$("input[name='gameId']").val()
							};
				    }
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
						    	$.equipDau.queryData();
							});
						  }
					});
				} else if(value == 'model'){
					$("#s_c_span").text('');
					var span = $("#s_c_span");
				    var str = '<select id="s_c" name="model" style="max-width:240px"><option value="-1">请选择...</option><option value="-99">所有机型</option></select>';
				    span.append(str);
				    
				    $("#s_c").change(function(){
				    	$.equipDau.queryData();
					});
				}
			},
			brokenDauBar:function(cc,dateArr,dataArr,type){

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
						
						var s = new Array();
						var nameArr = new Array();
						$.each(dataArr,function(i,v){
							var arr = v.dayDatas;
							for(var i=0;i<arr.length;i++){
								if(arr[i] == 0){
									arr[i] = {value:'0',
											     itemStyle: {
						                        	 normal: 
						                        	 {label :{
						                        		 show: true, 
						                        		 position: 'insideRight',
						                        		 textStyle:{color:'#fff'}}}}};
								}
							}
							
							var tem = {
						            name:type=='source'?v.entity.source:v.entity.model,
								            type:'bar',
								            stack: '总量',
								            itemStyle : { normal: {
								            	label : {
								            		show: true, 
								            		position: 'insideRight',
								            		textStyle:{color:'#000'}
								            			}}},
								            data:arr
								        };
							s.push(tem);
							nameArr.push(tem.name);
						});
						
						optionCumuli.series = s;
						optionCumuli.legend.data = nameArr;
						myChart.setOption(optionCumuli,true);
						
						tab_bar_series = s;
						
						bar_myChart_copy = myChart;
						bar_ec_copy = ec;
						bar_curTheme_copy = tarTheme;
						bar_option_copy = optionCumuli;
			        });
				}
			},
			brokenLine:function(cc,text,type,dataArr,dateArr){
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
						
						option_line.xAxis = [{type:'category',data:dateArr}];
						var formatterStr = '';
						if(text == '激活'){
							option_line.yAxis = [{type:'value',axisLabel : {formatter: '{value} '},boundaryGap: [0, 0.01]}];
							option_line.title = {text:text,subtext:'单位（个）'};
							
							formatterStr = "{b} <br/>{a} : {c} <br/>{a1} : {c1} <br/>{a2} : {c2} ";
							formatterStr += "<br/>{a3} : {c3} <br/>{a4} : {c4} <br/>{a5} : {c5} ";
						}else{
							option_line.yAxis = [{type:'value',axisLabel : {formatter: '{value} %'},boundaryGap: [0, 0.01]}];
							option_line.title = {text:text,subtext:'单位（%）'};
							
							formatterStr = "{b} <br/>{a} : {c} %<br/>{a1} : {c1} %<br/>{a2} : {c2} %";
							formatterStr += "<br/>{a3} : {c3} %<br/>{a4} : {c4} %";
						}
						option_line.tooltip = {trigger: 'axis',formatter: formatterStr};
						
						var s = new Array();
						var nameArr = new Array();
						$.each(dataArr,function(i,v){
							var tempArr = new Array();
							if(text == '激活率'){
								$.each(v.doubleDayDatas,function(i,v){
									tempArr.push(Math.round(v*10000)/100);
								});
							}
							var op = {
									name:type=='source'?v.entity.source:v.entity.model,
									type:'line',
									data:text == '激活'?v.dayDatas:tempArr
							};
							s.push(op);
							nameArr.push(op.name);
						});
						
						option_line.series = s;
						option_line.legend={data:nameArr,x:'27%',y:10};
						myChart.setOption(option_line,true);
						
						if(text == '激活'){
							tab_line_series = s;
						}else{
							tab_rate_line_series = s;
						}
						
			        });
				}
			},
			dataShapViewEvent:function(){
			$("#pirtu .dataShap li").each(function(i,n){
				$(n).click(function(){
					$("#pirtu .dataShap li").removeClass("onChoose");
					$(n).addClass("onChoose");
					
					if($("em",n).attr("class") == 'columnar'){
						$("#source").fadeIn();
						$("#source_newEquip_data").fadeOut();
						$("#source_install_data").fadeOut();
						$("#source_dau_data").fadeOut();
						
						$("#model").fadeIn();
						$("#model_newEquip_data").fadeOut();
						$("#model_install_data").fadeOut();
						$("#model_dau_data").fadeOut();
						
						$("#model_chart").css('width','49%');
						$("#source_chart").css('width','50%');
						
						$("#pirtu .dataShap ul").css('right','80px');
						$("#pirtu .dataShap ul").css('margin-top','19px');
					}else{
						$("#source").fadeOut();
						$("#model").fadeOut();
						$("#pirtu .dataShap ul").css('right','110px');
						$("#pirtu .dataShap ul").css('margin-top','28px');
						
						$("#source_chart").css('width','100%');
						$("#model_chart").css('width','100%');
						
						if($("#dt_emodel_wrapper").length > 0){
							$("#source_newEquip_data").fadeIn();
							$("#source_install_data").fadeIn();
							$("#source_dau_data").fadeIn();
							
							$("#model_newEquip_data").fadeIn();
							$("#model_install_data").fadeIn();
							$("#model_dau_data").fadeIn();
						}else{
							$.equipDau.brokenPieTable('model');
							$.equipDau.brokenPieTable('source');
							
							$("#model_newEquip_data").fadeIn();
							$("#model_install_data").fadeIn();
							$("#model_dau_data").fadeIn();
							
							$("#source_newEquip_data").fadeIn();
							$("#source_install_data").fadeIn();
							$("#source_dau_data").fadeIn();
						}
					}
				});
			});
		},
		dataShapSourceEvent:function(){
			$("#sourceTu #source_chart .dataShap li").each(function(i,n){
				$(n).click(function(){
					$("#sourceTu #source_chart .dataShap li").removeClass("onChoose");
					$(n).addClass("onChoose");
					
					if($("em",n).attr("class") == 'columnar'){
						$("#sourceTu #source").fadeIn();
						$("#sourceTu #source_newEquip_data").fadeOut();
						$("#sourceTu #source_install_data").fadeOut();
						$("#sourceTu #source_dau_data").fadeOut();
						
						$("#sourceTu #source_chart").css('width','49%');
						$("#sourceTu #dau_zhu_chart").css('width','50%');
						
						$("#sourceTu #source_chart .dataShap ul").css('right','10px');
						$("#sourceTu #source_chart .dataShap ul").css('top','19px');
					}else{
						$("#sourceTu #source").fadeOut();
						$("#sourceTu #source_chart .dataShap ul").css('right','80px');
						$("#sourceTu #source_chart .dataShap ul").css('top','26px');
						
						$("#sourceTu #source_chart").css('width','100%');
						$("#sourceTu #dau_zhu_chart").css('width','100%');
						
						if($("#dt_emodel_wrapper").length > 0 || $("#dt_esource_wrapper").length > 0){
							$("#sourceTu #source_newEquip_data").fadeIn();
							$("#sourceTu #source_install_data").fadeIn();
							$("#sourceTu #source_dau_data").fadeIn();
						}else{
							$.equipDau.brokenPieTable('source');
							
							$("#sourceTu #source_newEquip_data").fadeIn();
							$("#sourceTu #source_install_data").fadeIn();
							$("#sourceTu #source_dau_data").fadeIn();
						}
					}
					
					bar_myChart_copy = bar_ec_copy.init($("#sourceTu #dau_zhu")[0], bar_curTheme_copy);
					bar_myChart_copy.setOption(bar_option_copy, true);
					$.equipDau.brokenBarTable('source');
				});
			});
			
			$("#sourceTu #dau_zhu_chart .dataShap li").each(function(i,n){
				$(n).click(function(){
					$("#sourceTu #dau_zhu_chart .dataShap li").removeClass("onChoose");
					$(n).addClass("onChoose");
					
					if($("em",n).attr("class") == 'columnar'){
						$("#sourceTu #dau_zhu").fadeIn();
						$("#sourceTu #dau_zhu_data").fadeOut();
						
						$("#sourceTu #dau_zhu_chart .dataShap ul").css('right','10px');
						$("#sourceTu #dau_zhu_chart .dataShap ul").css('top','19px');
					}else{
						$("#sourceTu #dau_zhu").fadeOut();
						$("#sourceTu #dau_zhu_chart .dataShap ul").css('right','80px');
						$("#sourceTu #dau_zhu_chart .dataShap ul").css('top','26px');
						
						if($("#dt_db_wrapper").length > 0){
							$("#sourceTu #dau_zhu_data").fadeIn();
						}else{
							$.equipDau.brokenBarTable('source');
							
							$("#sourceTu #dau_zhu_data").fadeIn();
						}
					}
					
					bar_myChart_copy = bar_ec_copy.init($("#sourceTu #dau_zhu")[0], bar_curTheme_copy);
					bar_myChart_copy.setOption(bar_option_copy, true);
				});
			});
		},
		brokenBarTable:function(type){
			$('#dt_db_wrapper').remove();
			
			var table = $(".template_cache .bar_line_table_view").clone(true);
			table.attr("id", "dt_db");
			$('#dau_zhu_data').append(table);
			
			var tr = $("tr", table).first();
			var isNamed = false;
			for(var i=0;i<dateArr[1].length;i++){
				
				var trTemp = $("tbody tr", table).first().clone(true);
				$("td.ds", trTemp).text(dateArr[1][i]);
				
				$.each(tab_bar_series,function(j,v){
					if(!isNamed){
						$("td",tr).eq(j+1).text(v.name);
					}
					if(isNaN(v.data[i])){
						$("td.s"+(j+1), trTemp).text(v.data[i].value);
					}else{
						$("td.s"+(j+1), trTemp).text(v.data[i]);
					}
				});
				
				isNamed = true;
				$('tbody', table).append(trTemp);
			}

			table.removeClass("bar_line_table_view").addClass("table table-striped");
			$("tbody tr", table).first().remove();
			
			$.biDataTables.dataTables_chart($('#dt_db'));
			
			$('tbody tr',table).click(function (){
		        $(this).toggleClass('highlight');
		    });
		}
	};	
});
	                    