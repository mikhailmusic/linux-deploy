package rut.miit.linuxdeploy.repository;

import org.springframework.stereotype.Repository;
import rut.miit.linuxdeploy.model.Car;

import java.util.*;

@Repository
public class CarRepository {
    private final Map<Integer, Car> cars = new HashMap<>();
    private static int idCounter = 1;

    public List<Car> findAll() {
        return new ArrayList<>(cars.values());
    }

    public Optional<Car> findById(Integer id) {
        return Optional.ofNullable(cars.get(id));
    }

    public Car save(Car car) {
        if (car.getId() == null) {
            car.setId(idCounter);
            cars.put(idCounter, car);
            idCounter++;
        } else {
            cars.put(car.getId(), car);
        }
        return car;
    }

    public void deleteById(Integer id) {
        cars.remove(id);
    }
}
