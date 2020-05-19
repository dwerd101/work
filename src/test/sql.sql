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
	field_id int not null
	date_field date,
	domain varchar (255) not null ,
	constraint profile_result_field_id_fk foreign key(field_id) references field(id)
);