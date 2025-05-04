    import javax.swing.*;
    import javax.swing.text.DefaultFormatterFactory;
    import javax.swing.text.MaskFormatter;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.io.Console;
    import java.text.ParseException;

    public class Screen extends JFrame{
        JTextField marca = new JTextField();

        JTextField modelo = new JTextField();
        JFormattedTextField ano = new JFormattedTextField();
        JTextField placa = new JTextField();

        JButton addBtn = new JButton();
        JButton rmvBtn = new JButton();

        int parkingSpace = 120;

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> itemsList = new JList<>(listModel);

        public Screen(){
            setTitle("Estacionamento");
            setVisible(true);
            setSize(800, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Para terminar de rodar ao fechar o UI
            setLocationRelativeTo(null); //Para abrir no centro da tela
            setLayout(null);
            setResizable(false);
            getContentPane().setBackground(new Color(0x404040));

            modelo.setBounds(10, 10, 200, 30);
            modelo.setFont(new Font("Arial", Font.ITALIC, 12));
            modelo.setForeground(new Color(0x00EEFF));
            modelo.setBackground(new Color(0x5E5E5E));

            add(modelo);
            modelo.requestFocus();

            marca.setBounds(220, 10, 200, 30);
            marca.setFont(new Font("Arial", Font.ITALIC, 12));
            marca.setForeground(new Color(0x00EEFF));
            marca.setBackground(new Color(0x5E5E5E));

            add(marca);
            marca.requestFocus();


            addBtn.setText("Adicionar");
            addBtn.setBounds(250, 100, 100, 40);
            addBtn.setFont(new Font("Arial", Font.PLAIN , 12));
            addBtn.setForeground(new Color(255, 255, 255));
            addBtn.setBackground(new Color(114, 165, 77));
            addBtn.addActionListener(this::addBtnAction);

            add(addBtn);
            addBtn.requestFocus();

            rmvBtn.setText("Remover");
            rmvBtn.setBounds(400, 100, 100, 40);
            rmvBtn.setFont(new Font("Arial", Font.PLAIN, 12));
            rmvBtn.setForeground(new Color(255, 255, 255));
            rmvBtn.setBackground(new Color(232, 108, 108));
            rmvBtn.addActionListener(this::removeBtnAction);

            add(rmvBtn);
            rmvBtn.requestFocus();

            itemsList.setBounds(10, 150, 770, 300);
            itemsList.setForeground(new Color(0x00FFAC));
            itemsList.setBackground(new Color(0x494949));

            add(itemsList);
            itemsList.requestFocus();

            placa.setBounds(10, 50, 200, 30);
            placa.setFont(new Font("Arial", Font.PLAIN, 12));
            placa.setForeground(new Color(0x00EEFF));
            placa.setBackground(new Color(0x5E5E5E));

            add(placa);
            placa.requestFocus();

            MaskFormatter formatter = new MaskFormatter();
            try {
                formatter.setMask("##/##/####");
            } catch (ParseException e){
                e.printStackTrace();
            }

            ano.setFormatterFactory(new DefaultFormatterFactory(formatter, null, null));

            ano.setBounds(430, 10, 200, 30);
            ano.setFont(new Font("Arial", Font.PLAIN, 12));
            ano.setForeground(new Color(0x00EEFF));
            ano.setBackground(new Color(0x5E5E5E));

            add(ano);
            ano.requestFocus();
        }

        private void removeBtnAction(ActionEvent actionEvent) {
            String holderModel = modelo.getText();
            String holderMarca = marca.getText();
            String holderAno = ano.getText();
            String holderPlaca = placa.getText();

            if(listModel.isEmpty()){
                JOptionPane.showMessageDialog(null, "Não há carros no estacionamento", "Não este veículo no estacionamento", JOptionPane.ERROR_MESSAGE);
            }else {
                System.out.println("debug");
                if(listModel.contains(holderModel + " - " + holderMarca + " - " + holderAno + " - " + holderPlaca)) {
                    System.out.println("debug2");
                    listModel.removeElement(holderModel + " - " + holderMarca + " - " + holderAno + " - " + holderPlaca);
                    if(parkingSpace < 120){
                        parkingSpace++;
                    }
                }else if(holderModel.isEmpty() || holderMarca.isEmpty() || holderAno.isEmpty() || holderPlaca.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Ainda há campos para serem preenchidos ou este veículo não existe", "ERRO", JOptionPane.ERROR_MESSAGE);
                }
            }
            System.out.println("botão remover foi clicado");
        }

        private void addBtnAction(ActionEvent actionEvent) {
            String holderModel = modelo.getText();
            String holderMarca = marca.getText();
            String holderAno = ano.getText();
            String holderPlaca = placa.getText();

            if (holderModel.isEmpty() || holderMarca.isEmpty() || holderAno.isEmpty() || holderPlaca.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < listModel.size(); i++) {
                String item = listModel.get(i);
                String placaExistente = item.split(" - ")[3]; // Assume que a placa é o quarto elemento separado por " - "
                if (placaExistente.equals(holderPlaca)) {
                    JOptionPane.showMessageDialog(null, "Já existe um veículo com a placa " + holderPlaca + ".", "Veículo Existente", JOptionPane.ERROR_MESSAGE);
                    return; //
                }
            }

            if (parkingSpace > 0) {
                listModel.addElement(holderModel + " - " + holderMarca + " - " + holderAno + " - " + holderPlaca);
                parkingSpace--;
                System.out.println("Vaga restante: " + parkingSpace);
            } else {
                JOptionPane.showMessageDialog(null, "O estacionamento está lotado.", "Lotado", JOptionPane.WARNING_MESSAGE);
            }


            System.out.println("botao adicionar foi clicado");
        }
    }
