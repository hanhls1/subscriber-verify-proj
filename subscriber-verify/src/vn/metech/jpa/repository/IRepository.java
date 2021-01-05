package vn.metech.jpa.repository;

import vn.metech.shared.BaseEntity;

import java.util.Collection;
import java.util.List;

public interface IRepository<TEntity extends BaseEntity> {

    TEntity create(TEntity entity);

    TEntity update(TEntity entity);

    boolean delete(TEntity entity);

    TEntity getById(String id);

    List<TEntity> batchCreate(Collection<TEntity> entities);

    List<TEntity> batchUpdate(Collection<TEntity> entities);

    List<TEntity> getAll();


}
