package fr.univ_smb.isc.m2.repository;

import fr.univ_smb.isc.m2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}