$(function(){
	
		                    
	
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
				
				$(".tab-hd span").click(function(){
					$.equipVersionDau.redict($(this).attr("view"));
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
				var ps = $.equipVersionDau.checkParam();
				if(ps == null){
					return;
			    }
				
				ps.nameRequest = 'nameRequest';
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/equip/getEquipmentData.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  ps.nameRequest = '';
						  
						    var selected = [];
							$('#dt_ajax_wrapper').remove();
							var table = $(".template_cache .view_table").clone(true);
							var tr = $("thead tr",table).first();
							
							var columnsArr = new Array();//列定义数组
							columnsArr.push({"data": "ds","title":"日期","defaultContent":""});
							$.each(data.names,function(i,v){
								columnsArr.push({"data": "v"+(i+1),"title":v,"defaultContent":""});
								tr.append($("td",tr).first().clone(true));
							});
							columnsArr.push({"data": "other","title":"其他","defaultContent":""});
							
							
							table.attr("id", "dt_ajax");
							$('#data').append(table);
							
							var jq_table = $('#dt_ajax').dataTable( {
						        "processing": true,
						        "serverSide": true,
						        ajax: {
							          data: function(d){
							        	  d.beginTime=ps.beginTime;
							        	  d.endTime=ps.endTime;
							        	  d.snid=ps.snid;
							        	  d.gameId=ps.gameId;
							        	  d.view=ps.view;
							          },
									  type: "POST",
									  url:"/panel_bi/equip/getEquipmentData.ac?isPageRequest=2"
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
							    "searching": false, //过滤功能
							    "ordering": true, //排序功能
							    "order": [[ 0, "desc" ]],
							    rowCallback:function(row, data) {
			                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
			                            $(row).addClass('highlight');
			                        }
			                    },
							    "columns":columnsArr
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
					  }
				});
			},
			checkParam:function(){
				var ps={
						 beginTime:$("input[name='beginTime']").val(),
						 endTime:$("input[name='endTime']").val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val(),
						 view:$('#view').val(),
						 nameRequest:''
					};
			      
			      var dateArr = $.gameUtil.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='beginTime']").val(dateArr[0]);
			      $("input[name='endTime']").val(dateArr[1]);
			      
			      return ps;
			}
	};	
});
	                    