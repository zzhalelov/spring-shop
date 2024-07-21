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

CREATE TABLE cart_items
(
    id         SERIAL8 PRIMARY KEY,
    user_id    int8 REFERENCES users (id),
    product_id int8 REFERENCES products (id),
    quantity   INT NOT NULL
);

create table orders
(
    id         serial8 primary key,
    user_id    int8 references users (id),
    status     int          not null,
    address    varchar(255) not null,
    created_at timestamp    not null
);

create table order_product
(
    id         serial8 primary key,
    order_id   int8 references orders (id),
    product_id int8 references products (id),
    quantity   int not null
);

create table reviews
(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT REFERENCES users (id)    NOT NULL,
    product_id    BIGINT REFERENCES products (id) NOT NULL,
    review_rating SMALLINT                        NOT NULL,
    review_text   TEXT,
    review_data   TIMESTAMP
);

alter table products
    add column imageUrl VARCHAR(255);