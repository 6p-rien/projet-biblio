package fr.formation.dto.response;

import fr.formation.model.Genre;

public class GenreResponse {
    private Integer id;
    private String libelle;

    public Integer getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static GenreResponse convert(Genre genre) {
        GenreResponse response = new GenreResponse();
        response.setId(genre.getId());
        response.setLibelle(genre.getLibelle());
        return response;
    }
}
