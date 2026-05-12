package com.bibliotheque.api.controller;

import com.bibliotheque.api.dto.UtilisateurRequest;
import com.bibliotheque.api.entity.Utilisateur;
import com.bibliotheque.api.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUtilisateur(@RequestBody UtilisateurRequest request) {
        try {
            Utilisateur utilisateur = utilisateurService.createUtilisateur(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(utilisateur);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id,
                                                          @RequestBody UtilisateurRequest request) {
        return utilisateurService.updateUtilisateur(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        if (utilisateurService.deleteUtilisateur(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Utilisateur>> searchUtilisateur(@RequestParam String q) {
        return ResponseEntity.ok(utilisateurService.searchUtilisateur(q));
    }
}
