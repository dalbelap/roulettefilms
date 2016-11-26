package com.roulettefilms.roulette.service.mapper;

import com.roulettefilms.roulette.domain.*;
import com.roulettefilms.roulette.service.dto.GenreDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Genre and its DTO GenreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenreMapper {

    GenreDTO genreToGenreDTO(Genre genre);

    List<GenreDTO> genresToGenreDTOs(List<Genre> genres);

    @Mapping(target = "films", ignore = true)
    Genre genreDTOToGenre(GenreDTO genreDTO);

    List<Genre> genreDTOsToGenres(List<GenreDTO> genreDTOs);
}
