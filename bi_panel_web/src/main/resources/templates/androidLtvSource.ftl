select install_date as day,install_creative as source,reg,d0,d1,d2,d3,d4,d5,d6,d7,'${hour}' as hour,'${minute}' as minute,
'${snid}' as snid,'${gameid}' as gameid,'${type}' as type,'${endDay}' as endDay
from
(select install_date,install_creative,cast(reg as bigint) as reg,d0,
(case when date_add('day',1,date(install_date))>cast('${day}' as timestamp) then 0.0 else d1 end) as d1,
(case when date_add('day',2,date(install_date))>cast('${day}' as timestamp) then 0.0 else d2 end) as d2,
(case when date_add('day',3,date(install_date))>cast('${day}' as timestamp) then 0.0 else d3 end) as d3,
(case when date_add('day',4,date(install_date))>cast('${day}' as timestamp) then 0.0 else d4 end) as d4,
(case when date_add('day',5,date(install_date))>cast('${day}' as timestamp) then 0.0 else d5 end) as d5,
(case when date_add('day',6,date(install_date))>cast('${day}' as timestamp) then 0.0 else d6 end) as d6,
(case when date_add('day',7,date(install_date))>cast('${day}' as timestamp) then 0.0 else d7 end) as d7
from
(select install_date,install_creative,avg(reg) as reg,
round(sum(case when day<=0 then pay_total/reg/rate end),2) as d0,
round(sum(case when day<=1 then pay_total/reg/rate end),2) as d1,
round(sum(case when day<=2 then pay_total/reg/rate end),2) as d2,
round(sum(case when day<=3 then pay_total/reg/rate end),2) as d3,
round(sum(case when day<=4 then pay_total/reg/rate end),2) as d4,
round(sum(case when day<=5 then pay_total/reg/rate end),2) as d5,
round(sum(case when day<=6 then pay_total/reg/rate end),2) as d6,
round(sum(case when day<=7 then pay_total/reg/rate end),2) as d7
from
(select snid,gameid,a.install_date,a.install_creative,b.day,ds,reg,pay_total
from
(select snid,gameid,if( '0'='${source}' , 'TOTAL',install_creative ) as install_creative,split(first_date,' ')[1] as install_date,count(distinct userid) as reg
from aggr.a_user_new
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${day}') and date(split(first_date,' ')[1])>=date_add('day',-8,date'${day}')
group by snid,gameid,if( '0'='${source}' , 'TOTAL',install_creative ),split(first_date,' ')[1])a
left outer join
(select install_creative,install_date,ds,date_diff('day',cast(install_date  as timestamp),cast(ds as timestamp)) as day,sum(pay) as pay_total
from
(select userid,ds,sum(cast(amount as double)) as pay
from default.nt_payment
where snid='${snid}' and gameid='${gameid}' and date(ds)>=date_add('day',-8,date'${day}')
group by userid,ds)a
left outer join
(select userid,if( '0'='${source}' , 'TOTAL',install_creative ) as install_creative,split(first_date,' ')[1] as install_date
from aggr.a_user_new
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${day}') and date(split(first_date,' ')[1])>=date_add('day',-8,date'${day}') )b
on a.userid=b.userid
where b.userid is not null
group by install_creative,install_date,ds,date_diff('day',cast(install_date  as timestamp),cast(ds as timestamp))
)b
on a.install_creative=b.install_creative and a.install_date=b.install_date
)tmp1
left outer join
(select snid,gameid,rate from dimension.all_game_name where snid='${snid}' and gameid='${gameid}')tmp2
on tmp1.snid=tmp2.snid and tmp1.gameid=tmp2.gameid
group by install_date,install_creative

union all

select '${day}' as install_date,creative as install_creative,cast(reg as double) as reg,
round(pay_total/reg/rate,2) as d0,0.0 as d1,0.0 as d2,0.0 as d3,0.0 as d4,0.0 as d5,0.0 as d6,0.0 as d7
from
(select snid,gameid,creative,count(distinct a.userid) as reg,sum(pay) as pay_total
from
(select snid,gameid,userid,if( '0'='${source}' , 'TOTAL',creative ) as creative
from default.nt_install
where snid='${snid}' and gameid='${gameid}' and ds='${day}' 
group by snid,gameid,userid,if( '0'='${source}' , 'TOTAL',creative ))a
left outer join
(select userid
from aggr.a_user_new
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${day}'))b
on a.userid=b.userid
left outer join
(select userid,sum(cast(amount as double)) as pay
from default.nt_payment
where snid='${snid}' and gameid='${gameid}' and ds='${day}'
group by userid
)c
on a.userid=c.userid
where b.userid is null
group by snid,gameid,creative)tmp
left outer join
(select snid,gameid,rate from dimension.all_game_name where snid='${snid}' and gameid='${gameid}')tmp2
on tmp.snid=tmp2.snid and tmp.gameid=tmp2.gameid
)ok  )ok