package com.dcat.ReCo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.AuthDTO;
import com.dcat.ReCo.dtos.ChangePasswordDTO;
import com.dcat.ReCo.dtos.LoginDTO;
import com.dcat.ReCo.dtos.RegisterUserDTO;
import com.dcat.ReCo.dtos.SocialLoginDTO;
import com.dcat.ReCo.exceptions.CredentialNotValidException;
import com.dcat.ReCo.exceptions.UserNotFoundException;
import com.dcat.ReCo.models.AuthUser;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.repositories.UserRepository;
import com.dcat.ReCo.services.JwtUserDetailsService;
import com.dcat.ReCo.services.firebase.FirebaseService;
import com.dcat.ReCo.utils.JwtTokenUtil;
import com.dcat.ReCo.utils.https.HttpResponse;
import com.google.firebase.messaging.FirebaseMessagingException;

@RestController
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/login-social", method = RequestMethod.POST)
	public ResponseEntity<?> loginWithSocialMedia(
			@RequestBody SocialLoginDTO dto) {
		final AuthUser userDetails = userDetailsService
				.loadUserBySocialAccount(dto);

		AuthDTO authDto = new AuthDTO();
		authDto.setToken(jwtTokenUtil.generateToken(userDetails));
		authDto.setRole(userDetails.getUser().getRole().getName());
		authDto.setId(userDetails.getUser().getId());
		authDto.setStatus(userDetails.getUser().getStatus());

		return new HttpResponse(HttpStatus.OK, true, authDto).send();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody LoginDTO loginReq) throws Exception {
		authenticate(loginReq.getUsername(), loginReq.getPassword());

		final AuthUser userDetails = userDetailsService
				.loadUserByUsername(loginReq.getUsername());

		// get role
		AuthDTO authDto = new AuthDTO();
		authDto.setToken(jwtTokenUtil.generateToken(userDetails));
		authDto.setRole(userDetails.getUser().getRole().getName());
		authDto.setId(userDetails.getUser().getId());
		authDto.setStatus(userDetails.getUser().getStatus());

		return new HttpResponse(HttpStatus.OK, true, authDto).send();
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(
			@Valid @RequestBody RegisterUserDTO user) throws Exception {
		return new HttpResponse(HttpStatus.CREATED, true,
				userDetailsService.save(user)).send();
	}
	
	@RequestMapping(value = "/pwd", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(
			@RequestBody ChangePasswordDTO dto) {
		
		final AuthUser userDetails = userDetailsService
				.changePassword(dto);

		// get role
		AuthDTO authDto = new AuthDTO();
		authDto.setToken(jwtTokenUtil.generateToken(userDetails));
		authDto.setRole(userDetails.getUser().getRole().getName());
		authDto.setId(userDetails.getUser().getId());
		authDto.setStatus(userDetails.getUser().getStatus());

		return new HttpResponse(HttpStatus.OK, true, authDto).send();
	}

	private void authenticate(String username, String password)
			throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username,
							password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new CredentialNotValidException();
		}
	}

	@GetMapping("/fcm/sub")
	public ResponseEntity<?> subcribe(@RequestParam String token,
			@RequestParam(name = "uid", required = false, defaultValue = "-1") Long uid) {

		// if has user
		// in this case, we already had token registration in fb, just update
		// token for user
		if (uid != -1) {
			User user = userRepository.findById(uid)
					.orElseThrow(() -> new UserNotFoundException());
			user.setFcmToken(token);
			userRepository.save(user);
		} else {
			//
			try {
				FirebaseService.getInstance().subcribe(token);
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return HttpResponse.sendNoContent();
	}
}
