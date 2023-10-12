set foreign_key_checks = 0;
drop table if exists accounts cascade;
drop table if exists transaction_type cascade;
drop table if exists transactions cascade;

create table if not exists accounts
(
    id       int primary key,
    username text,
    password text,
    balance  long
);

create table if not exists transaction_type
(
    id   int primary key,
    type text
);

create table if not exists transactions
(
    id         int primary key,
    account_id int,
    amount     long,
    type       int,
    foreign key (type) references transaction_type (id),
    foreign key (account_id) references accounts (id)
);

delete
from transaction_type
where id > 0;
delete
from accounts
where id > 0;
insert into transaction_type (id, type)
values (1, 'DEPOSIT'),
       (2, 'WITHDRAWAL');
set foreign_key_checks = 1;
