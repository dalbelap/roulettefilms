package com.roulettefilms.roulette.web.rest;

import com.roulettefilms.roulette.RoulettefilmsApp;

import com.roulettefilms.roulette.domain.Genre;
import com.roulettefilms.roulette.repository.GenreRepository;
import com.roulettefilms.roulette.service.GenreService;
import com.roulettefilms.roulette.repository.search.GenreSearchRepository;
import com.roulettefilms.roulette.service.dto.GenreDTO;
import com.roulettefilms.roulette.service.mapper.GenreMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GenreResource REST controller.
 *
 * @see GenreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoulettefilmsApp.class)
public class GenreResourceIntTest {

    private static final String DEFAULT_GENRE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GENRE_NAME = "BBBBBBBBBB";

    @Inject
    private GenreRepository genreRepository;

    @Inject
    private GenreMapper genreMapper;

    @Inject
    private GenreService genreService;

    @Inject
    private GenreSearchRepository genreSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGenreMockMvc;

    private Genre genre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GenreResource genreResource = new GenreResource();
        ReflectionTestUtils.setField(genreResource, "genreService", genreService);
        this.restGenreMockMvc = MockMvcBuilders.standaloneSetup(genreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genre createEntity(EntityManager em) {
        Genre genre = new Genre()
                .genreName(DEFAULT_GENRE_NAME);
        return genre;
    }

    @Before
    public void initTest() {
        genreSearchRepository.deleteAll();
        genre = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenre() throws Exception {
        int databaseSizeBeforeCreate = genreRepository.findAll().size();

        // Create the Genre
        GenreDTO genreDTO = genreMapper.genreToGenreDTO(genre);

        restGenreMockMvc.perform(post("/api/genres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(genreDTO)))
                .andExpect(status().isCreated());

        // Validate the Genre in the database
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(databaseSizeBeforeCreate + 1);
        Genre testGenre = genres.get(genres.size() - 1);
        assertThat(testGenre.getGenreName()).isEqualTo(DEFAULT_GENRE_NAME);

        // Validate the Genre in ElasticSearch
        Genre genreEs = genreSearchRepository.findOne(testGenre.getId());
        assertThat(genreEs).isEqualToComparingFieldByField(testGenre);
    }

    @Test
    @Transactional
    public void checkGenreNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = genreRepository.findAll().size();
        // set the field null
        genre.setGenreName(null);

        // Create the Genre, which fails.
        GenreDTO genreDTO = genreMapper.genreToGenreDTO(genre);

        restGenreMockMvc.perform(post("/api/genres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(genreDTO)))
                .andExpect(status().isBadRequest());

        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenres() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        // Get all the genres
        restGenreMockMvc.perform(get("/api/genres?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(genre.getId().intValue())))
                .andExpect(jsonPath("$.[*].genreName").value(hasItem(DEFAULT_GENRE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);

        // Get the genre
        restGenreMockMvc.perform(get("/api/genres/{id}", genre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(genre.getId().intValue()))
            .andExpect(jsonPath("$.genreName").value(DEFAULT_GENRE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGenre() throws Exception {
        // Get the genre
        restGenreMockMvc.perform(get("/api/genres/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);
        genreSearchRepository.save(genre);
        int databaseSizeBeforeUpdate = genreRepository.findAll().size();

        // Update the genre
        Genre updatedGenre = genreRepository.findOne(genre.getId());
        updatedGenre
                .genreName(UPDATED_GENRE_NAME);
        GenreDTO genreDTO = genreMapper.genreToGenreDTO(updatedGenre);

        restGenreMockMvc.perform(put("/api/genres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(genreDTO)))
                .andExpect(status().isOk());

        // Validate the Genre in the database
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(databaseSizeBeforeUpdate);
        Genre testGenre = genres.get(genres.size() - 1);
        assertThat(testGenre.getGenreName()).isEqualTo(UPDATED_GENRE_NAME);

        // Validate the Genre in ElasticSearch
        Genre genreEs = genreSearchRepository.findOne(testGenre.getId());
        assertThat(genreEs).isEqualToComparingFieldByField(testGenre);
    }

    @Test
    @Transactional
    public void deleteGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);
        genreSearchRepository.save(genre);
        int databaseSizeBeforeDelete = genreRepository.findAll().size();

        // Get the genre
        restGenreMockMvc.perform(delete("/api/genres/{id}", genre.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean genreExistsInEs = genreSearchRepository.exists(genre.getId());
        assertThat(genreExistsInEs).isFalse();

        // Validate the database is empty
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGenre() throws Exception {
        // Initialize the database
        genreRepository.saveAndFlush(genre);
        genreSearchRepository.save(genre);

        // Search the genre
        restGenreMockMvc.perform(get("/api/_search/genres?query=id:" + genre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genre.getId().intValue())))
            .andExpect(jsonPath("$.[*].genreName").value(hasItem(DEFAULT_GENRE_NAME.toString())));
    }
}
