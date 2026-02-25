create table if not exists users (
    id bigserial primary key,
    name varchar(100) not null,
    email varchar(150) not null unique,
    age int not null,
    created_at timestamptz not null default now()
    );
