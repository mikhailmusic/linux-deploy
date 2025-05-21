package rut.miit.linuxdeploy.controller;

import org.springframework.web.bind.annotation.*;
import rut.miit.linuxdeploy.dto.UserDto;
import rut.miit.linuxdeploy.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public void updateUserProfile(@PathVariable Integer id, @RequestBody UserDto dto) {
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Integer id) {
        service.delete(id);
    }
}
