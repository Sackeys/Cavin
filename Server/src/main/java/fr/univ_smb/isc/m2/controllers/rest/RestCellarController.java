package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.Cellar;
import fr.univ_smb.isc.m2.models.CellarCompact;
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

    // A SUPPRIMER
    @RequestMapping(value = "/cellar", method = RequestMethod.GET)
    public List<Cellar> cellar() {
        return cellarService.all();
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
}