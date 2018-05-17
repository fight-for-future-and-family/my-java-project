$(function(){
	
	var dateArr = null;
    var itemCountArr = new Array();
    var isHaveData = null;
    
	$(document).ready(function() {
	    $.economyItemData.init();
	    $.timeZone.showTimeZone();
	});
		
	$.economyItemData={
			init:function(){
				$.economyItemData.initEvent();
				$.economyItemData.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
					dateArr = null;
				    $('#caption1 button')[0].disabled = false;
				    if(window.win_brokenBar!=null){
						window.clearInterval(window.win_brokenBar);    
					}
				    
					$.economyItemData.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.economyItemData.redict($(n).attr("id"));
					});
				});
				
				$("#indicators").change(function(){
					$.economyItemData.change();
				});
				
				$("#caption span").each(function(i,n){
					$(n).click(function(e){
//						e.preventDefault();
						
						$("#caption span").removeClass();
						$(this).addClass("ui-selected");
						$("#caption span").last().addClass("last");
						
						var val = $(this).attr("val");
						$("#queryType").val(val);
						$.economyItemData.queryData();
					});
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
			queryData:function(){
				var ps = $.economyItemData.checkParam();
				if(ps == null){
					return;
			    }
				
				dateArr = $.gameUtil.getDay('day',$("input[name='ec_beginTime']").val(),$("input[name='ec_endTime']").val());
				
				$('#dt_ajax_wrapper').remove();
				
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var selected = [];
				var columnsArr = new Array();//列定义数组
				var columnsDefArr = new Array();//列值定义数组
				columnsArr.push({"data": "consumeItem","title":"消费道具","defaultContent":""});
	        	var index = dateArr[1].length;
	        	for(var i=1;i<=index;i++){
	        		columnsArr.push({"data": null,"title":dateArr[1][i-1],"defaultContent": ""});
	        	}
	        	
	        	columnsDefArr.push({ orderable: false, targets: 0 });
	        	for(var k=1;k<=index;k++){
	        		columnsDefArr.push({
                        targets: k,
                        render: function (a, b, v, d) {
                        	       return $.economyItemData.processCellData(d.col,v);
                        	}
                    });
	        	}
	        	
				jq_table = $('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
				          data: function(d){
				        	  d.indicators=ps.indicators;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.beginClientid=ps.beginClientid;
				        	  d.endClientid=ps.endClientid;
				        	  d.view=ps.view;
				        	  d.queryType=ps.queryType;
				        	  d.groupType=ps.groupType;
				          },
						  type: "POST",
						  url:"/panel_bi/economy/getGameEconomy.ac"
//							  ,
//						  success : function(data,callback,settings){
//							  callback(
//								  $.economyItemData.initChartData(data)
//							  );
//						  }
		              },
			        "pageLength": 10,
				    "language":{
				    	"url": "/dataTables/chinese.json"
				    },
			        "deferRender": true,
				    "sPaginationType" : "full_numbers",
			        "dom" : '<"clear"><"top">frt<"bottom"ip><"clear">' ,
					"bSort" : true,
				    "bFilter" : true,
				    "paging": true, //翻页功能
				    "lengthChange": false, //改变每页显示数据数量
				    "searching": true, //过滤功能
				    "ordering": true, //排序功能
				    "order": [[ 1, "desc" ]],
				    rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                    },
				    "columns":columnsArr,
				    "columnDefs":columnsDefArr
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				$('#caption1 button').unbind("click"); 
				$('#caption1 button').click(function (){
					if(confirm("下载可能需要一段时间请耐心等待不要离开页面，是否确认下载？")){
						$(this).attr('disabled',true);
				        $(this).val(2);
				        window.btnTimeout=setInterval(function(){
							$.gameUtil.btnTimeout($('#caption1 button'),window.btnTimeout);
						  },1000);
				        $("#downLoadForm").submit();
					}
			    });
			},
			processCellData:function(index,v){
				
				var value = $.economyItemData.getItemValue(index,v)
			    
				if(value == 0){
					return 0;
				}else if(value == null || value == ''){
					return '';
				}
				
				var queryType = $("#queryType").val();
				if("amount" == queryType){
					var amount_point = Math.round((value / itemCountArr[index-1]) * 10000)/100;
					return '<span title="'+amount_point+'%">'+value+'</span>';
				}else{
					var point = Math.round((value / itemCountArr[index-1]) * 10000)/100;
					return '<span title="'+point+'%">'+value+'</span>';
				}
			},
			checkParam:function(){
				var ps={
					     indicators:$('#indicators').val(),
						 beginTime:$("input[name='ec_beginTime']").val(),
						 endTime:$("input[name='ec_endTime']").val(),
						 gameId:$("input[name='gameId']").val(),
					     snid:$("input[name='snid']").val(),
						 beginClientid:$('#b_s_c').val(),
						 endClientid:$('#e_s_c').val(),
						 view:$('#view').val(),
						 queryType:$('#queryType').val(),
						 groupType:$('#groupType').val()
					};
			      
			      if(ps.indicators == null || ps.indicators == ''){
			    	  ps.indicators = 'all'; 
			       }else if(ps.indicators == 'client'){
			    	   if(ps.beginClientid != null && ps.beginClientid == '-1'){
			    		   return null;
			    	   }
			    	   if(ps.endClientid != null && ps.endClientid == '-1'){
			    		   return null;
			    	   }
			       }
			      
			      if(ps.queryType == null || ps.queryType == ''){
			    	  ps.queryType = 'amount';
			      }
			      if(ps.groupType == null || ps.groupType == ''){
			    	  ps.groupType = 'first';
			      }
			      
			      var dateArr = $.economyItemData.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='ec_beginTime']").val(dateArr[0]);
			      $("input[name='ec_endTime']").val(dateArr[1]);
			      
			      return ps;
			},
			getItemValue:function(index,v){
				var tempValue = null;
	    		switch(index){
	    		case 1:
	    			tempValue = v.day1;
	    			break;
	    		case 2:
	    			tempValue = v.day2;
	    			break;
	    		case 3:
	    			tempValue = v.day3;
	    			break;
	    		case 4:
	    			tempValue = v.day4;
	    			break;
	    		case 5:
	    			tempValue = v.day5;
	    			break;
	    		case 6:
	    			tempValue = v.day6;
	    			break;
	    		case 7:
	    			tempValue = v.day7;
	    			break;
	    		case 8:
	    			tempValue = v.day8;
	    			break;
	    		case 9:
	    			tempValue = v.day9;
	    			break;
	    		case 10:
	    			tempValue = v.day10;
	    			break;
	    		case 11:
	    			tempValue = v.day11;
	    			break;
	    		case 12:
	    			tempValue = v.day12;
	    			break;
	    		case 13:
	    			tempValue = v.day13;
	    			break;
	    		case 14:
	    			tempValue = v.day14;
	    			break;
	    		case 15:
	    			tempValue = v.day15;
	    			break;
	    		}
	    		return tempValue;
			},
			processDate:function(beginTime,endTime){
			    if((endTime == null || endTime == '') && (beginTime == null || beginTime == '')){
			    	  var d = new Date();
			    	  d.setDate(d.getDate() - 1);
			    	  endTime = $.date.getDateFullStr(d);
			    	  
			    	  d = new Date(endTime);
			    	  d.setDate(d.getDate()- 7);
			    	  beginTime = $.date.getDateFullStr(d);
			    	  
			       }else if((endTime == null || endTime == '') && (beginTime != null && beginTime != '')){
			    	  var d = new Date(beginTime);
			    	  d.setDate(d.getDate()+ 7);
			    	  
			    	  endTime = $.date.getDateFullStr(d);
			       }else if((endTime != null && endTime != '') && (beginTime == null || beginTime == '')){
			    	  var d = new Date(endTime);
			    	  d.setDate(d.getDate()- 7);
			    	  
			    	  beginTime = $.date.getDateFullStr(d);
			      }
			    
			    var d = new Date(endTime);
			    var b = new Date(beginTime);
			    if(d.getTime() < b.getTime()){
			         alert('结束时间不能小于开始时间');
		    	     return null;
			     }
			    
			    b.setDate(b.getDate() + 15);
			    if(b.getTime() < d.getTime()){
			    	alert('日期区间不能大于15天！');
			    	return null;
			    }
			      
				return [beginTime,endTime];
			},
			change:function(){

				var value = $("#indicators").val();
				if(value == 'all'){
					$("#s_c_span").text('');
					$.economyItemData.submit();
				} else if(value == 'client'){
					$("#s_c_span").text('');
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/economy/getGameClient.ac",
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str_b = '<select id="b_s_c" name="b_s_c"';
						    var str_e = '<select id="e_s_c" name="e_s_c"';
						    var str = ' style="max-width:240px"><option value="-1">请选择...</option>';
						    $.each(data.gameClients,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str_b + str);
						    span.append(" 至  ");
						    span.append(str_e + str);
						  }
					});
				}
			}
	};	
});
	                    