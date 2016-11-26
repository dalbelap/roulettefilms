package com.roulettefilms.roulette.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Genre.
 */
@Entity
@Table(name = "genre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "genre")
public class Genre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "genre_name", nullable = false)
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Film> films = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public Genre genreName(String genreName) {
        this.genreName = genreName;
        return this;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public Genre films(Set<Film> films) {
        this.films = films;
        return this;
    }

    public Genre addFilm(Film film) {
        films.add(film);
        film.getGenres().add(this);
        return this;
    }

    public Genre removeFilm(Film film) {
        films.remove(film);
        film.getGenres().remove(this);
        return this;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;
        if(genre.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Genre{" +
            "id=" + id +
            ", genreName='" + genreName + "'" +
            '}';
    }
}
