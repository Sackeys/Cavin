package fr.univ_smb.isc.m2.models;

import java.util.ArrayList;

public class User {
    public int id;
    public String login;
    public String password;
    public ArrayList<Cellar> cellars;

    private static int counter = 0;

    public User(String login, String password) {
        id = counter++;
        this.login = login;
        this.password = password;
        cellars = new ArrayList<>();
    }

    public User add(Cellar cellar) {
        cellars.add(cellar);
        return this;
    }

    public User remove(Cellar cellar) {
        cellars.remove(cellar);
        return this;
    }
}
