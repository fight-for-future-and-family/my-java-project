select creative as source,equip as newEquip,new_reg as dnu,'${snid}' as snid,'${gameid}' as gameid,'${day}' as day
from
(select clientid,creative,dau,equip,pay,payer,new_reg,new_role,concat(cast(role_rate as varchar),'%')role_rate,new_payer,
concat(cast(reg_pay_rate as varchar),'%')reg_pay_rate,concat(cast(role_pay_rate as varchar),'%')role_pay_rate,new_pay
from
(select clientid,creative,dau,equip,pay,payer,new_reg,new_role,round(role_rate*100,1)role_rate,new_payer,round(reg_pay_rate*100,1)reg_pay_rate,
round(role_pay_rate*100,1)role_pay_rate,new_pay
from
(select clientid,creative,dau,equip,pay,new_reg,new_role,new_payer,payer,
if(new_role=cast(0 as double),cast(0 as double),cast(new_role as double)/cast(new_reg as double)) as role_rate,
if(new_payer=cast(0 as double),cast(0 as double),cast(new_payer as double)/cast(new_reg as double)) as reg_pay_rate,
if(new_role=cast(0 as double),cast(0 as double),cast(new_payer as double)/cast(new_role as double)) as role_pay_rate,
new_pay
from
(select a.clientid,creative,count(1) as dau,count(e.udid) as equip,round(sum(pay),1) as pay,
count(distinct case when b.userid is not null then b.userid end) as payer,
count(distinct case when d.userid is null then a.userid end) as new_reg,
count(distinct case when d.userid is null and c.userid is not null then c.userid end) as new_role,
count(distinct case when d.userid is null and b.userid is not null then b.userid end) as new_payer,
sum( case when d.userid is null and b.userid is not null then pay end) as new_pay
from
(select clientid,userid,creative,udid
from
(select if( '0'='0' , '不分服',clientid ) as clientid,userid,if(creative is null or creative=' ' or creative='',extra['download_from'],creative) as creative,udid
from default.nt_dau
where snid='${snid}' and gameid='${gameid}' and ds='${day}'  and if( '0'='0' , 1=1,clientid='0' ) and creative<>'')tmp
group by clientid,userid,creative,udid
)a
left outer join
(select clientid,userid,sum(cast(amount as double)/cast(rate as double)) as pay
from
(select snid,gameid,if( '0'='0' , '不分服',clientid ) as  clientid,userid,amount
from default.nt_payment
where snid='${snid}' and gameid='${gameid}' and ds='${day}'  and if( '0'='0' , 1=1,clientid='0' )
)aa
left outer join
(select snid,gameid,rate,currency from dimension.all_game_name where snid='${snid}' and gameid='${gameid}')bb
on aa.snid=bb.snid and aa.gameid=bb.gameid
group by clientid,userid
)b
on a.clientid=b.clientid and a.userid=b.userid
left outer join
(select if( '0'='0' , '不分服',clientid ) as clientid,userid
from default.nt_counter
where snid='${snid}' and gameid='${gameid}' and ds='${day}'  and counter='role_choice'  and if( '0'='0' , 1=1,clientid='0'  )
group by if( '0'='0' , '不分服',clientid ),userid
)c
on a.clientid=c.clientid and a.userid=c.userid
 left outer join
(select userid
from aggr.a_user_new
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${day}' ))d
on a.userid=d.userid
left outer join
(select a.udid as udid
from
(select udid
from nt_equipment
where snid='${snid}' and gameid='${gameid}' and ds='${day}' 
group by udid)a
left outer join
(select udid
from aggr.a_equipment
where snid='${snid}' and gameid='${gameid}' and date(ds)=date_add('day',-1,date'${day}' ) and first_uninstall_time='uninstall' and last_uninstall_time='uninstall'
group by udid)b
on a.udid=b.udid
where b.udid is null)e
on a.udid=e.udid
group by a.clientid,creative)ok
)ok2
order by new_reg desc)ok3)ok4