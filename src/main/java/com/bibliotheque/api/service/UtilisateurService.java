package com.bibliotheque.api.service;

import com.bibliotheque.api.dto.UtilisateurRequest;
import com.bibliotheque.api.entity.Utilisateur;
import com.bibliotheque.api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur createUtilisateur(UtilisateurRequest request) {
        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà.");
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setTelephone(request.getTelephone());
        return utilisateurRepository.save(utilisateur);
    }

    public Optional<Utilisateur> updateUtilisateur(Long id, UtilisateurRequest request) {
        return utilisateurRepository.findById(id).map(utilisateur -> {
            utilisateur.setNom(request.getNom());
            utilisateur.setPrenom(request.getPrenom());
            utilisateur.setEmail(request.getEmail());
            utilisateur.setTelephone(request.getTelephone());
            return utilisateurRepository.save(utilisateur);
        });
    }

    public boolean deleteUtilisateur(Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Utilisateur> searchUtilisateur(String query) {
        return utilisateurRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query);
    }
}
