package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.*;
import fr.univ_smb.isc.m2.repository.CellarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CellarService {
    private final CellarRepository cellarRepository;
    private final UserService userService;
    private final SlotService slotService;

    @Autowired()
    public CellarService(CellarRepository cellarRepository, @Lazy UserService userService, SlotService slotService) {
        this.cellarRepository = cellarRepository;
        this.userService = userService;
        this.slotService = slotService;
        init();
    }

    public void init() {
        add(new Cellar("Cave principale", "Toute sorte de vins"));
        add(new Cellar("Cave secondaire"));
        add(new Cellar("Cave pré-remplie", "", new ArrayList<Slot>(){{ add(slotService.get(1)); add(slotService.get(2)); }}));
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

    public Slot add(int idUser, int id, SlotCompact slotCompact) {
        User user = userService.get(idUser);
        if (user == null) {
            return null;
        }

        List<Cellar> cellars = user.cellars.stream().filter(c -> c.id == id).collect(toList());
        if (cellars.isEmpty()) {
            return null;
        }

        Cellar cellar = cellars.get(0);
        if (cellar == null || slotCompact == null || slotCompact.count <= 0) {
            return null;
        }

        if (slotCompact.count > Cellar.limit) {
            slotCompact.count = Cellar.limit;
        }

        List<Slot> slots = cellar.wines.stream().filter(s -> s.bottle.id == slotCompact.bottle).collect(toList());
        Slot slot;
        if (slots.isEmpty()) {
            slot = slotService.add(new Slot(slotService.getBottle(slotCompact.bottle), slotCompact.count));
            cellar.wines.add(slot);
            cellarRepository.save(cellar);
        }
        else {
            slot = slots.get(0);
            slotService.up(slot, slotCompact.count, Cellar.limit);
        }

        return slot;
    }

    public Slot up(int idUser, int id, int idSlot) {
        User user = userService.get(idUser);
        if (user == null) {
            return null;
        }

        List<Cellar> cellars = user.cellars.stream().filter(c -> c.id == id).collect(toList());
        if (cellars.isEmpty()) {
            return null;
        }

        Cellar cellar = cellars.get(0);
        if (cellar == null) {
            return null;
        }

        List<Slot> slots = cellar.wines.stream().filter(s -> s.id == idSlot).collect(toList());
        if (slots.isEmpty()) {
            return null;
        }

        Slot slot = slots.get(0);
        if (slot == null) {
            return null;
        }

        return slotService.up(slot, 1, Cellar.limit);
    }

    public Slot down(int idUser, int id, int idSlot) {
        User user = userService.get(idUser);
        if (user == null) {
            return null;
        }

        List<Cellar> cellars = user.cellars.stream().filter(c -> c.id == id).collect(toList());
        if (cellars.isEmpty()) {
            return null;
        }

        Cellar cellar = cellars.get(0);
        if (cellar == null) {
            return null;
        }

        List<Slot> slots = cellar.wines.stream().filter(s -> s.id == idSlot).collect(toList());
        if (slots.isEmpty()) {
            return null;
        }

        Slot slot = slots.get(0);
        if (slot == null) {
            return null;
        }

        Slot updatedSlot = slotService.down(slot, 1);
        if (updatedSlot.count <= 0) {
            cellar.wines.removeIf(w -> w.id == updatedSlot.id);
            cellarRepository.save(cellar);
            slotService.remove(updatedSlot);
        }

        return updatedSlot;
    }

    public Cellar remove(Cellar cellar) {
        if (cellar == null) {
            return null;
        }

        cellarRepository.delete(cellar);

        if (cellar.wines.size() > 0) {
            slotService.remove(cellar.wines);
        }

        return cellar;
    }

    public void remove(Bottle bottle) {
        if (bottle != null) {
            for (Cellar c : all()) {
                c.wines.removeIf(w -> w.bottle.id == bottle.id);
                cellarRepository.save(c);
            }
        }
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
}
