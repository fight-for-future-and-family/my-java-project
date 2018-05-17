$(function(){
	
	option_bar = {
		    legend: {data:['D1','D3','D7','D30']},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [{type : 'value',boundaryGap : [0, 0.01]}],
		    yAxis : [{
		    	type : 'category',
		        data : ['qudao1','qudao2','qudao3','qudao4','qudao5','qita']
		      }],
		    series : [
		        {
		            name:'D1',
		            type:'bar',
		            data:[18203, 23489, 29034, 104970, 131744, 630230]
		        },
		        {
		            name:'D3',
		            type:'bar',
		            data:[19325, 23438, 31000, 121594, 134141, 681807]
		        },
		        {
		            name:'D7',
		            type:'bar',
		            data:[19325, 23438, 31000, 121594, 134141, 681807]
		        },
		        {
		            name:'D30',
		            type:'bar',
		            data:[19325, 23438, 31000, 121594, 134141, 681807]
		        }
		    ]
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
		            name:'付费(累计)',type:'pie',selectedMode: 'single',
		            radius : [0, 55],
		            itemStyle : { normal : {label : {position : 'outer'},
		            	labelLine : {show : true}}},
		            data:[ ]
		        },
		        {name:'注册(累计)',type:'pie',radius : [75, 105], data:[],
		         itemStyle : { normal : {label : {position : 'outer'},
		        	 labelLine : {show : true}}}
		        },
		        {name:'活跃(平均)',type:'pie',radius : [125, 155],data:[],
		         itemStyle : { normal : {label : {position : 'outer'},
		         labelLine : {show : true}}}
		        }
		    ]
		};
	
	var game;
	var curTheme_copy = null,ec_copy = null,myChart_copy = null,option_copy = null,tab_dateArr = null;
	var tab_pie_data_pay = null,tab_pie_data_install = null,tab_pie_data_dau = null;
	var tab_pay_count = 1,tab_install_count = 1,tab_dau_count = 1;
	var tab_D1 = null,tab_D3 = null,tab_D7 = null,tab_D30 = null,tab_source = null;
	var isPageRequest = false;
	$(document).ready(function() {
	    $.gameSources.init();
	    $.timeZone.showTimeZone();
	});
	
	var initSub = true;
	$.gameSources={
			init:function(){
				$.gameSources.initEvent();
				$.gameSources.submit();
			},
			initEvent:function(){
				$("#query").click(function(){
					initSub = false;
					isPageRequest = false;
					$.gameSources.submit();
			    });
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gameSources.redict($(n).attr("id"));
					});
				});
				$("#indicators").change(function(){
					$.gameUtil.change($.gameSources);
				});
				
				$("#sc_pie_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#sc_pie_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#sc_pie").fadeIn();
							$("#sc_pay_data").fadeOut();
							$("#sc_install_data").fadeOut();
							$("#sc_dau_data").fadeOut();
							
							$("#sc_pie_chart").css('width','49%');
							$("#ltv_bar_chart").css('width','50%');
							//$("#ltv_bar").css('width','100%');
							
							myChart_copy = ec_copy.init($("#ltv_bar")[0], curTheme_copy);
							myChart_copy.setOption(option_copy, true);
							$.gameSources.brokenBarTable();
							
							$("#sc_pie_chart .dataShap ul").css('right','80px');
							$("#sc_pie_chart .dataShap ul").css('top','19px');
						}else{
							$("#sc_pie").fadeOut();
							$("#sc_pie_chart .dataShap ul").css('right','85px');
							$("#sc_pie_chart .dataShap ul").css('top','28px');
							
							$("#sc_pie_chart").css('width','100%');
							$("#ltv_bar_chart").css('width','100%');
							//$("#ltv_bar").css('width','100%');
							
							myChart_copy = ec_copy.init($("#ltv_bar")[0], curTheme_copy);
							myChart_copy.setOption(option_copy, true);
							$.gameSources.brokenBarTable();
							
							if($("#dt_scp_wrapper").length > 0){
								$("#sc_pay_data").fadeIn();
								$("#sc_install_data").fadeIn();
								$("#sc_dau_data").fadeIn();
							}else{
								$.gameSources.brokenPieTable();
								$("#sc_pay_data").fadeIn();
								$("#sc_install_data").fadeIn();
								$("#sc_dau_data").fadeIn();
							}
						}
					});
				});
				
				$("#ltv_bar_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#ltv_bar_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#ltv_bar").fadeIn();
							$("#ltv_bar_data").fadeOut();
							
							myChart_copy = ec_copy.init($("#ltv_bar")[0], curTheme_copy);
							myChart_copy.setOption(option_copy, true);
							
							$("#ltv_bar_chart .dataShap ul").css('right','80px');
							$("#ltv_bar_chart .dataShap ul").css('top','19px');
						}else{
							$("#ltv_bar").fadeOut();
							$("#ltv_bar_chart .dataShap ul").css('right','85px');
							$("#ltv_bar_chart .dataShap ul").css('top','28px');
							
							if($("#dt_ltv_wrapper").length > 0){
								$("#ltv_bar_data").fadeIn();
							}else{
								$.gameSources.brokenBarTable();
								$("#ltv_bar_data").fadeIn();
							}
						}
					});
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/gameView/toGameOverview.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/gameView/toGameOverview.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			checkDate:function(ps){
				var d = new Date(ps.endTime);
			    var b = new Date(ps.beginTime);
			    var temp = new Date(ps.endTime);
			    temp.setDate(d.getDate() - 31);
			    if(b.getTime() < temp.getTime()){
			    	b = temp;
			    	ps.beginTime = $.date.getDateFullStr(b);
				    $("input[name='beginTime']").val(ps.beginTime);
				    if(!initSub){
				    	alert('此页面查询区间限定31天');
				    }
			    }
			},
			submit:function(){
				var ps = $.gameUtil.checkParam();
				if(ps == null){
					return;
			    }
				
				$.gameSources.brokenAjaxTable(ps);
				if(!isPageRequest){
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/gameView/getGameOverview.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
							    game = data.game;
							    
						    	var sc_pie = document.getElementById('sc_pie');
						    	var ltv_bar = document.getElementById('ltv_bar');
						    	var indicators = $('#indicators').val();
						    	
						    	var dateArr = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 
						    	
						    	$.gameSources.brokenPie(sc_pie,data,dateArr[0].length);
						    	$.gameSources.brokenBar(ltv_bar,data.ltvs);
							    isPageRequest = true;
						  }
					});
				}
			},
			brokenAjaxTable:function(ps){
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				
				var rate = $("#gameRate").val();
				
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt_ajax");
				$('#ajax_data').append(table);
				
				var columns = [
		                      {"data": "day"},
		                      {"data": "source"},
		                      {"data": "dnu"},
		                      {"data": "roleChoice"},
		                      {"data": "dau"},
		                      {"data": null},
		                      {"data": "pu"}
		                  ];
				if($("#terminalType").val() == 1 && $("#systemType").val() == 0){
					$("thead tr",table).first().remove();
					
					columns.push({
						"data":"idfa"
					});
					columns.push({
						"data":"distinctIdfa"
					});
					columns.push({
						"data":"distinctIp"
					});
				}else{
					$("thead tr",table).eq(1).remove();
				}
				
				$('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
					          data: function(d){
					        	  d.id=ps.id;
					        	  d.gameId=ps.gameId;
					        	  d.indicators=ps.indicators;
					        	  d.beginTime=ps.beginTime;
					        	  d.endTime=ps.endTime;
					        	  d.source=ps.source;
					        	  d.view=ps.view;
					        	  d.isPageRequest='yes';
					          },
							  type: "POST",
							  url:"/panel_bi/gameView/getGameOverview.ac"
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
			        columns: columns,
			        columnDefs: [
			                     {
			                    	 targets: 1,
			                    	 orderable: false
			                     },
			                       {
				                        targets: 5,
				                        render: function (a, b, v, d) {
				                             return Math.round(v.paymentAmount/rate*100)/100;
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
			},
			brokenBar:function(cc,ltvs){

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
						
						var D1 = new Array();
						var D3 = new Array();
						var D7 = new Array();
						var D30 = new Array();
						var source = new Array();
						$.each(ltvs,function(i,v){
							var avg = Math.round((v.avgLtv / game.rate) * 100)/100;
							if(v.entity.retentionDay == 1){
								source.push(v.entity.source);
								D1.push(avg);
							}else if(v.entity.retentionDay == 3){
								D3.push(avg);
							}else if(v.entity.retentionDay == 7){
								D7.push(avg);
							}else if(v.entity.retentionDay == 30){
								D30.push(avg);
							}
						});
						if(ltvs.length > 0){
							option_bar.yAxis[0].data = source;
							option_bar.series[0].data = D1;
							option_bar.series[1].data = D3;
							option_bar.series[2].data = D7;
							option_bar.series[3].data = D30;
						}else{
							option_bar.yAxis[0].data = ['无数据'];
							option_bar.series[0].data = [0];
							option_bar.series[1].data = [0];
							option_bar.series[2].data = [0];
							option_bar.series[3].data = [0];
						}
						
						option_bar.title = {text: '注收比',
								            subtext:'单位('+game.currency+')',
								            x:70};
						option_bar.tooltip = {
								trigger: 'axis',
								formatter: "{a} <br/>{b} : {c} "+game.currency+""};
						myChart.setOption(option_bar,true);
						
						curTheme_copy = tarTheme;
						ec_copy = ec;
						myChart_copy = myChart;
						option_copy = option_bar;
						tab_D1 = D1,tab_D3 = D3,tab_D7 = D7,tab_D30 = D30,tab_source = source;
					 });
				}
			},
			brokenPie:function(cc,data,len){
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
							var pie_data_pay = new Array();
							var dataCount_install = 0;
							var dataCount_dau = 0;
							var dataCount_pay = 0;
							
							$.each(data.sireports, function(i,d){
								if(i<5){
									var td={name:d.entity.source,value:d.dnuSum};
									pie_data_install.push(td);
									dataCount_install += d.dnuSum;
								}else{
									return false;
								}
							}); 
							$.each(data.sdreports, function(i,d){
								if(i<5){
									var td={name:d.entity.source,value:Math.round(d.dauSum / len)};
									pie_data_dau.push(td);
									dataCount_dau += d.dauSum;
								}else{
									return false;
								}
							}); 
							$.each(data.spReports, function(i,d){
								var pay = Math.round((d.paymentSum / game.rate) * 100)/100;
								if(i<5){
									var td={name:d.entity.source,value:pay};
									pie_data_pay.push(td);
									dataCount_pay += pay;
								}else{
									return false;
								}
							}); 
							
							
							var other={name:'其他',value:data.sireportsCount - dataCount_install};
							pie_data_install.push(other);
							var other={name:'其他',value:Math.round((data.sdreportsCount - dataCount_dau)/len)};
							pie_data_dau.push(other);
							var other={name:'其他',value:(Math.round((data.spreportsCount / game.rate) * 100)/100 - dataCount_pay)};
							pie_data_pay.push(other);
							
							option_pie.series[0].data = pie_data_pay;
							option_pie.series[1].data = pie_data_install;
							option_pie.series[2].data = pie_data_dau;
			    		}
						option_pie.title = {
								text:'新注册活跃付费概览',
								subtext: '付费（单位：'+game.currency+'）',
								x:10,y:5,
						       };
						myChart.setOption(option_pie,true);
						
						tab_pie_data_pay = pie_data_pay;
						tab_pie_data_install = pie_data_install;
						tab_pie_data_dau = pie_data_dau;
						
						tab_pay_count = Math.round((data.spreportsCount / game.rate) * 100)/100;
						tab_install_count = data.sireportsCount;
						tab_dau_count = Math.round(data.sdreportsCount / len);
					 });
				}
			},
			brokenPieTable:function(){
				$('#dt_scp_wrapper').remove();
				$('#dt_sci_wrapper').remove();
				$('#dt_scd_wrapper').remove();
				
				var rate = game.rate;
				
				////////////////////////////////////////////////////////////////
				var table = $(".template_cache .sc_pay_table").clone(true);
				table.attr("id", "dt_scp");
				$('#sc_pay_data').append(table);

				$.each(tab_pie_data_pay,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.source", trTemp).text(v.name);
					$("td.payMent", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/tab_pay_count)*10000)/100+'%');

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("sc_pay_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart_no_page($('#dt_scp'));
				
				$('#dt_scp tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
				
				/////////////////////////////////////////////////////////////////
				var table = $(".template_cache .sc_install_table").clone(true);
				table.attr("id", "dt_sci");
				$('#sc_install_data').append(table);

				$.each(tab_pie_data_install,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.source", trTemp).text(v.name);
					$("td.install", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/tab_install_count)*10000)/100+'%');

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("sc_install_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart_no_page($('#dt_sci'));
				
				$('#dt_sci tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
				
				//////////////////////////////////////////////////////////////////
				var table = $(".template_cache .sc_dau_table").clone(true);
				table.attr("id", "dt_scd");
				$('#sc_dau_data').append(table);

				$.each(tab_pie_data_dau,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.source", trTemp).text(v.name);
					$("td.dau", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/tab_dau_count)*10000)/100+'%');

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("sc_dau_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart_no_page($('#dt_scd'));
				$('#dt_scd tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			brokenBarTable:function(){
				$('#dt_ltv_wrapper').remove();
				
				var table = $(".template_cache .ltv_table").clone(true);
				table.attr("id", "dt_ltv");
				$('#ltv_bar_data').append(table);

				$.each(tab_source,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.source", trTemp).text(v);
					$("td.D1", trTemp).text(tab_D1[i]);
					$("td.D3", trTemp).text(tab_D3[i]);
					$("td.D7", trTemp).text(tab_D7[i]);
					$("td.D30", trTemp).text(tab_D30[i]);

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("ltv_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart_no_page($('#dt_ltv'));
				$('#dt_ltv tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			}
	};
});