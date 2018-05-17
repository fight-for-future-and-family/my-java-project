select install_date  as day,install_creative as source,install as reg,d1,d2, d3,d4, d5, d6,d7,'${hour}' as hour,'${minute}' as minute,
'${snid}' as snid,'${gameid}' as gameid,'${type}' as type,'${endDay}' as endDay
from
(select install_date,install_creative,cast(install as bigint) as install ,
cast(round(cast(d1 as double)/cast(install as double)*100,2) as varchar) as d1,
cast(round(cast(d2 as double)/cast(install as double)*100,2) as varchar) as d2,
cast(round(cast(d3 as double)/cast(install as double)*100,2) as varchar) as d3,
cast(round(cast(d4 as double)/cast(install as double)*100,2) as varchar) as d4,
cast(round(cast(d5 as double)/cast(install as double)*100,2) as varchar) as d5,
cast(round(cast(d6 as double)/cast(install as double)*100,2) as varchar) as d6,
cast(round(cast(d7 as double)/cast(install as double)*100,2) as varchar) as d7
from
(select install_creative,install_date,avg(install) as install,
sum(case when date_diff('day',date(install_date),date(stat_date))=1 then retention_uu end) as d1,
sum(case when date_diff('day',date(install_date),date(stat_date))=2 then retention_uu end) as d2,
sum(case when date_diff('day',date(install_date),date(stat_date))=3 then retention_uu end) as d3,
sum(case when date_diff('day',date(install_date),date(stat_date))=4 then retention_uu end) as d4,
sum(case when date_diff('day',date(install_date),date(stat_date))=5 then retention_uu end) as d5,
sum(case when date_diff('day',date(install_date),date(stat_date))=6 then retention_uu end) as d6,
sum(case when date_diff('day',date(install_date),date(stat_date))=7 then retention_uu end) as d7
from
(select stat_date,install_date,if( '0'='${source}' , 'TOTAL',install_creative ) as install_creative,sum(install) as install,sum(retention_uu) as retention_uu
from
(select stat_date,install_date,
upper(case when install_creative='' or install_creative is null or install_creative='unknown' then install_dlfrom else install_creative end) as install_creative,
cast(sum(install) as bigint) as install,cast(sum(retention_uu) as bigint) as  retention_uu 
from etl.a_user_retention_source 
where snid='${snid}' and gameid='${gameid}' and date(ds)>=date_add('day',-8,date'${day}') and date(install_date)>=date_add('day',-8,date'${day}')
group by stat_date,install_date,
upper(case when install_creative='' or install_creative is null or install_creative='unknown' then install_dlfrom else install_creative end))a
group by stat_date,install_date,if( '0'='${source}' , 'TOTAL',install_creative )
union all
select '${day}' as stat_date,install_date,if( '0'='${source}' , 'TOTAL',install_creative ) as install_creative,
count(1) as install,count(case when b.userid is not null then 1 end) as retention_uu
from
(select userid,upper(case when install_creative='' or install_creative is null or install_creative='unknown' then install_dlfrom else install_creative end) as install_creative,
split(first_date,' ')[1] as install_date
from aggr.a_user_new
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${day}') and date(split(first_date,' ')[1])>=date_add('day',-8,date'${day}'))a
left outer join
(select (case when a1.userid is not null then a1.userid else b1.userid end) as userid
from
	(select userid
	from default.nt_counter
	where snid='${snid}' and gameid='${gameid}' and ds='${day}'
	group by userid)a1
full outer join
(select userid
	from default.nt_dau
	where snid='${snid}' and gameid='${gameid}' and ds='${day}'
	group by userid)b1
on a1.userid=b1.userid
)b
on a.userid=b.userid
group by install_date,if( '0'='${source}' , 'TOTAL',install_creative )
)ok
group by install_creative,install_date
)ok2
)ok3
order by install_date,install desc