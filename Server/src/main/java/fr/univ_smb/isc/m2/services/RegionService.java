package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Region;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    private final ArrayList<Region> regions;

    public RegionService() {
        regions = new ArrayList<>();
        init();
    }

    public void init() {
        regions.add(new Region("Auvergne-Rhône-Alpes"));
        regions.add(new Region("Bourgogne-Franche-Comté"));
        regions.add(new Region("Bretagne"));
        regions.add(new Region("Centre-Val de Loire"));
        regions.add(new Region("Corse"));
        regions.add(new Region("Grand Est"));
        regions.add(new Region("Hauts-de-France"));
        regions.add(new Region("Île-de-France"));
        regions.add(new Region("Normandie"));
        regions.add(new Region("Nouvelle-Aquitaine"));
        regions.add(new Region("Occitanie"));
        regions.add(new Region("Pays de la Loire"));
        regions.add(new Region("Provence-Alpes-Côte d'Azur"));
    }

    public List<Region> all() {
        return regions;
    }
}
