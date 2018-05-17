select '${seriesid}' as seriesid, '${seriesName}' as seriesName, dnu, install, 
dau, payer, equipment, '${beginDate}' as beginDate, '${endDate}' as endDate,'${dataType}' as dataType, '${ds}' as ds
from 
(select sum(cast(dnu as bigint)) as dnu,sum(cast(install as bigint)) install
from facts.f_game_day
where 1=1  and ds>='${beginDate}' and ds<='${endDate}' ${snGameIdsSql} )t1
left outer join 
(select count(distinct userid) as dau
from default.nt_dau
where 1=1 and ds>='${beginDate}' and ds<='${endDate}' ${snGameIdsSql})t2
on 1=1
left outer join 
(select count(distinct userid) payer
from default.nt_payment
where 1=1  and ds>='${beginDate}' and ds<='${endDate}' ${snGameIdsSql} )t3
on 1=1
left outer join 
(select  count(distinct udid) as equipment from
etl.total_equip_collect_isnew
where 1=1 and ds>='${beginDate}' and ds<='${endDate}' and is_new='1' ${snGameIdsSql} )t4
on 1=1