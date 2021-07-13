package com.dcat.ReCo.elastics;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.dcat.ReCo.models.Restaurant;

public interface RestaurantSearchRepository extends ElasticsearchRepository<Restaurant, Long> {

}
