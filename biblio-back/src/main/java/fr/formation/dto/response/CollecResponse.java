package fr.formation.dto.response;

import fr.formation.model.Collec;

public class CollecResponse {
    private int id;
    private String nom;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public static CollecResponse convert(Collec collec) {
        CollecResponse response = new CollecResponse();

        response.setId(collec.getId());
        response.setNom(collec.getLibelle());

        return response;
    }
}
