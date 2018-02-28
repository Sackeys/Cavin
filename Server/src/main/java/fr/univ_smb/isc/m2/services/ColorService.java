package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Color;
import fr.univ_smb.isc.m2.models.Wine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColorService {

    private final ArrayList<Color> colors;

    public ColorService() {
        colors = new ArrayList<>();
        init();
    }

    void init() {
        colors.add(new Color("Jaune vert", "e2dc85"));
        colors.add(new Color("Jaune paille", "fdd751"));
        colors.add(new Color("Or vert", "e9d750"));
        colors.add(new Color("Or pâle", "ebc651"));
        colors.add(new Color("Or jaune", "e4c040"));
        colors.add(new Color("Doré", "d4aa2e"));
        colors.add(new Color("Vieil or", "d19330"));
        colors.add(new Color("Ambré clair", "e19e36"));
        colors.add(new Color("Ambré foncé", "e39132"));
        colors.add(new Color("Roux", "c57d29"));
        colors.add(new Color("Framboise", "e94e65"));
        colors.add(new Color("Cerise", "d93d3b"));
        colors.add(new Color("Rubis", "b91c19"));
        colors.add(new Color("Pourpre", "9a1f49"));
        colors.add(new Color("Violet", "8e1665"));
        colors.add(new Color("Grenat", "743651"));
        colors.add(new Color("Tuilé", "8d3216"));
    }

    public List<Color> all() {
        return colors;
    }
}
