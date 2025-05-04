package entity;

public class Vehicle {
    private String modelo;
    private String marca;
    private String ano;
    private String placa;
    private String horaEntrada;
    private String horaSaida;

    public Vehicle() {}

    public Vehicle(String modelo, String marca, String ano, String placa) {
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.placa = placa;
    }

    public Vehicle(String modelo, String marca, String ano, String placa, String horaEntrada) {
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.placa = placa;
        this.horaEntrada = horaEntrada;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }
}