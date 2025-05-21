package rut.miit.linuxdeploy.service;

import org.springframework.stereotype.Service;
import rut.miit.linuxdeploy.dto.CarDto;
import rut.miit.linuxdeploy.exception.ParameterException;
import rut.miit.linuxdeploy.exception.NotFoundException;
import rut.miit.linuxdeploy.model.Car;
import rut.miit.linuxdeploy.repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarDto> getAll() {
        return carRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public CarDto getById(Integer id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car with id " + id + " not found"));
        return toDto(car);
    }

    public CarDto create(CarDto dto) {
        if (dto.getId() != null) {
            throw new ParameterException("New car must not have an ID");
        }
        Car car = new Car(dto.getName(), dto.getModel(), dto.getColor(), dto.getYear());
        return toDto(carRepository.save(car));
    }

    public void update(Integer id, CarDto dto) {
        Car existing = carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car with id " + id + " not found"));
        existing.setName(dto.getName());
        existing.setModel(dto.getModel());
        existing.setColor(dto.getColor());
        existing.setYear(dto.getYear());
        carRepository.save(existing);
    }

    public void delete(Integer id) {
        if (!carRepository.findById(id).isPresent()) {
            throw new NotFoundException("Car with id " + id + " not found");
        }
        carRepository.deleteById(id);
    }

    private CarDto toDto(Car car) {
        return new CarDto(car.getId(), car.getName(), car.getModel(), car.getColor(), car.getYear());
    }
}
