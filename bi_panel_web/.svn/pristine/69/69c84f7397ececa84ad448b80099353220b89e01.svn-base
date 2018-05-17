$(function(){
	
	$(document).ready(function() {
	    $.channelCpaCreative.init();
	    $.timeZone.showTimeZone();
	});
		
	var dateArr = null;
	$.channelCpaCreative={
			init:function(){
				$.channelCpaCreative.initEvent();
				$.channelCpaCreative.initDate();
				$.channelCpaCreative.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){

				    var beginTime = $("input[name='beginTime']").val();
				    var endTime = $("input[name='endTime']").val();
				    var ec_endTime= $("input[name='ec_endTime']").val();
				    var channel = $('#channel').val();

//				    if(!$.channelCpaCreative.isToday(ec_endTime)){
				    if(channel==-1 || channel=="-1")
				    	$.channelCpaCreative.delData(ec_endTime);
//				    }
				    
					$.channelCpaCreative.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.channelCpaCreative.redict($(n).attr("id"));
					});
				});
				
			},
			
//			redict:function(view){
//				window.location.href='/panel_bi/cpaCreative/toCPACreative.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/cpaCreative/toCPACreative.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			isToday:function(endTime){
				var today = new Date();
				var endDate = new Date(endTime);
				if(today.getFullYear()==endDate.getFullYear()
						&&today.getMonth()==endDate.getMonth()
						&&today.getDate()==endDate.getDate()){
					return true;
				}else{
					return false;
				}
			},
			initDate:function(){
				var d = new Date();
				
				d.setDate(d.getDate());
				endTime = $.date.getDateFullStr(d);
				
				d.setDate(d.getDate()- 1);
				beginTime = $.date.getDateFullStr(d);
				
			    $("input[name='beginTime']").val(beginTime);
			    $("input[name='endTime']").val(endTime);
			    $("input[name='ec_endTime']").val(endTime);
			},
			queryYesterdayData:function(ec_endTime){
				var data = {
						endTime:ec_endTime,
						snid:$("input[name='snid']").val(),
						gameId:$("input[name='gameId']").val()
					}
				$.ajax({
					type : "POST",
					url : "/panel_bi/cpaCreative/queryYesterdayData.ac",
					data : data,
					dataType : "json",
					success : function(msg) {
						
					}
				});
			},
			delData:function(ec_endTime){
				var data = {
						endTime:ec_endTime,
						snid:$("input[name='snid']").val(),
						gameId:$("input[name='gameId']").val()
					}
				$.ajax({
					type : "POST",
					url : "/panel_bi/cpaCreative/delChannelCreativeDataList.ac",
					data : data,
					dataType : "json",
					success : function(msg) {
						
					}
				});
			},
			queryData:function(){
				var ps = $.channelCpaCreative.checkParam();
				if(ps == null){
					return;
			    }
				
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var gameRate = $('#gameRate').val();
				var columnsDefArr = new Array();//列值定义数组
	        	
				$("td",$("tr",table).first()).first().remove();
				columnsDefArr.push({ orderable: false, targets: [0,1] });
				columnsDefArr.push({ 
					targets: 0,
							render: function (a, b, v, d) {
								return v.entity.source;
							}
				});
	        	columnsDefArr.push({ 
        			targets: 1,
        			render: function (a, b, v, d) {
        				return v.sourceName;
        			}
        		});
	        	columnsDefArr.push({ 
	        		targets: 2,
	        				render: function (a, b, v, d) {
	        					return v.entity.dnu
	        				}
	        	});
	        	columnsDefArr.push({ 
	        		targets: 3,
	        				render: function (a, b, v, d) {
	        					return v.entity.newEquip;
	        				}
	        	});
	        	
				jq_table = $('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
				          data: function(d){
//				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.view=ps.view;
				        	  d.channel=ps.channel;
				        	  d.snid=ps.snid;
				        	  d.gameId=ps.gameId;
				          },
						  type: "POST",
						  url:"/panel_bi/cpaCreative/getChannelCreativeDataList.ac"
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
				    rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                    },
				    "order": [[ 2, "desc" ]],
				    "columnDefs":columnsDefArr
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				$('#caption button').unbind(); 
				$('#caption button').click(function (){
			        $(this).attr('disabled',true);
			        $(this).val(2);
			        window.btnTimeout=setInterval(function(){
						$.gameUtil.btnTimeout($('#caption button'),window.btnTimeout);
					  },1000);
			        $("#downLoadForm").submit();
			    });
				
				$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
				
			},
			checkParam:function(){
				var ps={
						 endTime:$("input[name='ec_endTime']").val(),
						 view:$('#view').val(),
						 channel:$('#channel').val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val()
					};
			      
			      if(ps.channel == null || ps.channel == ''){
			    	  ps.queryType = '-1';
			      }
			      
			      var dateArr = $.channelCpaCreative.processDate(ps.endTime);
			      ps.endTime = dateArr[0];
		      
			      $("input[name='ec_endTime']").val(dateArr[0]);
			      
			      return ps;
			},
			processDate:function(endTime){
				var d = new Date();
				var endDate = $.date.getDateFullStr(d);
				d.setDate(d.getDate()- 1);
				var beginDate = $.date.getDateFullStr(d);
				
			    if(endTime == null || endTime == ''){
			    	  d.setDate(d.getDate());
			    	  endTime = $.date.getDateFullStr(d);
			       }
			    
			    var d = new Date(endDate);
			    var b = new Date(beginDate);
			    endTime = new Date(endTime);
			    if(endTime.getTime() < b.getTime()||endTime.getTime()>d.getTime()){
			         alert('查询时间只能为今天或昨天');
		    	     return null;
			    }
			      
				return [$.date.getDateFullStr(endTime)];
			}
	};	
});
	                    