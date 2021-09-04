create table if not exists customer
(
    id      bigserial    not null,
    "name"  varchar(50)  not null,
    surname varchar(100) not null,
    cpf     varchar(12)  not null,
    created_at timestamp,
    updated_at timestamp,
    constraint pk_customer_id primary key (id),
    constraint unq_customer_cpf unique (cpf)
);

create table if not exists address
(
    id          bigserial    not null,
    address     varchar(200) not null,
    id_customer int8         not null,

    constraint pk_address_id primary key (id),
    constraint fk_address_id_customer foreign key (id_customer) references customer (id) on delete cascade
)