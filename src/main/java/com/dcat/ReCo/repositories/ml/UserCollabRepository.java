package com.dcat.ReCo.repositories.ml;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.ml.UserCollab;

public interface UserCollabRepository extends JpaRepository<UserCollab, Long> {
	
	@Query("select uc.restaurant from UserCollab uc where uc.user.id=:id order by uc.predict desc")
	List<Restaurant> getTop20Restaurant(@Param("id") Long userId, Pageable pageable);

	void deleteByUserId(Long userId);
}
