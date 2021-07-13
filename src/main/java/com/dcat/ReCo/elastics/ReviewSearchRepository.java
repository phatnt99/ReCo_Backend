package com.dcat.ReCo.elastics;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.dcat.ReCo.models.Review;

public interface ReviewSearchRepository extends ElasticsearchRepository<Review, Long> {

}
