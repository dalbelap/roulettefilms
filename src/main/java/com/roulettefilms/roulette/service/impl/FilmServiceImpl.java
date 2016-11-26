package com.roulettefilms.roulette.service.impl;

import com.roulettefilms.roulette.service.FilmService;
import com.roulettefilms.roulette.domain.Film;
import com.roulettefilms.roulette.repository.FilmRepository;
import com.roulettefilms.roulette.repository.search.FilmSearchRepository;
import com.roulettefilms.roulette.service.dto.FilmDTO;
import com.roulettefilms.roulette.service.mapper.FilmMapper;
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
 * Service Implementation for managing Film.
 */
@Service
@Transactional
public class FilmServiceImpl implements FilmService{

    private final Logger log = LoggerFactory.getLogger(FilmServiceImpl.class);
    
    @Inject
    private FilmRepository filmRepository;

    @Inject
    private FilmMapper filmMapper;

    @Inject
    private FilmSearchRepository filmSearchRepository;

    /**
     * Save a film.
     *
     * @param filmDTO the entity to save
     * @return the persisted entity
     */
    public FilmDTO save(FilmDTO filmDTO) {
        log.debug("Request to save Film : {}", filmDTO);
        Film film = filmMapper.filmDTOToFilm(filmDTO);
        film = filmRepository.save(film);
        FilmDTO result = filmMapper.filmToFilmDTO(film);
        filmSearchRepository.save(film);
        return result;
    }

    /**
     *  Get all the films.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<FilmDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Films");
        Page<Film> result = filmRepository.findAll(pageable);
        return result.map(film -> filmMapper.filmToFilmDTO(film));
    }

    /**
     *  Get one film by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FilmDTO findOne(Long id) {
        log.debug("Request to get Film : {}", id);
        Film film = filmRepository.findOneWithEagerRelationships(id);
        FilmDTO filmDTO = filmMapper.filmToFilmDTO(film);
        return filmDTO;
    }

    /**
     *  Delete the  film by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Film : {}", id);
        filmRepository.delete(id);
        filmSearchRepository.delete(id);
    }

    /**
     * Search for the film corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FilmDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Films for query {}", query);
        Page<Film> result = filmSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(film -> filmMapper.filmToFilmDTO(film));
    }
}
