package com.bibliotheque.api.dto;

import com.bibliotheque.api.entity.Livre;

public class LivreRequest {
    private String titre;
    private String isbn;
    private Livre.Categorie categorie;
    private Long auteurId;

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Livre.Categorie getCategorie() { return categorie; }
    public void setCategorie(Livre.Categorie categorie) { this.categorie = categorie; }

    public Long getAuteurId() { return auteurId; }
    public void setAuteurId(Long auteurId) { this.auteurId = auteurId; }
}
