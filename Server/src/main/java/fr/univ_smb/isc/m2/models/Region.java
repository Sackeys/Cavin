package fr.univ_smb.isc.m2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public String label;

    public Region() {
    }

    public Region(String label) {
        this.label = label;
    }
}
