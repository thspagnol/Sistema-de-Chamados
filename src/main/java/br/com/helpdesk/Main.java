package br.com.helpdesk;

import br.com.helpdesk.model.Chamado;
import br.com.helpdesk.model.Prioridade;
import br.com.helpdesk.repository.ChamadoRepository;
import br.com.helpdesk.service.ChamadoService;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciando o Service(O "Funcionário" que vai trabalhar)
        ChamadoService service = new ChamadoService();
        ChamadoRepository repository = new ChamadoRepository();

        // 2. Criando VÁRIOS chamados
        Chamado c1 = new Chamado(1, "Mouse quebrado", "Botão direito não clica", Prioridade.BAIXA, "João");
        Chamado c2 = new Chamado(2, "Sistema fora do ar", "Erro 500 no servidor", Prioridade.CRITICA, "Maria");
        Chamado c3 = new Chamado(3, "Solicitação de Acesso", "Preciso de acesso à pasta X", Prioridade.MEDIA, "Ana");

        // 3. Salvando no "Banco" (Repository)
        repository.salvar(c1);
        repository.salvar(c2);
        repository.salvar(c3);

        System.out.println("--- LISTANDO TODOS OS CHAMADOS ---");
        // o "For Each" do Java. Para cada 'c' dentro da lista que o repository retornou...
        for  (Chamado c : repository.buscarTodos()) {
            // Imprimir apenas resumo
            System.out.println(c.getId() + " - " + c.getTitulo() + " [" + c.getStatus() + "]");
        }

        // 4. Testando a lógica de negócio num chamado específico
        System.out.println("\n--- ATENDENDO O CHAMADO CRÍTICO ---");
        Chamado critico = repository.buscarPorId(2); // Buscando o chamado da Maria

        if (critico != null) {
            service.iniciarAtendimento(critico);
            service.finalizarChamado(critico);
        }

        System.out.println("\n--- STATUS ATUALIZADO ---");
        // Mostra a lista de novo para provar que o status da Maria mudou
        for (Chamado c : repository.buscarTodos()) {
            System.out.println(c.getId() + " - " + c.getTitulo() + " [" + c.getStatus() + "]");
        }
    }
}