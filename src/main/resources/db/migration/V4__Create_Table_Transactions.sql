create table if not exists transactions
(
    id         int primary key,
    account_id int,
    amount     int,
    type       int,
    foreign key (type) references transaction_type (id),
    foreign key (account_id) references accounts (id)
);
