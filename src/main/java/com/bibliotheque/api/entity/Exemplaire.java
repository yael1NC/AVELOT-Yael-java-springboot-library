package com.bibliotheque.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "exemplaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Etat etat;

    @Column(nullable = false)
    private Boolean disponible = true;

    @OneToMany(mappedBy = "exemplaire", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Emprunt> emprunts;

    public enum Etat {
        NEUF, BON, ABIME, HORS_SERVICE
    }
}
