package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Cellar;
import fr.univ_smb.isc.m2.models.User;
import fr.univ_smb.isc.m2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CellarService cellarService;

    @Autowired()
    public UserService(UserRepository userRepository, CellarService cellarService) {
        this.userRepository = userRepository;
        this.cellarService = cellarService;
        init();
    }

    public void init() {
        add(new User("Jack", "EK94d69s", new ArrayList<Cellar>() {{
            add(cellarService.get(1));
            add(cellarService.get(2));
        }}));

        add(new User("Marc", "A69D666E"));
    }

    public List<User> all() {
        return userRepository.findAll();
    }

    public User add(User user) {
        if (user.login == null || user.login.isEmpty()
                || user.password == null || user.password.isEmpty()) {
            return null;
        }

        userRepository.save(user);
        return user;
    }

    public Cellar add(int id, Cellar cellar) {
        if (cellar.label == null || cellar.label.isEmpty()) {
            return null;
        }

        User user = get(id);
        if (user == null) {
            return null;
        }

        user.cellars.add(cellar);
        userRepository.save(user);
        return cellar;
    }

    public Cellar remove(User user, Cellar cellar) {
        if (user == null || cellar == null) {
            return null;
        }

        user.cellars.remove(cellar);
        userRepository.save(user);
        return cellar;
    }

    public User get(int id) {
        return userRepository.findOne(id);
    }

    /*
    public User getUser(int userId) {
        List<User> users = all().stream().filter(u -> u.id == userId).collect(toList());

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public List<Cellar> getCellars(int user) {
        List<User> collect = all().stream().filter(u -> u.id == user).collect(toList());

        if (collect.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return collect.get(0).cellars;
    }
    */
}
