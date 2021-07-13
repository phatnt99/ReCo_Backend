package com.dcat.ReCo.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dcat.ReCo.constants.ApproveStatusEnum;
import com.dcat.ReCo.constants.MapperEnum;
import com.dcat.ReCo.dtos.CreateRestaurantDTO;
import com.dcat.ReCo.dtos.CrudRestaurantDTO;
import com.dcat.ReCo.dtos.ModelMapping;
import com.dcat.ReCo.dtos.NearByRestaurantDTO;
import com.dcat.ReCo.dtos.RestaurantDistanceDTO;
import com.dcat.ReCo.dtos.SearchRestaurantDTO;
import com.dcat.ReCo.dtos.responses.RestaurantCheckIn;
import com.dcat.ReCo.dtos.responses.restaurant.CreateOneRestaurant;
import com.dcat.ReCo.dtos.responses.restaurant.EditRestaurant;
import com.dcat.ReCo.dtos.responses.restaurant.RestaurantOverview;
import com.dcat.ReCo.dtos.restaurant.RestaurantOnlyName;
import com.dcat.ReCo.dtos.restaurant.RestaurantTransformer;
import com.dcat.ReCo.dtos.restaurant.UpdateRestaurantDTO;
import com.dcat.ReCo.elastics.RestaurantSearchRepository;
import com.dcat.ReCo.exceptions.RestaurantNotFoundException;
import com.dcat.ReCo.models.Address;
import com.dcat.ReCo.models.Notification;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.RestaurantAndListTag;
import com.dcat.ReCo.models.Tag;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.repositories.AddressRepository;
import com.dcat.ReCo.repositories.DistrictRepository;
import com.dcat.ReCo.repositories.NotificationRepository;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.repositories.TagRepository;
import com.dcat.ReCo.repositories.UserRepository;
import com.dcat.ReCo.repositories.specifications.RestaurantSearchSpec;
import com.dcat.ReCo.services.firebase.FirebaseService;
import com.dcat.ReCo.utils.FunctionUtil;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
@Transactional
public class RestaurantServiceImpl extends BaseService
		implements RestaurantService, ModelMapping<Restaurant, CrudRestaurantDTO> {

	@Autowired
	private EntityManager em;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantSearchRepository searchRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Page<RestaurantTransformer> getAll(Pageable pageable) {
		Page<Restaurant> result = restaurantRepository.findAll(pageable);

		Page<RestaurantTransformer> respRestaurants = result.map(restaurant -> {
			RestaurantTransformer respRestaurant = new RestaurantTransformer().getJson(restaurant);
			setCountAnalys(respRestaurant, restaurant.getId());
			return respRestaurant;
		});

		return respRestaurants;
	}

	@Override
	public RestaurantTransformer getById(Long id, double lng, double lat) {
		Restaurant result = restaurantRepository.findById(id).orElseThrow(RestaurantNotFoundException::new);
		RestaurantTransformer respRestaurant = new RestaurantTransformer().getJson(result);
		if (respRestaurant.getAddress().getLongtitude() == null || respRestaurant.getAddress().getLatitude() == null
				|| lng == -1 || lat == -1) {
			respRestaurant.setDistance(null);
		} else {
			respRestaurant.setDistance(FunctionUtil.calDistance(lng, lat, respRestaurant.getAddress().getLongtitude(),
					respRestaurant.getAddress().getLatitude()));

		}

		setCountAnalys(respRestaurant, id);

		return respRestaurant;
	}

	private void setCountAnalys(RestaurantTransformer respRestaurant, Long id) {
		// counts
		// review, reservation, comment, userLike
		TypedQuery<Long> comQuery = em
				.createQuery("select count(c.id) from Comment c JOIN c.restaurant r WHERE r.id =:id", Long.class);
		comQuery.setParameter("id", id);
		respRestaurant.setCommentCount(comQuery.getSingleResult());
		TypedQuery<Long> revQuery = em
				.createQuery("select count(rev.id) from Review rev JOIN rev.restaurant r WHERE r.id =:id", Long.class);
		revQuery.setParameter("id", id);
		respRestaurant.setReviewCount(revQuery.getSingleResult());
		TypedQuery<Long> reserQuery = em.createQuery(
				"select count(reser.id) from Reservation reser JOIN reser.restaurant r WHERE r.id =:id", Long.class);
		reserQuery.setParameter("id", id);
		respRestaurant.setReservationCount(reserQuery.getSingleResult());
		TypedQuery<Long> ulQuery = em
				.createQuery("select count(ul.id) from Restaurant r JOIN r.userLikes ul WHERE r.id =:id", Long.class);
		ulQuery.setParameter("id", id);
		respRestaurant.setUserLikeCount(ulQuery.getSingleResult());
	}

	@Override
	public Page<Restaurant> search(SearchRestaurantDTO searchable, Pageable pageable, Sort sortable,
			boolean sortByDistance, String sortDirection) {
		if (sortByDistance) {
			List<Restaurant> result = restaurantRepository
					.findAll(Specification.where(RestaurantSearchSpec.hasDistrictIn(searchable.getDistrict())
							.and(RestaurantSearchSpec.hasMinPrice(searchable.getMinPrice()).and(RestaurantSearchSpec
									.hasMaxPrice(searchable.getMaxPrice())
									.and(RestaurantSearchSpec.hasTypeIn(searchable.getType())
											.and(RestaurantSearchSpec.hasNationIn(searchable.getNation())
													.and(RestaurantSearchSpec.hasDishesIn(searchable.getDishes()))))))))
					.stream().map(restaurant -> {
						if (restaurant.getAddress().getLatitude() == null
								|| restaurant.getAddress().getLongtitude() == null || searchable.getLatitude() == null
								|| searchable.getLongtitude() == null) {
							return restaurant;
						}
						restaurant.setDistance(FunctionUtil.calDistance(searchable.getLongtitude(),
								searchable.getLatitude(), restaurant.getAddress().getLongtitude(),
								restaurant.getAddress().getLatitude()));
						return restaurant;
					}).collect(Collectors.toList());

			Collections.sort(result, new Comparator<Restaurant>() {
				@Override
				public int compare(Restaurant o1, Restaurant o2) {
					if ("ASC".equals(sortDirection)) {
						return o1.getDistance() - o2.getDistance() > 0 ? 1
								: o1.getDistance() - o2.getDistance() < 0 ? -1 : 0;
					}

					return o2.getDistance() - o1.getDistance() > 0 ? 1
							: o2.getDistance() - o1.getDistance() < 0 ? -1 : 0;
				}
			});
			
			final int start = (int)pageable.getOffset();
			final int end = Math.min((start + pageable.getPageSize()), result.size());
			final Page<Restaurant> page = new PageImpl<Restaurant>(result.subList(start, end), pageable, result.size());

			return page;
		}
		return restaurantRepository.findAll(
				Specification.where(RestaurantSearchSpec.hasDistrictIn(searchable.getDistrict())
						.and(RestaurantSearchSpec.hasMinPrice(searchable.getMinPrice())
								.and(RestaurantSearchSpec.hasMaxPrice(searchable.getMaxPrice())
										.and(RestaurantSearchSpec.hasTypeIn(searchable.getType())
												.and(RestaurantSearchSpec.hasNationIn(searchable.getNation()).and(
														RestaurantSearchSpec.hasDishesIn(searchable.getDishes()))))))),
				pageable).map(restaurant -> {
					if (restaurant.getAddress().getLatitude() == null || restaurant.getAddress().getLongtitude() == null
							|| searchable.getLatitude() == null || searchable.getLongtitude() == null) {
						return restaurant;
					}
					restaurant
							.setDistance(FunctionUtil.calDistance(searchable.getLongtitude(), searchable.getLatitude(),
									restaurant.getAddress().getLongtitude(), restaurant.getAddress().getLatitude()));
					return restaurant;
				});
	}

	@Override
	public boolean checkExistById(Long id) {

		return restaurantRepository.existsById(id);
	}

	@Override
	public Page<RestaurantCheckIn> getAllCheckin(Pageable pageable) {

		return restaurantRepository.findAllCheckIn(pageable);
	}

	@Override
	public Page<RestaurantOverview> getAllOverview(Pageable pageable) {

		return restaurantRepository.findAllOverview(pageable);
	}

	@Override
	public CreateOneRestaurant createOne(CreateRestaurantDTO createRestaurantDTO) {
		// Create new restaurant without image
		Restaurant newRestaurant = restaurantRepository.save(getModel(createRestaurantDTO, MapperEnum.CREATE));
		// Create image from Multipart file
		String logoUrl = createRestaurantDTO.getLogo() != null
				? FirebaseService.getInstance().uploadImage(createRestaurantDTO.getLogo())
				: null;
		StringJoiner carouselBuilder = new StringJoiner("&");
		StringJoiner menuBuilder = new StringJoiner(";");
		for (MultipartFile carousel : createRestaurantDTO.getCarousel()) {
			carouselBuilder.add(FirebaseService.getInstance().uploadImage(carousel));
		}
		for (MultipartFile menu : createRestaurantDTO.getMenu()) {
			menuBuilder.add(FirebaseService.getInstance().uploadImage(menu));
		}
		// Update image link
		newRestaurant.setLogo(logoUrl);
		newRestaurant.setCarousel(carouselBuilder.toString());
		newRestaurant.setMenu(menuBuilder.toString());

		newRestaurant = restaurantRepository.save(newRestaurant);

		// add to elastic
		// create new for elastic
		Restaurant resIdx = new Restaurant();
		resIdx.setId(newRestaurant.getId());
		resIdx.setCuisine(newRestaurant.getCuisine());
		resIdx.setSuitable(newRestaurant.getSuitable());
		resIdx.setSpace(newRestaurant.getSpace());
		resIdx.setParking(newRestaurant.getParking());
		resIdx.setName(newRestaurant.getName());
		resIdx.setIntroduction(newRestaurant.getIntroduction());
		resIdx.setAddressDetail(newRestaurant.getAddress().getDetail());
		resIdx.setOwnerName(newRestaurant.getOwner() != null ? newRestaurant.getOwner().getFullName() : null);

		searchRepository.save(resIdx);

		return new CreateOneRestaurant().getDTO(newRestaurant);
	}

	@Override
	public Restaurant getModel(CrudRestaurantDTO dto, MapperEnum type) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(dto.getName());
		restaurant.setIntroduction(dto.getIntroduction());
		restaurant.setCuisine(dto.getCuisine());
		restaurant.setSuitable(dto.getSuitable());
		restaurant.setSpace(dto.getSpace());
		restaurant.setParking(dto.getParking());
		restaurant.setMinPrice(dto.getMinPrice());
		restaurant.setMaxPrice(dto.getMaxPrice());
		restaurant.setOpenTime(dto.getOpenTime());
		// Process payment method
		StringJoiner paymentBuilder = new StringJoiner("&");
		for (String p : dto.getPayment()) {
			paymentBuilder.add(p);
		}
		restaurant.setPayment(paymentBuilder.toString());

		// Process tags relationship
		List<Tag> tags = (List<Tag>) tagRepository.findAllById(dto.getTags());
		restaurant.setTags(tags);

		Address address = new Address();
		address.setDetail(dto.getAddress());
		address.setLatitude(dto.getLatitude());
		address.setLongtitude(dto.getLongtitude());
		address.setDistrict(districtRepository.getOne(dto.getDistrict()));

		// Process update Owner
		User user = userRepository.findById(dto.getOwnerId()).orElse(null);
		restaurant.setOwner(user);

		if (type.equals(MapperEnum.UPDATE)) {
			restaurant.setId(((UpdateRestaurantDTO) dto).getId());
			address.setId(((UpdateRestaurantDTO) dto).getAddressId());
		}

		address = addressRepository.save(address);
		restaurant.setAddress(address);
		return restaurant;
	}

	@Override
	public EditRestaurant getOneEdit(Long id) {

		return new EditRestaurant()
				.getDTO(restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException()));
	}

	public void initElastic() {
		List<Restaurant> dbRestaurant = restaurantRepository.findAll();

		for (Restaurant r : dbRestaurant) {
			Restaurant resIdx = new Restaurant();
			resIdx.setId(r.getId());
			resIdx.setCuisine(r.getCuisine());
			resIdx.setSuitable(r.getSuitable());
			resIdx.setSpace(r.getSpace());
			resIdx.setParking(r.getParking());
			resIdx.setName(r.getName());
			resIdx.setIntroduction(r.getIntroduction());
			resIdx.setAddressDetail(r.getAddress() != null ? r.getAddress().getDetail() : null);
			resIdx.setOwnerName(r.getOwner() != null ? r.getOwner().getFullName() : null);

			searchRepository.save(resIdx);
		}
	}

	@Override
	public void updateOne(UpdateRestaurantDTO dto) {

		Restaurant updateRestaurant = restaurantRepository.findById(dto.getId()).get();

		// process logo
		if (dto.getLogo() != null) {
			// has changed logo
			String newLogo = FirebaseService.getInstance().uploadImage(dto.getLogo());
			updateRestaurant.setLogo(newLogo);
		}

		// process carousel
		StringJoiner carouselBuilder = new StringJoiner("&");

		if (dto.getCarousel() != null && dto.getCarousel().size() > 0) {
			for (MultipartFile file : dto.getCarousel()) {
				String newCarousel = FirebaseService.getInstance().uploadImage(file);
				carouselBuilder.add(newCarousel);
			}
		}

		// same case of menuBuilder
		carouselBuilder.add(dto.getCarouselUrl());

		updateRestaurant.setCarousel(carouselBuilder.toString());

		// process menu
		StringJoiner menuBuilder = new StringJoiner(";");

		if (dto.getMenu() != null && dto.getMenu().size() > 0) {
			for (MultipartFile file : dto.getMenu()) {
				String newMenu = FirebaseService.getInstance().uploadImage(file);
				menuBuilder.add(newMenu);
			}
		}

		// add all current menu url
		// in case url is changed, just assign all of its to menuBuilder
		menuBuilder.add(dto.getMenuUrl());

		updateRestaurant.setMenu(menuBuilder.toString());

		// Process tags relationship
		List<Tag> tags = (List<Tag>) tagRepository.findAllById(dto.getTags());
		updateRestaurant.setTags(tags);

		// update text field
		Restaurant modelDto = getModel(dto, MapperEnum.UPDATE);
		updateRestaurant.setName(modelDto.getName());
		updateRestaurant.setIntroduction(modelDto.getIntroduction());
		updateRestaurant.setCuisine(modelDto.getCuisine());
		updateRestaurant.setSuitable(modelDto.getSuitable());
		updateRestaurant.setSpace(modelDto.getSpace());
		updateRestaurant.setParking(modelDto.getParking());
		updateRestaurant.setMinPrice(modelDto.getMinPrice());
		updateRestaurant.setMaxPrice(modelDto.getMaxPrice());
		updateRestaurant.setOpenTime(modelDto.getOpenTime());
		updateRestaurant.setPayment(modelDto.getPayment());
		updateRestaurant.setAddress(modelDto.getAddress());
		updateRestaurant.setOwner(modelDto.getOwner());

		restaurantRepository.save(updateRestaurant);

		// add to elastic
		// create new for elastic
		Restaurant resIdx = new Restaurant();
		resIdx.setId(updateRestaurant.getId());
		resIdx.setCuisine(updateRestaurant.getCuisine());
		resIdx.setSuitable(updateRestaurant.getSuitable());
		resIdx.setSpace(updateRestaurant.getSpace());
		resIdx.setParking(updateRestaurant.getParking());
		resIdx.setName(updateRestaurant.getName());
		resIdx.setIntroduction(updateRestaurant.getIntroduction());
		resIdx.setAddressDetail(updateRestaurant.getAddress().getDetail());
		resIdx.setOwnerName(updateRestaurant.getOwner() != null ? updateRestaurant.getOwner().getFullName() : null);

		searchRepository.save(resIdx);
	}

	@Override
	public void deleteOne(Long id) {

		restaurantRepository.deleteById(id);

		searchRepository.deleteById(id);
	}

	@Override
	public void approve(Long id, ApproveStatusEnum status) {
		Restaurant approveRestaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException());

		approveRestaurant.setApproveStatus(status.getValue());

		if (status.getValue() == ApproveStatusEnum.ACCEPT.getValue()) {
			// noti
			try {
				Notification noti = notificationRepository.save(Notification.fromRes(approveRestaurant));
				FirebaseService.getInstance().pushNotiToAll(noti);
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		restaurantRepository.save(approveRestaurant);
	}

	@Override
	public Page<RestaurantTransformer> getAllByOwner(Long id, Pageable pageable) {

		return restaurantRepository.findAllByOwner(id, pageable);
	}

	@Override
	public void bulkApprove(List<Long> ids, Integer type) {
		List<Restaurant> restaurants = restaurantRepository.findAllById(ids);
		restaurants.stream().forEach(restaurant -> restaurant.setApproveStatus(type));

		restaurantRepository.saveAll(restaurants);
	}

	@Override
	public void bulkDelete(List<Long> ids) {
		List<Restaurant> restaurants = restaurantRepository.findAllById(ids);

		restaurantRepository.deleteAll(restaurants);
	}

	@Override
	public List<String> getAllWithName() {

		return restaurantRepository.findAllWithName();
	}

	@Override
	public List<Restaurant> getNearby(NearByRestaurantDTO dto) {
		// get all restaurant
		List<RestaurantDistanceDTO> restaurants = restaurantRepository.findAllWithAddress();
		// create map hold id and distance
		Map<Long, Double> idAndDistanceHolder = new HashMap<Long, Double>();
		// caculate point with current point
		restaurants = restaurants.stream().map(r -> {
			RestaurantDistanceDTO rDto = FunctionUtil.distance(r, dto);
			idAndDistanceHolder.put(rDto.getId(), rDto.getDistance());
			return rDto;
		}).collect(Collectors.toList());

		Collections.sort(restaurants, new Comparator<RestaurantDistanceDTO>() {
			@Override
			public int compare(RestaurantDistanceDTO o1, RestaurantDistanceDTO o2) {
				if (o1.getDistance() - o2.getDistance() > 0) {
					return 1;
				} else if (o1.getDistance() - o2.getDistance() == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		});

		restaurants = restaurants.stream().filter(r -> r.getDistance() != -1d).limit(30).collect(Collectors.toList());

		// get restaurant reference
		List<Restaurant> restaurantRefs = restaurantRepository
				.findAllById(restaurants.stream().map(r -> r.getId()).collect(Collectors.toList()));

		// set distance
		restaurantRefs.stream().map(res -> {
			double distance = idAndDistanceHolder.get(res.getId());
			res.setDistance(distance);
			return res;
		}).collect(Collectors.toList());

		Collections.sort(restaurantRefs, new Comparator<Restaurant>() {
			@Override
			public int compare(Restaurant o1, Restaurant o2) {
				if (o1.getDistance() - o2.getDistance() > 0) {
					return 1;
				} else if (o1.getDistance() - o2.getDistance() == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		});

		return restaurantRefs;
	}

	@Override
	public List<RestaurantAndListTag> getA() {

		return restaurantRepository.findBy(RestaurantAndListTag.class);
	}

	@Override
	public List<Restaurant> getTop20BasedOnProfile(Long userId) {
		// get Top 20 restaurant Id based on userid

		return null;
	}

	@Override
	public List<RestaurantOnlyName> getAll2() {

		return restaurantRepository.findBy(RestaurantOnlyName.class);
	}

	@Override
	public List<RestaurantOnlyName> getAllByOwner(Long id) {

		return restaurantRepository.findByOwnerId(id);
	}

	@Override
	public List<Restaurant> getTop10RatedRestaurant() {

		return restaurantRepository.findTop10ByOrderByStarAverageDesc();
	}

	public Page<Restaurant> getByTag(Long tagId, Pageable pageable, double lng, double lat) {

		return restaurantRepository.findByTagsId(tagId, pageable).map(res -> {
			if (res.getAddress().getLongtitude() == null || res.getAddress().getLatitude() == null || lng == -1
					|| lat == -1) {
				res.setDistance(null);
			} else {
				res.setDistance(FunctionUtil.calDistance(lng, lat, res.getAddress().getLongtitude(),
						res.getAddress().getLatitude()));

			}

			return res;
		});
	}

	@Override
	public List<Restaurant> getTop10MostViewedByUser(Long userId, double lng, double lat) {

		return restaurantRepository.getTop10MostViewedByUser(userId, PageRequest.of(0, 10)).stream().map(restaurant -> {
			if (restaurant.getAddress().getLatitude() == null || restaurant.getAddress().getLongtitude() == null
					|| lat == -1 || lng == -1) {
				return restaurant;
			}
			restaurant.setDistance(FunctionUtil.calDistance(lng, lat, restaurant.getAddress().getLongtitude(),
					restaurant.getAddress().getLatitude()));
			return restaurant;
		}).collect(Collectors.toList());
	}

	@Override
	public List<Restaurant> getTop10MostViewedByIp(String ip, double lng, double lat) {

		return restaurantRepository.getTop10MostViewedByIp(ip, PageRequest.of(0, 10)).stream().map(restaurant -> {
			if (restaurant.getAddress().getLatitude() == null || restaurant.getAddress().getLongtitude() == null
					|| lat == -1 || lng == -1) {
				return restaurant;
			}
			restaurant.setDistance(FunctionUtil.calDistance(lng, lat, restaurant.getAddress().getLongtitude(),
					restaurant.getAddress().getLatitude()));
			return restaurant;
		}).collect(Collectors.toList());
	}

	@Override
	public List<Restaurant> getTop20MostViewedOverall(double lng, double lat) {

		return restaurantRepository.getTop20MostViewedOverall(PageRequest.of(0, 15)).stream().map(restaurant -> {
			if (restaurant.getAddress().getLatitude() == null || restaurant.getAddress().getLongtitude() == null
					|| lat == -1 || lng == -1) {
				return restaurant;
			}
			restaurant.setDistance(FunctionUtil.calDistance(lng, lat, restaurant.getAddress().getLongtitude(),
					restaurant.getAddress().getLatitude()));
			return restaurant;
		}).collect(Collectors.toList());
	}
}
