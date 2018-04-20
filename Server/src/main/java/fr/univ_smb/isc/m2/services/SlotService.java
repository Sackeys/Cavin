package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Bottle;
import fr.univ_smb.isc.m2.models.Slot;
import fr.univ_smb.isc.m2.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SlotService {
    private final SlotRepository slotRepository;
    private final CellarService cellarService;
    private final BottleService bottleService;

    @Autowired()
    public SlotService(SlotRepository slotRepository, @Lazy CellarService cellarService, BottleService bottleService) {
        this.slotRepository = slotRepository;
        this.cellarService = cellarService;
        this.bottleService = bottleService;
        init();
    }

    public void init() {
        add(new Slot(bottleService.get(1)));
        add(new Slot(bottleService.get(2), 4));
    }

    public List<Slot> all() {
        return slotRepository.findAll();
    }

    public Slot add(Slot slot) {
        if (slot.count <= 0
                || slot.bottle == null)
            return null;

        slotRepository.save(slot);
        return slot;
    }

    public void remove(List<Slot> slots) {
        if (slots != null) {
            slotRepository.delete(slots);
        }
    }

    public void remove(Bottle bottle) {
        if (bottle != null) {
            List<Slot> slots = all().stream().filter(s -> s.bottle.id == bottle.id).collect(toList());

            if (!slots.isEmpty()) {
                cellarService.remove(bottle);
                remove(slots);
            }
        }
    }

    public Slot get(int id) {
        return slotRepository.findOne(id);
    }
}