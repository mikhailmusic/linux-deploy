package rut.miit.linuxdeploy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rut.miit.linuxdeploy.model.Car;
import rut.miit.linuxdeploy.model.User;
import rut.miit.linuxdeploy.repository.CarRepository;
import rut.miit.linuxdeploy.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public DataLoader(UserRepository userRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.save(new User("Alice", "alice@example.com", "+1234567890", 30));
        userRepository.save(new User("Bob", "bob@example.com", "+0987654321", 25));
        userRepository.save(new User("Charlie", "charlie@example.com", "+1928374654", 40));

        carRepository.save(new Car("Tesla", "Model S", "Black", 2022));
        carRepository.save(new Car("Toyota", "Camry", "Silver", 2018));
        carRepository.save(new Car("BMW", "X5", "White", 2020));
        carRepository.save(new Car("Ford", "Mustang", "Red", 2021));
        carRepository.save(new Car("Audi", "A4", "Blue", 2019));

        System.out.println("Initial data loaded successfully!");
    }
}
