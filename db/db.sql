CREATE DATABASE quarkus-social

-- Criando tabela users
CREATE TABLE USERS (
	id bigserial not null primary key,
	name varchar(100) not null,
	age integer not null
)

-- Criando tabela posts
CREATE TABLE POSTS (
    id bigserial not null primary key,
    post_text varchar(150) not null,
    dateTime timestamp not null,
    user_id bigint not null references USERS(id)
);

-- Criando tabela followers
CREATE TABLE FOLLOWERS(
	id bigserial notnull primary key,
	user_id bigint not null references USERS(id),
	follower_id bigint not null references USERS(id)
);