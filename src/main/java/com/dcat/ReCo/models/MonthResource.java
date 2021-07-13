package com.dcat.ReCo.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "resources")
@Where(clause = "parent_id = 6")
public class MonthResource extends BaseModel {

	private static final long serialVersionUID = 1L;
	String name;

}
