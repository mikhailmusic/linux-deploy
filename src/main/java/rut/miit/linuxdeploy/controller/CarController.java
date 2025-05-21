package rut.miit.linuxdeploy.controller;

import org.springframework.web.bind.annotation.*;
import rut.miit.linuxdeploy.dto.CarDto;
import rut.miit.linuxdeploy.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping
    public List<CarDto> getAllCars() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CarDto getCar(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public CarDto addCar(@RequestBody CarDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public void updateCarInfo(@PathVariable Integer id, @RequestBody CarDto dto) {
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void removeCarInfo(@PathVariable Integer id) {
        service.delete(id);
    }
}
