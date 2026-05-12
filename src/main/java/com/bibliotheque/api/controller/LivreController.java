package com.bibliotheque.api.controller;

import com.bibliotheque.api.dto.LivreRequest;
import com.bibliotheque.api.entity.Livre;
import com.bibliotheque.api.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @GetMapping
    public ResponseEntity<List<Livre>> getAllLivres() {
        return ResponseEntity.ok(livreService.getAllLivres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
        return livreService.getLivreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Livre> createLivre(@RequestBody LivreRequest request) {
        Livre nouveauLivre = livreService.createLivre(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauLivre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable Long id,
                                              @RequestBody LivreRequest request) {
        return livreService.updateLivre(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivre(@PathVariable Long id) {
        if (livreService.deleteLivre(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Livre>> searchByTitre(@RequestParam String titre) {
        return ResponseEntity.ok(livreService.searchByTitre(titre));
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<Livre>> getByCategorie(@PathVariable Livre.Categorie categorie) {
        return ResponseEntity.ok(livreService.getByCategorie(categorie));
    }
}
