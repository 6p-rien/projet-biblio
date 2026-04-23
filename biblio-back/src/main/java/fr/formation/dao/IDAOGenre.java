package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.formation.model.Genre;

public interface IDAOGenre extends JpaRepository<Genre, Integer>{

}
