package vn.metech.service.base;


import vn.metech.entity.base.EntityBase;

public interface IService<T extends EntityBase> {

	T save(T entity);

	Iterable<T> saveAll(Iterable<T> entities);

}
