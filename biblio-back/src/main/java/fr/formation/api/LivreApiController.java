package fr.formation.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.dao.IDAOAuteur;
import fr.formation.dao.IDAOCollec;
import fr.formation.dao.IDAOEditeur;
import fr.formation.dao.IDAOGenre;
import fr.formation.dao.IDAOLivre;
import fr.formation.dto.request.CreateOrUpdateLivreRequest;
import fr.formation.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.dto.response.LivreResponse;
import fr.formation.model.Genre;
import fr.formation.model.Livre;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livre")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class LivreApiController {
    private static final Logger log = LoggerFactory.getLogger(LivreApiController.class);

    private final IDAOLivre dao;
    private final IDAOAuteur auteurDao;
    private final IDAOEditeur editeurDao;
    private final IDAOCollec collecDao;
    private final IDAOGenre genreDao;

    public LivreApiController(IDAOLivre dao, IDAOAuteur auteurDao, IDAOEditeur editeurDao, IDAOCollec collecDao,
            IDAOGenre genreDao) {
        this.dao = dao;
        this.auteurDao = auteurDao;
        this.editeurDao = editeurDao;
        this.collecDao = collecDao;
        this.genreDao = genreDao;
    }

    @GetMapping
    public List<LivreResponse> findAll() {
        log.debug("Liste des livres ... ");

        return this.dao.findAll().stream().map(LivreResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public LivreResponse findById(@PathVariable Integer id) {
        log.debug("Livre {} ...", id);

        return this.dao.findById(id).map(LivreResponse::convert).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdateLivreRequest request) {
        log.debug("Ajout d'un nouveau livre ...");

        Livre livre = new Livre();
        this.fillFromRequest(livre, request);

        this.dao.save(livre);

        log.debug("Livre {} créé !", livre.getId());

        return new EntityCreatedOrUpdatedResponse(livre.getId());
    }

    @PutMapping("/{id}")
    public EntityCreatedOrUpdatedResponse update(@PathVariable Integer id,
            @Valid @RequestBody CreateOrUpdateLivreRequest request) {
        log.debug("Modification du livre {} ...", id);

        Livre livre = this.dao.findById(id).orElseThrow(EntityNotFoundException::new);
        this.fillFromRequest(livre, request);

        this.dao.save(livre);

        log.debug("Livre {} modifié !", id);

        return new EntityCreatedOrUpdatedResponse(livre.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        log.debug("Suppression du livre {} ...", id);

        this.dao.deleteById(id);

        log.debug("Livre {} supprimé !", id);
    }

    private void fillFromRequest(Livre livre, CreateOrUpdateLivreRequest request) {
        livre.setTitre(request.getTitre());
        livre.setResume(request.getResume());
        livre.setAnnee(request.getAnnee());
        livre.setAuteur(this.auteurDao.findById(request.getAuteurId()).orElseThrow(EntityNotFoundException::new));
        livre.setEditeur(this.editeurDao.findById(request.getEditeurId()).orElseThrow(EntityNotFoundException::new));

        if (request.getCollectionId() != null) {
            livre.setCollection(this.collecDao.findById(request.getCollectionId()).orElseThrow(EntityNotFoundException::new));
        } else {
            livre.setCollection(null);
        }

        List<Genre> genres = new ArrayList<>();
        if (request.getGenreIds() != null) {
            for (Integer genreId : request.getGenreIds()) {
                genres.add(this.genreDao.findById(genreId).orElseThrow(EntityNotFoundException::new));
            }
        }
        livre.setGenres(genres);
    }
}
