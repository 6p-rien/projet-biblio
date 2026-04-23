package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Editeur;

public interface DAOEditeur extends JpaRepository<Editeur, Integer> {

}
