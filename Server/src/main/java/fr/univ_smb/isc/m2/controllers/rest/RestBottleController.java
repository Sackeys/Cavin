package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.User;
import fr.univ_smb.isc.m2.services.UserService;
import fr.univ_smb.isc.m2.models.Slot;
import fr.univ_smb.isc.m2.models.Bottle;
import fr.univ_smb.isc.m2.services.BottleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/api")
public class RestBottleController {
    private final UserService userService;
    private final BottleService bottleService;

    @Autowired()
    public RestBottleController(UserService userService, BottleService bottleService) {
        this.userService = userService;
        this.bottleService = bottleService;
    }

    @RequestMapping(value = "/bottle", method = RequestMethod.GET)
    public List<Bottle> bottle() {
        return bottleService.all();
    }

    @RequestMapping(value = "/bottle/{id}", method = RequestMethod.GET)
    public Bottle bottle(@PathVariable String id) {
        int idBottle = parseInt(id);
        return bottleService.getById(idBottle);
    }

    @RequestMapping(value = "/bottle", method = RequestMethod.GET, params = { "user", "cellar" })
    public List<Slot> bottle(@RequestParam String user, @RequestParam String cellar) {
        int idUser = parseInt(user);
        int idCellar = parseInt(cellar);
        User usr = userService.getUser(idUser);

        if (usr == null) {
            throw new ResourceNotFoundException();
        }

        return bottleService.getByUserCellar(usr, idCellar);
    }

    /*
    @RequestMapping(value = "/bottles", method = RequestMethod.GET, params = "label")
    public List<Bottle> bottlesByLabel(@RequestParam String label) {
        // Encode label qui vient de l'url
        List<Bottle> collect = bottleService.all().stream().filter(
                b -> b.label.matches(label)).collect(toList());

        if (collect.isEmpty())
            throw new ResourceNotFoundException();

        return collect;
    }
    */

    @RequestMapping(value = "/test", method = RequestMethod.GET, params = "label", produces = "application/json; charset=utf-8")
    public String test(@RequestParam String label) {
        return label;
    }
}
