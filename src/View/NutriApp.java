package View;
import DAO.UserDatabase;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class NutriApp {

    private JFrame frame;
    private JTable table;
    private final UserDatabase userdatabase;

    public NutriApp() {
        userdatabase = new UserDatabase();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("ZYN - Gerenciamento de Saúde");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // user table
        table = new JTable();
        frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        // button
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        // adicionar usuario
        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String name = JOptionPane.showInputDialog(frame, "Digite o nome do usuário: ");
                String ageInput = JOptionPane.showInputDialog(frame, "Digite a idade do usuário: ");

                if (name != null && ageInput != null && !name.isEmpty() && !ageInput.isEmpty()) {
                    try {
                        int age = Integer.parseInt(ageInput);
                        if (age <= 0) {
                            JOptionPane.showMessageDialog(frame, "Idade deve ser um número positivo.");
                            return;
                        }
                        User newUser = new User(name, age);
                        userdatabase.addUser(newUser);
                        loadTable();
                    } catch (NumberFormatException | SQLException e) {
                        JOptionPane.showMessageDialog(frame, "Idade inválida. Por favor, insira um número inteiro.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Os campos não podem estar vazios.");
                }
            }
        });

        // deletar usuario
        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String userName = table.getValueAt(selectedRow, 0).toString();
                    int confirmedDelete = JOptionPane.showConfirmDialog(frame,
                            "Tem certeza que deseja excluir o usuário: " + userName + "?",
                            "Confirmação", JOptionPane.YES_OPTION);

                    if (confirmedDelete == JOptionPane.YES_OPTION) {
                        if (userdatabase.removeUser(userName)) {
                            loadTable();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Falha ao excluir o usuário.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione um usuário para excluir.");
                }
            }
        });

        panel.add(addButton);
        panel.add(deleteButton);
        loadTable();
    }

    private void loadTable() {
        List<User> users = userdatabase.listUsers();
        String[] col = {"Nome", "Idade"};
        Object[][] dados = new Object[users.size()][2];
        for (int i = 0; i < users.size(); i++) {
            User usuario = users.get(i);
            dados[i][0] = usuario.getName();
            dados[i][1] = usuario.getAge();
        }
        table.setModel(new javax.swing.table.DefaultTableModel(dados, col));
    }

    public void show() {
        frame.setVisible(true);
    }
}