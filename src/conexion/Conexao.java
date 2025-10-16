import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Dados de conexão com o banco MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/labdatabase";
    private static final String USER = "labdatabase";
    private static final String PASSWORD = "labdatabase"; 

    /**
     * Cria e retorna uma conexão com o banco de dados.
     */
    public static Connection getConnection() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
            
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado!");
            e.printStackTrace();
            return null;
            
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados:");
            System.out.println(e.getMessage());
            return null;
        }
    }
}