package fr.formation.api;

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
import fr.formation.dto.request.CreateOrUpdateAuteurRequest;
import fr.formation.dto.response.AuteurResponse;
import fr.formation.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.model.Auteur;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auteur")
@PreAuthorize("hasRole('ADMIN')")
public class AuteurApiController {
    private static final Logger log = LoggerFactory.getLogger(AuteurApiController.class);

    private final IDAOAuteur dao;

    public AuteurApiController(IDAOAuteur dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<AuteurResponse> findAll() {
        log.debug("Liste des auteurs ... ");

        return this.dao.findAll().stream().map(AuteurResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public AuteurResponse findById(@PathVariable Integer id) {
        log.debug("Auteur {} ...", id);

        return this.dao.findById(id).map(AuteurResponse::convert).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdateAuteurRequest request) {
        log.debug("Ajout d'un nouvel auteur ...");

        Auteur auteur = new Auteur();

        auteur.setNom(request.getNom());
        auteur.setPrenom(request.getPrenom());
        auteur.setNationalite(request.getNationalite());

        this.dao.save(auteur);

        log.debug("Auteur {} créé !", auteur.getId());

        return new EntityCreatedOrUpdatedResponse(auteur.getId());
    }

    @PutMapping("/{id}")
    public EntityCreatedOrUpdatedResponse update(@PathVariable Integer id,
            @Valid @RequestBody CreateOrUpdateAuteurRequest request) {
        log.debug("Modification de l'auteur {} ...", id);

        Auteur auteur = this.dao.findById(id).orElseThrow(EntityNotFoundException::new);

        auteur.setNom(request.getNom());
        auteur.setPrenom(request.getPrenom());
        auteur.setNationalite(request.getNationalite());

        this.dao.save(auteur);

        log.debug("Auteur {} modifié !", id);

        return new EntityCreatedOrUpdatedResponse(auteur.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        log.debug("Suppression de l'auteur {} ...", id);

        this.dao.deleteById(id);

        log.debug("Auteur {} supprimé !", id);
    }
}
