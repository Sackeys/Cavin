package fr.univ_smb.isc.m2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Grape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String label;

    public Grape() {
    }

    public Grape(String label) {
        this.label = label;
    }
}
