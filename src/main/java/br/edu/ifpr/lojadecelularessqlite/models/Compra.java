package br.edu.ifpr.lojadecelularessqlite.models;

public class Compra {
    private int id;
    private int idAparelho;
    private String cliente;
    private int quantidade;
    private String data;

    public Compra() {}

    public Compra(int id, int idAparelho, String cliente, int quantidade, String data) {
        this.id = id;
        this.idAparelho = idAparelho;
        this.cliente = cliente;
        this.quantidade = quantidade;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAparelho() {
        return idAparelho;
    }

    public void setIdAparelho(int idAparelho) {
        this.idAparelho = idAparelho;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente + " | Qtd: " + quantidade + " | Data: " + data;
    }
}