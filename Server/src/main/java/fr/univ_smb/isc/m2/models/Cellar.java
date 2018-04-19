package fr.univ_smb.isc.m2.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cellar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String label;

    public String description;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Slot> wines;

    public static int limit = 10;

    public Cellar() {}

    public Cellar(String label) {
        this.label = label;
        this.description = "";
        this.wines = new ArrayList<>();
    }

    public Cellar(String label, String description) {
        this(label);
        this.description = description;
    }
}
