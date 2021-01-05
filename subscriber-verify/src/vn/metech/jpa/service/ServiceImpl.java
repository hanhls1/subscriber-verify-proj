package vn.metech.jpa.service;

import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.BaseEntity;

import java.util.Collection;
import java.util.List;

public class ServiceImpl<TEntity extends BaseEntity> implements IService<TEntity> {

    protected IRepository<TEntity> repository;

    public ServiceImpl(IRepository<TEntity> repository) {
        this.repository = repository;
    }

    @Override
    public TEntity getById(String id) {
        return repository.getById(id);
    }

    @Override
    public TEntity create(TEntity entity) {
        return repository.create(entity);
    }

    @Override
    public List<TEntity> batchCreate(Collection<TEntity> entities) {
        return repository.batchCreate(entities);
    }

    @Override
    public TEntity update(TEntity entity) {
        return repository.update(entity);
    }

    @Override
    public List<TEntity> batchUpdate(Collection<TEntity> entities) {
        return repository.batchUpdate(entities);
    }

    @Override
    public boolean delete(TEntity entity) {
        return repository.delete(entity);
    }
}
