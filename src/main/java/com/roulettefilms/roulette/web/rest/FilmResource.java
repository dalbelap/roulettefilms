package com.roulettefilms.roulette.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.roulettefilms.roulette.service.FilmService;
import com.roulettefilms.roulette.web.rest.util.HeaderUtil;
import com.roulettefilms.roulette.web.rest.util.PaginationUtil;
import com.roulettefilms.roulette.service.dto.FilmDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Film.
 */
@RestController
@RequestMapping("/api")
public class FilmResource {

    private final Logger log = LoggerFactory.getLogger(FilmResource.class);
        
    @Inject
    private FilmService filmService;

    /**
     * POST  /films : Create a new film.
     *
     * @param filmDTO the filmDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filmDTO, or with status 400 (Bad Request) if the film has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/films")
    @Timed
    public ResponseEntity<FilmDTO> createFilm(@Valid @RequestBody FilmDTO filmDTO) throws URISyntaxException {
        log.debug("REST request to save Film : {}", filmDTO);
        if (filmDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("film", "idexists", "A new film cannot already have an ID")).body(null);
        }
        FilmDTO result = filmService.save(filmDTO);
        return ResponseEntity.created(new URI("/api/films/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("film", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /films : Updates an existing film.
     *
     * @param filmDTO the filmDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filmDTO,
     * or with status 400 (Bad Request) if the filmDTO is not valid,
     * or with status 500 (Internal Server Error) if the filmDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/films")
    @Timed
    public ResponseEntity<FilmDTO> updateFilm(@Valid @RequestBody FilmDTO filmDTO) throws URISyntaxException {
        log.debug("REST request to update Film : {}", filmDTO);
        if (filmDTO.getId() == null) {
            return createFilm(filmDTO);
        }
        FilmDTO result = filmService.save(filmDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("film", filmDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /films : get all the films.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of films in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/films")
    @Timed
    public ResponseEntity<List<FilmDTO>> getAllFilms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Films");
        Page<FilmDTO> page = filmService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/films");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /films/:id : get the "id" film.
     *
     * @param id the id of the filmDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filmDTO, or with status 404 (Not Found)
     */
    @GetMapping("/films/{id}")
    @Timed
    public ResponseEntity<FilmDTO> getFilm(@PathVariable Long id) {
        log.debug("REST request to get Film : {}", id);
        FilmDTO filmDTO = filmService.findOne(id);
        return Optional.ofNullable(filmDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /films/:id : delete the "id" film.
     *
     * @param id the id of the filmDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/films/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        log.debug("REST request to delete Film : {}", id);
        filmService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("film", id.toString())).build();
    }

    /**
     * SEARCH  /_search/films?query=:query : search for the film corresponding
     * to the query.
     *
     * @param query the query of the film search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/films")
    @Timed
    public ResponseEntity<List<FilmDTO>> searchFilms(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Films for query {}", query);
        Page<FilmDTO> page = filmService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/films");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
