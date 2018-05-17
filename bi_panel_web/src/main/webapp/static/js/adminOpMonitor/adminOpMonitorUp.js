$(function(){
	
	$(document).ready(function() {
	    $.adminOpMonitorUp.init();
	    $.timeZone.showTimeZone();
	});
	
	$.adminOpMonitorUp={
		init:function(){
			$.adminOpMonitorUp.initEvent();
		},
		initEvent:function(){
			
			var file=document.querySelector("#file input[type='file']"); 
			var text=document.querySelector("#file input[type='text']"); 
			file.addEventListener("change",assign,false); 
			function assign(){ 
			text.value=file.value; 
			} 
			
			$("#query").click(function(){
				$.adminOpMonitorUp.queryData();
		    });
			
			$("#upload").click(function(){
				$.adminOpMonitorUp.upload();
			});
			
			$("#muban").click(function(){
				$.adminOpMonitorUp.muban();
			});
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.adminOpMonitorUp.redict($(n).attr("id"));
				});
			});
			
		},
		redict:function(view){
			window.location.href='/panel_bi/opMonitor/toOpMonitor.ac?&view='+view;
		},
		muban:function(){
			 $("#downType").val("template");
		        //$(this)[0].disabled = true;
		        $("#dataForm").submit();
		},
		checkParam:function(){

			  var ps={
					 view:$('#view').val(),
					 file:$("#uploadFile").val(),
					 date:$("#year").val()+"-"+$("#month").val()
				};
		      
			  if(ps.date == null || ps.date == ''){
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
			var ps = $.adminOpMonitorUp.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajaxFileUpload({
				  url:"/panel_bi/opMonitor/upload.ac?filePath="+ps.file+"&date="+ps.date,
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
	              $.adminOpMonitorUp.getUpEndMark();
	            },500);
		},
		getUpEndMark:function(){
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/opMonitor/getUpLoadEndMark.ac",
				  success: function(data){
					  if(data.isAdminOPMonitorUpLoadEnd == '1'){
						  $('.ajax_loading').hide(); //下载完成隐藏
						  $("#upload")[0].disabled = false;
						  if(window.win_up_endMark!=null){
								window.clearInterval(window.win_up_endMark);    
						   }
						  $("#file input[type='text']").val('未选择文件');
						  alert('上传成功!\n\n可点击【数据查询】查看。');
					     }else if(data.isAdminOPMonitorUpLoadEnd == '2'){
					    	 $('.ajax_loading').hide(); //下载完成隐藏
							  $("#upload")[0].disabled = false;
							  if(window.win_up_endMark!=null){
									window.clearInterval(window.win_up_endMark);    
							   }
							  alert('上传失败!\n\n请下载模板编辑保证格式正确，或者检查是否有数据格式错误，或者检查是否有数据为空等等。');
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
			
			var table = $(".template_cache .ajax_table").clone(true);
			table.attr("id", "dt_ajax");
			$('#data').append(table);
			
			var month = $("#year").val()+"-"+$("#month").val()
			var jq_table = $('#dt_ajax').dataTable( {
		        "processing": true,
		        "serverSide": true,
		        ajax: {
						  type: "POST",
						  data:function(d){
				        	  d.month=month;
				          },
						  url:"/panel_bi/opMonitor/queryOpMonitorUpData.ac"
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
			    "ordering": true, //排序功能
			    "order": [[ 1, "asc" ]],
		        columns: [
		                      {"data": "month","title":"日期","defaultContent": ""},
		                      {"data": "clientid","title":"服务器ID","defaultContent": ""},
		                      {"data": "opCode","title":"操作类型<br>代码","defaultContent":""},
		                      {"data": "opUserName","title":"操作人<br>名称","defaultContent":""},
		                      {"data": "itemId","title":"道具ID","defaultContent":""},
		                      {"data": "itemName","title":"道具名称","defaultContent":""},
		                      {"data": "itemNum","title":"道具数量","defaultContent":""},
		                      {"data": "beOperatedUserName","title":"被操作人<br>名称","defaultContent":""},
		                      {"data": "beOperatedUserRole","title":"被操作人<br>角色","defaultContent":""},
		                      {"data": "opDate","title":"操作日期","defaultContent":""},
		                      {"data": "applyUserName","title":"申请人<br>名称","defaultContent":""},
		                      {"data": null,"title":"用途","defaultContent":""}
		                  ],
		         columnDefs:[
							{
								   targets: 11,
								   render: function (a, b, v, d) {
									   if(v.useDesc.length > 20){
										  return '<span title='+v.useDesc+'>'+v.useDesc.substring(0,20)+'...</span>';
									   }else{
										   return v.useDesc;
									   }
								   }
							},
							{
								orderable:false,
								targets:[0,2,3,4,5,7,8,10,11]
							}
		                    ]
		    });

			$('#dt_ajax').removeClass().addClass('table table-striped');
			
			$('#dt_ajax .downBtn').click(function (){
		        $("#downType").val("data");
		        //$(this)[0].disabled = true;
		        $("#dataForm").submit();
		    });
		}
	};
});
	                    