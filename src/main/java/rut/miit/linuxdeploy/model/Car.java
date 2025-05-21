package rut.miit.linuxdeploy.model;

import rut.miit.linuxdeploy.exception.ParameterException;

import java.time.Year;

public class Car extends BaseEntity{
    private String name;
    private String model;
    private String color;
    private Integer year;

    public Car( String name, String model, String color, Integer year) {
        setName(name);
        setModel(model);
        setColor(color);
        setYear(year);
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

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ParameterException("Car name must not be null or empty");
        }
        this.name = name;
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new ParameterException("Car model must not be null or empty");
        }
        this.model = model;
    }

    public void setColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new ParameterException("Car color must not be null or empty");
        }
        this.color = color;
    }

    public void setYear(Integer year) {
        if (year == null) {
            throw new ParameterException("Year must not be null");
        }
        int currentYear = Year.now().getValue();
        if (year < 1886 || year > currentYear) {
            throw new ParameterException("Year must be between 1886 and " + currentYear);
        }
        this.year = year;
    }
}

