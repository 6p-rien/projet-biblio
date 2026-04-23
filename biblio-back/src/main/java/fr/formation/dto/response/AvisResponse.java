package fr.formation.dto.response;

import fr.formation.model.Avis;
import fr.formation.model.Livre;
import java.time.LocalDate;

public class AvisResponse {
  private int id;
  private int note;
  private String commentaire;
  private LocalDate date;
  private Livre livre;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getNote() {
    return note;
  }

  public void setNote(int note) {
    this.note = note;
  }

  public String getCommentaire() {
    return commentaire;
  }

  public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Livre getLivre() {
    return livre;
  }

  public void setLivre(Livre livre) {
    this.livre = livre;
  }

  public static AvisResponse convert(Avis avis) {
    AvisResponse response = new AvisResponse();
    response.setId(avis.getId());
    response.setNote(avis.getNote());
    response.setCommentaire(avis.getCommentaire());
    response.setDate(avis.getDate());
    response.setLivre(avis.getLivre());
    return response;
  }
}
