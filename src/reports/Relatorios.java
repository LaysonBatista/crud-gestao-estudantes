package reports;

import conexion.*;
import java.sql.*;

public class Relatorios {
    private static final double MEDIA_APROVACAO_PADRAO = 7.0;

    /**
     * Relátorio 1: Média por curso e semestre.
     * Agrupa por curso e semestre, considerando as notas lançadas.
     */

    public void mediaPorCursoESemestre() {
        String sql =
            "SELECT c.id_curso, c.nome_curso, n.semestre, " +
            "       ROUND(AVG(n.nota_estudante), 2) AS media " +
            "FROM NOTAS n " +
            "JOIN MATRICULAS m ON m.id_matricula = n.id_matricula " +
            "JOIN CURSOS c      ON c.id_curso = m.id_curso " +
            "GROUP BY c.id_curso, c.nome_curso, n.semestre " +
            "ORDER BY c.nome_curso, n.semestre";

        try (Connection cn = Conexao.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n== MÉDIA POR CURSO E SEMESTRE ==");
            System.out.printf("%-4s  %-30s  %-10s  %-6s%n",
                    "ID", "CURSO", "SEMESTRE", "MÉDIA");
            System.out.println("---------------------------------------------------------------");

            while (rs.next()) {
                int idCurso = rs.getInt("id_curso");
                String nomeCurso = rs.getString("nome_curso");
                String semestre = rs.getString("semestre");
                double media = rs.getDouble("media");

                System.out.printf("%-4d  %-30s  %-10s  %-6.2f%n",
                        idCurso, nomeCurso, semestre, media);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular média por curso e semestre: " + e.getMessage());
        }
    }

    /**
     * Relatório 2: Desempenho por curso com média de aprovação definida no código.
     * Chama a versão parametrizada com o corte padrão.
     */

        public void desempenhoPorCurso() {
        desempenhoPorCurso(MEDIA_APROVACAO_PADRAO);
    }

    /**
     * Relatório 2 (parametrizado): Desempenho por curso.
     * Calcula: Média geral, quantidade de notas, aprovados, reprovados e taxa de aprovação.
     * @param mediaAprovacao
     */

     public void desempenhoPorCurso(double mediaAprovacao) {
        String sql =
            "SELECT c.id_curso, c.nome_curso, " +
            "       ROUND(AVG(n.nota_estudante), 2) AS media_geral, " +
            "       COUNT(*) AS qtd_notas, " +
            "       SUM(n.nota_estudante >= ?) AS aprovados, " +       // boolean vira 0/1 no MySQL
            "       SUM(n.nota_estudante <  ?) AS reprovados, " +
            "       ROUND( (SUM(n.nota_estudante >= ?) / COUNT(*)) * 100, 2) AS taxa_aprovacao " +
            "FROM NOTAS n " +
            "JOIN MATRICULAS m ON m.id_matricula = n.id_matricula " +
            "JOIN CURSOS c      ON c.id_curso = m.id_curso " +
            "GROUP BY c.id_curso, c.nome_curso " +
            "ORDER BY c.nome_curso";

        try (Connection cn = Conexao.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            // Preenche o mesmo parâmetro nas 3 posições (>=, <, >=)
            ps.setDouble(1, mediaAprovacao);
            ps.setDouble(2, mediaAprovacao);
            ps.setDouble(3, mediaAprovacao);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n== DESEMPENHO POR CURSO (corte: " + mediaAprovacao + ") ==");
                System.out.printf("%-4s  %-30s  %-6s  %-8s  %-9s  %-10s  %-6s%n",
                        "ID", "CURSO", "MÉDIA", "QTD_NOTA", "APROVADOS", "REPROVADOS", "%APR");
                System.out.println("-------------------------------------------------------------------------------");

                while (rs.next()) {
                    int idCurso = rs.getInt("id_curso");
                    String nomeCurso = rs.getString("nome_curso");
                    double mediaGeral = rs.getDouble("media_geral");
                    int qtdNotas = rs.getInt("qtd_notas");
                    int aprovados = rs.getInt("aprovados");
                    int reprovados = rs.getInt("reprovados");
                    double taxa = rs.getDouble("taxa_aprovacao");

                    System.out.printf("%-4d  %-30s  %-6.2f  %-8d  %-9d  %-10d  %-6.2f%n",
                            idCurso, nomeCurso, mediaGeral, qtdNotas, aprovados, reprovados, taxa);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular desempenho por curso: " + e.getMessage());
        }
    }

}