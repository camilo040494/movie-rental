drop table if exists charge cascade;
drop table if exists movie cascade;
drop table if exists movie_history cascade;
drop table if exists movie_users cascade;
drop table if exists privilege cascade;
drop table if exists role cascade;
drop table if exists roles_privileges cascade;
drop table if exists users_roles cascade;
drop table if exists application_user cascade;
drop table if exists user_history cascade;
drop table if exists user_liked_movies cascade;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 2 increment 1;
create table charge (charge_type varchar(31) not null, id bigserial, cost numeric(19, 2),
	from_date timestamp, transaction_id varchar(255), penalty numeric(19, 2), returned boolean,
	until_date timestamp, movie_id int8, user_id int8, primary key (id));
create table movie (id bigserial, availability boolean, description varchar(255), image text,
	rental_price numeric(19, 2), sale_price numeric(19, 2), stock int4, tittle varchar(255),
	primary key (id));
create table movie_history (movie_id int8 not null, charge_id int8 not null,
	primary key (movie_id, charge_id));
create table movie_users (user_id int8 not null, movie_id int8 not null,
	primary key (user_id, movie_id));
create table privilege (id bigserial, name varchar(255), primary key (id));
create table role (id bigserial, name varchar(255), primary key (id));
create table roles_privileges (role_id int8 not null, privilege_id int8 not null);
create table users_roles (user_id int8 not null, role_id int8 not null);
create table application_user (id bigserial, email varchar(255), enabled boolean,
	first_name varchar(255), last_name varchar(255), password varchar(255),
	token_expired boolean, primary key (id));
create table user_history (user_id int8 not null, history_id int8 not null,
	primary key (user_id, history_id));
create table user_liked_movies (user_id int8 not null, movie_id int8 not null,
	primary key (user_id, movie_id));
alter table charge add constraint charge_transaction_id unique (transaction_id);
alter table movie_history add constraint movie_history_id unique (charge_id);
alter table application_user add constraint user_email unique (email);
alter table user_history add constraint user_history_id unique (history_id);
alter table charge add constraint charge_movie_id foreign key (movie_id) references movie;
alter table charge add constraint charge_user_id foreign key (user_id) references application_user;
alter table movie_history add constraint movie_history_charge foreign key (charge_id)
	references charge;
alter table movie_history add constraint movie_history_movie foreign key (movie_id)
	references movie;
alter table movie_users add constraint movie_constraint_id foreign key (movie_id)
	references application_user;
alter table movie_users add constraint user_constraint_id foreign key (user_id)
	references movie;
alter table roles_privileges add constraint privilege_constraint_id foreign key (privilege_id)
	references privilege;
alter table roles_privileges add constraint role_constraint_id foreign key (role_id)
	references role;
alter table users_roles add constraint role_constraint_id foreign key (role_id)
	references role;
alter table users_roles add constraint user_constraint_id foreign key (user_id)
	references application_user;
alter table user_history add constraint history_constraint_id foreign key (history_id)
	references charge;
alter table user_history add constraint user_constraint_id foreign key (user_id)
	references application_user;
alter table user_liked_movies add constraint liked_movies_constraint_id
	foreign key (movie_id) references movie;
alter table user_liked_movies add constraint user_constraint_id foreign key (user_id)
	references application_user;

insert into application_user(id, email, enabled, first_name, last_name, password, token_expired)
	values(1, 'test@correo.com', true, 'admin', 'admin',
	'$2a$10$/lTgmWnvno15Zm9LqrKpfOYA14ccG6KxScWNKnPvWPf5Tbgx641zm', false);
insert into role(id, name) values (1, 'ROLE_ADMIN');
insert into role(id, name) values (2, 'ROLE_USER');
insert into role(id, name) values (3, 'ROLE_EMPLOYEE');
insert into users_roles (user_id, role_id) values(1, 1);