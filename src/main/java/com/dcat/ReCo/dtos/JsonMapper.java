package com.dcat.ReCo.dtos;

public interface JsonMapper<DTO, M> {
	
	DTO getJson(M model);
}
