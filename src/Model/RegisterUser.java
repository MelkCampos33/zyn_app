package Model;

import DAO.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterUser extends JFrame {
    private JTextField txtUsername, txtWeight, txtHeight;
    private JTextField txtAge;
    private JPasswordField txtPassword;
    private JButton btnRegister;

    public RegisterUser() {
        setTitle("Cadastro de Usuário na Zyn");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsername = new JLabel("Usuário:");
        txtUsername = new JTextField(15);

        JLabel lblAge = new JLabel("Idade:");
        txtAge = new JTextField(15);

        JLabel lblPassword = new JLabel("Senha:");
        txtPassword = new JPasswordField(15);

        JLabel lblWeight = new JLabel("Peso (kg):");
        txtWeight = new JTextField(5);

        JLabel lblHeight = new JLabel("Altura (m):");
        txtHeight = new JTextField(5);

        btnRegister = new JButton("Cadastrar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblUsername, gbc);
        gbc.gridx = 1;
        add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblAge, gbc);
        gbc.gridx = 1;
        add(txtAge, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblPassword, gbc);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblWeight, gbc);
        gbc.gridx = 1;
        add(txtWeight, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblHeight, gbc);
        gbc.gridx = 1;
        add(txtHeight, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnRegister, gbc);

        btnRegister.addActionListener(this::registerUser);

        setVisible(true);
    }

    private void registerUser(ActionEvent e) {
        String username = txtUsername.getText();
        String age = txtAge.getText();
        String password = new String(txtPassword.getPassword());
        String weightText = txtWeight.getText();
        String heightText = txtHeight.getText();
        String role = "usuário"; // Definir função fixa

        // Validações
        if (username.isEmpty() || password.isEmpty() || weightText.isEmpty() || heightText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() > 8) {
            JOptionPane.showMessageDialog(this, "A senha deve ter no máximo 8 caracteres!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double weight = Double.parseDouble(weightText);
            double height = Double.parseDouble(heightText);

            if (height <= 0 || weight <= 0) {
                JOptionPane.showMessageDialog(this, "Peso e altura devem ser valores positivos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calcula IMC
            double imc = weight / (height * height);

            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO db (name, password, role, weight, height, imc) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.setDouble(4, weight);
                stmt.setDouble(5, height);
                stmt.setDouble(6, imc);


                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso! \nIMC Calculado: " + String.format("%.2f", imc));
                    dispose();
                    new Login();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso e altura devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
