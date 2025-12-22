package br.com.helpdesk.repository;

import java.util.ArrayList;
import java.util.List;
import br.com.helpdesk.model.Chamado;

public class ChamadoRepository {
    private List<Chamado> bancoDeDados = new ArrayList<>();

    // CREATE (Salvar)
    public void salvar(Chamado chamado) {
        this.bancoDeDados.add(chamado);
        System.out.println("LOG: Chamado " + chamado.getId() + " salvo no banco.");
    }

    // READ (Listar Tudo)
    public List<Chamado> buscarTodos() {
        return this.bancoDeDados;
    }

    // read (bUSCAR eSPECÍFICO)
    // aqui usamos o "Stream" do Java 8+ para filtrar (igual um SELECT WHERE do SQL)
    public Chamado buscarPorId(int id) {
        for (Chamado c : this.bancoDeDados) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null; // Se não achar, retorna nulo
    }
}