package fr.univ_smb.isc.m2.domain.customer;

public class Customer {

    public final String firstName;
    public final String lastName;
    public final int id;

    private static int counter = 0;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        id = counter++;
    }

}
