package br.com.helpdesk.repository;

// Adicione estes imports junto com os do com.google.gson...
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonElement;
import java.time.LocalDateTime; // Já deve ter, mas confira
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Não esqueça deste import para o filtro!

// IMPORTANDO A NOVA BIBLIOTECA
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken; // Importante para ler Listas
import java.lang.reflect.Type;

import br.com.helpdesk.model.Chamado;
import br.com.helpdesk.model.Status;

public class ChamadoRepository {
    
    private static final String ARQUIVO_DB = "banco_de_dados.json"; // Mudamos a extensão!
    private List<Chamado> bancoDeDados = new ArrayList<>();
    
    // O Objeto Gson que sabe trabalhar com JSON
    // setPrettyPrinting deixa o arquivo bonitinho de ler
    // Configurando o Gson com adaptadores para LocalDateTime
    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, type, context) -> 
            new JsonPrimitive(src.toString())) // Na hora de SALVAR, vira Texto
        .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) -> 
            LocalDateTime.parse(json.getAsString())) // Na hora de LER, vira Data
        .create();

    public ChamadoRepository() {
        carregarDados();
    }

    public void salvar(Chamado chamado) {
        this.bancoDeDados.add(chamado);
        salvarNoDisco();
        // Log removido para limpar a tela
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
    
    // Filtro (Mantivemos igual)
    public List<Chamado> buscarAbertos() {
        return this.bancoDeDados.stream()
            .filter(c -> c.getStatus() == Status.ABERTO)
            .collect(Collectors.toList());
    }

    public void atualizarArquivo() {
        salvarNoDisco();
    }

    // --- MÉTODOS PRIVADOS MODERNOS ---

    private void salvarNoDisco() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_DB))) {
            // A MÁGICA: O Gson pega a lista inteira e transforma em texto JSON
            String json = gson.toJson(this.bancoDeDados);
            
            writer.write(json);
            
        } catch (IOException e) {
            System.out.println("ERRO CRÍTICO: " + e.getMessage());
        }
    }

    private void carregarDados() {
        File arquivo = new File(ARQUIVO_DB);
        
        // Se o arquivo não existe, mantém a lista vazia criada lá em cima e encerra.
        if (!arquivo.exists()) {
            return; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            Type tipoLista = new TypeToken<ArrayList<Chamado>>(){}.getType();
            
            // Lê do arquivo
            List<Chamado> dadosLidos = gson.fromJson(reader, tipoLista);
            
            // CORREÇÃO DE SEGURANÇA:
            // Se o arquivo estiver vazio, o Gson retorna null.
            // Se for null, nós ignoramos e deixamos a lista vazia (new ArrayList).
            // Se não for null, usamos os dados lidos.
            if (dadosLidos != null) {
                this.bancoDeDados = dadosLidos;
            }
            
            System.out.println("LOG: Dados carregados via JSON.");
            
        } catch (IOException e) {
            System.out.println("ERRO ao carregar JSON: " + e.getMessage());
        }
    }
}