package com.roulettefilms.roulette.repository.search;

import com.roulettefilms.roulette.domain.Genre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Genre entity.
 */
public interface GenreSearchRepository extends ElasticsearchRepository<Genre, Long> {
}
