package com.dcat.ReCo.dtos;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

public class CreateRestaurantDTO extends CrudRestaurantDTO {
	
	MultipartFile logo;
	Collection<MultipartFile> carousel;
	Collection<MultipartFile> menu;

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		
		this.logo = logo;
	}

	public Collection<MultipartFile> getCarousel() {
		return carousel != null ? carousel : new ArrayList<MultipartFile>();
	}

	public void setCarousel(Collection<MultipartFile> carousel) {
		this.carousel = carousel;
	}

	public Collection<MultipartFile> getMenu() {
		return menu != null ? menu : new ArrayList<MultipartFile>();
	}

	public void setMenu(Collection<MultipartFile> menu) {
		this.menu = menu;
	}

}
