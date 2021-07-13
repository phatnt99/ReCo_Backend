package com.dcat.ReCo.dtos.reservation;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.dcat.ReCo.dtos.comment.projections.CommentWithDetail;
import com.dcat.ReCo.dtos.restaurant.RestaurantBasic;
import com.dcat.ReCo.dtos.user.UserBasic;
import com.dcat.ReCo.dtos.voucher.VoucherBasic;

public interface ReservationOverview {
	
	Long getId();
	
	String getFullName();
	
	String getPhone();
	
	String getEmail();
	
	String getNote();
	
	RestaurantBasic getRestaurant();
	
	UserBasic getUser();
	
	VoucherBasic getVoucher();
	
	Integer getPartySize();
	
	LocalDateTime getTimeComing();
	
	Date getCreatedAt();
	
	@Value("#{target.comment != null ? target.comment.id : null}")
	Long getCommentId();
	
	public interface ROComment {
		String getContent();
	}
}
