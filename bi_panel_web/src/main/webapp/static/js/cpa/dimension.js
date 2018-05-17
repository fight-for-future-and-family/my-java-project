$(function(){
	
	$(document).ready(function() {
	    $.cpaDimension.init();
	    $.timeZone.showTimeZone();
	});
	
	$.cpaDimension={
		init:function(){
			$.cpaDimension.initEvent();
		},
		initEvent:function(){
			
			var file=document.querySelector("#file input[type='file']"); 
			var text=document.querySelector("#file input[type='text']"); 
			file.addEventListener("change",assign,false); 
			function assign(){ 
			text.value=file.value; 
			} 
			
			$("#query").click(function(){
				$.cpaDimension.queryData();
		    });
			
			$("#upload").click(function(){
				$.cpaDimension.upload();
			});
			
			$("#muban").click(function(){
				//$.cpaDimension.muban();
			});
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.cpaDimension.redict($(n).attr("id"));
				});
			});
			
		},
//		redict:function(view){
//			window.location.href='/panel_bi/costPer/toCPA.ac?view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/costPer/toCPA.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		checkParam:function(){

			  var ps={
					 view:$('#view').val(),
					 file:$("#uploadFile").val(),
					 snid:$("input[name='snid']").val(),
					 gameId:$("input[name='gameId']").val()
				};
		      
			  if(ps.file == null || ps.file == ''
				  || ps.file == '未选择文件'){
				  alert('请选择要上传的文件');
				  return null;
			  }
		      
		      return ps;
		},
		upload:function(){
			var ps = $.cpaDimension.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajaxFileUpload({
				  url:"/panel_bi/costPer/upload_data.ac?filePath="+ps.file+"&snid="+ps.snid+"&gameId="+ps.gameId,
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
	              $.cpaDimension.getUpEndMark();
	            },500);
		},
		getUpEndMark:function(){
			var ps = $.cpaDimension.checkParam();
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/costPer/getUpLoadEndMark.ac",
				  data: $.param(ps),
				  success: function(data){
					  if(data.isCostPerUpLoadEnd == '1'){
						  $('.ajax_loading').hide(); //下载完成隐藏
						  $("#upload")[0].disabled = false;
						  $("#file input[type='text']").val('未选择文件');
						  alert('上传成功!\n\n可点击【数据管理】查看。');
						  if(window.win_up_endMark!=null){
								window.clearInterval(window.win_up_endMark);    
						   }
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
//			var ps = $.cpaDimension.checkParam();
//			if(ps == null){
//				return;
//			}
			var ps={
					 view:$('#view').val(),
					 file:$("#uploadFile").val(),
					 snid:$("input[name='snid']").val(),
					 gameId:$("input[name='gameId']").val()
				};
			
			var selected = [];
			$('#dt_ajax_wrapper').remove();
			
			var table = $(".template_cache .ajax_table").clone(true);
			table.attr("id", "dt_ajax");
			$('#data').append(table);
			
			var jq_table = $('#dt_ajax').dataTable( {
		        "processing": true,
		        "serverSide": true,
		        ajax: {
		        		  data: function(d){
		        			  d.gameId=ps.gameId;
				        	  d.snid=ps.snid;
		        		  },
						  type: "POST",
						  url:"/panel_bi/costPer/queryDimensionData.ac"
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
			    "order": [[ 1, "asc" ]],
			    rowCallback:function(row, data) {
                    if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                        $(row).addClass('highlight');
                    }
                },
		        columns: [
		                  	  {"data": "id","title":"ID","defaultContent": ""},
		                      {"data": "sourceCode","title":"渠道代码","defaultContent": ""},
		                      {"data": "sourceName","title":"渠道名称","defaultContent":""},
		                      {"data": "payRate","title":"支付额扣量率","defaultContent":""},
		                      {"data": "payUserRate","title":"支付人数扣量率","defaultContent":""},
		                      {"data": "payTimesRate","title":"支付次数扣量率","defaultContent":""},
		                      {"data":null, "title":"操作","defaultContent": ""}
		                  ],
		       columnDefs :[
								{
									 visible:false,
									 targets: [0]
								},
								{
									targets: 3,
									render: function (a, b, v, d) {
										if(v.payRate == null || v.payRate == 0){
											return '100%';
										}else{
											return v.payRate*100+'%';
										}
									}
								},
								{
									targets: 4,
									render: function (a, b, v, d) {
										if(v.payUserRate == null || v.payUserRate == 0){
											return '100%';
										}else{
											return v.payUserRate*100+'%';
										}
									}
								},
								{
									targets: 5,
									render: function (a, b, v, d) {
										if(v.payTimesRate == null || v.payTimesRate == 0){
											return '100%';
										}else{
											return v.payTimesRate*100+'%';
										}
									}
								},
								{
	                            	   targets: 6,
	                            	   render: function (a, b, v, d) {
	                            		   return "<button class='edit-btn btn btn-default btn-sm' type='button' oid='"+v.id+"'>编辑</button> <button class='del-btn btn btn-default btn-sm' type='button' oid='"+v.id+"'>删除</button>";
	                            	   }
	                            }
		                    
		                    ]        
		    });

			$('#dt_ajax').removeClass().addClass('table table-striped');
			$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
			
			$("#dt_ajax tbody").on("click",".edit-btn",function(){
		           var tds=$(this).parents("tr").children();
		           $.each(tds, function(i,val){
		               var jqob=$(val);
		               if(i < 1 || jqob.has('button').length ){return true;}//跳过第1项 序号,按钮
		               if(i>1){
		            	   var txt=jqob.text();
			               var put=$("<input type='text' style='width:80%'>");
			               put.val(txt.replace('%',''));
			               jqob.html(put);
			               jqob.append('%');
		               }else{
		            	   var txt=jqob.text();
			               var put=$("<input type='text'>");
			               put.val(txt);
			               jqob.html(put);
		               }
		               
		           });
		           $(this).html("保存");
		           $(this).toggleClass("edit-btn btn btn-default btn-sm");
		           $(this).toggleClass("save-btn btn btn-default btn-sm");
		       });
			
			$("#dt_ajax tbody").on("click",".save-btn",function(){
		           var row = jq_table.api().row($(this).parents("tr"));
		           var tds = $(this).parents("tr").children();
		           $.each(tds, function(i,val){
		               var jqob=$(val);
		               //把input变为字符串
		               if(!jqob.has('button').length){
		                   var txt=jqob.children("input").val();
		                   if(i>1){
		                	   jqob.html(txt+'%'); 
		                	   jq_table.api().cell(jqob).data(txt/100);//修改DataTables对象的数据
		                   }else{
		                	   jqob.html(txt);
		                	   jq_table.api().cell(jqob).data(txt);//修改DataTables对象的数据
		                   }
		                   
		                   
		               }
		           });
		           var data=row.data();
		           data.payRate = data.payRate;
		           data.payUserRate = data.payUserRate;
		           data.payTimesRate = data.payTimesRate;
		           $.ajax({
		               "url":"/panel_bi/costPer/saveDimensionData.ac",
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
		           $(this).toggleClass("edit-btn btn btn-default btn-sm");
		           $(this).toggleClass("save-btn btn btn-default btn-sm");
		       });
			
			$("#dt_ajax").on("click",".add-btn",function(){
				table.prepend($("tr",$('#add-table')).first().clone(true));
		       });
			
			$("#dt_ajax tbody").on("click",".add-save-btn",function(){
		           var tds = $(this).parents("tr").children();
		           var ps = new Array();
		           $.each(tds, function(i,val){
		               var jqob=$(val);
		               //把input变为字符串
		               if(!jqob.has('button').length){
		                   var txt=jqob.children("input").val();
		                   if(i>1){
		                	   jqob.html(txt+'%');
		                   }else{
		                	   jqob.html(txt);
		                   }
		                   ps[i] = txt;
		               }
		           });
		           
		           var data = {
		        		   sourceCode:ps[0],
		        		   sourceName:ps[1],
		        		   payRate:ps[2]/100,
		        		   payUserRate:ps[3]/100,
		        		   payTimesRate:ps[4]/100,
		        		   snid:$("input[name='snid']").val(),
		        		   gameId:$("input[name='gameId']").val()
		        	   };
		           
		           $.ajax({
		               "url":"/panel_bi/costPer/saveDimensionData.ac",
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
		           $(this).removeClass().addClass("edit-btn btn btn-default btn-sm");
		           
		           jq_table.api().draw(false);
		       });
			
			$("#dt_ajax tbody").on("click",".del-btn",function(){
				 var ps = new Array();
		          var data = {
		        		   id:$(this).attr("oid"),
		        		   snid:$("input[name='snid']").val(),
		        		   gameId:$("input[name='gameId']").val()
		        	   };
		           $.ajax({
		               "url":"/panel_bi/costPer/delDimensionData.ac",
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
		        $(this).attr('disabled',true);
		        $(this).val(2);
		        window.btnTimeout=setInterval(function(){
					$.gameUtil.btnTimeout($('#dt_ajax .downBt'),window.btnTimeout);
				  },1000);
		        $("#costPerForm").submit();
		    });
		}
	};
});
	                    