package com.roulettefilms.roulette.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the FavouriteFilm entity.
 */
public class FavouriteFilmDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String favouriteFilmTitle;

    @NotNull
    @Min(value = 1890)
    @Max(value = 2100)
    private Integer favouriteFilmYear;

    @Min(value = 0)
    @Max(value = 1440)
    private Integer favouriteFilmDurationInMinutes;

    @Size(min = 1, max = 1024)
    private String favouriteFilmSynopsis;

    @Min(value = 0)
    @Max(value = 100)
    private Integer favouriteFilmNetflixRating;

    @Min(value = 0)
    @Max(value = 100)
    private Integer favouriteFilmUserRating;

    private ZonedDateTime createdFavouriteFilm;


    private Long filmId;
    
    private Long ownerId;
    

    private String ownerLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getFavouriteFilmTitle() {
        return favouriteFilmTitle;
    }

    public void setFavouriteFilmTitle(String favouriteFilmTitle) {
        this.favouriteFilmTitle = favouriteFilmTitle;
    }
    public Integer getFavouriteFilmYear() {
        return favouriteFilmYear;
    }

    public void setFavouriteFilmYear(Integer favouriteFilmYear) {
        this.favouriteFilmYear = favouriteFilmYear;
    }
    public Integer getFavouriteFilmDurationInMinutes() {
        return favouriteFilmDurationInMinutes;
    }

    public void setFavouriteFilmDurationInMinutes(Integer favouriteFilmDurationInMinutes) {
        this.favouriteFilmDurationInMinutes = favouriteFilmDurationInMinutes;
    }
    public String getFavouriteFilmSynopsis() {
        return favouriteFilmSynopsis;
    }

    public void setFavouriteFilmSynopsis(String favouriteFilmSynopsis) {
        this.favouriteFilmSynopsis = favouriteFilmSynopsis;
    }
    public Integer getFavouriteFilmNetflixRating() {
        return favouriteFilmNetflixRating;
    }

    public void setFavouriteFilmNetflixRating(Integer favouriteFilmNetflixRating) {
        this.favouriteFilmNetflixRating = favouriteFilmNetflixRating;
    }
    public Integer getFavouriteFilmUserRating() {
        return favouriteFilmUserRating;
    }

    public void setFavouriteFilmUserRating(Integer favouriteFilmUserRating) {
        this.favouriteFilmUserRating = favouriteFilmUserRating;
    }
    public ZonedDateTime getCreatedFavouriteFilm() {
        return createdFavouriteFilm;
    }

    public void setCreatedFavouriteFilm(ZonedDateTime createdFavouriteFilm) {
        this.createdFavouriteFilm = createdFavouriteFilm;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }


    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String userLogin) {
        this.ownerLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FavouriteFilmDTO favouriteFilmDTO = (FavouriteFilmDTO) o;

        if ( ! Objects.equals(id, favouriteFilmDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FavouriteFilmDTO{" +
            "id=" + id +
            ", favouriteFilmTitle='" + favouriteFilmTitle + "'" +
            ", favouriteFilmYear='" + favouriteFilmYear + "'" +
            ", favouriteFilmDurationInMinutes='" + favouriteFilmDurationInMinutes + "'" +
            ", favouriteFilmSynopsis='" + favouriteFilmSynopsis + "'" +
            ", favouriteFilmNetflixRating='" + favouriteFilmNetflixRating + "'" +
            ", favouriteFilmUserRating='" + favouriteFilmUserRating + "'" +
            ", createdFavouriteFilm='" + createdFavouriteFilm + "'" +
            '}';
    }
}
