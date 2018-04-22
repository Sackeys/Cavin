package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.Cellar;
import fr.univ_smb.isc.m2.models.CellarCompact;
import fr.univ_smb.isc.m2.models.Slot;
import fr.univ_smb.isc.m2.models.SlotCompact;
import fr.univ_smb.isc.m2.services.CellarService;
import fr.univ_smb.isc.m2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/api")
public class RestCellarController {
    private final UserService userService;
    private final CellarService cellarService;

    @Autowired()
    public RestCellarController(UserService userService, CellarService cellarService) {
        this.userService = userService;
        this.cellarService = cellarService;
    }

    @RequestMapping(value = "/cellar", method = RequestMethod.GET, params = { "user" })
    public List<Cellar> cellar(@RequestParam String user) {
        int idUser = Integer.parseInt(user);
        List<Cellar> cellars = cellarService.getByUser(idUser);
        if (cellars == null) {
            throw new ResourceNotFoundException();
        }

        return cellars;
    }

    @RequestMapping(value = "/cellar", method = RequestMethod.POST)
    public Cellar add(@RequestParam String user, @RequestBody CellarCompact cellarComptact) {
        int idUser = Integer.parseInt(user);
        Cellar cellar = cellarService.add(idUser, cellarComptact);
        if (cellar == null) {
            throw new ResourceNotFoundException();
        }

        return cellar;
    }

    @RequestMapping(value = "/cellar/{id}", method = RequestMethod.DELETE)
    public Cellar remove(@PathVariable String id, @RequestParam String user) {
        int idCellar = parseInt(id),
                idUser = parseInt(user);
        Cellar cellar = cellarService.remove(idUser, idCellar);
        if (cellar == null) {
            throw new ResourceNotFoundException();
        }

        return cellar;
    }

    @RequestMapping(value = "/cellar/{id}", method = RequestMethod.POST)
    public Slot add(@PathVariable String id, @RequestParam String user, @RequestBody SlotCompact slotCompact) {
        int idCellar = parseInt(id),
                idUser = parseInt(user);

        Slot slot = cellarService.add(idUser, idCellar, slotCompact);
        if (slot == null) {
            throw new ResourceNotFoundException();
        }

        return slot;
    }

    @RequestMapping(value = "/cellar/{id}/up", method = RequestMethod.PUT)
    public Slot up(@PathVariable String id, @RequestParam String user, @RequestParam String slot) {
        int idCellar = parseInt(id),
                idUser = parseInt(user),
                idSlot = parseInt(slot);

        Slot updatedSlot = cellarService.up(idUser, idCellar, idSlot);
        if (updatedSlot == null) {
            throw new ResourceNotFoundException();
        }

        return updatedSlot;
    }

    @RequestMapping(value = "/cellar/{id}/down", method = RequestMethod.PUT)
    public Slot down(@PathVariable String id, @RequestParam String user, @RequestParam String slot) {
        int idCellar = parseInt(id),
                idUser = parseInt(user),
                idSlot = parseInt(slot);

        Slot updatedSlot = cellarService.down(idUser, idCellar, idSlot);
        if (updatedSlot == null) {
            throw new ResourceNotFoundException();
        }

        return updatedSlot;
    }
}