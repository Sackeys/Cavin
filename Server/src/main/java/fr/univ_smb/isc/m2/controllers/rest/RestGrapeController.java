package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.models.Grape;
import fr.univ_smb.isc.m2.services.GrapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestGrapeController {
    private final GrapeService grapeService;

    @Autowired()
    public RestGrapeController(GrapeService grapeService) {
        this.grapeService = grapeService;
    }

    @RequestMapping(value = "/grape", method = RequestMethod.GET)
    public List<Grape> grape() {
        return grapeService.all();
    }
}
