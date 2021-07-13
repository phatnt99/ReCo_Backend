package com.dcat.ReCo.vos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollabUserVO {
	private Long                     userId;
	private List<CollabRestaurantVO> restaurants;
	private List<CollabRestaurantVO> predictRestaurants;
	private Double                   avg;
	private List<OtherVO>            others;

	public double getRatingForRestaurant(Long restaurantId) {
		for (CollabRestaurantVO res : restaurants) {
			if (res.getRestaurantId() == restaurantId)
				return res.getRating();
		}

		return 0;
	}

	public void normalize() {
		double numerator   = restaurants.stream().reduce(0.0,
				(sum, res) -> sum + res.getRating(),
				Double::sum);
		int    denominator = restaurants.stream()
				.filter(res -> res.getRating() != 0)
				.collect(Collectors.toList()).size();
		double avg         = numerator / denominator;
		this.avg = avg;

		restaurants = restaurants.stream().map(res -> {
			res.setRating(res.getRating() != 0 ? res.getRating() - avg : 0);
			return res;
		}).collect(Collectors.toList());

	}

	public CollabUserVO() {
		restaurants = new ArrayList<CollabRestaurantVO>();
		others      = new ArrayList<OtherVO>();
	}

	public void addNewRating(Long resId, Double rating) {
		restaurants.add(new CollabRestaurantVO(resId, rating));
	}

	public void addNewUserDistance(Long userId, Double distance) {
		others.add(new OtherVO(userId, distance));
	}

	public List<OtherVO> getOthers() {
		return others;
	}

	public void setOthers(List<OtherVO> others) {
		this.others = others;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CollabRestaurantVO> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<CollabRestaurantVO> restaurants) {
		this.restaurants = restaurants;
	}

	public static class CollabRestaurantVO {
		private Long   restaurantId;
		private Double rating;

		public CollabRestaurantVO(Long restaurantId, Double rating) {
			super();
			this.restaurantId = restaurantId;
			this.rating       = rating;
		}

		public Long getRestaurantId() {
			return restaurantId;
		}

		public void setRestaurantId(Long restaurantId) {
			this.restaurantId = restaurantId;
		}

		public Double getRating() {
			return rating;
		}

		public void setRating(Double rating) {
			this.rating = rating;
		}

		@Override
		public String toString() {
			return "restaurantId=" + restaurantId + "\nrating=" + rating;
		}

	}

	@Override
	public String toString() {
		return "userId=" + userId + "\nrestaurants=" + restaurants
				+ "\npredictRestaurants=" + predictRestaurants + "\navg=" + avg
				+ "\nothers=" + others;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public List<CollabRestaurantVO> getPredictRestaurants() {
		return predictRestaurants;
	}

	public void setPredictRestaurants(
			List<CollabRestaurantVO> predictRestaurants) {
		this.predictRestaurants = predictRestaurants;
	}

	public static class OtherVO {
		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Double getDistance() {
			return distance;
		}

		public void setDistance(Double distance) {
			this.distance = distance;
		}

		private Long   userId;
		private Double distance;

		public OtherVO(Long userId, Double distance) {
			super();
			this.userId   = userId;
			this.distance = distance;
		}

		@Override
		public String toString() {
			return "userId=" + userId + "\ndistance=" + distance;
		}

	}

}
