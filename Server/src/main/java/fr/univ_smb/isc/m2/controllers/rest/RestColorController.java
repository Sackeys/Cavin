package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.models.Color;
import fr.univ_smb.isc.m2.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestColorController {
    private final ColorService colorService;

    @Autowired()
    public RestColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @RequestMapping(value = "/color", method = RequestMethod.GET)
    public List<Color> color() {
        return colorService.all();
    }
}