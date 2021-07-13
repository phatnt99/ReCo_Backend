package com.dcat.ReCo.dtos.responses;

public interface ResponseMapping<R, M> {
	R getResponse(M model);
}
