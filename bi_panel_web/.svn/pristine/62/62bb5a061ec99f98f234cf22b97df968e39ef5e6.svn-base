select install_date as day,install_creative as source,reg,d0,d1,d2,d3,d4,d5,d6,d7,'${hour}' as hour,'${minute}' as minute,
'${snid}' as snid,'${gameid}' as gameid,'${type}' as type,'${endDay}' as endDay
from(
select install_date,install_creative,cast(reg as bigint) as reg,d0,
(case when date_add('day',1,date(install_date))>date'${day}' then 0.0 else d1 end) as d1,
(case when date_add('day',2,date(install_date))>date'${day}' then 0.0 else d2 end) as d2,
(case when date_add('day',3,date(install_date))>date'${day}' then 0.0 else d3 end) as d3,
(case when date_add('day',4,date(install_date))>date'${day}' then 0.0 else d4 end) as d4,
(case when date_add('day',5,date(install_date))>date'${day}' then 0.0 else d5 end) as d5,
(case when date_add('day',6,date(install_date))>date'${day}' then 0.0 else d6 end) as d6,
(case when date_add('day',7,date(install_date))>date'${day}' then 0.0 else d7 end) as d7
from  
(select install_date,install_creative,avg(reg) as reg,
round(sum(case when day<=0 then cast(pay_total as double)/cast(reg as double)/cast(rate as double) end),2) as d0,
round(sum(case when day<=1 then cast(pay_total as double)/cast(reg as double)/cast(rate as double) end),2) as d1,
round(sum(case when day<=2 then cast(pay_total as double)/cast(reg as double)/cast(rate as double) end),2) as d2,
round(sum(case when day<=3 then cast(pay_total as double)/cast(reg as double)/cast(rate as double) end),2) as d3,
round(sum(case when day<=4 then cast(pay_total as double)/cast(reg as double)/cast(rate as double) end),2) as d4,
round(sum(case when day<=5 then cast(pay_total as double)/cast(reg as double)/cast(rate as double) end),2) as d5,
round(sum(case when day<=6 then cast(pay_total as double)/cast(reg as double)/cast(rate as double) end),2) as d6,
round(sum(case when day<=7 then cast(pay_total as double)/cast(reg as double)/cast(rate as double) end),2) as d7
from
(select a.install_date,a.install_creative,b.day,ds,reg,pay_total
from
(select if( '0'='${source}' , 'TOTAL',install_creative ) as install_creative,split(first_date,' ')[1] as install_date,count(distinct userid) as reg
from aggr.a_user_new
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${day}') and date(split(first_date,' ')[1])>=date_add('day',-8,date'${day}')
group by if( '0'='${source}' , 'TOTAL',install_creative ) ,split(first_date,' ')[1])a
left outer join
(select install_creative,install_date,ds,date_diff('day',date(install_date),date(ds)) as day,sum(pay) as pay_total
from
(select userid,ds,sum(cast(amount as double)) as pay
from default.nt_payment
where snid='${snid}' and gameid='${gameid}' and date(ds)>=date_add('day',-8,date'${day}')
group by userid,ds)a
left outer join
(select userid,if( '0'='${source}' , 'TOTAL',install_creative ) as install_creative,split(first_date,' ')[1] as install_date
from aggr.a_user_new
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${day}') and date(split(first_date,' ')[1])>=date_add('day',-8,date'${day}'))b
on a.userid=b.userid
where b.userid is not null
group by install_creative,install_date,ds,date_diff('day',date(install_date),date(ds))
)b
on a.install_creative=b.install_creative and a.install_date=b.install_date
)tmp1
join
(select rate from dimension.all_game_name where snid='${snid}' and gameid='${gameid}')tmp2
on 1=1
group by install_date,install_creative)tmp

union all

select install_date,install_creative,cast(reg as bigint) as reg,round(cast(d0 as double)/cast(reg as double)/cast(rate as double),2) as d0,d1,d2,d3,d4,d5,d6,d7
from

(select '${day}' as install_date, if( '0'='${source}' , 'TOTAL',a.creative ) as install_creative,count(distinct a.userid) as reg,
sum(cast(pay as double)) as d0,0.0 as d1,0.0 as d2,0.0 as d3,0.0 as d4,0.0 as d5,0.0 as d6,0.0 as d7
from
(select t1.userid,
case when t2.ad_place is not null then t2.ad_place 
	 when t3.ad_place is not null then t3.ad_place 
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
(select  userid,concat(install_date,' ',install_time) as first_date,
extra,upper(to_hex(md5(cast(udid as varbinary)))) as idfa,udid,from_userid,case when creative is null or creative = '' then 'unknown' else creative end as install_creative,
case when extra['download_from'] is null then 'unknown' else extra['download_from'] end as install_dlfrom,row_number() over (partition by from_userid ) as ip_rank
from default.nt_install where snid = '${snid}' AND gameid = '${gameid}' AND ds = '${day}' 
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
 (select distinct appkey,case when ifa is not null and ifa !='' then upper(to_hex(md5(cast(ifa as varbinary)))) else upper(ifa_md5) end as ifa,extra['ad_place'] ad_place
from default.nt_adtracking where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${day}') and ds<='${day}')t2
on t1.appkey=t2.appkey and t1.idfa=t2.ifa
left outer join 
(select distinct appkey,ip,extra['ad_place'] ad_place,row_number() over (partition by ip) as ip_rank
from default.nt_adtracking where snid = '${snid}' AND gameid = '${gameid}' AND date(ds)>=date_add('day',-3,date'${day}') and ds<='${day}')t3
on t1.appkey=t3.appkey and t1.from_userid=t3.ip and t1.ip_rank=t3.ip_rank
)a
left outer join
(select userid,sum(cast(amount as double)) as pay
from default.nt_payment
where snid='${snid}' and gameid='${gameid}' and ds='${day}'
group by userid
)b
on a.userid=b.userid 
left outer join
(select userid
from aggr.a_user_new
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${day}'))c
on a.userid=c.userid
where c.userid is null
group by if( '0'='${source}' , 'TOTAL',a.creative ))tmp
join
(select rate from dimension.all_game_name where snid='${snid}' and gameid='${gameid}')tmp2
on 1=1
)ok
order by install_date,install_creative