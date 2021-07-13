package com.dcat.ReCo.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.dcat.ReCo.constants.AppConst;
import com.dcat.ReCo.models.Role_;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.models.User_;

public class UserSpec {

	public static Specification<User> isDinner() {

		return (root, query, cb) -> cb.equal(root.join(User_.role).get(Role_.NAME), AppConst.Role.USER);
	}
	
	public static Specification<User> isOwner() {
		
		return (root, query, cb) -> cb.equal(root.join(User_.role).get(Role_.NAME), AppConst.Role.OWNER);
	}
}
