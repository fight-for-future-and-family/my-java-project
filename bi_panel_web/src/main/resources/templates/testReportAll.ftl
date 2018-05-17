select t1.ds,t1.dau,t1.dnu,t1.install,t1.role_choice,
cast(t1.payment_amount as double)/cast(t3.rate as double) as payment_amount,payer,
concat(cast(round(pay_rate*100,2) as varchar),'%') as pay_rate,
round(cast(t1.arpu as double)/cast(t3.rate as double),2) as arpu,
round(cast(t1.arppu as double)/cast(t3.rate as double),2) as arppu,
t2.new_equip,t2.install as install2,
concat(cast(round(cast(t2.install as double)/cast(t2.new_equip as double)*100,2) as varchar),'%') as jihuo_xinzeng_rate,
concat(cast(round(cast(t1.role_choice as double)/cast(t1.dnu as double)*100,2) as varchar),'%') as install_role_rate
from
(select ds,dau,dnu,install,role_choice,payment_amount,payer,pay_rate,arpu,arppu,snid,gameid
from facts.f_game_day
where snid='${snid}' and gameid='${gameid}' and ds>='${start_date}' and  ds<='${end_date}')t1
left outer join 
(select ds,new_equip,install
from facts.f_equip_day
where snid='${snid}' and gameid='${gameid}' and ds>='${start_date}' and  ds<='${end_date}')t2
on t1.ds=t2.ds
left outer join 
(select rate,snid,gameid
from dimension.all_game_name)t3
on t1.snid=t3.snid and t1.gameid=t3.gameid
order by t1.ds
