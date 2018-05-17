$(function(){
	
	$(document).ready(function() {
	    $.cpaDimensionCreative.init();
	    $.timeZone.showTimeZone();
	});
	
	$.cpaDimensionCreative={
		init:function(){
			$.cpaDimensionCreative.initEvent();
			$.cpaDimensionCreative.queryData();
		},
		initEvent:function(){
			
			$("#query").click(function(){
				$.cpaDimensionCreative.queryData();
		    });
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.cpaDimensionCreative.redict($(n).attr("id"));
				});
			});
			
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
				        	  d.gameId=$("input[name='gameId']").val();
				        	  d.snid=$("input[name='snid']").val();
					      },
						  type: "POST",
						  url:"/panel_bi/cpaCreative/queryDimensionCreativeData.ac"
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
		           data.snid = $("input[name='snid']").val(),
		           data.gameId = $("input[name='gameId']").val()
		           $.ajax({
		               "url":"/panel_bi/cpaCreative/saveDimensionCreativeData.ac",
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
		        		   snid:$("input[name='snid']").val(),
		        		   gameId:$("input[name='gameId']").val()
		        	   };
		           
		           $.ajax({
		               "url":"/panel_bi/cpaCreative/saveDimensionCreativeData.ac",
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
		        		   id:$(this).attr("oid")
		        	   };
		           $.ajax({
		               "url":"/panel_bi/cpaCreative/delDimensionCreativeData.ac",
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
	                    