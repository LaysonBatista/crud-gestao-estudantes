package controller;

import java.util.ArrayList;
import java.util.List;
import model.Estudante;

public class ControllerEstudante {
    private List<Estudante> estudantes = new ArrayList<>();

    // CREATE
    public void adicionarEstudante(Estudante estudante) {
        estudantes.add(estudante);
        System.out.println("Estudante adicionado com sucesso!");
    }

    // READ
    public List<Estudante> listarEstudantes() {
        return estudantes;
    }

    // UPDATE
    public void atualizarEstudante(int id, String novoNome, String novoEmail) {
        for (Estudante e : estudantes) {
            if (e.getIdEstudante() == id) {
                e.setNome(novoNome);
                e.setEmail(novoEmail);
                System.out.println("Estudante atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Estudante não encontrado!");
    }

    // DELETE
    public void removerEstudante(int id) {
        estudantes.removeIf(e -> e.getIdEstudante() == id);
        System.out.println("Estudante removido com sucesso!");
    }
}
