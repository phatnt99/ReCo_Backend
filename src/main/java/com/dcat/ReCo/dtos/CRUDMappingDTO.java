package com.dcat.ReCo.dtos;

public interface CRUDMappingDTO<D extends BaseDTO, M> {
	D getCreateDTO(M model);
	D getEditDTO(M model);
}
