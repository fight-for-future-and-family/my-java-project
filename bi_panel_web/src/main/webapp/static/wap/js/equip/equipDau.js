$(function(){
	
		                    
	
	$(document).ready(function() {
	    $.equipDau.init();
	    $.timeZone.showTimeZone();
	});
		
	$.equipDau={
			init:function(){
				$.equipDau.initEvent();
				$.equipDau.queryData();
			},
			initEvent:function(){
				$("#query").click(function(){
				    $('#caption button')[0].disabled = false;
					$.equipDau.queryData();
			    });
				
				$(".detail-nav li").each(function(i,n){
					$(n).click(function(){
						$.equipDau.redict($(n).attr("id"));
					});
				});
				
				$(".tab-hd span").click(function(){
					$.equipDau.redict($(this).attr("view"));
				});
				
				$("#indicators").change(function(){
					$.equipDau.change();
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
				var ps = $.equipDau.checkParam();
				if(ps == null){
					return;
			    }
				
				var selected = [];
				var indicators = ps.indicators;
				var isAll = true;
				$('#dt_ajax_wrapper').remove();
				
				var columnsArr = new Array();//列定义数组
				columnsArr.push({"data": "ds","title":"日期","defaultContent":""});
				if(indicators == 'source'){
					columnsArr.push({"data": "source","title":"渠道","defaultContent":""});
					isAll = false;
				}else if(indicators == 'model'){
					columnsArr.push({"data": "model","title":"机型","defaultContent":""});
					isAll = false;
				}
				columnsArr.push({"data": "dau","title":"活跃设备","defaultContent":""});
				columnsArr.push({"data": "newEquip","title":"激活设备","defaultContent":""});
				columnsArr.push({"data": "install","title":"激活且注册设备","defaultContent":""});
				columnsArr.push({"data": null,"title":"活跃老设备","defaultContent":""});
				columnsArr.push({"data": null,"title":"激活率","defaultContent":""});
				columnsArr.push({"data": "uninstall","title":"卸载设备","defaultContent":""});
				
				var table = isAll ? $(".template_cache .view_table").clone(true) : $(".template_cache .view_table_1").clone(true);
				table.attr("id", "dt_ajax");
				$('#data').append(table);
				
				var columnsDefArr = new Array();//列值定义数组
				if(!isAll){
					columnsDefArr.push({ orderable: false, targets: 1 });
				}
				columnsDefArr.push({
                    targets: isAll ? 4 : 5,
                    render: function (a, b, v, d) {
                    	if(v.dau == null || v.newEquip == null){
                    		return '-';
                    	}else{
                    		return v.dau-v.newEquip;
                    	}
                    }
                });
				columnsDefArr.push({
					targets: isAll ? 5 : 6,
							render: function (a, b, v, d) {
								if(v.install == null || v.install == 0
										|| v.newEquip == null || v.newEquip == 0){
									return '0%';
								}else{
									return Math.round(v.install/v.newEquip*10000)/100+'%';
								}
							}
				});
	        	
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
				    "ordering": true, //排序功能
				    "order": [[ 0, "desc" ]],
				    rowCallback:function(row, data) {
                        if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                            $(row).addClass('highlight');
                        }
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
				var ps = $.equipDau.checkParam();
				var value = $("#indicators").val();
				if(value == 'all'){
					$("#s_c_span").text('');
					$.equipDau.queryData();
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
						    	$.equipDau.queryData();
							});
						  }
					});
				} else if(value == 'model'){
					$("#s_c_span").text('');
					var span = $("#s_c_span");
				    var str = '<select id="s_c" name="model" style="max-width:240px"><option value="-1">请选择...</option><option value="-99">所有机型</option></select>';
				    span.append(str);
				    
				    $("#s_c").change(function(){
				    	$.equipDau.queryData();
					});
				}
			}
	};	
});
	                    