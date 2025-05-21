package rut.miit.linuxdeploy.dto;

public class CarDto {
    private Integer id;
    private String name;
    private String model;
    private String color;
    private Integer year;

    public CarDto(Integer id, String name, String model, String color, Integer year) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public Integer getYear() {
        return year;
    }
}
