package Model;

import javax.swing.*;

public class User {
    private int id;
    private String name;
    private String password;
    private int age;
    private double weight;
    private double height;
    private double imc;

    public User() {}

    public User(int id, String name, String password,int age, double weight, double height) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.imc = CalculateImc(); // calcula IMC automaticamente ao criar um usuário
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        this.imc = CalculateImc(); // atualiza o IMC sempre que o weight for alterado
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        this.imc = CalculateImc(); // atualiza o IMC sempre que a height for alterada
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    // calculo de IMC
    public double CalculateImc() {

        if (height > 0) {
            return weight / (height * height);
        } else {
            return 0;
        }
    }


    public class UserFrame extends JFrame {
        public UserFrame() {
            setTitle("Área do Usuário");
            setSize(300,200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            JLabel label = new JLabel("Seja Bem Vindo ao Zyn!", SwingConstants.CENTER);
            add(label);

            setVisible(true);
        }
    }
}
