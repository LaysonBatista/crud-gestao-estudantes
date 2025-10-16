package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.Conexao;

public class splash_screen {

    // ----------------- MÉTODOS DE CONSULTA --------------------

    /**
     * Exibe a contagem de registros inseridos em cada tabela
     */
    public static void exibirContagemRegistros() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.getConnection();
            if (conn == null) {
                System.out.println("Erro: Não foi possível conectar ao banco de dados!");
                return;
            }

            System.out.println("\n= CONTAGEM DE REGISTROS EXISTENTES  =");
            System.out.println("=====================================");

            // Contar estudantes
            int totalEstudantes = contarRegistros(conn, "ESTUDANTES");
            System.out.println("Total de Estudantes: " + totalEstudantes);

            // Contar cursos
            int totalCursos = contarRegistros(conn, "CURSOS");
            System.out.println("Total de Cursos: " + totalCursos);

            // Contar matrículas
            int totalMatriculas = contarRegistros(conn, "MATRICULAS");
            System.out.println("Total de Matrículas: " + totalMatriculas);

            // Contar notas
            int totalNotas = contarRegistros(conn, "NOTAS");
            System.out.println("Total de Notas: " + totalNotas);

            // Total geral
            int totalGeral = totalEstudantes + totalCursos + totalMatriculas + totalNotas;
            System.out.println("=====================================");
            System.out.println("TOTAL GERAL DE REGISTROS: " + totalGeral);
            System.out.println("=====================================\n");

        } catch (SQLException e) {
            System.out.println("Erro ao consultar o banco de dados: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexões: " + e.getMessage());
            }
        }
    }

    /**
     * Conta o número de registros em uma tabela específica
     */
    private static int contarRegistros(Connection conn, String nomeTabela) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM " + nomeTabela;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        int total = 0;
        if (rs.next()) {
            total = rs.getInt("total");
        }
        
        rs.close();
        stmt.close();
        return total;
    }
}

    

