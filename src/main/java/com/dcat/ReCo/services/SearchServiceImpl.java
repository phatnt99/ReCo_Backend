package com.dcat.ReCo.services;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.dtos.RestaurantSearchView;
import com.dcat.ReCo.dtos.responses.review.ReviewOverview;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.Review;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.repositories.ReviewRepository;
import com.dcat.ReCo.utils.FunctionUtil;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ElasticsearchRestTemplate esTemplate;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public Page<Object> searchReview(final String query,
			Pageable pageable) {

		NativeSearchQuery builder = new NativeSearchQueryBuilder()
				.withQuery(
						new MultiMatchQueryBuilder(query, "title", "content",
								"restaurantName", "userName"))
				.build();

		builder.setPageable(pageable);

		SearchHits<Review> result = esTemplate.search(builder, Review.class);
		// convert Hit to Review instance
		SearchPage<Review> resp = SearchHitSupport.searchPageFor(result,
				builder.getPageable());

		return resp.map(reviewHit -> reviewRepository.findById(
				reviewHit.getContent().getId(), ReviewOverview.class));
	}

	public Page<Object> searchRestaurant(final String query,
			Pageable pageable, String type, double lng, double lat) {

		NativeSearchQuery builder = new NativeSearchQueryBuilder()
				.withQuery(
						new MultiMatchQueryBuilder(query, "name",
								"introduction", "cuisine", "suitable", "space",
								"parking", "ownerName", "addressDetail"))
				.build();

		builder.setPageable(pageable);

		SearchHits<Restaurant> result = esTemplate.search(builder,
				Restaurant.class);

		// convert Hit to Review instance
		SearchPage<Restaurant> resp = SearchHitSupport.searchPageFor(result,
				builder.getPageable());
		
		if(!type.isEmpty()) {
			return resp.map(restaurantHit -> restaurantRepository.findById(
					restaurantHit.getContent().getId(), RestaurantSearchView.class));
		}

		return resp.map(restaurantHit -> restaurantRepository.findById(
				restaurantHit.getContent().getId(), Restaurant.class)).map(restaurant -> {
					if (restaurant.getAddress().getLatitude() == null
							|| restaurant.getAddress()
									.getLongtitude() == null
							|| lng == -1
							|| lat == -1) {
						return restaurant;
					}
					restaurant.setDistance(
							FunctionUtil.calDistance(lng,
									lat,
									restaurant.getAddress().getLongtitude(),
									restaurant.getAddress().getLatitude()));
					return restaurant;
				});
	}
}
