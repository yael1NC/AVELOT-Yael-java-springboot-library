package com.bibliotheque.api.repository;

import com.bibliotheque.api.entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    List<Livre> findByTitreContainingIgnoreCase(String titre);
    List<Livre> findByCategorie(Livre.Categorie categorie);
}
