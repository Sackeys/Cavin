package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BottleService {
    private final ArrayList<Bottle> bottles;

    public BottleService() {
        bottles = new ArrayList<>();
        init();
    }

    public void init() {
        bottles.add(new Bottle("Château Rahoul", new Region("Auvergne-Rhône-Alpes"), new Color("Cerise", "d93d3b"), 2015, new Grape("Finot Noit")));
        bottles.add(new Bottle("Champagne Bollinger Rosé", new Region("Corse"), new Color("Framboise", "e94e65"), 2017, new Grape("Cabernet-sauvignon")));
        bottles.add(new Bottle("L'Ibérique", new Region("Bretagne"), new Color("Jaune paille", "fdd751"), 2012, new Grape("Chenin")));
    }

    public List<Bottle> all() {
        return bottles;
    }

    public Bottle getById(int id) {
        List<Bottle> foundBottles = bottles.stream().filter(b -> b.id == id).collect(Collectors.toList());
        if (foundBottles.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return foundBottles.get(0);
    }

    public List<Slot> getByUserCellar(User user, int cellar) {
        List<Cellar> foundCellars = user.cellars.stream().filter(c -> c.id == cellar).collect(Collectors.toList());
        if (foundCellars.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return foundCellars.get(0).wines;
    }
}
