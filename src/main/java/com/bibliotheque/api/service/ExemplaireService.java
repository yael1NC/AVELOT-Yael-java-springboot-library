package com.bibliotheque.api.service;

import com.bibliotheque.api.dto.ExemplaireRequest;
import com.bibliotheque.api.entity.Exemplaire;
import com.bibliotheque.api.entity.Livre;
import com.bibliotheque.api.repository.ExemplaireRepository;
import com.bibliotheque.api.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExemplaireService {

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private LivreRepository livreRepository;

    public List<Exemplaire> getAllExemplaires() {
        return exemplaireRepository.findAll();
    }

    public Optional<Exemplaire> getExemplaireById(Long id) {
        return exemplaireRepository.findById(id);
    }

    public List<Exemplaire> getByLivre(Long livreId) {
        return exemplaireRepository.findByLivreId(livreId);
    }

    public List<Exemplaire> getDisponibles() {
        return exemplaireRepository.findByDisponible(true);
    }

    public List<Exemplaire> getDisponiblesByLivre(Long livreId) {
        return exemplaireRepository.findByLivreIdAndDisponible(livreId, true);
    }

    public Exemplaire createExemplaire(ExemplaireRequest request) {
        Livre livre = livreRepository.findById(request.getLivreId())
                .orElseThrow(() -> new IllegalArgumentException("Livre introuvable avec l'id : " + request.getLivreId()));

        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setLivre(livre);
        exemplaire.setEtat(request.getEtat());
        exemplaire.setDisponible(true);
        return exemplaireRepository.save(exemplaire);
    }

    public Optional<Exemplaire> updateExemplaire(Long id, ExemplaireRequest request) {
        return exemplaireRepository.findById(id).map(exemplaire -> {
            Livre livre = livreRepository.findById(request.getLivreId())
                    .orElseThrow(() -> new IllegalArgumentException("Livre introuvable avec l'id : " + request.getLivreId()));
            exemplaire.setLivre(livre);
            exemplaire.setEtat(request.getEtat());
            return exemplaireRepository.save(exemplaire);
        });
    }

    public boolean deleteExemplaire(Long id) {
        if (exemplaireRepository.existsById(id)) {
            exemplaireRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
