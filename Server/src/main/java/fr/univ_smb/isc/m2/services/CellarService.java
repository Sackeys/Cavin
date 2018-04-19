package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Cellar;
import fr.univ_smb.isc.m2.models.CellarCompact;
import fr.univ_smb.isc.m2.models.User;
import fr.univ_smb.isc.m2.repository.CellarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CellarService {
    private final CellarRepository cellarRepository;
    private final UserService userService;

    @Autowired()
    public CellarService(CellarRepository cellarRepository, @Lazy UserService userService) {
        this.cellarRepository = cellarRepository;
        this.userService = userService;
        init();
    }

    public void init() {
        add(new Cellar("Cave principale", "Toute sorte de vins"));
        add(new Cellar("Cave secondaire"));
    }

    public List<Cellar> all() {
        return cellarRepository.findAll();
    }

    public Cellar add(Cellar cellar) {
        if (cellar.label == null || cellar.label.isEmpty()) {
            return null;
        }

        cellarRepository.save(cellar);
        return cellar;
    }

    public Cellar add(int idUser, CellarCompact cellarCompact) {
        Cellar cellar = new Cellar(cellarCompact.label,
                cellarCompact.description);

        Cellar addedCellar = add(cellar);
        if (addedCellar == null) {
            return null;
        }

        return userService.add(idUser, addedCellar);
    }

    public Cellar remove(Cellar cellar) {
        if (cellar == null) {
            return null;
        }

        cellarRepository.delete(cellar);
        return cellar;
    }

    public Cellar remove(int idUser, int id) {
        User user = userService.get(idUser);
        if (user == null) {
            return null;
        }

        List<Cellar> cellars = user.cellars.stream().filter(c -> c.id == id).collect(toList());
        if (cellars.isEmpty()) {
            return null;
        }

        Cellar cellar = userService.remove(user, cellars.get(0));
        return remove(cellar);
    }

    public Cellar get(int id) {
        return cellarRepository.findOne(id);
    }

    public List<Cellar> getByUser(int id) {
        User user = userService.get(id);
        if (user == null) {
            return null;
        }

        return user.cellars;
    }

    /*

    public Cellar remove(User user, int cellar) {
        List<Cellar> cellars = user.cellars.stream().filter(c -> c.id == cellar).collect(toList());
        if (cellars.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        Cellar cellarToRemove = cellars.get(0);
        user.cellars.remove(cellarToRemove);

        return cellarToRemove;
    }
    */
}
