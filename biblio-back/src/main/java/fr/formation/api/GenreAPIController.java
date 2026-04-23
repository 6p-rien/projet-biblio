package fr.formation.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.dao.IDAOGenre;
import fr.formation.dto.request.CreateOrUpdateGenreRequest;
import fr.formation.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.dto.response.GenreResponse;
import fr.formation.model.Genre;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/genre")
public class GenreAPIController {
	private static final Logger log = LoggerFactory.getLogger(GenreAPIController.class);

    private final IDAOGenre dao;

    public GenreAPIController(IDAOGenre dao) {
        this.dao = dao;
    }

    public List<GenreResponse> findAll() {
        log.debug("Liste des genres ... ");

        return this.dao.findAll().stream().map(GenreResponse::convert).toList();
    }

     @GetMapping("/{id}")
    public GenreResponse findById(@PathVariable Integer id) {
        log.debug("Genre {} ...", id);

        return this.dao.findById(id).map(GenreResponse::convert).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdateGenreRequest request) {
        log.debug("Ajout d'un nouveau genre ...");

        Genre genre = new Genre();

        genre.setLibelle(request.getLibelle());
        this.dao.save(genre);

        log.debug("Genre {} créé !", genre.getId());

        return new EntityCreatedOrUpdatedResponse(genre.getId());
    }

    @PutMapping("/{id}")
    public EntityCreatedOrUpdatedResponse update(@PathVariable Integer id, @Valid @RequestBody CreateOrUpdateGenreRequest request) {
        log.debug("Modification d'un genre {} ...", id);

        Genre genre = this.dao.findById(id).orElseThrow(EntityNotFoundException::new);

        genre.setLibelle(request.getLibelle());
        
        this.dao.save(genre);

        log.debug("Genre {} modifié !", id);

        return new EntityCreatedOrUpdatedResponse(genre.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        log.debug("Suppression d'un genre {} ...", id);

        this.dao.deleteById(id);

        log.debug("Genre {} supprimé !", id);
    }

}
