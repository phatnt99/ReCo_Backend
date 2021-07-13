package com.dcat.ReCo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.models.Role;
import com.dcat.ReCo.repositories.RoleRepository;
@RestController
public class HomeAPI {
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/api/test")
	ResponseEntity<List<Role>> GetHello() {
		return ResponseEntity.ok(roleRepository.findAll());
	}
}
