package fr.univ_smb.isc.m2.controllers.rest;

import fr.univ_smb.isc.m2.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/region", method = RequestMethod.GET)
    public List<Region> region() {
        return regionService.all();
    }

    @RequestMapping(value = "/region", method = RequestMethod.POST)
    public void region(@RequestBody Region region) {
        regionService.add(region);
    }
}