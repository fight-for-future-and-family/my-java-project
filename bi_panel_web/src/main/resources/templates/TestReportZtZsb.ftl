select install_date,
sum(cast(case when retention_day=0 then install end as bigint))as install,  
round(sum(case when retention_day=0 then cast(pay as double)/cast(rate as double) end),2) as d0,                
round(sum(case when retention_day=1 then cast(pay as double)/cast(rate as double) end),2) as d1,
round(sum(case when retention_day=2 then cast(pay as double)/cast(rate as double) end),2) as d2,
round(sum(case when retention_day=3 then cast(pay as double)/cast(rate as double) end),2) as d3,
round(sum(case when retention_day=4 then cast(pay as double)/cast(rate as double) end),2) as d4,
round(sum(case when retention_day=5 then cast(pay as double)/cast(rate as double) end),2) as d5,
round(sum(case when retention_day=6 then cast(pay as double)/cast(rate as double) end),2) as d6,
round(sum(case when retention_day=7 then cast(pay as double)/cast(rate as double) end),2) as d7
from                                           
(select install_date,install,date_diff('day',cast(install_date as date),cast(ds as date)) as retention_day,
cast(total_payment as double)/cast(install as double)as pay,snid,gameid
from etl.a_user_retention_ltv
where  snid='${snid}' and gameid='${gameid}'  and install_date>='${start_date}' and ds>='${start_date}' and date(ds)<=date_add('day',7,date'${end_date}'))t1
left outer join 
(select snid,gameid,rate
from dimension.all_game_name
where snid='${snid}' and gameid='${gameid}' ) t2
on t1.snid=t2.snid and t1.gameid=t2.gameid
group by install_date
order by install_date