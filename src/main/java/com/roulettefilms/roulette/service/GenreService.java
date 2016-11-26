package com.roulettefilms.roulette.service;

import com.roulettefilms.roulette.service.dto.GenreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Genre.
 */
public interface GenreService {

    /**
     * Save a genre.
     *
     * @param genreDTO the entity to save
     * @return the persisted entity
     */
    GenreDTO save(GenreDTO genreDTO);

    /**
     *  Get all the genres.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<GenreDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" genre.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GenreDTO findOne(Long id);

    /**
     *  Delete the "id" genre.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the genre corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<GenreDTO> search(String query, Pageable pageable);
}
