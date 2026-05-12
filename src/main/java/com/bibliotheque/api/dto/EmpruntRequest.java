package com.bibliotheque.api.dto;

import java.time.LocalDate;

public class EmpruntRequest {
    private Long utilisateurId;
    private Long exemplaireId;
    private LocalDate dateRetourPrevue;

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    public Long getExemplaireId() { return exemplaireId; }
    public void setExemplaireId(Long exemplaireId) { this.exemplaireId = exemplaireId; }

    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) { this.dateRetourPrevue = dateRetourPrevue; }
}
