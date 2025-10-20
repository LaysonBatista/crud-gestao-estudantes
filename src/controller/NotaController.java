package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import conexion.*;

public class NotaController {

    // ----------------------------- SQLs ----------------------------
    private static final String SQL_INSERT = "INSERT INTO NOTAS (id_matricula, nota_estudante, semestre) VALUES (?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM NOTAS WHERE id_nota=?";
    private static final String SQL_NOTA_BY_ID = "SELECT id_nota, nota_estudante, semestre FROM NOTAS WHERE id_nota=?";

    private static final String SQL_UPDATE_NOTA = "UPDATE NOTAS SET nota_estudante=?, semestre=? WHERE id_nota=?";
    private static final String SQL_FINDALL_RESUMO = "SELECT n.id_nota, n.nota_estudante, n.semestre, " +
            "       m.id_matricula, e.nome AS estudante, c.nome_curso AS curso " +
            "FROM NOTAS n " +
            "JOIN MATRICULAS m ON m.id_matricula = n.id_matricula " +
            "JOIN ESTUDANTES e ON e.id_estudante = m.id_estudante " +
            "JOIN CURSOS c ON c.id_curso = m.id_curso " +
            "ORDER BY n.id_nota";
    private static final String SQL_FINDBY_MATRICULA = "SELECT id_nota, nota_estudante, semestre FROM NOTAS WHERE id_matricula=? ORDER BY id_nota";

    final String SQL = "SELECT c.id_curso, c.nome_curso, " +
            "       GROUP_CONCAT(DISTINCT n.semestre ORDER BY n.semestre SEPARATOR ', ') AS semestres " +
            "FROM CURSOS c " +
            "LEFT JOIN MATRICULAS m ON m.id_curso = c.id_curso " +
            "LEFT JOIN NOTAS n ON n.id_matricula = m.id_matricula " +
            "GROUP BY c.id_curso, c.nome_curso " +
            "ORDER BY c.id_curso";
    private static final String SQL_NOTAS_POR_CURSO_SEM = "SELECT n.id_nota, n.nota_estudante, n.semestre, m.id_matricula, "
            +
            "       e.nome AS estudante, c.nome_curso AS curso " +
            "FROM NOTAS n " +
            "JOIN MATRICULAS m ON m.id_matricula = n.id_matricula " +
            "JOIN ESTUDANTES e ON e.id_estudante = m.id_estudante " +
            "JOIN CURSOS c ON c.id_curso = m.id_curso " +
            "WHERE c.id_curso = ? AND n.semestre = ? " +
            "ORDER BY n.id_nota";

    private static final String SQL_ESTUDANTES_RAPIDO = "SELECT e.id_estudante, e.nome, COUNT(n.id_nota) AS qtd_notas "
            +
            "FROM ESTUDANTES e " +
            "LEFT JOIN MATRICULAS m ON m.id_estudante = e.id_estudante " +
            "LEFT JOIN NOTAS n ON n.id_matricula = m.id_matricula " +
            "GROUP BY e.id_estudante, e.nome " +
            "ORDER BY e.nome";

    // notas do estudante por ID (preciso)
    private static final String SQL_NOTAS_POR_ESTUDANTE_ID = "SELECT n.id_nota, n.nota_estudante, n.semestre, m.id_matricula, "
            +
            "       e.nome AS estudante, c.nome_curso AS curso " +
            "FROM NOTAS n " +
            "JOIN MATRICULAS m ON m.id_matricula = n.id_matricula " +
            "JOIN ESTUDANTES e ON e.id_estudante = m.id_estudante " +
            "JOIN CURSOS c ON c.id_curso = m.id_curso " +
            "WHERE e.id_estudante = ? " +
            "ORDER BY n.semestre, n.id_nota";

    // estudantes + qtd de matrículas
    private static final String SQL_ESTUDANTES_QTD_MATS = "SELECT e.id_estudante, e.nome, COUNT(m.id_matricula) AS qtd_mats "
            +
            "FROM ESTUDANTES e " +
            "LEFT JOIN MATRICULAS m ON m.id_estudante = e.id_estudante " +
            "GROUP BY e.id_estudante, e.nome " +
            "ORDER BY e.nome";

    // matrículas do estudante (curso + status)
    private static final String SQL_MATRICULAS_DO_ESTUDANTE = "SELECT m.id_matricula, m.status_matricula, c.nome_curso "
            +
            "FROM MATRICULAS m " +
            "JOIN CURSOS c ON c.id_curso = m.id_curso " +
            "WHERE m.id_estudante = ? " +
            "ORDER BY m.id_matricula";

    // ------------ MÉTODOS -------------------------------
    public void inserir(Scanner in) {
        System.out.print("ID matrícula: ");
        int idM = Integer.parseInt(in.nextLine());
        System.out.print("Nota (0–100 ou 0–10, conforme seu padrão): ");
        double nota = Double.parseDouble(in.nextLine());
        System.out.print("Semestre (ex.: 2024.1): ");
        String semestre = in.nextLine();

        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, idM);
            ps.setDouble(2, nota);
            ps.setString(3, semestre);
            System.out.println(ps.executeUpdate() > 0 ? "Nota inserida!" : "Falha ao inserir nota.");
        } catch (SQLIntegrityConstraintViolationException fk) {
            System.err.println("Matrícula inexistente (FK).");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir nota: " + e.getMessage());
        }
        listar(); // já mostra tudo após inserir
    }

    public void atualizar(java.util.Scanner in) {
        // Mostra a lista completa (ou use seus filtros antes)
        listar();

        System.out.print("\nID da nota que deseja atualizar: ");
        String idTxt = in.nextLine().trim();
        int idNota;
        try {
            idNota = Integer.parseInt(idTxt);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        try (var cn = conexion.Conexao.getConnection()) {

            // 1) Busca os valores atuais, para permitir ENTER = manter
            Double notaAtual = null;
            String semestreAtual = null;

            try (var ps = cn.prepareStatement(SQL_NOTA_BY_ID)) {
                ps.setInt(1, idNota);
                try (var rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Nota não encontrada.");
                        return;
                    }
                    notaAtual = rs.getDouble("nota_estudante");
                    semestreAtual = rs.getString("semestre");
                }
            }

            // 2) Pergunta novos valores (ENTER mantém)
            System.out.printf("Nova nota (atual: %.2f) [ENTER p/ manter]: ", notaAtual);
            String notaTxt = in.nextLine().trim();
            double novaNota = notaTxt.isEmpty() ? notaAtual : Double.parseDouble(notaTxt);

            System.out.printf("Novo semestre (atual: %s) [ENTER p/ manter]: ", semestreAtual);
            String novoSem = in.nextLine().trim();
            String semestre = novoSem.isEmpty() ? semestreAtual : novoSem;

            // 3) Atualiza
            try (var ps = cn.prepareStatement(SQL_UPDATE_NOTA)) {
                ps.setDouble(1, novaNota);
                ps.setString(2, semestre);
                ps.setInt(3, idNota);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "Nota atualizada!" : "Nada atualizado.");
            }

        } catch (java.sql.SQLException e) {
            System.err.println("Erro ao atualizar nota: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Valor de nota inválido.");
        }
    }

    public void remover(Scanner in) {
        listar();
        System.out.print("ID da nota: ");
        int id = Integer.parseInt(in.nextLine());

        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            System.out.println(ps.executeUpdate() > 0 ? "Nota removida!" : "Nada removido.");
        } catch (SQLException e) {
            System.err.println("Erro ao remover nota: " + e.getMessage());
        }
    }

    public void listar() {
        List<String> lista = new ArrayList<>();
        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_FINDALL_RESUMO);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(String.format("#%d | %.2f (%s) | mat:%d | %s -> %s",
                        rs.getInt("id_nota"),
                        rs.getDouble("nota_estudante"),
                        rs.getString("semestre"),
                        rs.getInt("id_matricula"),
                        rs.getString("estudante"),
                        rs.getString("curso")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar notas: " + e.getMessage());
        }
        lista.forEach(System.out::println);
    }

    public void listarPorMatricula(Scanner in) {
        System.out.print("ID matrícula: ");
        int idM = Integer.parseInt(in.nextLine());

        List<String> lista = new ArrayList<>();
        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_FINDBY_MATRICULA)) {
            ps.setInt(1, idM);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(String.format("#%d | %.2f | %s",
                            rs.getInt("id_nota"),
                            rs.getDouble("nota_estudante"),
                            rs.getString("semestre")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar notas por matrícula: " + e.getMessage());
        }
        lista.forEach(System.out::println);
    }

    public void listarPorCursoSemestre(Scanner in) {
        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_NOTAS_POR_CURSO_SEM)) {

            // ajuda: liste os cursos para a pessoa escolher
            listarCursosRapido(cn);

            System.out.print("ID do curso: ");
            int idCurso = Integer.parseInt(in.nextLine());
            System.out.print("Semestre (ex.: 2024.1): ");
            String sem = in.nextLine();

            ps.setInt(1, idCurso);
            ps.setString(2, sem);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n-- NOTAS por CURSO e SEMESTRE --");
                int count = 0;
                while (rs.next()) {
                    System.out.printf("#%d | %.2f (%s) | matricula:%d | %s -> %s%n",
                            rs.getInt("id_nota"),
                            rs.getDouble("nota_estudante"),
                            rs.getString("semestre"),
                            rs.getInt("id_matricula"),
                            rs.getString("estudante"),
                            rs.getString("curso"));
                    count++;
                }
                if (count == 0)
                    System.out.println("Nenhum registro.");
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar por curso/semestre: " + e.getMessage());
        }
    }

    // helper: lista id/nome dos cursos para facilitar a escolha
    private void listarCursosRapido(Connection cn) throws java.sql.SQLException {

        try (var ps = cn.prepareStatement(SQL);
                var rs = ps.executeQuery()) {

            System.out.println("\nCURSOS (com semestres disponíveis):");
            while (rs.next()) {
                int id = rs.getInt("id_curso");
                String nome = rs.getString("nome_curso");
                String semestres = rs.getString("semestres");
                if (semestres == null || semestres.isBlank())
                    semestres = "-";
                // >>> aqui troquei o separador por barra vertical
                System.out.printf("  %d - %s | %s%n", id, nome, semestres);
            }
        }
    }

    public void listarPorEstudantePorId(java.util.Scanner in) {
        try (var cn = conexion.Conexao.getConnection()) {

            // mostrar a lista para facilitar a escolha
            listarEstudantesRapido(cn);

            System.out.print("\nID do estudante: ");
            String idTxt = in.nextLine().trim();
            int id;
            try {
                id = Integer.parseInt(idTxt);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido.");
                return;
            }

            try (var ps = cn.prepareStatement(SQL_NOTAS_POR_ESTUDANTE_ID)) {
                ps.setInt(1, id);
                try (var rs = ps.executeQuery()) {
                    imprimirNotasResultset(rs, "\n-- NOTAS do ESTUDANTE (ID " + id + ") --");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar notas por estudante (ID): " + e.getMessage());
        }
    }

    private void imprimirNotasResultset(java.sql.ResultSet rs, String titulo) throws java.sql.SQLException {
        System.out.println(titulo);
        int count = 0;
        while (rs.next()) {
            System.out.printf("#%d | %.2f | %s | %s - %s%n",
                    rs.getInt("id_nota"),
                    rs.getDouble("nota_estudante"),
                    rs.getString("estudante"),
                    rs.getString("curso"),
                    rs.getString("semestre"));
            count++;
        }
        if (count == 0)
            System.out.println("Nenhum registro.");

        System.out.println();
        System.out.println("-".repeat(60));
        System.out.println();
    }

    // lista estudantes rapidamente (id, nome, qtd de notas)
    private void listarEstudantesRapido(java.sql.Connection cn) throws java.sql.SQLException {
        try (var ps = cn.prepareStatement(SQL_ESTUDANTES_RAPIDO);
                var rs = ps.executeQuery()) {
            System.out.println("\nESTUDANTES (qtd de notas):");
            while (rs.next()) {
                System.out.printf("  %d - %s | %d%n",
                        rs.getInt("id_estudante"),
                        rs.getString("nome"),
                        rs.getInt("qtd_notas"));
            }
        }
    }

    public void inserirPorEstudanteId(Scanner in) {
        String sqlInsert = "INSERT INTO NOTAS (id_matricula, nota_estudante, semestre) VALUES (?,?,?)";

        try (Connection cn = Conexao.getConnection()) {
            // 1) lista estudantes para facilitar a escolha
            listarEstudantesComQtdMats(cn);

            System.out.print("\nID do estudante: ");
            int idEst = Integer.parseInt(in.nextLine());

            // 2) recupera matrículas do estudante
            int idMatriculaEscolhida = -1;
            try (PreparedStatement ps = cn.prepareStatement(SQL_MATRICULAS_DO_ESTUDANTE)) {
                ps.setInt(1, idEst);
                try (ResultSet rs = ps.executeQuery()) {
                    java.util.List<Integer> mats = new java.util.ArrayList<>();
                    java.util.List<String> labels = new java.util.ArrayList<>();
                    while (rs.next()) {
                        mats.add(rs.getInt("id_matricula"));
                        labels.add(String.format("matricula:%d | %s (%s)",
                                rs.getInt("id_matricula"),
                                rs.getString("nome_curso"),
                                rs.getString("status_matricula")));
                    }

                    if (mats.isEmpty()) {
                        System.out.println("Este estudante não possui matrícula. Crie a matrícula primeiro.");
                        return;
                    } else if (mats.size() == 1) {
                        idMatriculaEscolhida = mats.get(0);
                        System.out.println("Usando " + labels.get(0));
                    } else {
                        System.out.println("\nMATRÍCULAS do estudante:");
                        for (String s : labels)
                            System.out.println("  " + s);
                        System.out.print("Digite o ID da matrícula desejada: ");
                        idMatriculaEscolhida = Integer.parseInt(in.nextLine());
                        if (!mats.contains(idMatriculaEscolhida)) {
                            System.out.println("Matrícula inválida.");
                            return;
                        }
                    }
                }
            }

            // 3) coleta dados da nota
            System.out.print("Semestre (ex.: 2024.1): ");
            String semestre = in.nextLine();
            System.out.print("Nota (ex.: 7.50): ");
            double nota = Double.parseDouble(in.nextLine());

            // 4) insere NOTA na matrícula escolhida
            try (PreparedStatement ps = cn.prepareStatement(sqlInsert)) {
                ps.setInt(1, idMatriculaEscolhida);
                ps.setDouble(2, nota);
                ps.setString(3, semestre);
                System.out.println(ps.executeUpdate() > 0 ? "Nota inserida!" : "Falha ao inserir nota.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao lançar nota por estudante: " + e.getMessage());
        }
    }

    private void listarEstudantesComQtdMats(Connection cn) throws SQLException {
        try (var ps = cn.prepareStatement(SQL_ESTUDANTES_QTD_MATS);
                var rs = ps.executeQuery()) {
            System.out.println("\nESTUDANTES (qtd matrículas):");
            while (rs.next()) {
                System.out.printf("  %d - %s | %d%n",
                        rs.getInt("id_estudante"),
                        rs.getString("nome"),
                        rs.getInt("qtd_mats"));
            }
        }
    }

}
