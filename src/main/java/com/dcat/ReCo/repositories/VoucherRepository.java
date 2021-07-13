package com.dcat.ReCo.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dcat.ReCo.dtos.voucher.VoucheDetail;
import com.dcat.ReCo.dtos.voucher.VoucherList;
import com.dcat.ReCo.dtos.voucher.VoucherTop10;
import com.dcat.ReCo.models.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {

	Page<Voucher> findAllByRestaurantId(Long restaurantId, Pageable pageable);
	
	Page<VoucherList> findByRestaurantIdIn(List<Long> ids, Pageable pageable);
	
	List<VoucherTop10> findTop10ByOrderByUpdatedAtDesc();
	
	VoucheDetail findOneById(Long id);
	
	void deleteByIdIn(Collection<Long> ids);
	
	Page<Voucher> findByCodeContainsOrTitleContains(String code, String title,Pageable pageble);
}
