package fr.formation.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateOrUpdateGenreRequest {

    @NotBlank
    private String libelle;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
