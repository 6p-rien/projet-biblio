package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Livre;

public interface IDAOLivre extends JpaRepository<Livre, Integer>{

}
