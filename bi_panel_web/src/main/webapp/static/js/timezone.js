$(function(){
	
	var timezones = [{time:'GMT-12',name:'GMT-12(IDL-国际换日线)'},
	                     {time:'GMT-11',name:'GMT-11(MIT-中途岛标准时间)'},
	                     {time:'GMT-10',name:'GMT-10(HST-夏威夷－阿留申标准时间)'},
	                     {time:'GMT-9:30',name:'GMT-9:30(MSIT-马克萨斯群岛标准时间)'},
	                     {time:'GMT-9',name:'GMT-9(AKST-阿拉斯加标准时间)'},
	                     {time:'GMT-8',name:'GMT-8(PSTA-太平洋标准时间A)'},
	                     {time:'GMT-7',name:'GMT-7(MST-北美山区标准时间)'},
	                     {time:'GMT-6',name:'GMT-6(CST-北美中部标准时间)'},
	                     {time:'GMT-5',name:'GMT-5(EST-北美东部标准时间)'},
	                     {time:'GMT-4:30',name:'GMT-4:30(RVT-委内瑞拉标准时间)'},
	                     {time:'GMT-4',name:'GMT-4(AST-大西洋标准时间)'},
	                     {time:'GMT-3:30',name:'GMT-3:30(NST-纽芬兰岛标准时间)'},
	                     {time:'GMT-3',name:'GMT-3(SAT-南美标准时间 )'},
	                     {time:'GMT-2',name:'GMT-2(BRT-巴西时间)'},
	                     {time:'GMT-1',name:'GMT-1(CVT-佛得角标准时间 )'},
	                     {time:'GMT',name:'GMT(WET-欧洲西部时区,GMT-格林威治标准时间)'},
	                     {time:'GMT+1',name:'GMT+1(CET-欧洲中部时区)'},
	                     {time:'GMT+2',name:'GMT+2(EET-欧洲东部时区)'},
	                     {time:'GMT+3',name:'GMT+3(MSK-莫斯科时区)'},
	                     {time:'GMT+3:30',name:'GMT+3:30(IRT-伊朗标准时间)'},
	                     {time:'GMT+4',name:'GMT+4 (META-中东时区A )'},
	                     {time:'GMT+4:30',name:'GMT+4:30(AFT-阿富汗标准时间 )'},
	                     {time:'GMT+5',name:'GMT+5(METB-中东时区B)'},
	                     {time:'GMT+5:30',name:'GMT+5:30(IDT-印度标准时间 )'},
	                     {time:'GMT+5:45',name:'GMT+5:45(NPT-尼泊尔标准时间 )'},
	                     {time:'GMT+6',name:'GMT+6(BHT-孟加拉标准时间 )'},
	                     {time:'GMT+6:30',name:'GMT+6:30(MRT-缅甸标准时间 )'},
	                     {time:'GMT+7',name:'GMT+7(MST-中南半岛标准时间 )'},
	                     {time:'GMT+8',name:'GMT+8(EAT-东亚标准时间)'},
	                     {time:'GMT+9',name:'GMT+9(FET- 远东标准时间)'},
	                     {time:'GMT+9:30',name:'GMT+9:30(ACST-澳大利亚中部标准时间)'},
	                     {time:'GMT+10',name:'GMT+10(AEST-澳大利亚东部标准时间)'},
	                     {time:'GMT+10:30',name:'GMT+10:30(FAST-澳大利亚远东标准时间)'},
	                     {time:'GMT+11',name:'GMT+11(VTT-瓦努阿图标准时间 )'},
	                     {time:'GMT+11:30',name:'GMT+11:30(NFT-诺福克岛标准时间 )'},
	                     {time:'GMT+12',name:'GMT+12(PSTB-太平洋标准时间B)'},
	                     {time:'GMT+12:45',name:'GMT+12:45(CIT-查塔姆群岛标准时间 )'},
	                     {time:'GMT+13',name:'GMT+13(PSTC-太平洋标准时间C)'},
	                     {time:'GMT+14',name:'GMT+14(PSTD-太平洋标准时间D)'}];
	
	$.timeZone = {
			showTimeZone:function(){
				var timeZone = $("#timeZone");
				if(timeZone != undefined && timeZone.length > 0){
					if($("#timeZone").val() == undefined
							|| $("#timeZone").val() == null
							|| $("#timeZone").val() == ''
								|| ($("#timeZone").val().indexOf('GMT')<0)){
						$("#timeZoneName").text('运营时区：'+this.getTimeZoneName('GMT+8'));//东8时区
					}else{
						$("#timeZoneName").text('运营时区：'+this.getTimeZoneName($("#timeZone").val()));
					}
				}
			},
		    getTimeZones:function(){
			   return timezones;
		    },
		    getTimeZoneName:function(timezone){
		    	var timeZones = $.timeZone.getTimeZones();
		    	var name = '未知时区';
		    	$.each(timeZones,function(i,v){
					if(timezone == v.time){
						name = v.name;
						return false;
					}
				});
		    	return name;
		    }
		
			
	};
	
	
});