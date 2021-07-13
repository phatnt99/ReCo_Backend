package com.dcat.ReCo.admin.controllers;

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

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.dtos.tag.CreateTagDTO;
import com.dcat.ReCo.dtos.tag.UpdateTagDTO;
import com.dcat.ReCo.services.ResourceService;
import com.dcat.ReCo.services.tag.TagService;
import com.dcat.ReCo.utils.https.HttpResponse;
@RestController
@RequestMapping("/admin/tags")
public class AdminTagController {
	
	@Autowired
	ResourceService restaurantTagService;
	
	@Autowired
	TagService tagService;

	@GetMapping
	public ResponseEntity<?> getAllTags(@ModelAttribute PageableDTO pageableDto,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortProp,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {
		
		Pageable pageable = PageRequest.of(pageableDto.getPage(), pageableDto.getSize(),
				Sort.by(Direction.fromString(direction), sortProp));

		return new HttpResponse(HttpStatus.OK, true, restaurantTagService.getAllTagWithAnalys(pageable)).send();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateOneTag(@PathVariable Long id,@RequestBody UpdateTagDTO dto) {
		
		tagService.updateOne(id, dto);
		
		return HttpResponse.sendNoContent();
	}
	
	@PostMapping
	public ResponseEntity<?> createOneTag(@RequestBody CreateTagDTO dto) {
		
		return HttpResponse.sendCreated(tagService.createOne(dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOneTag(@PathVariable Long id) {
		
		tagService.deleteOne(id);
		
		return HttpResponse.sendNoContent(); 
	}
}
