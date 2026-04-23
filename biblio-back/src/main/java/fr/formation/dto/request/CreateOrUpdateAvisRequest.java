package fr.formation.dto.request;

import fr.formation.model.Livre;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CreateOrUpdateAvisRequest {
  @NotBlank private int note;
  @NotBlank private String commentaire;
  @NotBlank private LocalDate date;
  @NotBlank private Livre livre;

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
