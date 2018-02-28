package fr.univ_smb.isc.m2.models;

public enum Wine {
    WHITE(0, "Vin blanc"),
    RED(1, "Vin rouge");

    public int id;
    public String label;

    private Wine(int id, String label) {
        this.id = id;
        this.label = label;
    }
}
