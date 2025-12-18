package br.com.helpdesk.model;
import java.time.LocalDateTime;

public class Chamado {
    // Atributos (Encapsulamento: private para proteger os dados)
    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataAbertura;
    private Prioridade prioridade;
    private Status status;
    private String solicitante;

    // Construtor
    public Chamado(int id, String titulo, String descricao, Prioridade prioridade, String solicitante) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.solicitante = solicitante;

        // Valores automáticos (o usuário não define isso)
        this.dataAbertura = LocalDateTime.now(); // Pega a hora exata do sistema
        this.status = Status.ABERTO; // Todo chamado nasce ABERTO
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    // Método para exibir os dados 
    public void exibirDetalhes() {
        System.out.println("---DETALHES DO CHAMADO---");
        System.out.println("ID: " + this.id);
        System.out.println("Título: " + this.titulo);
        System.out.println("Solicitante " + this.solicitante);
        System.out.println("Prioridade: " + this.prioridade);
        System.out.println("Status: " + this.status);
        System.out.println("Data Abertura: " + this.dataAbertura);
        System.out.println("---------------------------");
    }
} 