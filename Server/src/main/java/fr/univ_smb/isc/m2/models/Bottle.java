package fr.univ_smb.isc.m2.models;

public class Bottle {
    public int id;
    public String label;
    public Region region;
    public Color color;
    public int year;
    public Grape grape;

    private static int counter = 0;

    public Bottle(String label, Region region, Color color, int year, Grape grape) {
        id = counter++;
        this.label = label;
        this.region = region;
        this.color = color;
        this.year = year;
        this.grape = grape;
    }
}
