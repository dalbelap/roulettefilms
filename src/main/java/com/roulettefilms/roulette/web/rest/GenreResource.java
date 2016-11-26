package com.roulettefilms.roulette.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.roulettefilms.roulette.service.GenreService;
import com.roulettefilms.roulette.web.rest.util.HeaderUtil;
import com.roulettefilms.roulette.web.rest.util.PaginationUtil;
import com.roulettefilms.roulette.service.dto.GenreDTO;
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
 * REST controller for managing Genre.
 */
@RestController
@RequestMapping("/api")
public class GenreResource {

    private final Logger log = LoggerFactory.getLogger(GenreResource.class);
        
    @Inject
    private GenreService genreService;

    /**
     * POST  /genres : Create a new genre.
     *
     * @param genreDTO the genreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genreDTO, or with status 400 (Bad Request) if the genre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/genres")
    @Timed
    public ResponseEntity<GenreDTO> createGenre(@Valid @RequestBody GenreDTO genreDTO) throws URISyntaxException {
        log.debug("REST request to save Genre : {}", genreDTO);
        if (genreDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("genre", "idexists", "A new genre cannot already have an ID")).body(null);
        }
        GenreDTO result = genreService.save(genreDTO);
        return ResponseEntity.created(new URI("/api/genres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("genre", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /genres : Updates an existing genre.
     *
     * @param genreDTO the genreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genreDTO,
     * or with status 400 (Bad Request) if the genreDTO is not valid,
     * or with status 500 (Internal Server Error) if the genreDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/genres")
    @Timed
    public ResponseEntity<GenreDTO> updateGenre(@Valid @RequestBody GenreDTO genreDTO) throws URISyntaxException {
        log.debug("REST request to update Genre : {}", genreDTO);
        if (genreDTO.getId() == null) {
            return createGenre(genreDTO);
        }
        GenreDTO result = genreService.save(genreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("genre", genreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /genres : get all the genres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of genres in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/genres")
    @Timed
    public ResponseEntity<List<GenreDTO>> getAllGenres(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Genres");
        Page<GenreDTO> page = genreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/genres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /genres/:id : get the "id" genre.
     *
     * @param id the id of the genreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/genres/{id}")
    @Timed
    public ResponseEntity<GenreDTO> getGenre(@PathVariable Long id) {
        log.debug("REST request to get Genre : {}", id);
        GenreDTO genreDTO = genreService.findOne(id);
        return Optional.ofNullable(genreDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /genres/:id : delete the "id" genre.
     *
     * @param id the id of the genreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/genres/{id}")
    @Timed
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        log.debug("REST request to delete Genre : {}", id);
        genreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("genre", id.toString())).build();
    }

    /**
     * SEARCH  /_search/genres?query=:query : search for the genre corresponding
     * to the query.
     *
     * @param query the query of the genre search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/genres")
    @Timed
    public ResponseEntity<List<GenreDTO>> searchGenres(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Genres for query {}", query);
        Page<GenreDTO> page = genreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/genres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
