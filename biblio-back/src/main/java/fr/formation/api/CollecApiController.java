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

import fr.formation.dao.IDAOCollec;
import fr.formation.dto.request.CreateOrUpdateCollecRequest;
import fr.formation.dto.response.CollecResponse;
import fr.formation.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.exception.CollecNotFoundException;
import fr.formation.model.Collec;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/collec")
public class CollecApiController {
    private static final Logger log = LoggerFactory.getLogger(CollecApiController.class);

    private final IDAOCollec dao;

    public CollecApiController(IDAOCollec dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<CollecResponse> findAll() {
        log.debug("collec appel de l'api findAll ...");

        return this.dao.findAll().stream().map(CollecResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public CollecResponse findById(@PathVariable Integer id) {
        log.debug("Collec appel de l'api findById : {} ...", id);

        return this.dao.findById(id).map(CollecResponse::convert).orElseThrow(CollecNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdateCollecRequest request) {
        log.debug("Collec appel de l'api Create ...");

        Collec collec = new Collec();

        collec.setNom(request.getNom());

        this.dao.save(collec);

        log.debug("Collec appel de l'api Create : {} créée !", collec.getId());

        return new EntityCreatedOrUpdatedResponse(collec.getId());
    }

    @PutMapping("/{id}")
    public EntityCreatedOrUpdatedResponse update(@PathVariable Integer id,
            @Valid @RequestBody CreateOrUpdateCollecRequest request) {
        log.debug("Collec appel de l'api Modification : {} ...", id);

        Collec collec = this.dao.findById(id).orElseThrow(CollecNotFoundException::new);

        collec.setNom(request.getNom());

        this.dao.save(collec);

        log.debug("Collec appel de l'api Modification : {} modifiée !", id);

        return new EntityCreatedOrUpdatedResponse(collec.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        log.debug("Collec appel de l'api Suppression : {} ...", id);

        this.dao.deleteById(id);

        log.debug("Collec appel de l'api Suppression : {} supprimée !", id);
    }
}
