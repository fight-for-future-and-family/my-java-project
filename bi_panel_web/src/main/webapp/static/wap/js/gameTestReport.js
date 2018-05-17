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
						  
					    	 if(indicators == 'all'){//总体(主要为基础数据)
								  $("#data").show();
								  $("#data2").hide();
								  $("#data4").hide();
			                      $.gameTestReport.brokenTable(data.reports,indicators);
							  }else if(indicators == 'zsb'){ //总体(主要为注收比)
								  $("#data").hide();
								  $("#data2").hide();
								  $("#data4").show();
				                   $.gameTestReport.brokenTable4(data.reports4);
							  }else if(indicators == 'leave'){//总体(主要为留存数据)
								  $("#data").hide();
								  $("#data2").show();
								  $("#data4").hide();
			                      $.gameTestReport.brokenTable2(data.reports2);
							  }
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
			brokenTable : function(reports) {//数据概览
				$("#data").empty();
				$('#dt_wrapper').remove();
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt");
				$('#data').append(table);

				var rate = $("#gameRate").val();
				$.each(reports, function(i, v) {
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.ds", trTemp).text(v.ds);
					$("td.dau", trTemp).text(v.dau);
					$("td.dnu", trTemp).text(v.dnu);
					$("td.install", trTemp).text(v.install);
					$("td.role_choice", trTemp).text(v.role_choice);
					$("td.payment_amount", trTemp).text(v.payment_amount);
					$("td.payer", trTemp).text(v.payer);
					$("td.pay_rate", trTemp).text(v.pay_rate);
					$("td.arpu", trTemp).text(v.arpu);
					$("td.arppu", trTemp).text(v.arppu);
					$("td.new_equip", trTemp).text(v.new_equip);
					$("td.install2", trTemp).text(v.install2);
					$("td.jihuo_xinzeng_rate", trTemp).text(v.jihuo_xinzeng_rate);
					$("td.install_role_rate", trTemp).text(v.install_role_rate);
					$('tbody', table).append(trTemp);
				});
				table.removeClass("view_table").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				$.biDataTables.dataTables($('#dt'));
				$('#dt tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			
			brokenTable4 : function(reports4) {//注收比
				$("#data4").empty();
				$('#dt4_wrapper').remove();
				var table = $(".template_cache4 .view_table4").clone(true);
				table.attr("id", "dt4");
				$('#data4').append(table);

				var rate = $("#gameRate").val();
				$.each(reports4, function(i, v) {
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.install_date", trTemp).text(v.install_date==null?0:v.install_date);
					$("td.install", trTemp).text(v.install==null?0:v.install);
					$("td.d0", trTemp).text(v.d0==null?0:v.d0);
					$("td.d1", trTemp).text(v.d1==null?0:v.d1);
					$("td.d2", trTemp).text(v.d2==null?0:v.d2);
					$("td.d3", trTemp).text(v.d3==null?0:v.d3);
					$("td.d4", trTemp).text(v.d4==null?0:v.d4);
					$("td.d5", trTemp).text(v.d5==null?0:v.d5);
					$("td.d6", trTemp).text(v.d6==null?0:v.d6);
					$("td.d7", trTemp).text(v.d7==null?0:v.d7);
			
					$('tbody', table).append(trTemp);
				});
				table.removeClass("view_table4").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				$.biDataTables.dataTables($('#dt4'));
				$('#dt4 tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			brokenTable2 : function(reports2) {//留存
				$("#data2").empty();
				$('#dt2_wrapper').remove();
				var table = $(".template_cache2 .view_table2").clone(true);
				table.attr("id", "dt2");
				$('#data2').append(table);

				var rate = $("#gameRate").val();
				$.each(reports2, function(i, v) {
					var trTemp = $("tbody tr", table).first().clone(true);
					$("td.install_date", trTemp).text(v.install_date==null?0:v.install_date);
					$("td.install", trTemp).text(v.install==null?0:v.install);
					$("td.d1", trTemp).text(v.d1==null?0+'%':v.d1+'%');
					$("td.d2", trTemp).text(v.d2==null?0+'%':v.d2+'%');
					$("td.d3", trTemp).text(v.d3==null?0+'%':v.d3+'%');
					$("td.d4", trTemp).text(v.d4==null?0+'%':v.d4+'%');
					$("td.d5", trTemp).text(v.d5==null?0+'%':v.d5+'%');
					$("td.d6", trTemp).text(v.d6==null?0+'%':v.d6+'%');
					$("td.d7", trTemp).text(v.d7==null?0+'%':v.d7+'%');
			
					$('tbody', table).append(trTemp);
				});
				table.removeClass("view_table2").addClass("table table-striped");
				$("tbody tr", table).first().remove();
				$.biDataTables.dataTables($('#dt2'));
				$('#dt2 tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			/*
			brokenTable:function(reports,report){
				$('#caption').hide();
				$("#dt_wrapper").remove();
				
				var table = $(".template_cache .dataTable").clone(true);
				table.attr("id","dt");
				
				var type = $("#indicators").val();
				var trHead = $("tbody tr",table).first().clone(true);
				if(type == 'source'  && game.terminalType == 1 && game.systemType == 0){
					$("thead tr",table).first().remove();
					$("tbody tr",table).first().remove();
				}else{
					$("thead tr",table).eq(1).remove();
					$("tbody tr",table).eq(1).remove();
				}

				if(report!=null){
					var trSum = $("tbody tr",table).first().clone(true);
					$("td.day",trSum).text(report.day);
					$("td.dnu",trSum).text(report.dnu);
					$("td.newEquip",trSum).text(report.newEquip);
					$("td.roleChoice",trSum).text(report.roleChoice == null ? '-' : report.roleChoice);
					$("td.dau",trSum).text(report.dau);
					$("td.old_dau",trSum).text((report.dau - report.dnu));
					$("td.paymentAmount",trSum).text(Math.round((report.paymentAmount/game.rate)*100)/100);
					$("td.pu",trSum).text(report.pu);
					$("td.arpu",trSum).text(Math.round((report.arpu /game.rate)*100)/100);
					$("td.arppu",trSum).text(Math.round((report.arppu/game.rate)*100)/100);
					$("td.paymentCnt",trSum).text(report.paymentCnt);
					$("td.payRate",trSum).text(Math.round((report.pu/report.dau*100)*100)/100+'%');
					
					//$("td.newPayAmount",trSum).text(Math.round((report.newPayAmount/game.rate)*100)/100);
					//$("td.newPu",trSum).text(report.newPu);
					$("td.newDnuUserPayRate",trSum).text(Math.round((report.installPu/report.dnu)*100*100)/100+'%');
					$("td.newDnuUserArpu",trSum).text(Math.round(((report.installPayAmount/game.rate)/report.dnu)*100)/100);
					
					$("td.installPayAmount",trSum).text(Math.round((report.installPayAmount/game.rate)*100)/100);
					$("td.installPu",trSum).text(report.installPu);
					$("td.rollAmount",trSum).text(Math.round((report.rollAmount/game.rate)*100)/100);
					$("td.rollPayUsers",trSum).text(report.rollPayUsers);
					$("td.rollUsers",trSum).text(report.rollUsers);
					$("td.pcu",trSum).text(report.pcu);
					$("td.acu",trSum).text(Math.round(report.acu*100)/100);
					$("td.avgUserTime",trSum).text(Math.round((report.avgUserTime/60)*100)/100+'分');
					if(type == 'source' && game.terminalType == 1 && game.systemType == 0){
						$("td.idfa",trSum).text(report.idfa);
						$("td.distinctIdfa",trSum).text(report.distinctIdfa);
						$("td.distinctIp",trSum).text(report.distinctIp);
					}
					trSum.css({"background-color":"rgba(255, 0, 0, 0.34902)"});
					$("tfoot",table).append(trSum);
				}
				
				$.each(reports,function(i,v){
					
					var trTemp = $("tbody tr",table).first().clone(true);
					
					$("td.day",trTemp).text($.gameTestReport.convert(v.day));
					var ds = new Date(v.day).getDay();
					if(ds!="NaN"){
						if(ds==6||ds==0){
							$("td.day",trTemp).css("font-weight", "bold");
						}
					}
					$("td.dnu",trTemp).text(v.dnu);
					$("td.newEquip",trTemp).text(v.newEquip);
					$("td.roleChoice",trTemp).text(v.roleChoice == null ? '-' : v.roleChoice);
					$("td.dau",trTemp).text(v.dau);
					$("td.old_dau",trTemp).text((v.dau - v.dnu));
					$("td.paymentAmount",trTemp).text(Math.round((v.paymentAmount/game.rate)*100)/100);
					$("td.pu",trTemp).text(v.pu);
					$("td.arpu",trTemp).text(Math.round((v.arpu /game.rate)*100)/100);
					$("td.arppu",trTemp).text(Math.round((v.arppu/game.rate)*100)/100);
					$("td.paymentCnt",trTemp).text(v.paymentCnt);
					$("td.payRate",trTemp).text(Math.round(v.payRate * 100*100)/100+'%');
					
					//$("td.newPayAmount",trTemp).text(Math.round((v.newPayAmount/game.rate)*100)/100);
					//$("td.newPu",trTemp).text(v.newPu);
					$("td.newDnuUserPayRate",trTemp).text(Math.round((v.installPu/v.dnu)*100*100)/100+'%');
					$("td.newDnuUserArpu",trTemp).text(Math.round(((v.installPayAmount/game.rate)/v.dnu)*100)/100);
					
					$("td.installPayAmount",trTemp).text(Math.round((v.installPayAmount/game.rate)*100)/100);
					$("td.installPu",trTemp).text(v.installPu);
					$("td.rollAmount",trTemp).text(Math.round((v.rollAmount/game.rate)*100)/100);
					$("td.rollPayUsers",trTemp).text(v.rollPayUsers);
					$("td.rollUsers",trTemp).text(v.rollUsers);
					$("td.pcu",trTemp).text(v.pcu);
					$("td.acu",trTemp).text(Math.round(v.acu*100)/100);
					$("td.avgUserTime",trTemp).text(Math.round((v.avgUserTime/60)*100)/100+'分');
					
					if(type == 'source' && game.terminalType == 1 && game.systemType == 0){
						$("td.idfa",trTemp).text(v.idfa);
						$("td.distinctIdfa",trTemp).text(v.distinctIdfa);
						$("td.distinctIp",trTemp).text(v.distinctIp);
					}
					
					$('tbody',table).append(trTemp); 
				});
				
				table.removeClass("dataTable").addClass("display");
				$("tbody tr",table).first().remove();
				$('#data').append(table); 
				
				$.gameTestReport.dataTables($('#dt'));
				$('#dt tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
			},
			*/
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
	                    