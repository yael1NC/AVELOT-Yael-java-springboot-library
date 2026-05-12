package com.bibliotheque.api.dto;

import com.bibliotheque.api.entity.Exemplaire;

public class ExemplaireRequest {
    private Long livreId;
    private Exemplaire.Etat etat;

    public Long getLivreId() { return livreId; }
    public void setLivreId(Long livreId) { this.livreId = livreId; }

    public Exemplaire.Etat getEtat() { return etat; }
    public void setEtat(Exemplaire.Etat etat) { this.etat = etat; }
}
