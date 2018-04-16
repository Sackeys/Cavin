package fr.univ_smb.isc.m2.models;

import javax.persistence.*;

@Entity
public class Bottle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String label;

    @OneToOne
    public Region region;

    @OneToOne
    public Color color;

    public int year;

    @OneToOne
    public Grape grape;

    public Bottle() {
    }

    public Bottle(String label, Region region, Color color, int year, Grape grape) {
        this.label = label;
        this.region = region;
        this.color = color;
        this.year = year;
        this.grape = grape;
    }
}
