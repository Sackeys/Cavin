package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.Bottle;
import fr.univ_smb.isc.m2.models.BottleCompact;
import fr.univ_smb.isc.m2.services.BottleService;
import fr.univ_smb.isc.m2.services.UserService;
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

        Bottle bottle = bottleService.get(idBottle);
        if (bottle == null) {
            throw new ResourceNotFoundException();
        }

        return bottle;
    }

    @RequestMapping(value = "/bottle", method = RequestMethod.POST)
    public Bottle add(@RequestBody BottleCompact bottleCompact) {
        Bottle bottle = bottleService.add(bottleCompact);
        if (bottle == null) {
            throw new ResourceNotFoundException();
        }

        return bottle;
    }

    @RequestMapping(value = "/bottle/{id}", method = RequestMethod.DELETE)
    public Bottle remove(@PathVariable String id) {
        int idBottle = parseInt(id);
        Bottle bottle = bottleService.remove(idBottle);
        if (bottle == null) {
            throw new ResourceNotFoundException();
        }

        return bottle;
    }
}
