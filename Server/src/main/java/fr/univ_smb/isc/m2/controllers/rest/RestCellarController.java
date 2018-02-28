package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.CellarCompact;
import fr.univ_smb.isc.m2.models.User;
import fr.univ_smb.isc.m2.services.UserService;
import fr.univ_smb.isc.m2.models.Cellar;
import fr.univ_smb.isc.m2.services.CellarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

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

    @RequestMapping(value = "/cellar", method = RequestMethod.GET)
    public List<Cellar> cellar(@RequestParam String user) {
        int userId = parseInt(user);
        User usr = userService.getUser(userId);

        if (usr == null) {
            throw new ResourceNotFoundException();
        }

        return usr.cellars;
    }

    @RequestMapping(value = "/cellar/add", method = RequestMethod.POST)
    public Cellar add(@RequestParam String user, @RequestBody CellarCompact cellar) {
        int userId = parseInt(user);
        User usr = userService.getUser(userId);

        if (usr == null) {
            throw new ResourceNotFoundException();
        }

        return cellarService.add(usr, cellar);
    }

    @RequestMapping(value = "/cellar/remove", method = RequestMethod.DELETE)
    public Cellar remove(@RequestParam String user, @RequestParam String cellar) {
        int userId = parseInt(user);
        int cellarId = parseInt(cellar);
        User usr = userService.getUser(userId);

        if (usr == null) {
            throw new ResourceNotFoundException();
        }

        return cellarService.remove(usr, cellarId);
    }
}