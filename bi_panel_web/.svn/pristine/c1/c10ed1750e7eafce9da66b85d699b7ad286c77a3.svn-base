select case when t1.users is null then 0 else cast(t1.users as integer) end as dnu,
t1.creative as source, case when t2.num is null then 0 else cast(t2.num as integer) end as newEquip,
'${snid}' as snid,'${gameid}' as gameid,'${day}' as day
from 
(select upper(creative) as creative,count(distinct userid) users
from 
(select t1.userid,
case when t2.ad_place is not null then t2.ad_place 
	 	else 'unknown' end as creative
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
(select a.*
from 
(select  userid,concat(install_date,' ',install_time) as first_date,
extra,upper(to_hex(md5(cast(udid as varbinary)))) as idfa,udid,from_userid,case when creative is null or creative = '' then 'unknown' else creative end as install_creative,
case when extra['download_from'] is null then 'unknown' else extra['download_from'] end as install_dlfrom
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${day}')a
left outer join 
(select userid 
from aggr.a_user_new
where snid = '${snid}' AND gameid = '${gameid}' AND   date(ds)=date_add('day',-1,date'${day}')
group by userid)b
on a.userid=b.userid
where b.userid is null
)t1
left outer join 
(select udid,split(min(str),'=')[2] as ip
from 
(select udid,concat(eq_date,' ',eq_time,'=',family) as str                 
from default.nt_equipment where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${day}'
union all
select udid,concat(install_date,'',install_time,'=',from_userid) as str
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${day}')t
group by udid)t2
on t1.udid=t2.udid

)t
)a 
left outer join 
(select distinct case when ifa is not null and ifa !='' then upper(to_hex(md5(cast(ifa as varbinary)))) else upper(ifa_md5) end as ifa,pf,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${day}') and ds<='${day}' and split(callback_time,' ')[1]='${day}'
and ((ifa is not null and ifa !='') or (ifa_md5 is not null and ifa_md5!='')) and call_type='IDFA'
)b on a.idfa = b.ifa  
left outer join 
(select distinct ip,pf,row_number() over (partition by ip) as ip_rank,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${day}') and ds<='${day}' and split(callback_time,' ')[1]='${day}'
 and ip<>'' and not ip is null and call_type='IP')d  
 on a.from_userid = d.ip and a.ip_rank=d.ip_rank)t1
 
 
 left outer join 
 (select  appkey, ad_place
from default.nt_adshort where snid = '${snid}' AND gameid = '${gameid}' 
group by appkey, ad_place)t2
on t1.appkey=t2.appkey 
)a
group by upper(creative))t1



left outer join 

--------设备开始--------------
(select upper(equip_creative) as equip_creative,cast(count(distinct udid) as varchar) as num 
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
	 else 'unknown' end as appkey,a.ip,a.ip_rank,upper(to_hex(md5(cast(udid as varbinary)))) as idfa
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
		where snid = '${snid}' AND gameid = '${gameid}'and ds='${day}' and udid <> '' and udid is not null and udid<>'null'  
	)tmp
	group by udid,idfa)t
)a
left outer join 
(---判断新增
select udid from etl.accumulate_equip_info where snid = '${snid}' AND gameid = '${gameid}'   and ds=cast(date_add('day',-1,date('${day}')) as varchar)
)b on a.udid =b.udid
where b.udid is null
)t1
left outer join 
(select udid,upper(to_hex(md5(cast(udid as varbinary)))) as idfa,from_userid as ip 
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' and ds='${day}' and udid <> '' and udid is not null and udid<>'null' )t2
on t1.udid=t2.udid and t1.idfa=t2.idfa
)t
)a
left outer join
(select distinct case when ifa is not null and ifa !='' then upper(to_hex(md5(cast(ifa as varbinary)))) else upper(ifa_md5) end as ifa,pf,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${day}') and ds<='${day}'  and split(callback_time,' ')[1]='${day}'
and ((ifa is not null and ifa !='') or (ifa_md5 is not null and ifa_md5!='')) and call_type='IDFA'
)b
on a.idfa = b.ifa
left outer join
(select distinct ip,pf,row_number() over (partition by ip) as ip_rank,appkey
from default.nt_adcallback where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${day}') and ds<='${day}' and split(callback_time,' ')[1]='${day}' 
 and ip<>'' and not ip is null and call_type='IP')c 
on a.ip = c.ip and a.ip_rank = c.ip_rank)t1
left outer join 


(select  appkey, ad_place
from default.nt_adshort where snid = '${snid}' AND gameid = '${gameid}' 
group by appkey, ad_place)t2


on t1.appkey=t2.appkey 
)eq
group by upper(equip_creative))t2
on t1.creative=t2.equip_creative
