package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final ArrayList<User> users;

    public UserService() {
        users = new ArrayList<>();
        init();
    }

    public void init() {
        Bottle b1 = new Bottle("Château Rahoul", new Region("Auvergne-Rhône-Alpes"), new Color("Cerise", "d93d3b"), 2015, new Grape("Finot Noit")),
               b2 = new Bottle("Champagne Bollinger Rosé", new Region("Corse"), new Color("Framboise", "e94e65"), 2017, new Grape("Cabernet-sauvignon")),
               b3 = new Bottle("L'Ibérique", new Region("Bretagne"), new Color("Jaune paille", "fdd751"), 2012, new Grape("Chenin"));

        Cellar c1 = new Cellar().add(b1).add(b2),
               c2 = new Cellar().add(b1).add(b2).add(b3),
               c3 = new Cellar();

        User u1 = new User("John", "5d5v4e5fdl").add(c1),
             u2 = new User("Marc", "d6vD2vf").add(c2).add(c3),
             u3 = new User("Alf", "6969FFFF");

        users.add(u1);
        users.add(u2);
        users.add(u3);
    }

    public List<User> all() {
        return users;
    }

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
}
