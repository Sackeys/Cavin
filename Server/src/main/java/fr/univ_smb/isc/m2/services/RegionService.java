package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Region;
import fr.univ_smb.isc.m2.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    @Autowired()
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
        init();
    }

    public void init() {
        add(new Region("Auvergne-Rhône-Alpes"));
        add(new Region("Bourgogne-Franche-Comté"));
        add(new Region("Bretagne"));
        add(new Region("Centre-Val de Loire"));
        add(new Region("Corse"));
        add(new Region("Grand Est"));
        add(new Region("Hauts-de-France"));
        add(new Region("Île-de-France"));
        add(new Region("Normandie"));
        add(new Region("Nouvelle-Aquitaine"));
        add(new Region("Occitanie"));
        add(new Region("Pays de la Loire"));
        add(new Region("Provence-Alpes-Côte d'Azur"));
    }

    public List<Region> all() {
        return regionRepository.findAll();
    }

    public void add(Region region) {
        regionRepository.save(region);
    }
}
