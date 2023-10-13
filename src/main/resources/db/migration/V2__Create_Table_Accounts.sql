create table if not exists accounts
(
    id       int primary key,
    username text,
    password text,
    balance  int
);
