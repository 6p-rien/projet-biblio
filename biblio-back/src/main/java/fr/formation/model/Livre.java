package fr.formation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Livre {
  @Id private int id;

  public Livre() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
