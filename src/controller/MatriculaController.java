package controller;

import model.Matricula;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import conexion.*;

public class MatriculaController {

    private static final String SQL_INSERT = "INSERT INTO MATRICULAS (id_estudante, id_curso, data_matricula, status_matricula) VALUES (?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM MATRICULAS WHERE id_matricula=?";
    private static final String SQL_FINDALL_RESUMO = "SELECT m.id_matricula, e.nome AS estudante, c.nome_curso AS curso, m.status_matricula, m.data_matricula "
            +
            "FROM MATRICULAS m " +
            "JOIN ESTUDANTES e ON e.id_estudante = m.id_estudante " +
            "JOIN CURSOS c ON c.id_curso = m.id_curso " +
            "ORDER BY e.nome";
    private static final String SQL_FINDALL = "SELECT id_matricula, data_matricula, status_matricula, id_estudante, id_curso "
            +
            "FROM MATRICULAS ORDER BY id_matricula";

    // Mapeia entradas para o CHECK ('Ativo','Inativo')
    private String normalizaStatus(String s) {
        if (s == null)
            return null;
        String x = s.trim().toLowerCase();
        if (x.equals("ativo") || x.equals("ativa"))
            return "Ativo";
        if (x.equals("inativo") || x.equals("inativa"))
            return "Inativo";
        return null;
    }

    public void inserir(Scanner in) {
        System.out.print("ID estudante: ");
        int idE = Integer.parseInt(in.nextLine());
        System.out.print("ID curso: ");
        int idC = Integer.parseInt(in.nextLine());
        System.out.print("Data matrícula (AAAA-MM-DD): ");
        LocalDate d = LocalDate.parse(in.nextLine());
        System.out.print("Status (Ativo/Inativo): ");
        String status = normalizaStatus(in.nextLine());

        if (status == null) {
            System.err.println("Status inválido. Use 'Ativo' ou 'Inativo'.");
            return;
        }

        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, idE);
            ps.setInt(2, idC);
            ps.setDate(3, Date.valueOf(d));
            ps.setString(4, status);
            System.out.println(ps.executeUpdate() > 0 ? "Matrícula inserida!" : "Falha ao inserir matrícula.");
        } catch (SQLIntegrityConstraintViolationException dup) {
            System.err.println("Matrícula duplicada: (id_estudante, id_curso) já existe ou FK inválida.");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir matrícula: " + e.getMessage());
        }
    }

    public void remover(Scanner in) {
        listar(); // mostra para escolher
        System.out.print("ID matrícula: ");
        int id = Integer.parseInt(in.nextLine());
        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            System.out.println(ps.executeUpdate() > 0 ? "Removida!" : "Nada removido.");
        } catch (SQLException e) {
            System.err.println("Erro ao remover matrícula: " + e.getMessage());
        }
    }

    // Listagem resumida (String bonita no console)
    public void listar() {
        List<String> lista = new ArrayList<>();
        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_FINDALL_RESUMO);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(String.format("#%d | %s | %s | %s | %s",
                        rs.getInt("id_matricula"),
                        rs.getString("estudante"),
                        rs.getString("curso"),
                        rs.getString("status_matricula"),
                        rs.getDate("data_matricula").toLocalDate()));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar matrículas: " + e.getMessage());
        }
        lista.forEach(System.out::println);
    }

    // Se quiser lista “crua” como objetos:
    public List<Matricula> findAllComoObjetos() {
        List<Matricula> lista = new ArrayList<>();
        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_FINDALL);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Matricula(
                        rs.getInt("id_matricula"),
                        rs.getDate("data_matricula").toLocalDate(),
                        rs.getString("status_matricula"),
                        rs.getInt("id_estudante"),
                        rs.getInt("id_curso")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar matrículas: " + e.getMessage());
        }
        return lista;
    }
}
