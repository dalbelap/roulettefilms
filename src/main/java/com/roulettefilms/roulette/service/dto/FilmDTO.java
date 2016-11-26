package com.roulettefilms.roulette.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Film entity.
 */
public class FilmDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String filmTitle;

    @NotNull
    @Min(value = 1890)
    @Max(value = 2100)
    private Integer filmYear;

    @Min(value = 0)
    @Max(value = 1440)
    private Integer filmDurationInMinutes;

    @Size(min = 1, max = 1024)
    private String filmSynopsis;

    @NotNull
    @Min(value = 0)
    private Integer netflixRatingChartNumber;

    @NotNull
    private String filmUrl;

    @Min(value = 0)
    @Max(value = 100)
    private Integer filmCriticScore;

    @Min(value = 0)
    @Max(value = 100)
    private Integer filmAudienceScore;

    @Min(value = 0)
    @Max(value = 100)
    private Integer filmNetflixRating;

    private String filmBoxArtLink;

    @Size(min = 1, max = 4)
    private String filmCertRating;


    private Set<GenreDTO> genres = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }
    public Integer getFilmYear() {
        return filmYear;
    }

    public void setFilmYear(Integer filmYear) {
        this.filmYear = filmYear;
    }
    public Integer getFilmDurationInMinutes() {
        return filmDurationInMinutes;
    }

    public void setFilmDurationInMinutes(Integer filmDurationInMinutes) {
        this.filmDurationInMinutes = filmDurationInMinutes;
    }
    public String getFilmSynopsis() {
        return filmSynopsis;
    }

    public void setFilmSynopsis(String filmSynopsis) {
        this.filmSynopsis = filmSynopsis;
    }
    public Integer getNetflixRatingChartNumber() {
        return netflixRatingChartNumber;
    }

    public void setNetflixRatingChartNumber(Integer netflixRatingChartNumber) {
        this.netflixRatingChartNumber = netflixRatingChartNumber;
    }
    public String getFilmUrl() {
        return filmUrl;
    }

    public void setFilmUrl(String filmUrl) {
        this.filmUrl = filmUrl;
    }
    public Integer getFilmCriticScore() {
        return filmCriticScore;
    }

    public void setFilmCriticScore(Integer filmCriticScore) {
        this.filmCriticScore = filmCriticScore;
    }
    public Integer getFilmAudienceScore() {
        return filmAudienceScore;
    }

    public void setFilmAudienceScore(Integer filmAudienceScore) {
        this.filmAudienceScore = filmAudienceScore;
    }
    public Integer getFilmNetflixRating() {
        return filmNetflixRating;
    }

    public void setFilmNetflixRating(Integer filmNetflixRating) {
        this.filmNetflixRating = filmNetflixRating;
    }
    public String getFilmBoxArtLink() {
        return filmBoxArtLink;
    }

    public void setFilmBoxArtLink(String filmBoxArtLink) {
        this.filmBoxArtLink = filmBoxArtLink;
    }
    public String getFilmCertRating() {
        return filmCertRating;
    }

    public void setFilmCertRating(String filmCertRating) {
        this.filmCertRating = filmCertRating;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
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

        FilmDTO filmDTO = (FilmDTO) o;

        if ( ! Objects.equals(id, filmDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FilmDTO{" +
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
