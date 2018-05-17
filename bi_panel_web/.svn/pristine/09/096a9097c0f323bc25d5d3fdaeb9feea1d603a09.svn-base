$(function(){
	
	$(document).ready(function() {
	    $.adShortUrl.init();
	    $.timeZone.showTimeZone();
	});
	
	var jq_table = null;
	$.adShortUrl={
		init:function(){
			$.adShortUrl.initEvent();
			$.adShortUrl.queryData();
		},
		initEvent:function(){
			
			$(".batch-add-btn").click(function(){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/adTracking/toAdShortAdd.ac?snid='+snid+'&gameId='+gameId;
		    });
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.adShortUrl.redict($(n).attr("id"));
				});
			});
			
			$(".caption").on("click",".add-btn",function(){
				$("#qi").hide();
				$(".theme-popover").css('width',"700px");
				$(".theme-popover").css('height',"380px");
				$(".theme-popover").css('margin','-180px 0 0 -300px');
				$.adShortUrl.updateShortUrl(0,'添加数据','添加平台投放','');
			});
			
			$(".caption").on("click",".queryInstall-btn",function(){
				$("#qi").show();
				$("#qidfa").val('');
				$("#suid").val('');
				
				$("#addTable").html("");
				$(".theme-popover .add-btn").unbind();
				$(".theme-popover .add-btn").text("查询激活");
				$("#gameTypeName").text("查询激活信息");
				
				$(".theme-popover").css('width',"1000px");
				$(".theme-popover").css('height',"550px");
				$(".theme-popover").css('margin','-250px 0 0 -400px');
				
				$(".theme-popover .add-btn").click(function(){
					var ps_data = {
						idfa:$("#qidfa").val(),
						suid:$("#suid").val(),
						snid:$("input[name='snid']").val(),
						gameId:$("input[name='gameId']").val()
					};
					
					if(ps_data.idfa == null || ps_data.idfa.trim() == ''){
						alert('idfa不能为空');
						return;
					}
					if(ps_data.suid == null || ps_data.suid.trim() == ''){
						alert('suid不能为空');
						return;
					}
					
					var arr  = ps_data.idfa.match(/,/g);
					if(arr!=null && arr.length>9){
						alert('idfa不能超过10个！');
						return;
					}
					
					$.ajax({
			               "url":"/panel_bi/adTracking/queryInstalls.ac",
			               "data":$.param(ps_data),
			               "type":"post",
			               "error":function(){
			                   alert("服务器未正常响应，请重试");
			               },
			               "success":function(data){
			            	   if(data.msg=='2'){
			            		   var installs = data.installs;
			            		   var table = $(".template_cache .queryInstallTemp").clone(true);
			       					table.attr("id", "dt");
			       					$("#addTable").html(table);
//			       					$("#addTable").addClass("detail-table");
			       				
			       				$.each(installs,function(i,v){
			       					var trTemp = $("tbody tr", table).first().clone(true);
			       					$("td.idfa", trTemp).text(v.ifa);
			       					$("td.actTime", trTemp).text($.adShortUrl.getLocalTime(v.actTime));
			       					$("td.installTime", trTemp).text(v.gameInstalls == null ? '-' : v.gameInstalls.installTime);
			       					$("td.callBackTime", trTemp).text(v.adTrackingCallback == null ? '-' : $.adShortUrl.getLocalTime(v.adTrackingCallback.callbackTime));
			       					
			       					if(v.adTrackingCallback == null){
			       						$("td.callBackStatus", trTemp).text('-');
			       					}else if(v.adTrackingCallback.status == '0'){
			       						$("td.callBackStatus", trTemp).text('匹配成功');
			       					}else if(v.adTrackingCallback.status == '1'){
			       						$("td.callBackStatus", trTemp).text('回调中');
			       					}else if(v.adTrackingCallback.status == '2'){
			       						$("td.callBackStatus", trTemp).text('回调成功');
			       					}else if(v.adTrackingCallback.status == '3'){
			       						$("td.callBackStatus", trTemp).text('回调失败');
			       					}

			       					$('tbody', table).append(trTemp);
			       				});
			       				
			       				table.removeClass("queryInstallTemp").addClass("table table-striped");
			       				$("tbody tr", table).first().remove();
			       				
			       				$('#dt tbody tr').click(function (){
			       				     $(this).toggleClass('highlight');
			       				 });
			                   }else if(data.msg == '1'){
			                	   alert('内容不能为空，请重新输入！');
			                   }else if(data.msg == '-1'){
			                	   alert('此渠道不存在！');
			                   }else if(data.msg == '-2'){
			                	   alert('拉取信息出错！');
			                   }
			               }
			           });
				});
				
				$('.theme-popover-mask').fadeIn(100);
				$('.theme-popover').slideDown(200);
			});
			
			$('.theme-poptit .close').click(function(){
				$('.theme-popover-mask').fadeOut(100);
				$('.theme-popover').slideUp(200);
			})
			
			$('.caption .query-btn').click(function(){
				$.adShortUrl.queryData();
			});
		},
//		redict:function(view){
//			window.location.href='/panel_bi/adTracking/toAdTracking.ac?view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/adTracking/toAdTracking.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		getLocalTime:function(nS) {     
			   var date = new Date(parseInt(nS));
			   return $.date.getDateSecond(date);
		    },
		updateShortUrl:function(isAdd,buttonName,header,dataId){
			    var tempTable = null;
			    if(isAdd == 0){
			    	tempTable = $(".template_cache .addDataTable_a").clone(true);
			    }else{
			    	tempTable = $(".template_cache .updateDataTable_a").clone(true)
			    }
				$("#addTable").html(tempTable);
				
				$(".theme-popover .add-btn").unbind();
				$(".theme-popover .add-btn").text(buttonName);
				$("#gameTypeName").text(header);
				
				$("#platformName").change(function(){
					var ps_data={
							platformName:$("#platformName").val(),
							snid:$("input[name='snid']").val(),
							gameId:$("input[name='gameId']").val()
					};
					
					$.ajax({
						"url":"/panel_bi/adTracking/getShortUrlByPlatform.ac",
		                "data": $.param(ps_data),
		                "type": "post",
						"dataType": "json",
						success: function(msg){
							if(msg.data!=null){
								var adShortUrl = msg.data;
								$("#appUrl").val(adShortUrl.appUrl);
								$("#encryptKey").val(adShortUrl.encryptKey);
								$("#sign").val(adShortUrl.sign);
								$("#matchRule").val(adShortUrl.matchRule);
								$("#redirect").val(adShortUrl.redirect);
								$("#terminalType").val(adShortUrl.terminalType);
								$("#systemType").val(adShortUrl.systemType);
							}else{
								$("#appUrl").val('');
								$("#encryptKey").val('');
								$("#sign").val('');
							}
						}
					});
				});
				
				$(".theme-popover .add-btn").click(function(){
					var ps_data={
							platformName:isAdd == 0 ? $("#platformName").val() : $("#platformModify").text(),
							adPlace:$("#adPlace",tempTable).val(),
							appUrl:$("#appUrl",tempTable).val(),
							sign:$("#sign",tempTable).val(),
							status:$("#status",tempTable).val(),
							redirect:$("#redirect",tempTable).val(),
							matchRule:$("#matchRule",tempTable).val(),
							encryptKey:$("#encryptKey",tempTable).val(),
							terminalType:$("#terminalType",tempTable).val(),
							systemType:$("#systemType",tempTable).val(),
							id:dataId,
							snid:$("input[name='snid']").val(),
							gameId:$("input[name='gameId']").val()
					};
					
					if($.adShortUrl.isEmpty(ps_data.platformName)
							|| $.adShortUrl.isEmpty(ps_data.appUrl)
							|| $.adShortUrl.isEmpty(ps_data.sign)
							|| $.adShortUrl.isEmpty(ps_data.redirect)
							|| $.adShortUrl.isEmpty(ps_data.adPlace)
							|| $.adShortUrl.isEmpty(ps_data.status)
							|| $.adShortUrl.isEmpty(ps_data.matchRule)
							|| $.adShortUrl.isEmpty(ps_data.encryptKey)
							|| $.adShortUrl.isEmpty(ps_data.systemType)
							|| $.adShortUrl.isEmpty(ps_data.terminalType)){
						    alert('内容不能为空，请重新输入！');//内容不能为空，请重新输入！
						    return false;
						}
					
					if(!ps_data.adPlace.match(/^[A-Za-z0-9]+$/)){
			        	   alert('广告位只能由英文字母与数字组成！');
			        	   return false;
			           }
					
					if(ps_data.terminalType == 0){
						ps_data.systemType = 0;
					}else if(ps_data.terminalType == 1 && ps_data.systemType == -1){
						ps_data.systemType = 0;
					}
					
					$.ajax({
			               "url":"/panel_bi/adTracking/saveShortUrl.ac",
			               "data":$.param(ps_data),
			               "type":"post",
			               "error":function(){
			                   alert("服务器未正常响应，请重试");
			               },
			               "success":function(data){
			            	   if(data.msg=='2'){
			                	   alert('保存成功！');
			                	   $('.theme-popover-mask').fadeOut(100);
			   					   $('.theme-popover').slideUp(200);
			                	   jq_table.api().draw(false);
			                   }else if(data.msg == '1'){
			                	   alert('内容不能为空，请重新输入！');
			                   }
			               }
			           });
				});
				
				$(".pname",tempTable).change(function(){
					$(".pcode",tempTable).text($(".pname",tempTable).val().split('_')[1] + '_');
				});
				
				$('.theme-popover-mask').fadeIn(100);
				$('.theme-popover').slideDown(200);
				
				return tempTable;
		},
		isEmpty:function(str){
			return str == null || str == '';
		},
		queryData:function(){
			
			$('#dt_ajax_wrapper').remove();
			var ps = {
					snid:$("input[name='snid']").val(),
					gameId:$("input[name='gameId']").val()
			};
			var table = $(".template_cache .ajax_table").clone(true);
			table.attr("id", "dt_ajax");
			$('#data').append(table);
			
			jq_table = $('#dt_ajax').dataTable( {
		        "processing": true,
		        "serverSide": true,
		        ajax: {
						  type: "POST",
						  data: function(d){
				        	  d.gameId=ps.gameId;
				        	  d.snid=ps.snid;
				          },
						  url:"/panel_bi/adTracking/getShortUrl.ac"
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
			    "ordering": false, //排序功能
			    "order": [[ 0, "asc" ]],
		        columns: [
		                      {"data": "id","title":"ID","defaultContent": ""},
		                      {"data": "platformName","title":"投放平台","defaultContent": ""},
		                      {"data": "adPlace","title":"广告位","defaultContent": ""},
		                      {"data": null,"title":"短链接","defaultContent":""},
		                      {"data": "matchRule","title":"匹配规则","defaultContent":""},
		                      {"data": "redirect","title":"是否跳转","defaultContent":""},
		                      {"data": "appUrl","title":"平台地址","defaultContent":""},
		                      {"data": "sign","title":"签名","defaultContent":""},
		                      {"data": "status","title":"是否有效","defaultContent":""},
		                      {"data": "terminalType","title":"设备类型","defaultContent":""},
		                      {"data": "systemType","title":"系统类型","defaultContent":""},
		                      {"data": "encryptKey","title":"签名秘钥","defaultContent":""},
		                      {"data":null, "title":"操作","defaultContent": "<button class='edit-btn btn btn-default btn-sm' type='button'>编辑</button> <button class='del-btn btn btn-default btn-sm' type='button'>删除</button>"}
		                  ],
		        columnDefs: [
		                     {
		                    	 visible:false,
		                    	 targets: [7,9,10,11],
		                     },
                               {
                            	   targets: 2,
                            	   render: function (a, b, v, d) {
                            		   return v.platformCode+'_'+v.adPlace;
                            	   }
                               },
                               {
                         		  targets: 3,
                         		  render: function (a, b, v, d) {
                         			   return 'http://tracking.hoolai.com/tracking/'+v.id+'/';
                              }
                               },
                               {
                            	   targets: 6,
                            	   render: function (a, b, v, d) {
                            		   return '<a href="'+v.appUrl+'" target="_black">'+v.appUrl+'</a>';
                            	   }
                               },
                               {
                            	   targets: 5,
                            	   render: function (a, b, v, d) {
                            		   return v.redirect == 1 ? "是" : "否";
                            	   }
                               },
                               {
                            	   targets: 4,
                            	   render: function (a, b, v, d) {
                            		   return v.matchRule == 1 ? "ip" : "idfa";
                            	   }
                               },
                               {
                            	   targets: 8,
                            	   render: function (a, b, v, d) {
                            		   return v.status == 1 ? "有效" : "无效";
                            	   }
                               },
		                     ]
		    });

			$('#dt_ajax').removeClass().addClass('table table-striped');
			
			$("#dt_ajax tbody").on("click",".edit-btn",function(){
					var row = jq_table.api().row($(this).parents("tr"));
			        var data=row.data();
					
					var tempTable = $.adShortUrl.updateShortUrl(1,'修改数据','修改平台投放',data.id);
					
					$("#platformModify",tempTable).text(data.platformName);
					$("#pcode",tempTable).text(data.platformCode+'_');
					$("#adPlace",tempTable).val(data.adPlace);
					$("#appUrl",tempTable).val(data.appUrl);
					$("#sign",tempTable).val(data.sign);
					$("#status",tempTable).val(data.status);
					$("#redirect",tempTable).val(data.redirect);
					$("#matchRule",tempTable).val(data.matchRule);
					$("#terminalType",tempTable).val(data.terminalType);
					$("#systemType",tempTable).val(data.terminalType == 0 ? -1 : data.systemType);
					$("#encryptKey",tempTable).val(data.encryptKey);
		       });
			
			$("#dt_ajax tbody").on("click",".del-btn",function(){
				 var row = jq_table.api().row($(this).parents("tr"));
		           var tds = $(this).parents("tr").children();
		           $.each(tds, function(i,val){
		               var jqob=$(val);
		               //把input变为字符串
		               if(!jqob.has('button').length){
			                   jq_table.api().cell(jqob).data(jqob.text());//修改DataTables对象的数据
		            	   }
		           });
		           
		           var rowData = row.data();
		           $.ajax({
		               "url":"/panel_bi/adTracking/delShortUrl.ac",
		               "data":$.param(rowData),
		               "type":"post",
		               "error":function(){
		                   alert("服务器未正常响应，请重试");
		               },
		               "success":function(data){
		                   if(data.msg=='2'){
		                	   jq_table.api().draw(false);
		                	   alert('删除成功！');
		                   }else if(data.msg == '1'){
		                	   alert('删除的道具不存在！');
		                   }else if(data.msg == '0'){
		                	   alert('删除的消费点不存在！');
		                   }
		               }
		           });
		      });
		}
	};
});

	                    