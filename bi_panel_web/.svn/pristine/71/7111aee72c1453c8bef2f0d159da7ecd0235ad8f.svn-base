$(function(){
	
	$(document).ready(function() {
	    $.authManageCreative.init();
	    $.timeZone.showTimeZone();
	});
	
	var isOpChange = false;// 是否进行了渠道分配操作
	$.authManageCreative={
		init:function(){
			$.authManageCreative.initEvent();
			$.authManageCreative.queryData();
		},
		initEvent:function(){
			
			$("#query").click(function(){
				$.authManageCreative.queryData();
		    });
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.authManageCreative.redict($(n).attr("id"));
				});
			});
			
			$('.theme-poptit .close').click(function(){
				
				if(isOpChange){
					if(confirm('直接点击关闭按钮，你的操作将不会保存！\n\n保存操作请点击保存按钮！\n\n你确定要关闭吗？\n')){
						isOpChange = false;
						$('.theme-popover-mask').fadeOut(100);
						$('.theme-popover').slideUp(200);
					}
				}else{
					$('.theme-popover-mask').fadeOut(100);
					$('.theme-popover').slideUp(200);
				}
			})
			
			
		},
//		redict:function(view){
//			window.location.href='/panel_bi/cpaCreative/toCPACreative.ac?view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/cpaCreative/toCPACreative.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		queryData:function(){
		 var data = {
				 loginName:$("#loginName").val(),
				 email:$("#email").val(),
				 snid:$("input[name='snid']").val(),
				 gameId:$("input[name='gameId']").val()
		 };
           $.ajax({
               "url":"/panel_bi/cpaCreative/queryUserList.ac",
               "data":data,
               "type":"post",
               "error":function(){
                   alert("服务器未正常响应，请重试");
               },
               "success":function(data){
            	   var selected = [];
            	   $('#dt_ajax_wrapper').remove();
       			
       			   var table = $(".template_cache .view_table").clone(true);
       			   table.attr("id", "dt_ajax");
       			   $('#data').append(table);
       			   
       			   $.each(data.userList,function(i,v){
       				   	var trTemp = $("tbody tr", table).first().clone(true);
	       				$("td.loginName", trTemp).text(v.loginName);
						$("td.email", trTemp).text(v.email);
						$("td.realName", trTemp).text(v.realName);
						$("td.telepone", trTemp).text(v.telepone);
						$(".auth-btn", trTemp).attr('oid',v.id);
						
						$('tbody', table).append(trTemp);
       			   });
	       			$("tbody tr", table).first().remove();
	
	    			$('#dt_ajax').dataTable( {
	    		        "pageLength": 10,
	    			    "language":{
	    			    	"url": "/dataTables/chinese.json"
	    			    },
	    		        "deferRender": true,
	    			    "sPaginationType" : "full_numbers",
	    		        "dom" : '<"clear"><"top">rt<"bottom"ip><"clear">' ,
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
	    		       columnDefs :[
	    								{
	    									 orderable:false,
	    									 targets: [4]
	    								}
	    		                    ]        
	    		    });
	    			
	    			$(".auth-btn",table).each(function(i,n){
						$(n).click(function(){
							$.authManageCreative.sourceAuth($(n).attr("oid"));
						});
					});
	    			
	    			$('#dt_ajax').removeClass().addClass('table table-striped');
	    			$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
               }
           });
		},
		sourceAuth:function(userid){
			var data={
				userid:	userid,
				snid:$("input[name='snid']").val(),
				gameId:$("input[name='gameId']").val()
			};
			$.ajax({
	               "url":"/panel_bi/cpaCreative/querySource.ac",
	               "type":"post",
	               "data":data,
	               "error":function(){
	                   alert("服务器未正常响应，请重试");
	               },
	               "success":function(data){
	            	   if(data.msg == '1'){
	                	   alert('内容不能为空，请重新输入！');
	                	   return;
	                   }
	            	   var sourceop = new Array();
	       			
	            	   $('#dt_had_wrapper').remove();
	       			   var hadTable = $(".template_cache .hadTable").clone(true);
	       			   hadTable.attr("id", "dt_had");
	       			   $('#hadTable').append(hadTable);
	       			   
	       			   var had_jq_table = $.authManageCreative.createTable(data.hadDimensions,hadTable,'dt_had');
	       			   
	       			   $('#dt_nothad_wrapper').remove();
	       			   var needTable = $(".template_cache .needTable").clone(true);
	       			   needTable.attr("id", "dt_nothad");
	       			   $('#needTable').append(needTable);
	       			   
	       			   var nothad_jq_table = $.authManageCreative.createTable(data.notHadDimensions,needTable,'dt_nothad');
	       			   
		       			$(".del-btn",hadTable).each(function(i,n){
		    				$(n).click(del);
		    			});
		    			$(".add-btn",needTable).each(function(i,n){
		    				$(n).click(add);
		    			});
		    			function del(){
	    					var tr = $(this).parents("tr");
	    				    var tds = tr.children();
    			            $.each(tds, function(i,td){
    			               var jqob=$(td);
    			               //把input变为字符串
    			               if(jqob.has('button').length){
    			            	   var but=jqob.children("button");
    			            	   but.removeClass().addClass('add-btn');
    			            	   but.text('增加');
    			            	   had_jq_table.cell(jqob).data(jqob.html());
    			            	   sourceop.push(but.attr('sid')+":del");
    			            	   isOpChange = true;
    			               }
    			           });
	    			        var row = had_jq_table.row(tr); 
	    					var node = nothad_jq_table.row.add(row.data()).draw(false).node();
	    					row.remove().draw();
	    					
	    					$(".add-btn",node).click(add);
	    				}
		    			function add(){
	    					var tr = $(this).parents("tr");
	    				    var tds = tr.children();
				            $.each(tds, function(i,td){
				               var jqob=$(td);
				               //把input变为字符串
				               if(jqob.has('button').length){
				            	   var but=jqob.children("button");
				            	   but.removeClass().addClass('del-btn');
				            	   but.text('移除');
				            	   nothad_jq_table.cell(jqob).data(jqob.html());
				            	   sourceop.push(but.attr('sid')+":add");
				            	   isOpChange = true;
				               }
				           });
	    			        var row = nothad_jq_table.row(tr);  
	    					var node = had_jq_table.row.add(row.data()).draw(false).node();
	    					row.remove().draw();
	    					$(".del-btn",node).click(del);
		    			}
		    			
		    		
	       			   
	       			window.style=setInterval(function(){
                       if($("#dt_had").length > 0){
                    	   $("#dt_had").css('width','100%');
                    	   window.clearInterval(window.style);    
                       }
	  	            },50);
	       			window.style1=setInterval(function(){
	       				if($("#dt_nothad").length > 0){
	       					$("#dt_nothad").css('width','100%');
	       					window.clearInterval(window.style1);    
	       				}
	       			},50);
	       			
	       			
	       			$(".theme-popover .save-btn").unbind();
	    			$(".theme-popover .save-btn").click(function(){
	    				var ps_data={
	    						sourceop:sourceop.join(','),
	    						userid:userid,
	    						snid:$("input[name='snid']").val(),
	    						gameId:$("input[name='gameId']").val()
	    				}
	    				
	    				$.ajax({
	    		               "url":"/panel_bi/cpaCreative/authManager.ac",
	    		               "data":$.param(ps_data),
	    		               "type":"post",
	    		               "error":function(){
	    		                   alert("服务器未正常响应，请重试");
	    		               },
	    		               "success":function(data){
	    		            	   if(data.msg=='2'){
	    		                	   alert('保存成功！');
	    		                	   isOpChange = false;
	    		                	   $('.theme-popover-mask').fadeOut(100);
	    		   					   $('.theme-popover').slideUp(200);
	    		                   }else if(data.msg == '1'){
	    		                	   alert('内容不能为空，请重新输入！');
	    		                   }
	    		               }
	    		           });
	    			});
	               }
	           });
			
			
			$('.theme-popover-mask').fadeIn(100);
			$('.theme-popover').slideDown(200);
	    },
	    createTable:function(dimensions,hadTable,dtName){
	    	$.each(dimensions,function(i,v){
				var trTemp = $("tbody tr", hadTable).first().clone(true);
				$("td.id", trTemp).text(v.id);
   				$("td.sourceCode", trTemp).text(v.sourceCode);
				$("td.sourceName", trTemp).text(v.sourceName);
				if(dtName == 'dt_had'){
					$(".del-btn", trTemp).attr('sid',v.id);
				}else{
					$(".add-btn", trTemp).attr('sid',v.id);
				}
				
				
				$('tbody', hadTable).append(trTemp);
			   });
   			$("tbody tr", hadTable).first().remove();

			var jqTable = $('#'+dtName+'').DataTable( {
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
			    "ordering": true, //排序功能
			    "order": [[ 1, "asc" ]],
		       columnDefs :[
								{
									 orderable:false,
									 targets: [2]
								},
								{
									visible:false,
									targets: [0]
								}
								
		                    ]      
		    });
			
			$('#'+dtName+'').removeClass().addClass('table table-striped');
			
			return jqTable;
	    }
	};
});
	                    