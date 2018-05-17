$(function(){
	
	$(document).ready(function() {
	    $.prediction.init();
	    $.timeZone.showTimeZone();
	});
	
	
	var dataVO = null;
	var game = null;
	var dateArr = null;
	$.prediction={
		init:function(){
			$.prediction.initEvent();
			$.prediction.queryData();
		},
		initEvent:function(){
			
			$(".detail-nav li").each(function(i,n){
				$(n).click(function(){
					$.prediction.redict($(n).attr("id"));
				});
			});
			
			$($("[css='dateSelect']")).each(function(i,n){
				$(n).click(function(){
					$("[css='dateSelect']").removeClass("ui-selected");
					$(n).addClass("ui-selected");
					$.prediction.initData($(n).attr("val"));
				});
			});
			
			$("#reset").click(function(){
				if($("#currMonth").hasClass("ui-selected")){
					$.prediction.initData(0);
				}else{
					$.prediction.initData(1);
				}
				
			});
			
			$("#download").click(function(){
				$.prediction.download();
			});
			
			$("#retention,#install").change(function(){
				var table = $("#data_table");
				var trs = $("tbody",table).children();
				var input = $(this);
				var tdNum = input.attr("colnum");
				
				 $.each(trs, function(i,tr){
					 if(i!=0){
						 var tds = $(tr).children();
						 $.each(tds, function(i,td){
							 if(i == tdNum && tdNum== 7){
					            $(td).text(input.val()+'%');
							 }else if(i == tdNum && tdNum== 6){
								 $(td).text(input.val());
							 }
						 });
					 }
				 });
			
			});
			
			$("#calculate").click(function(){
				$.prediction.calculate();
			});
			
			$(".enClick").each(function(i,n){
				$(n).dblclick(function(){
					var table = $("#data_table");
					var trs = $("tbody",table).children();
					var tdNum = $(n).attr("val");
					
					 $.each(trs, function(i,tr){
						 if(i!=0){
							 var tds = $(tr).children();
							 $.each(tds, function(i,td){
								 if(i == tdNum){
						              if($(td).has('input').length){
						            	  $(td).text($(td).children("input").val());
						              }else{
						            	  var put=$("<input type='text' style=\"width:80px;\">");
							              put.val($(td).text());
							              $(td).html(put);
						              }
								 }
							 });
						 }
					 });
				});
			});
		},
//		redict:function(view){
//			window.location.href='/panel_bi/analysisTool/toGameAnalysisTool.ac?view='+view;
//		},
		redict:function(view){
			var snid = $("input[name='snid']").val();
			var gameId = $("input[name='gameId']").val();
			window.location.href='/panel_bi/analysisTool/toGameAnalysisTool.ac?view='+view+'&snid='+snid+'&gameId='+gameId;
		},
		queryData:function(){
			
			var ps ={
					isCurrMonth:0,
					snid:$("input[name='snid']").val(),
					gameId:$("input[name='gameId']").val()
			}
			if(!$("#currMonth").hasClass("ui-selected")){
				ps.isCurrMonth = 1;
			}
			
			$.ajax({
				  type: "POST",
				  url: "/panel_bi/prediction/getPredictionData.ac",
				  data: $.param(ps),
				  dataType: "json",
				  success: function(data){
					  
				    	dataVO = data.gamePrediction;
				    	game = data.game;
				    	
				    	$.prediction.initData(ps.isCurrMonth);
				    	
				    	//也不知道是上面哪个方法哪改变了这个宽度，非要在这写一下才行
				    	var header_w = $(".navbar").width();
						$("#img-repeat").css('width',(header_w-410)+'px');
				  }
			});
		},
		initData:function(isCurrMonth){
			
			var obj = dataVO.entity;
			var sumPayment = obj.sumPayment/game.rate
			
			if(Number(isCurrMonth) == 0){
				dateArr = $.gameUtil.getDay('day',dataVO.currDate,dataVO.monthendDate);
			}else{
				var curDate = new Date(dataVO.monthendDate);
				curDate.setDate(curDate.getDate() + 1);
				var begin = $.date.getDateFullStr(curDate);
				
				var curMonth = curDate.getMonth();
		        curDate.setMonth(curMonth + 1);
		        curDate.setDate(0);
				var end = $.date.getDateFullStr(curDate);
				
				dateArr = $.gameUtil.getDay('day',begin,end);
				sumPayment = 0;
			}
			
    		var table = $("#data_table");
    		var trs = $("tbody",table).children("tr");
    		
    		$.each(trs,function(i,v){
    			if(i != 0){
    				$(v).remove();
    			}
    		});
    		
    		var tr = $("tbody tr",table).first();
    		tr.removeClass();
    		
    		$("td.install", tr).text(Math.round(obj.avgDnu));
    		$("td.d1", tr).text(Math.round(obj.avgD1 * 10000)/100 + '%');
    		$("td.dau", tr).text(Math.round(obj.avgDau));
    		$("td.oldUser", tr).text(Math.round(obj.avgOldUser));
    		$("td.payment", tr).text(Math.round(obj.avgPayment/game.rate));
    		$("td.arpu", tr).text(Math.round(obj.avgArpu/game.rate * 100)/100);
    		$("td.oldUserLossRate", tr).text(Math.round(obj.avgOldUserLossRate * 10000)/100  + '%');
    		
    		var dateArr1 = dateArr[1];
    		var predictAmount = 0;
    		$.each(dateArr1,function(i,v){
    			var trTemp = $("tbody tr",table).first().clone(true);
    			$("td.day", trTemp).text(v);
    			table.append(trTemp);
    			predictAmount += obj.avgPayment/game.rate;
    		});
    		tr.addClass("cankao_tr");
    		
    		
    		$("#predictAmount").text(Math.round((predictAmount + sumPayment)));
    		$("#retention").val(Math.round(obj.avgD1 * 10000)/100);
    		$("#install").val(Math.round(obj.avgDnu));
    		
    		$("#arppu_before").val(Math.round(obj.avgArppu/game.rate * 100)/100);
    		$("#arppu_increase").val(Math.round(obj.avgArppu/game.rate * 100)/100);
    		$("#arppu_use_time").val(1);
    		
    		
    		if(obj.avgDau == null || obj.avgDau == 0){
    			$("#arpu_before").text(0);
        		$("#arpu_increase").text(0);
    		}else{
    			var arpu_temp = Math.round((obj.avgDau * obj.avgPayRate * obj.avgArppu/game.rate/obj.avgDau) * 100)/100;
    			$("#arpu_before").text(arpu_temp);
        		$("#arpu_increase").text(arpu_temp);
    		}
    		
    		$("#payRate_before").val(Math.round(obj.avgPayRate * 10000)/100);
    		$("#payRate_increase").val(Math.round(obj.avgPayRate * 10000)/100);
    		
    		$("#oldUser_before").val(Math.round(obj.avgOldUserLossRate * 10000)/100);
    		$("#oldUser_increase").val(Math.round(obj.avgOldUserLossRate * 10000)/100);
    		$("#oldUser_use_time").val(0);
		},
		calOldUser:function(arr,oldUser_before,oldUser_increase,oldUser_use_time){
			var isIncrease = oldUser_increase - oldUser_before > 0;
			arr[0] = oldUser_before;
			if(oldUser_use_time == 0){
				for(var i=0;i<arr.length-1;i++){
					arr[i+1]=oldUser_before;
				}
			}else{
				for(var i=0;i<arr.length-1;i++){
					if(!isIncrease && arr[i] <= oldUser_increase){
						arr[i+1]=arr[i];
					}else if(isIncrease && arr[i] >= oldUser_increase){
						arr[i+1]=arr[i];
					}else{
						arr[i+1]=(arr[i]*((arr[i]/((oldUser_increase-oldUser_before)/oldUser_use_time)+1)/(arr[i]/((oldUser_increase-oldUser_before)/oldUser_use_time))));
					}
				}
			}
			return arr;
		},
		calculate:function(){
			// 第一步：计算注册（实时更改）
			// 第二步：老玩家流失率（需要用的值）
			var arr = new Array(dateArr[1].length);
			var oldUser_before = Number($("#oldUser_before").val());
			var oldUser_increase = Number($("#oldUser_increase").val());
			var oldUser_use_time = Number($("#oldUser_use_time").val());
			arr = $.prediction.calOldUser(arr,oldUser_before,oldUser_increase,oldUser_use_time);
			// 第三步：次日留存（实时更改）
			
			//第6步：根据付费率、arppu计算arpu（需要用的值）
			var arpu_use_time = Number($("#arppu_use_time").val());
			var arppu_before = $("#arppu_before").val();
			var arppu_increase = $("#arppu_increase").val();
			var payRate_before = $("#payRate_before").val();
			var payRate_increase = $("#payRate_increase").val();
			var arpu_before = 0;
			var arpu_increase = 0;
			var arpu_f = 0;
			arpu_use_time = arpu_use_time < 1 ? 1 : arpu_use_time;
			var isCalArpuBase = false;
			var isArpuIncrease = true;
			
			// 第7步：计算收入（需要用的值）
			var sum_pay = 0;
			
			var table = $("#data_table");
    		var trs = $("tbody",table).children("tr");
    		$.each(trs,function(i,v){
    			if(i > 0){
    				// 第二步：老玩家流失率
    				$("td.oldUserLossRate",v).text(Math.round(arr[i-1]*100)/100 + '%');
    				
    				// 第四步：老用户
    				var ret = $.prediction.getLastTrOldUserValue(trs[i-1]);
    				if(ret.oldUser_update_f == ''){
    					$("td.oldUser",v).text(Math.round(ret.oldUser_f+ret.install_f*ret.d1_f-ret.oldUser_f*ret.oldLossRate_f));
    				}else{
    					$("td.oldUser",v).text(Math.round(ret.oldUser_f+ret.install_f*ret.d1_f-ret.oldUser_f*ret.oldUser_update_f));
    				}
    				
        			// 第5步：计算dau
    				var install = Number($("td.install",v).text());
    				var oldUser = Number($("td.oldUser",v).text());
    				$("td.dau",v).text(install+oldUser);
        				
    				// 第6步：根据付费率,arppu计算arpu
    				if(!isCalArpuBase){
    					var dau = install+oldUser;
    					var arpu_temp = Math.round((dau * (payRate_before / 100) * arppu_before/dau) * 100)/100;
    					
    					var dau_temp = Number($("td.dau",trs[arpu_use_time]).text());
    					var arpu_increase_temp = Math.round((dau_temp * (payRate_increase / 100) * arppu_increase/dau_temp) * 100)/100;
    					
    					arpu_before = arpu_temp;
    					arpu_increase = arpu_increase_temp;
    					
    					$("#arpu_before").text(arpu_temp);
    		    		$("#arpu_increase").text(arpu_increase_temp);
    		    		
    		    		isCalArpuBase = true;
    		    		isArpuIncrease = arpu_increase - arpu_before > 0;
    				}
    				
    				var arppu_update = $("td.arppu_update",v).text();
        			var payRate_update = $("td.payRate_update",v).text();
        			if(payRate_update == '' || arppu_update == ''){
        				if(i == 1){
        					$("td.arpu",v).text(arpu_before);
        				}else{
                			var arppu_update_f = $("td.arppu_update",trs[i-1]).text();
                			var payRate_update_f = $("td.payRate_update",trs[i-1]).text();
        					var arpu_temp = Number($("td.arpu",trs[i-1]).text());
        					var isNoChange = payRate_update_f == '' || payRate_update_f == '';
        					if(isNoChange){
        						if(isArpuIncrease && arpu_temp >= arpu_increase){
                					$("td.arpu",v).text(arpu_temp);
                				}else if(!isArpuIncrease && arpu_temp <= arpu_increase){
                					$("td.arpu",v).text(arpu_temp);
                				}else{
                					$("td.arpu",v).text(Math.round((arpu_temp*((arpu_temp/((arpu_increase-arpu_before)/arpu_use_time)+1)/(arpu_temp/((arpu_increase-arpu_before)/arpu_use_time))))*100)/100);
                				}
        					}else{
        						var resValue = arpu_before;
        						for(var t=2;t<=i;t++){
        							resValue = Math.round((resValue*((resValue/((arpu_increase-arpu_before)/arpu_use_time)+1)/(resValue/((arpu_increase-arpu_before)/arpu_use_time))))*100)/100;
        						}
        						$("td.arpu",v).text(resValue);
        					}
        				}
        			}else{
        				var dau = Number($("td.dau",v).text());
        				var arppu_update_number = Number(arppu_update);
        				var payRate_update = Number(payRate_update.replace("%",""))/100;
        					
        				$("td.arpu",v).text(Math.round((dau*arppu_update_number*payRate_update/dau) *100)/100);
        			}
    				
    				// 第7步：计算收入
    				var dau = Number($("td.dau",v).text());
    				var arpu = Number($("td.arpu",v).text());
    				var pay_temp = Math.round(dau*arpu);
    				sum_pay += pay_temp;
    				$("td.payment",v).text(pay_temp);
    			}
    		  });
    		
    		if(!$("#currMonth").hasClass("ui-selected")){
				$("#predictAmount").text(Math.round(sum_pay));
			}else{
				$("#predictAmount").text(Math.round((sum_pay + dataVO.entity.sumPayment/game.rate)));
			}
		},
		getLastTrOldUserValue:function(v){
			var oldUser_f = Number($("td.oldUser",v).text());
			var install_f = Number($("td.install",v).text());
			var d1_f = Number($("td.d1",v).text().replace("%",""))/100;
			var oldLossRate_f = Number($("td.oldUserLossRate",v).text().replace("%",""))/100;
			var oldUser_update_f = '';
			var oldUser_update_f_temp = $("td.oldUser_update",v).text();
			if(oldUser_update_f_temp != ''){
				oldUser_update_f = Number(oldUser_update_f_temp.replace("%",""))/100;
			}
			var ret={
					oldUser_f:oldUser_f,
					install_f:install_f,
					d1_f:d1_f,
					oldLossRate_f:oldLossRate_f,
					oldUser_update_f:oldUser_update_f
			};
			return ret;
		},
		download:function(){
			var config = {};
			var table = $("#data_table");
			
			var headTr = $("thead",table).children("tr");
			var tds = $(headTr).children();
			var tdsValue = new Array();
			 $.each(tds, function(i,td){
				 tdsValue.push($(td).text());
			 });
			 config.columns = tdsValue
			
			 config.rowData = [];
    		var trs = $("tbody",table).children("tr");
			 $.each(trs, function(i,tr){
				 var tds = $(tr).children();
				 var tdsValue_temp =  new Array();
				 $.each(tds, function(i,td){
					 tdsValue_temp.push($(td).text());
				 });
				 config.rowData.push(tdsValue_temp);
			 });
			 
			 config.parm = {
					 predictAmount:$("#predictAmount").text(),
					 retention:$("#retention").val(),
					 install:$("#install").val(),
					 arppuBefore:$("#arppu_before").val(),
					 arppuIncrease:$("#arppu_increase").val(),
					 arppuUseTime:$("#arppu_use_time").val(),
					 arpuBefore:$("#arpu_before").text(),
					 arpuIncrease:$("#arpu_increase").text(),
					 payRateBefore:$("#payRate_before").val(),
					 payRateIncrease:$("#payRate_increase").val(),
					 oldUserBefore:$("#oldUser_before").val(),
					 oldUserIncrease:$("#oldUser_increase").val(),
					 oldUserUseTime:$("#oldUser_use_time").val()
					 
			 }
			 
			 $("#data_table_value").text(JSON.stringify(config));
			 $("#downLoadForm").submit();
			 
			  //导出为三种格式
			//  $("#download").export($.extend({exporttype : "xls", fileName : "predict.xls"}, config));
			
//			$(table).DataTable({
//	            "dom" : 'T<"clear">' ,
//			    "bFilter" : false,
//			    "paging": false, //翻页功能
//			    "bPaginate" : false,
//			    "lengthChange": false, //改变每页显示数据数量
//			    "searching": false, //过滤功能
//			    "ordering": false, //排序功能
//			    "processing": false,
//			    tableTools: {
//			    	"sSwfPath": "/dataTables/extensions/TableTools/swf/copy_csv_xls.swf",
//	 　　　                                        "aButtons":[ 
//	                            { 
//	                               "sExtends": "xls", 
//	                               "sButtonText": "下载数据" 
//	                             }]
//		        }
//			});
//			
//			$(table).removeClass();
//			$(table).addClass('table table-striped');
			
		}
	};
});
	                    