package com.dcat.ReCo.models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.dcat.ReCo.constants.ApproveStatusEnum;
import com.dcat.ReCo.models.ml.ItemDistance;
import com.dcat.ReCo.models.ml.ProfileDistance;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "restaurants")
@Document(indexName = "restaurantidx")
public class Restaurant extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Field(type = FieldType.Text, name = "name")
	private String name;

	@Column(name = "approve_status")
	private Integer approveStatus;

	@Column(name = "carousel")
	private String carousel;

	@Column(name = "logo")
	private String logo;

	@Column(name = "introduction")
	@Field(type = FieldType.Text, name = "introduction")
	private String introduction;

	// @Column(name = "short_description")
	// private String shortDescription;

	@Column(name = "min_price")
	private Double minPrice;

	@Column(name = "max_price")
	private Double maxPrice;

	@Column(name = "time_open")
	private String openTime;

	@Column(name = "star_1")
	// General
	private Integer star1;

	@Column(name = "star_2")
	// Food
	private Integer star2;

	@Column(name = "star_3")
	// Service
	private Integer star3;

	@Column(name = "star_4")
	// Space
	private Integer star4;

	@Column(name = "star_5")
	// Ambiance
	private Integer star5;

	@Column(name = "star_average")
	private Double starAverage;

	@Column
	private Double starFood;

	@Column
	private Double starAmbiance;

	@Column
	private Double starService;

	@Column
	private Double starNoise;

	@Column(name = "review_point")
	private Double reviewPoint;

	@Column(name = "cuisine")
	@Field(type = FieldType.Text, name = "cuisine")
	private String cuisine;

	@Column(name = "suitable")
	@Field(type = FieldType.Text, name = "suitable")
	private String suitable;

	@Column(name = "space")
	@Field(type = FieldType.Text, name = "space")
	private String space;

	@Column(name = "parking")
	@Field(type = FieldType.Text, name = "parking")
	private String parking;

	@Column(name = "payment")
	@Field(type = FieldType.Text, name = "payment")
	private String payment;

	@Column(name = "menu")
	private String menu;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<Campaign> campaigns;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Comment> comments;

	@OneToMany(mappedBy = "restaurant")
	@JsonBackReference
	private List<Review> reviews;

	@ManyToMany
	@JoinTable(name = "tag_restaurant", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;

	@ManyToMany
	@JoinTable(name = "user_like", joinColumns = @JoinColumn(name = "likeable_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@WhereJoinTable(clause = "likeable_type='RESTAURANT'")
	@JsonBackReference
	private List<User> userLikes;

	@OneToMany(mappedBy = "restaurant")
	private List<UserFRestaurant> followedUsers;

	@OneToMany(mappedBy = "restaurant")
	private List<Reservation> reservations;

	@OneToMany(mappedBy = "restaurant")
	@Where(clause = "type <> 3")
	@JsonBackReference
	private List<Reservation> legalReservations;
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ProfileDistance> distanceWithProfiles;
	
	@OneToMany(mappedBy = "restaurantBase", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ItemDistance> distanceWithItems;
	
	@OneToMany(mappedBy = "restaurantChild", cascade = CascadeType.ALL)
	private List<ItemDistance> distanceWithOthers;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	@JsonBackReference
	private User owner;

	@OneToMany(mappedBy = "restaurant")
	@JsonIgnore
	private List<Voucher> vouchers;
	
	@OneToMany(mappedBy = "restaurant")
	@JsonIgnore
	private List<History> histories;

	// for elastic
	@Transient
	private String ownerName;

	@Transient
	private String addressDetail;

	@Transient
	// for calculate coordinate
	private Double distance;

	public Restaurant() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
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

	public Double getStarAverage() {
		return starAverage;
	}

	public void setStarAverage(Double starAverage) {
		this.starAverage = starAverage;
	}

	public Double getReviewPoint() {
		return reviewPoint;
	}

	public void setReviewPoint(Double reviewPoint) {
		this.reviewPoint = reviewPoint;
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

	public String getCarousel() {
		return carousel;
	}

	public void setCarousel(String carousel) {
		this.carousel = carousel;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<User> getUserLikes() {
		return userLikes;
	}

	public void setUserLikes(List<User> userLikes) {
		this.userLikes = userLikes;
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

	@PrePersist
	public void setDefaultApproveStatus() {
		this.approveStatus = ApproveStatusEnum.WAITING.getValue();
		this.star1         = 0;
		this.star2         = 0;
		this.star3         = 0;
		this.star4         = 0;
		this.star5         = 0;
		this.reviewPoint   = (double) 0;
		this.starAverage   = (double) 0;
		this.starAmbiance  = 0.0;
		this.starFood      = 0.0;
		this.starNoise     = 0.0;
		this.starService   = 0.0;
	}

	// Getters
	public List<Reservation> getLegalReservations() {
		return legalReservations;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getNewestVoucher() {
		if (this.vouchers == null || this.vouchers.isEmpty()) {
			return null;
		}

		Collections.sort(this.vouchers, new Comparator<Voucher>() {

			@Override
			public int compare(Voucher o1, Voucher o2) {
				return o2.getUpdatedAt().compareTo(o1.getUpdatedAt());
			}

		});

		return this.vouchers.get(0).getCode();
	}
	
	public int getCommentCount() {
		if(this.comments == null)
			return 0;
		
		return this.comments.size();
	}
}
