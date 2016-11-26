package com.roulettefilms.roulette.service;

import com.roulettefilms.roulette.service.dto.FilmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Film.
 */
public interface FilmService {

    /**
     * Save a film.
     *
     * @param filmDTO the entity to save
     * @return the persisted entity
     */
    FilmDTO save(FilmDTO filmDTO);

    /**
     *  Get all the films.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FilmDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" film.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FilmDTO findOne(Long id);

    /**
     *  Delete the "id" film.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the film corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FilmDTO> search(String query, Pageable pageable);
}
