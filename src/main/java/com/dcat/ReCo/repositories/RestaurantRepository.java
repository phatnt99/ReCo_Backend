package com.dcat.ReCo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dcat.ReCo.dtos.RestaurantCB;
import com.dcat.ReCo.dtos.RestaurantDistanceDTO;
import com.dcat.ReCo.dtos.responses.RestaurantCheckIn;
import com.dcat.ReCo.dtos.responses.restaurant.RestaurantOverview;
import com.dcat.ReCo.dtos.restaurant.RestaurantOnlyName;
import com.dcat.ReCo.dtos.restaurant.RestaurantTransformer;
import com.dcat.ReCo.models.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>,
		JpaSpecificationExecutor<Restaurant> {

	@Query("SELECT new com.dcat.ReCo.dtos.responses.RestaurantCheckIn(r.id, r.name, r.logo, r.address.detail) FROM Restaurant r")
	Page<RestaurantCheckIn> findAllCheckIn(Pageable pageable);

	@Query("SELECT new com.dcat.ReCo.dtos.responses.restaurant.RestaurantOverview(r.id, r.name, r.logo, r.address.detail, ow.fullName, r.approveStatus) FROM Restaurant r left join r.owner ow")
	Page<RestaurantOverview> findAllOverview(Pageable pageable);

	@Query("SELECT new com.dcat.ReCo.dtos.restaurant.RestaurantTransformer(r.id, r.name, r.approveStatus, r.address.detail, r.reviewPoint, COUNT(re.id),  COUNT(ul.id),  COUNT(rev.id), r.createdAt, r.logo) FROM Restaurant r LEFT JOIN r.legalReservations re LEFT JOIN r.reviews rev LEFT JOIN r.userLikes ul WHERE r.owner.id=:id GROUP BY r.id")
	Page<RestaurantTransformer> findAllByOwner(@Param("id") Long id,
			Pageable pageable);
	
	List<RestaurantOnlyName> findByOwnerId(Long id);
	
	@Query("SELECT r.name from Restaurant r WHERE r.approveStatus = 2")
	List<String> findAllWithName();
	
	@Query("SELECT new com.dcat.ReCo.dtos.RestaurantDistanceDTO(r.id, a.latitude, a.longtitude) from Restaurant r JOIN r.address a WHERE r.approveStatus = 2")
	List<RestaurantDistanceDTO> findAllWithAddress();
	
	@Query("SELECT new com.dcat.ReCo.dtos.RestaurantCB(r.id, d.id, r.tags) from Restaurant r JOIN r.address a JOIN a.district d")
	List<RestaurantCB> findAllForCB();
	
	<T> T findById(Long id, Class<T> type);
	
	<T> List<T> findBy(Class<T> type);
	
	<T> List<T> findByIdNot(Long id,  Class<T> type);
	
	List<Restaurant> findTop10ByOrderByStarAverageDesc();
	
	Page<Restaurant> findByTagsId(Long tagId, Pageable pageable);
	
	// recent view
	@Query("SELECT r FROM Restaurant r LEFT JOIN r.histories rh WHERE rh.user.id=:uid GROUP BY r.id ORDER BY rh.updatedAt DESC")
	List<Restaurant> getTop10MostViewedByUser(@Param("uid") Long userId, Pageable pageable);
	
	// recent view
	@Query("SELECT r FROM Restaurant r LEFT JOIN r.histories rh WHERE rh.ip=:ip GROUP BY r.id ORDER BY rh.updatedAt DESC")
	List<Restaurant> getTop10MostViewedByIp(@Param("ip") String ip, Pageable pageable);
	
	@Query("SELECT r FROM Restaurant r LEFT JOIN r.histories rh GROUP BY r.id ORDER BY COUNT(rh.id) DESC")
	List<Restaurant> getTop20MostViewedOverall(Pageable pageable);
	}