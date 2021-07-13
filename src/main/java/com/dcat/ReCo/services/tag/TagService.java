package com.dcat.ReCo.services.tag;

import com.dcat.ReCo.dtos.tag.CreateTagDTO;
import com.dcat.ReCo.dtos.tag.UpdateTagDTO;
import com.dcat.ReCo.models.Tag;

public interface TagService {

	void updateOne(Long id, UpdateTagDTO dto);
	
	Tag createOne(CreateTagDTO dto);
	
	void deleteOne(Long id);

}