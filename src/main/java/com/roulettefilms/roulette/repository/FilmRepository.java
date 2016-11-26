package com.roulettefilms.roulette.repository;

import com.roulettefilms.roulette.domain.Film;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Film entity.
 */
@SuppressWarnings("unused")
public interface FilmRepository extends JpaRepository<Film,Long> {

    @Query("select distinct film from Film film left join fetch film.genres")
    List<Film> findAllWithEagerRelationships();

    @Query("select film from Film film left join fetch film.genres where film.id =:id")
    Film findOneWithEagerRelationships(@Param("id") Long id);

}
