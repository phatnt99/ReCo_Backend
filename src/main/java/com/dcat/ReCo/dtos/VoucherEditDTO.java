package com.dcat.ReCo.dtos;

import com.dcat.ReCo.dtos.voucher.VoucherCreateDTO;
import com.dcat.ReCo.models.Voucher;

public class VoucherEditDTO extends VoucherCreateDTO {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Voucher toEntity() {
		Voucher voucher = super.toEntity();
		voucher.setId(id);
		
		return voucher;
	}
}
