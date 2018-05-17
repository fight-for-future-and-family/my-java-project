$(function(){
	
	option_line = {
		    tooltip : {trigger: 'axis'},
		    toolbox: {
		        show : true,orient:'vertical',x:'right',y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }
		};
	
	var indicatorsArr = [];
	var dateArrA = [];
	var dateArrB = [];
	var reportsA = null,reportsB = null;
	var ltvsA = null,ltvsB = null;
	
	$(document).ready(function() {
	    $.gameTrend.init();
	});
	
	$.gameTrend={
			init:function(){
				$.gameTrend.initEvent();
				$.gameTrend.initSelectIndicators();
				$.gameTrend.drawYMW();
				$.gameTrend.laydateInit();
				$.gameTrend.submit();
			},
			initEvent:function(){
				$("#dateDimension").change(function(){
					$.gameTrend.dateDimensionChange();
					$.gameTrend.submit();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.gameTrend.redict($(n).attr("id"));
					});
				});
				
				$("#channel").change(function(){
					$.gameTrend.channelChange();
				});
				
				$("#indicators").change(function(){
					$.gameTrend.selectIndicators();
				});
				
				$("#gameA").change(function(){
					var value = $("#channel").val();
					if(value != 'all'){
						$.gameTrend.channelChange();
						$("#s_c").val('3366');
					}else{
						$.gameTrend.submit();
					}
				});
				
				$("#gameB").change(function(){
					$.gameTrend.submit();
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/analysisTool/toGameAnalysisTool.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/analysisTool/toGameAnalysisTool.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			laydateInit:function(){
				if($("#beginTimeA").length > 0){
					laydate({
				    	elem: '#beginTimeA',
				    	choose: function(dates){ //选择好日期的回调
				    		$.gameTrend.submit();
					    }
					});
					laydate({
				    	elem: '#endTimeA',
				    	choose: function(dates){ //选择好日期的回调
				    		$.gameTrend.submit();
					    }
					});
					laydate({
				    	elem: '#beginTimeB',
				    	choose: function(dates){ //选择好日期的回调
				    		$.gameTrend.submit();
					    }
					});
					laydate({
				    	elem: '#endTimeB',
				    	choose: function(dates){ //选择好日期的回调
				    		$.gameTrend.submit();
					    }
					});
				}
			},
			dateDimensionChange:function(){
				if($("#dateDimension").val() != 'day'){
					$("#ch-s").hide();
					$("#ch-c").hide();
					
					$("#channel").val("all"); 
					$("#s_c").remove();
					$("#gameB").show();
					$("#gameB_label").show();
					$("#dateB_label").removeClass('h_kong');
					
					if($("#dateDimension").val() == 'week'){
						$("#day_span_A").hide();
						$("#day_span_B").hide();
						$("#month_week_span_A").show();
						$("#month_week_span_B").show();
						$("#week_begin_A").show();
					    $("#week_begin_B").show();
					    $("#week_end_A").show();
					    $("#week_end_B").show();
					    $("#brspan").show();
					    $("#datetextSpan").show();
					    $(".detail-info").css("min-height","190px");
					    $(".detail-div-ul").css("width","81%");
					    $(".detail-div").css("width","18%");
					    $(".detail-div").css("height","180px");
					}else{
						$("#day_span_A").hide();
						$("#day_span_B").hide();
						$("#month_week_span_A").show();
						$("#month_week_span_B").show();
						$("#week_begin_A").hide();
					    $("#week_begin_B").hide();
					    $("#week_end_A").hide();
					    $("#week_end_B").hide();
					    $("#brspan").show();
					    $("#datetextSpan").show();
					    $(".detail-info").css("min-height","180px");
					    $(".detail-div-ul").css("width","75%");
					    $(".detail-div").css("width","24%");
					    $(".detail-div").css("height","170px");
					}
					
					$.gameTrend.dateSelectChange('year_begin_A', 'month_begin_A', 'week_begin_A', 'w_m_w_span_a_begin', 'm_beginTimeA', $("#dateDimension").val());
					$.gameTrend.dateSelectChange('year_begin_B', 'month_begin_B', 'week_begin_B', 'w_m_w_span_b_begin', 'm_beginTimeB', $("#dateDimension").val());
					$.gameTrend.dateSelectChange('year_end_A', 'month_end_A', 'week_end_A', 'w_m_w_span_a_end', 'm_endTimeA', $("#dateDimension").val());
					$.gameTrend.dateSelectChange('year_end_B', 'month_end_B', 'week_end_B', 'w_m_w_span_b_end', 'm_endTimeB', $("#dateDimension").val());
				}else{
					$("#ch-s").show();
					$("#ch-c").show();
					$("#day_span_A").show();
					$("#day_span_B").show();
					$("#month_week_span_A").hide();
					$("#month_week_span_B").hide();
					$("#brspan").hide();
				    $("#datetextSpan").hide();
					$(".detail-info").css("min-height","150px");
				    $(".detail-div-ul").css("width","65%");
				    $(".detail-div").css("width","34%");
				    $(".detail-div").css("height","130px");
				}
			},
			darwW:function(year,month,spanName,selName){
				var str = 'style="width:64px">';
			    var week = $.date.getWeekCounts(year,month);
			    for(var i=1;i<=week;i++){
			    	str += '<option value="' + i +'">第' + i +'周</option>';
			    }
			    str += '</select>';
			    $("#"+spanName+"").append('<select class="dateSelect" id="'+selName+'" '+str);
			},
			dateSelectChange:function(yearName,monthName,weekName,weekSpanName,dateName,dateDimension){
				var year = $("#"+yearName+"").val();
				var month = Number($("#"+monthName+"").val());
				
				var monthStr = (month+1) < 10 ? "-0" +(month+1) : "-"+(month+1);
		    	if(dateDimension == 'week'){
		    		var oldWeekIndex = $("#"+weekName+"").get(0).selectedIndex;
		    		$("#"+weekName+"").remove();
					$.gameTrend.darwW(year,month,''+weekSpanName+'',''+weekName+'');
					$("#"+weekName+" option").eq(oldWeekIndex).attr("selected",true);
					
					$("#"+weekName+"").change(function(){
						$.gameTrend.dateSelectChangeEvent(this);
				    	$.gameTrend.submit();
					});
					var week = $("#"+weekName+"").val();
					
					$("#"+dateName+"").val($.date.getWeekDate(year,month,week));
		    	}else{
		    		
					$("#"+dateName+"").val(year+ monthStr);
		    	}
			},
			drawYMW:function(){
				var d = new Date();
				var year = d.getFullYear();
			    var month = d.getMonth();
			    
			    var b = new Date();
			    var m = month - 4;
			    var y = year;
			    if(m<0){
			    	m = m+12;
			    	y = y -1;
			    }
			    b.setMonth(m);
		    	b.setYear(y);
			    
		    	$("#month_end_A").val(month);
			    $("#month_end_B").val(month);
			    $("#month_begin_A").val(m);
			    $("#month_begin_B").val(m);
			    
			    $("#year_end_A").val(year);
			    $("#year_end_B").val(year);
			    $("#year_begin_A").val(y);
			    $("#year_begin_B").val(y);
			    
			    $.gameTrend.darwW(year,m,'w_m_w_span_a_begin','week_begin_A');
			    $.gameTrend.darwW(year,m,'w_m_w_span_b_begin','week_begin_B');
			    $.gameTrend.darwW(year,month,'w_m_w_span_a_end','week_end_A');
			    $.gameTrend.darwW(year,month,'w_m_w_span_b_end','week_end_B');
			    
			    $("#month_week_span_A").hide();
			    $("#month_week_span_B").hide();
			    
			    $(".dateSelect").change(function(){
			    	$.gameTrend.dateSelectChangeEvent(this);
			    	$.gameTrend.submit();
				});
			},
			dateSelectChangeEvent:function(n){
				if($(n).attr("id") == 'week_begin_A' 
		    		|| $(n).attr("id") == 'year_begin_A' 
		    		|| $(n).attr("id") == 'month_begin_A'){
		    		
		    		$.gameTrend.dateSelectChange('year_begin_A', 'month_begin_A', 'week_begin_A', 'w_m_w_span_a_begin', 'm_beginTimeA', $("#dateDimension").val());
		    	
		    	}else if($(n).attr("id") == 'week_begin_B' 
		    		|| $(n).attr("id") == 'year_begin_B' 
			    	|| $(n).attr("id") == 'month_begin_B'){
		    		
		    		$.gameTrend.dateSelectChange('year_begin_B', 'month_begin_B', 'week_begin_B', 'w_m_w_span_b_begin', 'm_beginTimeB', $("#dateDimension").val());
		    	
		    	}else if($(n).attr("id") == 'week_end_A' 
		    		|| $(n).attr("id") == 'year_end_A' 
			    	|| $(n).attr("id") == 'month_end_A'){
		    		
		    		$.gameTrend.dateSelectChange('year_end_A', 'month_end_A', 'week_end_A', 'w_m_w_span_a_end', 'm_endTimeA', $("#dateDimension").val());
		    	
		    	}else if($(n).attr("id") == 'week_end_B' 
		    		|| $(n).attr("id") == 'year_end_B' 
			    	|| $(n).attr("id") == 'month_end_B'){
		    		
		    		$.gameTrend.dateSelectChange('year_end_B', 'month_end_B', 'week_end_B', 'w_m_w_span_b_end', 'm_endTimeB', $("#dateDimension").val());
		    	
		    	}
			},
			initSelectIndicators:function(){
				for(var i=1;i<=4;i++){
					indicatorsArr.push(i+'');
					var valStr = '<abbr title="'+$("#indicators [value='"+i+"']").text()+'">';
					var str =  valStr +'<input type="button" class="div-but" indiVal="'+i+'" value="'+ $("#indicators [value='"+i+"']").text() +'"/></abbr>';
					$(".detail-div-but").append(str);
					
					$(".div-but").unbind("dblclick"); 
					$(".div-but").dblclick(function(){
						if(indicatorsArr.length <= 2){
							alert('至少保留2个指标');
							return;
						}
						indicatorsArr.splice($.inArray($(this).attr("indiVal"),indicatorsArr),1);
						$("#"+$(this).val()+"").remove();
						$(this).remove();
						$.gameTrend.brokenLineSubmit();
					});
				}
			},
			selectIndicators:function(){
				var val = $("#indicators option:selected").val();
				if(indicatorsArr.length >= 9){
					alert('同时最多选择9个指标，双击即可删除指标');
					return false;
				}else if($.inArray(val,indicatorsArr) >= 0){
					return false;
				}else{
					indicatorsArr.push(val);
				}
				var valStr = '<abbr title="'+$("#indicators option:selected").text()+'">';
				var str =  valStr +'<input type="button" class="div-but" indiVal="'+val+'" value="'+ $("#indicators option:selected").text() +'"/></abbr>';
				$(".detail-div-but").append(str);
				
				$(".div-but").unbind("dblclick"); 
				$(".div-but").dblclick(function(){
					indicatorsArr.splice($.inArray($(this).attr("indiVal"),indicatorsArr),1);
					$("#"+$(this).val()+"").remove();
					$(this).remove();
					$.gameTrend.brokenLineSubmit();
				});
				
				this.brokenLineSubmit();
			},
			channelChange:function(){
				 var ps={
					     gamesId:$("#gameA option:selected").val(),
					     snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val()
					};
				 
				var value = $("#channel").val();
				if(value == 'all'){
					$("#s_c").remove();
					
					$("#gameB").show();
					$("#gameB_label").show();
					$("#dateB_label").removeClass('h_kong');
					$.gameTrend.submit();
					return;
				}else if(value == 'source'){
					$("#s_c").remove();
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/game/getGameSource.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" style="width:50%"><option value="-1">请选择...</option>';
						    $.each(data.gameSources,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	$.gameTrend.submit();
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
						    var str = '<select id="s_c"><option value="-1">请选择...</option>';
						    $.each(data.gameClients,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	$.gameTrend.submit();
							});
						  }
					});
				}
				
				$("#gameB").hide();
				$("#gameB_label").hide();
				$("#dateB_label").addClass('h_kong');
			},
			checkParam:function(){

				  var ps={
					     gameAId:$("#gameA").val(),
					     gameBId:$("#gameB").val(),
					     dateDimension:$("#dateDimension").val(),
					     channel:$('#channel').val(),
					     beginTimeA:$("input[name='beginTimeA']").val(),
						 endTimeA:$("input[name='endTimeA']").val(),
						 beginTimeB:$("input[name='beginTimeB']").val(),
						 endTimeB:$("input[name='endTimeB']").val(),
						 m_beginTimeA:$("input[id='m_beginTimeA']").val(),
						 m_endTimeA:$("input[id='m_endTimeA']").val(),
						 m_beginTimeB:$("input[id='m_beginTimeB']").val(),
						 m_endTimeB:$("input[id='m_endTimeB']").val(),
						 source:$('#s_c').val(),
						 clientid:$('#s_c').val(),
						 view:$('#view').val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val()
					};
			      
				  if(ps.gameAId == null || ps.gameAId == '-1'){ return null; }
			      if(ps.channel == 'all' && (ps.gameBId == null || ps.gameBId == '')){ return null; }
			      
			      if(ps.channel == null || ps.channel == ''){
			    	  ps.channel = 'all'; 
			       }else if(ps.channel == 'source' && (ps.source != null && ps.source == '-1')){
			    	   return null;
			       }else if(ps.channel == 'client' && (ps.clientid != null && ps.clientid == '-1')){
			    	   return null;
			       }
			      
			      if(ps.dateDimension == null || ps.dateDimension == ''){
			    	  ps.dateDimension = 'day'; 
			      }
			      
			      if(ps.dateDimension == 'day'){
			    	  var dateA = $.gameTrend.processDate(ps.beginTimeA,ps.endTimeA)
				      var dateB = $.gameTrend.processDate(ps.beginTimeB,ps.endTimeB)
				      ps.beginTimeA = dateA[0];
				      ps.endTimeA = dateA[1];
				      ps.beginTimeB = dateB[0];
				      ps.endTimeB = dateB[1];
				      
				      $("input[name='endTimeA']").val(ps.endTimeA);
				      $("input[name='beginTimeA']").val(ps.beginTimeA);
				      $("input[name='endTimeB']").val(ps.endTimeB);
				      $("input[name='beginTimeB']").val(ps.beginTimeB);
			      }else if(ps.dateDimension == 'month'){
			    	  $.gameTrend.processMonthDate(ps.m_beginTimeA,ps.m_endTimeA);
			    	  $.gameTrend.processMonthDate(ps.m_beginTimeB,ps.m_endTimeB);
			      }else{
			    	  $.gameTrend.processWeekDate(ps.m_beginTimeA,ps.m_endTimeA);
			    	  $.gameTrend.processWeekDate(ps.m_beginTimeB,ps.m_endTimeB);
			      }
			      
			      return ps;
				
			},
			processWeekDate:function(m_beginTime,m_endTime){
				var time1 = m_beginTime.split('_')[0];
				var time2 = m_endTime.split('_')[0];
				$.gameTrend.processMonthDate(time1,time2);
			},
			processMonthDate:function(m_beginTime,m_endTime){
				var m_beginTime = new Date(m_beginTime);
		    	  var m_endTime = new Date(m_endTime);
		    	  if(m_endTime.getTime() < m_beginTime.getTime()){
		    		  alert('结束时间不能小于开始时间');
		    		  return null;
		    	  }
			},
			processDate:function(beginTime,endTime){
			    if((endTime == null || endTime == '') && (beginTime == null || beginTime == '')){
			    	var d = new Date();
			    	  endTime = $.date.getDateFullStr(d);
			    	// if(d.getDate() <= 3){
			    	  d.setDate(d.getDate()- 30);
		    	 // }else{
			    //	  d.setDate(1);
		    	//  }
			    	  beginTime = $.date.getDateFullStr(d);
			    	  
			       }else if((endTime == null || endTime == '') && (beginTime != null && beginTime != '')){
			    	  var d = new Date(beginTime);
			    	  d.setDate(d.getDate()+ 30);
			    	  
			    	  endTime = $.date.getDateFullStr(d);
			       }else if((endTime != null && endTime != '') && (beginTime == null || beginTime == '')){
			    	  var d = new Date(endTime);
			    	  d.setDate(d.getDate()- 30);
			    	  
			    	  beginTime = $.date.getDateFullStr(d);
			      }
			    
			    var d = new Date(endTime);
			    var b = new Date(beginTime);
			    if(d.getTime() < b.getTime()){
			         alert('结束时间不能小于开始时间');
		    	     return null;
			     }
			    
			    b.setDate(b.getDate() + 100);
			    if(b.getTime() < d.getTime()){
			    	alert('日期区间不能大于100天！');
			    	return null;
			    }
			      
				return [beginTime,endTime];
			},
			submit:function(){
				var ps = $.gameTrend.checkParam();
				if(ps == null){
					return;
			    }
				
				$.ajax({
					  type: "POST",
					  url: "/panel_bi/analysisTool/getGameContrastData.ac",
					  data: $.param(ps),
					  dataType: "json",
					  success: function(data){
						  
						  if(ps.dateDimension == 'day'){
					    		dateArrA = $.gameUtil.getDay(ps.dateDimension,$("input[name='beginTimeA']").val(),$("input[name='endTimeA']").val()); 
						    	dateArrB = $.gameUtil.getDay(ps.dateDimension,$("input[name='beginTimeB']").val(),$("input[name='endTimeB']").val()); 
						  }else if(ps.dateDimension == 'month'){
					    		dateArrA = $.gameUtil.getDay(ps.dateDimension,$("#m_beginTimeA").val(),$("#m_endTimeA").val()); 
						    	dateArrB = $.gameUtil.getDay(ps.dateDimension,$("#m_beginTimeB").val(),$("#m_endTimeB").val()); 
					    	}else{
					    		dateArrA = $.date.getWeekArr($("#m_beginTimeA").val(),$("#m_endTimeA").val(),$("#week_begin_A").val(),$("#week_end_A").val());
					    		dateArrB = $.date.getWeekArr($("#m_beginTimeB").val(),$("#m_endTimeB").val(),$("#week_begin_B").val(),$("#week_end_B").val());
					    	}
					    	
					    	reportsA = $.gameUtil.proReportData(data.gameAReports,ps.dateDimension,dateArrA);
					    	reportsB = $.gameUtil.proReportData(data.gameBReports,ps.dateDimension,dateArrB);
					    	
					    	if(ps.dateDimension == 'day'){
					    		ltvsA = $.gameTrend.getLtvData(data.ltvsA,dateArrA[1]);
							    ltvsB = $.gameTrend.getLtvData(data.ltvsB,dateArrB[1]);
					    	}else{
					    		ltvsA = $.gameTrend.getLtvData(null,dateArrA[1]);
							    ltvsB = $.gameTrend.getLtvData(null,dateArrB[1]);
					    	}
					    	
					    	
					    	$.gameTrend.brokenLineSubmit();
					  }
				});
			},
			getLtvData:function(reports,dateArr){
				var ir_1 = new Array(),ir_3 = new Array(),ir_7 = new Array();
				$.each(dateArr,function(i,d){
					var isNotExist = true;
					if(reports != null){
						$.each(reports,function(j,ltv){
							if(d == ltv.gameLtv.installDay){
								ir_1.push(Math.round(ltv.gameLtv.d1 * 100*100)/100);
								ir_3.push(Math.round(ltv.gameLtv.d3 * 100*100)/100);
								ir_7.push(Math.round(ltv.gameLtv.d7 * 100*100)/100);
								isNotExist = false;
							}
						});
					}
					
					if(isNotExist){
						ir_1.push('-');
						ir_3.push('-');
						ir_7.push('-');
					}
				});
				
				return [ir_1,ir_3,ir_7];
			},
			getLineData:function(indiVal,arr,ltvreports,rate){
				if(indiVal == 1){
					var pay_arr = new Array();
					$.each(arr.pay_arr,function(i,n){
						pay_arr.push(Math.round(arr.pay_arr[i]/rate*100)/100);
					});
					
					return pay_arr;
				}else if(indiVal == 2){
					var payRate_arr = new Array();
					$.each(arr.payRate_arr,function(i,n){
						payRate_arr.push(Math.round(arr.payRate_arr[i]*100*100)/100);
					});
					
					return payRate_arr;
				}else if(indiVal == 3){
					return arr.dau_wau_mau_arr;
				}else if(indiVal == 4){
					return arr.install_arr;
				}else if(indiVal == 5){
					return arr.dnu_wnu_mnu_arr;
				}else if(indiVal == 6){
					var arpu_arr = new Array();
					$.each(arr.arpu_arr,function(i,n){
						arpu_arr.push(Math.round(arr.arpu_arr[i] / rate*100)/100);
					});
					
					return arpu_arr;
				}else if(indiVal == 7){
					var arppu_arr = new Array();
					$.each(arr.arppu_arr,function(i,n){
						arppu_arr.push(Math.round(arr.arppu_arr[i] / rate*100)/100);
					});
					
					return arppu_arr;
				}else if(indiVal == 8){
					return arr.pay_user_arr;
				}else if(indiVal == 9){
					return arr.pay_cnt_arr;
				}else if(indiVal == 10){
					var rollAmount_arr = new Array();
					$.each(arr.rollAmount_arr,function(i,n){
						rollAmount_arr.push(Math.round(arr.rollAmount_arr[i] / rate*100)/100);
					});
					
					return rollAmount_arr;
				}else if(indiVal == 11){
					return arr.rollUsers_arr;
				}else if(indiVal == 12){
					return arr.rollPayUsers_arr;
				}else if(indiVal == 13){
					return arr.newPu_arr;
				}else if(indiVal == 14){
					var newPayAmount_arr = new Array();
					$.each(arr.newPayAmount_arr,function(i,n){
						newPayAmount_arr.push(Math.round(arr.newPayAmount_arr[i] / rate*100)/100);
					});
					
					return newPayAmount_arr;
				}else if(indiVal == 15){
					return arr.installPu_arr;
				}else if(indiVal == 16){
					var installPayAmount_arr = new Array();
					$.each(arr.installPayAmount_arr,function(i,n){
						installPayAmount_arr.push(Math.round(arr.installPayAmount_arr[i] / rate*100)/100);
					});
					
					return installPayAmount_arr;
				}else if(indiVal == 17){
					return ltvreports == null ? null : ltvreports[0];
				}else if(indiVal == 18){
					return ltvreports == null ? null : ltvreports[1];
				}else if(indiVal == 19){
					return ltvreports == null ? null : ltvreports[2];
				}else if(indiVal == 20){
					return arr.acu_arr;
				}else if(indiVal == 21){
					return arr.pcu_arr;
				}else if(indiVal == 22){
					return arr.act_arr;
				}
			},
			isNeedPoint:function(indiVal){
				if(indiVal == 2 || indiVal == 17 || indiVal == 18 || indiVal == 19){
					return true;
				}
				return false;
			},
			isNeedR:function(indiVal){
				
				if(indiVal == 1 || indiVal == 6
						|| indiVal == 7 || indiVal == 10
						|| indiVal == 14 || indiVal == 16){
					return true;
				}
				return false;
			},
			brokenLineSubmit:function(){
				var x=0;
				var tdStr = '';
				var divStr = '';
				var tabDataArr = [];
				
				var rateA = $("#gameA option:selected").attr("rate");
				var rateB = $("#gameB option:selected").attr("rate")
				if($("#channel").val() != 'all'){
					rateB = rateA;
				}
				for(x in indicatorsArr){
					var indiValue = indicatorsArr[x];
					var text = $("#indicators [value='"+indiValue+"']").text();
					
					var isNeedR = this.isNeedR(indiValue);
					var isNeedPoint = this.isNeedPoint(indiValue);
					
					$("#indi_"+indiValue+"").remove();
					$("#chart").append('<div id="indi_'+indiValue+'" class="chart"></div>');
					
					var aline_data = this.getLineData(indiValue,reportsA,ltvsA,rateA);
					var bline_data = this.getLineData(indiValue,reportsB,ltvsB,rateB);
					
					var dataObj={
							tdText:text,
							isNeedR:isNeedR,
							isNeedPoint:isNeedPoint,
							dataA:aline_data,
					        dataB:bline_data
					};
					
					tabDataArr.push(dataObj);
					this.brokenLine($("#indi_"+indiValue+"")[0],dataObj);
				}
				
				$.gameTrend.brokenTable(tabDataArr);
			},
			brokenLine:function(cc,dataObj){

				require.config({
					paths: {
						echarts: '/js/echarts/dist/',
						theme:'/js/echarts/theme/'
					}
				});
				require(
					[
						'echarts',
						'echarts/chart/bar',
						'echarts/chart/line'
					],
					DrawEChart
				);
				
				function DrawEChart(ec){
					require(['theme/macarons'], function(tarTheme){
						var myChart = ec.init(cc,tarTheme);
						
                        var isSame = false;
						
						var acurrency = $("#gameA option:selected").attr("currency");
						var bcurrency = $("#gameB option:selected").attr("currency");  
						
						var aname = $("#gameA option:selected").text();
						var bname = $("#gameB option:selected").text();
						if(aname == bname || $("#channel").val() != 'all'){
							bname = '对比游戏('+aname+')';
							bcurrency = acurrency;
							isSame = true;
						}
						
						if(dataObj.isNeedR){
							option_line.tooltip = {trigger: 'axis',formatter: '{b}<br>{a}: {c}'+acurrency+'<br>{b1}<br>{a1}: {c1}'+bcurrency+''};
						}else if(dataObj.isNeedPoint){
							option_line.tooltip = {trigger: 'axis',formatter: '{b}<br>{a}: {c}%<br>{b1}<br>{a1}: {c1}%'};
						}else{
							option_line.tooltip = {trigger: 'axis',formatter: '{b}<br>{a}: {c}<br>{b1}<br>{a1}: {c1}'};
//							option_line.tooltip = {trigger: 'axis',formatter: function (params){
//					            return params[0].name + ' <br> '
//					               + params[0].seriesName +': '+ params[0].value + '<br/>'
//					               + params[1].name + ' <br> '
//					               + params[1].seriesName +': '+ params[1].value
//				        }
//							};
						}
							
						option_line.title = {text: dataObj.tdText};
						
						option_line.xAxis = [ {type : 'category',data : dateArrA[0]}, {type : 'category',data : dateArrB[0]}];
						option_line.yAxis = [ {type : 'value',boundaryGap : [ 0, 0.01 ]} ];
						option_line.legend = {data:[aname,bname]};
						option_line.series = [ 
						  {
							name : aname,
							type : 'line',
							data : dataObj.dataA
						  },
						  {
							name : bname,
							type : 'line',
							data : dataObj.dataB
						  }];
						
						myChart.setOption(option_line,true);
					 });
				}
			},
			brokenTable:function(tabDataArr){
				
				var aname = $("#gameA option:selected").text();
				var bname = $("#gameB option:selected").text();
				var acurrency = $("#gameA option:selected").attr("currency");
			    var bcurrency = $("#gameB option:selected").attr("currency");
				
				if($("#channel").val() != 'all'){
					bname = aname;
					bcurrency = acurrency;
				}
				
				var str = '<table id=\"dt\" class="display" cellspacing="0" width="100%">';
				str += '<thead>';
				var tdStr = '';
				var dataStr='';
				
				$.each(tabDataArr,function(i,v){
					tdStr += '<td>'+v.tdText+'</td>';
				});
				
				if(tabDataArr.length > 0){
					$.each(dateArrA[1],function(i,v){
						dataStr += '<tr pv="'+(i+1)+'"><td>'+v+'</td><td>'+aname+'</td>';
						$.each(tabDataArr,function(j,data){
							var tempVal = data.dataA[i];
							if(data.isNeedPoint){
								tempVal = tempVal+'%';
							}
							dataStr += '<td tdIndiClass="td">' + tempVal + '</td>';
						});
						dataStr += '</tr>';
					});
					
					$.each(dateArrB[1],function(i,v){
						dataStr += '<tr pv="'+(i+1)+'"><td>'+v+'</td><td>'+bname+'</td>';
						$.each(tabDataArr,function(j,data){
							var tempVal = data.dataB[i];
							if(data.isNeedPoint){
								tempVal = tempVal+'%';
							}
							dataStr += '<td>' + tempVal + '</td>';
						});
						dataStr += '</tr>';
					});
				}
				
				str += '<tr id="t_titile"><td>日期</td><td>游戏名称</td>'+tdStr+'</tr></thead><tbody>';
				str += dataStr;
				str += '</tbody></table>';
				$("#dt").remove();
				$("#data").append(str);
				
				$.biDataTables.dataTables($('#dt'));
				$('#dt tbody tr').click(function (){
			        $(this).toggleClass('highlight');
			    });
				
				 window.scrollRef=setInterval(function(){
					 var sb=$("div.top");
						if(sb.length<1){
							return ;
						}
						if(window.scrollRef!=null){
							window.clearInterval(window.scrollRef);    
						}
						$("div.top").html('<span class="caption">付费单位 ('+aname+': '+acurrency+',  '+bname+': '+bcurrency+')</span>');
					  },500);
				
			}
	};
});