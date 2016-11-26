package com.roulettefilms.roulette.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.roulettefilms.roulette.service.FavouriteFilmService;
import com.roulettefilms.roulette.web.rest.util.HeaderUtil;
import com.roulettefilms.roulette.web.rest.util.PaginationUtil;
import com.roulettefilms.roulette.service.dto.FavouriteFilmDTO;
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
 * REST controller for managing FavouriteFilm.
 */
@RestController
@RequestMapping("/api")
public class FavouriteFilmResource {

    private final Logger log = LoggerFactory.getLogger(FavouriteFilmResource.class);
        
    @Inject
    private FavouriteFilmService favouriteFilmService;

    /**
     * POST  /favourite-films : Create a new favouriteFilm.
     *
     * @param favouriteFilmDTO the favouriteFilmDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new favouriteFilmDTO, or with status 400 (Bad Request) if the favouriteFilm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/favourite-films")
    @Timed
    public ResponseEntity<FavouriteFilmDTO> createFavouriteFilm(@Valid @RequestBody FavouriteFilmDTO favouriteFilmDTO) throws URISyntaxException {
        log.debug("REST request to save FavouriteFilm : {}", favouriteFilmDTO);
        if (favouriteFilmDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("favouriteFilm", "idexists", "A new favouriteFilm cannot already have an ID")).body(null);
        }
        FavouriteFilmDTO result = favouriteFilmService.save(favouriteFilmDTO);
        return ResponseEntity.created(new URI("/api/favourite-films/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("favouriteFilm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /favourite-films : Updates an existing favouriteFilm.
     *
     * @param favouriteFilmDTO the favouriteFilmDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated favouriteFilmDTO,
     * or with status 400 (Bad Request) if the favouriteFilmDTO is not valid,
     * or with status 500 (Internal Server Error) if the favouriteFilmDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/favourite-films")
    @Timed
    public ResponseEntity<FavouriteFilmDTO> updateFavouriteFilm(@Valid @RequestBody FavouriteFilmDTO favouriteFilmDTO) throws URISyntaxException {
        log.debug("REST request to update FavouriteFilm : {}", favouriteFilmDTO);
        if (favouriteFilmDTO.getId() == null) {
            return createFavouriteFilm(favouriteFilmDTO);
        }
        FavouriteFilmDTO result = favouriteFilmService.save(favouriteFilmDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("favouriteFilm", favouriteFilmDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /favourite-films : get all the favouriteFilms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of favouriteFilms in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/favourite-films")
    @Timed
    public ResponseEntity<List<FavouriteFilmDTO>> getAllFavouriteFilms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FavouriteFilms");
        Page<FavouriteFilmDTO> page = favouriteFilmService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/favourite-films");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /favourite-films/:id : get the "id" favouriteFilm.
     *
     * @param id the id of the favouriteFilmDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the favouriteFilmDTO, or with status 404 (Not Found)
     */
    @GetMapping("/favourite-films/{id}")
    @Timed
    public ResponseEntity<FavouriteFilmDTO> getFavouriteFilm(@PathVariable Long id) {
        log.debug("REST request to get FavouriteFilm : {}", id);
        FavouriteFilmDTO favouriteFilmDTO = favouriteFilmService.findOne(id);
        return Optional.ofNullable(favouriteFilmDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /favourite-films/:id : delete the "id" favouriteFilm.
     *
     * @param id the id of the favouriteFilmDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/favourite-films/{id}")
    @Timed
    public ResponseEntity<Void> deleteFavouriteFilm(@PathVariable Long id) {
        log.debug("REST request to delete FavouriteFilm : {}", id);
        favouriteFilmService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("favouriteFilm", id.toString())).build();
    }

    /**
     * SEARCH  /_search/favourite-films?query=:query : search for the favouriteFilm corresponding
     * to the query.
     *
     * @param query the query of the favouriteFilm search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/favourite-films")
    @Timed
    public ResponseEntity<List<FavouriteFilmDTO>> searchFavouriteFilms(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of FavouriteFilms for query {}", query);
        Page<FavouriteFilmDTO> page = favouriteFilmService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/favourite-films");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
