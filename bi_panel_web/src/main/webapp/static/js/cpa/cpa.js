$(function(){
	
	$(document).ready(function() {
	    $.channelCpa.init();
	    $.timeZone.showTimeZone();
	});
		
	var dateArr = null;
	$.channelCpa={
			init:function(){
				$.channelCpa.initEvent();
				$.channelCpa.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
				    $('#caption button')[0].disabled = false;
					$.channelCpa.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.channelCpa.redict($(n).attr("id"));
					});
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/costPer/toCPA.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/costPer/toCPA.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			queryData:function(){
				var ps = $.channelCpa.checkParam();
				if(ps == null){
					return;
			    }
				
				dateArr = $.gameUtil.getDay('day',$("input[name='ec_beginTime']").val(),$("input[name='ec_endTime']").val());
				
				$('#dt_ajax_wrapper').remove();
				
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var columnsArr = new Array();//列定义数组
				var columnsDefArr = new Array();//列值定义数组
				columnsArr.push({"data": "channelId","title":"渠道代码","defaultContent":""});
				columnsArr.push({"data": "channelName","title":"渠道名称","defaultContent":""});
				if(!data.isOutSideUser){
					columnsArr.push({"data": "realActive","title":"真实活跃","defaultContent":""});
					columnsArr.push({"data": "realRegist","title":"真实注册","defaultContent":""});
				}
				columnsArr.push({"data": "kouliangActive","title":"活跃","defaultContent":""});
				columnsArr.push({"data": "kouliangRegist","title":"注册","defaultContent":""});
				columnsArr.push({"data": "day1","title":"d1","defaultContent":""});
				columnsArr.push({"data": "day3","title":"d3","defaultContent":""});
				columnsArr.push({"data": "day7","title":"d7","defaultContent":""});
	        	
	        	columnsDefArr.push({ orderable: false, targets: [0,1] });
	        	
				jq_table = $('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
				          data: function(d){
				        	  d.indicators=ps.indicators;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.view=ps.view;
				        	  d.channel=ps.channel;
				        	  d.snid=ps.snid;
				        	  d.gameId=ps.gameId;
				          },
						  type: "POST",
						  url:"/panel_bi/costPer/getChannelDataList.ac"
		              },
			        "pageLength": 50,
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
				    "columns":columnsArr,
				    "columnDefs":columnsDefArr
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				$('#caption button').unbind(); 
				$('#caption button').click(function (){
					if(confirm("下载可能需要一段时间请耐心等待不要离开页面，是否确认下载？")){
						$(this)[0].disabled = true;
				        $("#downLoadForm").submit();
					}
			    });
				
				
			},
			checkParam:function(){
				var ps={
					     indicators:$('#indicators').val(),
						 beginTime:$("input[name='ec_beginTime']").val(),
						 endTime:$("input[name='ec_endTime']").val(),
						 view:$('#view').val(),
						 channel:$('#channel').val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val()
					};
			      
			      if(ps.indicators == null || ps.indicators == ''){
			    	  ps.indicators = 'source'; 
			       }
			      
			      if(ps.channel == null || ps.channel == ''){
			    	  ps.queryType = '-1';
			      }
			      
			      var dateArr = $.channelCpa.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='ec_beginTime']").val(dateArr[0]);
			      $("input[name='ec_endTime']").val(dateArr[1]);
			      
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
			    if(b.getTime() <= d.getTime()){
			    	alert('日期区间不能大于15天！');
			    	return null;
			    }
			      
				return [beginTime,endTime];
			}
	};	
});
	                    