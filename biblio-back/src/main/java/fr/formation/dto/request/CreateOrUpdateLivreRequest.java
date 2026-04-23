package fr.formation.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateOrUpdateLivreRequest {

    @NotBlank
    private String titre;

    private String resume;

    @PositiveOrZero
    private int annee;

    @NotNull
    private Integer auteurId;

    @NotNull
    private Integer editeurId;

    private Integer collectionId;

    private List<Integer> genreIds;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resumer) {
        this.resume = resumer;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public Integer getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(Integer auteurId) {
        this.auteurId = auteurId;
    }

    public Integer getEditeurId() {
        return editeurId;
    }

    public void setEditeurId(Integer editeurId) {
        this.editeurId = editeurId;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }
}
