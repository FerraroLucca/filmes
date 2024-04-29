create schema filme;

create table if not exists filme.TB_PAIS(
	ID serial primary key,
	NOME varchar(255) not null check (NOME <> ''),
	CAPITAL varchar(255) not null check (CAPITAL <> ''),
	CONTINENTE varchar(255) not null check (CONTINENTE <>''),
	LINGUA varchar(255) not null check (LINGUA <> '')
);

create table if not exists filme.TB_DIRETOR(
	ID serial primary key,
	NOME varchar(255) not null check (NOME <> ''),
	NASCIMENTO date not null,
	GENERO varchar(225) not null check (GENERO <> ''),
	FK_PAIS integer not null references filme.TB_PAIS(ID) on delete cascade
);

create table if not exists filme.TB_PRODUTORA(
	ID serial primary key,
	NOME varchar(255) not null check (NOME <> ''),
	ANO_FUNDACAO integer not null,
	FK_PAIS integer not null references filme.TB_PAIS(ID) on delete cascade
);

create table if not exists filme.TB_ATOR(
	ID serial primary key,
	NOME varchar(255) not null check (NOME <> ''),
	NASCIMENTO date not null,
	GENERO varchar(225) not null check (GENERO <> ''),
	DRT integer not null,
	FK_PAIS integer not null references filme.TB_PAIS(ID) on delete cascade
);

create table if not exists filme.TB_FILME(
	ID serial primary key,
	NOME varchar(255) not null check (NOME <> ''),
	CATEGORIA varchar(225) not null check (CATEGORIA <> ''),
	TEMPO_DURACAO integer not null,
	CLASSIFICACAO varchar(225) not null check (Classificacao <> ''),
	SINOPSE varchar(500) not null check (SINOPSE <> ''),
	POSTER varchar(500) not null check (POSTER <> ''),
	FK_PRODUTORA integer not null references filme.TB_PRODUTORA(ID) on delete cascade,
	FK_DIRETOR integer not null references filme.TB_DIRETOR(ID) on delete cascade
);

create table if not exists filme.TB_ATOR_FILME(
	ID serial primary key,
	FK_ATOR integer not null references filme.TB_ATOR(ID) on delete cascade,
	FK_FILME integer not null references filme.TB_FILME(ID) on delete cascade
);

create table if not exists filme.TB_TRAILER(
	ID serial primary key,
	LINK varchar(255) not null check (LINK <> ''),
	FK_FILME integer not null references filme.TB_FILME(ID) on delete cascade
);


















