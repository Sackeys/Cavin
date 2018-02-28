package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univ_smb.isc.m2.models.Region;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestRegionController {

    private final RegionService regionService;

    @Autowired()
    public RestRegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @RequestMapping(value = "/regions", method = RequestMethod.GET)
    public List<Region> region() {
        return regionService.all();
    }
}