package vn.metech.jpa.service;

import vn.metech.shared.BaseEntity;

import java.util.Collection;
import java.util.List;

public interface IService<TEntity extends BaseEntity> {

	TEntity getById(String id);

	TEntity create(TEntity entity);

	List<TEntity> batchCreate(Collection<TEntity> entities);

	TEntity update(TEntity entity);

	List<TEntity> batchUpdate(Collection<TEntity> entities);

	boolean delete(TEntity entity);

}
