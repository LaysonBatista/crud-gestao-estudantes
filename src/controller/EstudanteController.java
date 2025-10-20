package controller;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import conexion.*;

public class EstudanteController {

    // ---------------- SQLs ----------------------------------
    private static final String SQL_UPDATE = "UPDATE ESTUDANTES SET nome=?, data_nascimento=?, cpf=?, email=? WHERE id_estudante=?";
    String sqlEst = "INSERT INTO ESTUDANTES (nome, data_nascimento, cpf, email) VALUES (?,?,?,?)";
    String sqlCursos = "SELECT id_curso, nome_curso FROM CURSOS ORDER BY id_curso";
    String sqlMat = "INSERT INTO MATRICULAS (data_matricula, status_matricula, id_estudante, id_curso) VALUES (?,?,?,?)";
    // SQLs em ordem: NOTAS -> MATRICULAS -> ESTUDANTES
    final String SQL_EXISTE = "SELECT 1 FROM ESTUDANTES WHERE id_estudante = ?";
    final String SQL_DEL_NOTAS = "DELETE n FROM NOTAS n " +
            "JOIN MATRICULAS m ON m.id_matricula = n.id_matricula " +
            "WHERE m.id_estudante = ?";
    final String SQL_DEL_MATS = "DELETE FROM MATRICULAS WHERE id_estudante = ?";
    final String SQL_DEL_EST = "DELETE FROM ESTUDANTES WHERE id_estudante = ?";
    final String SQL = "SELECT id_estudante, nome, data_nascimento, cpf, email " +
            "FROM ESTUDANTES ORDER BY id_estudante";

            // ----------------  MÉTODOS  ----------------------------------
    public void inserir(Scanner in) {
        System.out.print("Nome: ");
        String nome = in.nextLine();
        System.out.print("Data nascimento (AAAA-MM-DD): ");
        LocalDate dn = LocalDate.parse(in.nextLine());
        System.out.print("CPF (apenas números): ");
        String cpf = in.nextLine();
        System.out.print("Email: ");
        String email = in.nextLine();

        try (Connection cn = Conexao.getConnection()) {
            cn.setAutoCommit(false); // transação

            // 1) cria estudante e pega o ID gerado
            int idEstudanteGerado;
            try (PreparedStatement ps = cn.prepareStatement(sqlEst, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nome);
                ps.setDate(2, Date.valueOf(dn));
                ps.setString(3, cpf);
                ps.setString(4, email);
                if (ps.executeUpdate() == 0)
                    throw new SQLException("Falha ao inserir estudante.");
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next())
                        throw new SQLException("Sem ID gerado para estudante.");
                    idEstudanteGerado = keys.getInt(1);
                }
            }

            // 2) lista cursos para escolher
            System.out.println("\nCURSOS disponíveis:");
            try (PreparedStatement ps = cn.prepareStatement(sqlCursos);
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("  %d - %s%n", rs.getInt("id_curso"), rs.getString("nome_curso"));
                }
            }
            System.out.print("ID do curso para matrícula: ");
            int idCurso = Integer.parseInt(in.nextLine());

            // 3) cria matrícula ATIVA hoje
            try (PreparedStatement ps = cn.prepareStatement(sqlMat)) {
                ps.setDate(1, Date.valueOf(LocalDate.now()));
                ps.setString(2, "Ativo"); // respeita o CHECK ('Ativo','Inativo')
                ps.setInt(3, idEstudanteGerado);
                ps.setInt(4, idCurso);
                if (ps.executeUpdate() == 0)
                    throw new SQLException("Falha ao criar matrícula.");
            }

            cn.commit();
            System.out.println("Estudante e matrícula criados com sucesso!");

        } catch (SQLIntegrityConstraintViolationException dup) {
            System.err.println("CPF/email duplicado ou matrícula violou restrição (talvez já exista).");
        } catch (Exception e) {
            System.err.println("Erro ao inserir com matrícula: " + e.getMessage());
        }
    }

    public void atualizar(Scanner in) {
        listar();
        System.out.print("ID do estudante: ");
        int id = Integer.parseInt(in.nextLine());
        System.out.print("Nome: ");
        String nome = in.nextLine();
        System.out.print("Data nascimento (AAAA-MM-DD): ");
        LocalDate dn = LocalDate.parse(in.nextLine());
        System.out.print("CPF: ");
        String cpf = in.nextLine();
        System.out.print("Email: ");
        String email = in.nextLine();

        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, nome);
            ps.setDate(2, Date.valueOf(dn));
            ps.setString(3, cpf);
            ps.setString(4, email);
            ps.setInt(5, id);
            System.out.println(ps.executeUpdate() > 0 ? "Atualizado!" : "Nada atualizado.");
        } catch (SQLIntegrityConstraintViolationException dup) {
            System.err.println("CPF ou e-mail já existe.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar estudante: " + e.getMessage());
        }
    }

    public void remover(Scanner in) {
        listar();
        System.out.print("ID do estudante: ");
        int id = Integer.parseInt(in.nextLine());

        Connection cn = null;
        try {
            cn = Conexao.getConnection();
            cn.setAutoCommit(false); // inicia transação
            try (PreparedStatement ps = cn.prepareStatement(SQL_EXISTE)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("ID não encontrado. Nada a remover.");
                        cn.rollback();
                        cn.setAutoCommit(true);
                        return;
                    }
                }
            }
            // 1) apaga as NOTAS das matrículas do estudante
            try (PreparedStatement ps = cn.prepareStatement(SQL_DEL_NOTAS)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // 2) apaga as MATRÍCULAS do estudante
            try (PreparedStatement ps = cn.prepareStatement(SQL_DEL_MATS)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // 3) apaga o ESTUDANTE
            int afetados;
            try (PreparedStatement ps = cn.prepareStatement(SQL_DEL_EST)) {
                ps.setInt(1, id);
                afetados = ps.executeUpdate();
            }

            cn.commit();
            System.out.println(afetados > 0 ? "Removido!" : "Nada removido.");
        } catch (SQLException e) {
            try {
                if (cn != null)
                    cn.rollback();
            } catch (SQLException ignore) {
            }
            System.err.println("Erro ao remover estudante: " + e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true);
                    cn.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }

    public void listar() {
        List<String> lista = new ArrayList<>();

        try (Connection cn = Conexao.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_estudante");
                String nome = rs.getString("nome");
                java.time.LocalDate dn = rs.getDate("data_nascimento").toLocalDate();
                String cpf = maskCpf(rs.getString("cpf")); // deixa bonitinho
                String email = rs.getString("email");

                lista.add(String.format("#%d | %s | %s | %s | %s",
                        id, nome, email, cpf, dn)); // id | nome | email | cpf | nascimento
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar estudantes: " + e.getMessage());
            return;
        }

        if (lista.isEmpty()) {
            System.out.println("(Nenhum estudante cadastrado)");
        } else {
            lista.forEach(System.out::println);
        }
    }

    // mascara cpf
    private static String maskCpf(String cpf) {
        if (cpf == null)
            return "";
        String d = cpf.replaceAll("\\D", "");
        if (d.length() != 11)
            return cpf;
        return d.substring(0, 3) + "." + d.substring(3, 6) + "." + d.substring(6, 9) + "-" + d.substring(9);
    }

}
