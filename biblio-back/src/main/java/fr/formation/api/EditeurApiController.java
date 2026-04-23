package fr.formation.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import fr.formation.dao.IDAOEditeur;
import fr.formation.dto.request.CreateOrUpdateEditeurRequest;
import fr.formation.dto.response.EditeurResponse;
import fr.formation.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.model.Editeur;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/editeur")
public class EditeurApiController {

    private static final Logger log = LoggerFactory.getLogger(EditeurApiController.class);

    private final IDAOEditeur dao;

    public EditeurApiController(IDAOEditeur dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<EditeurResponse> findAll() {
        log.debug("Liste des editeurs ...");

        return this.dao.findAll()
                .stream()
                .map(EditeurResponse::convert)
                .toList();
    }

    @GetMapping("/{id}")
    public EditeurResponse findById(@PathVariable Integer id) {
        log.debug("Editeur {} ...", id);

        return this.dao.findById(id)
                .map(EditeurResponse::convert)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdateEditeurRequest request) {
        log.debug("Ajout d'un nouvel editeur ...");

        Editeur editeur = new Editeur();

        editeur.setNom(request.getNom());
        editeur.setPays(request.getPays());

        this.dao.save(editeur);

        log.debug("Editeur {} créé !", editeur.getId());

        return new EntityCreatedOrUpdatedResponse(editeur.getId());
    }

    @PutMapping("/{id}")
    public EntityCreatedOrUpdatedResponse update(@PathVariable Integer id,
                                                 @Valid @RequestBody CreateOrUpdateEditeurRequest request) {

        log.debug("Modification de l'editeur {} ...", id);

        Editeur editeur = this.dao.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        editeur.setNom(request.getNom());
        editeur.setPays(request.getPays());

        this.dao.save(editeur);

        log.debug("Editeur {} modifié !", id);

        return new EntityCreatedOrUpdatedResponse(editeur.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {

        log.debug("Suppression de l'editeur {} ...", id);

        this.dao.deleteById(id);

        log.debug("Editeur {} supprimé !", id);
    }
}