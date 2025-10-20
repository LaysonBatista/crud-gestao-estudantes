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
            
    // Método para exibir cabeçalho do sistema
    public static void exibirCabecalho() {
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
    }
        
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao;

        // Exibir cabeçalho por 3 segundos antes do menu
        limparTela();
        exibirCabecalho();
        
        try {
            Thread.sleep(5000); // Aguarda 3 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        do {
            limparTela();
            
            System.out.println("---- MENU PRINCIPAL ----");
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
                    limparTela();
                    exibirCabecalho();
                    System.out.println("\nSaindo do sistema... Até logo!");
                    try {
                        Thread.sleep(5000); // Aguarda 2 segundos antes de sair
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
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
                    limparTela();
                    System.out.println("\nExibindo relatório de média por curso e semestre...");
                    rel.mediaPorCursoESemestre();
                    break;
                case 2:
                    limparTela();
                    System.out.println("\nExibindo desempenho dos cursos...");
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

    // Método para perguntar se o usuário deseja continuar inserindo
    public static boolean perguntarContinuarInserindo(Scanner input) {
        System.out.print("\nDeseja inserir mais algum registro? (S/N): ");
        String resposta = input.nextLine().trim().toUpperCase();
        
        if (resposta.equals("S") || resposta.equals("SIM")) {
            limparTela();
            return true; // Continua no menu de inserir
        } else if (resposta.equals("N") || resposta.equals("NÃO") || resposta.equals("NAO")) {
            System.out.println("Voltando ao menu principal...");
            return false; // Volta ao menu principal
        } else {
            System.out.println("Resposta inválida! Voltando ao menu principal...");
            return false; // Volta ao menu principal por segurança
        }
    }

    public static void menuInserir(Scanner input) {
        int opcao;
        boolean continuar = true;
        
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
                    System.out.println("\n== INSERIR ESTUDANTE ==");
                    new EstudanteController().inserir(input);
                    continuar = perguntarContinuarInserindo(input);
                    break;
                case 2:
                    System.out.println("\n== INSERIR CURSO ==");
                    new CursoController().inserir(input);
                    continuar = perguntarContinuarInserindo(input);
                    break;
                case 3:
                    System.out.println("\n== INSERIR NOTA ==");
                    new NotaController().inserirPorEstudanteId(input);
                    continuar = perguntarContinuarInserindo(input);
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (continuar);
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
                    limparTela();
                    System.out.println("\n== ATUALIZAR ESTUDANTE ==");
                    new EstudanteController().atualizar(input);
                    break;
                case 2:
                    limparTela();
                    System.out.println("\n== ATUALIZAR CURSO ==");
                    new CursoController().atualizar(input);
                    break;
                case 3:
                    limparTela();
                    System.out.println("\n== ATUALIZAR NOTA ==");
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
                    limparTela();
                    System.out.println("\n== REMOVER ESTUDANTE ==");
                    new EstudanteController().remover(input);
                    break;
                case 2:
                    limparTela();
                    System.out.println("\n== REMOVER CURSO ==");
                    new CursoController().remover(input);
                    break;
                case 3:
                    limparTela();
                    System.out.println("\n== REMOVER NOTA ==");
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
                    limparTela();
                    cursoCtl.listar();
                    break;
                case 2:
                    limparTela();
                    System.out.println("\n-- ESTUDANTES --");
                    estCtl.listar();
                    break;
                case 3:
                    limparTela();
                    System.out.println("\n-- MATRÍCULAS --");
                    matCtl.listar();
                    break;
                case 4:
                    limparTela();
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
        
            System.out.println("== LISTAR NOTAS ==");
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
                    limparTela();
                    System.out.println("\n== NOTAS POR CURSO E SEMESTRE ==");
                    notaCtl.listarPorCursoSemestre(input);
                    break;
                case 2:
                    limparTela();
                    System.out.println("\n== NOTAS POR ESTUDANTE ==");
                    notaCtl.listarPorEstudantePorId(input);
                    break;
                case 5:
                    limparTela();
                    //case 3: listarUltimasN(in);         break;
                    //case 4: listarPorFaixa(in);         break;
                    //case 5: System.out.println("Voltando..."); break;
                default: 
                    //System.out.println("Opção inválida.");
            }
        } while (opcao != 5);
    }

    // Método para limpar a tela
    public static void limparTela() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("windows")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("linux") || os.contains("unix") || os.contains("mac")) {
                // Linux/Unix/macOS - usa códigos ANSI
                System.out.print("\033[H\033[2J");
                System.out.flush();
            } else {
                // Fallback para outros sistemas
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Se não conseguir limpar a tela, apenas pula várias linhas
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }     
    }
}

