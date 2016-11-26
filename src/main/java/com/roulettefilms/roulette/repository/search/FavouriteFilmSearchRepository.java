package com.roulettefilms.roulette.repository.search;

import com.roulettefilms.roulette.domain.FavouriteFilm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the FavouriteFilm entity.
 */
public interface FavouriteFilmSearchRepository extends ElasticsearchRepository<FavouriteFilm, Long> {
}
