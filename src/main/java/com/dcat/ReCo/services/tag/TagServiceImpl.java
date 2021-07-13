package com.dcat.ReCo.services.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.constants.MapperEnum;
import com.dcat.ReCo.dtos.ModelMapping;
import com.dcat.ReCo.dtos.tag.CreateTagDTO;
import com.dcat.ReCo.dtos.tag.UpdateTagDTO;
import com.dcat.ReCo.exceptions.TagNotFoundException;
import com.dcat.ReCo.models.Tag;
import com.dcat.ReCo.repositories.TagRepository;

@Service
public class TagServiceImpl
		implements TagService, ModelMapping<Tag, CreateTagDTO> {

	@Autowired
	private TagRepository tagRepository;

	@Override
	public void updateOne(Long id, UpdateTagDTO dto) {

		Tag updateTag = tagRepository.findById(id)
				.orElseThrow(() -> new TagNotFoundException());

		updateTag.setName(dto.getName());

		tagRepository.save(updateTag);
	}

	@Override
	public Tag createOne(CreateTagDTO dto) {

		return tagRepository.save(getModel(dto, MapperEnum.CREATE));
	}

	@Override
	public Tag getModel(CreateTagDTO dto, MapperEnum type) {
		Tag createTag = new Tag();

		createTag.setName(dto.getName());

		return createTag;
	}

	@Override
	public void deleteOne(Long id) {

		tagRepository.deleteById(id);

	}
}
