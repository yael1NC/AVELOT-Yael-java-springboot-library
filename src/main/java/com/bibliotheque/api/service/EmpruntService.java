package com.bibliotheque.api.service;

import com.bibliotheque.api.dto.EmpruntRequest;
import com.bibliotheque.api.entity.Emprunt;
import com.bibliotheque.api.entity.Exemplaire;
import com.bibliotheque.api.entity.Utilisateur;
import com.bibliotheque.api.repository.EmpruntRepository;
import com.bibliotheque.api.repository.ExemplaireRepository;
import com.bibliotheque.api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Emprunt> getAllEmprunts() {
        return empruntRepository.findAll();
    }

    public Optional<Emprunt> getEmpruntById(Long id) {
        return empruntRepository.findById(id);
    }

    public List<Emprunt> getEmpruntsByUtilisateur(Long utilisateurId) {
        return empruntRepository.findByUtilisateurId(utilisateurId);
    }

    public List<Emprunt> getEmpruntsByStatut(Emprunt.Statut statut) {
        return empruntRepository.findByStatut(statut);
    }

    public List<Emprunt> getEmpruntsEnRetard() {
        List<Emprunt> enCours = empruntRepository.findByStatut(Emprunt.Statut.EN_COURS);
        LocalDate today = LocalDate.now();
        return enCours.stream()
                .filter(e -> e.getDateRetourPrevue().isBefore(today))
                .toList();
    }

    public Emprunt createEmprunt(EmpruntRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(request.getUtilisateurId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec l'id : " + request.getUtilisateurId()));

        Exemplaire exemplaire = exemplaireRepository.findById(request.getExemplaireId())
                .orElseThrow(() -> new IllegalArgumentException("Exemplaire introuvable avec l'id : " + request.getExemplaireId()));

        if (!exemplaire.getDisponible()) {
            throw new IllegalStateException("Cet exemplaire n'est pas disponible.");
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setUtilisateur(utilisateur);
        emprunt.setExemplaire(exemplaire);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(request.getDateRetourPrevue());
        emprunt.setStatut(Emprunt.Statut.EN_COURS);

        exemplaire.setDisponible(false);
        exemplaireRepository.save(exemplaire);

        return empruntRepository.save(emprunt);
    }

    public Optional<Emprunt> retournerEmprunt(Long id) {
        return empruntRepository.findById(id).map(emprunt -> {
            if (emprunt.getStatut() == Emprunt.Statut.RETOURNE) {
                throw new IllegalStateException("Cet emprunt est déjà retourné.");
            }

            emprunt.setDateRetourEffective(LocalDate.now());
            emprunt.setStatut(Emprunt.Statut.RETOURNE);

            Exemplaire exemplaire = emprunt.getExemplaire();
            exemplaire.setDisponible(true);
            exemplaireRepository.save(exemplaire);

            return empruntRepository.save(emprunt);
        });
    }

    public boolean deleteEmprunt(Long id) {
        if (empruntRepository.existsById(id)) {
            empruntRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void mettreAJourRetards() {
        LocalDate today = LocalDate.now();
        List<Emprunt> enCours = empruntRepository.findByStatut(Emprunt.Statut.EN_COURS);
        enCours.stream()
                .filter(e -> e.getDateRetourPrevue().isBefore(today))
                .forEach(e -> {
                    e.setStatut(Emprunt.Statut.EN_RETARD);
                    empruntRepository.save(e);
                });
    }
}
