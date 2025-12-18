package br.com.helpdesk.service;

import br.com.helpdesk.model.Chamado;
import br.com.helpdesk.model.Status;

public class ChamadoService {

    //Regra de Negócio: Encerrar um chamado
    public void iniciarAtendimento(Chamado chamado) {
        if (chamado.getStatus() == Status.ABERTO) {
            chamado.setStatus(Status.EM_ANDAMENTO);
            System.out.println(">>> Chamado iniciado com sucesso!");
        } else {
            System.out.println("Erro: Só é possível iniciar chamados que estejam abertos.");
        }
    }

    // Método para finalizar
    public void finalizarChamado(Chamado chamado) {
        chamado.setStatus(Status.FECHADO);
        System.out.println(">>> Chamado finalizado.");
    }
}