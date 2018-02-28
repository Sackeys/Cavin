package fr.univ_smb.isc.m2.models;

public class Color {
    public int id;
    public String label;
    public String hexa;

    private static int counter = 0;

    public Color(String label, String hexa) {
        id = counter++;
        this.label = label;
        this.hexa = hexa;
    }
}
