$(function(){
	
	$(document).ready(function() {
	    $.economyDimension.init();
	    $.timeZone.showTimeZone();
	});
	
	$.economyDimension={
		init:function(){
			$.economyDimension.initEvent();
		},
		initEvent:function(){
			
			var file=document.querySelector("#file input[type='file']"); 
			var text=document.querySelector("#file input[type='text']"); 
			file.addEventListener("change",assign,false); 
			function assign(){ 
			text.value=file.value; 
			} 
			
			$("#query").click(function(){
				$.economyDimension.queryData();
		    });
			
			$("#upload").click(function(){
				$.economyDimension.upload();
			});
			
			$("#muban").click(function(){
				//$.economyDimension.muban();
			});
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.economyDimension.redict($(n).attr("id"));
				});
			});
			
		},
//		redict:function(view){
//			window.location.href='/panel_bi/economy/toGameEconomy.ac?id='+$("input[name='gamesId']").val()+'&view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/economy/toGameEconomy.ac?id='+$("input[name='gamesId']").val()+'&view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		checkParam:function(){

			  var ps={
					 view:$('#view').val(),
					 file:$("#uploadFile").val(),
					 templeteType:$("#templeteType").val(),
					 snid:$("input[name='snid']").val(),
					 gameId:$("input[name='gameId']").val()
				};
		      
			  if(ps.templeteType == null || ps.templeteType == ''){
				  return null;
			  }
			  if(ps.file == null || ps.file == ''
				  || ps.file == '未选择文件'){
				  alert('请选择要上传的文件');
				  return null;
			  }
		      
		      return ps;
		},
		upload:function(){
			var ps = $.economyDimension.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajaxFileUpload({
				  url:"/panel_bi/economy/upload_data.ac?filePath="+ps.file+"&templeteType="+ps.templeteType+"&snid="+ps.snid+"&gameId="+ps.gameId,
				  secureuri:false,                       //是否启用安全提交,默认为false
			      fileElementId:'uploadFile',           //文件选择框的id属性
			      dataType:'text',                       //服务器返回的格式,可以是json或xml等
			      success:function(data, status){        //服务器响应成功时的处理函数
			            $('.ajax_loading').hide(); //上传完成隐藏
			        },
			        error:function(data, status, e){ //服务器响应失败时的处理函数
			            $('.ajax_loading').hide(); //上传完成隐藏
			            alert('文件上传失败，请重试！！');
			        }
				});
			
			$('.ajax_loading').show();
			$("#upload")[0].disabled = true;
			window.win_up_endMark=setInterval(function(){
	              $.economyDimension.getUpEndMark();
	            },500);
		},
		getUpEndMark:function(){
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/economy/getUpLoadEndMark.ac",
				  success: function(data){
					  if(data.isEconomyUpLoadEnd == '1'){
						  $('.ajax_loading').hide(); //下载完成隐藏
						  $("#upload")[0].disabled = false;
						  if(window.win_up_endMark!=null){
								window.clearInterval(window.win_up_endMark);    
						   }
						  $("#file input[type='text']").val('未选择文件');
						  alert('上传成功!\n\n可点击【数据查询】查看。');
					     }
					  },
					  error:function(){
						  $('.ajax_loading').hide(); //下载完成隐藏
						  $("#upload")[0].disabled = false;
						  if(window.win_up_endMark!=null){
								window.clearInterval(window.win_up_endMark);    
						   }
						  alert('请求错误！');
					  }
				});
			
		},
		queryData:function(){
			
			$('#dt_ajax_wrapper').remove();
			var ps = {
					 view:$('#view').val(),
					 file:$("#uploadFile").val(),
					 templeteType:$("#templeteType").val(),
					 snid:$("input[name='snid']").val(),
					 gameId:$("input[name='gameId']").val()
				};;
			var table = $(".template_cache .ajax_table").clone(true);
			table.attr("id", "dt_ajax");
			$('#data').append(table);
			
			var jq_table = $('#dt_ajax').dataTable( {
		        "processing": true,
		        "serverSide": true,
		        ajax: {
						  type: "POST",
						  data: function(d){
				        	  d.snid=ps.snid;
				        	  d.gameId=ps.gameId;
				          },
						  url:"/panel_bi/economy/queryDimensionData.ac"
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
		                      {"data": "consumeCode","title":"消费代码","defaultContent": ""},
		                      {"data": "consumeName","title":"消费名称","defaultContent":""},
		                      {"data":null, "title":"操作","defaultContent": "<button class='edit-btn' type='button'>编辑</button><button class='del-btn' type='button'>删除</button>"}
		                  ]
		    });

			$('#dt_ajax').removeClass().addClass('table table-striped');
			
			$("#dt_ajax tbody").on("click",".edit-btn",function(){
		           var tds=$(this).parents("tr").children();
		           $.each(tds, function(i,val){
		               var jqob=$(val);
		               if(i < 1 || jqob.has('button').length ){return true;}//跳过第1项 序号,按钮
		               var txt=jqob.text();
		               var put=$("<input type='text'>");
		               put.val(txt);
		               jqob.html(put);
		           });
		           $(this).html("保存");
		           $(this).toggleClass("edit-btn");
		           $(this).toggleClass("save-btn");
		       });
			
			$("#dt_ajax tbody").on("click",".save-btn",function(){
		           var row = jq_table.api().row($(this).parents("tr"));
		           var tds = $(this).parents("tr").children();
		           $.each(tds, function(i,val){
		               var jqob=$(val);
		               //把input变为字符串
		               if(!jqob.has('button').length){
		                   var txt=jqob.children("input").val();
		                   jqob.html(txt);
		                   jq_table.api().cell(jqob).data(txt);//修改DataTables对象的数据
		               }
		           });
		           var data=row.data();
		           $.ajax({
		               "url":"/panel_bi/economy/saveDimensionData.ac",
		               "data":data,
		               "type":"post",
		               "error":function(){
		                   alert("服务器未正常响应，请重试");
		               },
		               "success":function(data){
		            	   if(data.msg=='2'){
		                	   alert('修改成功！');
		                   }else if(data.msg == '1'){
		                	   alert('内容不能为空，请重新输入！');
		                   }
		               }
		           });
		           $(this).html("编辑");
		           $(this).toggleClass("edit-btn");
		           $(this).toggleClass("save-btn");
		       });
			
			$("#dt_ajax").on("click",".add-btn",function(){
				var trs = $("#dt_ajax tbody").children();
				var trClass = 'odd';
				if((trs.length + 1) % 2 == 0){
					trClass = 'even';
				}
				
				table.prepend('<tr role="row" calss="'+trClass+'"><td><input type="text"></td><td><input type="text"></td><td><button class="add-save-btn" type="button">保存</button><button class="del-btn" type="button">删除</button></td></tr>');
		       });
			
			$("#dt_ajax tbody").on("click",".add-save-btn",function(){
		           var tds = $(this).parents("tr").children();
		           var ps = new Array();
		           $.each(tds, function(i,val){
		               var jqob=$(val);
		               //把input变为字符串
		               if(!jqob.has('button').length){
		                   var txt=jqob.children("input").val();
		                   jqob.html(txt);
		                   ps[i] = txt;
		               }
		           });
		           
		           var data = {
		        			   consumeCode:ps[0],
		        			   consumeName:ps[1],
		        			   snid:$("input[name='snid']").val(),
		        			   gameId:$("input[name='gameId']").val()
		        	   };
		           
		           $.ajax({
		               "url":"/panel_bi/economy/saveDimensionData.ac",
		               "data":$.param(data),
		               "type":"post",
		               "error":function(){
		                   alert("服务器未正常响应，请重试");
		               },
		               "success":function(data){
		            	   if(data.msg=='2'){
		                	   jq_table.api().draw(false);
		                	   alert('保存成功！');
		                   }else if(data.msg == '1'){
		                	   alert('内容不能为空，请重新输入！');
		                   }
		               }
		           });
		           $(this).html("编辑");
		           $(this).removeClass().addClass("edit-btn");
		           
		           jq_table.api().draw(false);
		       });
			
			$("#dt_ajax tbody").on("click",".del-btn",function(){
				 var tds = $(this).parents("tr").children();
				 var ps = new Array();
		           $.each(tds, function(i,val){
		               var jqob=$(val);
		               //把input变为字符串
		               if(!jqob.has('button').length){
		                   var txt=jqob.text();
		                   ps[i] = txt;
		               }
		           });
		           
		           var data = {
		        			   consumeCode:ps[0],
		        			   consumeName:ps[1],
		        			   snid:$("input[name='snid']").val(),
		        			   gameId:$("input[name='gameId']").val()
		        	   };
		           $.ajax({
		               "url":"/panel_bi/economy/delDimensionData.ac",
		               "data":$.param(data),
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
			
			$('#dt_ajax .downBtn').click(function (){
		        $("#search").val($("#dt_ajax_filter .search").val());
		        $(this)[0].disabled = true;
		        $("#economyForm").submit();
		    });
		}
	};
});
	                    