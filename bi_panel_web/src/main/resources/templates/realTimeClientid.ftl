select t1.clientid,dnu,dau,payer,total_amount,dau_total,payer_total,'${snid}' as snid,'${gameid}' as gameid,'${ds}' as day,'${finish_time}' as finish_time,'${start_time}' as start_time
from 

(select t1.clientid,count(distinct t1.userid) as dnu 
from 
(select clientid, userid,install_date,install_time
from default.nt_install
where  snid='${snid}' and gameid='${gameid}' and ds='${ds}'   and install_time <='${finish_time}' and install_time>'${start_time}'
group by clientid, userid,install_date,install_time)t1

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
where  snid='${snid}' and gameid='${gameid}' and ds='${ds}' and  install_time <='${start_time}' )t2
on t1.userid=t2.userid
where t2.userid is null
group by t1.clientid)t1

left outer join 

(select clientid,count(distinct userid) as dau
from 
(select  userid,clientid,install_date as date1,install_time as time1 from default.nt_install where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and install_time <='${finish_time}' and  install_time >'${start_time}'
union all 
select  userid,clientid,dau_date as date1,dau_time as time1  from default.nt_dau where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and dau_time <='${finish_time}' and dau_time >'${start_time}'
union all
select userid,clientid ,counter_date as date1,counter_time from default.nt_counter where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and counter_time <='${finish_time}' and counter_time>'${start_time}'
union all
select userid,clientid,economy_date as date1,economy_time as time1  from default.nt_economy where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and economy_time <='${finish_time}' and economy_time>'${start_time}')t 
group by clientid)t2


on t1.clientid=t2.clientid 




left outer join 


(select clientid,count(distinct t1.userid) as payer,sum(amount/rate/if(exchange_rate is null,1,exchange_rate)) as total_amount
from 

(select snid,gameid,userid,clientid,currency,cast(amount as double) as amount
from default.nt_payment
where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and payment_time <='${finish_time}' and payment_time >'${start_time}' and  cast(amount as double)>0)t1
left outer join 
(select snid,gameid,cast(rate as double) as rate
from dimension.all_game_name)t2
on t1.snid=t2.snid and t1.gameid=t2.gameid
left outer join
(select distinct ds,currency,cast(exchange_rate as double) as exchange_rate
from dimension.d_exchange_rate where date(ds)=date_add('day',-1,date'${ds}') and currency<>'CNY')t3
on t1.currency=t3.currency
group by clientid)t3

on t1.clientid=t3.clientid 

left outer join 
(select clientid,count(distinct userid) as dau_total
from 
(select  userid,clientid,install_date as date1,install_time as time1 from default.nt_install where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and install_time <='${finish_time}' 
union all 
select  userid,clientid,dau_date as date1,dau_time as time1  from default.nt_dau where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and dau_time <='${finish_time}' 
union all
select userid,clientid ,counter_date as date1,counter_time from default.nt_counter where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and counter_time <='${finish_time}'
union all
select userid,clientid,economy_date as date1,economy_time as time1  from default.nt_economy where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and economy_time <='${finish_time}' )t
group by clientid)t4
on t1.clientid=t4.clientid 

left outer join 

(select clientid, count(distinct userid) as payer_total
from default.nt_payment
where snid='${snid}' and gameid='${gameid}' and ds='${ds}' and payment_time <='${finish_time}'   and  cast(amount as double)>0
group  by clientid)t5
on t1.clientid=t5.clientid 




