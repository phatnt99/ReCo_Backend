package com.dcat.ReCo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcat.ReCo.dtos.comment.projections.CommentOverview;
import com.dcat.ReCo.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	CommentOverview findOneById(Long id);
	
	Page<CommentOverview> findBy(Pageable pageable);
	
	Page<CommentOverview> findByUserId(Long userId, Pageable pageable);
	
	Page<CommentOverview> findByRestaurantId(Long restaurantId, Pageable pageable);
	
	Page<CommentOverview> findByRestaurantOwnerId(Long ownerId, Pageable pageable);
	
	@Query("SELECT c FROM Comment c WHERE c.user.id = :uid")
	Page<Comment> findAllByUserId(@Param("uid") Long userId, Pageable pageable);
	
	Page<CommentOverview> findByUserFullNameContainsOrRestaurantNameContains(String query1, String query2, Pageable pageble);
}
