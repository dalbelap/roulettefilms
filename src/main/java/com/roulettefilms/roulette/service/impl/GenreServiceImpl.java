package com.roulettefilms.roulette.service.impl;

import com.roulettefilms.roulette.service.GenreService;
import com.roulettefilms.roulette.domain.Genre;
import com.roulettefilms.roulette.repository.GenreRepository;
import com.roulettefilms.roulette.repository.search.GenreSearchRepository;
import com.roulettefilms.roulette.service.dto.GenreDTO;
import com.roulettefilms.roulette.service.mapper.GenreMapper;
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
 * Service Implementation for managing Genre.
 */
@Service
@Transactional
public class GenreServiceImpl implements GenreService{

    private final Logger log = LoggerFactory.getLogger(GenreServiceImpl.class);
    
    @Inject
    private GenreRepository genreRepository;

    @Inject
    private GenreMapper genreMapper;

    @Inject
    private GenreSearchRepository genreSearchRepository;

    /**
     * Save a genre.
     *
     * @param genreDTO the entity to save
     * @return the persisted entity
     */
    public GenreDTO save(GenreDTO genreDTO) {
        log.debug("Request to save Genre : {}", genreDTO);
        Genre genre = genreMapper.genreDTOToGenre(genreDTO);
        genre = genreRepository.save(genre);
        GenreDTO result = genreMapper.genreToGenreDTO(genre);
        genreSearchRepository.save(genre);
        return result;
    }

    /**
     *  Get all the genres.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<GenreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Genres");
        Page<Genre> result = genreRepository.findAll(pageable);
        return result.map(genre -> genreMapper.genreToGenreDTO(genre));
    }

    /**
     *  Get one genre by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public GenreDTO findOne(Long id) {
        log.debug("Request to get Genre : {}", id);
        Genre genre = genreRepository.findOne(id);
        GenreDTO genreDTO = genreMapper.genreToGenreDTO(genre);
        return genreDTO;
    }

    /**
     *  Delete the  genre by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Genre : {}", id);
        genreRepository.delete(id);
        genreSearchRepository.delete(id);
    }

    /**
     * Search for the genre corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GenreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Genres for query {}", query);
        Page<Genre> result = genreSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(genre -> genreMapper.genreToGenreDTO(genre));
    }
}
