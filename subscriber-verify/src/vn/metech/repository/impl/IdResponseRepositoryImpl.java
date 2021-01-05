package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.IdResponse;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IIdResponseRepository;

import javax.persistence.EntityManager;

@Repository
public class IdResponseRepositoryImpl
				extends RepositoryImpl<IdResponse> implements IIdResponseRepository {

	public IdResponseRepositoryImpl(EntityManager entityManager) {
		super(entityManager, IdResponse.class);
	}

}
