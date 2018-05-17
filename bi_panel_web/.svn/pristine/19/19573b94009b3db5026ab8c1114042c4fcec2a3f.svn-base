jQuery(function($) {
	$(document).ready(function() {
	    $.gameTypes.init();
	});
	
	$.gameTypes={
			init:function(){
				$.gameTypes.initEvents();
				$.gameTypes.initDatas();
			},
			initEvents:function(){
			},	
			initDatas:function(){
				$('#dt_ajax_wrapper').remove();
				
				var table = $(".template_cache .ajax_table").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var jq_table = $('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
							  type: "POST",
							  url:"/panel_manage/gameManager/getGameTypes.ac"
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
			                      {"data": "id","title":"分类ID","defaultContent": ""},
			                      {"data": "name","title":"分类名称","defaultContent":""},
			                      //{"data": "configType","title":"配置类型","defaultContent":""},
			                      //{"data": "parentId","title":"隶属分类","defaultContent":""},
			                      {"data":null, "title":"操作","defaultContent": "<button class='edit-btn btn btn-default btn-sm' type='button'>编辑</button> <button class='del-btn btn btn-default btn-sm' type='button'>删除</button>"}
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
			                   jqob.html(txt);
			                   jq_table.api().cell(jqob).data(txt);//修改DataTables对象的数据
			               }
			           });
			           var data=row.data();
			           $.ajax({
			               "url":"/panel_manage/gameManager/saveGameTypes.ac",
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
					var trs = $("#dt_ajax tbody").children();
					var trClass = 'odd';
					if((trs.length + 1) % 2 == 0){
						trClass = 'even';
					}
					
					table.prepend('<tr role="row" calss="'+trClass+'"><td>系统自增</td><td><input type="text"></td><td><button class="add-save-btn btn btn-default btn-sm" type="button">保存</button> <button class="del-btn btn btn-default btn-sm" type="button">删除</button></td></tr>');
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
			        		   id:-1,
			        		   name:ps[1]
			        		   //configType:ps[2],
			        		   //parentId:ps[3]
			        	   };
			           
			           $.ajax({
			               "url":"/panel_manage/gameManager/saveGameTypes.ac",
			               "data":$.param(data),
			               "type":"post",
			               "error":function(){
			                   alert("服务器未正常响应，请重试");
			               },
			               "success":function(data){
			            	   if(data.msg=='2'){
			                	   jq_table.api().draw(false);
			                	   $(this).parents("tr").children().first().text(data.sysConfig.id);
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
			        			   id:ps[0],
			        			   name:ps[1]
			        	   };
			           $.ajax({
			               "url":"/panel_manage/gameManager/delGameTypes.ac",
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
			                	   alert('删除的类型不存在！');
			                   }else if(data.msg == '0'){
			                	   alert('删除的类型不存在！');
			                   }
			               }
			           });
			      });
			}
			
	}
	
});