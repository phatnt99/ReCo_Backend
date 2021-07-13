package com.dcat.ReCo.dtos.voucher;

import java.util.Collection;

public class VoucherBulkDeleteDTO {
	private Collection<Long> bulkIds;

	public Collection<Long> getBulkIds() {
		return bulkIds;
	}

	public void setBulkIds(Collection<Long> bulkIds) {
		this.bulkIds = bulkIds;
	}

}
