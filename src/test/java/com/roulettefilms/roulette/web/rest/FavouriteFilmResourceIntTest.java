package com.roulettefilms.roulette.web.rest;

import com.roulettefilms.roulette.RoulettefilmsApp;

import com.roulettefilms.roulette.domain.FavouriteFilm;
import com.roulettefilms.roulette.repository.FavouriteFilmRepository;
import com.roulettefilms.roulette.service.FavouriteFilmService;
import com.roulettefilms.roulette.repository.search.FavouriteFilmSearchRepository;
import com.roulettefilms.roulette.service.dto.FavouriteFilmDTO;
import com.roulettefilms.roulette.service.mapper.FavouriteFilmMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FavouriteFilmResource REST controller.
 *
 * @see FavouriteFilmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoulettefilmsApp.class)
public class FavouriteFilmResourceIntTest {

    private static final String DEFAULT_FAVOURITE_FILM_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_FAVOURITE_FILM_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_FAVOURITE_FILM_YEAR = 1890;
    private static final Integer UPDATED_FAVOURITE_FILM_YEAR = 1891;

    private static final Integer DEFAULT_FAVOURITE_FILM_DURATION_IN_MINUTES = 0;
    private static final Integer UPDATED_FAVOURITE_FILM_DURATION_IN_MINUTES = 1;

    private static final String DEFAULT_FAVOURITE_FILM_SYNOPSIS = "AAAAAAAAAA";
    private static final String UPDATED_FAVOURITE_FILM_SYNOPSIS = "BBBBBBBBBB";

    private static final Integer DEFAULT_FAVOURITE_FILM_NETFLIX_RATING = 0;
    private static final Integer UPDATED_FAVOURITE_FILM_NETFLIX_RATING = 1;

    private static final Integer DEFAULT_FAVOURITE_FILM_USER_RATING = 0;
    private static final Integer UPDATED_FAVOURITE_FILM_USER_RATING = 1;

    private static final ZonedDateTime DEFAULT_CREATED_FAVOURITE_FILM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_FAVOURITE_FILM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_FAVOURITE_FILM_STR = DateTimeFormatter.ISO_INSTANT.format(DEFAULT_CREATED_FAVOURITE_FILM);

    @Inject
    private FavouriteFilmRepository favouriteFilmRepository;

    @Inject
    private FavouriteFilmMapper favouriteFilmMapper;

    @Inject
    private FavouriteFilmService favouriteFilmService;

    @Inject
    private FavouriteFilmSearchRepository favouriteFilmSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFavouriteFilmMockMvc;

    private FavouriteFilm favouriteFilm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FavouriteFilmResource favouriteFilmResource = new FavouriteFilmResource();
        ReflectionTestUtils.setField(favouriteFilmResource, "favouriteFilmService", favouriteFilmService);
        this.restFavouriteFilmMockMvc = MockMvcBuilders.standaloneSetup(favouriteFilmResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavouriteFilm createEntity(EntityManager em) {
        FavouriteFilm favouriteFilm = new FavouriteFilm()
                .favouriteFilmTitle(DEFAULT_FAVOURITE_FILM_TITLE)
                .favouriteFilmYear(DEFAULT_FAVOURITE_FILM_YEAR)
                .favouriteFilmDurationInMinutes(DEFAULT_FAVOURITE_FILM_DURATION_IN_MINUTES)
                .favouriteFilmSynopsis(DEFAULT_FAVOURITE_FILM_SYNOPSIS)
                .favouriteFilmNetflixRating(DEFAULT_FAVOURITE_FILM_NETFLIX_RATING)
                .favouriteFilmUserRating(DEFAULT_FAVOURITE_FILM_USER_RATING)
                .createdFavouriteFilm(DEFAULT_CREATED_FAVOURITE_FILM);
        return favouriteFilm;
    }

    @Before
    public void initTest() {
        favouriteFilmSearchRepository.deleteAll();
        favouriteFilm = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavouriteFilm() throws Exception {
        int databaseSizeBeforeCreate = favouriteFilmRepository.findAll().size();

        // Create the FavouriteFilm
        FavouriteFilmDTO favouriteFilmDTO = favouriteFilmMapper.favouriteFilmToFavouriteFilmDTO(favouriteFilm);

        restFavouriteFilmMockMvc.perform(post("/api/favourite-films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(favouriteFilmDTO)))
                .andExpect(status().isCreated());

        // Validate the FavouriteFilm in the database
        List<FavouriteFilm> favouriteFilms = favouriteFilmRepository.findAll();
        assertThat(favouriteFilms).hasSize(databaseSizeBeforeCreate + 1);
        FavouriteFilm testFavouriteFilm = favouriteFilms.get(favouriteFilms.size() - 1);
        assertThat(testFavouriteFilm.getFavouriteFilmTitle()).isEqualTo(DEFAULT_FAVOURITE_FILM_TITLE);
        assertThat(testFavouriteFilm.getFavouriteFilmYear()).isEqualTo(DEFAULT_FAVOURITE_FILM_YEAR);
        assertThat(testFavouriteFilm.getFavouriteFilmDurationInMinutes()).isEqualTo(DEFAULT_FAVOURITE_FILM_DURATION_IN_MINUTES);
        assertThat(testFavouriteFilm.getFavouriteFilmSynopsis()).isEqualTo(DEFAULT_FAVOURITE_FILM_SYNOPSIS);
        assertThat(testFavouriteFilm.getFavouriteFilmNetflixRating()).isEqualTo(DEFAULT_FAVOURITE_FILM_NETFLIX_RATING);
        assertThat(testFavouriteFilm.getFavouriteFilmUserRating()).isEqualTo(DEFAULT_FAVOURITE_FILM_USER_RATING);
        assertThat(testFavouriteFilm.getCreatedFavouriteFilm()).isEqualTo(DEFAULT_CREATED_FAVOURITE_FILM);

        // Validate the FavouriteFilm in ElasticSearch
        FavouriteFilm favouriteFilmEs = favouriteFilmSearchRepository.findOne(testFavouriteFilm.getId());
        assertThat(favouriteFilmEs).isEqualToComparingFieldByField(testFavouriteFilm);
    }

    @Test
    @Transactional
    public void checkFavouriteFilmTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = favouriteFilmRepository.findAll().size();
        // set the field null
        favouriteFilm.setFavouriteFilmTitle(null);

        // Create the FavouriteFilm, which fails.
        FavouriteFilmDTO favouriteFilmDTO = favouriteFilmMapper.favouriteFilmToFavouriteFilmDTO(favouriteFilm);

        restFavouriteFilmMockMvc.perform(post("/api/favourite-films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(favouriteFilmDTO)))
                .andExpect(status().isBadRequest());

        List<FavouriteFilm> favouriteFilms = favouriteFilmRepository.findAll();
        assertThat(favouriteFilms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFavouriteFilmYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = favouriteFilmRepository.findAll().size();
        // set the field null
        favouriteFilm.setFavouriteFilmYear(null);

        // Create the FavouriteFilm, which fails.
        FavouriteFilmDTO favouriteFilmDTO = favouriteFilmMapper.favouriteFilmToFavouriteFilmDTO(favouriteFilm);

        restFavouriteFilmMockMvc.perform(post("/api/favourite-films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(favouriteFilmDTO)))
                .andExpect(status().isBadRequest());

        List<FavouriteFilm> favouriteFilms = favouriteFilmRepository.findAll();
        assertThat(favouriteFilms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFavouriteFilms() throws Exception {
        // Initialize the database
        favouriteFilmRepository.saveAndFlush(favouriteFilm);

        // Get all the favouriteFilms
        restFavouriteFilmMockMvc.perform(get("/api/favourite-films?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(favouriteFilm.getId().intValue())))
                .andExpect(jsonPath("$.[*].favouriteFilmTitle").value(hasItem(DEFAULT_FAVOURITE_FILM_TITLE.toString())))
                .andExpect(jsonPath("$.[*].favouriteFilmYear").value(hasItem(DEFAULT_FAVOURITE_FILM_YEAR)))
                .andExpect(jsonPath("$.[*].favouriteFilmDurationInMinutes").value(hasItem(DEFAULT_FAVOURITE_FILM_DURATION_IN_MINUTES)))
                .andExpect(jsonPath("$.[*].favouriteFilmSynopsis").value(hasItem(DEFAULT_FAVOURITE_FILM_SYNOPSIS.toString())))
                .andExpect(jsonPath("$.[*].favouriteFilmNetflixRating").value(hasItem(DEFAULT_FAVOURITE_FILM_NETFLIX_RATING)))
                .andExpect(jsonPath("$.[*].favouriteFilmUserRating").value(hasItem(DEFAULT_FAVOURITE_FILM_USER_RATING)))
                .andExpect(jsonPath("$.[*].createdFavouriteFilm").value(hasItem(DEFAULT_CREATED_FAVOURITE_FILM_STR)));
    }

    @Test
    @Transactional
    public void getFavouriteFilm() throws Exception {
        // Initialize the database
        favouriteFilmRepository.saveAndFlush(favouriteFilm);

        // Get the favouriteFilm
        restFavouriteFilmMockMvc.perform(get("/api/favourite-films/{id}", favouriteFilm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(favouriteFilm.getId().intValue()))
            .andExpect(jsonPath("$.favouriteFilmTitle").value(DEFAULT_FAVOURITE_FILM_TITLE.toString()))
            .andExpect(jsonPath("$.favouriteFilmYear").value(DEFAULT_FAVOURITE_FILM_YEAR))
            .andExpect(jsonPath("$.favouriteFilmDurationInMinutes").value(DEFAULT_FAVOURITE_FILM_DURATION_IN_MINUTES))
            .andExpect(jsonPath("$.favouriteFilmSynopsis").value(DEFAULT_FAVOURITE_FILM_SYNOPSIS.toString()))
            .andExpect(jsonPath("$.favouriteFilmNetflixRating").value(DEFAULT_FAVOURITE_FILM_NETFLIX_RATING))
            .andExpect(jsonPath("$.favouriteFilmUserRating").value(DEFAULT_FAVOURITE_FILM_USER_RATING))
            .andExpect(jsonPath("$.createdFavouriteFilm").value(DEFAULT_CREATED_FAVOURITE_FILM_STR));
    }

    @Test
    @Transactional
    public void getNonExistingFavouriteFilm() throws Exception {
        // Get the favouriteFilm
        restFavouriteFilmMockMvc.perform(get("/api/favourite-films/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavouriteFilm() throws Exception {
        // Initialize the database
        favouriteFilmRepository.saveAndFlush(favouriteFilm);
        favouriteFilmSearchRepository.save(favouriteFilm);
        int databaseSizeBeforeUpdate = favouriteFilmRepository.findAll().size();

        // Update the favouriteFilm
        FavouriteFilm updatedFavouriteFilm = favouriteFilmRepository.findOne(favouriteFilm.getId());
        updatedFavouriteFilm
                .favouriteFilmTitle(UPDATED_FAVOURITE_FILM_TITLE)
                .favouriteFilmYear(UPDATED_FAVOURITE_FILM_YEAR)
                .favouriteFilmDurationInMinutes(UPDATED_FAVOURITE_FILM_DURATION_IN_MINUTES)
                .favouriteFilmSynopsis(UPDATED_FAVOURITE_FILM_SYNOPSIS)
                .favouriteFilmNetflixRating(UPDATED_FAVOURITE_FILM_NETFLIX_RATING)
                .favouriteFilmUserRating(UPDATED_FAVOURITE_FILM_USER_RATING)
                .createdFavouriteFilm(UPDATED_CREATED_FAVOURITE_FILM);
        FavouriteFilmDTO favouriteFilmDTO = favouriteFilmMapper.favouriteFilmToFavouriteFilmDTO(updatedFavouriteFilm);

        restFavouriteFilmMockMvc.perform(put("/api/favourite-films")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(favouriteFilmDTO)))
                .andExpect(status().isOk());

        // Validate the FavouriteFilm in the database
        List<FavouriteFilm> favouriteFilms = favouriteFilmRepository.findAll();
        assertThat(favouriteFilms).hasSize(databaseSizeBeforeUpdate);
        FavouriteFilm testFavouriteFilm = favouriteFilms.get(favouriteFilms.size() - 1);
        assertThat(testFavouriteFilm.getFavouriteFilmTitle()).isEqualTo(UPDATED_FAVOURITE_FILM_TITLE);
        assertThat(testFavouriteFilm.getFavouriteFilmYear()).isEqualTo(UPDATED_FAVOURITE_FILM_YEAR);
        assertThat(testFavouriteFilm.getFavouriteFilmDurationInMinutes()).isEqualTo(UPDATED_FAVOURITE_FILM_DURATION_IN_MINUTES);
        assertThat(testFavouriteFilm.getFavouriteFilmSynopsis()).isEqualTo(UPDATED_FAVOURITE_FILM_SYNOPSIS);
        assertThat(testFavouriteFilm.getFavouriteFilmNetflixRating()).isEqualTo(UPDATED_FAVOURITE_FILM_NETFLIX_RATING);
        assertThat(testFavouriteFilm.getFavouriteFilmUserRating()).isEqualTo(UPDATED_FAVOURITE_FILM_USER_RATING);
        assertThat(testFavouriteFilm.getCreatedFavouriteFilm()).isEqualTo(UPDATED_CREATED_FAVOURITE_FILM);

        // Validate the FavouriteFilm in ElasticSearch
        FavouriteFilm favouriteFilmEs = favouriteFilmSearchRepository.findOne(testFavouriteFilm.getId());
        assertThat(favouriteFilmEs).isEqualToComparingFieldByField(testFavouriteFilm);
    }

    @Test
    @Transactional
    public void deleteFavouriteFilm() throws Exception {
        // Initialize the database
        favouriteFilmRepository.saveAndFlush(favouriteFilm);
        favouriteFilmSearchRepository.save(favouriteFilm);
        int databaseSizeBeforeDelete = favouriteFilmRepository.findAll().size();

        // Get the favouriteFilm
        restFavouriteFilmMockMvc.perform(delete("/api/favourite-films/{id}", favouriteFilm.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean favouriteFilmExistsInEs = favouriteFilmSearchRepository.exists(favouriteFilm.getId());
        assertThat(favouriteFilmExistsInEs).isFalse();

        // Validate the database is empty
        List<FavouriteFilm> favouriteFilms = favouriteFilmRepository.findAll();
        assertThat(favouriteFilms).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFavouriteFilm() throws Exception {
        // Initialize the database
        favouriteFilmRepository.saveAndFlush(favouriteFilm);
        favouriteFilmSearchRepository.save(favouriteFilm);

        // Search the favouriteFilm
        restFavouriteFilmMockMvc.perform(get("/api/_search/favourite-films?query=id:" + favouriteFilm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favouriteFilm.getId().intValue())))
            .andExpect(jsonPath("$.[*].favouriteFilmTitle").value(hasItem(DEFAULT_FAVOURITE_FILM_TITLE.toString())))
            .andExpect(jsonPath("$.[*].favouriteFilmYear").value(hasItem(DEFAULT_FAVOURITE_FILM_YEAR)))
            .andExpect(jsonPath("$.[*].favouriteFilmDurationInMinutes").value(hasItem(DEFAULT_FAVOURITE_FILM_DURATION_IN_MINUTES)))
            .andExpect(jsonPath("$.[*].favouriteFilmSynopsis").value(hasItem(DEFAULT_FAVOURITE_FILM_SYNOPSIS.toString())))
            .andExpect(jsonPath("$.[*].favouriteFilmNetflixRating").value(hasItem(DEFAULT_FAVOURITE_FILM_NETFLIX_RATING)))
            .andExpect(jsonPath("$.[*].favouriteFilmUserRating").value(hasItem(DEFAULT_FAVOURITE_FILM_USER_RATING)))
            .andExpect(jsonPath("$.[*].createdFavouriteFilm").value(hasItem(DEFAULT_CREATED_FAVOURITE_FILM_STR)));
    }
}
