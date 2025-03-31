package Model;

public class User {
    private int id;
    private String name;
    private int age;
    private double weight;
    private double height;
    private double imc;

    public User() {}

    public User(int id, String name, int age, double weight, double height) {
        this.id = id;
        this.name = name;
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

}
