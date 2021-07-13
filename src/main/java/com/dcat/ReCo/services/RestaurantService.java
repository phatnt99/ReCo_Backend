package com.dcat.ReCo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dcat.ReCo.constants.ApproveStatusEnum;
import com.dcat.ReCo.dtos.CreateRestaurantDTO;
import com.dcat.ReCo.dtos.NearByRestaurantDTO;
import com.dcat.ReCo.dtos.SearchRestaurantDTO;
import com.dcat.ReCo.dtos.responses.RestaurantCheckIn;
import com.dcat.ReCo.dtos.responses.restaurant.CreateOneRestaurant;
import com.dcat.ReCo.dtos.responses.restaurant.EditRestaurant;
import com.dcat.ReCo.dtos.responses.restaurant.RestaurantOverview;
import com.dcat.ReCo.dtos.restaurant.RestaurantOnlyName;
import com.dcat.ReCo.dtos.restaurant.RestaurantTransformer;
import com.dcat.ReCo.dtos.restaurant.UpdateRestaurantDTO;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.RestaurantAndListTag;

public interface RestaurantService {
	public Page<RestaurantTransformer> getAll(Pageable page);

	public RestaurantTransformer getById(Long id, double lng, double lat);

	public Page<Restaurant> search(SearchRestaurantDTO searchable,
			Pageable pageable,
			Sort sort, boolean sortByDistance, String sortDirection);

	public Page<RestaurantCheckIn> getAllCheckin(Pageable pageable);

	public Page<RestaurantOverview> getAllOverview(Pageable pageable);

	public Page<RestaurantTransformer> getAllByOwner(Long id, Pageable pageable);

	public List<RestaurantOnlyName> getAllByOwner(Long id);

	public CreateOneRestaurant createOne(CreateRestaurantDTO dto);

	public EditRestaurant getOneEdit(Long id);

	public void updateOne(UpdateRestaurantDTO dto);

	public void deleteOne(Long id);

	public void approve(Long id, ApproveStatusEnum status);

	void bulkApprove(List<Long> ids, Integer type);

	void bulkDelete(List<Long> ids);

	List<String> getAllWithName();

	List<Restaurant> getNearby(NearByRestaurantDTO dto);

	List<RestaurantAndListTag> getA();

	List<RestaurantOnlyName> getAll2();

	List<Restaurant> getTop20BasedOnProfile(Long userId);

	List<Restaurant> getTop10RatedRestaurant();

	Page<Restaurant> getByTag(Long tagId, Pageable pageable, double longtitude, double latitude);

	List<Restaurant> getTop10MostViewedByUser(Long userId, double lng, double lat);

	List<Restaurant> getTop10MostViewedByIp(String ip, double lng, double lat);

	List<Restaurant> getTop20MostViewedOverall(double lng, double lat);

	void initElastic();
}
