import java.util.Scanner;
import controller.CursoController;
import controller.EstudanteController;
import controller.MatriculaController;
import controller.NotaController;
import reports.Relatorios;
import utils.splash_screen;


public class Main {
        private static final CursoController cursoCtl = new CursoController();
        private static final EstudanteController estCtl = new EstudanteController();
        private static final MatriculaController matCtl = new MatriculaController();
        private static final NotaController notaCtl = new NotaController();
        private static final Relatorios rel = new Relatorios();
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
        System.out.println("CRIADO POR:\n" +
                "Carlos Vinicius\n" +
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
            System.out.println("5 - Listar registros");
            System.out.println("6 - Sair");
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
                menuListar(input);
                    break;
                case 6:
                    System.out.println("Saindo do sistema... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 6);

        input.close();
    }

    // ----------------- SUBMENUS --------------------

    public static void menuRelatorios(Scanner input) {
        int opcao;
        do {
            System.out.println("\n== RELATÓRIOS ==");
            System.out.println("1 - Média por curso e semestre");
            System.out.println("2 - Desempenho dos cursos");
            System.out.println("3 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Exibindo relatório de média por curso e semestre...");
                    rel.mediaPorCursoESemestre();
                    break;
                case 2:
                    System.out.println("Exibindo desempenho dos cursos...");
                    rel.desempenhoPorCurso(); 
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
                    new EstudanteController().inserir(input);
                    break;
                case 2:
                    System.out.println("Inserindo curso...");
                    new CursoController().inserir(input);
                    break;
                case 3:
                    System.out.println("Inserindo nota...");
                    new NotaController().inserirPorEstudanteId(input);
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
                    new EstudanteController().atualizar(input);
                    break;
                case 2:
                    System.out.println("Atualizando curso...");
                    new CursoController().atualizar(input);
                    break;
                case 3:
                    System.out.println("Atualizando nota...");
                    new NotaController().atualizar(input);
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
                    new EstudanteController().remover(input);
                    break;
                case 2:
                    System.out.println("Removendo curso...");
                    new CursoController().remover(input);
                    break;
                case 3:
                    System.out.println("Removendo nota...");
                    new NotaController().remover(input);
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 4);
    }

    public static void menuListar(Scanner input) {
        int opcao;
        do {
            System.out.println("\n== LISTAR REGISTROS ==");
            System.out.println("1 - Cursos");
            System.out.println("2 - Estudantes");
            System.out.println("3 - Matrículas");
            System.out.println("4 - Notas");
            System.out.println("5 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n-- CURSOS --");
                    cursoCtl.listar();
                    break;
                case 2:
                    System.out.println("\n-- ESTUDANTES --");
                    estCtl.listar();
                    break;
                case 3:
                    System.out.println("\n-- MATRÍCULAS --");
                    matCtl.listar();
                    break;
                case 4:
                    System.out.println("\n-- NOTAS --");
                    menuListarNotas(input);
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);
    }

    public static void menuListarNotas(Scanner input) {
    int opcao;
    do {
        System.out.println("1 - Por curso e semestre");
        System.out.println("2 - Por estudante");
        //System.out.println("3 - Últimas N notas");
        //System.out.println("4 - Por faixa de NOTA (min..max)");
        System.out.println("5 - Voltar");
        System.out.print("Escolha: ");
        opcao = input.nextInt();
        input.nextLine();

        switch (opcao) {
            case 1: 
            notaCtl.listarPorCursoSemestre(input);
             break;
            case 2:
            notaCtl.listarPorEstudantePorId(input);
                break;
            //case 3: listarUltimasN(in);         break;
            //case 4: listarPorFaixa(in);         break;
            //case 5: System.out.println("Voltando..."); break;
            default: System.out.println("Opção inválida.");
        }
    } while (opcao != 5);
}
}

