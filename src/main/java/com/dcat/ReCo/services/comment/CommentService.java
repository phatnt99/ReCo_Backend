package com.dcat.ReCo.services.comment;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dcat.ReCo.dtos.CreateCommentDTO;
import com.dcat.ReCo.dtos.comment.CommentBulkApproveDTO;
import com.dcat.ReCo.dtos.comment.projections.CommentOverview;
import com.dcat.ReCo.models.Comment;

public interface CommentService {
	
	Page<CommentOverview> getAll(Pageable pageble);
	
	Page<CommentOverview> getAllByUser(Long userId, Pageable pageable);
	
	CommentOverview getOne(Long id);
	
	Page<CommentOverview> getAllByRestaurant(Long restaurantId, Pageable pageable);
	
	Page<CommentOverview> getAllByOwner(Long ownerId, Pageable pageable);
	
	Comment createOne(CreateCommentDTO dto);
	
	Page<Comment> getAllByUserId(Long userId, Pageable pageable);

	void deleteByIds(Collection<Long> id);
	
	void approveByIds(CommentBulkApproveDTO dto);
	
	Page<CommentOverview> search(String query, Pageable pageable);
}
