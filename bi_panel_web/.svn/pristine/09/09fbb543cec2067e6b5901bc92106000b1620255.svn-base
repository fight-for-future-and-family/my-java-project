$(function(){
	
	option = {
		    tooltip : {
		        trigger: 'axis',formatter: '{b}\n{c}个'
		    },
		    toolbox: {
		        show : true,x:'right',y:'top',itemSize:16,
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }
		};
	
	option_pie = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                    	x: '15%',
		                    	y:'28%',
		                        width: '60%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'数据来源',
		            type:'pie',
		            radius : '55%',
		            center: ['45%', '60%'],
		            clockWise:false,
		            data:[
		                {value:335, name:'渠道1'},
		                {value:310, name:'渠道2'},
		                {value:234, name:'渠道3'},
		                {value:135, name:'渠道4'},
		                {value:1548, name:'渠道5'},
		                {value:220, name:'其他'}
		            ]
		        }
		    ]
		};
	
	var dataArr = null;
	var dateArr = null;
	var tdObj = null;
	var pieCount = 0;
	var tab_pie_data = null;
	var pie_count = 0;
	$(document).ready(function() {
	    $.gameInstall.init();
	    $.timeZone.showTimeZone();
	});
		
	$.gameInstall={
			init:function(){
				$.gameInstall.initEvent();
				$.gameInstall.submit();
			},
			initEvent:function(){
				$("#query").click(function(){
					$.gameInstall.submit();
			    });
				$("#indicators").change(function(){
					$.gameUtil.change($.gameInstall);
				});
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gameInstall.redict($(n).attr("id"));
					});
				});
				
				$("#install_sb_data").hide();
				$("#install_sc_data").hide();
				$("#install_count_data").hide();
				$("#install_sc_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#install_sc_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						
						if($("em",n).attr("class") == 'columnar'){
							$("#install_sc").fadeIn();
							$("#install_sc_data").fadeOut();
						}else{
							$("#install_sc").fadeOut();
							if($("#dt_sc_wrapper").length > 0){
								$("#install_sc_data").fadeIn();
							}else{
								$.gameInstall.brokenPieTable(2);
								$("#install_sc_data").fadeIn();
							}
						}
					});
				});
				$("#install_sb_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#install_sb_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						if($("em",n).attr("class") == 'columnar'){
							$("#install_sb").fadeIn();
							$("#install_sb_data").fadeOut();
						}else{
							$("#install_sb").fadeOut();
							if($("#dt_sb_wrapper").length > 0){
								$("#install_sb_data").fadeIn();
							}else{
								$.gameInstall.brokenPieTable(1);
								$("#install_sb_data").fadeIn();
							}
						}
					});
				});
				$("#install_count_chart .dataShap li").each(function(i,n){
					$(n).click(function(){
						$("#install_count_chart .dataShap li").removeClass("onChoose");
						$(n).addClass("onChoose");
						if($("em",n).attr("class") == 'columnar'){
							$("#install_count").fadeIn();
							$("#install_count_data").fadeOut();
							$("#install_count_chart .dataShap ul").css('right','180px');
							$("#install_count_chart .dataShap ul").css('top','15px');
						}else{
							$("#install_count").fadeOut();
							$("#install_count_chart .dataShap ul").css('right','90px');
							$("#install_count_chart .dataShap ul").css('top','18px');
							if($("#dt_count_wrapper").length > 0){
								$("#install_count_data").fadeIn();
							}else{
								$.gameInstall.brokenLineTable();
								$("#install_count_data").fadeIn();
							}
						}
					});
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/gamePlayer/toGameplayerAnalysis.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/gamePlayer/toGameplayerAnalysis.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			submit:function(){
				var ps = $.gameUtil.checkParam();
				if(ps == null){
					return;
			    }
				
				if($("#s_c").val() == '-99'){
	    			$.gameInstall.brokenAjaxTable(ps);
	    			return;
				}else{
					$('#ajax_data').hide();
					$('#install_sc_chart').show();
					$('#install_sb_chart').show();
					$('#install_count_chart').show();
					$('#data').show();
					
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/gamePlayer/getGameplayerAnalysis.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
							  
						    	var div_install_count = document.getElementById('install_count');
						    	var div_inst_s = document.getElementById('install_sb');
						    	var div_inst_c = document.getElementById('install_sc');
						    	var indicators = $('#indicators').val();
						    	
						    	var dateArr1 = $.gameUtil.getDay('day',$("input[name='beginTime']").val(),$("input[name='endTime']").val()); 
						    	
						    	var reports;
						    	if(indicators == 'all'){
						    		reports = $.gameUtil.proReportData(data.reports,'day',dateArr1);
						    	}else{
						    		reports = $.gameUtil.proReportData(data.scReports,'day',dateArr1);
						    	}
						    	
						    	var arr = reports.dnu_wnu_mnu_arr;
						    	dataArr = arr;
					    		dateArr = dateArr1;
					    		
					    		$.gameInstall.pageCssControl(indicators);
						    	if(indicators == 'all'){
						    		$.gameInstall.brokenLine(div_install_count,arr,dateArr);
							    	$.gameInstall.brokenPie(div_inst_s,data.sreports,data.sreportsCount,'分渠道新注册',1);
							    	$.gameInstall.brokenPie(div_inst_c,data.creports,data.creportsCount,'分服新注册',2);
							    	$.gameInstall.brokenTable(data.reports);
						    	}else {
						    		$.gameInstall.brokenLine(div_install_count,arr,dateArr);
						    		$.gameInstall.brokenTable(data.scReports);
						    	}
						  }
					});
					return;
				}
			},
			pageCssControl:function(page){
				if(page == 'all'){
					$("#install_sb").show();
		    		$("#install_sc").show();
				}else{
					$("#install_sb").hide();
		    		$("#install_sc").hide();
				}
	    		$("#install_count").show();
	    		
	    		$("#install_sb_data").hide();
				$("#install_sc_data").hide();
				$("#install_count_data").hide();
				
				$('#dt_count_wrapper').remove();
				$('#dt_sb_wrapper').remove();
				$('#dt_sc_wrapper').remove();
				
				$(".dataShap li").removeClass("onChoose");
				if(page == 'all'){
					$(".dataShap li:eq(0)").addClass("onChoose");
					$(".dataShap li:eq(2)").addClass("onChoose");
				}
				$(".dataShap li:eq(4)").addClass("onChoose");
				
				$("#install_count_chart .dataShap ul").css('right','180px');
				$("#install_count_chart .dataShap ul").css('top','15px');
			},
			brokenTable:function(reports){
				$('#dt_wrapper').remove();
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt");
				$('#data').append(table);

				var rate = $("#gameRate").val();
				$.each(reports,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.date", trTemp).text(v.day);
					$("td.install", trTemp).text(v.dnu);
					$("td.roleChoice", trTemp).text(v.roleChoice);
					$("td.installPay", trTemp).text(Math.round((v.installPayAmount/rate)*100)/100);
					$("td.installPayRate", trTemp).text(v.dnu==0 ? 0 : (Math.round((v.installPu/v.dnu)*100*100)/100)+"%");
					$("td.arpu", trTemp).text(v.dnu==0 ? 0 :Math.round((v.installPayAmount/v.dnu/rate)*100)/100);
					$("td.arppu", trTemp).text(v.installPu==0 ? 0 :Math.round((v.installPayAmount/v.installPu/rate)*100)/100);

					$('tbody', table).append(trTemp);
				});
				
				table.removeClass("view_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables($('#dt'));
				$('#dt tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
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
						
						option.xAxis = [{data:dateArr[0],type:'category'}];
						option.yAxis = [{type:'value'}];
						
						option.title = {text:'新注册', subtext:'单位（个）'};
						
						option.series = [{name:'新注册',type:'line',data:dataArr}];
						myChart.setOption(option,true);
			        });
				}
			},
			brokenPie:function(cc,data,count,name,index){

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
						if(data == null || count == null || count == 0){
							option_pie.series[0].data = [{value:100, name:'无数据'}];
							option_pie.toolbox.feature.magicType.option.funnel.max = 100;
						}else{
							var pie_data = new Array();
							var otherCount = 0;
							var max = 100;
							$.each(data, function(i,d){
								var name = index == 1 ? d.entity.source : d.entity.clientid;
								var td={name:name,value:d.dnuSum};
								pie_data.push(td);
								otherCount += d.dnuSum;
								max = Math.max(d.dnuSum,max);
							}); 
							var other={name:'其他',value:count - otherCount};
							pie_data.push(other);
							
							option_pie.series[0].data = pie_data;
							option_pie.toolbox.feature.magicType.option.funnel.max = max;
			    		}
						option_pie.title = {text:'',subtext:'单位（%）',x:30,y:20};
						option_pie.title.text = name;
						myChart.setOption(option_pie,true);
						
						if(index == 1){
							tdObj = pie_data;
							pieCount = count;
						}else{
							tab_pie_data = pie_data;
							pie_count = count;
						}
						
						
			        });
				}
			},
			brokenPieTable:function(index){
				var table = null;
				var pieData = null;
				var count = 0;
				
				if(index == 1){
					$('#dt_sb_wrapper').remove();
					table = $(".template_cache .install_sb_table").clone(true);
					table.attr("id", "dt_sb");
					$('#install_sb_data').append(table);
					pieData = tdObj;
					count = pieCount;
				}else{
					$('#dt_sc_wrapper').remove();
					table = $(".template_cache .install_sc_table").clone(true);
					table.attr("id", "dt_sc");
					$('#install_sc_data').append(table);
					pieData = tab_pie_data;
					count = pie_count;
				}
				
				$.each(pieData,function(i,v){
					var trTemp = $("tbody tr", table).first().clone(true);
					if(index == 1){
						$("td.source", trTemp).text(v.name);
					}else{
						$("td.clienid", trTemp).text(v.name);
					}
					
					$("td.install", trTemp).text(v.value);
					$("td.rate", trTemp).text(Math.round((v.value/count)*10000)/100 + '%');

					$('tbody', table).append(trTemp);
				});
				
				if(index == 1){
					table.removeClass("install_sb_table").addClass("table table-striped");
				}else{
					table.removeClass("install_sc_table").addClass("table table-striped");
				}
				
				$("tbody tr", table).first().remove();
				
				if(index == 1){
					$.biDataTables.dataTables_chart($('#dt_sb'));
					$('#dt_sb tbody tr').click(function (){
				        $(this).toggleClass('highlight');
				    });
				}else{
					$.biDataTables.dataTables_chart($('#dt_sc'));
					$('#dt_sc tbody tr').click(function (){
				        $(this).toggleClass('highlight');
				    });
				}
			},
			brokenLineTable:function(){
				$('#dt_count_wrapper').remove();
				var table = $(".template_cache .install_count_table").clone(true);
				table.attr("id", "dt_count");
				$('#install_count_data').append(table);

				for(var i=0;i<dateArr[0].length;i++){
					var trTemp = $("tbody tr", table).first().clone(true);

					$("td.date", trTemp).text(dateArr[0][i]);
					$("td.install", trTemp).text(dataArr[i]);

					$('tbody', table).append(trTemp);
				}
				
				table.removeClass("install_count_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				
				$.biDataTables.dataTables_chart($('#dt_count'));
				$('#dt_count tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			brokenAjaxTable:function(ps){
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				
				var type = $("#indicators").val();
				var rate = $("#gameRate").val();
				$('#ajax_data').show();
				$('#install_sc_chart').hide();
				$('#install_sb_chart').hide();
				$('#install_count_chart').hide();
				$('#data').hide();
				
				var table = null;
				if("source" == type){
					table= $(".template_cache .source_ajax_table").clone(true);
				}else{
					table= $(".template_cache .client_ajax_table").clone(true);
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
							  url:"/panel_bi/gamePlayer/getGameplayerAnalysis.ac"
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
			                      {"data": "dnu"},
			                      {"data": null},
			                      {"data": null},
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
			                    	 targets: 3,
			                    	 render: function (a, b, c, d) {
			                    		 return type == "source" ? c.roleChoice : "-";
			                    	 }
			                     },
			                      {
			                        targets: 4,
			                        render: function (a, b, c, d) {
			                             return Math.round(c.installPayAmount/rate*100)/100;
			                          }
			                       },
			                       {
				                        targets: 5,
				                        render: function (a, b, c, d) {
				                             return c.dnu==0 ? "-%" : (Math.round((c.installPu/c.dnu)*100*100)/100)+"%";
				                          }
				                    },
				                    {
				                        targets: 6,
				                        render: function (a, b, c, d) {
				                             return c.dnu==0 ? "-" :Math.round(c.installPayAmount/c.dnu/rate*100)/100;
				                          }
				                     },
				                     {
					                     targets: 7,
					                     render: function (a, b, c, d) {
					                         return c.installPu==0 ? "-" :Math.round(c.installPayAmount/c.installPu/rate*100)/100;
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
	                    