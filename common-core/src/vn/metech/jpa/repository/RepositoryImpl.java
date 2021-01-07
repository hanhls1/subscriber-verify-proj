package vn.metech.jpa.repository;

import org.springframework.transaction.annotation.Transactional;
import vn.metech.shared.BaseEntity;

import javax.persistence.EntityManager;
import java.util.*;

@Transactional
public class RepositoryImpl<TEntity extends BaseEntity> implements IRepository<TEntity> {

    protected EntityManager entityManager;
    protected Class<TEntity> clazz;

    public RepositoryImpl(EntityManager entityManager, Class<TEntity> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    @Override
    public TEntity create(TEntity entity) {
        try {
            entityManager.persist(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

            return null;
        }
    }

    @Override
    public TEntity update(TEntity entity) {
        try {
            entity.setUpdatedDate(new Date());
            entityManager.merge(entity);

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

            return null;
        }
    }

    @Override
    public boolean delete(TEntity entity) {
        try {
            entity.setDeleted(true);

            return update(entity) != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
    }

    @Override
    public TEntity getById(String id) {
        try {
            TEntity entity = entityManager.find(clazz, id);

            return entity.isDeleted() ? null : entity;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return null;
        }
    }

    @Override
    public List<TEntity> batchCreate(Collection<TEntity> entities) {
        try {
            List<TEntity> lst = new ArrayList<>();
            entities.forEach(entity -> {
                entityManager.persist(entity);
                lst.add(entity);
            });

            return lst;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return Collections.emptyList();
        }
    }

    @Override
    public List<TEntity> batchUpdate(Collection<TEntity> entities) {
        try {
            List<TEntity> lst = new ArrayList<>();
            entities.forEach(entity -> {
                entityManager.merge(entity);
                lst.add(entity);
            });

            return lst;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return Collections.emptyList();
        }
    }

    @Override
    public List<TEntity> getAll() {
        try {
            // @formatter:off
            String jpql = " FROM " +
                            clazz.getName() +
                          " WHERE " +
                            "deleted =: deleted";
            // @formatter:on
            return entityManager
                    .createQuery(jpql, clazz)
                    .setParameter("deleted", false)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return Collections.emptyList();
        }
    }
}
