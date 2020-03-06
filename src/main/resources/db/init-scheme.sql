create table if not exists account
(
    id      identity primary key,
    number  varchar(20)    not null,
    balance decimal(20, 2) not null default 0
);

create index if not exists idx_acc_number ON account (number);

insert into account (number)
values ('123');