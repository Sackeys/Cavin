package fr.univ_smb.isc.m2.models;

import javax.persistence.*;

@Entity
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @OneToOne
    public Bottle bottle;

    public int count;

    public Slot() {}

    public Slot(Bottle bottle, int count) {
        this.bottle = bottle;
        this.count = count;
    }

    public Slot(Bottle bottle) {
        this(bottle, 1);
    }
}
