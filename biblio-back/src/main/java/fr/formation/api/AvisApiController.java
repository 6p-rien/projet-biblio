package fr.formation.api;

import fr.formation.dao.IDAOAvis;
import fr.formation.dto.request.CreateOrUpdateAvisRequest;
import fr.formation.dto.response.AvisResponse;
import fr.formation.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.exception.AvisNotFoundException;
import fr.formation.model.Avis;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("api/avis")
public class AvisApiController {
  private static final Logger log = LoggerFactory.getLogger(AvisApiController.class);
  private final IDAOAvis dao;

  public AvisApiController(IDAOAvis dao) {
    this.dao = dao;
  }

  @GetMapping
  public List<AvisResponse> findAll() {
    log.debug("Liste des avis ...");
    return this.dao.findAll().stream().map(AvisResponse::convert).toList();
  }

  @GetMapping("/{id}")
  public AvisResponse findById(@PathVariable Integer id) {
    log.debug("Avis {} ...", id);
    return this.dao.findById(id).map(AvisResponse::convert).orElseThrow(AvisNotFoundException::new);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EntityCreatedOrUpdatedResponse create(
      @Valid @RequestBody CreateOrUpdateAvisRequest request) {
    log.debug("Creation d'un nouvel avis ...");
    Avis avis = new Avis();
    avis.setNote(request.getNote());
    avis.setCommentaire(request.getCommentaire());
    avis.setDate(request.getDate());
    avis.setLivre(request.getLivre());

    this.dao.save(avis);
    log.debug("Avis {} cree !", avis.getId());

    return new EntityCreatedOrUpdatedResponse(avis.getId());
  }

  @PutMapping("/{id}")
  public EntityCreatedOrUpdatedResponse update(
      @PathVariable Integer id, @Valid @RequestBody CreateOrUpdateAvisRequest request) {
    log.debug("Modification de avis {} ...", id);

    Avis avis = this.dao.findById(id).orElseThrow(AvisNotFoundException::new);
    avis.setNote(request.getNote());
    avis.setCommentaire(request.getCommentaire());
    avis.setDate(request.getDate());
    avis.setLivre(request.getLivre());

    this.dao.save(avis);
    log.debug("Avis {} modifie !", id);

    return new EntityCreatedOrUpdatedResponse(avis.getId());
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Integer id) {
    log.debug("Suppression de l'avis {} ...", id);

    this.dao.deleteById(id);

    log.debug("Avis {} supprime !", id);
  }
}
