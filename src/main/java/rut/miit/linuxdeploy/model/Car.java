package rut.miit.linuxdeploy.model;

public class Car extends BaseEntity{
    public String name;
    public String model;
    public String color;
    public int year;

    public Car( String name, String model, String color, int year) {
        this.name = name;
        this.model = model;
        this.color = color;
        this.year = year;
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

    public int getYear() {
        return year;
    }
}

