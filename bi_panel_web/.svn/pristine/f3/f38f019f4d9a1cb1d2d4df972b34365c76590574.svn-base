select install_date,
sum(cast(case when retention_day=0 then new_equip end as bigint)) as new_equip,                    
cast(round(sum(case when retention_day=1 then retention_rate end)*100,2) as varchar) as d1,
cast(round(sum(case when retention_day=2 then retention_rate end)*100,2) as varchar) as d2,
cast(round(sum(case when retention_day=3 then retention_rate end)*100,2) as varchar) as d3,
cast(round(sum(case when retention_day=4 then retention_rate end)*100,2) as varchar) as d4,
cast(round(sum(case when retention_day=5 then retention_rate end)*100,2) as varchar) as d5,
cast(round(sum(case when retention_day=6 then retention_rate end)*100,2) as varchar) as d6,
cast(round(sum(case when retention_day=7 then retention_rate end)*100,2) as varchar) as d7
from 
(select install_date,new_equip,date_diff('day',cast(install_date as date),cast(ds as date)) as retention_day,
cast(retention_equip as double)/cast(new_equip as double) as retention_rate
from facts.equip_retention_ltv
where  snid='${snid}' and gameid='${gameid}'  and install_date>='${start_date}' and ds>='${start_date}' and date(ds)<=date_add('day',7,date'${end_date}'))t
group by install_date
order by install_date