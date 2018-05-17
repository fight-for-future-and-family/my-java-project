$(function(){
	
	option = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    
		    series : [
		        {
		            name:'KPI指标（月）',
		            type:'gauge',
		            startAngle: 180,
		            endAngle: 0,
		            center : ['50%', '50%'],    // 默认全局居中
		            radius : 100,
		            axisLine: {            // 坐标轴线
		                lineStyle: {       // 属性lineStyle控制线条样式
		                    width: 60
		                }
		            },
		            axisTick: {            // 坐标轴小标记
		                splitNumber: 10,   // 每份split细分多少段
		                length :12,        // 属性length控制线长
		            },
		            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
		                formatter: function(v){
		                    switch (v+''){
		                        case '10': return '低';
		                        case '50': return '中';
		                        case '90': return '高';
		                        default: return '';
		                    }
		                },
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    color: '#fff',
		                    fontSize: 15
		                    
		                }
		            },
		            pointer: {
		                width:10,
		                length: '90%',
		                color: 'rgba(255, 12, 12, 0.8)'
		            },
		            title : {
		                show : true,
		                offsetCenter: [0, '20%'],       // x, y，单位px
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    color: '#454',
		                    fontSize: 14,
		                    fontWeight: 'bolder'
		                }
		            },
		            detail : {
		                show : true,
		                backgroundColor: 'rgba(0,0,0,0)',
		                borderWidth: 0,
		                borderColor: '#ccc',
		                width: 100,
		                height: 40,
		                offsetCenter: [0, -40],       // x, y，单位px
		                formatter:'{value}%',
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    fontSize : 16
		                }
		            },
		            data:[{value: 50, name: '完成率'}]
		        }
		    ]
		};

	
	$(".data-container-main .game").each(function(i,n){
		brokenLine(n,$(n).attr("tips"),$(n).attr("rate"));
	});
	
	function brokenLine(cc,name,value){
		require.config({
			paths: {
				echarts: '/js/echarts/dist/',
				theme:'/js/echarts/theme/'
			}
		});
		require(
			[
				'echarts',
				'echarts/chart/bar',  
				'echarts/chart/line',
				'echarts/chart/scatter',
				'echarts/chart/k',
				'echarts/chart/pie',
				'echarts/chart/radar',
				'echarts/chart/force',
				'echarts/chart/chord',
				'echarts/chart/gauge',
				'echarts/chart/funnel',
				'echarts/chart/eventRiver'
			],
			DrawEChart
		);
		
		function DrawEChart(ec){
			//var timeTicket;
			var myChart = ec.init(cc);
			require(['theme/macarons'], function(tarTheme){
				myChart.setTheme(tarTheme);
	        });
			//timeTicket = setInterval(
			//	function (){
					option.series[0].data[0].name = name;
					option.series[0].data[0].value = value;
				    myChart.setOption(option,true);	
			//	}
			//,100);
		}
	}
	
	
/////////////////////////////////////////////////////////////////////////////	
	
	$(".data-container-main .game").each(function(i,n){
		$(n).click(function(){
			$("input[name='entity.id']").val($(n).attr("gvalue"));
			$("#submitForm").submit();
		});
	});
	
});
	                    