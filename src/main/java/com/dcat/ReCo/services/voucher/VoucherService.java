package com.dcat.ReCo.services.voucher;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dcat.ReCo.dtos.VoucherEditDTO;
import com.dcat.ReCo.dtos.voucher.VoucheDetail;
import com.dcat.ReCo.dtos.voucher.VoucherBulkDeleteDTO;
import com.dcat.ReCo.dtos.voucher.VoucherCreateDTO;
import com.dcat.ReCo.dtos.voucher.VoucherList;
import com.dcat.ReCo.dtos.voucher.VoucherTop10;
import com.dcat.ReCo.dtos.voucher.VoucherTransformer;
import com.dcat.ReCo.models.Voucher;

public interface VoucherService {
	
	Page<VoucherTransformer> getAll(Pageable pageable);

	Page<VoucherTransformer> getAllByRestaurant(Long restaurantId, Pageable pageable);
	
	Page<VoucherList> getAllByOwner(Long id, Pageable pageable);
	
	Page<VoucherTransformer> search(String query, Pageable pageable);

	Voucher createOne(VoucherCreateDTO dto);
	
	void updateOne(VoucherEditDTO dto);
	
	void bulkDelete(VoucherBulkDeleteDTO dto);
	
	VoucheDetail getOne(Long id);
	
	List<VoucherTop10> getTop10();
}