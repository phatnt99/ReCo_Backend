package com.dcat.ReCo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcat.ReCo.models.UserFRestaurant;

public interface FollowRestaurantRepository extends JpaRepository<UserFRestaurant, Long> {

}
