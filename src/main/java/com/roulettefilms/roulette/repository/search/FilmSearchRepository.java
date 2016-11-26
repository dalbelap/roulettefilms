package com.roulettefilms.roulette.repository.search;

import com.roulettefilms.roulette.domain.Film;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Film entity.
 */
public interface FilmSearchRepository extends ElasticsearchRepository<Film, Long> {
}
