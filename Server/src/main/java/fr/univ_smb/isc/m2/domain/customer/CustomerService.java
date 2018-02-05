package fr.univ_smb.isc.m2.domain.customer;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final ArrayList<Customer> customers;

    public CustomerService() {
        customers = new ArrayList<>();
        customers.add(new Customer("Ritchie", "Blackmore"));
        customers.add(new Customer("Jon", "Lord"));
        customers.add(new Customer("Ian", "Paice"));
        customers.add(new Customer("Rod", "Evans"));
        customers.add(new Customer("Nick", "Simper"));
    }

    public List<Customer> all() {
        return customers;
    }
}
