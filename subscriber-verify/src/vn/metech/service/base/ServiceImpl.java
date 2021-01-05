package vn.metech.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.base.EntityBase;

public class ServiceImpl<T extends EntityBase> implements IService<T> {

	@Autowired
	protected CrudRepository<T, String> crudRepository;

	@Override
	public T save(T entity) {
		return crudRepository.save(entity);
	}

	@Override
	public Iterable<T> saveAll(Iterable<T> entities) {
		return crudRepository.saveAll(entities);
	}
}
