package com.roulettefilms.roulette.service;

import com.roulettefilms.roulette.service.dto.FavouriteFilmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing FavouriteFilm.
 */
public interface FavouriteFilmService {

    /**
     * Save a favouriteFilm.
     *
     * @param favouriteFilmDTO the entity to save
     * @return the persisted entity
     */
    FavouriteFilmDTO save(FavouriteFilmDTO favouriteFilmDTO);

    /**
     *  Get all the favouriteFilms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FavouriteFilmDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" favouriteFilm.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FavouriteFilmDTO findOne(Long id);

    /**
     *  Delete the "id" favouriteFilm.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the favouriteFilm corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FavouriteFilmDTO> search(String query, Pageable pageable);
}
