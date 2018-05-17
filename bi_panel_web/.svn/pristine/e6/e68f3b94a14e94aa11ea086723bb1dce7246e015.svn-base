$(function(){
	
	$(document).ready(function() {
	    $.chCps.init();
	    $.timeZone.showTimeZone();
	});
		
	var dateArr = null;
	$.chCps={
			init:function(){
				$.chCps.initEvent();
				$.chCps.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
				    $('#caption button')[0].disabled = false;
					$.chCps.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.chCps.redict($(n).attr("id"));
					});
				});
				
				$('#indicators').change(function(){
					if($('#indicators').val() == 'month'){
						$('#day_span').hide();
						$('#month_span').show();
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
					    
				    	$("#month_end").val(month);
					    $("#month_begin").val(m);
					    
					    $("#year_end").val(year);
					    $("#year_begin").val(y);
					}else{
						$('#day_span').show();
						$('#month_span').hide();
					}
				});
			},
//			redict:function(view){
//				window.location.href='/panel_bi/costPer/toCPA.ac?view='+view;
//			},
			redict:function(view){
				var snid = $("input[name='snid']").val();
				var gameId = $("input[name='gameId']").val();
				window.location.href='/panel_bi/costPer/toCPA.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
			},
			queryData:function(){
				var ps = $.chCps.checkParam();
				if(ps == null){
					return;
			    }
				
				var selected = [];
				$('#dt_ajax_wrapper').remove();
				var table = $(".template_cache .view_table").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var gameRate = $('#gameRate').val();
				var columnsDefArr = new Array();//列值定义数组
	        	
				if(ps.indicators != 'source'){
					columnsDefArr.push({ orderable: false, targets: [1,2] });
					columnsDefArr.push({ 
						targets: 0,
						render: function (a, b, v, d) {
								return v.entity.day;
							} });
				}else{
					$("td",$("tr",table).first()).first().remove();
					columnsDefArr.push({ orderable: false, targets: [0,1] });
				}
				columnsDefArr.push({ 
					targets: ps.indicators != 'source' ? 1 : 0,
							render: function (a, b, v, d) {
								return v.entity.source;
							}
				});
	        	columnsDefArr.push({ 
        			targets: ps.indicators != 'source' ? 2 : 1,
        			render: function (a, b, v, d) {
        				return v.sourceName;
        			}
        		});
	        	columnsDefArr.push({ 
	        		targets: ps.indicators != 'source' ? 3 : 2,
	        				render: function (a, b, v, d) {
	        					return v.pu
	        				}
	        	});
	        	columnsDefArr.push({ 
	        		targets: ps.indicators != 'source' ? 4 : 3,
	        				render: function (a, b, v, d) {
	        					return v.paymentCnt;
	        				}
	        	});
	        	columnsDefArr.push({ 
	        		targets: ps.indicators != 'source' ? 5 : 4,
	        				render: function (a, b, v, d) {
	        					return Math.round(v.paymentAmount/gameRate*100)/100;
	        				}
	        	});
	        	
				jq_table = $('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
				          data: function(d){
				        	  d.indicators=ps.indicators;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.view=ps.view;
				        	  d.channel=ps.channel;
				        	  d.snid=ps.snid;
				        	  d.gameId=ps.gameId;
				          },
						  type: "POST",
						  url:"/panel_bi/costPer/getChannelDataList.ac"
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
				    "order": ps.indicators != 'source' ? [[ 0, "desc" ]] : [[ 2, "desc" ]],
		    		rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                    },
				    "columnDefs":columnsDefArr
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				$('#caption button').unbind(); 
				$('#caption button').click(function (){
			        $(this).attr('disabled',true);
			        $(this).val(2);
			        window.btnTimeout=setInterval(function(){
						$.gameUtil.btnTimeout($('#caption button'),window.btnTimeout);
					  },1000);
			        $("#downLoadForm").submit();
			    });
				
				$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
				
			},
			checkParam:function(){
				var ps={
					     indicators:$('#indicators').val(),
						 beginTime:$("input[name='ec_beginTime']").val(),
						 endTime:$("input[name='ec_endTime']").val(),
						 view:$('#view').val(),
						 channel:$('#channel').val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val()
					};
				
			      if(ps.indicators == null || ps.indicators == ''){
			    	  ps.indicators = 'source'; 
			       }
			      if(ps.indicators == 'month'){
			    	  ps.beginTime = $("#year_begin").val()+'-'+$("#month_begin").val();
			    	  ps.endTime = $("#year_end").val()+'-'+$("#month_end").val();
			      }
			      
			      if(ps.channel == null || ps.channel == ''){
			    	  ps.queryType = '-1';
			      }
			      if(ps.indicators != 'month'){
				      var dateArr = $.chCps.processDate(ps.beginTime,ps.endTime);
				      ps.beginTime = dateArr[0];
				      ps.endTime = dateArr[1];
			      
			    	  $("input[name='ec_beginTime']").val(dateArr[0]);
				      $("input[name='ec_endTime']").val(dateArr[1]);
				      
				      $("input[name='beginTime']").val(dateArr[0]);
				      $("input[name='endTime']").val(dateArr[1]);
			      }else{
			    	  $("input[name='beginTime']").val(ps.beginTime);
				      $("input[name='endTime']").val(ps.endTime);
			      }
			      
			      
			      
			      return ps;
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
			    
			    b.setDate(b.getDate() + 180);
			    if(b.getTime() <= d.getTime()){
			    	alert('日期区间不能大于180天！');
			    	return null;
			    }
			      
				return [beginTime,endTime];
			}
	};	
});
	                    