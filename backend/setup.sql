create database if not exists csim2126;

use csim2126;

drop table if exists Menus;
drop table if exists Orders;

create table Orders(
    id bigint primary key not null auto_increment,
    date varchar(255),
    customerName varchar(255),
    deliveryAddress varchar(255),
    totalPrice double
);

desc Orders;

create table Menus(
    id bigint primary key not null auto_increment,
    name varchar(255),
    category varchar(255),
    description varchar(255),
    ingredients varchar(255),
    price double,
    orderId bigint references Orders(id)
);

desc Menus;

insert into Orders (id, date, customerName, deliveryAddress, totalPrice) VALUES  (default, '2023-11-24', 'customer A', 'address A', 10.0);
insert into Orders (id, date, customerName, deliveryAddress, totalPrice) VALUES  (default, '2023-11-25', 'customer B', 'address B', 11.1);
insert into Orders (id, date, customerName, deliveryAddress, totalPrice) VALUES  (default, '2023-11-26', 'customer C', 'address C', 12.2);

insert into Menus (id, name, category, description, ingredients, price, orderId) VALUES (default, 'name A', 'category A', 'description A', 'ingredients A', 10.0, null);
insert into Menus (id, name, category, description, ingredients, price, orderId) VALUES (default, 'name B', 'category B', 'description B', 'ingredients B', 11.1, null);
insert into Menus (id, name, category, description, ingredients, price, orderId) VALUES (default, 'name C', 'category C', 'description C', 'ingredients C', 12.2, null);
