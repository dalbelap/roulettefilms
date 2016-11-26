package com.roulettefilms.roulette.repository;

import com.roulettefilms.roulette.domain.Genre;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Genre entity.
 */
@SuppressWarnings("unused")
public interface GenreRepository extends JpaRepository<Genre,Long> {

}
