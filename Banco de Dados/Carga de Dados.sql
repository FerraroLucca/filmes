insert into filme.TB_PAIS(NOME, CAPITAL, CONTINENTE, LINGUA) values
	('Estados Unidos', 'Washinton', 'America', 'Ingles'),
	('Coreia', 'Seul', 'Asia', 'Coreano'),
	('Japao', 'Tokio', 'Asia', 'Japao'),
	('Brasil', 'Brasilia', 'America', 'Portugues'),
	('Inglaterra', 'Londres', 'Europa', 'Ingles');
	
insert into filme.TB_DIRETOR(NOME, NASCIMENTO, GENERO, FK_PAIS) values
    ('Chistopher Nolan', '1982-08-12', 'Masculino', 1),
    ('Sandra Bullock', '1965-03-14', 'Feminino', 2),
    ('Joe Russo', '1970-06-18', 'Masculino', 3),
    ('Scarllet Jonhson', '1978-08-22', 'Feminino', 4),
    ('Bill gibson', '1990-10-25', 'Masculino', 5);
	
insert into filme.TB_PRODUTORA(NOME, ANO_FUNDACAO, FK_PAIS) values
    ('Warner', 1930, 1),
    ('Paramount', 1940, 1),
    ('Disney', 1886, 1),
    ('Lucas Film', 1973, 1),
    ('Marvel', 1945, 1);
   
insert into filme.TB_ATOR(NOME, NASCIMENTO, GENERO, DRT, FK_PAIS) values
	('Cillian Murphy', '1978-08-12', 'Masculino', 123123, 1),
	('Scarlett Johansson', '1978-08-12', 'Feminino', 321321, 2),
	('Dwane Jhonson', '1978-08-12', 'Masculino', 456456, 3),
	('Michelle Yeoh', '1978-08-12', 'Feminino', 654654, 4),
	('Vin Diesel', '1978-08-12', 'Masculino', 789789, 5);

insert into filme.tb_filme(NOME, CATEGORIA, TEMPO_DURACAO, CLASSIFICACAO, SINOPSE, POSTER, FK_PRODUTORA, FK_DIRETOR) values
	('Oppenheimer', 'Suspense', 180, '16', 'O físico J. Robert Oppenheimer trabalha com uma equipe de cientistas durante o Projeto Manhattan, levando ao desenvolvimento da bomba atômica.', 'Poster', 1,1),
	('Terrifier', 'Terror', 120, '18', 'Enquanto cuida de duas crianças no halloween, uma babá encontra uma antiga fita VHS no saco de doces.', 'Poster', 2,2),
	('Juntos e Misturados', 'Comedia', 90, 'Livre', 'Após um primeiro encontro desastroso, Jim e Lauren viajam, por coincidência, para o mesmo hotel durante as férias, junto com seus filhos de casamentos anteriores.', 'Poster', 3,3),
	('Plus One', 'Romance', 120, '14', 'Em um verão cheio de casamentos, dois amigos solteiros de longa data decidem formar um par para irem acompanhados a todas as cerimônias', 'Poster', 4,4),
	('O menino e a Garca', 'Aventura', 150, 'Livre', 'Mahito, um menino de 12 anos, luta para se estabelecer em uma nova cidade após a morte de sua mãe. ', 'Poster', 5,5);


insert into filme.tb_ator_filme (FK_ATOR, FK_FILME) values
	(1,1),
	(2,2),
	(3,3),
	(4,4),
	(5,5);

insert into filme.tb_trailer (LINK, FK_FILME) values
	('www.youtube.com', 1),
	('www.youtube.com', 2),
	('www.youtube.com', 3),
	('www.youtube.com', 4),
	('www.youtube.com', 5);


   	