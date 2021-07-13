package com.dcat.ReCo.services.user;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.constants.UserStatusEnum;
import com.dcat.ReCo.dtos.ApproveDTO;
import com.dcat.ReCo.dtos.user.UserDetail;
import com.dcat.ReCo.dtos.user.UserProfileUpdateDTO;
import com.dcat.ReCo.dtos.user.UserTransformer;
import com.dcat.ReCo.dtos.user.UserUpdateDTO;
import com.dcat.ReCo.dtos.user.projections.DinnerOverviewDTO;
import com.dcat.ReCo.models.Address;
import com.dcat.ReCo.models.Tag;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.repositories.AddressRepository;
import com.dcat.ReCo.repositories.TagRepository;
import com.dcat.ReCo.repositories.UserRepository;
import com.dcat.ReCo.repositories.specifications.MobileUserSpec;
import com.dcat.ReCo.services.BaseService;

@Service
public class UserServiceImpl extends BaseService implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private EntityManager em;

	@Override
	public boolean checkExistById(Long id) {
		return userRepository.existsById(id);
	}

	@Override
	public Optional<User> getById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Page<User> getAll(Pageable pageable) {

		return userRepository.findAll(pageable);
	}

	@Override
	public Page<User> getAllMobileUser(Pageable pageable) {
		return userRepository.findAll(new MobileUserSpec(), pageable);
	}

	@Override
	public Page<DinnerOverviewDTO> getAllDinner(Pageable pageable) {
		return userRepository.findAllDinner(pageable);
	}
	
	@Override
	public Page<DinnerOverviewDTO> search(String query, Pageable pageable) {
		
		return userRepository.search(query, pageable);
	}

	@Override
	public UserDetail getDinnerDetail(Long id) {

		return userRepository.findOneById(id);
	}

	@Override
	public Page<UserTransformer> getAllOwner(Pageable pageable) {

		Page<UserTransformer> respOwners = userRepository
				.findAllOwner(pageable);

		return respOwners;
	}

	@Override
	public void approveUsers(ApproveDTO<UserStatusEnum> dto) {

		List<User> approveUsers = userRepository.findAllById(dto.getIds());
		approveUsers.stream().forEach(user -> user.setStatus(dto.getType()));

		userRepository.saveAll(approveUsers);
	}

	@Override
	public void deleteUsers(List<Long> ids) {

		List<User> deleteUsers = userRepository.findAllById(ids);

		userRepository.deleteAll(deleteUsers);

	}

	@Override
	public UserTransformer getOwner(Long id) {

		User user = userRepository.findById(id).orElseThrow();

		TypedQuery<Object[]> query = em.createQuery(
				"SELECT COUNT(DISTINCT res.id), COUNT(reser.id) FROM User u LEFT JOIN u.restaurants res LEFT JOIN res.legalReservations reser WHERE u.id=:uid",
				Object[].class);
		query.setParameter("uid", id);
		Object[] analysForOwner = query.getSingleResult();

		UserTransformer respUser = new UserTransformer().getJson(user);
		respUser.setRestaurantCount((Long) analysForOwner[0]);
		respUser.setReservationCount((Long) analysForOwner[1]);

		return respUser;
	}

	@Override
	public void updateUser(UserUpdateDTO dto) {
		User user = userRepository.findById(dto.getId()).orElseThrow();

		if (user.getAddress() != null && !user.getAddress().getDetail().equals(dto.getAddressDetail()) || 
				user.getAddress() == null) {
			// first create address
			Address newAddress = new Address();
			newAddress.setDetail(dto.getAddressDetail());
			newAddress = addressRepository.save(newAddress);

			user.setAddress(newAddress);
		}
		// second update new infor
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new PropertyMap<UserUpdateDTO, User>() {

			@Override
			protected void configure() {
				// ignore address field
				skip(destination.getAddress());
			}
		});

		mapper.map(dto, user);

		userRepository.save(user);
	}

	@Override
	public void updateDinerProfile(UserProfileUpdateDTO dto) {
		List<Tag> tags = tagRepository.findAllById(dto.getTagId());
		User user = userRepository.findById(dto.getUserId()).orElseThrow();
		
		user.setFavoriteTags(tags);
		
		StringJoiner builder = new StringJoiner("&");
		for(String area : dto.getAreas()) {
			builder.add(area);
		}
		
		user.setActiveAreaIds(builder.toString());
		
		userRepository.save(user);
	}

	@Override
	public Page<UserTransformer> search2(String query, Pageable pageable) {
		
		return userRepository.search2(query, pageable);
	}
}
