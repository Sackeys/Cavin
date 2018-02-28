package fr.univ_smb.isc.m2.models;

public class Slot {
    public int id;
    public Bottle bottle;
    public int count;

    private static int counter = 0;

    public Slot(Bottle bottle, int count) {
        id = counter++;
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
