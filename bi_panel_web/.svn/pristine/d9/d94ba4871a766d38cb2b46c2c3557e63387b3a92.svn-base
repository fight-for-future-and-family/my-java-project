$(function(){
	var date=[0,2,4,6,8,10,12,14,16,18,20,22,24],x = "时",dateIndex = 0;
	$('.de-line-ul li').on("click",function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		dateIndex = parseInt($(this).index(),10);
		switch(dateIndex){
			case 0:
				date=[0,2,4,6,8,10,12,14,16,18,20,22,24];
				x = "时";
				brokenLine();
				break;
			case 1:
				date=[1,2,3,4,5,6,7];
				x = "天";
				brokenLine();
				break;
			case 2:
				date=[1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31];
				x = "天";
				brokenLine();
				break;
			case 3:
				date=[1,2,3,4,5,6,7,8,9,10,11,12];
				x = "月";
				brokenLine();
				break;
			default:
				break;
		}
	});
	
	if ($("#beginTime").length > 0){
		laydate({
	    	elem: '#beginTime',
		});
		laydate({
	    	elem: '#endTime'
		});
	}
	
	if ($("#ec_beginTime").length > 0){
		laydate({
			elem: '#ec_beginTime',
		});
	}
	
	if($("#ec_endTime").length > 0){
		laydate({
			elem: '#ec_endTime'
		});
	}
	
	if($("#onlineDate").length > 0){
		laydate({
	    	elem: '#onlineDate'
		});
	}
	
	$.date={
		//计算当月总天数
		getCountDays:function(curDate){
			//var curDate = new Date();
	          /* 获取当前月份 */
	          var curMonth = curDate.getMonth();
	         /* 获取下月日期 */
	         curDate.setMonth(curMonth + 1);
	         /* 将日期设置为0, 获取上个月的最后一天,相当于date-1 */
	         curDate.setDate(0);
	         /* 返回当月的天数 */
	         return curDate.getDate();
		},
		//每周的起始结束时间
		getWeekDate:function(year,month,week) {

		      var firstDate = new Date(year,month,1,0,0,1,0);    //该月的第一天的年月日。
		      var firstDay = firstDate.getDay(); //该月第一天是周几   （日一二三四五六   ----对应数字：0、1、2、3、4、5、6）
		      var onedayTime = 24 * 60 * 60 * 1000;
		      if(week == 1){
		    	  var begin = new Date(firstDate);
		    	  var end = new Date(firstDate);
		    	  begin.setTime(firstDate.getTime() - ((firstDay - 1) < 0 ? 6 : (firstDay - 1))*onedayTime);
		    	  end.setTime(begin.getTime() + 6 * onedayTime);
		    	  return $.date.getDateFullStr(begin)+'_'+$.date.getDateFullStr(end);
		      }else{
		    	  var begin = new Date(firstDate);
		    	  var end = new Date(firstDate);
		    	  
		    	  firstDay = firstDay == 0 ? 7 : firstDay;
		    	  begin.setDate(firstDay + (week-1)*7-(firstDay-1)*2);
		    	  end.setTime(begin.getTime() + 6 * onedayTime);
		    	  
		    	  return $.date.getDateFullStr(begin)+'_'+$.date.getDateFullStr(end);
		      }
		  },
		  //计算该月总周数
		  getWeekCounts:function(year,month) { 
			  year = Number(year);
			  month = Number(month);
			  var firstDay = new Date(year, month, 1).getDay(); //这个月的第一天 --->(拿到这个月第一天是星期几 )
		      var lastDay = new Date(year, month+1,0).getDate();  //这个月的最后一天  --->(拿到这个月有多少天 )
		      switch(firstDay){
		      case 1:
		      case 2:
		      case 3:
		      case 4:
		      case 5:
		    	  return 5;
		      case 6:
		    	  if(lastDay == 28 || lastDay == 29 || lastDay == 30){
		    		  return 5;
		    	  }else{
		    		  return 6;
		    	  }
		      case 0:
		    	  if(lastDay == 28 || lastDay == 29){
		    		  return 5
		    	  }else{
		    		  return 6;
		    	  }
		      }
		  },
		getWeekArr:function(beginDate,endDate,beginWeek,endWeek){
			var time1 = beginDate.split('_');
			var time2 = endDate.split('_');
			
			var beginTime = new Date(time1[0]);
			var endTime = new Date(time2[0]);
			
			//避免跨月周造成的周数不一致
			var dateStr1 = this.getWeekDate(beginTime.getFullYear(), beginTime.getMonth(), beginWeek);
			var dateStr2 = this.getWeekDate(endTime.getFullYear(), endTime.getMonth(), endWeek);
			if(dateStr1 != beginDate){
				beginTime = new Date(time1[1]);
			}
			if(dateStr2 != endDate){
				endTime = new Date(time2[1]);
			}
			var fullDateStr = new Array();
			var charDateStr = new Array();
			
			dateStr2 = this.getWeekDate(endTime.getFullYear(), endTime.getMonth(), endWeek);
			
			var index = 1;
			var isContinue = true;
			do{
				var year = beginTime.getFullYear();
				var month = beginTime.getMonth();
				var weekCount = this.getWeekCounts(year, month);
				for(var week = beginWeek;week<=weekCount;week++){
					dateStr1 = this.getWeekDate(year, month, week);
					
					if(dateStr1 == fullDateStr[fullDateStr.length-1]){
						fullDateStr.push(dateStr1);
						charDateStr[charDateStr.length -1] = charDateStr[charDateStr.length -1] +'('+ ((beginTime.getMonth()+1)+'月_'+week+'周')+')';
					}else{
						fullDateStr.push(dateStr1);
						charDateStr.push((beginTime.getMonth()+1)+'月_'+week+'周');
					}
					if(dateStr1 == dateStr2){
						isContinue = false;
						break;
					}
				}
				beginTime.setMonth(beginTime.getMonth() + 1);
				beginWeek = 1;
				index += 1;
			}while(isContinue && index <=30);
			return [charDateStr,fullDateStr];
		},
		getDateFullStr:function(d){
			var year = d.getFullYear();
		    var month = (d.getMonth()+1) < 10 ? "-0" +(d.getMonth()+1) : "-"+(d.getMonth()+1);
		    var date = d.getDate() < 10 ? "-0"+d.getDate() : "-"+d.getDate();
		    return '' + year + month + date;
		},
		getDateMonthDayStr:function(d){
		    var month = (d.getMonth()+1) < 10 ? "0" +(d.getMonth()+1) : ""+(d.getMonth()+1);
		    var date = d.getDate() < 10 ? "-0"+d.getDate() : "-"+d.getDate();
		    return "" + month + date;
		},
		getDateYearMonthStr:function(d){
			var year = d.getFullYear();
		    var month = (d.getMonth()+1) < 10 ? "-0" +(d.getMonth()+1) : "-"+(d.getMonth()+1);
		    return "" +year+ month;
		},
		getBeforeTodayDate:function(bd){
			var d = new Date();
			var end = $.date.getDateFullStr(d);
			d.setDate(d.getDate()-bd);
			var begin = $.date.getDateFullStr(d);
			return [begin,end];
		},
		getBeforeDate:function(dstr,bd){
			var d = new Date(dstr);
			d.setDate(d.getDate()-bd);
			var begin = $.date.getDateFullStr(d);
			return begin;
		},
		getSecond:function(d){
			var hour = d.getHours();
			var min = d.getMinutes();
			var s = d.getSeconds();
			
			hour = hour < 10 ? "0"+hour : ""+hour;
			min = min < 10 ? "0"+min : ""+min;
			s = s < 10 ? "0"+s : ""+s;
			
			return ""+hour+":"+min+":"+s;
		},
		getDateSecond:function(d){
			return $.date.getDateFullStr(d) + " " + $.date.getSecond(d);
		}
	};

});