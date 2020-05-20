create table public.sources (
	id integer primary key generated always as identity,
	name varchar(255) not null
);
create table public.owners (
	id integer primary key generated always as identity,
	source_id integer not null,
	name varchar(255) not null,
	constraint owners_source_id_fk foreign key (source_id) references public.sources (id)
);
create table public.tables (
	id integer primary key generated always as identity,
	owner_id integer not null,
	name varchar(255) not null,
	constraint tables_owner_id_fk foreign key (owner_id) references public.owners (id)
);
create table field(
	id integer primary key generated always as identity,
	field_name varchar (255) not null ,
	size varchar (255) not null ,
	type varchar (255) not null ,
	tables_id int not null,
	constraint field_tables_id_fk foreign key(tables_id) references tables(id)
);

create table profile_result(
	id integer primary key generated always as identity,
	field_id int not null,
	date_field date,
	domain varchar (255) not null ,
	constraint profile_result_field_id_fk foreign key(field_id) references field(id)
);

insert into sources(name) values ('Ресурс 1');
insert into sources(name) values ('Ресурс 2');
insert into sources(name) values ('Ресурс 3');
insert into owners(source_id, name) VALUES (1,'Андрей');
insert into owners(source_id, name) VALUES (2,'Юджин');
insert into owners(source_id, name) VALUES (3,'Александр');
insert into tables(owner_id, name) VALUES (1,'Таблица 1');
insert into tables(owner_id, name) VALUES (2,'Таблица 2');
insert into tables(owner_id, name) VALUES (3,'Таблица 3');
insert into field(field_name, size, type, tables_id) VALUES ('Поле 1', '20', 'Текст', 1);
insert into field(field_name, size, type, tables_id) VALUES ('Поле 2', '25', 'Текст', 2);
insert into field(field_name, size, type, tables_id) VALUES ('Поле 3', '30', 'Текст', 3);
insert into profile_result(field_id, date_field, domain) VALUES (1,'2020-01-20','hello.world.ru');
insert into profile_result(field_id, date_field, domain) VALUES (2,'2020-02-02','company.ru');
insert into profile_result(field_id, date_field, domain) VALUES (3,'2020-04-10','dworld.ru');