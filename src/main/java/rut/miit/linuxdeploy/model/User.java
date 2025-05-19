package rut.miit.linuxdeploy.model;


public class User extends BaseEntity {
    private String name;
    private String email;
    private String phone;
    private int age;

    public User(String name, String email, String phone, int age) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }
}
