package com.bibliotheque.api.controller;

import com.bibliotheque.api.dto.ExemplaireRequest;
import com.bibliotheque.api.entity.Exemplaire;
import com.bibliotheque.api.service.ExemplaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exemplaires")
public class ExemplaireController {

    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping
    public ResponseEntity<List<Exemplaire>> getAllExemplaires() {
        return ResponseEntity.ok(exemplaireService.getAllExemplaires());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exemplaire> getExemplaireById(@PathVariable Long id) {
        return exemplaireService.getExemplaireById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/livre/{livreId}")
    public ResponseEntity<List<Exemplaire>> getByLivre(@PathVariable Long livreId) {
        return ResponseEntity.ok(exemplaireService.getByLivre(livreId));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Exemplaire>> getDisponibles() {
        return ResponseEntity.ok(exemplaireService.getDisponibles());
    }

    @GetMapping("/livre/{livreId}/disponibles")
    public ResponseEntity<List<Exemplaire>> getDisponiblesByLivre(@PathVariable Long livreId) {
        return ResponseEntity.ok(exemplaireService.getDisponiblesByLivre(livreId));
    }

    @PostMapping
    public ResponseEntity<?> createExemplaire(@RequestBody ExemplaireRequest request) {
        try {
            Exemplaire exemplaire = exemplaireService.createExemplaire(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(exemplaire);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExemplaire(@PathVariable Long id,
                                               @RequestBody ExemplaireRequest request) {
        try {
            return exemplaireService.updateExemplaire(id, request)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExemplaire(@PathVariable Long id) {
        if (exemplaireService.deleteExemplaire(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
