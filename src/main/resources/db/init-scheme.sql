create table account
(
    id      identity primary key,
    number  varchar(20)    not null,
    balance decimal(20, 2) not null default 0
);

create unique index uidx_acc_number ON account (number);

insert into account (number, balance)
values ('123', 10);
insert into account (number)
values ('321');