$(function(){
	$(document).ready(function() {
	    $.customTask.init();
	    $.timeZone.showTimeZone();
	});
/*	function MyAutoRun(){
		//alert("111");
		$.customTask.queryData();
	}
	window.setInterval(MyAutoRun, 30000); 
	 */
	var jq_table = null;
	$.customTask={
		init:function(){
			$.customTask.initEvent();
//			$.customTask.queryData(null);
			$.customTask.querySelect();
		},
		initEvent:function(){
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.customTask.redict($(n).attr("id"));
				});
			});
			
			$("#str1").hide();
			
			$(".reflush-btn").click(function(){
				if(jq_table == null){
					return false;
				}else{
					 jq_table.draw(false);
				}
			});
			
			$("#model","#taskSelect").change(function(){
				var tempTable = $(".template_cache .addDataTable").clone(true);
				var trTemp = $(".parm",tempTable).first().clone(true);
				var value = $(this).val();
				var params={
						snid:$("input[name='snid']").val(),
						gameId:$("input[name='gameId']").val()
					};
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/customTask/getTempPram.ac?templateId="+value,
					  data:$.param(params),
					  dataType: "json",
					  success: function(data){
						  
						  $(".parm", "#taskSelect").remove();
						  var tr = null;
						  var laydateArr = new Array();
						  $.each(data.params,function(i,n){
							  if(i % 1 == 0){
								  if(tr != null){
									  $("th.name",tr).first().remove();
									  $("td.value",tr).first().remove();
								  }
								  
								  tr = trTemp.clone(true);
								  $("#taskSelect").append(tr);
							  }
							 
							  var td = $("th.name",tr).first().clone(true);
							  td.text(n.paramName);
							  var tdv = $("td.value",tr).first().clone(true);
							  if(n.paramCode == 'snid' || n.paramCode == 'gameid'){
								  tdv.html('<span code='+n.paramCode+'>'+$("#"+n.paramCode+"").val()+'</sapn>');
							  }else if(n.paramCode == 'day' || n.paramCode == 'ds' || n.paramCode.toLowerCase().indexOf('date'.toLowerCase()) >= 0){
								  tdv.html('<input id="'+n.paramCode+'" />');
								  laydateArr.push(n.paramCode);
							  }else{
								  tdv.html('<input id="'+n.paramCode+'" />');
							  }
							  
							  tr.append(td);
							  tr.append(tdv);
						  });
						  
						  if(tr != null){
							  $("th.name",tr).first().remove();
							  $("td.value",tr).first().remove();
						  }
						  for(var i=0;i<laydateArr.length;i++){
							  laydate({
							    	elem: '#'+laydateArr[i]+'',
								});
						  }
						  
						  if(data.customReportModel){
							  $('#tempDesc').show();
							  $('#interveldesc').text(data.customReportModel.intervalTime);
							  
							  if(data.customReportModel.description.length > 90){
								  $('#desc').text(data.customReportModel.description.substring(0,90)+'...');
								  $('#desc').attr('title',data.customReportModel.description);
							  }else{
								  $('#desc').text(data.customReportModel.description);
							  }
							  
						  }
					  }
				});
			});
			
			$("#queryTask").click(function(){
				
				var modelValue = $('#model').val();
				if(modelValue==-1){
					$("#str1").show();
					return;
				}else{
					$("#str1").hide();
				}
				
				$.customTask.queryTask();
			});
			
			$("#model").change(function(){
				if($("#taskSelect").val()!=-1){
					$("#str1").hide();
				}
			});
			
		},
//		redict:function(view){
//			window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		
		MillisecondToDate:function(msd) {
		    var time = parseFloat(msd) / 1000;
		    if (null != time && "" != time) {
		        if (time > 60 && time < 60 * 60) {
		            time = parseInt(time / 60.0) + "分钟" + parseInt((parseFloat(time / 60.0) -
		                parseInt(time / 60.0)) * 60) + "秒";
		        }
		        else if (time >= 60 * 60 && time < 60 * 60 * 24) {
		            time = parseInt(time / 3600.0) + "小时" + parseInt((parseFloat(time / 3600.0) -
		                parseInt(time / 3600.0)) * 60) + "分钟" +
		                parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) -
		                parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60) + "秒";
		        }
		        else {
		            time = parseInt(time) + "秒";
		        }
		    }
		    return time;
		},
		
		queryTask:function(){
			var reportModel = $("#model", "#taskSelect").val();
			if(reportModel == -1){
				return;
			}
			
			var name = '';
			var value = '';
			
			$(".queryTask .parm").each(function(i,n){
				for(var j=0;j<$("td",n).length;j++){
					var td = $("td",n).eq(j);
					if($("input",td).length > 0){
						var input = $("input",td);
						name += $("th",n).eq(j).text()+":"+input.attr("id")+",";
						value += input.val() + ",";
					}else{
						var span = $("span",td);
						name += $("th",n).eq(j).text()+":"+span.attr("code")+",";
						value += span.text() + ",";
					}
				}
				
			});
			
			var params={
				nameArr:name,
				valueArr:value,
				reportModel:reportModel,
				snid:$("input[name='snid']").val(),
				gameId:$("input[name='gameId']").val()
			};
			
			$.ajax({
	               "url":"/panel_bi/wap/gameView/addTask.ac",
	               "data":$.param(params),
	               "type":"post",
	               "error":function(){
	                   alert("服务器未正常响应，请重试");
	               },
	               "success":function(data){
	            	   if(data==""){
	            		   $.customTask.queryData(params);
	                   }else if(data.msg==0){
	                	   $.customTask.queryData(params);
	                   }else if(data.msg == 1){
	                	   $("#str1").show();
	                   }else if(data.msg == 3){
	                	   alert('执行频繁，请：'+data.taskInterval+"分钟后再试！");
	                   }else if(data.msg == 4){
	                	   $("#str1").show();
	                   }
	               }
	           });
		},
		
		querySelect:function(){
			var params={
					snid:$("input[name='snid']").val(),
					gameId:$("input[name='gameId']").val()
				};
			$.ajax({
				type: "POST",
				url: "/panel_bi/customTask/toAddTask.ac",
				data: $.param(params),
				dataType: "json",
				success: function(data){
					var select = $("#model");
					$.each(data.models,function(i,n){
						var option = $("option",select).first().clone(true);
						option.val(n.id);
						option.text(n.name);
						select.append(option);
					});
				}
			});
		},
		
		queryData:function(source){
			var params = null;
			if(source!=null){
				params = source;
			}
			$('#dt_ajax_wrapper').remove();
			var ps = {
					snid:$("input[name='snid']").val(),
					gameId:$("input[name='gameId']").val()
			};
			
			var modelValue = $('#model').val();
			var dateTime = $('#ec_beginTime').val();
			
			var table = $(".template_cache .ajax_table").clone(true);
			table.attr("id", "dt_ajax");
			$('#data').append(table);
			var selected = [];
			var urlStr = "/panel_bi/wap/gameView/getTaskList.ac?reportModel="+modelValue;
			urlStr = params==null?urlStr:(urlStr+"&nameArr="+params.nameArr+"&valueArr="+params.valueArr+"&snid="+ps.snid+"&gameId="+ps.gameId);
		   jq_table = $('#dt_ajax').DataTable({
				"processing": true,
		        "serverSide": true,
		        ajax: {
						  type: "POST",
						  url:urlStr
		              },
		        "pageLength": 5,
			    "language":{
			    	"url": "/dataTables/chinese.json"
			    },
		        "deferRender": false,
			    "sPaginationType" : "full_numbers",
		        "dom" : '<"clear"><"top">frt<"bottom"ip><"clear">' ,
				"bSort" : false,
			    "bFilter" : true,
			    "paging": true, //翻页功能
			    "lengthChange": false, //改变每页显示数据数量
			    "searching": false, //过滤功能
			    "ordering": false, //排序功能
			    "order": [[ 0, "asc" ]],
			    rowCallback:function(row, data) {
                    if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                        $(row).addClass('highlight');
                    }
                },
			    columns: [
			              {"data": "taskName","title":"任务名称","defaultContent": ""},
			              {"data": "status","title":"执行状态","defaultContent":""},
	                      {"data": "executeTime","title":"执行时间","defaultContent":""},
	                      {"data": "endTime","title":"执行时长","defaultContent":""},
	                      {"data":null, "title":"操作","defaultContent": ""}
	            ],
                columnDefs: [	
								{targets: 0,
									   render: function (a, b, v, d) {
										   if(v.taskName.length > 50){
											  return '<span title='+v.taskName+'>'+v.taskName.substring(0,50)+'...</span>';
										   }else{
											   return v.taskName;
										   }
									   }
								},
								{
	  		                    	 targets: 1,
	  		                    	 render: function (a, b, v, d) {
	  		                    		 return v.status == 1 ? "成功" : v.status == 0 ? "失败" : "执行中";
	  		                    	 }
	                               },
                               {targets: 2,
                            	   render: function (a, b, v, d) {
                            		   return $.date.getDateSecond(new Date(v.executeTime));
                            	   }
                               },
                               {
                            	   targets: 3,
                            	   render: function (a, b, v, d) {
                            		   return v.status == 1 ? $.customTask.MillisecondToDate(v.endTime-v.executeTime) : "";
                            	   }
                               },
	                               {
	                            	   targets: 4,
	                            	   render: function (a, b, v, d) {
	                            		   if(v.status == 1){
	                            			   	var query = "<button class='query-btn' style='width:50px;' type='button' val='"+v.id+"' rec="+v.recordCount+"  title='点击查看此报表的已有的查询结果，速度快'>查看</button>";
	                           			   		var flu = "<button id='flush' class='add-btn' style='width:50px;' type='button' val='"+v.id+"' title='点击刷新获取最新数据，需等待且需间隔一定时间不能频繁执行'>刷新</button>";
	                           				    return query + flu;
	                            		   }else if(v.status == 0){
	                            			   return "<button class='add-btn' style='width:100px;' type='button' val='"+v.id+"' >重试</button>";
	                            		   }else{
	                            			   return "";
	                            		   }
	                            	   }
	                               }
			                     ]
			    });
		   		
				$('#dt_ajax').removeClass().addClass('table table-striped');
				
				$('#dt_ajax tbody').on('click', 'tr', function () {
					$("tr",$('#dt_ajax tbody')).removeClass('highlight');
			        var id = this.id;
			        var index = $.inArray(id, selected);
			 
			        if ( index === -1 ) {
			            selected.push( id );
			        } else {
			            selected.splice( index, 1 );
			        }
			 
			        $(this).toggleClass('highlight');
			    } );
				
				$("#dt_ajax tbody").on("click",".query-btn",function(){
					if($(this).attr("rec") <= 0){
						$(".result").html('<font style="font-weight: bold">无查询结果<br></font>');
						$(".result").show();
						$("#value_data").hide();
						return;
					}else{
						$(".result").html('<font style="font-weight: bold">请耐心等待查询结果...<br></font>');
						$(".result").show();
						$("#value_data").show();
					}
					
					var taskId = $(this).attr("val");
						var params={
							snid:$("input[name='snid']").val(),
							gameId:$("input[name='gameId']").val()
						};
			           $.ajax({
			               "url":"/panel_bi/customTask/getTaskDataCache.ac?taskId="+taskId,
			               "data":$.param(params),
			               "type":"post",
			               /*"error":function(){
			                   alert("服务器未正常响应，请重试");
			               },*/
			               "success":function(data){
			            	   var selected = [];
			            	   $('#dt_value_ajax_wrapper').remove();
							   var table = $(".template_cache .ajax_cache_table").clone(true);
								table.attr("id", "dt_value_ajax");
								$('#value_data').append(table);
								
								var paramNames = data.paramNames;
								var paramCodes = data.paramCodes;
								var values = data.values;
								
								if(data.reportModel.description == ''){
									$(".result").html('<font style="font-weight: bold">查询结果：<br></font>');
								}else{
									$(".result").html('<font style="font-weight: bold">模板说明：<br></font>'+data.reportModel.description);
								}
								
								var index = paramNames.length;
								var th = $("thead tr",table);
								for(var i=0;i<index;i++){
									var td = $("td",th).first().clone(true);
								  	td.text(paramNames[i]);
									th.append(td);
					        	}
								$("td",th).first().remove();
								
								var valueIndex = values.length;
								for(var j=0;j<valueIndex;j++){
									var tr = $("tbody tr",table).first().clone(true);
									for(var k=0;k<index;k++){
										var td = $("td",tr).first().clone(true);
										var vp = values[j][paramCodes[k]];
									  	td.text(vp == null || vp == 'null' ? '' : vp);
										tr.append(td);
						        	}
									$("td",tr).first().remove();
									table.append(tr);
								}
								$("tbody tr",table).first().remove();
								
								 $('#dt_value_ajax').DataTable({
									 "pageLength": 50,
									    "language":{
									    	"url": "/dataTables/chinese.json"
									    },
								        "deferRender": true,
									    "sPaginationType" : "full_numbers",
								        "dom" : 'T<"clear"><"top">frt<"bottom"ip><"clear">' ,
										"bSort" : false,
									    "bFilter" : true,
									    "paging": true, //翻页功能
									    "lengthChange": false, //改变每页显示数据数量
									    "searching": false, //过滤功能
									    "ordering": true, //排序功能
									    "order": [[ 0, "asc" ]],
									    rowCallback:function(row, data) {
					                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
					                            $(row).addClass('highlight');
					                        }
					                    },
									    tableTools: {
									    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
							                                       "aButtons":[ 
							                            { 
							                               "sExtends": "xls", 
							                               "sButtonText": "下载数据" 
							                             }]
								        }
								 });
								 $('#dt_value_ajax').removeClass().addClass('table table-striped');
								 $.gameUtil.trHighLight($('#dt_value_ajax tbody'),selected);
			               }
			           });
			      });
				
				$("#dt_ajax tbody").on("click",".add-btn",function(){
					
					$(this).hide();
					var params={
							snid:$("input[name='snid']").val(),
							gameId:$("input[name='gameId']").val()
						};
					var taskId = $(this).attr("val");
			           $.ajax({
			               "url":"/panel_bi/customTask/flushTask.ac?taskId="+taskId,
			               "data":$.param(params),
			               "type":"post",
			               "error":function(){
			            	   $(this).show();
			                   alert("服务器未正常响应，请重试");
			               },
			               "success":function(data){
			            	   $(this).show();
			            	   if(data.msg==0){
			                	   jq_table.draw(false);
			                   }else if(data.msg == 1){
			                	   alert('内容不能为空，请重新输入！');
			                   }else if(data.msg == 3){
			                	   alert('执行此任务的时间间隔为：'+data.taskInterval+"分钟,请稍后再试！");
			                   }
			               }
			           });
			      });
		}
	};
});