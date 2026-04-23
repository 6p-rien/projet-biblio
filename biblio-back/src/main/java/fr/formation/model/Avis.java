package fr.formation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Avis {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private int note;

  @Column(length = 35, nullable = false)
  private String commentaire;

  @Column(nullable = false)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  @ManyToOne private Livre livre;

  public int getId() {
    return id;
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

  public Avis(int note, String commentaire, LocalDate date, Livre livre) {
    this.note = note;
    this.commentaire = commentaire;
    this.date = date;
    this.livre = livre;
  }

  @Override
  public String toString() {
    return "Avis [id="
        + id
        + ", note="
        + note
        + ", commentaire="
        + commentaire
        + ", date="
        + date
        + ", livre="
        + livre
        + "]";
  }
}
