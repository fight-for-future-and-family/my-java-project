$(function(){
	$(document).ready(function() {
	    $.customTask.init();
	    $.timeZone.showTimeZone();
	    
	   
	});
	
	function MyAutoRun(){
		//alert("111");
		$.customTask.queryData();
	}
	window.setInterval(MyAutoRun, 30000); 
	 
	var jq_table = null;
	$.customTask={
		init:function(){
			$.customTask.initEvent();
			$.customTask.queryData();
			
		},
		initEvent:function(){
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.customTask.redict($(n).attr("id"));
				});
			});
			
			$(".reflush-btn").click(function(){
				if(jq_table == null){
					return false;
				}else{
					 jq_table.draw(false);
				}
			});
			
			$(".add-btn").click(function(){
				var params={
						snid:$("input[name='snid']").val(),
						gameId:$("input[name='gameId']").val()
					};
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/customTask/toAddTask.ac",
					  data:$.param(params),
					  dataType: "json",
					  success: function(data){
						  var tempTable = $(".template_cache .addDataTable").clone(true)
						  var select = $("#model",tempTable);
						  
						  $.each(data.models,function(i,n){
							  var option = $("option",select).first().clone(true);
							  option.val(n.id);
							  option.text(n.name);
							  select.append(option);
						  });
						  
						  $("#addTable").html(tempTable);
						  
						  $(".theme-popover .add-btn").unbind();
							$(".theme-popover .add-btn").click(function(){
								
								var reportModel = $("#model",tempTable).val();
								if(reportModel == -1){
									return;
								}
								
								var name = '';
								var value = '';
								
								$(".theme-popover .parm").each(function(i,n){
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
								
//								$(".theme-popover .value").each(function(i,n){
//									if($("input",n).length > 0){
//										var input = $("input",n);
//										name += input.attr("id")+",";
//										value += input.val() + ",";
//									}else{
//										var span = $("span",n);
//										name += span.attr("code")+",";
//										value += span.text() + ",";
//									}
//									
//								});
								
								var data={
									nameArr:name,
									valueArr:value,
									reportModel:reportModel,
									snid:$("input[name='snid']").val(),
									gameId:$("input[name='gameId']").val()
								};
								
								$.ajax({
						               "url":"/panel_bi/customTask/addTask.ac",
						               "data":$.param(data),
						               "type":"post",
						               "error":function(){
						                   alert("服务器未正常响应，请重试");
						               },
						               "success":function(data){
						            	   if(data.msg==0){
						                	   alert('保存成功！');
						                	   $.customTask.queryData();
						                	   jq_table.draw(false);
						                	   $('.theme-popover-mask').fadeOut(100);
						   					   $('.theme-popover').slideUp(200);
						   					
						                   }else if(data.msg == 1){
						                	   alert('内容不能为空，请重新输入！');
						                   }else if(data.msg == 2){
						                	   var temp = '此任务已存在，任务名称：'+data.taskName;
						                	   var temp1 = '\n\n1.如果任务正在执行，请耐心等待执行结果。';
						                	   var temp2 = '\n2.如果任务执行完毕且成功，点击查看按钮直接查看数据。';
						                	   var temp3 = '\n3.如果查询结果不是最新数据，点击刷新按钮，重新执行。';
						                	   alert(temp+temp1+temp2+temp3);
						                   }else if(data.msg == 3){
						                	   alert('执行频繁，请：'+data.taskInterval+"分钟后再试！");
						                   }else if(data.msg == 4){
						            	       alert('执行任务失败，请稍后再试！');
						                   }
						               }
						           });
							});
							
							var trTemp = $(".parm",tempTable).first().clone(true);
							$(".parm",tempTable).first().remove();
							$("#model",tempTable).change(function(){
								var value = $(this).val();
								var snid = $("input[name='snid']").val();
								var gameId = $("input[name='gameId']").val();
								$.ajax({
									  type: "POST",
									  url: "/panel_bi/customTask/getTempPram.ac?templateId="+value+"&snid="+snid+"&gameId="+gameId,
									  dataType: "json",
									  success: function(data){
										  
										  $(".parm",tempTable).remove();
										  var tr = null;
										  var laydateArr = new Array();
										  $.each(data.params,function(i,n){
											  if(i % 2 == 0){
												  if(tr != null){
													  $("th.name",tr).first().remove();
													  $("td.value",tr).first().remove();
												  }
												  
												  tr = trTemp.clone(true);
												  tempTable.append(tr);
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
							
							$('.theme-popover-mask').fadeIn(100);
							$('.theme-popover').slideDown(200);
							
							$('.theme-poptit .close').click(function(){
								$('#tempDesc').hide();
								$('.theme-popover-mask').fadeOut(100);
								$('.theme-popover').slideUp(200);
							})
					  }
				});
					
					
					
					
			});
			
			
		},
		sx:function(){
			 jq_table.draw(false);
		},
//		redict:function(view){
//			window.location.href='/panel_bi/customTask/toTask.ac?view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/customTask/toTask.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		queryData:function(){
			
			$('#dt_ajax_wrapper').remove();
			
			var table = $(".template_cache .ajax_table").clone(true);
			table.attr("id", "dt_ajax");
			$('#data').append(table);
			var selected = [];
		   jq_table =$('#dt_ajax').DataTable({
				"processing": true,
		        "serverSide": true,
		        ajax: {
				          data: function(d){
					        	  d.gameId=$("input[name='gameId']").val();
					        	  d.snid=$("input[name='snid']").val();
					      },
						  type: "POST",
						  url:"/panel_bi/customTask/getTaskList.ac"
		              },
              "pageLength": 5,
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
			    "ordering": false, //排序功能
			    "order": [[ 0, "asc" ]],
			    rowCallback:function(row, data) {
                    if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                        $(row).addClass('highlight');
                    }
                },
			    columns: [
	                      {"data": "id","title":"ID","defaultContent": ""},
	                      {"data": "taskName","title":"任务名称","defaultContent": ""},
	                      //{"data": "taskCode","title":"任务代码","defaultContent": ""},
	                      {"data": "executeTime","title":"执行时间","defaultContent":""},
	                      {"data": "endTime","title":"结束时间","defaultContent":""},
	                      {"data": "status","title":"执行状态","defaultContent":""},
	                      {"data": "recordCount","title":"记录数","defaultContent":""},
	                      {"data": "executeUserName","title":"执行人","defaultContent":""},
	                      {"data":null, "title":"操作","defaultContent": ""}
	                  ],
	                  columnDefs: [
	                               {
	                            	   targets: 1,
	                            	   render: function (a, b, v, d) {
	                            		   if(v.taskName.length > 50){
	                            			  return '<span title='+v.taskName+'>'+v.taskName.substring(0,50)+'...</span>';
	                            		   }else{
	                            			   return v.taskName;
	                            		   }
	                            	   }
	                               },
	                               {
	                            	   targets: 2,
	                            	   render: function (a, b, v, d) {
	                            		   return $.date.getDateSecond(new Date(v.executeTime));
	                            	   }
	                               },
	                               {
	                            	   targets: 3,
	                            	   render: function (a, b, v, d) {
	                            		   return v.endTime == null ? '' : $.date.getDateSecond(new Date(v.endTime));
	                            	   }
	                               },
	                               {
	  		                    	 targets: 4,
	  		                    	 render: function (a, b, v, d) {
	  		                    		 return v.status == 1 ? "执行成功" : v.status == 0 ? "执行失败" : "执行中";
	  		                    	 }
	                               },
	                               {
	                            	   targets: 7,
	                            	   render: function (a, b, v, d) {
	                            		   if(v.status == 1){
	                            			   //if(v.recordCount <= 0){
	                            				//   return "<button class='add-btn' style='width:100px;' type='button' val='"+v.id+"' >刷新</button>"
	                            			  // }else{
	                            			   		var downloadCsv = "<button id='downloadCsv' class='downloadCsv' style='width:50px;' type='button' val='"+v.id+"' rec="+v.recordCount+"  title='点击下载此报表的已有的查询结果'>下载</button>";
	                            			   		var query = "<button class='query-btn' style='width:50px;' type='button' val='"+v.id+"' rec="+v.recordCount+"  title='点击查看此报表的已有的查询结果，速度快'>查看</button>";
	                            			   		var flu = "<button class='add-btn' style='width:50px;' type='button' val='"+v.id+"' title='点击刷新获取最新数据，需等待且需间隔一定时间不能频繁执行'>刷新</button>";
	                            				    return v.recordCount>1000?downloadCsv + flu:query+flu;
	                            			  // }
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
				
				$("#dt_ajax tbody").on("click","#downloadCsv",function(){
					if($(this).attr("rec") <= 0){
						$(".result").html('<font style="font-weight: bold">无查询结果<br></font>');
						$(".result").show();
						$("#value_data").hide();
						return;
					}
					var snid = $("input[name='snid']").val();
					var gameId = $("input[name='gameId']").val();
					var taskId = $(this).attr("val");
					var url = "/panel_bi/customTask/downloadCsv.ac?taskId="+taskId+"&snid="+snid+"&gameId="+gameId;
					$("#downForm").prop("action", url);
					window.btnTimeout1=setInterval(function(){
						$.gameUtil.btnTimeout($('#downloadCsv'),window.btnTimeout1);
					  },1000);
			        $("#downForm").submit();
				});
				
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
					var snid = $("input[name='snid']").val();
					var gameId = $("input[name='gameId']").val();
			           $.ajax({
			               "url":"/panel_bi/customTask/getTaskDataCache.ac?taskId="+taskId+"&snid="+snid+"&gameId="+gameId,
			               "type":"post",
			               "error":function(){
			                   alert("服务器未正常响应，请重试");
			               },
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
									    "searching": true, //过滤功能
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
					var taskId = $(this).attr("val");
					var snid = $("input[name='snid']").val();
					var gameId = $("input[name='gameId']").val();
			           $.ajax({
			               "url":"/panel_bi/customTask/flushTask.ac?taskId="+taskId+"&snid="+snid+"&gameId="+gameId,
			               "type":"post",
			               "error":function(){
			            	   $(this).show();
			                   alert("服务器未正常响应，请重试");
			               },
			               "success":function(data){
			            	   $(this).show();
			            	   if(data.msg==0){
			            		   $.customTask.queryData();
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