package com.dcat.ReCo.services;

import com.dcat.ReCo.dtos.HistoryCreateDTO;
import com.dcat.ReCo.models.History;

public interface HistoryService {

	History createOne(HistoryCreateDTO dto);

}