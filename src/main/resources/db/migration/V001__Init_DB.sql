create sequence hibernate_sequence start 1 increment 1;
create table IF NOT EXISTS role
(
    id      serial not null,
    name    varchar(50),
    primary key (id)
);

create table IF NOT EXISTS users
(
    id          serial not null,
    email      varchar(50) unique,
    password   varchar(255),
    first_name varchar(50),
    last_name  varchar(50),
    role_id    int8,
    primary key (id),
    foreign key (role_id)
        references role (id)
);

create table IF NOT EXISTS manufacturer
(
    id    serial not null,
    name varchar(50) unique,
    primary key (id)
);

create table IF NOT EXISTS product
(
    id              serial not null,
    name           varchar(50) unique ,
    price          float8 not null,
    manufacturer_id serial,
    primary key (id),
    foreign key (manufacturer_id)
        references manufacturer (id)
);

insert into role (name) values ('Admin');
insert into role (name) values ('User');
