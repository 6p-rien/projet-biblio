package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Auteur;

public interface IDAOAuteur extends JpaRepository<Auteur, Integer> {

}
