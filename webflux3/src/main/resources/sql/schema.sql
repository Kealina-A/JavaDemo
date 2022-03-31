create table IF NOT EXISTS todo
(
    id    int auto_increment PRIMARY KEY,
    text  varchar(255) null,
    completed   bit null
);
delete from todo where 1=1;