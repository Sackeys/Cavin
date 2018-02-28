package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.config.rest.ResourceNotFoundException;
import fr.univ_smb.isc.m2.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CellarService {
    public Cellar add(User user, CellarCompact cellarCompact) {
        Cellar cellar = new Cellar(cellarCompact.label, cellarCompact.description);
        user.cellars.add(cellar);

        return cellar;
    }

    public Cellar remove(User user, int cellar) {
        List<Cellar> cellars = user.cellars.stream().filter(c -> c.id == cellar).collect(toList());
        if (cellars.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        Cellar cellarToRemove = cellars.get(0);
        user.cellars.remove(cellarToRemove);

        return cellarToRemove;
    }
}
