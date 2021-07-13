package com.dcat.ReCo.services.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dcat.ReCo.constants.UserStatusEnum;
import com.dcat.ReCo.dtos.ApproveDTO;
import com.dcat.ReCo.dtos.user.UserDetail;
import com.dcat.ReCo.dtos.user.UserProfileUpdateDTO;
import com.dcat.ReCo.dtos.user.UserTransformer;
import com.dcat.ReCo.dtos.user.UserUpdateDTO;
import com.dcat.ReCo.dtos.user.projections.DinnerOverviewDTO;
import com.dcat.ReCo.models.User;

public interface UserService {

	Page<User> getAll(Pageable pageable);

	Page<User> getAllMobileUser(Pageable pageable);

	Page<DinnerOverviewDTO> getAllDinner(Pageable pageable);
	
	Page<UserTransformer> getAllOwner(Pageable pageable);
	
	UserTransformer getOwner(Long id);
	
	void updateUser(UserUpdateDTO dto);
	
	void updateDinerProfile(UserProfileUpdateDTO dto);
	
	void approveUsers(ApproveDTO<UserStatusEnum> dto);
	
	void deleteUsers(List<Long> ids);

	Optional<User> getById(Long id);
	
	UserDetail getDinnerDetail(Long id);

	User save(User user);
	
	Page<DinnerOverviewDTO> search(String query, Pageable pageable);
	
	 Page<UserTransformer> search2(String query, Pageable pageable);
}
