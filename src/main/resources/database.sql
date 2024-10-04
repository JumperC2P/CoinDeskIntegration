create table if not exists currency(
                                       "name" varchar(50) not null,
    short_name varchar(3) not null primary key,
    zh_tw_name varchar(10) not null,
    latest_rate decimal(12,10),
    latest_update_time timestamp
    );

create table if not exists exchange_rate (
                                             update_time timestamp not null,
                                             base_currency varchar(3) not null,
    target_currency varchar(20) not null,
    rate decimal(12,10) not null,
    primary key(update_time, base_currency, target_currency)
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