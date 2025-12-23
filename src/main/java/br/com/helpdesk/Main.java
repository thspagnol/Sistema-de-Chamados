package br.com.helpdesk;

import java.util.Scanner;
import br.com.helpdesk.model.Chamado;
import br.com.helpdesk.model.Prioridade;
import br.com.helpdesk.repository.ChamadoRepository;
import br.com.helpdesk.service.ChamadoService;

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
        }
    }
}



















