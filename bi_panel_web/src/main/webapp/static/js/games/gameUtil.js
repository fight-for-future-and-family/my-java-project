$(function(){
	
	$.gameUtil={
			getDay:function(indicators,beginDay,endDay){
				var d = new Date(beginDay);
				var dateArr = new Array();
				var dateArr_year = new Array();
				
				if(indicators == 'day'){
					var s = '';
					dateArr.push($.date.getDateMonthDayStr(d));
					dateArr_year.push($.date.getDateFullStr(d));
					var index = 0;
					
				    while(beginDay != endDay && s != endDay && index != 90){
				    	d.setDate(d.getDate()+1);
				    	s = $.date.getDateFullStr(d);
					    dateArr.push($.date.getDateMonthDayStr(d));
					    dateArr_year.push(s);
					    index ++;
				    }
				    return [dateArr,dateArr_year];
				}else if(indicators == 'month'){
					var s = '';
					var end = new Date(endDay);
					dateArr.push((d.getMonth()+1)+"月");
					dateArr_year.push($.date.getDateYearMonthStr(d));
					var index = 0;
					
				    while(d.getMonth() != end.getMonth() && s != endDay && index != 12){
				    	d.setMonth(d.getMonth()+1);
				    	s = $.date.getDateYearMonthStr(d);
					    
					    var dd =(d.getMonth()+1)+"月";
					    dateArr.push(dd);
					    dateArr_year.push(s);
					    index ++;
				    }
				    return [dateArr,dateArr_year];
				}
			},
			proReportData:function(reports,indicators,dateArr){//处理日报/周报/月报数据
				
				var pay_arr = new Array();
				dau_wau_mau_arr = new Array();
				install_arr = new Array();
				dnu_wnu_mnu_arr = new Array();
			    arpu_arr = new Array();
                arppu_arr = new Array();
                pay_user_arr = new Array();
                pay_cnt_arr = new Array();
                payRate_arr = new Array();
                rollUsers_arr = new Array();
                rollPayUsers_arr = new Array();
                rollAmount_arr = new Array();
                newPu_arr = new Array();
                newPayAmount_arr = new Array();
                installPu_arr = new Array();
                installPayAmount_arr = new Array();
                acu_arr = new Array();
                act_arr = new Array();
                pcu_arr = new Array();
				
		    	for(var i=0;i<dateArr[1].length;i++){
		    		pay_arr.push(0);
					dau_wau_mau_arr.push(0);
					install_arr.push(0);
					dnu_wnu_mnu_arr.push(0);
				    arpu_arr.push(0);
	                arppu_arr.push(0);
	                pay_user_arr.push(0);
	                pay_cnt_arr.push(0);
	                payRate_arr.push(0);
	                rollUsers_arr.push(0);
	                rollPayUsers_arr.push(0);
	                rollAmount_arr.push(0);
	                newPu_arr.push(0);
	                newPayAmount_arr.push(0);
	                installPu_arr.push(0);
	                installPayAmount_arr.push(0);
	                acu_arr.push(0);
	                act_arr.push(0);
	                pcu_arr.push(0);
		    	}
		    	
		    	$.each(dateArr[1], function(i,date){
		    		var isExist = false;
		    		if(reports != null && reports.length != 0){
		    			$.each(reports, function(j,rp){
			    			if((indicators == 'day' ? rp.day : indicators == 'month' ? rp.month : rp.week) == date){
			    				pay_arr[i] = rp.paymentAmount;
								install_arr[i] = rp.install;
							    arpu_arr[i] = rp.arpu;
				                arppu_arr[i] = rp.arppu;
				                pay_user_arr[i] = rp.pu;
				                payRate_arr[i] = rp.payRate;
				                newPu_arr[i] = rp.newPu;
				                newPayAmount_arr[i] = rp.newPayAmount;
				                installPu_arr[i] = rp.installPu;
				                installPayAmount_arr[i] = rp.installPayAmount;
				                acu_arr[i] = rp.acu;
				                act_arr[i] = Math.round(rp.avgUserTime / 60*10)/10;// 数据库为秒，转化成分钟
				                pcu_arr[i] = rp.pcu;
			    				
			    				if(indicators == 'day'){
			    					dau_wau_mau_arr[i] = rp.dau;
			    					dnu_wnu_mnu_arr[i] = rp.dnu;
			    					pay_cnt_arr[i] = rp.paymentCnt;
			    					rollUsers_arr[i] = rp.rollUsers;
			 				        rollPayUsers_arr[i] = rp.rollPayUsers;
			 				        rollAmount_arr[i] = rp.rollAmount;
			    				}else if(indicators == 'week'){
			    					dau_wau_mau_arr[i] = rp.wau;
			    					dnu_wnu_mnu_arr[i] = rp.wnu;
			    				}else{
			    					dnu_wnu_mnu_arr[i] = rp.mnu;
			    					dau_wau_mau_arr[i] = rp.mau;
			    				}
				    		}
				    	}); 
		    		}
		    	});
		    	
		    	
		    	function ReportsObj(pay_arr,dau_wau_mau_arr,install_arr,dnu_wnu_mnu_arr,arpu_arr,
                                          arppu_arr,pay_user_arr,pay_cnt_arr,payRate_arr,
                                          rollUsers_arr,rollPayUsers_arr,rollAmount_arr,
                                          newPu_arr,newPayAmount_arr,installPu_arr,
                                          installPayAmount_arr,acu_arr,act_arr,pcu_arr){
		    		this.pay_arr = pay_arr;
		    		this.dau_wau_mau_arr = dau_wau_mau_arr;
		    		this.install_arr = install_arr;
		    		this.dnu_wnu_mnu_arr = dnu_wnu_mnu_arr;
		    		this.arpu_arr = arpu_arr;
		    		this.arppu_arr = arppu_arr;
		    		this.pay_user_arr = pay_user_arr;
		    		this.pay_cnt_arr = pay_cnt_arr;
		    		this.payRate_arr = payRate_arr;
		    		this.rollUsers_arr = rollUsers_arr;
		    		this.rollPayUsers_arr = rollPayUsers_arr;
		    		this.rollAmount_arr = rollAmount_arr;
		    		this.newPu_arr = newPu_arr;
		    		this.newPayAmount_arr = newPayAmount_arr;
		    		this.installPu_arr = installPu_arr;
		    		this.installPayAmount_arr = installPayAmount_arr;
		    		this.acu_arr = acu_arr;
		    		this.act_arr = act_arr;
		    		this.pcu_arr = pcu_arr;
		    	}
		    	
		    	ReportsObj.prototype = {
		    			getCalculateValue : function(arr){
		    				var count = 0,x = 0;
		    				var tempArr = [];
		    				for (x in arr){
		    				   tempArr.push(Number(arr[x]));
		    				   count += tempArr[x];
		    				}
		    				tempArr.sort(function(a,b){return a>b?1:-1});
		    				var temp = {
		    						max:tempArr[tempArr.length - 1],
		    						min:tempArr[0],
		    						count:count,
		    						avg:count / tempArr.length
		    					};
		    				return temp;
		    			},
		    			getRatePointValue : function(arr){
		    				var pointArr = new Array();
		    				var x = 0;
		    				for (x in arr){
		    					pointArr.push(Math.round(Number(arr[x]) *100*100)/100);
		    				}
		    				return pointArr;
		    			}
		    	}
		    	
		    	var obj = new ReportsObj(pay_arr,dau_wau_mau_arr,install_arr,dnu_wnu_mnu_arr,arpu_arr,
                        arppu_arr,pay_user_arr,pay_cnt_arr,payRate_arr,
                        rollUsers_arr,rollPayUsers_arr,rollAmount_arr,
                        newPu_arr,newPayAmount_arr,installPu_arr,
                        installPayAmount_arr,acu_arr,act_arr,pcu_arr);
		    	
		    	return obj;
			},
			
		proReportData2:function(reports,indicators,dateArr){//测试报告
				
				
                jhsb_arr = new Array();
                xzwj_arr = new Array();
                jhzczhl_arr = new Array();
                pay_arr = new Array();
                payRate_arr = new Array();
		    	for(var i=0;i<dateArr[1].length;i++){
		    		jhsb_arr.push(0);
		    		xzwj_arr.push(0);
		    		jhzczhl_arr.push(0);
		    		pay_arr.push(0);
		    		payRate_arr.push(0);
		    	}
		    	$.each(dateArr[1], function(i,date){
		    		var isExist = false;
		    		if(reports != null && reports.length != 0){
		    			$.each(reports, function(j,rp){
			    			if((indicators == 'day' ? rp.ds : indicators == 'month' ? rp.month : rp.week) == date){
			    				jhsb_arr[i] = rp.new_equip;
			    				xzwj_arr[i] = rp.dnu;
			    				
			    				str = rp.jihuo_xinzeng_rate.substr(0,rp.jihuo_xinzeng_rate.length-1);
			    				
			    				jhzczhl_arr[i] = str;
			    				pay_arr[i] = rp.pay_arr;
			    				payRate_arr[i] = rp.pay_rate;
				    		}
				    	}); 
		    		}
		    	});
		    	
		    	
		    	function ReportsObj(jhsb_arr,xzwj_arr,jhzczhl_arr,pay_arr,payRate_arr){
		    	
		    		this.jhsb_arr = jhsb_arr;
		    		this.xzwj_arr = xzwj_arr;
		    		this.jhzczhl_arr = jhzczhl_arr;
		    		this.pay_arr = pay_arr;
		    		this.payRate_arr = payRate_arr;
		    	}
		    	
		    	ReportsObj.prototype = {
		    			getCalculateValue : function(arr){
		    				var count = 0,x = 0;
		    				var tempArr = [];
		    				for (x in arr){
		    				   tempArr.push(Number(arr[x]));
		    				   count += tempArr[x];
		    				}
		    				tempArr.sort(function(a,b){return a>b?1:-1});
		    				var temp = {
		    						max:tempArr[tempArr.length - 1],
		    						min:tempArr[0],
		    						count:count,
		    						avg:count / tempArr.length
		    					};
		    				return temp;
		    			},
		    			getRatePointValue : function(arr){
		    				var pointArr = new Array();
		    				var x = 0;
		    				for (x in arr){
		    					pointArr.push(Math.round(Number(arr[x]) *100*100)/100);
		    				}
		    				return pointArr;
		    			}
		    	}
		    	
		    	var obj = new ReportsObj(jhsb_arr,xzwj_arr,jhzczhl_arr,pay_arr,payRate_arr);
		    	
		    	return obj;
			},
			change:function(exclass){
				var ps = $.gameUtil.checkChannelChangeParam();
			    if(ps == null){
				  return;
		        }
				var value = $("#indicators").val();
				if(value == 'all'){
					/*
					$("#dau_cu_chart").show();
					$("#data").show();
					$("#life_bar_chart2").hide();
					$("#data2").hide();
					
					$("#life_bar_chart").hide();
					$("#data4").hide();
					*/
					$("#s_c").remove();
					exclass.submit();
				}else if(value == 'source'){
					$("#s_c").remove();
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/game/getGameSource.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" style="max-width:240px"><option value="-1">请选择...</option><option value="-99">所有渠道</option>';
						    $.each(data.gameSources,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	exclass.submit();
							});
						  }
					});
				}else if(value == 'client'){
					$("#s_c").remove();
					$.ajax({
						  type: "POST",
						  url: "/panel_bi/game/getGameClient.ac",
						  data: $.param(ps),
						  dataType: "json",
						  success: function(data){
						    var span = $("#s_c_span");
						    var str = '<select id="s_c" style="max-width:240px"><option value="-1">请选择...</option><option value="-99">所有服务器</option>';
						    $.each(data.gameClients,function(i,v){
						    	str += '<option value="' + v +'">' + v +'</option>';
						    });
						    str += '</select>';
						    span.append(str);
						    
						    $("#s_c").change(function(){
						    	exclass.submit();
							});
						  }
					});
				}else if(value == 'zsb'){
					/*
					$("#dau_cu_chart").hide();
					$("#data").hide();
					$("#life_bar_chart2").hide();
					$("#data2").hide();
					
					$("#life_bar_chart").show();
					$("#data4").show();
					*/
					$("#s_c").remove();
					exclass.submit();
				}else if(value == 'leave'){
					/*
					$("#dau_cu_chart").hide();
					$("#data").hide();
					$("#life_bar_chart2").show();
					$("#date2").show();
					$("#life_bar_chart").hide();
					$("#data4").hide();
					*/
					$("#s_c").remove();
					exclass.submit();
					
				}
			},
			checkChannelChangeParam:function(){
				  var ps={
					     id:$("input[name='gamesId']").val(),
					     gameId:$("input[name='gameId']").val(),
					     snid:$("input[name='snid']").val(),
						 beginTime:$("input[name='beginTime']").val(),
						 endTime:$("input[name='endTime']").val()
					};
			      if(ps.id == null || ps.id == ''){ return null; }
			      if(ps.gameId == null || ps.gameId == ''){ return null; }
			      if(ps.snid == null || ps.snid == ''){ return null; }
			      
			      var dateArr = $.gameUtil.processDate(ps.beginTime,ps.endTime);
			      ps.beginTime = dateArr[0];
			      ps.endTime = dateArr[1];
			      $("input[name='beginTime']").val(dateArr[0]);
			      $("input[name='endTime']").val(dateArr[1]);
			      
			      return ps;
				},
			checkParam:function(){
			  var ps={
				     id:$("input[name='gamesId']").val(),
				     gameId:$("input[name='gameId']").val(),
				     snid:$("input[name='snid']").val(),
				     indicators:$('#indicators').val(),
				     queryType:$('#queryType').val(),
					 beginTime:$("input[name='beginTime']").val(),
					 endTime:$("input[name='endTime']").val(),
					 source:$('#s_c').val(),
					 clientid:$('#s_c').val(),
					 view:$('#view').val()
				};
		      if(ps.id == null || ps.id == ''){ return null; }
		      if(ps.gameId == null || ps.gameId == ''){ return null; }
		      if(ps.snid == null || ps.snid == ''){ return null; }
		      
		      if(ps.indicators == null || ps.indicators == ''){
		    	  ps.indicators = 'all'; 
		       }else if(ps.indicators == 'source' && (ps.source != null && ps.source == '-1')){
		    	   return null;
		       }else if(ps.indicators == 'client' && (ps.clientid != null && ps.clientid == '-1')){
		    	   return null;
		       }
		      
		      var dateArr = $.gameUtil.processDate(ps.beginTime,ps.endTime);
		      ps.beginTime = dateArr[0];
		      ps.endTime = dateArr[1];
		      $("input[name='beginTime']").val(dateArr[0]);
		      $("input[name='endTime']").val(dateArr[1]);
		      
		      return ps;
			},
			processDate:function(beginTime,endTime){
			    if((endTime == null || endTime == '') && (beginTime == null || beginTime == '')){
			    	  var d = new Date();
			    	  d.setDate(d.getDate() - 1);
			    	  endTime = $.date.getDateFullStr(d);
			    	  
			    	  d = new Date(endTime);
			    	  d.setDate(d.getDate()- 31);
			    	  beginTime = $.date.getDateFullStr(d);
			    	  
			       }else if((endTime == null || endTime == '') && (beginTime != null && beginTime != '')){
			    	  var d = new Date(beginTime);
			    	  d.setDate(d.getDate()+ 31);
			    	  
			    	  endTime = $.date.getDateFullStr(d);
			       }else if((endTime != null && endTime != '') && (beginTime == null || beginTime == '')){
			    	  var d = new Date(endTime);
			    	  d.setDate(d.getDate()- 31);
			    	  
			    	  beginTime = $.date.getDateFullStr(d);
			      }
			    
			    var d = new Date(endTime);
			    var b = new Date(beginTime);
			    if(d.getTime() < b.getTime()){
			         alert('结束时间不能小于开始时间');
		    	     return null;
			     }
			    
			    b.setDate(b.getDate() + 100);
			    if(b.getTime() < d.getTime()){
			    	alert('日期区间不能大于100天！');
			    	return null;
			    }
			      
				return [beginTime,endTime];
			},
			//测试报告
			processDate2:function(beginTime,endTime){
			
				 if((endTime == null || endTime == '') && (beginTime == null || beginTime == '')){
			    	  var d = new Date();
			    	  d.setDate(d.getDate() - 1);
			    	  endTime = $.date.getDateFullStr(d);
			    	  
			    	  d = new Date(endTime);
			    	  d.setDate(d.getDate()- 31);
			    	  beginTime = $.date.getDateFullStr(d);
			    	  
			       }else if((endTime == null || endTime == '') && (beginTime != null && beginTime != '')){
			    	  var d = new Date(beginTime);
			    	  d.setDate(d.getDate()+ 31);
			    	  
			    	  endTime = $.date.getDateFullStr(d);
			       }else if((endTime != null && endTime != '') && (beginTime == null || beginTime == '')){
			    	  var d = new Date(endTime);
			    	  d.setDate(d.getDate()- 31);
			    	  
			    	  beginTime = $.date.getDateFullStr(d);
			      }
			    
			    var d = new Date(endTime);
			    var b = new Date(beginTime);
			    if(d.getTime() < b.getTime()){
			         alert('结束时间不能小于开始时间');
		    	     return null;
			     }
			    
			    b.setDate(b.getDate() + 100);
			    if(b.getTime() < d.getTime()){
			    	alert('日期区间不能大于100天！');
			    	return null;
			    }
			      
			    /*
			   if($.gameUtil.getDays(beginTime,endTime)>10){
				   alert('日期区间不能大于10天！');
				   return null;
			   }
			   */
			    
				return [beginTime,endTime];
			},
			getDays:function(strDateStart,strDateEnd){
		    	   var strSeparator = "-"; //日期分隔符
		    	   var oDate1;
		    	   var oDate2;
		    	   var iDays;
		    	   oDate1= strDateStart.split(strSeparator);
		    	   oDate2= strDateEnd.split(strSeparator);
		    	   var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
		    	   var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
		    	   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数
		    	   return iDays ;
		    	},
			btnTimeout:function(n,win){
				if(n.val()<=0){
					n.attr('disabled',false);
					window.clearInterval(win);
				}else{
					n.val(n.val()-1);
				}
			},
			trHighLight:function(table,selected){
				table.on('click', 'tr', function () {
			        var id = this.id;
			        var index = $.inArray(id, selected);
			 
			        if ( index === -1 ) {
			            selected.push( id );
			        } else {
			            selected.splice( index, 1 );
			        }
			 
			        $(this).toggleClass('highlight');
			    } );
			}
	};
});