package vn.metech.repository.base;


import vn.metech.entity.base.EntityBase;

public interface IRepository<T extends EntityBase> {

	T save(T entity);

	Iterable<T> saveAll(Iterable<T> entities);

	T getByKey(String key);

}
