package controller;

import java.util.ArrayList;
import java.util.List;
import model.Matricula;

public class ControllerMatricula {
    private List<Matricula> matriculas = new ArrayList<>();

    // CREATE
    public void adicionarMatricula(Matricula matricula) {
        matriculas.add(matricula);
        System.out.println("✅ Matrícula adicionada com sucesso!");
    }

    // READ
    public List<Matricula> listarMatriculas() {
        return matriculas;
    }

    // UPDATE
    public void atualizarStatusMatricula(int id, String novoStatus) {
        for (Matricula m : matriculas) {
            if (m.getIdMatricula() == id) {
                m.setStatusMatricula(novoStatus);
                System.out.println("✅ Matrícula atualizada com sucesso!");
                return;
            }
        }
        System.out.println("⚠️ Matrícula não encontrada!");
    }

    // DELETE
    public void removerMatricula(int id) {
        matriculas.removeIf(m -> m.getIdMatricula() == id);
        System.out.println("✅ Matrícula removida com sucesso!");
    }
}
