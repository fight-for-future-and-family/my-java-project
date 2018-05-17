$(function(){
	
	
	$(document).ready(function() {
	    $.gdtQuery.init();
	    $.timeZone.showTimeZone();
	});
	
	$.gdtQuery={
		init:function(){
			$.gdtQuery.initEvent();
			//$.gdtQuery.submit();
		},
		initEvent:function(){
			$.gdtQuery.checkParam();//只想初始化日期
			
			$("#query").click(function(){
				
				$('#dt_gdt_summary_wrapper').remove(); 
				$('#dt_gdt_analysis_wrapper').remove(); 
				$('#dt_market_wrapper').remove(); 
				
				if($("#templeteType").val() == 'scgl'){
					$("#tabs").hide();
				}else{
					$("#tabs").show();
					
					$("[css='tabs']").removeClass("ui-selected");
					$("[css='tabs']").first().addClass("ui-selected");
					
					$("#summary").show();
					$("#analysis").hide();
					$("#marketData").hide();
				}
				
				$.gdtQuery.submit();
		    });
			
			$("[css='tabs']").click(function(){
				$("[css='tabs']").removeClass("ui-selected");
				$(this).addClass("ui-selected");
				if($(this).attr("val") == 'summary' ){
					$("#analysis").hide();
					$("#summary").show();
				}else{
					$("#analysis").show();
					$("#summary").hide();
				}
			});
			
			$("#templeteType").change(function(){
				$('#dt_gdt_summary_wrapper').remove(); 
				$('#dt_gdt_analysis_wrapper').remove(); 
				$('#dt_market_wrapper').remove(); 
				
				$("#summary").hide();
				$("#analysis").hide();
				$("#marketData").hide();
				
				$("#tabs").hide();
			});
			
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.gdtQuery.redict($(n).attr("id"));
				});
			});
			
			
		},
		redict:function(view){
			window.location.href='/panel_bi/market/toMarket.ac?id='+$("input[name='gamesId']").val()+'&view='+view;
		},
		checkParam:function(){
			var ps = {
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 view:$('#view').val(),
					 templeteType:$("#templeteType").val()
			};
			
			if(ps.gameId == null || ps.gameId == '-1'){ return null; }
		      var date = $.gameUtil.processDate(ps.beginTime,ps.endTime)
			  ps.beginTime = date[0];
			  ps.endTime = date[1];
			      
			  $("input[name='endTime']").val(ps.endTime);
			  $("input[name='beginTime']").val(ps.beginTime);
		      
		      return ps;
		},
		submit:function(){
			var ps = $.gdtQuery.checkParam();
			if(ps == null){
				return;
		    }
			$("#query")[0].disabled = true;
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/market/getMarketDats.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
				      $.gdtQuery.brokenTable_summary(data.summaryGDTs);
				      $.gdtQuery.brokenTable_analysis(data.analysisGDTs);
				      $.gdtQuery.brokenTable_marketDatas(data.marketDatas);
				      $("#query")[0].disabled = false;
				  }
				});
		},
		brokenTable_summary:function(reports){
			if(reports == null) return;
			
			$('#dt_gdt_summary_wrapper').remove(); 
			
			var table = $(".template_cache .gdt_summary_tab").clone(true);
			table.attr("id","dt_gdt_summary");
			
			$.each(reports,function(i,v){
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.date",trTemp).text(v.date);
				$("td.exposure",trTemp).text(v.exposure);
				$("td.clickCount",trTemp).text(v.clickCount);
				$("td.clickRate",trTemp).text(Math.round(v.clickRate*10000)/100+'%');
				$("td.install",trTemp).text(v.install);
				$("td.conversionRate",trTemp).text(Math.round(v.conversionRate*10000)/100+'%');
				$("td.allCost",trTemp).text(Math.round(v.allCost*100)/100);
				$("td.d0Cost",trTemp).text(Math.round(v.d0Cost*100)/100);
				$("td.cpc",trTemp).text(Math.round(v.cpc*100)/100);
				$("td.cpa",trTemp).text(Math.round(v.cpa*100)/100);
				
				$('tbody',table).append(trTemp); 
			});
			
			table.removeClass("gdt_summary_tab").addClass("table table-striped");
			$("tbody tr",table).first().remove();
			$('#data_summary').append(table); 
			
			$.biDataTables.dataTables($('#dt_gdt_summary'));
			$('#dt_gdt_summary tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},
		brokenTable_analysis:function(reports){
			if(reports == null) return;
			
			$('#dt_gdt_analysis_wrapper').remove(); 
			
			var table=$(".template_cache .gdt_analysis_tab").clone(true);
		    table.attr("id","dt_gdt_analysis");
			$('#data_analysis').append(table); 
				
			  $.each(reports,function(i,v){
			    var trTemp = $("tbody tr",table).first().clone(true);
				  
				$("td.date",trTemp).text(v.date);
				$("td.advInstall",trTemp).text(v.advInstall);
				$("td.advCost",trTemp).text(Math.round(v.advCost*100)/100);
				$("td.d0Payuser",trTemp).text(v.d0Payuser);
				$("td.payRate",trTemp).text(Math.round(v.payRate*10000)/100 + '%');
				$("td.d0Payment",trTemp).text(v.d0Payment == null ? '-' : Math.round(v.d0Payment*100)/100);
				$("td.d1Payment",trTemp).text(v.d1Payment == null ? '-' : Math.round(v.d1Payment*100)/100);
				$("td.d3Payment",trTemp).text(v.d3Payment == null ? '-' : Math.round(v.d3Payment*100)/100);
				$("td.d7Payment",trTemp).text(v.d7Payment == null ? '-' : Math.round(v.d7Payment*100)/100);
				$("td.d30Payment",trTemp).text(v.d30Payment == null ? '-' : Math.round(v.d30Payment*100)/100);
				$("td.d0Roi",trTemp).text(v.d0Roi == null ? '-' : Math.round(v.d0Roi*10000)/100 + '%');
				$("td.d1Roi",trTemp).text(v.d1Roi == null ? '-' : Math.round(v.d1Roi*10000)/100 + '%');
				$("td.d3Roi",trTemp).text(v.d3Roi == null ? '-' : Math.round(v.d3Roi*10000)/100 + '%');
				$("td.d7Roi",trTemp).text(v.d7Roi == null ? '-' : Math.round(v.d7Roi*10000)/100 + '%');
				$("td.d30Roi",trTemp).text(v.d30Roi == null ? '-' : Math.round(v.d30Roi*10000)/100 + '%');
				
				$('tbody',table).append(trTemp); 
			});
			  
			  table.removeClass("gdt_analysis_tab").addClass("table table-striped");
			  $("tbody tr",table).first().remove();
			
			$.biDataTables.dataTables($('#dt_gdt_analysis'));
			$('#dt_gdt_analysis tbody tr').click(function (){
		        $(this).toggleClass('highlight');
		    });
		},
		brokenTable_marketDatas:function(reports){
			if(reports == null) return;
			
            $('#dt_market_wrapper').remove(); 
			
			var table = $(".template_cache .market_tab").clone(true);
			table.attr("id","dt_market");
			
			$.each(reports,function(i,v){
				var trTemp = $("tbody tr",table).first().clone(true);
				
				$("td.date",trTemp).text(v.ds);
				$("td.exposure",trTemp).text(v.exposure);
				$("td.clickCount",trTemp).text(v.clickCount);
				$("td.clickRate",trTemp).text(v.clickRate*100+'%');
				$("td.install",trTemp).text(v.install);
				$("td.conversionRate",trTemp).text(v.conversionRate);
				$("td.allCost",trTemp).text(v.allCost);
				$("td.d0Cost",trTemp).text(v.d0Cost);
				$("td.cpc",trTemp).text(v.cpc);
				$("td.cpa",trTemp).text(v.cpa);
				
				$('tbody',table).append(trTemp); 
			});
			
			table.removeClass("market_tab").addClass("table table-striped");
			$("tbody tr",table).first().remove();
			$('#data_summary').append(table); 
			
			$.biDataTables.dataTables($('#dt_market'));
		}
	};
});
	                    