package fr.formation.dto.response;

import java.util.List;

import fr.formation.model.Livre;

public class LivreResponse {
    private Integer id;
    private String titre;
    private String resumer;
    private int annee;
    private Integer auteurId;
    private Integer editeurId;
    private Integer collectionId;
    private List<Integer> genreIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResumer() {
        return resumer;
    }

    public void setResumer(String resumer) {
        this.resumer = resumer;
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

    public static LivreResponse convert(Livre livre) {
        LivreResponse response = new LivreResponse();

        response.setId(livre.getId());
        response.setTitre(livre.getTitre());
        response.setResumer(livre.getResumer());
        response.setAnnee(livre.getAnnee());
        response.setAuteurId(livre.getAuteur() != null ? livre.getAuteur().getId() : null);
        response.setEditeurId(livre.getEditeur() != null ? livre.getEditeur().getId() : null);
        response.setCollectionId(livre.getCollection() != null ? livre.getCollection().getId() : null);
        response.setGenreIds(livre.getGenres().stream().map(g -> g.getId()).toList());

        return response;
    }
}
