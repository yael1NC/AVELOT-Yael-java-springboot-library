package com.bibliotheque.api.service;

import com.bibliotheque.api.dto.LivreRequest;
import com.bibliotheque.api.entity.Auteur;
import com.bibliotheque.api.entity.Livre;
import com.bibliotheque.api.repository.AuteurRepository;
import com.bibliotheque.api.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private AuteurRepository auteurRepository;

    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    public Optional<Livre> getLivreById(Long id) {
        return livreRepository.findById(id);
    }

    public Livre createLivre(LivreRequest request) {
        Livre livre = new Livre();
        livre.setTitre(request.getTitre());
        livre.setIsbn(request.getIsbn());
        livre.setCategorie(request.getCategorie());

        if (request.getAuteurId() != null) {
            Auteur auteur = auteurRepository.findById(request.getAuteurId())
                    .orElseThrow(() -> new IllegalArgumentException("Auteur introuvable avec l'id : " + request.getAuteurId()));
            livre.setAuteur(auteur);
        }

        return livreRepository.save(livre);
    }

    public Optional<Livre> updateLivre(Long id, LivreRequest request) {
        return livreRepository.findById(id).map(livre -> {
            livre.setTitre(request.getTitre());
            livre.setIsbn(request.getIsbn());
            livre.setCategorie(request.getCategorie());

            if (request.getAuteurId() != null) {
                Auteur auteur = auteurRepository.findById(request.getAuteurId())
                        .orElseThrow(() -> new IllegalArgumentException("Auteur introuvable avec l'id : " + request.getAuteurId()));
                livre.setAuteur(auteur);
            } else {
                livre.setAuteur(null);
            }

            return livreRepository.save(livre);
        });
    }

    public boolean deleteLivre(Long id) {
        if (livreRepository.existsById(id)) {
            livreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Livre> searchByTitre(String titre) {
        return livreRepository.findByTitreContainingIgnoreCase(titre);
    }

    public List<Livre> getByCategorie(Livre.Categorie categorie) {
        return livreRepository.findByCategorie(categorie);
    }
}
