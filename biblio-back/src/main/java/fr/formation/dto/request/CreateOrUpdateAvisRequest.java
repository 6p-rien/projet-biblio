package fr.formation.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CreateOrUpdateAvisRequest {
  private int note;
  @NotBlank private String commentaire;
  private LocalDate date;
  private int idLivre;

  public int getNote() {
    return note;
  }

  public String getCommentaire() {
    return commentaire;
  }

  public LocalDate getDate() {
    return date;
  }

  public int getIdLivre() {
    return idLivre;
  }
}
