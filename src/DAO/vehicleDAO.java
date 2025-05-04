package DAO;

import entity.Vehicle;
import mySQLConecction.Connector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class vehicleDAO {

    public void vehicleSingUp(Vehicle vehicle) {
        String sql = "INSERT INTO VEHICLE (MODELO, MARCA, ANO, PLACA, hora_entrada) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = Connector.getConnector().prepareStatement(sql);
            ps.setString(1, vehicle.getMarca());
            ps.setString(2, vehicle.getModelo());
            ps.setString(3, vehicle.getAno());
            ps.setString(4, vehicle.getPlaca());
            ps.setString(5, vehicle.getHoraEntrada()); // Adicionando hora de entrada
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void vehicleRemove(Vehicle vehicle) {
        String sql = "DELETE FROM VEHICLE WHERE PLACA =?";
        PreparedStatement ps = null;
        try {
            ps = Connector.getConnector().prepareStatement(sql);
            ps.setString(1, vehicle.getPlaca());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateVehicleExitTime(Vehicle vehicle) {
        String sql = "UPDATE VEHICLE SET hora_saida = ? WHERE PLACA = ?";
        PreparedStatement ps = null;
        try {
            ps = Connector.getConnector().prepareStatement(sql);
            ps.setString(1, vehicle.getHoraSaida());
            ps.setString(2, vehicle.getPlaca());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Vehicle> listAllVehicle(){
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM VEHICLE";
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = Connector.getConnector().prepareStatement(sql);
            result = ps.executeQuery();
            while(result.next()) {
                String modelo = result.getString("modelo");
                String marca = result.getString("marca");
                String ano = result.getString("ano");
                String placa = result.getString("placa");
                String horaEntrada = result.getString("hora_entrada");
                String horaSaida = result.getString("hora_saida"); // Carregando hora de saída

                Vehicle v = new Vehicle(modelo, marca, ano, placa, horaEntrada);
                v.setHoraSaida(horaSaida); // Definindo a hora de saída no objeto

                vehicles.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (result != null) result.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return vehicles;
    }
}