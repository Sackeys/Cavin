package fr.univ_smb.isc.m2.models;

public class Grape {
    public int id;
    public String label;

    private static int count = 0;

    public Grape(String label) {
        id = count++;
        this.label = label;
    }
}
