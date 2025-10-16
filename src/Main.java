import java.util.Scanner;
import utils.splash_screen;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao;

        System.out.println("====================================");
        System.out.println("     SISTEMA DE GESTÃO DE ESTUDANTES");
        System.out.println("====================================");
        splash_screen.exibirContagemRegistros();
        System.out.println("Professor: Howard Roatti");
        System.out.println("Disciplina: Banco de Dados");
        System.out.println("Semestre: 2025/2\n");
        System.out.println("CRIADO POR:\n"+ 
        "Carlos Vinicius\n"+
        "Layson Batista\n" +
        "Lucas da Silva de Melo\n" +
        "Sabrina Rosa\n" +
        "Soffia Martins");
        System.out.println("====================================");

        do {
            System.out.println("\n---- MENU PRINCIPAL ----");
            System.out.println("1 - Relatórios");
            System.out.println("2 - Inserir registros");
            System.out.println("3 - Atualizar registros");
            System.out.println("4 - Remover registros");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    menuRelatorios(input);
                    break;
                case 2:
                    menuInserir(input);
                    break;
                case 3:
                    menuAtualizar(input);
                    break;
                case 4:
                    menuRemover(input);
                    break;
                case 5:
                    System.out.println("Saindo do sistema... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);

        input.close();
    }

    // ----------------- SUBMENUS --------------------

    public static void menuRelatorios(Scanner input) {
        int opcao;
        do {
            System.out.println("\n== RELATÓRIOS ==");
            System.out.println("1 - Média por turma");
            System.out.println("2 - Desempenho dos cursos");
            System.out.println("3 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Exibindo relatório de média por turma...");
                    break;
                case 2:
                    System.out.println("Exibindo desempenho dos cursos...");
                    break;
                case 3:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 3);
    }

    public static void menuInserir(Scanner input) {
        int opcao;
        do {
            System.out.println("\n== INSERIR REGISTROS ==");
            System.out.println("1 - Estudante");
            System.out.println("2 - Curso");
            System.out.println("3 - Nota");
            System.out.println("4 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Inserindo estudante...");
                    break;
                case 2:
                    System.out.println("Inserindo curso...");
                    break;
                case 3:
                    System.out.println("Inserindo nota...");
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 4);
    }

    public static void menuAtualizar(Scanner input) {
        int opcao;
        do {
            System.out.println("\n== ATUALIZAR REGISTROS ==");
            System.out.println("1 - Estudante");
            System.out.println("2 - Curso");
            System.out.println("3 - Nota");
            System.out.println("4 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Atualizando estudante...");
                    break;
                case 2:
                    System.out.println("Atualizando curso...");
                    break;
                case 3:
                    System.out.println("Atualizando nota...");
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 4);
    }

    public static void menuRemover(Scanner input) {
        int opcao;
        do {
            System.out.println("\n== REMOVER REGISTROS ==");
            System.out.println("1 - Estudante");
            System.out.println("2 - Curso");
            System.out.println("3 - Nota");
            System.out.println("4 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Removendo estudante...");
                    break;
                case 2:
                    System.out.println("Removendo curso...");
                    break;
                case 3:
                    System.out.println("Removendo nota...");
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 4);
    }
}
