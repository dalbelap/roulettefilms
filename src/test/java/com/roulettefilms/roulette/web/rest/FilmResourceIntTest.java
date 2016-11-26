package com.roulettefilms.roulette.web.rest;

import com.roulettefilms.roulette.RoulettefilmsApp;

import com.roulettefilms.roulette.domain.Film;
import com.roulettefilms.roulette.repository.FilmRepository;
import com.roulettefilms.roulette.service.FilmService;
import com.roulettefilms.roulette.repository.search.FilmSearchRepository;
import com.roulettefilms.roulette.service.dto.FilmDTO;
import com.roulettefilms.roulette.service.mapper.FilmMapper;

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
 * Test class for the FilmResource REST controller.
 *
 * @see FilmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoulettefilmsApp.class)
public class FilmResourceIntTest {

    private static final String DEFAULT_FILM_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_FILM_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_FILM_YEAR = 1890;
    private static final Integer UPDATED_FILM_YEAR = 1891;

    private static final Integer DEFAULT_FILM_DURATION_IN_MINUTES = 0;
    private static final Integer UPDATED_FILM_DURATION_IN_MINUTES = 1;

    private static final String DEFAULT_FILM_SYNOPSIS = "AAAAAAAAAA";
    private static final String UPDATED_FILM_SYNOPSIS = "BBBBBBBBBB";

    private static final Integer DEFAULT_NETFLIX_RATING_CHART_NUMBER = 0;
    private static final Integer UPDATED_NETFLIX_RATING_CHART_NUMBER = 1;

    private static final String DEFAULT_FILM_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILM_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_FILM_CRITIC_SCORE = 0;
    private static final Integer UPDATED_FILM_CRITIC_SCORE = 1;

    private static final Integer DEFAULT_FILM_AUDIENCE_SCORE = 0;
    private static final Integer UPDATED_FILM_AUDIENCE_SCORE = 1;

    private static final Integer DEFAULT_FILM_NETFLIX_RATING = 0;
    private static final Integer UPDATED_FILM_NETFLIX_RATING = 1;

    private static final String DEFAULT_FILM_BOX_ART_LINK = "AAAAAAAAAA";
    private static final String UPDATED_FILM_BOX_ART_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_FILM_CERT_RATING = "AAAA";
    private static final String UPDATED_FILM_CERT_RATING = "BBBB";

    @Inject
    private FilmRepository filmRepository;

    @Inject
    private FilmMapper filmMapper;

    @Inject
    private FilmService filmService;

    @Inject
    private FilmSearchRepository filmSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFilmMockMvc;

    private Film film;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FilmResource filmResource = new FilmResource();
        ReflectionTestUtils.setField(filmResource, "filmService", filmService);
        this.restFilmMockMvc = MockMvcBuilders.standaloneSetup(filmResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Film createEntity(EntityManager em) {
        Film film = new Film()
                .filmTitle(DEFAULT_FILM_TITLE)
                .filmYear(DEFAULT_FILM_YEAR)
                .filmDurationInMinutes(DEFAULT_FILM_DURATION_IN_MINUTES)
                .filmSynopsis(DEFAULT_FILM_SYNOPSIS)
                .netflixRatingChartNumber(DEFAULT_NETFLIX_RATING_CHART_NUMBER)
                .filmUrl(DEFAULT_FILM_URL)
                .filmCriticScore(DEFAULT_FILM_CRITIC_SCORE)
                .filmAudienceScore(DEFAULT_FILM_AUDIENCE_SCORE)
                .filmNetflixRating(DEFAULT_FILM_NETFLIX_RATING)
                .filmBoxArtLink(DEFAULT_FILM_BOX_ART_LINK)
                .filmCertRating(DEFAULT_FILM_CERT_RATING);
        return film;
    }

    @Before
    public void initTest() {
        filmSearchRepository.deleteAll();
        film = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilm() throws Exception {
        int databaseSizeBeforeCreate = filmRepository.findAll().size();

        // Create the Film
        FilmDTO filmDTO = filmMapper.filmToFilmDTO(film);

        restFilmMockMvc.perform(post("/api/films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
                .andExpect(status().isCreated());

        // Validate the Film in the database
        List<Film> films = filmRepository.findAll();
        assertThat(films).hasSize(databaseSizeBeforeCreate + 1);
        Film testFilm = films.get(films.size() - 1);
        assertThat(testFilm.getFilmTitle()).isEqualTo(DEFAULT_FILM_TITLE);
        assertThat(testFilm.getFilmYear()).isEqualTo(DEFAULT_FILM_YEAR);
        assertThat(testFilm.getFilmDurationInMinutes()).isEqualTo(DEFAULT_FILM_DURATION_IN_MINUTES);
        assertThat(testFilm.getFilmSynopsis()).isEqualTo(DEFAULT_FILM_SYNOPSIS);
        assertThat(testFilm.getNetflixRatingChartNumber()).isEqualTo(DEFAULT_NETFLIX_RATING_CHART_NUMBER);
        assertThat(testFilm.getFilmUrl()).isEqualTo(DEFAULT_FILM_URL);
        assertThat(testFilm.getFilmCriticScore()).isEqualTo(DEFAULT_FILM_CRITIC_SCORE);
        assertThat(testFilm.getFilmAudienceScore()).isEqualTo(DEFAULT_FILM_AUDIENCE_SCORE);
        assertThat(testFilm.getFilmNetflixRating()).isEqualTo(DEFAULT_FILM_NETFLIX_RATING);
        assertThat(testFilm.getFilmBoxArtLink()).isEqualTo(DEFAULT_FILM_BOX_ART_LINK);
        assertThat(testFilm.getFilmCertRating()).isEqualTo(DEFAULT_FILM_CERT_RATING);

        // Validate the Film in ElasticSearch
        Film filmEs = filmSearchRepository.findOne(testFilm.getId());
        assertThat(filmEs).isEqualToComparingFieldByField(testFilm);
    }

    @Test
    @Transactional
    public void checkFilmTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = filmRepository.findAll().size();
        // set the field null
        film.setFilmTitle(null);

        // Create the Film, which fails.
        FilmDTO filmDTO = filmMapper.filmToFilmDTO(film);

        restFilmMockMvc.perform(post("/api/films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
                .andExpect(status().isBadRequest());

        List<Film> films = filmRepository.findAll();
        assertThat(films).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilmYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = filmRepository.findAll().size();
        // set the field null
        film.setFilmYear(null);

        // Create the Film, which fails.
        FilmDTO filmDTO = filmMapper.filmToFilmDTO(film);

        restFilmMockMvc.perform(post("/api/films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
                .andExpect(status().isBadRequest());

        List<Film> films = filmRepository.findAll();
        assertThat(films).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNetflixRatingChartNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = filmRepository.findAll().size();
        // set the field null
        film.setNetflixRatingChartNumber(null);

        // Create the Film, which fails.
        FilmDTO filmDTO = filmMapper.filmToFilmDTO(film);

        restFilmMockMvc.perform(post("/api/films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
                .andExpect(status().isBadRequest());

        List<Film> films = filmRepository.findAll();
        assertThat(films).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilmUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = filmRepository.findAll().size();
        // set the field null
        film.setFilmUrl(null);

        // Create the Film, which fails.
        FilmDTO filmDTO = filmMapper.filmToFilmDTO(film);

        restFilmMockMvc.perform(post("/api/films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
                .andExpect(status().isBadRequest());

        List<Film> films = filmRepository.findAll();
        assertThat(films).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFilms() throws Exception {
        // Initialize the database
        filmRepository.saveAndFlush(film);

        // Get all the films
        restFilmMockMvc.perform(get("/api/films?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(film.getId().intValue())))
                .andExpect(jsonPath("$.[*].filmTitle").value(hasItem(DEFAULT_FILM_TITLE.toString())))
                .andExpect(jsonPath("$.[*].filmYear").value(hasItem(DEFAULT_FILM_YEAR)))
                .andExpect(jsonPath("$.[*].filmDurationInMinutes").value(hasItem(DEFAULT_FILM_DURATION_IN_MINUTES)))
                .andExpect(jsonPath("$.[*].filmSynopsis").value(hasItem(DEFAULT_FILM_SYNOPSIS.toString())))
                .andExpect(jsonPath("$.[*].netflixRatingChartNumber").value(hasItem(DEFAULT_NETFLIX_RATING_CHART_NUMBER)))
                .andExpect(jsonPath("$.[*].filmUrl").value(hasItem(DEFAULT_FILM_URL.toString())))
                .andExpect(jsonPath("$.[*].filmCriticScore").value(hasItem(DEFAULT_FILM_CRITIC_SCORE)))
                .andExpect(jsonPath("$.[*].filmAudienceScore").value(hasItem(DEFAULT_FILM_AUDIENCE_SCORE)))
                .andExpect(jsonPath("$.[*].filmNetflixRating").value(hasItem(DEFAULT_FILM_NETFLIX_RATING)))
                .andExpect(jsonPath("$.[*].filmBoxArtLink").value(hasItem(DEFAULT_FILM_BOX_ART_LINK.toString())))
                .andExpect(jsonPath("$.[*].filmCertRating").value(hasItem(DEFAULT_FILM_CERT_RATING.toString())));
    }

    @Test
    @Transactional
    public void getFilm() throws Exception {
        // Initialize the database
        filmRepository.saveAndFlush(film);

        // Get the film
        restFilmMockMvc.perform(get("/api/films/{id}", film.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(film.getId().intValue()))
            .andExpect(jsonPath("$.filmTitle").value(DEFAULT_FILM_TITLE.toString()))
            .andExpect(jsonPath("$.filmYear").value(DEFAULT_FILM_YEAR))
            .andExpect(jsonPath("$.filmDurationInMinutes").value(DEFAULT_FILM_DURATION_IN_MINUTES))
            .andExpect(jsonPath("$.filmSynopsis").value(DEFAULT_FILM_SYNOPSIS.toString()))
            .andExpect(jsonPath("$.netflixRatingChartNumber").value(DEFAULT_NETFLIX_RATING_CHART_NUMBER))
            .andExpect(jsonPath("$.filmUrl").value(DEFAULT_FILM_URL.toString()))
            .andExpect(jsonPath("$.filmCriticScore").value(DEFAULT_FILM_CRITIC_SCORE))
            .andExpect(jsonPath("$.filmAudienceScore").value(DEFAULT_FILM_AUDIENCE_SCORE))
            .andExpect(jsonPath("$.filmNetflixRating").value(DEFAULT_FILM_NETFLIX_RATING))
            .andExpect(jsonPath("$.filmBoxArtLink").value(DEFAULT_FILM_BOX_ART_LINK.toString()))
            .andExpect(jsonPath("$.filmCertRating").value(DEFAULT_FILM_CERT_RATING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFilm() throws Exception {
        // Get the film
        restFilmMockMvc.perform(get("/api/films/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilm() throws Exception {
        // Initialize the database
        filmRepository.saveAndFlush(film);
        filmSearchRepository.save(film);
        int databaseSizeBeforeUpdate = filmRepository.findAll().size();

        // Update the film
        Film updatedFilm = filmRepository.findOne(film.getId());
        updatedFilm
                .filmTitle(UPDATED_FILM_TITLE)
                .filmYear(UPDATED_FILM_YEAR)
                .filmDurationInMinutes(UPDATED_FILM_DURATION_IN_MINUTES)
                .filmSynopsis(UPDATED_FILM_SYNOPSIS)
                .netflixRatingChartNumber(UPDATED_NETFLIX_RATING_CHART_NUMBER)
                .filmUrl(UPDATED_FILM_URL)
                .filmCriticScore(UPDATED_FILM_CRITIC_SCORE)
                .filmAudienceScore(UPDATED_FILM_AUDIENCE_SCORE)
                .filmNetflixRating(UPDATED_FILM_NETFLIX_RATING)
                .filmBoxArtLink(UPDATED_FILM_BOX_ART_LINK)
                .filmCertRating(UPDATED_FILM_CERT_RATING);
        FilmDTO filmDTO = filmMapper.filmToFilmDTO(updatedFilm);

        restFilmMockMvc.perform(put("/api/films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
                .andExpect(status().isOk());

        // Validate the Film in the database
        List<Film> films = filmRepository.findAll();
        assertThat(films).hasSize(databaseSizeBeforeUpdate);
        Film testFilm = films.get(films.size() - 1);
        assertThat(testFilm.getFilmTitle()).isEqualTo(UPDATED_FILM_TITLE);
        assertThat(testFilm.getFilmYear()).isEqualTo(UPDATED_FILM_YEAR);
        assertThat(testFilm.getFilmDurationInMinutes()).isEqualTo(UPDATED_FILM_DURATION_IN_MINUTES);
        assertThat(testFilm.getFilmSynopsis()).isEqualTo(UPDATED_FILM_SYNOPSIS);
        assertThat(testFilm.getNetflixRatingChartNumber()).isEqualTo(UPDATED_NETFLIX_RATING_CHART_NUMBER);
        assertThat(testFilm.getFilmUrl()).isEqualTo(UPDATED_FILM_URL);
        assertThat(testFilm.getFilmCriticScore()).isEqualTo(UPDATED_FILM_CRITIC_SCORE);
        assertThat(testFilm.getFilmAudienceScore()).isEqualTo(UPDATED_FILM_AUDIENCE_SCORE);
        assertThat(testFilm.getFilmNetflixRating()).isEqualTo(UPDATED_FILM_NETFLIX_RATING);
        assertThat(testFilm.getFilmBoxArtLink()).isEqualTo(UPDATED_FILM_BOX_ART_LINK);
        assertThat(testFilm.getFilmCertRating()).isEqualTo(UPDATED_FILM_CERT_RATING);

        // Validate the Film in ElasticSearch
        Film filmEs = filmSearchRepository.findOne(testFilm.getId());
        assertThat(filmEs).isEqualToComparingFieldByField(testFilm);
    }

    @Test
    @Transactional
    public void deleteFilm() throws Exception {
        // Initialize the database
        filmRepository.saveAndFlush(film);
        filmSearchRepository.save(film);
        int databaseSizeBeforeDelete = filmRepository.findAll().size();

        // Get the film
        restFilmMockMvc.perform(delete("/api/films/{id}", film.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean filmExistsInEs = filmSearchRepository.exists(film.getId());
        assertThat(filmExistsInEs).isFalse();

        // Validate the database is empty
        List<Film> films = filmRepository.findAll();
        assertThat(films).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFilm() throws Exception {
        // Initialize the database
        filmRepository.saveAndFlush(film);
        filmSearchRepository.save(film);

        // Search the film
        restFilmMockMvc.perform(get("/api/_search/films?query=id:" + film.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(film.getId().intValue())))
            .andExpect(jsonPath("$.[*].filmTitle").value(hasItem(DEFAULT_FILM_TITLE.toString())))
            .andExpect(jsonPath("$.[*].filmYear").value(hasItem(DEFAULT_FILM_YEAR)))
            .andExpect(jsonPath("$.[*].filmDurationInMinutes").value(hasItem(DEFAULT_FILM_DURATION_IN_MINUTES)))
            .andExpect(jsonPath("$.[*].filmSynopsis").value(hasItem(DEFAULT_FILM_SYNOPSIS.toString())))
            .andExpect(jsonPath("$.[*].netflixRatingChartNumber").value(hasItem(DEFAULT_NETFLIX_RATING_CHART_NUMBER)))
            .andExpect(jsonPath("$.[*].filmUrl").value(hasItem(DEFAULT_FILM_URL.toString())))
            .andExpect(jsonPath("$.[*].filmCriticScore").value(hasItem(DEFAULT_FILM_CRITIC_SCORE)))
            .andExpect(jsonPath("$.[*].filmAudienceScore").value(hasItem(DEFAULT_FILM_AUDIENCE_SCORE)))
            .andExpect(jsonPath("$.[*].filmNetflixRating").value(hasItem(DEFAULT_FILM_NETFLIX_RATING)))
            .andExpect(jsonPath("$.[*].filmBoxArtLink").value(hasItem(DEFAULT_FILM_BOX_ART_LINK.toString())))
            .andExpect(jsonPath("$.[*].filmCertRating").value(hasItem(DEFAULT_FILM_CERT_RATING.toString())));
    }
}
