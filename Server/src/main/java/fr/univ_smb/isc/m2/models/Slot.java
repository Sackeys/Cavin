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

    public Slot(Bottle bottle, int count) {
        this.bottle = bottle;
        this.count = count;
    }

    public Slot(Bottle bottle) {
        this(bottle, 1);
    }

    public int add() {
        return ++this.count;
    }

    public int remove() {
        return --this.count;
    }
}
