package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Editeur;

public interface IDAOEditeur extends JpaRepository<Editeur, Integer> {

}
