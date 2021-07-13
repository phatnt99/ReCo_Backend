package com.dcat.ReCo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dcat.ReCo.dtos.RestaurantTagDTO;
import com.dcat.ReCo.models.Resource;
import com.dcat.ReCo.models.Tag;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
	@Query("SELECT new com.dcat.ReCo.dtos.RestaurantTagDTO(t, COUNT(r.id)) FROM Tag t LEFT JOIN t.restaurants r GROUP BY t.name")
	public Page<RestaurantTagDTO> findAllTagWithAnalys(Pageable pageable);
	
	@Query("SELECT new com.dcat.ReCo.dtos.RestaurantTagDTO(t, COUNT(r.id)) FROM Tag t JOIN t.restaurants r GROUP BY t.name")
	public List<RestaurantTagDTO> findAllTagWithAnalys();
	
	@Query("SELECT t FROM Tag t WHERE t.id = :tagId")
	public Tag findOneTag(@Param("tagId") Long id);
	
	@Query("SELECT new com.dcat.ReCo.dtos.RestaurantTagDTO(t, COUNT(r.id)) FROM Tag t JOIN t.restaurants r WHERE t.name LIKE %:query% GROUP BY t.name")
	public Page<RestaurantTagDTO> search(String query, Pageable pageable);
}
