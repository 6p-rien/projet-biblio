package fr.formation.dto.request;

import fr.formation.model.Livre;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CreateOrUpdateAvisRequest {
  private int note;
  @NotBlank private String commentaire;
  private LocalDate date;
  private Livre livre;

  public int getNote() {
    return note;
  }

  public String getCommentaire() {
    return commentaire;
  }

  public LocalDate getDate() {
    return date;
  }

  public Livre getLivre() {
    return livre;
  }
}
