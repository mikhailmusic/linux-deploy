package rut.miit.linuxdeploy.dto;

public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Integer age;

    public UserDto(Integer id, String name, String email, String phone, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
    }

    public Integer getId() {
        return id;
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
}
