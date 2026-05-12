package com.bibliotheque.api.repository;

import com.bibliotheque.api.entity.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    List<Emprunt> findByUtilisateurId(Long utilisateurId);
    List<Emprunt> findByStatut(Emprunt.Statut statut);
    List<Emprunt> findByDateRetourPrevueBefore(LocalDate date);
    List<Emprunt> findByExemplaireId(Long exemplaireId);
}
