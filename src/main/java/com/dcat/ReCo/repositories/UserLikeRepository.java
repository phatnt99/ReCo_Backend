package com.dcat.ReCo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcat.ReCo.models.UserLike;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
	@Query("SELECT ul FROM UserLike ul WHERE ul.user.id = :uid AND ul.restaurant.id = :rid")
	List<UserLike> findByUserIdAndRestaurantId(@Param("uid")Long userId, @Param("rid")Long likeableId);
}
