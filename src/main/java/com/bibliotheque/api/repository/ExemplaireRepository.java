package com.bibliotheque.api.repository;

import com.bibliotheque.api.entity.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
    List<Exemplaire> findByLivreId(Long livreId);
    List<Exemplaire> findByDisponible(Boolean disponible);
    List<Exemplaire> findByLivreIdAndDisponible(Long livreId, Boolean disponible);
}
