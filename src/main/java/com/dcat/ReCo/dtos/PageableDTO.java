package com.dcat.ReCo.dtos;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.dcat.ReCo.constants.AppConst;

public class PageableDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer           page;
	private Integer           size;
	private String            sortable;
	private String            direction;

	public PageableDTO() {
		super();
		page      = 0;
		size      = AppConst.Pagination.PAGING_SIZE;
		sortable  = "updatedAt";
		direction = "DESC";
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Pageable get() {
		if(sortable != null || !sortable.isEmpty() || "x".equals(sortable)) {
			return PageRequest.of(this.page, this.size,
					Sort.by(Direction.fromString(direction),sortable));
		}
		
		return PageRequest.of(this.page, this.size,
				Sort.by("createdAt").descending());
	}
	
	public Pageable get(boolean isSort) {
		return PageRequest.of(this.page, this.size);
	}

	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
