$(function(){
	
	
	function delaa(){
		
		alert("123");
		
	};
	
	$(document).ready(function() {
	
	    $.modelListManager.init();
	});
	
	
	var arrayObj = new Array();
	var chestr="";
	$.modelListManager={
		init:function(){
//			$.modelListManager.initEvent();
			$.modelListManager.queryData();
			$("#del-btn").click(function(){
				if (confirm("确认要删除？")) {
						$.modelListManager.delTask(arrayObj);
				}
				
			});
		
		},
		queryData:function(){
			$('#dt_ajax_wrapper').remove();
			
			var table = $(".template_cache .ajax_table").clone(true);
			table.attr("id", "dt_ajax");
			$('#data').append(table);
			var selected = [];
			jq_table = $('#dt_ajax').DataTable({
				"processing": true,
		        "serverSide": true,
		        ajax: {
						  type: "POST",
						  url:"/panel_bi/customTask/getManagerTaskList.ac"
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
						{"data": "executeTime","title":"执行时间","defaultContent":""},
						{"data": "endTime","title":"结束时间","defaultContent":""},
						{"data": "status","title":"执行状态","defaultContent":""},
						{"data": "recordCount","title":"记录数","defaultContent":""},
						{"data": "executeUserName","title":"执行人","defaultContent":""},
						{"title":"选择"}
						//{"title":"<button id=\"del-btn\" class=\"del-btn\" type=\"button\" onclick=\"delaa()\">删除</button>"}
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
                     			   	//var del = "<button id='del-btn' class='del-btn' style='width:50px;' type='button' val='"+v.id+"' rec="+v.recordCount+"  title='删除此任务'>删除</button>";
	                     		   var del = "<input id='chk' class='chk' name='box' type='checkbox' value='"+v.id+"' rec="+v.recordCount+" />";
                 				    return del;
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
			
			$('#dt_ajax tbody').on("click", ".chk", function(){
				
				var str=document.getElementsByName("box");
				var objarray=str.length;
				
				for (i=0;i<objarray;i++)
				{
					 if(str[i].checked == true)
					 {
					  arrayObj.push(str[i].value);
					  
					 }else{
						 $.modelListManager.removeByValue(arrayObj, str[i].value);
					 }
				}
				
				//var checkList += $(this).attr("val");
				//if (confirm("确认要删除？")) {
					//$.modelListManager.delTask($(this).attr("val"));
				//}
			});
			
			
		
		},
		 removeByValue:function(arr, val) {
			  for(var i=0; i<arr.length; i++) {
			    if(arr[i] == val) {
			      arr.splice(i,5);
			    }
			  }
		},
		delTask:function(taskId){
			$.ajax({
				"url":"/panel_bi/customTask/delTask.ac?taskId="+taskId,
	            "type":"post",
	            "success":function(data){
	            	$.modelListManager.queryData();
	            	alert("删除成功");
	            }
			});
		}
	};
	

	
});