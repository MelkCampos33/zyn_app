package Model;

import DAO.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;

    public Login() {
        setTitle("Login - NutriApp");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Janela no centro da tela

        // Painel principal com layout centralizado
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os elementos
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel lblUsername = new JLabel("Usuário:");
        txtUsername = new JTextField(15);

        JLabel lblPassword = new JLabel("Senha:");
        txtPassword = new JPasswordField(15);

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Cadastrar");

        // Configuração do layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsername, gbc);

        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);

        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        gbc.gridy = 3;
        panel.add(btnRegister, gbc);
        add(panel);

        // Eventos
        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> new RegisterUser()); // Abre a tela de cadastro

        setVisible(true);
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT role FROM db WHERE name = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(this, "Login bem-sucedido como " + role + "!");
                this.dispose();
                new Dashboard(username);

            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
