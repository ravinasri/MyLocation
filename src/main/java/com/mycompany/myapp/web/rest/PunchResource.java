package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Punch;
import com.mycompany.myapp.repository.PunchRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Punch}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PunchResource {

    private final Logger log = LoggerFactory.getLogger(PunchResource.class);

    private static final String ENTITY_NAME = "punch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PunchRepository punchRepository;

    public PunchResource(PunchRepository punchRepository) {
        this.punchRepository = punchRepository;
    }

    /**
     * {@code POST  /punches} : Create a new punch.
     *
     * @param punch the punch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new punch, or with status {@code 400 (Bad Request)} if the punch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/punches")
    public ResponseEntity<Punch> createPunch(@RequestBody Punch punch) throws URISyntaxException {
        log.debug("REST request to save Punch : {}", punch);
        if (punch.getId() != null) {
            throw new BadRequestAlertException("A new punch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Punch result = punchRepository.save(punch);
        return ResponseEntity.created(new URI("/api/punches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /punches} : Updates an existing punch.
     *
     * @param punch the punch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated punch,
     * or with status {@code 400 (Bad Request)} if the punch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the punch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/punches")
    public ResponseEntity<Punch> updatePunch(@RequestBody Punch punch) throws URISyntaxException {
        log.debug("REST request to update Punch : {}", punch);
        if (punch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Punch result = punchRepository.save(punch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, punch.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /punches} : get all the punches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of punches in body.
     */
    @GetMapping("/punches")
    public List<Punch> getAllPunches() {
        log.debug("REST request to get all Punches");
        return punchRepository.findAll();
    }

    /**
     * {@code GET  /punches/:id} : get the "id" punch.
     *
     * @param id the id of the punch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the punch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/punches/{id}")
    public ResponseEntity<Punch> getPunch(@PathVariable Long id) {
        log.debug("REST request to get Punch : {}", id);
        Optional<Punch> punch = punchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(punch);
    }

    /**
     * {@code DELETE  /punches/:id} : delete the "id" punch.
     *
     * @param id the id of the punch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/punches/{id}")
    public ResponseEntity<Void> deletePunch(@PathVariable Long id) {
        log.debug("REST request to delete Punch : {}", id);
        punchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
