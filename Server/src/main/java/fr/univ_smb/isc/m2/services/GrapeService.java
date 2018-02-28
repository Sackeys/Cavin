package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Grape;
import fr.univ_smb.isc.m2.models.Wine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GrapeService {
    private final ArrayList<Grape> grapes;

    public GrapeService() {
        grapes = new ArrayList<>();
        init();
    }

    public void init() {
        grapes.add(new Grape("Riesling"));
        grapes.add(new Grape("Gewürztraminer"));
        grapes.add(new Grape("Viognier"));
        grapes.add(new Grape("Chenin"));
        grapes.add(new Grape("Chardonnay"));
        grapes.add(new Grape("Sauvignon"));
        grapes.add(new Grape("Cabernet-sauvignon"));
        grapes.add(new Grape("Cabernet-franc"));
        grapes.add(new Grape("Finot Noit"));
        grapes.add(new Grape("Merlot"));
        grapes.add(new Grape("Syrah"));
        grapes.add(new Grape("Grenache"));
    }

    public List<Grape> all() {
        return grapes;
    }
}
