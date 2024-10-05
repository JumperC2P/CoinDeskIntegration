create table if not exists currency(
   "name" varchar(50) not null,
    short_name varchar(3) not null primary key,
    zh_tw_name varchar(10) not null
);


MERGE INTO currency (name, short_name, zh_tw_name)
    KEY(short_name)
    VALUES ('US Dollar', 'USD', '美金');

MERGE INTO currency (name, short_name, zh_tw_name)
    KEY(short_name)
    VALUES ('Euro', 'EUR', '歐元');

MERGE INTO currency (name, short_name, zh_tw_name)
    KEY(short_name)
    VALUES ('Great Britain Pound', 'GBP', '英鎊');