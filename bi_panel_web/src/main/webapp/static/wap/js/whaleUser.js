$(function(){
	
	$(document).ready(function() {
	    $.whaleUser.init();
	    $.timeZone.showTimeZone();
	});
		
	var dateArr = null;
	$.whaleUser={
			init:function(){
				$.whaleUser.initEvent();
				$.whaleUser.queryData();
			},
			initEvent:function(){
				if ($("#beginDauTime").length > 0){
					laydate({
						elem: '#beginDauTime',
					});
				}
				
				if($("#endDauTime").length > 0){
					laydate({
						elem: '#endDauTime'
					});
				}
				
				$("#query").click(function(){
				    $('#caption button')[0].disabled = false;
					$.whaleUser.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.whaleUser.redict($(n).attr("id"));
					});
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			queryData:function(){
				var ps = $.whaleUser.checkParam();
				if(ps == null){
					return;
			    }
				
				var selected = [];
				var curr = $("#gameCurrency").val();
				$('#dt_ajax_wrapper').remove();
				
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var columnsArr = new Array();//列定义数组
				var columnsDefArr = new Array();//列值定义数组
				columnsArr.push({"data": "userid","title":"用户ID","defaultContent":""});
				columnsArr.push({"data": "installDate","title":"安装日期","defaultContent":""});
				columnsArr.push({"data": "firstPayTime","title":"首次<br/>付费日期","defaultContent":""});
				columnsArr.push({"data": "firstPayAmount","title":"首次<br/>付费额("+curr+")","defaultContent":""});
				columnsArr.push({"data": "totalPayAmount","title":"总付费("+curr+")","defaultContent":""});
				columnsArr.push({"data": "lastPayTime","title":"最近<br/>付费日期","defaultContent":""});
				columnsArr.push({"data": null,"title":"未充值<br/>天数","defaultContent":""});
				columnsArr.push({"data": "lastDauTime","title":"最近<br/>登录日期","defaultContent":""});
				columnsArr.push({"data": null,"title":"未登陆<br/>天数","defaultContent":""});
				columnsArr.push({"data": "level","title":"用户<br/>等级","defaultContent":""});
				columnsArr.push({"data": "firstServer","title":"首登<br/>服务器","defaultContent":""});
				columnsArr.push({"data": "lastServer","title":"近登<br/>服务器","defaultContent":""});
				columnsArr.push({"data": "clientid","title":"服务器ID","defaultContent":""});
				columnsArr.push({"data": "roleid","title":"角色ID","defaultContent":""});
				columnsArr.push({"data": "roleName","title":"角色名称","defaultContent":""});
				columnsArr.push({"data": "vipLevel","title":"vip等级","defaultContent":""});
				columnsArr.push({"data": "ds","title":"更新日期","defaultContent":""});
	        	
	        	columnsDefArr.push({ orderable: false, targets: [0,10,11,13,14,16] });
	        	columnsDefArr.push({ 
					targets: 6,
					render: function (a, b, v, d) {
						var startTime = new Date(v.ds).getTime();     
					    var endTime = new Date(v.lastPayTime).getTime();     
					    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
						return dates;
						} 
	        		}
	        	);
	        	columnsDefArr.push({ 
					targets: 8,
					render: function (a, b, v, d) {
						var startTime = new Date(v.ds).getTime();     
					    var endTime = new Date(v.lastDauTime).getTime();     
					    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
						return dates;
						} 
	        		}
	        	);
	        	
				jq_table = $('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
				          data: function(d){
				        	  d.indicators=ps.indicators;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.beginDauTime=ps.beginDauTime;
				        	  d.endDauTime=ps.endDauTime;
				        	  d.beginPay=ps.beginPay;
				        	  d.endPay=ps.endPay;
				        	  d.indicators=ps.indicators;
				          },
						  type: "POST",
						  url:"/panel_bi/whaleUser/getWhaleUser.ac"
		              },
			        "pageLength": 50,
				    "language":{
				    	"url": "/dataTables/chinese.json"
				    },
			        "deferRender": true,
				    "sPaginationType" : "full_numbers",
			        "dom" : '<"clear"><"top">rt<"bottom"ip><"clear">' ,
					"bSort" : true,
				    "bFilter" : true,
				    "paging": true, //翻页功能
				    "lengthChange": false, //改变每页显示数据数量
				    "searching": true, //过滤功能
				    "ordering": true, //排序功能
				    "order": [[ 4, "desc" ]],
				    "columns":columnsArr,
				    "columnDefs":columnsDefArr,
				    rowCallback:function(row, data) {
				    	var startTime = new Date(data.ds).getTime();     
					    var endTime = new Date(data.lastPayTime).getTime();     
					    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);
					    if(Number(dates)>=14){
					    	$(row).addClass('rowDataWarn-yellow');
					    }
					    
					    // 以未登录为主
					    var lastDauTime = data.lastDauTime;
		                var before = $.date.getBeforeTodayDate(4)[0];
		                if(lastDauTime <= before){
		                	$(row).removeClass('rowDataWarn-yellow');
		                	$(row).addClass('rowDataWarn');
		                }
	                   
	                   if ($.inArray(data.DT_RowId, selected) !== -1 ) {
	                        $(row).addClass('highlight');
	                    }
	                }
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				$('caption button').unbind(); 
				$('caption button').click(function (){
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
						 beginDauTime:$("input[name='beginDauTime']").val(),
						 endDauTime:$("input[name='endDauTime']").val(),
						 beginPay:$("input[name='beginPay']").val(),
						 endPay:$("input[name='endPay']").val(),
						 indicators:$('#indicators').val()
					};
			      
			      if(ps.indicators == null || ps.indicators == ''){
			    	  ps.indicators = 'all'; 
			       }
			      return ps;
			}
	};	
});
	                    