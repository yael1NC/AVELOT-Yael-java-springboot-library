package com.bibliotheque.api.repository;

import com.bibliotheque.api.entity.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    List<Auteur> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}
