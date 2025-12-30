package br.com.helpdesk;

import java.util.Scanner;
import br.com.helpdesk.model.Chamado;
import br.com.helpdesk.model.Prioridade;
import br.com.helpdesk.repository.ChamadoRepository;
import br.com.helpdesk.service.ChamadoService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ChamadoService service = new ChamadoService();
        ChamadoRepository repository = new ChamadoRepository();

        Scanner scanner = new Scanner(System.in);

        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n=== HEL;PDESK SYSTEM ===");
            System.out.println("1 - Novo Chamado");
            System.out.println("2 - Listar Chamados");
            System.out.println("3 - Atender/Finalizar Chamado");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            String entrada = scanner.nextLine(); 
            
            try {
                // Tenta converter texto "1" para número 1
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                // Se o usuário digitar "banana", o programa não quebra, só avisa.
                System.out.println("Erro: Digite apenas números!");
                opcao = 0; // Força voltar ao menu
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Novo Chamado ---");

                    System.out.print("Título do problema: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Descrição detalhada: ");
                    String descricao = scanner.nextLine();

                    System.out.print("Nome do Solicitante: ");
                    String solicitante = scanner.nextLine();

                    Prioridade prioridade = Prioridade.MEDIA;

                    int novoId = repository.buscarTodos().size() + 1;

                    Chamado novoChamado = new Chamado(novoId, titulo, descricao, prioridade, solicitante);
                    repository.salvar(novoChamado);

                    System.out.println("Chamado criado com ID: " + novoId);
                    break;

                case 2:
                    System.out.println("\n--- LISTAGEM ---");
                    System.out.println("1 - Ver Todos");
                    System.out.println("2 - Ver Apenas Abertos");
                    System.out.print("Opção: ");

                    int tipoListagem = Integer.parseInt(scanner.nextLine());
                    List<Chamado> listaParaMostrar;

                    if (tipoListagem == 2) {
                        listaParaMostrar = repository.buscarAbertos();
                        System.out.println("Exibindo apenas chamados ABERTOS:");
                    } else {
                        listaParaMostrar = repository.buscarTodos();
                        System.out.println("Exibindo TODOS os chamados:");
                    }

                    if (listaParaMostrar.isEmpty()) {
                        System.out.println("Nenhum registro encontrado.");
                    } else {
                        for (Chamado c : listaParaMostrar) {
                            String destaque = (c.getPrioridade() == Prioridade.CRITICA) ? "*" : " ";

                            System.out.printf("%s #%d - %s [%s] (%s)\n",
                                destaque, c.getId(), c.getTitulo(), c.getStatus(), c.getPrioridade());
                        }
                    }
                    break;
                case 3: 
                    System.out.println("\n--- Atualizar Status do Chamado ---");

                    System.out.print("Digite o ID do chamado: ");
                    int idBusca = Integer.parseInt(scanner.nextLine());

                    Chamado chamadoEncontrado = repository.buscarPorId(idBusca);

                    if (chamadoEncontrado == null) {
                        System.out.println("ERRO: Chamado não encontrado!");
                        break;
                    }

                    System.out.println("Chamado selecionado: " + chamadoEncontrado.getTitulo());
                    System.out.println("Status atual: " + chamadoEncontrado.getStatus());

                    System.out.println("O que deseja fazer?");                    
                    System.out.println("1 - Iniciar Atendimento");                    
                    System.out.println("2 - Finalizar Chamado");
                    System.out.print("Opção: ");

                    int opcaoAcao = Integer.parseInt(scanner.nextLine());

                    if (opcaoAcao == 1) {
                        service.iniciarAtendimento(chamadoEncontrado);
                        repository.atualizarArquivo();
                    } else if (opcaoAcao == 2) {
                        service.finalizarChamado(chamadoEncontrado);
                        repository.atualizarArquivo();
                    } else {
                        System.out.println("Opção inválida.");
                    }
                    break;

                case 4:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");    
            }
        }

        System.out.println("Sistema encerrado.");
    }
}



















