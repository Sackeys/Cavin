package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Slot;
import fr.univ_smb.isc.m2.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {
    private final SlotRepository slotRepository;

    @Autowired()
    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
        init();
    }

    public void init() {

    }

    public List<Slot> all() {
        return slotRepository.findAll();
    }

    public Slot add(Slot slot) {
        if (slot.bottle == null)
            return null;

        slotRepository.save(slot);
        return slot;
    }
}