package com.roulettefilms.roulette.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Genre entity.
 */
public class GenreDTO implements Serializable {

    private Long id;

    @NotNull
    private String genreName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GenreDTO genreDTO = (GenreDTO) o;

        if ( ! Objects.equals(id, genreDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GenreDTO{" +
            "id=" + id +
            ", genreName='" + genreName + "'" +
            '}';
    }
}
