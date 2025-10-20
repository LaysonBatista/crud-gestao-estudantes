USE labdatabase;

-- INSERE DADOS NA TABELA DE ESTUDANTES
INSERT INTO ESTUDANTES (nome, data_nascimento, cpf, email)
VALUES 
('Maria Silva', '2000-03-12', '12345678901', 'maria.silva@email.com'),
('João Pereira', '1999-07-25', '98765432100', 'joao.pereira@email.com'),
('Ana Costa', '2001-11-02', '45678912300', 'ana.costa@email.com'),
('Carlos Souza', '1998-01-18', '32165498700', 'carlos.souza@email.com'),
('Beatriz Rocha', '2002-09-30', '78912345600', 'beatriz.rocha@email.com');

-- INSERE DADOS NA TABELA DE CURSOS
INSERT INTO CURSOS (nome_curso, carga_horaria)
VALUES
('Engenharia de Software', 3600),
('Administracao', 3200),
('Design Grafico', 2800),
('Ciencia de Dados', 4000),
('Marketing Digital', 3000);

-- INSERE DADOS NA TABELA DE MATRICULAS
-- ATENÇÃO: status deve bater com o CHECK ('Ativo'/'Inativo')
INSERT INTO MATRICULAS (data_matricula, status_matricula, id_estudante, id_curso)
VALUES
('2023-02-10', 'Ativo',   1, 1),
('2023-02-15', 'Ativo',   2, 2),
('2023-03-01', 'Inativo', 3, 3),
('2024-01-20', 'Ativo',   4, 4),
('2024-02-05', 'Ativo',   5, 5);

-- INSERE DADOS NA TABELA DE NOTAS
INSERT INTO NOTAS (nota_estudante, semestre, id_matricula)
VALUES
(8.50, '2023.1', 1),
(7.80, '2023.1', 2),
(5.90, '2023.1', 3),
(9.20, '2024.1', 4),
(8.70, '2024.1', 5);

COMMIT;
