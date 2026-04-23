package fr.formation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Livre {
  @Id private int id;
}
