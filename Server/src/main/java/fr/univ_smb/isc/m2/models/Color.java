package fr.univ_smb.isc.m2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public String label;

    public String hexa;

    public Color() {
    }

    public Color(String label, String hexa) {
        this.label = label;
        this.hexa = hexa;
    }
}
