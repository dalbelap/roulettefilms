package com.roulettefilms.roulette.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A FavouriteFilm.
 */
@Entity
@Table(name = "favourite_film")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "favouritefilm")
public class FavouriteFilm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "favourite_film_title", length = 30, nullable = false)
    private String favouriteFilmTitle;

    @NotNull
    @Min(value = 1890)
    @Max(value = 2100)
    @Column(name = "favourite_film_year", nullable = false)
    private Integer favouriteFilmYear;

    @Min(value = 0)
    @Max(value = 1440)
    @Column(name = "favourite_film_duration_in_minutes")
    private Integer favouriteFilmDurationInMinutes;

    @Size(min = 1, max = 1024)
    @Column(name = "favourite_film_synopsis", length = 1024)
    private String favouriteFilmSynopsis;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "favourite_film_netflix_rating")
    private Integer favouriteFilmNetflixRating;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "favourite_film_user_rating")
    private Integer favouriteFilmUserRating;

    @Column(name = "created_favourite_film")
    private ZonedDateTime createdFavouriteFilm;

    @OneToOne
    @JoinColumn(unique = true)
    private Film film;

    @ManyToOne
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFavouriteFilmTitle() {
        return favouriteFilmTitle;
    }

    public FavouriteFilm favouriteFilmTitle(String favouriteFilmTitle) {
        this.favouriteFilmTitle = favouriteFilmTitle;
        return this;
    }

    public void setFavouriteFilmTitle(String favouriteFilmTitle) {
        this.favouriteFilmTitle = favouriteFilmTitle;
    }

    public Integer getFavouriteFilmYear() {
        return favouriteFilmYear;
    }

    public FavouriteFilm favouriteFilmYear(Integer favouriteFilmYear) {
        this.favouriteFilmYear = favouriteFilmYear;
        return this;
    }

    public void setFavouriteFilmYear(Integer favouriteFilmYear) {
        this.favouriteFilmYear = favouriteFilmYear;
    }

    public Integer getFavouriteFilmDurationInMinutes() {
        return favouriteFilmDurationInMinutes;
    }

    public FavouriteFilm favouriteFilmDurationInMinutes(Integer favouriteFilmDurationInMinutes) {
        this.favouriteFilmDurationInMinutes = favouriteFilmDurationInMinutes;
        return this;
    }

    public void setFavouriteFilmDurationInMinutes(Integer favouriteFilmDurationInMinutes) {
        this.favouriteFilmDurationInMinutes = favouriteFilmDurationInMinutes;
    }

    public String getFavouriteFilmSynopsis() {
        return favouriteFilmSynopsis;
    }

    public FavouriteFilm favouriteFilmSynopsis(String favouriteFilmSynopsis) {
        this.favouriteFilmSynopsis = favouriteFilmSynopsis;
        return this;
    }

    public void setFavouriteFilmSynopsis(String favouriteFilmSynopsis) {
        this.favouriteFilmSynopsis = favouriteFilmSynopsis;
    }

    public Integer getFavouriteFilmNetflixRating() {
        return favouriteFilmNetflixRating;
    }

    public FavouriteFilm favouriteFilmNetflixRating(Integer favouriteFilmNetflixRating) {
        this.favouriteFilmNetflixRating = favouriteFilmNetflixRating;
        return this;
    }

    public void setFavouriteFilmNetflixRating(Integer favouriteFilmNetflixRating) {
        this.favouriteFilmNetflixRating = favouriteFilmNetflixRating;
    }

    public Integer getFavouriteFilmUserRating() {
        return favouriteFilmUserRating;
    }

    public FavouriteFilm favouriteFilmUserRating(Integer favouriteFilmUserRating) {
        this.favouriteFilmUserRating = favouriteFilmUserRating;
        return this;
    }

    public void setFavouriteFilmUserRating(Integer favouriteFilmUserRating) {
        this.favouriteFilmUserRating = favouriteFilmUserRating;
    }

    public ZonedDateTime getCreatedFavouriteFilm() {
        return createdFavouriteFilm;
    }

    public FavouriteFilm createdFavouriteFilm(ZonedDateTime createdFavouriteFilm) {
        this.createdFavouriteFilm = createdFavouriteFilm;
        return this;
    }

    public void setCreatedFavouriteFilm(ZonedDateTime createdFavouriteFilm) {
        this.createdFavouriteFilm = createdFavouriteFilm;
    }

    public Film getFilm() {
        return film;
    }

    public FavouriteFilm film(Film film) {
        this.film = film;
        return this;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public User getOwner() {
        return owner;
    }

    public FavouriteFilm owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FavouriteFilm favouriteFilm = (FavouriteFilm) o;
        if(favouriteFilm.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, favouriteFilm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FavouriteFilm{" +
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
