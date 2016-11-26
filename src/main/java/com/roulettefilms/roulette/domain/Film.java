package com.roulettefilms.roulette.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * not an ignored comment                                                      
 * 
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "film")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "film")
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "film_title", length = 30, nullable = false)
    private String filmTitle;

    @NotNull
    @Min(value = 1890)
    @Max(value = 2100)
    @Column(name = "film_year", nullable = false)
    private Integer filmYear;

    @Min(value = 0)
    @Max(value = 1440)
    @Column(name = "film_duration_in_minutes")
    private Integer filmDurationInMinutes;

    @Size(min = 1, max = 1024)
    @Column(name = "film_synopsis", length = 1024)
    private String filmSynopsis;

    @NotNull
    @Min(value = 0)
    @Column(name = "netflix_rating_chart_number", nullable = false)
    private Integer netflixRatingChartNumber;

    @NotNull
    @Column(name = "film_url", nullable = false)
    private String filmUrl;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "film_critic_score")
    private Integer filmCriticScore;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "film_audience_score")
    private Integer filmAudienceScore;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "film_netflix_rating")
    private Integer filmNetflixRating;

    @Column(name = "film_box_art_link")
    private String filmBoxArtLink;

    @Size(min = 1, max = 4)
    @Column(name = "film_cert_rating", length = 4)
    private String filmCertRating;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "film_genre",
               joinColumns = @JoinColumn(name="films_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="genres_id", referencedColumnName="ID"))
    private Set<Genre> genres = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public Film filmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
        return this;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public Integer getFilmYear() {
        return filmYear;
    }

    public Film filmYear(Integer filmYear) {
        this.filmYear = filmYear;
        return this;
    }

    public void setFilmYear(Integer filmYear) {
        this.filmYear = filmYear;
    }

    public Integer getFilmDurationInMinutes() {
        return filmDurationInMinutes;
    }

    public Film filmDurationInMinutes(Integer filmDurationInMinutes) {
        this.filmDurationInMinutes = filmDurationInMinutes;
        return this;
    }

    public void setFilmDurationInMinutes(Integer filmDurationInMinutes) {
        this.filmDurationInMinutes = filmDurationInMinutes;
    }

    public String getFilmSynopsis() {
        return filmSynopsis;
    }

    public Film filmSynopsis(String filmSynopsis) {
        this.filmSynopsis = filmSynopsis;
        return this;
    }

    public void setFilmSynopsis(String filmSynopsis) {
        this.filmSynopsis = filmSynopsis;
    }

    public Integer getNetflixRatingChartNumber() {
        return netflixRatingChartNumber;
    }

    public Film netflixRatingChartNumber(Integer netflixRatingChartNumber) {
        this.netflixRatingChartNumber = netflixRatingChartNumber;
        return this;
    }

    public void setNetflixRatingChartNumber(Integer netflixRatingChartNumber) {
        this.netflixRatingChartNumber = netflixRatingChartNumber;
    }

    public String getFilmUrl() {
        return filmUrl;
    }

    public Film filmUrl(String filmUrl) {
        this.filmUrl = filmUrl;
        return this;
    }

    public void setFilmUrl(String filmUrl) {
        this.filmUrl = filmUrl;
    }

    public Integer getFilmCriticScore() {
        return filmCriticScore;
    }

    public Film filmCriticScore(Integer filmCriticScore) {
        this.filmCriticScore = filmCriticScore;
        return this;
    }

    public void setFilmCriticScore(Integer filmCriticScore) {
        this.filmCriticScore = filmCriticScore;
    }

    public Integer getFilmAudienceScore() {
        return filmAudienceScore;
    }

    public Film filmAudienceScore(Integer filmAudienceScore) {
        this.filmAudienceScore = filmAudienceScore;
        return this;
    }

    public void setFilmAudienceScore(Integer filmAudienceScore) {
        this.filmAudienceScore = filmAudienceScore;
    }

    public Integer getFilmNetflixRating() {
        return filmNetflixRating;
    }

    public Film filmNetflixRating(Integer filmNetflixRating) {
        this.filmNetflixRating = filmNetflixRating;
        return this;
    }

    public void setFilmNetflixRating(Integer filmNetflixRating) {
        this.filmNetflixRating = filmNetflixRating;
    }

    public String getFilmBoxArtLink() {
        return filmBoxArtLink;
    }

    public Film filmBoxArtLink(String filmBoxArtLink) {
        this.filmBoxArtLink = filmBoxArtLink;
        return this;
    }

    public void setFilmBoxArtLink(String filmBoxArtLink) {
        this.filmBoxArtLink = filmBoxArtLink;
    }

    public String getFilmCertRating() {
        return filmCertRating;
    }

    public Film filmCertRating(String filmCertRating) {
        this.filmCertRating = filmCertRating;
        return this;
    }

    public void setFilmCertRating(String filmCertRating) {
        this.filmCertRating = filmCertRating;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Film genres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public Film addGenre(Genre genre) {
        genres.add(genre);
        genre.getFilms().add(this);
        return this;
    }

    public Film removeGenre(Genre genre) {
        genres.remove(genre);
        genre.getFilms().remove(this);
        return this;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Film film = (Film) o;
        if(film.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Film{" +
            "id=" + id +
            ", filmTitle='" + filmTitle + "'" +
            ", filmYear='" + filmYear + "'" +
            ", filmDurationInMinutes='" + filmDurationInMinutes + "'" +
            ", filmSynopsis='" + filmSynopsis + "'" +
            ", netflixRatingChartNumber='" + netflixRatingChartNumber + "'" +
            ", filmUrl='" + filmUrl + "'" +
            ", filmCriticScore='" + filmCriticScore + "'" +
            ", filmAudienceScore='" + filmAudienceScore + "'" +
            ", filmNetflixRating='" + filmNetflixRating + "'" +
            ", filmBoxArtLink='" + filmBoxArtLink + "'" +
            ", filmCertRating='" + filmCertRating + "'" +
            '}';
    }
}
