$(function(){
	
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
		    legend: { data:['老玩家', '新玩家'],y:10},
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
	
	$(document).ready(function() {
	    $.equipVersionDau.init();
	    $.timeZone.showTimeZone();
	});
		
	$.equipVersionDau={
			init:function(){
				$.equipVersionDau.initEvent();
				$.equipVersionDau.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
				    $('#caption button')[0].disabled = false;
					$.equipVersionDau.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.equipVersionDau.redict($(n).attr("id"));
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
//				window.location.href='/panel_bi/equip/toEuipment.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/equip/toEuipment.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			queryData:function(){
				var ps = $.equipVersionDau.checkParam();
				if(ps == null){
					return;
			    }
				
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/equip/getEquipmentData.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  
							$('#dt_ajax_wrapper').remove();
							var table = $(".template_cache .view_table").clone(true);
							table.attr("id", "dt_ajax");
							$('#data').append(table);
							
							var tr = $("thead tr",table).first();
							var legend = new Array();//列定义数组
							$.each(data.names,function(i,v){
								var td = $("td",tr).first().clone(true);
								td.text(v);
								legend.push(v);
								tr.append(td);
							});
							var td = $("td",tr).first().clone(true);
							td.text('其他');
							legend.push('其他');
							tr.append(td);
							
							var len = legend.length;
							$.each(data.equipVersionDauList,function(i,v){
								var tr = $("tbody tr",table).first().clone(true);
								var temArr = [v.v1,v.v2,v.v3,v.v4,v.v5];
								$("td",tr).eq(0).text(v.ds);
								for(var k=1;k<=len;k++){
									var td = $("td",tr).first().clone(true);
									td.text(temArr[k-1] == null ? 0 : temArr[k-1]);
									tr.append(td);
								}
								table.append(tr);
							});
							
							$("tbody tr", table).first().remove();
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
							
							$('#dt_ajax').DataTable({
								"sPaginationType" : "simple_numbers",
					            //"aoColumnDefs": [ { "bSortable": false, "aTargets": [1] }] ,
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
							    	"url": "/dataTables/chinese_chart.json"
							    }
								}
							  );
							
							$('#dt_ajax').removeClass().addClass('table table-striped');
							$('tbody tr',table).click(function (){
						        $(this).toggleClass('highlight');
						    });
							
							var dateArr = $.gameUtil.getDay('day',ps.beginTime,ps.endTime);
							$.equipVersionDau.brokenDauBar($("#dau_zhu")[0],dateArr,legend,data.equipVersionDauList);
					  }
				});
			},
			checkParam:function(){
				var ps={
						 beginTime:$("input[name='beginTime']").val(),
						 endTime:$("input[name='endTime']").val(),
						 view:$('#view').val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val()
					};
			      
			      var dateArr = $.gameUtil.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='beginTime']").val(dateArr[0]);
			      $("input[name='endTime']").val(dateArr[1]);
			      
			      return ps;
			},
			brokenDauBar:function(cc,dateArr,names,list){

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
						
						optionCumuli.xAxis = [{type:'category',data:dateArr[0]}];
						
						var len = names.length;
						var dataArr = new Array();
						for(var i=0;i<len;i++){
							dataArr.push({
								name:names[i],
					            type:'bar',
					            stack: '总量',
					            itemStyle : { normal: {
					            	label : {
					            		show: true, 
					            		position: 'insideRight',
					            		textStyle:{color:'#fff'}
					            			}}},
					            data:new Array()
							}
							);
						}
						
						$.each(dateArr[1],function(k,d){
							$.each(list,function(i,v){
								if(v.ds == d){
									var temArr = [v.v1,v.v2,v.v3,v.v4,v.v5];
									for(var j=0;j<len;j++){
										dataArr[j].data.push(temArr[j] == null ? 0 : temArr[j]);
									}
								}
							});
						});
						
						optionCumuli.series = dataArr;
						optionCumuli.legend.data = names;
						myChart.setOption(optionCumuli,true);
			        });
				}
			}
	};	
});
	                    