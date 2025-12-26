package br.com.helpdesk.repository;

import java.io.BufferedReader; // <--- NOVO (Lê texto)
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;     // <--- NOVO (Lê arquivo)
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.helpdesk.model.Chamado;
import br.com.helpdesk.model.Prioridade; // <--- Precisamos importar os Enums para converter
import br.com.helpdesk.model.Status;

public class ChamadoRepository {
    
    private static final String ARQUIVO_DB = "banco_de_dados.txt";
    private List<Chamado> bancoDeDados = new ArrayList<>();

    // O Construtor é executado assim que damos "new ChamadoRepository()"
    public ChamadoRepository() {
        System.out.println("LOG: Inicializando repositório...");
        carregarDados(); // <--- A MÁGICA: Já nasce carregando o arquivo
    }

    public void salvar(Chamado chamado) {
        this.bancoDeDados.add(chamado);
        salvarNoDisco();
        // Não precisa imprimir log aqui se não quiser poluir a tela
    }

    public List<Chamado> buscarTodos() {
        return this.bancoDeDados;
    }

    public Chamado buscarPorId(int id) {
        for (Chamado c : this.bancoDeDados) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    
    // Chamamos este método quando atualizamos um status
    public void atualizarArquivo() {
        salvarNoDisco();
    }

    // --- MÉTODOS PRIVADOS (A Cozinha do Restaurante) ---

    private void salvarNoDisco() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_DB))) {
            for (Chamado c : this.bancoDeDados) {
                writer.write(c.paraLinhaCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // NOVO MÉTODO: Lê o arquivo e recria os objetos
    private void carregarDados() {
        File arquivo = new File(ARQUIVO_DB);
        
        // Se o arquivo não existe (primeira vez que roda), não faz nada
        if (!arquivo.exists()) {
            return; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            
            // Enquanto tiver linhas no arquivo, leia...
            while ((linha = reader.readLine()) != null) {
                // Quebra a linha onde tem ";"
                // Ex: "1;Mouse;Quebrado;ALTA;Thiago;ABERTO" vira um array com 6 posições
                String[] partes = linha.split(";");

                // Convertendo de volta (Parsing)
                int id = Integer.parseInt(partes[0]);
                String titulo = partes[1];
                String descricao = partes[2];
                Prioridade prioridade = Prioridade.valueOf(partes[3]); // Converte texto "ALTA" para Enum ALTA
                String solicitante = partes[4];
                Status statusRecuperado = Status.valueOf(partes[5]);   // Converte texto "FECHADO" para Enum FECHADO

                // Recriando o objeto
                Chamado c = new Chamado(id, titulo, descricao, prioridade, solicitante);
                
                // IMPORTANTE: O construtor define status como ABERTO por padrão.
                // Precisamos forçar o status que lemos do arquivo.
                c.setStatus(statusRecuperado);

                // Adiciona na memória
                this.bancoDeDados.add(c);
            }
            System.out.println("LOG: " + this.bancoDeDados.size() + " chamados carregados do disco.");
            
        } catch (IOException e) {
            System.out.println("ERRO ao carregar dados: " + e.getMessage());
        }
    }
}