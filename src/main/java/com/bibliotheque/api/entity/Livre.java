package com.bibliotheque.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "livres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Auteur auteur;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Exemplaire> exemplaires;

    public enum Categorie {
        ROMAN, NOUVELLE, POESIE, MANGA, ROMANCE, COMEDIE
    }
}
