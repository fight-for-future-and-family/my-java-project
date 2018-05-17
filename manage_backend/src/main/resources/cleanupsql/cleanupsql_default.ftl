use default;
alter table nt_mirrorinfo drop partition(snid='${snid}', gameid='${gameid}', ds='${ds}');
alter table nt_payment drop partition(snid='${snid}', gameid='${gameid}', ds='${ds}');
alter table nt_counter drop partition(snid='${snid}', gameid='${gameid}', ds='${ds}');
alter table nt_milestone drop partition(snid='${snid}', gameid='${gameid}', ds='${ds}');
alter table nt_economy drop partition(snid='${snid}', gameid='${gameid}', ds='${ds}');
alter table nt_install drop partition(snid='${snid}', gameid='${gameid}', ds='${ds}');
alter table nt_gameinfo drop partition(snid='${snid}', gameid='${gameid}', ds='${ds}');
alter table nt_dau drop partition(snid='${snid}', gameid='${gameid}', ds='${ds}');