package com.dcat.ReCo.dtos.restaurant;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.dcat.ReCo.dtos.BaseTransformer;
import com.dcat.ReCo.dtos.JsonMapper;
import com.dcat.ReCo.dtos.address.AddressTransformer;
import com.dcat.ReCo.dtos.tag.TagTransformer;
import com.dcat.ReCo.models.Restaurant;

public class RestaurantTransformer extends BaseTransformer
		implements JsonMapper<RestaurantTransformer, Restaurant> {

	String          logo;
	String          name;
	String          introduction;
	Integer         approveStatus;
	Double          starAverage;
	String          carousel;
	Double          reviewPoint;
	Long            userLikeCount;
	Long            reservationCount;
	Long            reviewCount;
	private Long    commentCount;
	String          cuisine;
	String          suitable;
	private Double  minPrice;
	private Double  maxPrice;
	private String  openTime;
	private Integer star1;
	private Integer star2;
	private Integer star3;
	private Integer star4;
	private Integer star5;
	private Double  starFood;
	private Double  starAmbiance;
	private Double  starService;
	private Double  starNoise;
	private String  space;
	private String  parking;
	private String  payment;
	private String  menu;
	private Long    ownerId;
	private Double distance;
	//
	AddressTransformer address;

	List<TagTransformer> tags;

	public RestaurantTransformer() {
		super();
	}

	public RestaurantTransformer(Long id, String name, Integer status,
			String address,
			Double reviewPoint,
			Long reservationCount,
			Long userLikeCount,
			Long reviewCount, Date createdAt, String logo) {
		super();
		this.id               = id;
		this.name             = name;
		this.approveStatus    = status;
		this.userLikeCount    = userLikeCount;
		this.reservationCount = reservationCount;
		this.reviewCount      = reviewCount;
		this.address          = new AddressTransformer();
		this.address.setDetail(address);
		this.reviewPoint = reviewPoint;
		this.createdAt   = createdAt;
		this.logo        = logo;
	}

	public List<TagTransformer> getTags() {
		return tags;
	}

	public void setTags(List<TagTransformer> tags) {
		this.tags = tags;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public Integer getStar1() {
		return star1;
	}

	public void setStar1(Integer star1) {
		this.star1 = star1;
	}

	public Integer getStar2() {
		return star2;
	}

	public void setStar2(Integer star2) {
		this.star2 = star2;
	}

	public Integer getStar3() {
		return star3;
	}

	public void setStar3(Integer star3) {
		this.star3 = star3;
	}

	public Integer getStar4() {
		return star4;
	}

	public void setStar4(Integer star4) {
		this.star4 = star4;
	}

	public Integer getStar5() {
		return star5;
	}

	public void setStar5(Integer star5) {
		this.star5 = star5;
	}

	public Double getStarFood() {
		return starFood;
	}

	public void setStarFood(Double starFood) {
		this.starFood = starFood;
	}

	public Double getStarAmbiance() {
		return starAmbiance;
	}

	public void setStarAmbiance(Double starAmbiance) {
		this.starAmbiance = starAmbiance;
	}

	public Double getStarService() {
		return starService;
	}

	public void setStarService(Double starService) {
		this.starService = starService;
	}

	public Double getStarNoise() {
		return starNoise;
	}

	public void setStarNoise(Double starNoise) {
		this.starNoise = starNoise;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getStarAverage() {
		return starAverage;
	}

	public void setStarAverage(Double starAverage) {
		this.starAverage = starAverage;
	}

	public String getCarousel() {
		return carousel;
	}

	public void setCarousel(String carousel) {
		this.carousel = carousel;
	}

	public AddressTransformer getAddress() {
		return address;
	}

	public void setAddress(AddressTransformer address) {
		this.address = address;
	}

	public Long getUserLikeCount() {
		return userLikeCount;
	}

	public void setUserLikeCount(Long userLikeCount) {
		this.userLikeCount = userLikeCount;
	}

	public Long getReservationCount() {
		return reservationCount;
	}

	public void setReservationCount(Long reservationCount) {
		this.reservationCount = reservationCount;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getSuitable() {
		return suitable;
	}

	public void setSuitable(String suitable) {
		this.suitable = suitable;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer status) {
		this.approveStatus = status;
	}

	public Double getReviewPoint() {
		return reviewPoint;
	}

	public void setReviewPoint(Double reviewPoint) {
		this.reviewPoint = reviewPoint;
	}

	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	@Override
	public RestaurantTransformer getJson(Restaurant model) {
		ModelMapper mapper = new ModelMapper();

		return mapper.map(model, RestaurantTransformer.class);
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
