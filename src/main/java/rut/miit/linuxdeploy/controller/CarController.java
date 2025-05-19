package rut.miit.linuxdeploy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.linuxdeploy.model.Car;
import rut.miit.linuxdeploy.repository.CarRepository;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository repository;

    public CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Car> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody Car car) {
        if (car.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Car savedCar = repository.save(car);
        return ResponseEntity.ok(savedCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Car car) {
        if (!repository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        car.setId(id);
        repository.save(car);
        return ResponseEntity.ok("Car updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (!repository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Car deleted");
    }
}
