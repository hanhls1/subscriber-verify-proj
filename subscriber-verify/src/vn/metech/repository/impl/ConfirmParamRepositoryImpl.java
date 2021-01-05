package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.ConfirmParam;
import vn.metech.repository.IConfirmParamRepository;
import vn.metech.repository.base.RepositoryImpl;

import javax.persistence.EntityManager;

@Repository
public class ConfirmParamRepositoryImpl extends RepositoryImpl<ConfirmParam> implements IConfirmParamRepository {

	public ConfirmParamRepositoryImpl(EntityManager em) {
		super(em, ConfirmParam.class);
	}

}
