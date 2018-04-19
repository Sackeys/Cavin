package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Grape;
import fr.univ_smb.isc.m2.repository.GrapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrapeService {
    private final GrapeRepository grapeRepository;

    @Autowired()
    public GrapeService(GrapeRepository grapeRepository) {
        this.grapeRepository = grapeRepository;
        init();
    }

    public void init() {
        add(new Grape("Riesling"));
        add(new Grape("Gewürztraminer"));
        add(new Grape("Viognier"));
        add(new Grape("Chenin"));
        add(new Grape("Chardonnay"));
        add(new Grape("Sauvignon"));
        add(new Grape("Cabernet-sauvignon"));
        add(new Grape("Cabernet-franc"));
        add(new Grape("Finot Noit"));
        add(new Grape("Merlot"));
        add(new Grape("Syrah"));
        add(new Grape("Grenache"));
    }

    public List<Grape> all() {
        return grapeRepository.findAll();
    }

    public Grape add(Grape grape) {
        if (grape.label == null || grape.label.isEmpty()) {
            return null;
        }

        grapeRepository.save(grape);
        return grape;
    }

    public Grape get(int id) {
        return grapeRepository.findOne(id);
    }
}
