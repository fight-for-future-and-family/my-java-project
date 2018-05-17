var notice,noticeValue_1,noticeValue_2,scroll,timer;
function init(){
	notice=document.getElementById("notice");
	noticeValue_1=document.getElementById("noticeValue_1");
	noticeValue_2=document.getElementById("noticeValue_2");
	scroll=document.getElementById("scroll");
	
	scroll.style.width=(noticeValue_1.offsetWidth*2+100)+"px";
//	noticeValue_2.innerHTML=noticeValue_1.innerHTML;
    timer=setInterval(mar,30)
}
function mar(){
	if(noticeValue_1.offsetWidth * 0.5 <= notice.scrollLeft){
		notice.scrollLeft -= noticeValue_1.offsetWidth* 0.5;
//		noticeValue_1.innerHTML=noticeValue_2.innerHTML;
	}else{
		notice.scrollLeft++;
	}
}

window.onload=init;
