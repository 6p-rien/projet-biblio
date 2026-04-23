package fr.formation.dto.response;

import java.util.List;

import fr.formation.model.Livre;

public class LivreResponse {
    private Integer id;
    private String titre;
    private String resume;
    private int annee;
    private AuteurResponse auteur;
    private EditeurResponse editeur;
    private CollecResponse collection;
    private List<GenreResponse> genres;

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

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public AuteurResponse getAuteur() {
        return auteur;
    }

    public void setAuteur(AuteurResponse auteur) {
        this.auteur = auteur;
    }

    public EditeurResponse getEditeur() {
        return editeur;
    }

    public void setEditeur(EditeurResponse editeur) {
        this.editeur = editeur;
    }

    public CollecResponse getCollection() {
        return collection;
    }

    public void setCollection(CollecResponse collection) {
        this.collection = collection;
    }

    public List<GenreResponse> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreResponse> genres) {
        this.genres = genres;
    }

    public static LivreResponse convert(Livre livre) {
        LivreResponse response = new LivreResponse();

        response.setId(livre.getId());
        response.setTitre(livre.getTitre());
        response.setResume(livre.getResume());
        response.setAnnee(livre.getAnnee());
        response.setAuteur(livre.getAuteur() != null ? AuteurResponse.convert(livre.getAuteur()) : null);
        response.setEditeur(livre.getEditeur() != null ? EditeurResponse.convert(livre.getEditeur()) : null);
        response.setCollection(livre.getCollection() != null ? CollecResponse.convert(livre.getCollection()) : null);
        response.setGenres(livre.getGenres().stream().map(GenreResponse::convert).toList());

        return response;
    }
}
