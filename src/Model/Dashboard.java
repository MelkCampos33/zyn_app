package Model;

import DAO.DatabaseConnection;
import DAO.UserDatabase;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JFrame {
    private JPanel mainPanel;
    private JTable table;
    private DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;
    private String username;

    public Dashboard(String username) {
        this.username = username;
        setTitle("Bem-vindo, " + username);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal rolável
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Mensagem de boas-vindas
        JLabel lblMessage = new JLabel("Olá, " + username + "! Bem-vindo ao sistema.", SwingConstants.CENTER);
        lblMessage.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblMessage);

        // Tabela de progresso
        mainPanel.add(createUserTable());

        // Gráfico interativo (atualizado com dados do banco)
        chartPanel = new ChartPanel(createIMCGraph());
        mainPanel.add(chartPanel);

        // Botões com conteúdos especializados
        mainPanel.add(createContentButtons());

        // Alertas e Notificações
        JLabel alertLabel = new JLabel("Alerta: Mantenha o foco na sua alimentação!", SwingConstants.CENTER);
        alertLabel.setForeground(Color.RED);
        mainPanel.add(alertLabel);

        setVisible(true);
    }

    /**
     * Cria a tabela do usuário com os dados do banco de dados
     */
    private JScrollPane createUserTable() {
        String[] columns = {"Data", "Peso", "IMC", "Progresso"};
        Object[][] data = fetchUserData();

        table = new JTable(data, columns);
        return new JScrollPane(table);
    }

    /**
     * Busca os dados do usuário no banco de dados
     */
    private Object[][] fetchUserData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT weight, height, imc, DATE_FORMAT(created_at, '%d/%m/%Y') AS date FROM db WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            // Contar o número de linhas manualmente
            List<Object[]> tempData = new ArrayList<>();
            while (rs.next()) {
                double peso = rs.getDouble("weight");
                double imc = rs.getDouble("imc");
                String dataRegistro = rs.getString("date");

                String progresso = (imc < 25) ? "Saudável" : "Precisa melhorar";

                tempData.add(new Object[]{dataRegistro, peso, imc, progresso});
            }

            // Converter a lista para um array
            Object[][] data = tempData.toArray(new Object[0][0]);
            return data;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Object[][]{};
        }
    }

    /**
     * Cria um gráfico de progresso do IMC baseado nos dados do banco de dados
     */
    private JFreeChart createIMCGraph() {
        dataset = new DefaultCategoryDataset();
        loadIMCDataFromDB();

        return ChartFactory.createLineChart(
                "Evolução do IMC",   // Título
                "Data",              // Eixo X
                "IMC",               // Eixo Y
                dataset              // Dados
        );
    }

    /**
     * Carrega os dados de IMC do banco de dados e adiciona ao gráfico
     */
    private void loadIMCDataFromDB() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT imc, DATE_FORMAT(created_at, '%d/%m/%Y') AS date FROM db WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double imc = rs.getDouble("imc");
                String dataRegistro = rs.getString("date");
                dataset.addValue(imc, "IMC", dataRegistro);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cria os botões para acessar conteúdos de especialistas
     */
    private JPanel createContentButtons() {
        JButton btnNutrição = new JButton("Dicas de Nutrição");
        JButton btnBemEstar = new JButton("Dicas de Bem-Estar");
        JButton btnSaude = new JButton("Dicas de Saúde");

        btnNutrição.addActionListener(e -> JOptionPane.showMessageDialog(null, "Aqui estão as dicas de nutrição..."));
        btnBemEstar.addActionListener(e -> JOptionPane.showMessageDialog(null, "Aqui estão as dicas de bem-estar..."));
        btnSaude.addActionListener(e -> JOptionPane.showMessageDialog(null, "Aqui estão as dicas de saúde..."));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnNutrição);
        buttonPanel.add(btnBemEstar);
        buttonPanel.add(btnSaude);

        return buttonPanel;
    }

    public static void main(String[] args) {
        new Dashboard("João");
    }
}
