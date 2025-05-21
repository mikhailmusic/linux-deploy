package rut.miit.linuxdeploy.service;

import org.springframework.stereotype.Service;
import rut.miit.linuxdeploy.dto.UserDto;
import rut.miit.linuxdeploy.exception.ParameterException;
import rut.miit.linuxdeploy.exception.NotFoundException;
import rut.miit.linuxdeploy.model.User;
import rut.miit.linuxdeploy.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserDto getById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        return toDto(user);
    }

    public UserDto create(UserDto dto) {
        if (dto.getId() != null) {
            throw new ParameterException("New user must not have an ID");
        }
        User user = new User(dto.getName(), dto.getEmail(), dto.getPhone(), dto.getAge());
        return toDto(userRepository.save(user));
    }

    public void update(Integer id, UserDto dto) {
        User existing = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setAge(dto.getAge());
        userRepository.save(existing);
    }

    public void delete(Integer id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new NotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getAge());
    }
}
