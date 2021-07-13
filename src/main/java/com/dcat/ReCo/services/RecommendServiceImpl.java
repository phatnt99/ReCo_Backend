package com.dcat.ReCo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.repositories.ml.ItemDistanceRepository;
import com.dcat.ReCo.repositories.ml.ProfileDistanceRepository;
import com.dcat.ReCo.repositories.ml.UserCollabRepository;
import com.dcat.ReCo.utils.FunctionUtil;

@Service
public class RecommendServiceImpl implements RecommendService {

	@Autowired
	private ProfileDistanceRepository profileDistanceRepository;

	@Autowired
	private ItemDistanceRepository itemDistanceRepository;

	@Autowired
	private UserCollabRepository userCollabRepository;

	public List<Restaurant> getTop20FCB(Long userId, double lng, double lat) {

		return profileDistanceRepository.getTop20FCB(userId,
				PageRequest.of(0, 15)).stream().map(res -> {

					if (lng == -1 || lat == -1
							|| res.getAddress().getLongtitude() == null
							|| res.getAddress().getLatitude() == null) {
						res.setDistance(null);
						return res;
					}

					res.setDistance(FunctionUtil.calDistance(lng, lat,
							res.getAddress().getLongtitude(),
							res.getAddress().getLatitude()));

					return res;
				}).collect(Collectors.toList());
	}

	public List<Restaurant> getTop10ICB(Long restaurantId, double lng,
			double lat) {

		return itemDistanceRepository.getTop10RelatedRestaurant(restaurantId,
				PageRequest.of(0, 10)).stream().map(res -> {

					if (lng == -1 || lat == -1
							|| res.getAddress().getLongtitude() == null
							|| res.getAddress().getLatitude() == null) {
						res.setDistance(null);
						return res;
					}

					res.setDistance(FunctionUtil.calDistance(lng, lat,
							res.getAddress().getLongtitude(),
							res.getAddress().getLatitude()));

					return res;
				}).collect(Collectors.toList());
	}

	@Override
	public List<Restaurant> getTop20UCB(Long userId, double lng, double lat) {

		return userCollabRepository
				.getTop20Restaurant(userId, PageRequest.of(0, 20)).stream()
				.map(res -> {

					if (lng == -1 || lat == -1
							|| res.getAddress().getLongtitude() == null
							|| res.getAddress().getLatitude() == null) {
						res.setDistance(null);
						return res;
					}

					res.setDistance(FunctionUtil.calDistance(lng, lat,
							res.getAddress().getLongtitude(),
							res.getAddress().getLatitude()));

					return res;
				}).collect(Collectors.toList());
	}

}
