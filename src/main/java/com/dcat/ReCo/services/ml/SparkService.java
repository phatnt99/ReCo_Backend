package com.dcat.ReCo.services.ml;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.recommendation.Rating;

import com.dcat.ReCo.dtos.user.UserProfileUpdateDTO;
import com.dcat.ReCo.vos.RatingVO;

public interface SparkService {

	void doContentBasedProfile(UserProfileUpdateDTO dto);

	void doContentBasedItem(Long restaurant_base);

	void doUserCollab2(Long userId);

	void doAll();

	List<Rating> doUserCollab(Integer userId);

	JavaRDD<RatingVO> loadRating();

}