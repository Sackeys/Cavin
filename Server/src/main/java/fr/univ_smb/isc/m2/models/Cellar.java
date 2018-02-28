package fr.univ_smb.isc.m2.models;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Cellar {
    public int id;
    public String label;
    public String description;
    public ArrayList<Slot> wines;

    private static int counter = 0;


    public Cellar() {
        this("Nouvelle cave", "");
    }

    public Cellar(String label, String description) {
        id = counter++;
        this.label = label;
        this.description = description;
        wines = new ArrayList<>();
    }


    public Cellar add(Bottle bottle) {
        List<Slot> slots = wines.stream().filter(s -> s.bottle.id == bottle.id).collect(toList());

        if (slots.isEmpty()) {
            // Create a new slot with only one bottle
            wines.add(new Slot(bottle));
        }
        else {
            // Add a bottle to the existing slot
            slots.get(0).add();
        }

        return this;
    }

    public Cellar remove(Bottle bottle) {
        List<Slot> slots = wines.stream().filter(s -> s.bottle.id == bottle.id).collect(toList());

        if (!slots.isEmpty()) {
            if (slots.get(0).count > 1) {
                // Remove a bottle to the existing slot
                slots.get(0).remove();
            }
            else {
                // Remove the empty slot
                wines.remove(slots.get(0));
            }
        }

        return this;
    }
}
