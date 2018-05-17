select t1.install_creative,t1.dau,t1.dnu,dau_total,payer_total,
case when t2.equips is null then 0 else t2.equips end as equips,
t1.payer,t1.total_amount,'${snid}' as snid,'${gameid}' as gameid,'${ds}' as day,'${finish_time}' as finish_time,'${start_time}' as start_time
from 



(select t1.install_creative,count(distinct t1.userid) as dau,count(distinct case when t2.userid is not null then t2.userid end) as dnu,
count(distinct case when t3.userid is not null then t3.userid end) as payer,
sum(case when t3.userid is not null then t3.total_amount else 0 end) as total_amount
from 

(select case when b.install_creative is null then 'unknown' else b.install_creative end as install_creative,a.userid 
from 
(select userid
from 
(select  userid from default.nt_install where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and install_time <='${finish_time}' and  install_time >'${start_time}'
union all 
select  userid from default.nt_dau where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and dau_time <='${finish_time}' and dau_time >'${start_time}'
union all
select userid  from default.nt_counter where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and counter_time <='${finish_time}' and counter_time>'${start_time}'
union all
select userid from default.nt_economy where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and economy_time <='${finish_time}' and economy_time>'${start_time}')t 
group by userid)a

left outer join 

(select userid,first_date,install_creative from aggr.a_user_new 
where snid='${snid}' and gameid='${gameid}' and ds=cast(date_add('day',-1,date('${ds}')) as varchar)
union all
select t1.userid,t1.first_date,
case when t2.ad_place is not null then t2.ad_place 
	 	else 'unknown' end as install_creative
from 

(select userid,first_date,cast(0 as double) as total_payment_amount,
case when b.pf is not null then  b.pf
	 when d.pf is not null then d.pf
	 else 'unknown' end as install_creative,
case when b.appkey is not null then b.appkey 
	 when d.appkey is not null then d.appkey
	 else 'unknown' end as appkey,
install_dlfrom, a.from_userid,a.idfa,a.ip_rank
from 


(select userid,first_date,extra,idfa,udid, from_userid,install_creative,
install_dlfrom,row_number() over (partition by from_userid ) as ip_rank
from 
(select userid,first_date,extra,idfa,t1.udid,case when t2.ip is not null then t2.ip else t1.from_userid end as from_userid,install_creative,
install_dlfrom
from 

(select  userid,concat(install_date,' ',install_time) as first_date,
extra,upper(to_hex(md5(cast(udid as varbinary)))) as idfa,udid,from_userid,case when creative is null or creative = '' then 'unknown' else creative end as install_creative,
case when extra['download_from'] is null then 'unknown' else extra['download_from'] end as install_dlfrom
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}' and install_time<='${finish_time}')t1

left outer join 

(select udid,split(min(str),'=')[2] as ip
from 
(select udid,concat(eq_date,' ',eq_time,'=',family) as str                 
from default.nt_equipment where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}'
union all
select udid,concat(install_date,'',install_time,'=',from_userid) as str
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}')t
group by udid)t2
on t1.udid=t2.udid


)t
)a 

left outer join 

(select distinct case when ifa is not null and ifa !='' then upper(to_hex(md5(cast(ifa as varbinary)))) else upper(ifa_md5) end as ifa,pf,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${ds}') and ds<='${ds}' and split(callback_time,' ')[1]='${ds}'
and ((ifa is not null and ifa !='') or (ifa_md5 is not null and ifa_md5!='')) and call_type='IDFA'
)b on a.idfa = b.ifa  
left outer join 
(select distinct ip,pf,row_number() over (partition by ip) as ip_rank,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${ds}') and ds<='${ds}' and split(callback_time,' ')[1]='${ds}'
 and ip<>'' and not ip is null and call_type='IP')d  
 on a.from_userid = d.ip and a.ip_rank=d.ip_rank)t1
 left outer join 
 (select  appkey, ad_place
from default.nt_adshort where snid = '${snid}' AND gameid = '${gameid}' 
group by appkey, ad_place)t2
on t1.appkey=t2.appkey 
)b
on a.userid=b.userid)t1


left outer join 

(select t1.userid
from 
(select  userid,concat(install_date,' ',install_time) as first_date
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}' and install_time<='${finish_time}' and install_time >'${start_time}' )t1
left outer join 
(select userid
from aggr.a_user_new
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-2,date'${ds}')
union all
select userid
from default.nt_install
where  snid='${snid}' and gameid='${gameid}'  and  date(ds)=date_add('day',-1,date'${ds}')
union all
select userid
from default.nt_install
where  snid='${snid}' and gameid='${gameid}' and ds='${ds}' and  install_time <='${start_time}'
)t2
on t1.userid=t2.userid
where t2.userid is null )t2 

on t1.userid=t2.userid



left outer join 


(select t1.userid,sum(cast(amount as double)/cast(rate as double)) as total_amount
from 
(select snid,gameid,userid,amount
from default.nt_payment
where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and payment_time <='${finish_time}' and payment_time>'${start_time}'  and  cast(amount as double)>0)t1
left outer join 
(select snid,gameid,rate
from dimension.all_game_name)t2
on t1.snid=t2.snid and t1.gameid=t2.gameid
group by t1.userid)t3

on t1.userid=t3.userid
group by t1.install_creative)t1
		
		
		
		


left outer join


(select upper(equip_creative) as equip_creative,count(distinct udid)  as equips 
from
(select t1.udid,
case when t2.ad_place is not null then t2.ad_place 
	 else 'unknown'
end as equip_creative
from 
(select a.udid,case when b.pf is not null then b.pf
	 when c.pf is not null then c.pf
	 else 'unknown' end as  equip_creative,
case when b.appkey is not null then b.appkey 
	 when c.appkey is not null then c.appkey
	 else 'unknown' end as appkey,a.ip,a.ip_rank,upper(to_hex(md5(cast(udid as varbinary)))) as idfa,a.equip_time
from 

(select udid,idfa,equip_time,ip,row_number() over (partition by ip) as ip_rank
from 
(select t1.udid,t1.idfa,equip_time,case when t1.ip='' or t1.ip=' ' or t1.ip is null then t2.ip else t1.ip end as ip 
from 
	
(select a.*
from 
(select udid,idfa,split(str,'=')[1] as equip_time,split(str,'=')[2] as ip
from
(select udid,idfa,min(concat(time,'=',ip)) as str
	from
	(	select udid,upper(to_hex(md5(cast(udid as varbinary)))) as idfa, family as ip,concat(eq_date,' ',eq_time) as time
		from default.nt_equipment 
		where snid = '${snid}' AND gameid = '${gameid}'and ds='${ds}' and udid <> '' and udid is not null and udid<>'null' and eq_time<='${finish_time}' and eq_time >'${start_time}'
	)tmp
group by udid,idfa)t)a
left outer join 
(select udid
from aggr.a_equipment
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-2,date'${ds}')
union all
select udid
from default.nt_equipment
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${ds}')
union all
select udid
from default.nt_equipment
where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and eq_time <='${start_time}')b 
on a.udid =b.udid
where b.udid is null
)t1
left outer join 
(select udid,upper(to_hex(md5(cast(udid as varbinary)))) as idfa,from_userid as ip 
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' and ds='${ds}' and udid <> '' and udid is not null and udid<>'null' and install_time >'${start_time}')t2
on t1.udid=t2.udid and t1.idfa=t2.idfa
)t
)a


left outer join
(select distinct case when ifa is not null and ifa !='' then upper(to_hex(md5(cast(ifa as varbinary)))) else upper(ifa_md5) end as ifa,pf,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${ds}') and ds<='${ds}'  and split(callback_time,' ')[1]='${ds}'
and ((ifa is not null and ifa !='') or (ifa_md5 is not null and ifa_md5!='')) and call_type='IDFA'
)b
on a.idfa = b.ifa
left outer join
(select distinct ip,pf,row_number() over (partition by ip) as ip_rank,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${ds}') and ds<='${ds}' and split(callback_time,' ')[1]='${ds}' 
 and ip<>'' and not ip is null and call_type='IP')c 
on a.ip = c.ip and a.ip_rank = c.ip_rank)t1
left outer join 
 (select  appkey, ad_place
from default.nt_adshort where snid = '${snid}' AND gameid = '${gameid}' 
group by appkey, ad_place)t2
on t1.appkey=t2.appkey 
)eq
group by upper(equip_creative))t2
on upper(t1.install_creative)=upper(t2.equip_creative) 

left outer join 






(select case when b.install_creative is null then 'unknown' else b.install_creative end as install_creative,count(distinct a.userid) as dau_total
from 
(select userid
from 
(select  userid from default.nt_install where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and install_time <='${finish_time}' 
union all 
select  userid from default.nt_dau where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and dau_time <='${finish_time}' 
union all
select userid  from default.nt_counter where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and counter_time <='${finish_time}' 
union all
select userid from default.nt_economy where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and economy_time <='${finish_time}' )t 
group by userid)a

left outer join 

(select userid,first_date,install_creative from aggr.a_user_new 
where snid='${snid}' and gameid='${gameid}' and ds=cast(date_add('day',-1,date('${ds}')) as varchar)
union all
select t1.userid,t1.first_date,
case when t2.ad_place is not null then t2.ad_place 
	 	else 'unknown' end as install_creative
from 

(select userid,first_date,cast(0 as double) as total_payment_amount,
case when b.pf is not null then  b.pf
	 when d.pf is not null then d.pf
	 else 'unknown' end as install_creative,
case when b.appkey is not null then b.appkey 
	 when d.appkey is not null then d.appkey
	 else 'unknown' end as appkey,
install_dlfrom, a.from_userid,a.idfa,a.ip_rank
from 


(select userid,first_date,extra,idfa,udid, from_userid,install_creative,
install_dlfrom,row_number() over (partition by from_userid ) as ip_rank
from 
(select userid,first_date,extra,idfa,t1.udid,case when t2.ip is not null then t2.ip else t1.from_userid end as from_userid,install_creative,
install_dlfrom
from 

(select  userid,concat(install_date,' ',install_time) as first_date,
extra,upper(to_hex(md5(cast(udid as varbinary)))) as idfa,udid,from_userid,case when creative is null or creative = '' then 'unknown' else creative end as install_creative,
case when extra['download_from'] is null then 'unknown' else extra['download_from'] end as install_dlfrom
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}' and install_time<='${finish_time}')t1

left outer join 

(select udid,split(min(str),'=')[2] as ip
from 
(select udid,concat(eq_date,' ',eq_time,'=',family) as str                 
from default.nt_equipment where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}'
union all
select udid,concat(install_date,'',install_time,'=',from_userid) as str
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}')t
group by udid)t2
on t1.udid=t2.udid


)t
)a 

left outer join 

(select distinct case when ifa is not null and ifa !='' then upper(to_hex(md5(cast(ifa as varbinary)))) else upper(ifa_md5) end as ifa,pf,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${ds}') and ds<='${ds}' and split(callback_time,' ')[1]='${ds}'
and ((ifa is not null and ifa !='') or (ifa_md5 is not null and ifa_md5!='')) and call_type='IDFA'
)b on a.idfa = b.ifa  
left outer join 
(select distinct ip,pf,row_number() over (partition by ip) as ip_rank,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${ds}') and ds<='${ds}' and split(callback_time,' ')[1]='${ds}'
 and ip<>'' and not ip is null and call_type='IP')d  
 on a.from_userid = d.ip and a.ip_rank=d.ip_rank)t1
 left outer join 
 (select  appkey, ad_place
from default.nt_adshort where snid = '${snid}' AND gameid = '${gameid}' 
group by appkey, ad_place)t2
on t1.appkey=t2.appkey 
)b
on a.userid=b.userid
group by case when b.install_creative is null then 'unknown' else b.install_creative end )t3

on  upper(t1.install_creative)=upper(t3.install_creative) 

left outer join 



(select case when b.install_creative is null then 'unknown' else b.install_creative end as install_creative,count(distinct a.userid) as payer_total
from 
(select userid
from default.nt_payment
where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and payment_time <='${finish_time}'  and  cast(amount as double)>0
group by userid)a

left outer join 


(select userid,first_date,install_creative from aggr.a_user_new 
where snid='${snid}' and gameid='${gameid}' and ds=cast(date_add('day',-1,date('${ds}')) as varchar)
union all
select t1.userid,t1.first_date,
case when t2.ad_place is not null then t2.ad_place 
	 	else 'unknown' end as install_creative
from 

(select userid,first_date,cast(0 as double) as total_payment_amount,
case when b.pf is not null then  b.pf
	 when d.pf is not null then d.pf
	 else 'unknown' end as install_creative,
case when b.appkey is not null then b.appkey 
	 when d.appkey is not null then d.appkey
	 else 'unknown' end as appkey,
install_dlfrom, a.from_userid,a.idfa,a.ip_rank
from 


(select userid,first_date,extra,idfa,udid, from_userid,install_creative,
install_dlfrom,row_number() over (partition by from_userid ) as ip_rank
from 
(select userid,first_date,extra,idfa,t1.udid,case when t2.ip is not null then t2.ip else t1.from_userid end as from_userid,install_creative,
install_dlfrom
from 

(select  userid,concat(install_date,' ',install_time) as first_date,
extra,upper(to_hex(md5(cast(udid as varbinary)))) as idfa,udid,from_userid,case when creative is null or creative = '' then 'unknown' else creative end as install_creative,
case when extra['download_from'] is null then 'unknown' else extra['download_from'] end as install_dlfrom
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}' and install_time<='${finish_time}')t1

left outer join 

(select udid,split(min(str),'=')[2] as ip
from 
(select udid,concat(eq_date,' ',eq_time,'=',family) as str                 
from default.nt_equipment where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}'
union all
select udid,concat(install_date,'',install_time,'=',from_userid) as str
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${ds}')t
group by udid)t2
on t1.udid=t2.udid


)t
)a 

left outer join 

(select distinct case when ifa is not null and ifa !='' then upper(to_hex(md5(cast(ifa as varbinary)))) else upper(ifa_md5) end as ifa,pf,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${ds}') and ds<='${ds}' and split(callback_time,' ')[1]='${ds}'
and ((ifa is not null and ifa !='') or (ifa_md5 is not null and ifa_md5!='')) and call_type='IDFA'
)b on a.idfa = b.ifa  
left outer join 
(select distinct ip,pf,row_number() over (partition by ip) as ip_rank,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${ds}') and ds<='${ds}' and split(callback_time,' ')[1]='${ds}'
 and ip<>'' and not ip is null and call_type='IP')d  
 on a.from_userid = d.ip and a.ip_rank=d.ip_rank)t1
 left outer join 
 (select  appkey, ad_place
from default.nt_adshort where snid = '${snid}' AND gameid = '${gameid}' 
group by appkey, ad_place)t2
on t1.appkey=t2.appkey 
)b
on a.userid=b.userid
group by case when b.install_creative is null then 'unknown' else b.install_creative end)t4

on  upper(t1.install_creative)=upper(t4.install_creative) 


