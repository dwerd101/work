create table public.source (
	id integer primary key generated always as identity,
	name varchar(255) not null
);
create table public.owner (
	id integer primary key generated always as identity,
	source_id integer not null,
	name varchar(255) not null,
	constraint owners_source_id_fk foreign key (source_id) references public.source (id)
);
create table public.table (
	id integer primary key generated always as identity,
	owner_id integer not null,
	name varchar(255) not null,
	constraint tables_owner_id_fk foreign key (owner_id) references public.owner (id)
);
create table field(
	id integer primary key generated always as identity,
	field_name varchar (255) not null ,
	size varchar (255) not null ,
	type varchar (255) not null ,
	tables_id int not null,
	constraint field_tables_id_fk foreign key(tables_id) references table(id)
);

create table profile_result(
	id integer primary key generated always as identity,
	field_id int not null,
	date_field date,
	domain varchar (255) not null ,
	constraint profile_result_field_id_fk foreign key(field_id) references field(id)
);

insert into source(name) values ('Ресурс 1');
insert into source(name) values ('Ресурс 2');
insert into source(name) values ('Ресурс 3');
insert into owner(source_id, name) VALUES (1,'Андрей');
insert into owner(source_id, name) VALUES (1,'Роман');
insert into owner(source_id, name) VALUES (2,'Юджин');
insert into owner(source_id, name) VALUES (3,'Александр');
insert into table(owner_id, name) VALUES (1,'Таблица 1');
insert into table(owner_id, name) VALUES (2,'Таблица 2');
insert into table(owner_id, name) VALUES (3,'Таблица 3');
insert into field(field_name, size, type, tables_id) VALUES ('Поле 1', '20', 'Текст', 1);
insert into field(field_name, size, type, tables_id) VALUES ('Поле 2', '25', 'Текст', 2);
insert into field(field_name, size, type, tables_id) VALUES ('Поле 3', '30', 'Текст', 3);
insert into profile_result(field_id, date_field, domain) VALUES (1,'2020-01-20','hello.world.ru');
insert into profile_result(field_id, date_field, domain) VALUES (2,'2020-02-02','company.ru');
insert into profile_result(field_id, date_field, domain) VALUES (3,'2020-04-10','dworld.ru');
insert into profile_result(field_id, date_field, domain) VALUES (4,'2020-04-10','dworld.ru');
insert into table(owner_id, name) VALUES (4,'Таблица 4');
alter table profile_result ADD comments varchar(100);

create view profile_result_view as
select  profile_result.id, sources.name as sources_name, owners.name as owners_name,
       tables.name as tables_name, field.field_name as field_name, profile_result.domain, profile_result.comment
from profile_result inner join field  on profile_result.field_id = field.id
                    join tables  on field.tables_id = tables.id
                    join owners  on tables.owner_id = owners.id
                    join sources  on owners.source_id = sources.id

                    alter table field rename column tables_id to table_id