package com.dcat.ReCo.services.voucher;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.dtos.VoucherEditDTO;
import com.dcat.ReCo.dtos.address.AddressTransformer;
import com.dcat.ReCo.dtos.restaurant.RestaurantOnlyName;
import com.dcat.ReCo.dtos.restaurant.RestaurantTransformer;
import com.dcat.ReCo.dtos.voucher.VoucheDetail;
import com.dcat.ReCo.dtos.voucher.VoucherBulkDeleteDTO;
import com.dcat.ReCo.dtos.voucher.VoucherCreateDTO;
import com.dcat.ReCo.dtos.voucher.VoucherList;
import com.dcat.ReCo.dtos.voucher.VoucherTop10;
import com.dcat.ReCo.dtos.voucher.VoucherTransformer;
import com.dcat.ReCo.models.Notification;
import com.dcat.ReCo.models.Voucher;
import com.dcat.ReCo.repositories.NotificationRepository;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.repositories.VoucherRepository;
import com.dcat.ReCo.services.firebase.FirebaseService;
import com.dcat.ReCo.utils.FunctionUtil;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
public class VoucherServiceImpl implements VoucherService {

	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	public VoucherServiceImpl() {
		super();
	}

	private VoucherTransformer entityToResponse(Voucher v) {
		VoucherTransformer vt = new VoucherTransformer();

		vt.setCode(v.getCode());
		vt.setContent(v.getContent());
		vt.setFromTime(v.getFromTime());
		vt.setToTime(v.getToTime());
		vt.setId(v.getId());
		vt.setImage(v.getImage());
		vt.setTitle(v.getTitle());
		vt.setToTime(v.getToTime());
		vt.setValue(v.getValue());
		vt.setCount((long) v.getReservations().size());

		// map restaurant
		AddressTransformer at = new AddressTransformer();
		RestaurantTransformer rt = new RestaurantTransformer();
		at.setId(v.getRestaurant().getAddress().getId());
		at.setDetail(v.getRestaurant().getAddress().getDetail());
		at.setLatitude(v.getRestaurant().getAddress().getLatitude());
		at.setLongtitude(v.getRestaurant().getAddress().getLongtitude());

		rt.setAddress(at);
		rt.setId(v.getRestaurant().getId());
		rt.setApproveStatus(v.getRestaurant().getApproveStatus());
		rt.setCarousel(v.getRestaurant().getCarousel());
		rt.setCreatedAt(v.getRestaurant().getCreatedAt());
		rt.setName(v.getRestaurant().getName());
		rt.setLogo(v.getRestaurant().getLogo());
		rt.setStarAverage(v.getRestaurant().getStarAverage());
		rt.setUserLikeCount((long) v.getRestaurant().getUserLikes().size());
		rt.setReservationCount((long) v.getRestaurant().getLegalReservations().size());
		rt.setSuitable(v.getRestaurant().getSuitable());

		vt.setRestaurant(rt);

		return vt;
	}

	@Override
	public Page<VoucherTransformer> getAllByRestaurant(Long restaurantId, Pageable pageable) {
		Page<Voucher> vouchers = voucherRepository.findAllByRestaurantId(restaurantId, pageable);
		Page<VoucherTransformer> respVouchers = vouchers.map(v -> entityToResponse(v));

		return respVouchers;
	}

	@Override
	public Voucher createOne(VoucherCreateDTO dto) {
		// convert to Model
		Voucher voucher = new Voucher();

		voucher.setTitle(dto.getTitle());
		voucher.setValue(dto.getValue());
		voucher.setRestaurant(restaurantRepository.getOne(dto.getRestaurantId()));
		voucher.setCode(dto.getCode());
		voucher.setFromTime(dto.getFromTime());
		voucher.setToTime(dto.getToTime());
		voucher.setImage(dto.getImage());
		// list ID
		voucher.setListRelation(dto.getListRelation());

		voucher = voucherRepository.save(voucher);

		// push notification

		try {
			Notification noti = notificationRepository.save(Notification.fromVoucher(voucher));
			FirebaseService.getInstance().pushNotiToAll(noti);
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return voucher;
	}

	@Override
	public VoucheDetail getOne(Long id) {
		VoucheDetail voucher = voucherRepository.findOneById(id);

		return voucher;
	}

	@Override
	public Page<VoucherTransformer> getAll(Pageable pageable) {
		Page<Voucher> vouchers = voucherRepository.findAll(pageable);
		Page<VoucherTransformer> respVouchers = vouchers.map(v -> entityToResponse(v));
		return respVouchers;
	}

	public void updateOne(VoucherEditDTO dto) {
		Voucher entity = dto.toEntity();
		entity.setRestaurant(restaurantRepository.findById(dto.getRestaurantId()).orElseGet(null));

		voucherRepository.save(entity);
	}

	@Override
	public void bulkDelete(VoucherBulkDeleteDTO dto) {

		List<Voucher> vouchers = voucherRepository.findAllById(dto.getBulkIds());

		voucherRepository.deleteAll(vouchers);

	}

	@Override
	public Page<VoucherList> getAllByOwner(Long id, Pageable pageable) {
		// get all restaurants are responsible by owner
		List<RestaurantOnlyName> restaurants = restaurantRepository.findByOwnerId(id);
		List<Long> ids = restaurants.stream().map(res -> res.getId()).collect(Collectors.toList());
		// get vouchers of each restaurant

		return voucherRepository.findByRestaurantIdIn(ids, pageable);
	}

	@Override
	public List<VoucherTop10> getTop10() {

		return voucherRepository.findTop10ByOrderByUpdatedAtDesc().stream()
				.filter(v -> FunctionUtil.isRunning(v.getFromTime(), v.getToTime())).collect(Collectors.toList());
	}

	public Page<VoucherTransformer> search(String query, Pageable pageable) {
		
		return voucherRepository.findByCodeContainsOrTitleContains(query, query, pageable).map(v -> entityToResponse(v));
	}
}
