package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.CreateRestaurantDTO;
import com.dcat.ReCo.dtos.NearByRestaurantDTO;
import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.dtos.SearchRestaurantDTO;
import com.dcat.ReCo.dtos.restaurant.ApproveRestaurantDTO;
import com.dcat.ReCo.dtos.restaurant.RestaurantApproveDTO;
import com.dcat.ReCo.dtos.restaurant.UpdateRestaurantDTO;
import com.dcat.ReCo.services.RecommendService;
import com.dcat.ReCo.services.RestaurantService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private RecommendService recommendService;

	@GetMapping
	public ResponseEntity<?> getAllRestaurant(@ModelAttribute PageableDTO pageableDto,
			@RequestParam(name = "sortable", required = false, defaultValue = "updatedAt") String sortProp,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {
		Pageable pageable = PageRequest.of(pageableDto.getPage(), pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortProp));

		return new HttpResponse(HttpStatus.OK, true, restaurantService.getAllOverview(pageable)).send();
	}

	public ResponseEntity<?> getAllWithouPaging() {
		return HttpResponse.sendOK(restaurantService.getAll2());
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchRestaurant(@RequestBody SearchRestaurantDTO filters,
			@ModelAttribute PageableDTO pageableDto) {
		Sort sortable = Sort.by(Direction.fromString(pageableDto.getDirection()), pageableDto.getSortable());
		boolean sortByDistance = "distance".equals(pageableDto.getSortable());
		return new HttpResponse(HttpStatus.OK, true, restaurantService.search(filters, pageableDto.get(), sortable, sortByDistance, pageableDto.getDirection())).send();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRestaurantById(@PathVariable Long id,
			@RequestParam(name = "longtitude", defaultValue = "-1") double lng,
			@RequestParam(name = "latitude", defaultValue = "-1") double lat) {

		return HttpResponse.sendOK(restaurantService.getById(id, lng, lat));
	}

	@GetMapping("/owner/{id}")
	public ResponseEntity<?> getRestaurantByOwner(@PathVariable Long id, @ModelAttribute PageableDTO dto) {

		return HttpResponse.sendOK(restaurantService.getAllByOwner(id, dto.get()));
	}

	@GetMapping("/all/owner/{id}")
	public ResponseEntity<?> getAllRestaurantByOwner(@PathVariable Long id) {

		return HttpResponse.sendOK(restaurantService.getAllByOwner(id));
	}

	@GetMapping("/checkin")
	public ResponseEntity<?> getRestaurantForCheckIn(@ModelAttribute PageableDTO pageableDto) {
		Pageable pageable = PageRequest.of(pageableDto.getPage(), pageableDto.getSize(), Sort.by("name"));

		return new HttpResponse(HttpStatus.OK, true, restaurantService.getAllCheckin(pageable)).send();
	}

	@PostMapping
	public ResponseEntity<?> createRestaurant(@ModelAttribute CreateRestaurantDTO createRestaurantDto) {

		return new HttpResponse(HttpStatus.CREATED, true, restaurantService.createOne(createRestaurantDto)).send();
	}

	@GetMapping("/edit/{id}")
	public ResponseEntity<?> getRestaurantEdit(@PathVariable("id") Long id) {

		return new HttpResponse(HttpStatus.OK, true, restaurantService.getOneEdit(id)).send();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRestaurant(@PathVariable Long id, @ModelAttribute UpdateRestaurantDTO dto) {

		restaurantService.updateOne(dto);

		return HttpResponse.sendNoContent();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {

		restaurantService.deleteOne(id);

		return HttpResponse.sendNoContent();
	}

	@PostMapping("/approve")
	public ResponseEntity<?> approveRestaurant(@RequestBody ApproveRestaurantDTO dto) {

		restaurantService.approve(dto.getRestaurantId(), dto.getStatus());

		return HttpResponse.sendNoContent();
	}

	@PostMapping("/bulk-approve")
	public ResponseEntity<?> approveReservations(@RequestBody RestaurantApproveDTO dto) {
		restaurantService.bulkApprove(dto.getIds(), dto.getType());

		return HttpResponse.sendNoContent();
	}

	@PostMapping("/bulk-delete")
	public ResponseEntity<?> deleteReservations(@RequestBody RestaurantApproveDTO dto) {
		restaurantService.bulkDelete(dto.getIds());

		return HttpResponse.sendNoContent();
	}

	@GetMapping("/name")
	public ResponseEntity<?> getAllRestaurantName() {

		return HttpResponse.sendOK(restaurantService.getAllWithName());
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAll2() {

		return HttpResponse.sendOK(restaurantService.getAll2());
	}

	@GetMapping("/nearby")
	public ResponseEntity<?> getRestaurantNearBy(NearByRestaurantDTO dto) {

		return HttpResponse.sendOK(restaurantService.getNearby(dto));
	}

	@GetMapping("/profile-recom")
	public ResponseEntity<?> getContentProfileBased(@RequestParam Long userId,
			@RequestParam(name = "longtitude", defaultValue = "-1") double lng,
			@RequestParam(name = "latitude", defaultValue = "-1") double lat) {

		return HttpResponse.sendOK(recommendService.getTop20FCB(userId, lng, lat));
	}

	@GetMapping("/restaurant-recom")
	public ResponseEntity<?> getContentItemBased(@RequestParam Long restaurantId,
			@RequestParam(name = "longtitude", defaultValue = "-1") double lng,
			@RequestParam(name = "latitude", defaultValue = "-1") double lat) {

		return HttpResponse.sendOK(recommendService.getTop10ICB(restaurantId, lng, lat));
	}

	@GetMapping("/user-collab")
	public ResponseEntity<?> getUserCollab(@RequestParam Long userId,
			@RequestParam(name = "longtitude", defaultValue = "-1") double lng,
			@RequestParam(name = "latitude", defaultValue = "-1") double lat) {

		return HttpResponse.sendOK(recommendService.getTop20UCB(userId, lng, lat));
	}

	@GetMapping("/top10")
	public ResponseEntity<?> getTop10RatedRestaurant() {

		return HttpResponse.sendOK(restaurantService.getTop10RatedRestaurant());
	}

	@GetMapping("/tag/{tagId}")
	public ResponseEntity<?> getRestaurantByTag(@PathVariable Long tagId,
			@RequestParam(name = "longtitude", defaultValue = "-1") double lng,
			@RequestParam(name = "latitude", defaultValue = "-1") double lat, @ModelAttribute PageableDTO pageable) {

		return HttpResponse.sendOK(restaurantService.getByTag(tagId, pageable.get(), lng, lat));
	}

	@GetMapping("/top10/user/{uid}")
	public ResponseEntity<?> getTop10MostViewedByUser(@PathVariable Long uid,
			@RequestParam(name = "longtitude", defaultValue = "-1") double lng,
			@RequestParam(name = "latitude", defaultValue = "-1") double lat) {

		return HttpResponse.sendOK(restaurantService.getTop10MostViewedByUser(uid, lng, lat));
	}

	@GetMapping("/top10/ip/{ip}")
	public ResponseEntity<?> getTop10MostViewedByUser(@PathVariable String ip,
			@RequestParam(name = "longtitude", defaultValue = "-1") double lng,
			@RequestParam(name = "latitude", defaultValue = "-1") double lat) {

		return HttpResponse.sendOK(restaurantService.getTop10MostViewedByIp(ip, lng, lat));
	}

	@GetMapping("/most-viewed")
	public ResponseEntity<?> getTop10MostViewedByUser(
			@RequestParam(name = "longtitude", defaultValue = "-1") double lng,
			@RequestParam(name = "latitude", defaultValue = "-1") double lat) {

		return HttpResponse.sendOK(restaurantService.getTop20MostViewedOverall(lng, lat));
	}

	@GetMapping("/els")
	public ResponseEntity<?> initElastic() {
		restaurantService.initElastic();

		return HttpResponse.sendNoContent();
	}

}
