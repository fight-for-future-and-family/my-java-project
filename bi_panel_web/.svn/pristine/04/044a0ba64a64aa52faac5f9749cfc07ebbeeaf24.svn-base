$(function(){
		
	$(document).ready(function() {
	    $.gameRetentionReport.init();
	});
		
	var lastShow = 0;
	var currShow = 0;
	var jqTable = null;
	$.gameRetentionReport={
			init:function(){
				$.gameRetentionReport.initEvent();
				$.gameRetentionReport.submit();
			},
			initEvent:function(){
				
				$("#query").click(function(){
					$.gameRetentionReport.submit();
			    });
				
				$("#data_caption span").each(function(i,n){
					$(n).click(function(){
						//$("#warnSpan").show();
						currShow = $(n).attr("val");
						if(lastShow == currShow){
							return false;
						}
						
						$("#data_caption span").removeClass("ui-selected");
						$(n).addClass("ui-selected");
						
//						$("#data_caption span").each(function(i,n){
//							$(n).attr("disabled",false);
//						});
						//$('.theme-popover-mask').fadeIn(100);
						//$('.theme-popover').slideDown(200);
						
						$.gameRetentionReport.showhidecol('data');
						//$("#warnSpan").hide();
						//alert('手机端点击可能需要耐心等待6秒左右...');
					});
				});
				
				$("#ajax_data_caption span").each(function(i,n){
					$(n).click(function(){
						currShow = $(n).attr("val");
						if(lastShow == currShow){
							return false;
						}
						
						$("#ajax_data_caption span").removeClass("ui-selected");
						$(n).addClass("ui-selected");
						
//						$("#data_caption span").each(function(i,n){
//							$(n).attr("disabled",false);
//						});
						//$('.theme-popover-mask').fadeIn(100);
						//$('.theme-popover').slideDown(200);
						
						$.gameRetentionReport.showhidecol('ajax');
					});
				});
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gameRetentionReport.redict($(n).attr("id"));
					});
				});
				
				$(".tab-hd span").click(function(){
					$.gameRetentionReport.redict($(this).attr("view"));
				});
				
				$("#channel").change(function(){
					$.gameRetentionReport.change($.gameRetentionReport);
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/wap/gameView/toWapGameDatasView.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			submit:function(){
				var ps = $.gameRetentionReport.checkParam();
				if(ps == null){
					return;
			    }
				if($("#s_c").val() == '-99'){
					lastShow = 0;
					currShow = 1;
					$("#data_caption span").removeClass("ui-selected");
				    $("#data_caption span").first().addClass("ui-selected");
				    $("#ajax_data_caption span").removeClass("ui-selected");
				    $("#ajax_data_caption span").first().addClass("ui-selected");
					$.gameRetentionReport.brokenAjaxTable(ps);
	    			return;
				}
				
				$("#query")[0].disabled = true;
				$('#ajax_data').hide();
				$('#data').show();
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/wap/gameView/getWapGameDatasView.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						    $.gameRetentionReport.brokenTable(data.installRetentions);
						    $("#query")[0].disabled = false;
						    $("#data_caption span").removeClass("ui-selected");
						    $("#data_caption span").first().addClass("ui-selected");
						    $("#ajax_data_caption span").removeClass("ui-selected");
						    $("#ajax_data_caption span").first().addClass("ui-selected");
						    lastShow = 0;
						    currShow = 1;
					  }
				});
			},
			showhidecol:function(type){
				if(jqTable != null){
					if(type == 'data'){
						$.gameRetentionReport.showOrHide(2,lastShow,false);
						$.gameRetentionReport.showOrHide(2,currShow,true);
					}else{
						$.gameRetentionReport.showOrHide(3,lastShow,false);
						$.gameRetentionReport.showOrHide(3,currShow,true);
					}
					
					
					lastShow = currShow;
					jqTable.draw(false);
					
//					$('tbody tr').unbind();
//					$('tbody tr').click(function (){
//				           $(this).toggleClass('highlight');
//				     });
					
//					$("#data_caption span").each(function(i,n){
//						$(n).attr("disabled",true);
//					});
					//$('.theme-popover-mask').fadeOut(100);
					//$('.theme-popover').slideUp(200);
				}
			},
			showOrHide:function(begin,lastShow,visible){
				if(lastShow == 0 || lastShow == 1){
					for(var i=begin;i<=(begin+29);i++){
						jqTable.column(i).visible(visible);
					}
				}else if(lastShow == 2){
					for(var i=(begin+30);i<=(begin+59);i++){
						jqTable.column(i).visible(visible);
					}
				}else if(lastShow == 3){
					for(var i=(begin+60);i<=(begin+89);i++){
						jqTable.column(i).visible(visible);
					}
				}
			},
			brokenTable:function(reports){
                $("#dt_wrapper").remove();
                
                var colorArr = ['#D9FFFD','#97FFF4','#01E7FA',
                                '#01D6E7','#01D1F5','#01C8EB','#39A8F9',
                                '#269FF9','#1899F8','#078EF1','#FC324B'];
    			
				var table = $(".template_cache .dataTable").clone(true);
				table.attr("id","dt");
				
				var headTemp = $("thead tr",table).first();
				var value = $("#channel").val().split('_');
				if(value.length > 0 && value[0] == 'role'){
					$("td.head_install",headTemp).text('创角数');
				}else{
					$("td.head_install",headTemp).text('新注册');
				}
				
				$.each(reports,function(i,n){
					
					var trTemp = $("tbody tr",table).first().clone(true);
					var v = n.gameLtv;
					
					if(v.install == null || v.install == undefined || v.install == '-'){
						$("td.date",trTemp).text(v.installDay);
						$("td.install",trTemp).text("-");
						for(var j=0;j<90;j++){
							$("td.d"+(j+1)+"",trTemp).text('-%');
						}
					}else{
						$("td.date",trTemp).text(v.installDay);
						$("td.install",trTemp).text(v.install);
						var va = [Math.round(v.d1 * 100 * 100) / 100,
									Math.round(v.d2 * 100 * 100) / 100,
									Math.round(v.d3 * 100 * 100) / 100,
									Math.round(v.d4 * 100 * 100) / 100,
									Math.round(v.d5 * 100 * 100) / 100,
									Math.round(v.d6 * 100 * 100) / 100,
									Math.round(v.d7 * 100 * 100) / 100,
									Math.round(v.d8 * 100 * 100) / 100,
									Math.round(v.d9 * 100 * 100) / 100,
									Math.round(v.d10 * 100 * 100) / 100,
									Math.round(v.d11 * 100 * 100) / 100,
									Math.round(v.d12 * 100 * 100) / 100,
									Math.round(v.d13 * 100 * 100) / 100,
									Math.round(v.d14 * 100 * 100) / 100,
									Math.round(v.d15 * 100 * 100) / 100,
									Math.round(v.d16 * 100 * 100) / 100,
									Math.round(v.d17 * 100 * 100) / 100,
									Math.round(v.d18 * 100 * 100) / 100,
									Math.round(v.d19 * 100 * 100) / 100,
									Math.round(v.d20 * 100 * 100) / 100,
									Math.round(v.d21 * 100 * 100) / 100,
									Math.round(v.d22 * 100 * 100) / 100,
									Math.round(v.d23 * 100 * 100) / 100,
									Math.round(v.d24 * 100 * 100) / 100,
									Math.round(v.d25 * 100 * 100) / 100,
									Math.round(v.d26 * 100 * 100) / 100,
									Math.round(v.d27 * 100 * 100) / 100,
									Math.round(v.d28 * 100 * 100) / 100,
									Math.round(v.d29 * 100 * 100) / 100,
									Math.round(v.d30 * 100 * 100) / 100,
									Math.round(v.d31 * 100 * 100) / 100,
									Math.round(v.d32 * 100 * 100) / 100,
									Math.round(v.d33 * 100 * 100) / 100,
									Math.round(v.d34 * 100 * 100) / 100,
									Math.round(v.d35 * 100 * 100) / 100,
									Math.round(v.d36 * 100 * 100) / 100,
									Math.round(v.d37 * 100 * 100) / 100,
									Math.round(v.d38 * 100 * 100) / 100,
									Math.round(v.d39 * 100 * 100) / 100,
									Math.round(v.d40 * 100 * 100) / 100,
									Math.round(v.d41 * 100 * 100) / 100,
									Math.round(v.d42 * 100 * 100) / 100,
									Math.round(v.d43 * 100 * 100) / 100,
									Math.round(v.d44 * 100 * 100) / 100,
									Math.round(v.d45 * 100 * 100) / 100,
									Math.round(v.d46 * 100 * 100) / 100,
									Math.round(v.d47 * 100 * 100) / 100,
									Math.round(v.d48 * 100 * 100) / 100,
									Math.round(v.d49 * 100 * 100) / 100,
									Math.round(v.d50 * 100 * 100) / 100,
									Math.round(v.d51 * 100 * 100) / 100,
									Math.round(v.d52 * 100 * 100) / 100,
									Math.round(v.d53 * 100 * 100) / 100,
									Math.round(v.d54 * 100 * 100) / 100,
									Math.round(v.d55 * 100 * 100) / 100,
									Math.round(v.d56 * 100 * 100) / 100,
									Math.round(v.d57 * 100 * 100) / 100,
									Math.round(v.d58 * 100 * 100) / 100,
									Math.round(v.d59 * 100 * 100) / 100,
									Math.round(v.d60 * 100 * 100) / 100,
									Math.round(v.d61 * 100 * 100) / 100,
									Math.round(v.d62 * 100 * 100) / 100,
									Math.round(v.d63 * 100 * 100) / 100,
									Math.round(v.d64 * 100 * 100) / 100,
									Math.round(v.d65 * 100 * 100) / 100,
									Math.round(v.d66 * 100 * 100) / 100,
									Math.round(v.d67 * 100 * 100) / 100,
									Math.round(v.d68 * 100 * 100) / 100,
									Math.round(v.d69 * 100 * 100) / 100,
									Math.round(v.d70 * 100 * 100) / 100,
									Math.round(v.d71 * 100 * 100) / 100,
									Math.round(v.d72 * 100 * 100) / 100,
									Math.round(v.d73 * 100 * 100) / 100,
									Math.round(v.d74 * 100 * 100) / 100,
									Math.round(v.d75 * 100 * 100) / 100,
									Math.round(v.d76 * 100 * 100) / 100,
									Math.round(v.d77 * 100 * 100) / 100,
									Math.round(v.d78 * 100 * 100) / 100,
									Math.round(v.d79 * 100 * 100) / 100,
									Math.round(v.d80 * 100 * 100) / 100,
									Math.round(v.d81 * 100 * 100) / 100,
									Math.round(v.d82 * 100 * 100) / 100,
									Math.round(v.d83 * 100 * 100) / 100,
									Math.round(v.d84 * 100 * 100) / 100,
									Math.round(v.d85 * 100 * 100) / 100,
									Math.round(v.d86 * 100 * 100) / 100,
									Math.round(v.d87 * 100 * 100) / 100,
									Math.round(v.d88 * 100 * 100) / 100,
									Math.round(v.d89 * 100 * 100) / 100,
									Math.round(v.d90 * 100 * 100) / 100
						          ];
						for(var j=0;j<90;j++){
							$("td.d"+(j+1)+"",trTemp).text(va[j] + '%');
						}
					}
					
					for(var j=1;j<=90;j++){
						var td = $("td.d"+j+"",trTemp);
						var data = Number(td.text().replace('%',''));
						
						if(data >= 100){
							td.css('background-color',colorArr[colorArr.length-1]);
						}else if(data > 0 && data < 100){
							var index = Math.floor(data / 10);
							td.css('background-color',colorArr[index]);
						}
					}
					
					$('tbody',table).append(trTemp); 
				});
				
				$("tbody tr",table).first().remove();
				$('#data').append(table); 
				
				var hiddenColumn = [32,33,34,35,36,37,38,39,40,
				                    41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,
				                    56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,
				                    71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,
				                    86,87,88,89,90,91];
				
				jqTable = $('#dt').DataTable({
					"sPaginationType" : "full_numbers",
		            "aoColumnDefs": [{  "bSearchable": false, 
		            					"bVisible": false, 
		            					"aTargets": hiddenColumn }] ,
		            "dom" : 'T<"clear"><"top">rt<"bottom"ip><"clear">' ,
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
				    "processing": true,
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
				});
				
				$('#dt').removeClass();
				$('#dt').addClass('table table-striped');
				   
				$('#dt tbody tr').click(function (){
			        $('td',this).toggleClass('highlight');
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
				var value = $("#channel").val();
				if(value == 'install_all' || value == 'role_all'){
					$("#s_c").remove();
					exclass.submit();
				}else if(value == 'install_source' || value == 'role_source'){
					$("#s_c").remove();
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/game/getGameSource.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" style="max-width:200px"><option value="-1">请选择...</option><option value="-99">所有渠道</option>';
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
						    var str = '<select id="s_c" style="max-width:200px"><option value="-1">请选择...</option><option value="-99">所有服务器</option>';
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
				     channel:$("#channel").val(),
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 source:$('#s_c').val(),
					 clientid:$('#s_c').val(),
					 view:$('#view').val()
				};
		      if(ps.id == null || ps.id == ''){ return null; }
		      if(ps.gameId == null || ps.gameId == ''){ return null; }
		      if(ps.snid == null || ps.snid == ''){ return null; }
		      
		      if(ps.channel == null || ps.channel == ''){
		    	  ps.channel = 'install_all'; 
		       }else if(ps.channel.split('_')[1] == 'source' && (ps.source != null && ps.source == '-1')){
		    	   return null;
		       }else if(ps.channel == 'client' && (ps.clientid != null && ps.clientid == '-1')){
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
		      
//		      var dateArr = $.gameUtil.processDate(ps.beginTime,ps.endTime);
//		      ps.beginTime = dateArr[0];
//		      ps.endTime = dateArr[1];
//		      $("input[name='beginTime']").val(dateArr[0]);
//		      $("input[name='endTime']").val(dateArr[1]);
		      
		      return ps;
			},
			getItemValue:function(index,v){
				switch(index){
				   case 3:
				      return v.gameLtv.d1;
				   case 4:
				      return v.gameLtv.d2;
				   case 5:
				      return v.gameLtv.d3;
				   case 6:
				      return v.gameLtv.d4;
				   case 7:
				      return v.gameLtv.d5;
				   case 8:
				      return v.gameLtv.d6;
				   case 9:
				      return v.gameLtv.d7;
				   case 10:
				      return v.gameLtv.d8;
				   case 11:
				      return v.gameLtv.d9;
				   case 12:
				      return v.gameLtv.d10;
				   case 13:
				      return v.gameLtv.d11;
				   case 14:
				      return v.gameLtv.d12;
				   case 15:
				      return v.gameLtv.d13;
				   case 16:
				      return v.gameLtv.d14;
				   case 17:
				      return v.gameLtv.d15;
				   case 18:
				      return v.gameLtv.d16;
				   case 19:
				      return v.gameLtv.d17;
				   case 20:
				      return v.gameLtv.d18;
				   case 21:
				      return v.gameLtv.d19;
				   case 22:
				      return v.gameLtv.d20;
				   case 23:
				      return v.gameLtv.d21;
				   case 24:
				      return v.gameLtv.d22;
				   case 25:
				      return v.gameLtv.d23;
				   case 26:
				      return v.gameLtv.d24;
				   case 27:
				      return v.gameLtv.d25;
				   case 28:
				      return v.gameLtv.d26;
				   case 29:
				      return v.gameLtv.d27;
				   case 30:
				      return v.gameLtv.d28;
				   case 31:
				      return v.gameLtv.d29;
				   case 32:
				      return v.gameLtv.d30;
				   case 33:
				      return v.gameLtv.d31;
				   case 34:
				      return v.gameLtv.d32;
				   case 35:
				      return v.gameLtv.d33;
				   case 36:
				      return v.gameLtv.d34;
				   case 37:
				      return v.gameLtv.d35;
				   case 38:
				      return v.gameLtv.d36;
				   case 39:
				      return v.gameLtv.d37;
				   case 40:
				      return v.gameLtv.d38;
				   case 41:
				      return v.gameLtv.d39;
				   case 42:
				      return v.gameLtv.d40;
				   case 43:
				      return v.gameLtv.d41;
				   case 44:
				      return v.gameLtv.d42;
				   case 45:
				      return v.gameLtv.d43;
				   case 46:
				      return v.gameLtv.d44;
				   case 47:
				      return v.gameLtv.d45;
				   case 48:
				      return v.gameLtv.d46;
				   case 49:
				      return v.gameLtv.d47;
				   case 50:
				      return v.gameLtv.d48;
				   case 51:
				      return v.gameLtv.d49;
				   case 52:
				      return v.gameLtv.d50;
				   case 53:
				      return v.gameLtv.d51;
				   case 54:
				      return v.gameLtv.d52;
				   case 55:
				      return v.gameLtv.d53;
				   case 56:
				      return v.gameLtv.d54;
				   case 57:
				      return v.gameLtv.d55;
				   case 58:
				      return v.gameLtv.d56;
				   case 59:
				      return v.gameLtv.d57;
				   case 60:
				      return v.gameLtv.d58;
				   case 61:
				      return v.gameLtv.d59;
				   case 62:
				      return v.gameLtv.d60;
				   case 63:
				      return v.gameLtv.d61;
				   case 64:
				      return v.gameLtv.d62;
				   case 65:
				      return v.gameLtv.d63;
				   case 66:
				      return v.gameLtv.d64;
				   case 67:
				      return v.gameLtv.d65;
				   case 68:
				      return v.gameLtv.d66;
				   case 69:
				      return v.gameLtv.d67;
				   case 70:
				      return v.gameLtv.d68;
				   case 71:
				      return v.gameLtv.d69;
				   case 72:
				      return v.gameLtv.d70;
				   case 73:
				      return v.gameLtv.d71;
				   case 74:
				      return v.gameLtv.d72;
				   case 75:
				      return v.gameLtv.d73;
				   case 76:
				      return v.gameLtv.d74;
				   case 77:
				      return v.gameLtv.d75;
				   case 78:
				      return v.gameLtv.d76;
				   case 79:
				      return v.gameLtv.d77;
				   case 80:
				      return v.gameLtv.d78;
				   case 81:
				      return v.gameLtv.d79;
				   case 82:
				      return v.gameLtv.d80;
				   case 83:
				      return v.gameLtv.d81;
				   case 84:
				      return v.gameLtv.d82;
				   case 85:
				      return v.gameLtv.d83;
				   case 86:
				      return v.gameLtv.d84;
				   case 87:
				      return v.gameLtv.d85;
				   case 88:
				      return v.gameLtv.d86;
				   case 89:
				      return v.gameLtv.d87;
				   case 90:
				      return v.gameLtv.d88;
				   case 91:
				      return v.gameLtv.d89;
				   case 92:
				      return v.gameLtv.d90;
				}
			},
             processCellData:function(index,v){
            	 
	            if(index == 0){
	            	return v.gameLtv.installDay;	 
	             }else if(index == 1){
	            	 return v.gameLtv.sourceOrClientName;
	             }else if(index == 2){
	            	 return v.gameLtv.install;
	             }
				
				var value = $.gameRetentionReport.getItemValue(index,v)
			    
				if(value == 0){
					return '0%';
				}else if(value == null || value == ''){
					return '-';
				}
				
				return Math.round(value * 10000)/100 + '%';
			},
			 brokenAjaxTable:function(ps){
				 
				 var selected = [];
				$('#dt_ajax_wrapper').remove();
				
				var type = $("#channel").val();
				var rate = $("#gameRate").val();
				$('#ajax_data').show();
				$('#life_funnel_chart').hide();
				$('#life_line_chart').hide();
				$('#life_bar_chart').hide();
				$('#data').hide();
				
				var table = $(".template_cache .ajax_table").clone(true);
				var headTemp = $("thead tr",table).first();
				
				if("client" == type){
					$("td.head_type",headTemp).text('服务器');
				}else{
					$("td.head_type",headTemp).text('渠道');
				}
				if(type == 'role_source'){
					$("td.head_install",headTemp).text('创角数');
				}else{
					$("td.head_install",headTemp).text('新注册');
				}
				
				table.attr("id", "dt_ajax");
				$('#ajax_data').append(table);
				
				var hiddenColumn = new Array();
				for(var i=33;i<=92;i++){
					hiddenColumn.push(i);
				}
				
				var columnsDefArr = new Array();//列值定义数组
				columnsDefArr.push(
						{  "bSearchable": false, 
							"bVisible": false, 
							"aTargets": hiddenColumn 
						}
						);
				for(var k=0;k<=92;k++){
	        		columnsDefArr.push({
                        targets: k,
                        render: function (a, b, v, d) {
                        	       return $.gameRetentionReport.processCellData(d.col,v);
                        	}
                    });
	        	}
				
				jqTable = $('#dt_ajax').DataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
					          data: function(d){
					        	  d.id=ps.id;
					        	  d.gameId=ps.gameId;
					        	  d.channel=ps.channel;
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
				    "ordering": false, //排序功能
				    "order": [[ 0, "desc" ]],
				    rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                    },
				    "columnDefs":columnsDefArr
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				
				$('#ajax_data_caption button').click(function (){
			        $(this).attr('disabled',true);
			        $(this).val(2);
			        window.btnTimeout=setInterval(function(){
						$.gameUtil.btnTimeout($('#ajax_data_caption button'),window.btnTimeout);
					  },1000);
			        $("#downLoadForm").submit();
			    });
				
				$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
			}
			
	};	
});
	                    