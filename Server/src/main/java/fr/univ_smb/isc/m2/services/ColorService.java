package fr.univ_smb.isc.m2.services;

import fr.univ_smb.isc.m2.models.Color;
import fr.univ_smb.isc.m2.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {
    private final ColorRepository colorRepository;

    @Autowired()
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
        init();
    }

    public void init() {
        add(new Color("Jaune vert", "e2dc85"));
        add(new Color("Jaune paille", "fdd751"));
        add(new Color("Or vert", "e9d750"));
        add(new Color("Or pâle", "ebc651"));
        add(new Color("Or jaune", "e4c040"));
        add(new Color("Doré", "d4aa2e"));
        add(new Color("Vieil or", "d19330"));
        add(new Color("Ambré clair", "e19e36"));
        add(new Color("Ambré foncé", "e39132"));
        add(new Color("Roux", "c57d29"));
        add(new Color("Framboise", "e94e65"));
        add(new Color("Cerise", "d93d3b"));
        add(new Color("Rubis", "b91c19"));
        add(new Color("Pourpre", "9a1f49"));
        add(new Color("Violet", "8e1665"));
        add(new Color("Grenat", "743651"));
        add(new Color("Tuilé", "8d3216"));
    }

    public List<Color> all() {
        return colorRepository.findAll();
    }

    public Color add(Color color) {
        if (color.label == null || color.label.isEmpty()
                || color.hexa == null || color.hexa.isEmpty()) {
            return null;
        }

        colorRepository.save(color);
        return color;
    }

    public Color get(int id) {
        return colorRepository.findOne(id);
    }
}
