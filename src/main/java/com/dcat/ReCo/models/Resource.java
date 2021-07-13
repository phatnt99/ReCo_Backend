package com.dcat.ReCo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;


@Entity
@Table(name = "resources")
@Where(clause = "parent_id IS NULL")
public class Resource extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
	private List<Tag> tags;
	
	@OneToMany(mappedBy = "parent")
	private List<District> child;

	public Resource() {
		super();
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<District> getChild() {
		return child;
	}

	public void setChild(List<District> child) {
		this.child = child;
	}
	
}
