package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.Bottle;
import fr.univ_smb.isc.m2.repository.BottleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BottleService {
    private final BottleRepository bottleRepository;
    private final RegionService regionService;
    private final ColorService colorService;
    private final GrapeService grapeService;

    @Autowired()
    public BottleService(BottleRepository bottleRepository, RegionService regionService, ColorService colorService, GrapeService grapeService) {
        this.bottleRepository = bottleRepository;
        this.regionService = regionService;
        this.colorService = colorService;
        this.grapeService = grapeService;
        init();
    }

    public void init() {
        add(new Bottle("Château Rahoul", regionService.get(1), colorService.get(12), 2015, grapeService.get(9)));
        add(new Bottle("Champagne Bollinger Rosé", regionService.get(5), colorService.get(11), 2017, grapeService.get(7)));
        add(new Bottle("L'Ibérique", regionService.get(3), colorService.get(2), 2012, grapeService.get(4)));
    }

    public List<Bottle> all() {
        return bottleRepository.findAll();
    }

    public Bottle add(Bottle bottle) {
        if (bottle.label == null || bottle.label.isEmpty()
                || bottle.region == null
                || bottle.color == null
                || bottle.year <= 0
                || bottle.grape == null)
            return null;

        bottleRepository.save(bottle);
        return bottle;
    }

    public Bottle get(int id) {
        List<Bottle> foundBottles = all().stream().filter(b -> b.id == id).collect(Collectors.toList());
        if (foundBottles.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return foundBottles.get(0);
    }

    /*
    public List<Slot> getByUserCellar(User user, int cellar) {
        List<Cellar> foundCellars = user.cellars.stream().filter(c -> c.id == cellar).collect(Collectors.toList());
        if (foundCellars.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return foundCellars.get(0).wines;
    }
    */
}
