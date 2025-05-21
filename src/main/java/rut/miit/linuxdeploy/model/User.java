package rut.miit.linuxdeploy.model;

import rut.miit.linuxdeploy.exception.ParameterException;

public class User extends BaseEntity {
    private String name;
    private String email;
    private String phone;
    private Integer age;

    public User(String name, String email, String phone, Integer age) {
        setName(name);
        setEmail(email);
        setPhone(phone);
        setAge(age);
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

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ParameterException("Name must not be null or empty");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new ParameterException("Invalid email format");
        }
        this.email = email;
    }

    public void setPhone(String phone) {
        if (phone == null || !phone.matches("^\\+?[0-9]{10,15}$")) {
            throw new ParameterException("Invalid phone number");
        }
        this.phone = phone;
    }

    public void setAge(Integer age) {
        if (age == null) {
            throw new ParameterException("Age must not be null");
        }
        if (age < 14 || age > 80) {
            throw new ParameterException("Age must be between 14 and 80");
        }
        this.age = age;
    }
}
