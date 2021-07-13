package com.dcat.ReCo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.constants.AppConst;
import com.dcat.ReCo.constants.LangConst;
import com.dcat.ReCo.dtos.ChangePasswordDTO;
import com.dcat.ReCo.dtos.RegisterUserDTO;
import com.dcat.ReCo.dtos.SocialLoginDTO;
import com.dcat.ReCo.exceptions.PasswordNotMatchException;
import com.dcat.ReCo.exceptions.UserNotFoundException;
import com.dcat.ReCo.models.AuthUser;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.repositories.RoleRepository;
import com.dcat.ReCo.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(LangConst.userNameNotFoundMess + username);
		}

		// get Authority

		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(AppConst.Role.ROLE_PREFIX + user.getRole().getName()));

		return new AuthUser(user, authorities);
	}

	public AuthUser loadUserBySocialAccount(SocialLoginDTO dto) {
		// firstly check if there are already account mapped with this social
		// uid
		User user = userRepository.findByEmailAndRoleName(dto.getEmail(), "USER");

		if (user == null) {
			// there is no account related with this social
			// create new one
			user = new User();
			user.setUsername(dto.getEmail());
			user.setEmail(dto.getEmail());
			// dumb pass
			user.setPassword(bcryptEncoder.encode(user.getEmail()));
			user.setAvatar(dto.getAvatar());
			user.setFullName(dto.getFullName());
			user.setRole(roleRepository.findByName("USER").orElse(null));

			user = userRepository.save(user);
		}

		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(AppConst.Role.ROLE_PREFIX + user.getRole().getName()));
		AuthUser resp = new AuthUser(user, authorities);

		return resp;
	}

	public AuthUser changePassword(ChangePasswordDTO dto) {

		// load user by current id
		User currentUser = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException());
		// check old password matched
		if(!bcryptEncoder.matches(dto.getCurrentPassword(), currentUser.getPassword())) {
			throw new PasswordNotMatchException();
		}
		// encode the new password and update
		currentUser.setPassword(bcryptEncoder.encode(dto.getNewPassword()));
		
		currentUser = userRepository.save(currentUser);
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(AppConst.Role.ROLE_PREFIX + currentUser.getRole().getName()));

		return new AuthUser(currentUser, authorities);
		
	}

	public User save(RegisterUserDTO user) {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setEmail(user.getEmail());
		newUser.setRole(roleRepository.findByName(user.getRole()).orElse(null));
		return userRepository.save(newUser);
	}

}
