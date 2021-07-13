package com.dcat.ReCo.dtos;

public interface DTOMapping<DTO, M> {
	DTO getDTO(M model);
}
