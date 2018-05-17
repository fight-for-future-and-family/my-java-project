$(function(){
	
	$(document).ready(function() {
	    $.platforms.init();
	    $.timeZone.showTimeZone();
	});
	
	$.platforms={
		init:function(){
			$.platforms.initEvent();
			$.platforms.queryData();
		},
		initEvent:function(){
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.platforms.redict($(n).attr("id"));
				});
			});
			
			$("#caption").on("click",".add-btn",function(){
				window.location.href='/panel_manage/adManage/toadManage.ac?view=addPlatforms';
			});
			
			$(".query-btn",$('#caption')).click(function(){
				$.platforms.queryData();
			});
			
		},
		redict:function(view){
			window.location.href='/panel_manage/adManage/toadManage.ac?view='+view;
		},
		queryData:function(){
			
			$('#dt_ajax_wrapper').remove();
			
			var table = $(".template_cache .ajax_table").clone(true);
			table.attr("id", "dt_ajax");
			$('#data').append(table);
			
			var btn2 = "<button class='edit-all-btn btn btn-default btn-sm' type='button'>编辑</button>";
			var btn3 = "<button class='del-btn btn btn-default btn-sm' type='button'>删除</button>";
			
			var jq_table = $('#dt_ajax').dataTable( {
		        "processing": true,
		        "serverSide": true,
		        ajax: {
						  type: "POST",
						  url:"/panel_manage/adManage/getPlatforms.ac"
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
		                      {"data": "name","title":"平台名称","defaultContent": ""},
		                      {"data": "code","title":"平台编码","defaultContent":""},
		                      {"data": "status","title":"是否有效","defaultContent":""},
		                      {"data":null, "title":"操作","defaultContent": btn2+" "+btn3}
		                  ],
		        columnDefs: [
                               { "visible": false, "aTargets": [0] },
                               {
  		                    	 targets: 3,
  		                    	 render: function (a, b, v, d) {
  		                    		 return v.status == 1 ? "有效" : "无效";
  		                    	 }
                               }
		                     ]
		    });

			$('#dt_ajax').removeClass().addClass('table table-striped');
			
			$("#dt_ajax tbody").on("click",".edit-all-btn",function(){
		           var row = jq_table.api().row($(this).parents("tr"));
		           var data=row.data();
		           window.location.href='/panel_manage/adManage/toadManage.ac?view=updatePlatforms&platformsId='+data.id;
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
		               "url":"/panel_manage/adManage/delPlatforms.ac?op=e",
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
		                	   alert('删除的平台不存在！');
		                   }else if(data.msg == '0'){
		                	   alert('删除的平台不存在！');
		                   }
		               }
		           });
		      });
			
			$('#dt_ajax .downBtn').click(function (){
		        $("#search").val($("#dt_ajax_filter .search").val());
		        $(this)[0].disabled = true;
		        $("#adForm").submit();
		    });
		},
		addColumn:function(){
			var ul = $(".template .templateUl").clone(true);
			var li = $("li",ul).first();
			$("#columnUl").append(li);
		}
	};
});
	                    