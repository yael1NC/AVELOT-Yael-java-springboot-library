package com.bibliotheque.api.controller;

import com.bibliotheque.api.dto.AuteurRequest;
import com.bibliotheque.api.entity.Auteur;
import com.bibliotheque.api.service.AuteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auteurs")
public class AuteurController {

    @Autowired
    private AuteurService auteurService;

    @GetMapping
    public ResponseEntity<List<Auteur>> getAllAuteurs() {
        return ResponseEntity.ok(auteurService.getAllAuteurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auteur> getAuteurById(@PathVariable Long id) {
        return auteurService.getAuteurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Auteur> createAuteur(@RequestBody AuteurRequest request) {
        Auteur auteur = auteurService.createAuteur(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(auteur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auteur> updateAuteur(@PathVariable Long id,
                                                @RequestBody AuteurRequest request) {
        return auteurService.updateAuteur(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuteur(@PathVariable Long id) {
        if (auteurService.deleteAuteur(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Auteur>> searchAuteur(@RequestParam String q) {
        return ResponseEntity.ok(auteurService.searchAuteur(q));
    }
}
