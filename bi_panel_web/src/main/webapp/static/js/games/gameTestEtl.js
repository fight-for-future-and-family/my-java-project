$(function(){
	
	
	$(document).ready(function() {
	    $.gameEtl.init();
	});
	
	$.gameEtl={
		init:function(){
			$.gameEtl.initEvent();
			//$.gameEtl.submit();
		},
		initEvent:function(){
			$("#query").click(function(){
				$.gameEtl.submit();
		    });
			
		},
		checkParam:function(){

			  var ps={
					 id:$("input[name='gamesId']").val(),
					 gameId:$("input[name='gameId']").val(),
					 snid:$("input[name='snid']").val(),
					 
					 gameId2:$("input[name='gameId2']").val(),
					 snid2:$("input[name='snid2']").val(),
					 
					 
					 indicators:$('#indicators').val(),
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 source:$('#s_c').val(),
					 view:$('#view').val(),
				};
			  
			  if(ps.id == null || ps.id == ''){ return null; }
		      if(ps.gameId == null || ps.gameId == ''){ return null; }
		      if(ps.snid == null || ps.snid == ''){ return null; }
		      
		      if(ps.indicators == null || ps.indicators == ''){
		    	  ps.indicators = 'all'; 
		       }else if(ps.indicators == 'source' && (ps.source != null && ps.source == '-1')){
		    	   return null;
		       }
		      
		      var dateArr = $.gameUtil.processDate2(ps.beginTime,ps.endTime);
		      if(dateArr ==  null || dateArr == ''){
		    	  return null;
		      }
		      ps.beginTime = dateArr[0];
		      ps.endTime = dateArr[1];
//		      $("input[name='beginTime']").val(dateArr[0]);
//		      $("input[name='endTime']").val(dateArr[1]);
//			
		      return ps;
			
		},
		submit:function(){
			var ps = $.gameEtl.checkParam();
			if(ps == null){
				return;
		    }
			
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/gameTestEtl/getGamePerform.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
					  
					  
                     
				  }
				});
		},
	
	};
});
	                    