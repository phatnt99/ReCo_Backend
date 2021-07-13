package com.dcat.ReCo.dtos.comment;

import java.util.Collection;

public class CommentBulkActionDTO {
	Collection<Long> bulkIds;

	public Collection<Long> getBulkIds() {
		return bulkIds;
	}

	public void setBulkIds(Collection<Long> bulkIds) {
		this.bulkIds = bulkIds;
	}
}
