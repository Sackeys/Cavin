package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Bottle;
import fr.univ_smb.isc.m2.models.BottleCompact;
import fr.univ_smb.isc.m2.repository.BottleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                || bottle.grape == null) {
            return null;
        }

        bottleRepository.save(bottle);
        return bottle;
    }

    public Bottle add(BottleCompact bottleCompact) {
        Bottle bottle = new Bottle(bottleCompact.label,
                regionService.get(bottleCompact.region),
                colorService.get(bottleCompact.color),
                bottleCompact.year,
                grapeService.get(bottleCompact.grape));

        return add(bottle);
    }

    public Bottle remove(Bottle bottle) {
        if (bottle == null) {
            return null;
        }

        bottleRepository.delete(bottle);
        return bottle;
    }

    public Bottle remove(int id) {
        return remove(get(id));
    }

    public Bottle get(int id) {
        return bottleRepository.findOne(id);
    }
}
