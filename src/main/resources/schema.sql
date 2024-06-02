create table categories
(
    id   serial8 primary key,
    name varchar(255) not null unique
);

create table options
(
    id          serial8 primary key,
    name        varchar(255)                    not null,
    category_id int8 references categories (id) not null
);

create table products
(
    id          serial8 primary key,
    name        varchar(255)                    not null,
    price       double precision                not null,
    category_id int8 references categories (id) not null
);

create table values
(
    id         serial8 primary key,
    name       varchar(255)                  not null,
    product_id int8 references products (id) not null,
    option_id  int8 references options (id)  not null,
    unique (product_id, option_id)
);

create table users
(
    id       serial8 primary key,
    name     varchar(255),
    lastname varchar(255),
    login    varchar(255) not null unique,
    password varchar(255) not null,
    role     int,
    created  timestamp
);