
import conexion.Conexao;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * Classe responsável por configurar o banco de dados,
 * criando as tabelas e inserindo dados de exemplo.
 */
public class DatabaseSetup {
    
    private Connection connection;
    
    /**
     * Conecta ao banco de dados usando a classe Conexao
     */
    public void connect() {
        try {
            connection = Conexao.getConnection();
            if (connection != null) {
                System.out.println("Conexão estabelecida com sucesso!");
            } else {
                System.err.println("Falha ao conectar ao banco de dados!");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Desconecta do banco de dados
     */
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão encerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao desconectar: " + e.getMessage());
        }
    }
    
    /**
     * Lê um arquivo SQL e retorna seu conteúdo como String
     */
    private String readSqlFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo " + filePath + ": " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Divide uma string SQL em comandos individuais baseado no separador
     */
    private List<String> splitSqlCommands(String sql, String separator) {
        return Arrays.asList(sql.split(separator));
    }
    
    /**
     * Executa comandos DDL (Data Definition Language) - CREATE, ALTER, DROP
     */
    public void createTables(String sql) {
        List<String> commands = splitSqlCommands(sql, ";");
        
        try (Statement statement = connection.createStatement()) {
            for (String command : commands) {
                command = command.trim();
                if (command.length() > 0) {
                    System.out.println("Executando: " + command.substring(0, Math.min(command.length(), 50)) + "...");
                    try {
                        statement.execute(command);
                        System.out.println("Comando executado com sucesso!");
                    } catch (SQLException e) {
                        System.err.println("Erro ao executar comando: " + e.getMessage());
                        // Continua executando os próximos comandos mesmo se um falhar
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar statement: " + e.getMessage());
        }
    }
    
    /**
     * Executa comandos DML (Data Manipulation Language) - INSERT, UPDATE, DELETE
     */
    public void generateRecords(String sql, String separator) {
        List<String> commands = splitSqlCommands(sql, separator);
        
        try (Statement statement = connection.createStatement()) {
            for (String command : commands) {
                command = command.trim();
                if (command.length() > 0) {
                    System.out.println("Executando: " + command.substring(0, Math.min(command.length(), 50)) + "...");
                    try {
                        statement.executeUpdate(command);
                        System.out.println("Comando executado com sucesso!");
                    } catch (SQLException e) {
                        System.err.println("Erro ao executar comando: " + e.getMessage());
                        // Continua executando os próximos comandos mesmo se um falhar
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar statement: " + e.getMessage());
        }
    }
    
    /**
     * Método principal que executa todo o processo de configuração do banco
     */
    public void run() {
        System.out.println("=== INICIANDO CONFIGURAÇÃO DO BANCO DE DADOS ===");
        
        // Conecta ao banco
        connect();
        
        try {
            // Lê e executa o arquivo de criação de tabelas
            String createTablesSql = readSqlFile("sql/creat_tables_estudantes.sql");
            if (!createTablesSql.isEmpty()) {
                System.out.println("\n--- CRIANDO TABELAS ---");
                createTables(createTablesSql);
                System.out.println("Tabelas criadas com sucesso!");
            } else {
                System.err.println("Erro: Arquivo de criação de tabelas não encontrado ou vazio!");
                return;
            }
            
            // Lê e executa o arquivo de inserção de dados
            String insertDataSql = readSqlFile("sql/inserting_samples_records.sql");
            if (!insertDataSql.isEmpty()) {
                System.out.println("\n--- INSERINDO DADOS DE EXEMPLO ---");
                generateRecords(insertDataSql, ";");
                System.out.println("Dados inseridos com sucesso!");
            } else {
                System.err.println("Erro: Arquivo de inserção de dados não encontrado ou vazio!");
                return;
            }
            
            System.out.println("\n=== CONFIGURAÇÃO DO BANCO CONCLUÍDA COM SUCESSO! ===");
            
        } catch (Exception e) {
            System.err.println("Erro durante a configuração: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Sempre desconecta no final
            disconnect();
        }
    }
    
    /**
     * Método main para executar o script
     */
    public static void main(String[] args) {
        DatabaseSetup setup = new DatabaseSetup();
        setup.run();
    }
}
