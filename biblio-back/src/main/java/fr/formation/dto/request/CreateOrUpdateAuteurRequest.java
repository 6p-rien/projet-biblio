package fr.formation.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateOrUpdateAuteurRequest {

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    private String nationalite;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

}
