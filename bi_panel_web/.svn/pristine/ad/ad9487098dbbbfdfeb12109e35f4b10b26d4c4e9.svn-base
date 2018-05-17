$(function(){
	
	$(document).ready(function() {
	    $.addCustomReportModel.init();
	});
	
	$.addCustomReportModel ={
			init:function(){
				$.addCustomReportModel.initEvent();
			},
			initEvent:function(){
				 $("#modelType").change(function(){
					  var value = $("#modelType").val();
					  if(value == 1){
					   $("#gameIdSpan").show();
					  }else{
					   $("#gameIdSpan").hide();
					  }
				   });
				 
				 $("#add").click(function(){
						$.addCustomReportModel.submit();
				  });
				 
				 $("#templateSql").change(function(){
					 $.addCustomReportModel.getParams();
					 $.addCustomReportModel.getColumns();
					 $("#isSqlChange").val(1);
				 });
				 
				 $(".tempColSpan").change(function(){
					 $("#isColumnChange").val(1);
				 });
				 
				 $(".tempParamSpan").change(function(){
					 $("#isParamChange").val(1);
				 });
				 
				 var textareaDefaultValue = "";
				 if( $("#templateSql").val() == ''){
					 var temp1 = "1.填写sql,移开鼠标后会自动解析出参数与返回列。";
					 var temp2 = "其中参数格式为${param}。参数为日期的，结构必须是：day,ds,或者以date结尾。返回列必须是简单无运算的，否则系统无法正确解析。";
					 var temp3 = "\n2.请仔细查看系统解析出参数与返回列是否有误，重新调整sql格式或者联系管理员。";
					 var temp4 = "\n3.请给参数排序，便于用户新建任务有序填写参数。";
					 textareaDefaultValue = temp1+temp2+temp3+temp4;
					 $("#templateSql").val(textareaDefaultValue);
					 $("#templateSql").addClass('notic');
				 }
				 
				 var descDefaultValue = "对报表的功能或者返回字段给用户做一个简单的说明，例如返回字段的单位等。需要断行加<br>";
				 if( $("#description").val() == ''){
					 $("#description").val(descDefaultValue);
					 $("#description").addClass('notic');
				 }
				 
				 $(".p-input",$("#baseUl")).each(function(i,n){
					 $(n).click(function () { 
						 var check1 = $(this).val(); 
						 if (check1 == $.addCustomReportModel.getDefaultValue(n,textareaDefaultValue,descDefaultValue)) { 
						     $(this).val(""); 
						     $(this).removeClass('notic');
						 } 
					}); 
					 
					 $(n).blur(function () { 
						 var check1 = $(this).val(); 
						 if (check1.trim() == "") { 
							 $(this).val($.addCustomReportModel.getDefaultValue(n,textareaDefaultValue,descDefaultValue));
						     $(this).addClass('notic');
						 }else{
							 $(this).removeClass('notic');
						 }
					}); 
				 });
			},
			getDefaultValue:function(n,textareaDefaultValue,descDefaultValue){
				var defaultValue = '';
				 if($(n).attr("id") == 'templateSql'){
					 defaultValue =  textareaDefaultValue;
				 }else if($(n).attr("id") == 'description'){
					 defaultValue =  descDefaultValue;
				 }else{
					 defaultValue = n.defaultValue;
				 }
				 return defaultValue;
			},
			submit:function(){
				var modelType = $("#modelType").val();
				var gameId = $("#gameId").val();
				if(modelType == -1){
					alert('请选择模板类型');
					return false;
				}else if(modelType == 1 && gameId == -1){
					alert('请选择游戏');
					return false;
				}
				
				 var ul = $("#columnUl");
				 var paramUl = $("#paramUl");
				 var str = "";
				 var paramStr = "";
				 var arr = new Array($("select",paramUl).length);
				 var isCon = true;
				 
				 $(".notic").each(function(i,n){
					var check1 = $(n).val(); 
					if(check1 == this.defaultValue){
						$("label").each(function(i,v){
							if($(v).attr("for") == $(n).attr("path")){
								alert($(v).text+"不能为空。");
								isCon = false;
								return false;
							}
						});
					}
				});
				 
				 if(isCon){
					 $("select",paramUl).each(function(i,n){
							var v = $(n).val();
							if(v == -1){
								alert('请给参数'+$(n).attr('code')+'排序');
								isCon = false;
								return false;
							}else if(arr[v-1] != null || arr[v-1] == 0){
								alert('参数'+$(n).attr('code')+'排序重复');
								isCon = false;
								return false;
							}
							var valp = $("input[name="+$(n).attr('code')+"]").val();
							if(valp == null || valp.trim() == ''){
								alert('参数'+$(n).attr('code')+'不能为空');
								isCon = false;
								return false;
							}
							arr[v-1] = $(n).attr('code')+":"+valp+",";
						});
				 }
				 
				 if(isCon){
					 $("input",ul).each(function(i,n){
						 var name = $(n).attr("name");
						 var value = $(n).val();
						 if(value == null || value.trim() == ''){
							 alert('参数'+name+'不能为空');
							 isCon = false;
							 return false;
						 }
						 str += name + ":" + value+",";
					});
					 if(isCon){
						 for(var i=0;i<arr.length;i++){
							 paramStr += arr[i];
						 }
						 $("#columns").val(str);
						 $("#params").val(paramStr);
						 $("#submitForm").submit();
					 }
				 }
			},
			getParams:function(){
				var templateSql = $("#templateSql").val();
				var data={
						templateSql:templateSql
				}
				$.ajax({
					  type: "POST",
					  url: "/panel_manage/customReportModel/getPrams.ac",
					  "data":$.param(data),
		              "type":"post",
					  dataType: "json",
					  success: function(data){
						  if(data.params != null){
								var ul = $("#paramUl");
								var templateUl = $(".paramTemplateUl");
								var paramsCount = data.params.length;
								$("li",ul).remove();
								var li = $("li",templateUl).first().clone(true);
								var span = null;
								for(var i=0;i<data.params.length;i++){
									var column = data.params[i];
									if(i!=0 && i%3 == 0){
										$("span",li).first().remove();
										ul.append(li);
										li = $("li",templateUl).first().clone(true);
									}
									span = $("span",li).first().clone(true);
									
									$("label",span).attr("for",column);
									$("label",span).text(column);
									$("input",span).attr("name",column);
									
									var select = $("select",span);
									select.attr('code',column);
									for(var j=1;j<=paramsCount;j++){
										var op = $("option",select).first().clone(true);
										op.val(j);
										op.text(j);
										select.append(op);
									}
									
									
									li.append(span);
								}
								
								$("span",li).first().remove();
								ul.append(li);
						  }
					  }
				});
			},
			getColumns:function(){
				var templateSql = $("#templateSql").val();
				var columnsStr = templateSql.substring(templateSql.indexOf('select'),templateSql.indexOf('from'));
				columnsStr = columnsStr.replace('select','');
				var columnsArr = columnsStr.split(',');
				
				var ul = $("#columnUl");
				var templateUl = $(".templateUl");
				$("li",ul).remove();
				var li = $("li",templateUl).first().clone(true);
				var span = null;
				for(var i=0;i<columnsArr.length;i++){
					var column = columnsArr[i];
					if(column.indexOf(' as ') > 0){
						column = column.split('as')[1];
					}else if(column.indexOf('.') > 0){
						column = column.split('.')[1];
					}
					column = column.replace(new RegExp(' ','gm'),'').replace(new RegExp('\n','gm'),'');
					
					if(i!=0 && i%3 == 0){
						$("span",li).first().remove();
						ul.append(li);
						li = $("li",templateUl).first().clone(true);
					}
					span = $("span",li).first().clone(true);
					
					$("label",span).attr("for",column);
					$("label",span).text(column);
					$("input",span).attr("name",column);
					
					li.append(span);
				}
				
				$("span",li).first().remove();
				ul.append(li);
			}
	};
});