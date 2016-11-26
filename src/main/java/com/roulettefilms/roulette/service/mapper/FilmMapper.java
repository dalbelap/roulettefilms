package com.roulettefilms.roulette.service.mapper;

import com.roulettefilms.roulette.domain.*;
import com.roulettefilms.roulette.service.dto.FilmDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Film and its DTO FilmDTO.
 */
@Mapper(componentModel = "spring", uses = {GenreMapper.class, })
public interface FilmMapper {

    FilmDTO filmToFilmDTO(Film film);

    List<FilmDTO> filmsToFilmDTOs(List<Film> films);

    Film filmDTOToFilm(FilmDTO filmDTO);

    List<Film> filmDTOsToFilms(List<FilmDTO> filmDTOs);

    default Genre genreFromId(Long id) {
        if (id == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}
