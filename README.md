## Executando o Script de Configuração

### Execução manual

0. obs: Antes de tudo, inicie o banco de dados mysql no terminal do Ubunto (certifique-se de estar na pasta "database_services"):
```bash
docker compose up -d mysql
```

1. Faça um clone do projeto em alguma pasta (lembre-se de executar o comando 'git init' nela antes de fazer o clone):
```bash
git clone https://github.com/LaysonBatista/crud-gestao-estudantes
```

2. Compile o projeto:
```bash
javac -d bin -cp "src/conexion/mysql-connector-java-8.0.30.jar" src/*.java src/conexion/*.java src/model/*.java src/reports/*.java src/utils/*.java
```

3. Execute o script de configuração:
```bash
java -cp "bin:src/conexion/mysql-connector-java-8.0.30.jar:src" DatabaseSetup
```

## O que o Script Faz

O script `DatabaseSetup.java` irá:

1. **Conectar** ao banco de dados MySQL usando as credenciais configuradas
2. **Ler** o arquivo `sql/creat_tables_estudantes.sql`
3. **Executar** os comandos SQL para:
   - Apagar relacionamentos existentes (se houver)
   - Apagar tabelas existentes (se houver)
   - Criar as tabelas: ESTUDANTES, CURSOS, MATRICULAS, NOTAS
   - Criar os relacionamentos entre as tabelas
4. **Ler** o arquivo `sql/inserting_samples_records.sql`
5. **Inserir** dados de exemplo nas tabelas
6. **Desconectar** do banco de dados

## Estrutura das Tabelas Criadas

- **ESTUDANTES**: id_estudante, nome, data_nascimento, cpf, email
- **CURSOS**: id_curso, nome_curso, carga_horaria
- **MATRICULAS**: id_matricula, data_matricula, status_matricula, id_estudante, id_curso
- **NOTAS**: id_nota, nota_estudante, semestre, id_matricula


4. Execute o Menu Principal do Sistema:
```bash
java -cp "bin:src/conexion/mysql-connector-java-8.0.30.jar:src" Main
```

# crud-gestao-estudantes

## Integrantes

- Carlos Vinícius
- Layson Batista
- Lucas da Silva de Melo
- Sabrina Rosa
- Soffia Martins

## Tema

- Sistema de Gestão de Estudantes
