import Model.Login;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        JLabel title = label("Seja bem vindo ao Zyn!");
        Login window = new Login();
        window.show();

    }

    private static JLabel label(String text) {
        JLabel labelView = new JLabel(text);
        labelView.setHorizontalAlignment(SwingConstants.CENTER);
        labelView.setFont(new Font("Tahamo", Font.BOLD, 17));
        labelView.setForeground(Color.black);

        return labelView;
    }
}