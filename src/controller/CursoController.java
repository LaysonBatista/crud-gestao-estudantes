package controller;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import conexion.*;

public class CursoController {
    // SQLs
    private static final String SQL_INSERT = "INSERT INTO CURSOS (nome_curso, carga_horaria) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE CURSOS SET nome_curso=?, carga_horaria=? WHERE id_curso=?";
    //private static final String SQL_DELETE = "DELETE FROM CURSOS WHERE id_curso=?";
    //private static final String SQL_FINDALL = "SELECT id_curso, nome_curso, carga_horaria FROM CURSOS ORDER BY id_curso";

    public void inserir(Scanner in) {
        System.out.print("Nome do curso: ");
        String nome = in.nextLine();
        System.out.print("Carga horária - CH: ");
        int ch = Integer.parseInt(in.nextLine());

        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, nome);
            ps.setInt(2, ch);
            System.out.println(ps.executeUpdate() > 0 ? "Curso inserido!" : "Falha ao inserir.");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir curso: " + e.getMessage());
        }
    }

    public void atualizar(Scanner in) {
        listar();
        System.out.print("ID do curso: ");
        int id = Integer.parseInt(in.nextLine());
        System.out.print("Novo nome: ");
        String nome = in.nextLine();
        System.out.print("Nova carga horária: ");
        int ch = Integer.parseInt(in.nextLine());

        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, nome);
            ps.setInt(2, ch);
            ps.setInt(3, id);
            System.out.println(ps.executeUpdate() > 0 ? "Atualizado!" : "Nada atualizado.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar curso: " + e.getMessage());
        }
    }

    public void remover(Scanner in) {
        listar();
    System.out.print("ID do curso: ");
    int id = Integer.parseInt(in.nextLine());

    final String SQL_COUNT_NOTAS =
        "SELECT COUNT(*) " +
        "FROM NOTAS n " +
        "JOIN MATRICULAS m ON m.id_matricula = n.id_matricula " +
        "WHERE m.id_curso = ?";
    final String SQL_COUNT_MATS =
        "SELECT COUNT(*) FROM MATRICULAS WHERE id_curso = ?";

    final String SQL_DEL_NOTAS =
        "DELETE n FROM NOTAS n " +
        "JOIN MATRICULAS m ON m.id_matricula = n.id_matricula " +
        "WHERE m.id_curso = ?";
    final String SQL_DEL_MATS =
        "DELETE FROM MATRICULAS WHERE id_curso = ?";
    final String SQL_DEL_CURSO =
        "DELETE FROM CURSOS WHERE id_curso = ?";

    Connection cn = null;
    try {
        cn = Conexao.getConnection();
        cn.setAutoCommit(false); // inicia transação

        
        int qtdNotas = 0, qtdMats = 0;
        try (PreparedStatement ps = cn.prepareStatement(SQL_COUNT_NOTAS)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) qtdNotas = rs.getInt(1);
            }
        }
        try (PreparedStatement ps = cn.prepareStatement(SQL_COUNT_MATS)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) qtdMats = rs.getInt(1);
            }
        }
        System.out.printf("Isso vai excluir %d nota(s) e %d matrícula(s) desse curso.%n", qtdNotas, qtdMats);

        // 1) NOTAS do curso
        try (PreparedStatement ps = cn.prepareStatement(SQL_DEL_NOTAS)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }

        // 2) MATRÍCULAS do curso
        try (PreparedStatement ps = cn.prepareStatement(SQL_DEL_MATS)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }

        // 3) CURSO
        int afetados;
        try (PreparedStatement ps = cn.prepareStatement(SQL_DEL_CURSO)) {
            ps.setInt(1, id);
            afetados = ps.executeUpdate();
        }

        cn.commit();
        System.out.println(afetados > 0 ? "Curso removido!" : "Nada removido.");
    } catch (SQLException e) {
        try { if (cn != null) cn.rollback(); } catch (SQLException ignore) {}
        System.err.println("Erro ao remover curso: " + e.getMessage());
    } finally {
        try { if (cn != null) { cn.setAutoCommit(true); cn.close(); } } catch (SQLException ignore) {}
    }
    }

    public void listar() {
        List<String> lista = new ArrayList<>();
        final String SQL = "SELECT id_curso, nome_curso, carga_horaria FROM CURSOS ORDER BY id_curso";

        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(String.format("#%d | %s | CH:%d",
                        rs.getInt("id_curso"),
                        rs.getString("nome_curso"),
                        rs.getInt("carga_horaria")));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar cursos: " + e.getMessage());
            return;
        }

        System.out.println("\n-- CURSOS --");
        if (lista.isEmpty()) {
            System.out.println("(Nenhum curso cadastrado)");
        } else {
            lista.forEach(System.out::println);
        }
    }

}
