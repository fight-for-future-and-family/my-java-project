$(function(){
		
	$(document).ready(function() {
	    $.gameTestReport.init();
	});
	
	var game;
		
	$.gameTestReport={
			init:function(){
				$.gameTestReport.initEvent();
				$.gameTestReport.submit();
			},
			initEvent:function(){
				$("#query").click(function(){
					$.gameTestReport.submit();
			    });
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gameTestReport.redict($(n).attr("id"));
					});
				});
				/*
				$("#indicators").change(function(){
					$.gameTestReport.change($.gameTestReport);
				});
				*/
				$("#indicators").change(function(){
					$.gameUtil.change($.gameTestReport);
				});
				$(".tab-hd span").click(function(){
					$.gameTestReport.redict($(this).attr("view"));
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view;
//				//window.location.href='/panel_bi/gameTestReport/toGameTestReport.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			submit:function(){
				var ps = $.gameTestReport.checkParam();
				if(ps == null){
					return;
			    }
				
				if($("#s_c").val() == '-99'){
					$.gameTestReport.brokenAjaxTable(ps);
	    			return;
				}
				
				//$("#query")[0].disabled = true;
				$('#ajax_data').hide();
				$('#data').show();
				
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/gameTestReport/getGameOverview.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						
						  var indicators = $('#indicators').val();
			              $.gameTestReport.brokenTable2(data.reports);
							 
					  }
				});
			},
			convert:function(str){
				var ds = new Date(str).getDay().toString();
				if(ds!="NaN"){
					var arr = ["日","一","二","三","四","五","六"];
					for (var i = 0; i < arr.length; i++) {
						ds = ds.replace(i, arr[i]);
					}
					return str+"(周"+ds+")";
				}else{
					return str;
				}
			},
			dataTables:function(div){
				
				$('.top').remove();
				$('.bottom').remove();
				$('.clear').remove();
				$('.DTTT_container').remove();
				
				var table = div.DataTable({
				"sPaginationType" : "full_numbers",
	            "dom" : 'T<"top">rt<"bottom"ip><"clear">' ,
	            "bDestroy" : true,
	            "bPaginate" : true,
	            "bInfo" : true,
				"bSort" : true,
				"bScrollCollapse" : true,
				"bScrollInfinite" : true,
			    "bFilter" : false,
			    "paging": true, //翻页功能
			    "lengthChange": false, //改变每页显示数据数量
			    "searching": false, //过滤功能
			    "ordering": true, //排序功能
			    "order": [[ 0, "desc" ]],
			    "processing": false,
	            "lengthMenu": [ 10, 20, 50, 100],
			    "pageLength": 10,
			    "language":{
			    	"url": "/dataTables/chinese.json"
			    },
			    tableTools: {
			    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
	                                        "aButtons":[ 
	                            { 
	                               "sExtends": "xls", 
	                               "sButtonText": "下载数据" 
	                             }]
		        }
				}
			  );
			   div.removeClass();
			   div.addClass('table table-striped');
			   
			   return table;
			},
			
			brokenTable2 : function(reports2) {//留存
				$("#data").empty();
				$('#dt2_wrapper').remove();
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt");
				$('#data').append(table);

				var rate = $("#gameRate").val();
				$.each(reports2, function(i, v) {
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.install_date", trTemp).text(v.install_date==null?0:v.install_date);
					$("td.new_equip", trTemp).text(v.new_equip==null?0:v.new_equip);
					$("td.d1", trTemp).text(v.d1==null?0+'%':v.d1+'%');
					$("td.d2", trTemp).text(v.d2==null?0+'%':v.d2+'%');
					$("td.d3", trTemp).text(v.d3==null?0+'%':v.d3+'%');
					$("td.d4", trTemp).text(v.d4==null?0+'%':v.d4+'%');
					$("td.d5", trTemp).text(v.d5==null?0+'%':v.d5+'%');
					$("td.d6", trTemp).text(v.d6==null?0+'%':v.d6+'%');
					$("td.d7", trTemp).text(v.d7==null?0+'%':v.d7+'%');
			
					$('tbody', table).append(trTemp);
				});
				table.removeClass("view_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				$.biDataTables.dataTables($('#dt'));
				$('#dt tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			
			changeCheck:function(){
				var ps={
					     id:$("input[name='gamesId']").val(),
					     gameId:$("input[name='gameId']").val(),
					     snid:$("input[name='snid']").val(),
					};
			      if(ps.id == null || ps.id == ''){ return null; }
			      if(ps.gameId == null || ps.gameId == ''){ return null; }
			      if(ps.snid == null || ps.snid == ''){ return null; }
			      return ps;
			},
			change:function(exclass){
				var ps = this.changeCheck();
				if(ps == null){
					return;
				}
			      
				var value = $("#indicators").val();
				if(value == 'all'){
					$("#s_c").remove();
					exclass.submit();
				}else if(value == 'source'){
					$("#s_c").remove();
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/game/getGameSource.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" style="max-width:210px"><option value="-1">请选择...</option><option value="-99">所有渠道</option>';
						    $.each(data.gameSources,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	exclass.submit();
							});
						  }
					});
				}else if(value == 'client'){
					$("#s_c").remove();
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/game/getGameClient.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" style="max-width:210px"><option value="-1">请选择...</option><option value="-99">所有服务器</option>';
						    $.each(data.gameClients,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	exclass.submit();
							});
						  }
					});
				}
			},
			checkParam:function(){
			  var ps={
				     id:$("input[name='gamesId']").val(),
				     gameId:$("input[name='gameId']").val(),
				     snid:$("input[name='snid']").val(),
				     indicators:$("#indicators").val(),
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 source:$('#s_c').val(),
					 clientid:$('#s_c').val(),
					 view:$('#view').val()
				};
		      if(ps.id == null || ps.id == ''){ return null; }
		      if(ps.gameId == null || ps.gameId == ''){ return null; }
		      if(ps.snid == null || ps.snid == ''){ return null; }
		      
		      if(ps.indicators == null || ps.indicators == ''){
		    	  ps.indicators = 'all'; 
		       }else if(ps.indicators == 'source' && (ps.source != null && ps.source == '-1')){
		    	   return null;
		       }else if(ps.indicators == 'client' && (ps.clientid != null && ps.clientid == '-1')){
		    	   return null;
		       }
		      
		      if((ps.endTime == null || ps.endTime == '') && (ps.beginTime == null || ps.beginTime == '')){
		    	  var d = new Date();
		    	  ps.endTime = $.date.getDateFullStr(d);
		    	  $("input[name='endTime']").val(ps.endTime);
		    	  
		    	  // if(d.getDate() <= 3){
			    	  d.setDate(d.getDate()- 30);
		    	 // }else{
			    //	  d.setDate(1);
		    	//  }
		    	  ps.beginTime = $.date.getDateFullStr(d);
		    	  
		    	  $("input[name='beginTime']").val(ps.beginTime);
		      }else if((ps.endTime == null || ps.endTime == '') && (ps.beginTime != null && ps.beginTime != '')){
		    	  var d = new Date(ps.beginTime);
		    	  d.setDate(d.getDate()+ 30);
		    	  
		    	  ps.endTime = $.date.getDateFullStr(d);
		    	  $("input[name='endTime']").val(ps.endTime);
		      }else if((ps.endTime != null && ps.endTime != '') && (ps.beginTime == null || ps.beginTime == '')){
		    	  var d = new Date(ps.endTime);
		    	  d.setDate(d.getDate()- 30);
		    	  
		    	  ps.beginTime = $.date.getDateFullStr(d);
		    	  $("input[name='beginTime']").val(ps.beginTime);
		      }
		      
		      return ps;
			},
			brokenAjaxTable:function(ps){
				
				$('#caption').show();
				$('#caption button').unbind();
				$('#caption button').click(function (){
			        $(this).attr('disabled',true);
			        $(this).val(2);
			        window.btnTimeout=setInterval(function(){
						$.gameUtil.btnTimeout($('#caption button'),window.btnTimeout);
					  },1000);
			        $("#downLoadForm").submit();
			    });
				
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				
				var type = $("#indicators").val();
				var rate = $("#gameRate").val();
				$('#ajax_data').show();
				$('#data').hide();
				
				var table = $(".template_cache .ajax_table").clone(true);
				
                var columns = [
			                      {"data": "day"},
			                      {"data": type == "source" ? "source" : "clientid"},
			                      {"data": "dnu"},
			                      {"data": null},
			                      {"data": null},
			                      {"data": "dau"},
			                      {"data": null},
			                      {"data": null},
			                      {"data": "pu"},
			                      {"data": null},
			                      {"data": null},
			                      {"data": "paymentCnt"},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": null},
			                      {"data": "installPu"},
			                      {"data": null},
			                      {"data": "rollPayUsers"},
			                      {"data": "rollUsers"},
			                      {"data": "pcu"},
			                      {"data": null},
			                      {"data": null}
			                  ];
				if("source" == type && game.terminalType == 1 && game.systemType == 0){
					var headTemp = $("thead tr",table).eq(1);
					$("td.head_type",headTemp).text('渠道');
					$("thead tr",table).first().remove();
					
					columns.push({
						"data":"idfa"
					});
					columns.push({
						"data":"distinctIdfa"
					});
					columns.push({
						"data":"distinctIp"
					});
				}else{
					var headTemp = $("thead tr",table).first();
					$("td.head_type",headTemp).text('服务器');
					$("thead tr",table).eq(1).remove();
				}
				
				table.attr("id", "dt_ajax");
				$('#ajax_data').append(table);
				
				$('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
					          data: function(d){
					        	  d.id=ps.id;
					        	  d.gameId=ps.gameId;
					        	  d.indicators=ps.indicators;
					        	  d.beginTime=ps.beginTime;
					        	  d.endTime=ps.endTime;
					        	  d.source=ps.source;
					        	  d.clientid=ps.clientid;
					        	  d.view=ps.view;
					          },
							  type: "POST",
							  url:"/panel_bi/wap/gameView/getWapGameDatasView.ac"
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
				    "order": [[ 0, "desc" ]],
			        columns: columns,
			        rowCallback:function(row, data) {
			        	//
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                        //
                    },
			        columnDefs: [
								{targets: 0,
									   render: function (a, b, v, d) {
										   var ds = new Date(v.day).getDay();
										   if(ds!="NaN"){
											   if(ds==6||ds==0){
												   return '<span style="font-weight:bold">'+$.gameTestReport.convert(v.day)+'</span>';
											   }else{
												   return $.gameTestReport.convert(v.day);
											   }
										   }else{
											   return v.day;
										   }
									   }
								},
								{
									targets: 3,
			                    	render: function (a, b, v, d) {
			                    		 return v.newEquip == null ? '-' : v.newEquip;
			                    	}
								},
			                     {
			                    	 targets: 4,
			                    	 render: function (a, b, v, d) {
			                    		 return v.roleChoice == null ? '-' : v.roleChoice;
			                    	 }
			                     },
			                     {
			                    	 targets: 6,
			                    	 orderable:false,
			                    	 render: function (a, b, v, d) {
			                    		 return v.dau - v.dnu;
			                    	 }
			                     },
			                     {
			                    	 targets: 7,
			                    	 orderable:false,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.paymentAmount/rate)*100)/100;
			                    	 }
			                     },
			                     {
			                    	 targets: 9,
			                    	 orderable:false,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.arpu /rate)*100)/100;
			                    	 }
			                     },
			                     {
			                    	 targets: 10,
			                    	 orderable:false,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.arppu /rate)*100)/100;
			                    	 }
			                     },
			                     {
			                    	 targets: 12,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round(v.payRate * 100*100)/100+'%';
			                    	 }
			                     },
			                     /*
			                     {
			                    	 targets: 13,
			                    	 orderable:false,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.newPayAmount/rate)*100)/100;
			                    	 }
			                     },*/
			                     {
			                    	 targets: 13,
			                    	 orderable:false,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.installPu/v.dnu)*100)/100+'%';
			                    	 }
			                     },
			                     {
			                    	 targets: 14,
			                    	 orderable:false,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.installPayAmount/v.dnu))/100;
			                    	 }
			                     },
			                     {
			                    	 targets: 15,
			                    	 orderable:false,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.installPayAmount/rate)*100)/100;
			                    	 }
			                     },
			                     {
			                    	 targets: 17,
			                    	 orderable:false,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.rollAmount/rate)*100)/100;
			                    	 }
			                     },
			                     {
			                    	 targets: 21,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round(v.acu*100)/100;
			                    	 }
			                     },
			                     {
			                    	 targets: 22,
			                    	 render: function (a, b, v, d) {
			                    		 return Math.round((v.avgUserTime/60)*100)/100+'分';
			                    	 }
			                     }
			                      
			                     ]
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
			}
	};	
});
	                    