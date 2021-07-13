package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.dtos.user.UserApproveDTO;
import com.dcat.ReCo.dtos.user.UserProfileUpdateDTO;
import com.dcat.ReCo.dtos.user.UserUpdateDTO;
import com.dcat.ReCo.services.user.UserService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/t/diners/{id}")
	public ResponseEntity<?> getDinnerDetail(
			@PathVariable Long id) {

		return HttpResponse.sendOK(userService.getDinnerDetail(id));
	}

	@GetMapping("/t/diners")
	public ResponseEntity<?> getAllUser(
			@ModelAttribute PageableDTO pageableDto) {

		return new HttpResponse(HttpStatus.OK, true,
				userService.getAllDinner(pageableDto.get()))
						.sendWithPaginateHeaders();
	}
	
	@GetMapping("/t/diners/search")
	public ResponseEntity<?> search(
			@RequestParam(required = false) String query,
			@ModelAttribute PageableDTO dto) {

		return new HttpResponse(HttpStatus.OK, true,
				userService.search(query, dto.get()))
						.sendWithPaginateHeaders();
	}
	
	@GetMapping("/t/owners/search")
	public ResponseEntity<?> search2(
			@RequestParam(required = false) String query,
			@ModelAttribute PageableDTO dto) {

		return new HttpResponse(HttpStatus.OK, true,
				userService.search2(query, dto.get()))
						.sendWithPaginateHeaders();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOneUser(@PathVariable Long id) {

		return new HttpResponse(HttpStatus.OK, true, userService.getById(id))
				.send();
	}

	@GetMapping("/t/owners")
	public ResponseEntity<?> getAllOwners(
			@ModelAttribute PageableDTO pageableDTO) {

		return HttpResponse.sendOK(userService.getAllOwner(pageableDTO.get()));
	}

	@PutMapping("/t/owners")
	public ResponseEntity<?> updateOwner(
			@RequestBody UserUpdateDTO dto) {

		userService.updateUser(dto);

		return HttpResponse.sendNoContent();
	}

	@PutMapping("/t/diners")
	public ResponseEntity<?> updateDiner(
			@RequestBody UserUpdateDTO dto) {

		userService.updateUser(dto);

		return HttpResponse.sendNoContent();
	}

	@PutMapping("/t/diners/profile")
	public ResponseEntity<?> updateDinerProfile(
			@RequestBody UserProfileUpdateDTO dto) {

		userService.updateDinerProfile(dto);

		return HttpResponse.sendNoContent();
	}

	@GetMapping("/t/owners/{id}")
	public ResponseEntity<?> getOwnerDetail(
			@PathVariable Long id) {

		return HttpResponse.sendOK(userService.getOwner(id));
	}

	@PostMapping("/t/owners/bulk-approve")
	public ResponseEntity<?> approveUsers(
			@RequestBody UserApproveDTO dto) {

		userService.approveUsers(dto);

		return HttpResponse.sendNoContent();
	}

	@PostMapping("/t/owners/bulk-delete")
	public ResponseEntity<?> deleteUsers(
			@RequestBody UserApproveDTO dto) {

		userService.deleteUsers(dto.getIds());

		return HttpResponse.sendNoContent();
	}

}
