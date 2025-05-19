package rut.miit.linuxdeploy.repository;

import org.springframework.stereotype.Repository;
import rut.miit.linuxdeploy.model.User;

import java.util.*;

@Repository
public class UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private static int idCounter = 1;

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idCounter);
            users.put(idCounter, user);
            idCounter++;
        } else {
            users.put(user.getId(), user);
        }
        return user;
    }

    public void deleteById(Integer id) {
        users.remove(id);
    }
}