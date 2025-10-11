package controller;

import java.util.ArrayList;
import java.util.List;
import model.Curso;

public class ControllerCurso {
    private List<Curso> cursos = new ArrayList<>();

    // CREATE
    public void adicionarCurso(Curso curso) {
        cursos.add(curso);
        System.out.println("✅ Curso adicionado com sucesso!");
    }

    // READ
    public List<Curso> listarCursos() {
        return cursos;
    }

    // UPDATE
    public void atualizarCurso(int id, String novoNome, int novaCargaHoraria) {
        for (Curso c : cursos) {
            if (c.getIdCurso() == id) {
                c.setNomeCurso(novoNome);
                c.setCargaHoraria(novaCargaHoraria);
                System.out.println("✅ Curso atualizado com sucesso!");
                return;
            }
        }
        System.out.println("⚠️ Curso não encontrado!");
    }

    // DELETE
    public void removerCurso(int id) {
        cursos.removeIf(c -> c.getIdCurso() == id);
        System.out.println("✅ Curso removido com sucesso!");
    }
}
