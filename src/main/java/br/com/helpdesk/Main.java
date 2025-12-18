package br.com.helpdesk;

import br.com.helpdesk.model.Chamado;
import br.com.helpdesk.model.Prioridade;
import br.com.helpdesk.service.ChamadoService;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciando o Service(O "Funcionário" que vai trabalhar)
        ChamadoService service = new ChamadoService();

        // 2. Criando o Chamado
        Chamado chamado01 = new Chamado(
            1,
            "Erro no Login",
            "Senha inválida repetidamente",
            Prioridade.ALTA,
            "Thiago Spagnol"
        );

        // 3 .Fluxo de Vida
        chamado01.exibirDetalhes();

        System.out.println("\n--- TENTANDO INICIAR ATENDIMENTO ---");
        service.iniciarAtendimento(chamado01);

        chamado01.exibirDetalhes();

        System.out.println("\n--- TENTANDO FINALIZAR ---");
        service.finalizarChamado(chamado01);

        System.out.println("Status Final: " + chamado01.getStatus());
    }
}