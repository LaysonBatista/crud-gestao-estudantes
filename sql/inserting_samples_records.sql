/*INSERE DADOS NA TABELA DE ESTUDANTES*/
INSERT INTO labdatabase.ESTUDANTES (nome, data_nascimento, cpf, email)
VALUES 
('Maria Silva', '2000-03-12', '12345678901', 'maria.silva@email.com'),
('João Pereira', '1999-07-25', '98765432100', 'joao.pereira@email.com'),
('Ana Costa', '2001-11-02', '45678912300', 'ana.costa@email.com'),
('Carlos Souza', '1998-01-18', '32165498700', 'carlos.souza@email.com'),
('Beatriz Rocha', '2002-09-30', '78912345600', 'beatriz.rocha@email.com');

/*INSERE DADOS NA TABELA DE CURSOS*/
INSERT INTO labdatabase.CURSOS (nome_curso, carga_horaria)
VALUES
('Engenharia de Software', 3600),
('Administração', 3200),
('Design Gráfico', 2800),
('Ciência de Dados', 4000),
('Marketing Digital', 3000);

SELECT * FROM CURSOS;

/*INSERE DADOS NA TABELA DE MATRICULAS*/
INSERT INTO labdatabase.MATRICULAS (data_matricula, status_matricula, id_estudante, id_curso)
VALUES
('2023-02-10', 'Ativo', 1, 1),
('2023-02-15', 'Ativo', 2, 2),
('2023-03-01', 'Inativo', 3, 3),
('2024-01-20', 'Ativo', 4, 4),
('2024-02-05', 'Ativo', 5, 5);

/* INSERE DADOS NA TABELA DE NOTAS */
INSERT INTO labdatabase.NOTAS (nota_estudante, semestre, id_matricula)
VALUES
(8.5, '2023.1', 1),
(7.8, '2023.1', 2),
(5.9, '2023.1', 3),
(9.2, '2024.1', 4),
(8.7, '2024.1', 5);

COMMIT;
