package com.roulettefilms.roulette.service.impl;

import com.roulettefilms.roulette.service.FavouriteFilmService;
import com.roulettefilms.roulette.domain.FavouriteFilm;
import com.roulettefilms.roulette.repository.FavouriteFilmRepository;
import com.roulettefilms.roulette.repository.search.FavouriteFilmSearchRepository;
import com.roulettefilms.roulette.service.dto.FavouriteFilmDTO;
import com.roulettefilms.roulette.service.mapper.FavouriteFilmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FavouriteFilm.
 */
@Service
@Transactional
public class FavouriteFilmServiceImpl implements FavouriteFilmService{

    private final Logger log = LoggerFactory.getLogger(FavouriteFilmServiceImpl.class);
    
    @Inject
    private FavouriteFilmRepository favouriteFilmRepository;

    @Inject
    private FavouriteFilmMapper favouriteFilmMapper;

    @Inject
    private FavouriteFilmSearchRepository favouriteFilmSearchRepository;

    /**
     * Save a favouriteFilm.
     *
     * @param favouriteFilmDTO the entity to save
     * @return the persisted entity
     */
    public FavouriteFilmDTO save(FavouriteFilmDTO favouriteFilmDTO) {
        log.debug("Request to save FavouriteFilm : {}", favouriteFilmDTO);
        FavouriteFilm favouriteFilm = favouriteFilmMapper.favouriteFilmDTOToFavouriteFilm(favouriteFilmDTO);
        favouriteFilm = favouriteFilmRepository.save(favouriteFilm);
        FavouriteFilmDTO result = favouriteFilmMapper.favouriteFilmToFavouriteFilmDTO(favouriteFilm);
        favouriteFilmSearchRepository.save(favouriteFilm);
        return result;
    }

    /**
     *  Get all the favouriteFilms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<FavouriteFilmDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FavouriteFilms");
        Page<FavouriteFilm> result = favouriteFilmRepository.findAll(pageable);
        return result.map(favouriteFilm -> favouriteFilmMapper.favouriteFilmToFavouriteFilmDTO(favouriteFilm));
    }

    /**
     *  Get one favouriteFilm by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FavouriteFilmDTO findOne(Long id) {
        log.debug("Request to get FavouriteFilm : {}", id);
        FavouriteFilm favouriteFilm = favouriteFilmRepository.findOne(id);
        FavouriteFilmDTO favouriteFilmDTO = favouriteFilmMapper.favouriteFilmToFavouriteFilmDTO(favouriteFilm);
        return favouriteFilmDTO;
    }

    /**
     *  Delete the  favouriteFilm by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FavouriteFilm : {}", id);
        favouriteFilmRepository.delete(id);
        favouriteFilmSearchRepository.delete(id);
    }

    /**
     * Search for the favouriteFilm corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FavouriteFilmDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FavouriteFilms for query {}", query);
        Page<FavouriteFilm> result = favouriteFilmSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(favouriteFilm -> favouriteFilmMapper.favouriteFilmToFavouriteFilmDTO(favouriteFilm));
    }
}
