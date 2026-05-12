package com.bibliotheque.api.service;

import com.bibliotheque.api.dto.AuteurRequest;
import com.bibliotheque.api.entity.Auteur;
import com.bibliotheque.api.repository.AuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuteurService {

    @Autowired
    private AuteurRepository auteurRepository;

    public List<Auteur> getAllAuteurs() {
        return auteurRepository.findAll();
    }

    public Optional<Auteur> getAuteurById(Long id) {
        return auteurRepository.findById(id);
    }

    public Auteur createAuteur(AuteurRequest request) {
        Auteur auteur = new Auteur();
        auteur.setNom(request.getNom());
        auteur.setPrenom(request.getPrenom());
        return auteurRepository.save(auteur);
    }

    public Optional<Auteur> updateAuteur(Long id, AuteurRequest request) {
        return auteurRepository.findById(id).map(auteur -> {
            auteur.setNom(request.getNom());
            auteur.setPrenom(request.getPrenom());
            return auteurRepository.save(auteur);
        });
    }

    public boolean deleteAuteur(Long id) {
        if (auteurRepository.existsById(id)) {
            auteurRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Auteur> searchAuteur(String query) {
        return auteurRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query);
    }
}
