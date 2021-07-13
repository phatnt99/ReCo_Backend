package com.dcat.ReCo.repositories.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.dcat.ReCo.constants.AppConst;
import com.dcat.ReCo.models.Role_;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.models.User_;

public class MobileUserSpec implements Specification<User> {

	private static final long serialVersionUID = 1L;

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.equal(root.join(User_.role).get(Role_.name), AppConst.Role.USER);
	}

}
