$(function(){
	
		                    
	
	$(document).ready(function() {
	    $.newEquipRetention.init();
	    $.timeZone.showTimeZone();
	});
		
	$.newEquipRetention={
			init:function(){
				$.newEquipRetention.initEvent();
				$.newEquipRetention.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
				    $('#caption button')[0].disabled = false;
					$.newEquipRetention.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.newEquipRetention.redict($(n).attr("id"));
					});
				});
				
				$(".tab-hd span").click(function(){
					$.newEquipRetention.redict($(this).attr("view"));
				});
				
				$("#indicators").change(function(){
					$.newEquipRetention.change();
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
				var ps = $.newEquipRetention.checkParam();
				if(ps == null){
					return;
			    }
				
				var colorArr = ['#D9FFFD','#97FFF4','#01E7FA',
				                '#01D6E7','#01D1F5','#01C8EB','#39A8F9',
				                '#269FF9','#1899F8','#078EF1','#FC324B'];
				var selected = [];
				var indicators = ps.indicators;
				var isAll = true;
				$('#dt_ajax_wrapper').remove();
				
				var columnsArr = new Array();//列定义数组
				columnsArr.push({"data": "installDate","title":"日期","defaultContent":""});
				columnsArr.push({"data": "newEquip","title":"新增设备","defaultContent":""});
				if(indicators == 'source'){
					columnsArr.push({"data": "sourceOrModel","title":"渠道","defaultContent":""});
					isAll = false;
				}else if(indicators == 'model'){
					columnsArr.push({"data": "sourceOrModel","title":"机型","defaultContent":""});
					isAll = false;
				}
				for(var i=1;i<=7;i++){
					columnsArr.push({"data": null,"title":"D"+i,"defaultContent":""});
				}
				
				var table = isAll ? $(".template_cache .view_table").clone(true) : $(".template_cache .view_table_1").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var columnsDefArr = new Array();//列值定义数组
				for(var i=1;i<=7;i++){
					columnsDefArr.push({
	                    targets: isAll ? i+1 : i+2,
	                    render: function (a, b, v, d) {
	                    	var val = 0;
	                    	switch(d.col){
	                    	case 2:
	                    		val = v.d1;
	                    		break;
	                    	case 3:
	                    		val = isAll ? v.d2 : v.d1;
	                    		break;
	                    	case 4:
	                    		val = isAll ? v.d3 : v.d2;
	                    		break;
	                    	case 5:
	                    		val = isAll ? v.d4 : v.d3;
	                    		break;
	                    	case 6:
	                    		val = isAll ? v.d5 : v.d4;
	                    		break;
	                    	case 7:
	                    		val = isAll ? v.d6 : v.d5;
	                    		break;
	                    	case 8:
	                    		val = isAll ? v.d7 : v.d6;
	                    		break;
	                    	case 9:
	                    		val = v.d7;
	                    		break;
	                    	}
	                    	
	                    	return Math.round(val*10000)/100+'%';
	                    }
	                });
				}
				
				var jq_table = $('#dt_ajax').dataTable( {
			        "processing": true,
			        "serverSide": true,
			        ajax: {
				          data: function(d){
				        	  d.indicators=ps.indicators;
				        	  d.beginTime=ps.beginTime;
				        	  d.endTime=ps.endTime;
				        	  d.snid=ps.snid;
				        	  d.gameId=ps.gameId;
				        	  d.source=ps.source;
				        	  d.model=ps.model;
				        	  d.view=ps.view;
				          },
						  type: "POST",
						  url:"/panel_bi/equip/getEquipmentData.ac?isPageRequest=2"
		              },
			        "pageLength": 40,
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
				    "ordering": false, //排序功能
				    "order": [[ 0, "desc" ]],
				    rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
                        var dataArr = [data.d1,data.d2,data.d3,data.d4,data.d5,data.d6,data.d7];
                        $.each(row.children,function(k,td){
                    		if(isAll && k<2){
                    			return true;
                    		}else if(!isAll && k<3){
                    			return true;
                    		}
                    		var val = isAll ? dataArr[k-2]*100 : dataArr[k-3]*100;
                			if(val >= 100){
                				$(td).css('background-color',colorArr[colorArr.length-1]);
    						}else if(val > 0 && val < 100){
    							var index = Math.floor(val / 10);
    							$(td).css('background-color',colorArr[index]);
    						}
                        });
                    },
				    "columns":columnsArr,
				    "columnDefs":columnsDefArr
			    });

				$('#dt_ajax').removeClass().addClass('table table-striped');
				$('#caption button').unbind(); 
				$('#caption button').click(function (){
					if(confirm("下载可能需要一段时间请耐心等待不要离开页面，是否确认下载？")){
						$(this).attr('disabled',true);
				        $(this).val(2);
				        window.btnTimeout=setInterval(function(){
							$.gameUtil.btnTimeout($('#caption button'),window.btnTimeout);
						  },1000);
				        $("#downLoadForm").submit();
					}
			    });
				
				$.gameUtil.trHighLight($('#dt_ajax tbody'),selected);
			},
			checkParam:function(){
				var ps={
					     indicators:$('#indicators').val(),
						 beginTime:$("input[name='beginTime']").val(),
						 endTime:$("input[name='endTime']").val(),
						 snid:$("input[name='snid']").val(),
						 gameId:$("input[name='gameId']").val(),
						 model:$('#s_c').val(),
						 source:$('#s_c').val(),
						 view:$('#view').val()
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
			      
			      var dateArr = $.gameUtil.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='beginTime']").val(dateArr[0]);
			      $("input[name='endTime']").val(dateArr[1]);
			      
			      return ps;
			},
			change:function(){
				var ps = $.newEquipRetention.checkParam();
				var value = $("#indicators").val();
				if(value == 'all'){
					$("#s_c_span").text('');
					$.newEquipRetention.queryData();
				} else if(value == 'source'){
					$("#s_c_span").text('');
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/equip/getGameSource.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" name="source" style="max-width:200px"><option value="-1">请选择...</option><option value="-99">所有渠道</option>';
						    $.each(data.gameSources,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	$.newEquipRetention.queryData();
							});
						  }
					});
				} else if(value == 'model'){
					$("#s_c_span").text('');
					var span = $("#s_c_span");
				    var str = '<select id="s_c" name="model" style="max-width:240px"><option value="-1">请选择...</option><option value="-99">所有机型</option></select>';
				    span.append(str);
				    
				    $("#s_c").change(function(){
				    	$.newEquipRetention.queryData();
					});
				}
			}
	};	
});
	                    