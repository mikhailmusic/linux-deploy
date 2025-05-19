package rut.miit.linuxdeploy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rut.miit.linuxdeploy.model.User;
import rut.miit.linuxdeploy.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        User savedUser = repository.save(user);
        return ResponseEntity.ok(savedUser);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody User user) {
        if (!repository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        repository.save(user);
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (!repository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }
}

