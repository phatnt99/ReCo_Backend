package com.dcat.ReCo.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.dtos.comment.CommentBulkApproveDTO;
import com.dcat.ReCo.dtos.comment.CommentBulkDeleteDTO;
import com.dcat.ReCo.dtos.responses.CommentResponse;
import com.dcat.ReCo.models.Comment;
import com.dcat.ReCo.services.comment.CommentService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/admin/comments")
public class AdminCommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping("user/{uid}")
	public ResponseEntity<?> getAllByUserId(
			@ModelAttribute PageableDTO pageableDto,
			@PathVariable Long uid) {
		Pageable pageable = PageRequest.of(pageableDto.getPage(), pageableDto.getSize());
		Page<Comment> result = commentService.getAllByUserId(uid, pageable);
		Page<CommentResponse> pages = new PageImpl<CommentResponse>(CommentResponse.getListResponse(result.toList()), pageable, result.getTotalElements());

		return new HttpResponse(HttpStatus.OK, true, pages).sendWithPaginateHeaders();

	}
	
	@PostMapping("/bulk-delete")
	public ResponseEntity<?> deleteBulkComment(@RequestBody CommentBulkDeleteDTO dto) {
		
		commentService.deleteByIds(dto.getBulkIds());
		
		return HttpResponse.sendNoContent();
	}
	
	@PostMapping("/bulk-approve")
	public ResponseEntity<?> approveBulkComment(@RequestBody CommentBulkApproveDTO dto) {
		
		commentService.approveByIds(dto);
		
		return HttpResponse.sendNoContent();
	}
	
}
