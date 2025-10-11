package controller;

import java.util.ArrayList;
import java.util.List;
import model.Nota;

public class ControllerNota {
    private List<Nota> notas = new ArrayList<>();

    // CREATE
    public void adicionarNota(Nota nota) {
        notas.add(nota);
        System.out.println("Nota adicionada com sucesso!");
    }

    // READ
    public List<Nota> listarNotas() {
        return notas;
    }

    // UPDATE
    public void atualizarNota(int id, double novaNota, String novoSemestre) {
        for (Nota n : notas) {
            if (n.getIdNota() == id) {
                n.setNotaEstudante(novaNota);
                n.setSemestre(novoSemestre);
                System.out.println("Nota atualizada com sucesso!");
                return;
            }
        }
        System.out.println("Nota não encontrada!");
    }

    // DELETE
    public void removerNota(int id) {
        notas.removeIf(n -> n.getIdNota() == id);
        System.out.println("Nota removida com sucesso!");
    }
}
