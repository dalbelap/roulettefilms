package com.roulettefilms.roulette.repository;

import com.roulettefilms.roulette.domain.FavouriteFilm;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FavouriteFilm entity.
 */
@SuppressWarnings("unused")
public interface FavouriteFilmRepository extends JpaRepository<FavouriteFilm,Long> {

    @Query("select favouriteFilm from FavouriteFilm favouriteFilm where favouriteFilm.owner.login = ?#{principal.username}")
    List<FavouriteFilm> findByOwnerIsCurrentUser();

}
