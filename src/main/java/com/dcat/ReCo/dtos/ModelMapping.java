package com.dcat.ReCo.dtos;

import com.dcat.ReCo.constants.MapperEnum;

public interface ModelMapping<M, DTO> {
	public M getModel(DTO dto, MapperEnum type);
}
