$(function(){
	
	$(document).ready(function() {
	    $.dailyReportDownLoad.init();
	    $.timeZone.showTimeZone();
	});
	
	$.dailyReportDownLoad={
		init:function(){
			$.dailyReportDownLoad.initEvent();
		},
		initEvent:function(){
			$.dailyReportDownLoad.checkParam();//只想初始化日期
			$("#query").click(function(){
				$.dailyReportDownLoad.submit();
		    });
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.dailyReportDownLoad.redict($(n).attr("id"));
				});
			});
			
		},
//		redict:function(view){
//			window.location.href='/panel_bi/tool/toGameTool.ac?id='+$("input[name='gamesId']").val()+'&view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/tool/toGameTool.ac?id='+$("input[name='gamesId']").val()+'&view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		checkParam:function(){

			  var ps={
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 view:$('#view').val(),
					 workbookType:$('#workbookType').val(),
					 snid:$("input[name='snid']").val(),
					 gameId:$("input[name='gameId']").val()
				};
		      
			  if(ps.gameId == null || ps.gameId == '-1'){ return null; }
			  if(ps.workbookType == null || ps.workbookType == ''){
				  ps.workbookType = 'xlsx';
			  }
		      
		      var date = $.gameUtil.processDate(ps.beginTime,ps.endTime)
			  ps.beginTime = date[0];
			  ps.endTime = date[1];
			      
			  $("input[name='endTime']").val(ps.endTime);
			  $("input[name='beginTime']").val(ps.beginTime);
		      
		      return ps;
		},
		submit:function(){
            $('.ajax_loading').show(); //下载时候的提示
            $("#query")[0].disabled = true;
                     
            window.win_endMark=setInterval(function(){
              $.dailyReportDownLoad.getEndMark();
            },500);
            $("#downLoadForm").submit();
		},
		getEndMark:function(){
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/tool/getEndMark.ac",
				  success: function(data){
					  if(data.isLoadEnd == '1'){
						  $('.ajax_loading').hide(); //下载完成隐藏
						  $("#query")[0].disabled = false;
						  if(window.win_endMark!=null){
								window.clearInterval(window.win_endMark);    
						   }
					  }
				  },
				  error:function(){
					  $('.ajax_loading').hide(); //下载完成隐藏
					  $("#query")[0].disabled = false;
					  if(window.win_endMark!=null){
							window.clearInterval(window.win_endMark);    
					   }
					  alert('请求错误！');
				  }
				});
			
		}
	};
});
	                    