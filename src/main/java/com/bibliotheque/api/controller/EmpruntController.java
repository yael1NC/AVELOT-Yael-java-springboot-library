package com.bibliotheque.api.controller;

import com.bibliotheque.api.dto.EmpruntRequest;
import com.bibliotheque.api.entity.Emprunt;
import com.bibliotheque.api.service.EmpruntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

    @Autowired
    private EmpruntService empruntService;

    @GetMapping
    public ResponseEntity<List<Emprunt>> getAllEmprunts() {
        return ResponseEntity.ok(empruntService.getAllEmprunts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprunt> getEmpruntById(@PathVariable Long id) {
        return empruntService.getEmpruntById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<Emprunt>> getEmpruntsByUtilisateur(@PathVariable Long utilisateurId) {
        return ResponseEntity.ok(empruntService.getEmpruntsByUtilisateur(utilisateurId));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Emprunt>> getEmpruntsByStatut(@PathVariable Emprunt.Statut statut) {
        return ResponseEntity.ok(empruntService.getEmpruntsByStatut(statut));
    }

    @GetMapping("/retards")
    public ResponseEntity<List<Emprunt>> getEmpruntsEnRetard() {
        return ResponseEntity.ok(empruntService.getEmpruntsEnRetard());
    }

    @PostMapping
    public ResponseEntity<?> createEmprunt(@RequestBody EmpruntRequest request) {
        try {
            Emprunt emprunt = empruntService.createEmprunt(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(emprunt);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/retour")
    public ResponseEntity<?> retournerEmprunt(@PathVariable Long id) {
        try {
            return empruntService.retournerEmprunt(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmprunt(@PathVariable Long id) {
        if (empruntService.deleteEmprunt(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
