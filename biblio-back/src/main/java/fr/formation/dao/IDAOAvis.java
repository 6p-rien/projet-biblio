package fr.formation.dao;

import fr.formation.model.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDAOAvis extends JpaRepository<Avis, Integer> {}
