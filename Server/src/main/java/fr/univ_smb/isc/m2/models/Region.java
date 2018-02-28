package fr.univ_smb.isc.m2.models;

public class Region {
    public int id;
    public String label;

    private static int counter = 0;

    public Region(String label) {
        id = counter++;
        this.label = label;
    }
}
