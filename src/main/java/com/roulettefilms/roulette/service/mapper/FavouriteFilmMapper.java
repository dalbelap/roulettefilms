package com.roulettefilms.roulette.service.mapper;

import com.roulettefilms.roulette.domain.*;
import com.roulettefilms.roulette.service.dto.FavouriteFilmDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity FavouriteFilm and its DTO FavouriteFilmDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface FavouriteFilmMapper {

    @Mapping(source = "film.id", target = "filmId")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    FavouriteFilmDTO favouriteFilmToFavouriteFilmDTO(FavouriteFilm favouriteFilm);

    List<FavouriteFilmDTO> favouriteFilmsToFavouriteFilmDTOs(List<FavouriteFilm> favouriteFilms);

    @Mapping(source = "filmId", target = "film")
    @Mapping(source = "ownerId", target = "owner")
    FavouriteFilm favouriteFilmDTOToFavouriteFilm(FavouriteFilmDTO favouriteFilmDTO);

    List<FavouriteFilm> favouriteFilmDTOsToFavouriteFilms(List<FavouriteFilmDTO> favouriteFilmDTOs);

    default Film filmFromId(Long id) {
        if (id == null) {
            return null;
        }
        Film film = new Film();
        film.setId(id);
        return film;
    }
}
