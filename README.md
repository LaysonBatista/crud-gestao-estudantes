# Sistema de Gestão de Estudantes | Java & MySQL

Esse sistema é composto por um conjunto de tabelas que representam a gestão de estudantes, contendo tabelas como: estudantes, cursos, matriculas e notas.

Esse sistema deverá ser executado no ambiente proposto: Linux (Ubunto).

O sistema exige que as tabelas existam, então basta compilar o projeto e executar o script Java de configuração do banco de dados "DatabaseSetup.java" para criação das tabelas e preenchimento de dados de exemplos, siga os passos a seguir.

-> Video de demonstração: https://youtu.be/1Hq6aYKLh-M?si=z_N9gXrsLoeSdiwV

## Executando o Script de Configuração do Banco de dados

### Execução manual

obs: Antes de tudo, inicie o banco de dados MySQL no terminal do Ubunto (certifique-se de estar na pasta "database_services" para executar o comando):
```bash
docker compose up -d mysql
```

1. Faça um clone do projeto em alguma pasta:
```bash
git clone https://github.com/LaysonBatista/crud-gestao-estudantes
```

2. Na pasta do projeto, Compile:
```bash
javac -d bin -cp "lib/mysql-connector-java-8.0.30.jar" src/*.java src/conexion/*.java src/controller/*.java src/model/*.java src/reports/*.java src/utils/*.java
```

3. Execute o script de configuração do banco:
```bash
java -cp "bin:lib/mysql-connector-java-8.0.30.jar:src" DatabaseSetup
```

4. Execute o Menu Principal do Sistema:
```bash
java -cp "bin:lib/mysql-connector-java-8.0.30.jar:src" Main
```

## O que o Script DatabaseSetup.java Faz

O script `DatabaseSetup.java` irá:

1. **Conectar** ao banco de dados MySQL usando as credenciais configuradas
2. **Ler** o arquivo `sql/creat_tables_estudantes.sql`
3. **Executar** os comandos SQL para:
   - Apagar relacionamentos existentes (se houver)
   - Apagar tabelas existentes (se houver)
   - Criar as tabelas: ESTUDANTES, CURSOS, MATRICULAS, NOTAS
   - Criar os relacionamentos entre as tabelas
4. **Ler** o arquivo `sql/inserting_samples_records.sql`
5. **Inserir** dados de exemplo nas tabelas para realizar o select na splash_screen
6. **Desconectar** do banco de dados

## Pré-requisitos para execução do sistema

### 1. Softwares Necessários

#### Java Development Kit (JDK)
- **Versão**: JDK 8 ou superior
- **Verificação**: `java -version` e `javac -version`

#### Docker 
- **Para**: Executar MySQL via Docker compose
- **Verficação**: `docker --version` e `docker-compose --version`

### 2. Banco de Dados

#### Configuração Conexão MySQL
- **Host**: localhost
- **Porta**: 3306
- **Database**: labdatabase
- **Usuário**: labdatabase
- **senha**: lab@Database2025

## Organização
- [diagrams](diagrams): Nesse diretório está o [diagrama relacional](diagrams/DIAGRAMA_RELACIONAL_ESTUDANTES.PNG) (lógico) do sistema.
    * O sistema possui quatro entidades: ESTUDANTES, CURSOS, MATRICULAS E NOTAS.
- [lib](lib): Nesse diretório está o [mysql-connector-java-8.0.30.jar](mysql-connector-java-8.0.30.jar) para realizar a conexão com o banco de dados.
- [sql](sql): Nesse diretório estão os scripts para criação das tabelas e inserção de dados fictícios para testes do sistema
    * [create_tables_estudantes.sql](sql/create_tables_estudantes.sql): script responsável pela criação das tabelas, relacionamentos e criação de permissão no esquema LabDatabase.
    * [inserting_samples_records.sql](sql/inserting_samples_records.sql): script responsável pela inserção dos registros fictícios para testes do sistema.
- [src](src): Nesse diretório estão os scripts do sistema
    * [conexion](src/conexion): Nesse repositório encontra-se o [módulo de conexão com o banco de dados MySQL](src/conexion/Conexao.java).
    * [controller](src/controller/): Nesse diretório encontram-sem as classes controladoras, responsáveis por realizar inserção, alteração e exclusão dos registros das tabelas.
    * [model](src/model/): Nesse diretório encontram-ser as classes das entidades descritas no [diagrama relacional](diagrams/DIAGRAMA_RELACIONAL_ESTUDANTES.png).
    * [reports](src/reports/) Nesse diretório encontra-se a [classe](src/reports/Relatorios.java) responsável por gerar todos os relatórios do sistema.
    * [utils](src/utils/): Nesse diretório encontram-se scripts de automatização da [tela de informações iniciais](src/utils/splash_screen.java).


# crud-gestao-estudantes

## Integrantes

- Carlos Vinícius
- Layson Batista
- Lucas da Silva de Melo
- Sabrina Rosa
- Soffia Martins

## Tema

- Sistema de Gestão de Estudantes
