package com.dcat.ReCo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dcat.ReCo.dtos.user.UserDetail;
import com.dcat.ReCo.dtos.user.UserTransformer;
import com.dcat.ReCo.dtos.user.projections.DinnerOverviewDTO;
import com.dcat.ReCo.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	public Page<User> findAll(Pageable pageable);

	public User findByUsername(String username);
	
	public User findByEmailAndRoleName(String email, String role);
	
	public UserDetail findOneById(Long id);

	public boolean existsByEmail(String email);

	public boolean existsByUsername(String username);

	@Query("SELECT new com.dcat.ReCo.dtos.user.projections.DinnerOverviewDTO(u.id, u.fullName, COUNT(r.id), COUNT(rv.id), u.createdAt) "
			+ "FROM User u LEFT JOIN u.reservations r LEFT JOIN u.reviews rv WHERE u.role.name = 'USER' GROUP BY u.id")
	public Page<DinnerOverviewDTO> findAllDinner(Pageable pageable);
	
	@Query("SELECT new com.dcat.ReCo.dtos.user.projections.DinnerOverviewDTO(u.id, u.fullName, COUNT(r.id), COUNT(rv.id), u.createdAt) "
			+ "FROM User u LEFT JOIN u.reservations r LEFT JOIN u.reviews rv WHERE u.role.name = 'USER' AND u.fullName LIKE %:query% GROUP BY u.id")
	public Page<DinnerOverviewDTO> search(String query, Pageable pageable);
	
	@Query("SELECT new com.dcat.ReCo.dtos.user.UserTransformer(u.id, u.fullName, u.avatar, u.createdAt, u.status, COUNT(DISTINCT res.id), COUNT(reser.id)) FROM User u LEFT JOIN u.restaurants res LEFT JOIN res.legalReservations reser WHERE u.role.name = 'ROWNER' GROUP BY u.id")
	public Page<UserTransformer> findAllOwner(Pageable pageable);
	
	@Query("SELECT new com.dcat.ReCo.dtos.user.UserTransformer(u.id, u.fullName, u.avatar, u.createdAt, u.status, COUNT(DISTINCT res.id), COUNT(reser.id)) FROM User u LEFT JOIN u.restaurants res LEFT JOIN res.legalReservations reser WHERE u.role.name = 'ROWNER' AND u.fullName LIKE %:query% GROUP BY u.id")
	public Page<UserTransformer> search2(String query, Pageable pageable);

}