$(function(){
	
	$(document).ready(function() {
	    $.upLoad.init();
	    $.timeZone.showTimeZone();
	});
	
	$.upLoad={
		init:function(){
			$.upLoad.initEvent();
		},
		initEvent:function(){
			
			$("#query").click(function(){
				$.upLoad.submit();
		    });
			
			$("#muban").click(function(){
				//$.upLoad.muban();
			});
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.upLoad.redict($(n).attr("id"));
				});
			});
			
		},
//		redict:function(view){
//			window.location.href='/panel_bi/market/toMarket.ac?id='+$("input[name='gamesId']").val()+'&view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/market/toMarket.ac?id='+$("input[name='gamesId']").val()+'&view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		checkParam:function(){

			  var ps={
					 view:$('#view').val(),
					 file:$("#uploadFile").val(),
					 templeteType:$("#templeteType").val(),
					 snid:$("input[name='snid']").val(),
					 gameId:$("input[name='gameId']").val()
				};
		      
			  if(ps.gameId == null || ps.gameId == '-1'){ return null; }
			  if(ps.workbookType == null || ps.workbookType == ''){
				  ps.workbookType = 'xlsx';
			  }
			  if(ps.file == null || ps.file == ''
				  || ps.file == '未选择文件'){
				  alert('请选择要上传的文件');
				  return null;
			  }
		      
		      return ps;
		},
		submit:function(){
			var ps = $.upLoad.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajaxFileUpload({
				  url:"/panel_bi/market/upload_data.ac?id="+$("input[name='gamesId']").val()+'&filePath='+$("#uploadFile").val()+"&snid="+ps.snid+"&gameId="+ps.gameId,
				  secureuri:false,                       //是否启用安全提交,默认为false
			      fileElementId:'uploadFile',           //文件选择框的id属性
			      dataType:'text',                       //服务器返回的格式,可以是json或xml等
			      success:function(data, status){        //服务器响应成功时的处理函数
			            $('.ajax_loading').hide(); //上传完成隐藏
			        },
			        error:function(data, status, e){ //服务器响应失败时的处理函数
			            $('.ajax_loading').hide(); //上传完成隐藏
			            alert('文件上传失败，请重试！！');
			        }
				});
			
			$('.ajax_loading').show();
			$("#query")[0].disabled = true;
			window.win_up_endMark=setInterval(function(){
	              $.upLoad.getUpEndMark();
	            },500);
		},
		getUpEndMark:function(){
			var ps = $.upLoad.checkParam();
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/market/getUpLoadEndMark.ac",
				  data: $.param(ps),
				  success: function(data){
					  if(data.isUpLoadEnd == '1'){
						  $('.ajax_loading').hide(); //下载完成隐藏
						  $("#query")[0].disabled = false;
						  if(window.win_up_endMark!=null){
								window.clearInterval(window.win_up_endMark);    
						   }
						  $("#file input[type='text']").val('未选择文件');
						  alert('上传成功!\n\n可在【数据查询】中查看。');
					     }
					  },
					  error:function(){
						  $('.ajax_loading').hide(); //下载完成隐藏
						  $("#query")[0].disabled = false;
						  if(window.win_up_endMark!=null){
								window.clearInterval(window.win_up_endMark);    
						   }
						  alert('请求错误！');
					  }
				});
			
		},
		muban:function(){
			var ps = $.dailyReportDownLoad.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/tool/download_data_templete.ac",
				  data: $.param(ps),
				  beforeSend:function(){
                      $('.ajax_loading').show(); //下载时候的提示
                      return true;
                    }, 
				  success: function(data){
					  $('.ajax_loading').hide(); //下载完成隐藏
				  }
				});
		}
	};
});
	                    