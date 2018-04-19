package fr.univ_smb.isc.m2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String login;

    @JsonIgnore
    public String password;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Cellar> cellars = new ArrayList<>();

    public User() {}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, List<Cellar> cellars) {
        this(login, password);
        this.cellars = cellars;
    }
}
