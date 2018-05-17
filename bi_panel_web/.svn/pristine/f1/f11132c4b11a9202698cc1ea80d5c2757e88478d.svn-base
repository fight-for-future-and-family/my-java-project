select '${seriesid}' as seriesid, '${gameName}' as gameName,'${snid}' as snid,'${gameid}' as gameid,dnu,install,payment_amount as paymentAmount,dau,payer,
equipment,
case when dau=0 then 0 else round(cast(payment_amount as double)/cast(dau as double),2) end AS arpu,
case when  payer=0 then 0 else round(cast(payment_amount as double)/cast(payer as double),2) end as arppu,
'${beginDate}' as beginDate,'${endDate}' as endDate,'${dataType}' as dataType, '${ds}' as ds
from 
(select dnu,install,cast(payment_amount as double)/cast(c.exchange_rate as double)*cast(d.exchange_rate as double) as payment_amount
from 
(select snid,gameid,sum(cast(dnu as bigint)) as dnu,sum(cast(install as bigint)) install,sum(cast(payment_amount as double)) as payment_amount
from facts.f_game_day
where 1=1   and ds>='${beginDate}' and ds<='${endDate}' and snid='${snid}' and gameid='${gameid}' 
group by snid,gameid)a
left outer join
(select snid,gameid,
case when currency = '${usa}' then 'USD'
     when currency = '${taibi}' then 'TWD' 
	 when currency='${rmb}'  then 'USD' end  as currency1,
case when currency = '${usa}' then 'CNY'
     when currency = '${taibi}' then 'CNY' 
	 when currency='${rmb}'  then 'USD' end as currency2 
from dimension.all_game_name)b
on a.snid=b.snid and a.gameid=b.gameid
left outer join
(select currency,exchange_rate
from dimension.d_exchange_rate
where date(ds)=date_add('day',-1,date'${endDate}'))c
on b.currency1=c.currency
left outer join 
(select currency,exchange_rate
from dimension.d_exchange_rate
where date(ds)=date_add('day',-1,date'${endDate}'))d
on b.currency2=d.currency)t1
left outer join 
(select  count(distinct userid) as dau
from default.nt_dau
where 1=1   and ds>='${beginDate}' and ds<='${endDate}' and snid='${snid}' and gameid='${gameid}' )t2
on 1=1
left outer join 
(select count(distinct userid) payer
from default.nt_payment
where 1=1   and ds>='${beginDate}' and ds<='${endDate}' and snid='${snid}' and gameid='${gameid}'  )t3
on 1=1
left outer join 
(select  count(distinct udid) as equipment
from
etl.total_equip_collect_isnew
where 1=1   and ds>='${beginDate}' and ds<='${endDate}' and is_new='1'  and snid='${snid}' and gameid='${gameid}' )t4
on 1=1
