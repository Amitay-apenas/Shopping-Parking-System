import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.Collection;

import entity.Vehicle;
import DAO.vehicleDAO;

public class Screen extends JFrame {
    JLabel marcaLabel = new JLabel("Marca");
    JTextField marca = new JTextField();

    JLabel modeloLabel = new JLabel("Modelo");
    JTextField modelo = new JTextField();

    JLabel anoLabel = new JLabel("Ano");
    JFormattedTextField ano = new JFormattedTextField();

    JLabel placaLabel = new JLabel("Placa");
    JTextField placa = new JTextField();

    JLabel horaEntradaLabel = new JLabel("Hora de Entrada (HH:MM)");
    JFormattedTextField horaEntrada = new JFormattedTextField();

    JButton addBtn = new JButton("Adicionar");
    JButton rmvBtn = new JButton("Remover");

    int parkingSpace = 120;
    JLabel pSpace = new JLabel("Vagas disponiveis " + parkingSpace);

    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> itemsList = new JList<>(listModel);

    vehicleDAO vDAO = new vehicleDAO();

    public Screen() {
        setTitle("Estacionamento");
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(0x404040));

        // Inicialização dos componentes
        modeloLabel.setBounds(10, 9, 200, 10);
        modeloLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        modeloLabel.setForeground(new Color(0x00EEFF));
        add(modeloLabel);
        modelo.setBounds(10, 20, 200, 30);
        modelo.setFont(new Font("Arial", Font.ITALIC, 12));
        modelo.setForeground(new Color(0x00EEFF));
        modelo.setBackground(new Color(0x5E5E5E));
        add(modelo);

        marcaLabel.setBounds(220, 9, 200, 10);
        marcaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        marcaLabel.setForeground(new Color(0x00EEFF));
        add(marcaLabel);
        marca.setBounds(220, 20, 200, 30);
        marca.setFont(new Font("Arial", Font.ITALIC, 12));
        marca.setForeground(new Color(0x00EEFF));
        marca.setBackground(new Color(0x5E5E5E));
        add(marca);

        anoLabel.setBounds(430, 9, 200, 10);
        anoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        anoLabel.setForeground(new Color(0x00EEFF));
        add(anoLabel);
        MaskFormatter anoFormatter = null;
        try {
            anoFormatter = new MaskFormatter("####");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ano.setFormatterFactory(new DefaultFormatterFactory(anoFormatter));
        ano.setBounds(430, 20, 200, 30);
        ano.setFont(new Font("Arial", Font.PLAIN, 12));
        ano.setForeground(new Color(0x00EEFF));
        ano.setBackground(new Color(0x5E5E5E));
        add(ano);

        placaLabel.setBounds(10, 60, 200, 10);
        placaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        placaLabel.setForeground(new Color(0x00EEFF));
        add(placaLabel);
        placa.setBounds(10, 70, 200, 30);
        placa.setFont(new Font("Arial", Font.PLAIN, 12));
        placa.setForeground(new Color(0x00EEFF));
        placa.setBackground(new Color(0x5E5E5E));
        add(placa);

        horaEntradaLabel.setBounds(220, 60, 200, 10);
        horaEntradaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        horaEntradaLabel.setForeground(new Color(0x00EEFF));
        add(horaEntradaLabel);
        MaskFormatter horaFormatter = null;
        try {
            horaFormatter = new MaskFormatter("##:##");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        horaEntrada.setFormatterFactory(new DefaultFormatterFactory(horaFormatter));
        horaEntrada.setBounds(220, 70, 200, 30);
        horaEntrada.setFont(new Font("Arial", Font.PLAIN, 12));
        horaEntrada.setForeground(new Color(0x00EEFF));
        horaEntrada.setBackground(new Color(0x5E5E5E));
        add(horaEntrada);

        pSpace.setBounds(430, 70, 200, 30);
        pSpace.setForeground(new Color(0x00EEFF));
        add(pSpace);

        addBtn.setBounds(250, 120, 100, 40);
        addBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        addBtn.setForeground(new Color(255, 255, 255));
        addBtn.setBackground(new Color(114, 165, 77));
        addBtn.addActionListener(this::addBtnAction);
        add(addBtn);

        rmvBtn.setBounds(400, 120, 100, 40);
        rmvBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        rmvBtn.setForeground(new Color(255, 255, 255));
        rmvBtn.setBackground(new Color(232, 108, 108));
        rmvBtn.addActionListener(this::removeBtnAction);
        add(rmvBtn);

        itemsList.setBounds(10, 170, 770, 380);
        itemsList.setForeground(new Color(0x00FFAC));
        itemsList.setBackground(new Color(0x494949));
        add(itemsList);

        // Carrega os veículos do banco de dados ao iniciar
        loadVehiclesFromDatabase();
    }

    private void loadVehiclesFromDatabase() {
        Collection<Vehicle> vehicles = vDAO.listAllVehicle();
        listModel.clear(); // Limpa a lista antes de carregar
        for (Vehicle vehicle : vehicles) {
            listModel.addElement(vehicle.getModelo() + " - " + vehicle.getMarca() + " - " + vehicle.getAno() + " - " + vehicle.getPlaca() + " - " + vehicle.getHoraEntrada());
        }
        parkingSpace = 120 - listModel.size();
        pSpace.setText("Vagas disponiveis " + parkingSpace);
    }

    private void removeBtnAction(ActionEvent actionEvent) {
        String selectedItem = itemsList.getSelectedValue();
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(null, "Selecione um veículo para remover.", "Nenhum veículo selecionado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] parts = selectedItem.split(" - ");
        if (parts.length >= 4) {
            String placaSelecionada = parts[3];
            int selectedIndex = itemsList.getSelectedIndex();
            Vehicle vehicleToRemove = new Vehicle();
            vehicleToRemove.setPlaca(placaSelecionada);
            vDAO.vehicleRemove(vehicleToRemove);
            listModel.remove(selectedIndex);
            parkingSpace++;
            pSpace.setText("Vagas disponiveis " + parkingSpace);
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao processar a seleção.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("botão remover foi clicado");
    }

    private void addBtnAction(ActionEvent actionEvent) {
        String holderModel = modelo.getText();
        String holderMarca = marca.getText();
        String holderAno = ano.getText();
        String holderPlaca = placa.getText();
        String holderHoraEntrada = horaEntrada.getText();

        if (holderModel.isEmpty() || holderMarca.isEmpty() || holderAno.isEmpty() || holderPlaca.isEmpty() || holderHoraEntrada.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < listModel.size(); i++) {
            String item = listModel.get(i);
            String[] parts = item.split(" - ");
            if (parts.length >= 4 && parts[3].equals(holderPlaca)) {
                JOptionPane.showMessageDialog(null, "Já existe um veículo com a placa " + holderPlaca + ".", "Veículo Existente", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (parkingSpace > 0) {
            Vehicle newVehicle = new Vehicle();
            newVehicle.setMarca(holderMarca);
            newVehicle.setModelo(holderModel);
            newVehicle.setAno(holderAno);
            newVehicle.setPlaca(holderPlaca);
            newVehicle.setHoraEntrada(holderHoraEntrada);

            vDAO.vehicleSingUp(newVehicle);

            listModel.addElement(newVehicle.getModelo() + " - " + newVehicle.getMarca() + " - " + newVehicle.getAno() + " - " + newVehicle.getPlaca() + " - " + newVehicle.getHoraEntrada());
            parkingSpace--;
            pSpace.setText("Vagas disponiveis " + parkingSpace);

            // Limpa os campos de entrada após adicionar
            modelo.setText("");
            marca.setText("");
            ano.setText("");
            placa.setText("");
            horaEntrada.setText("");

            System.out.println("Vaga restante: " + parkingSpace);
        } else {
            JOptionPane.showMessageDialog(null, "O estacionamento está lotado.", "Lotado", JOptionPane.WARNING_MESSAGE);
        }

        System.out.println("botao adicionar foi clicado");
    }
}