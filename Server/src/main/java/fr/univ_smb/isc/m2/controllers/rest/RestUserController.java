package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.User;
import fr.univ_smb.isc.m2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/api")
public class RestUserController {
    private final UserService userService;

    @Autowired()
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> user() {
        return userService.all();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User user(@PathVariable String id) {
        int idUser = parseInt(id);

        User user = userService.get(idUser);
        if (user == null) {
            throw new ResourceNotFoundException();
        }

        return user;
    }

    /*
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public boolean subscribe(@RequestParam String login, @RequestParam String password) {
        return true || false;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestParam String login, @RequestParam String password) {
        return true || false;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public boolean logout(@RequestParam String login, @RequestParam String password) {
        return true || false;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> user() {
        return userService.all();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User user(@PathVariable String id) throws ResourceNotFoundException {
        int userId = parseInt(id);
        User user = userService.get(userId);

        if (user == null) {
            throw new ResourceNotFoundException();
        }

        return user;
    }
    */
}
