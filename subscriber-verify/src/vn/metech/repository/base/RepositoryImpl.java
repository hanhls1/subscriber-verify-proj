package vn.metech.repository.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.common.ValueKey;
import vn.metech.entity.base.EntityBase;

import javax.persistence.EntityManager;

@Transactional
public class RepositoryImpl<T extends EntityBase> implements IRepository<T> {

    protected Class<T> clazz;
    protected EntityManager em;

    @Value(ValueKey.DATA_FETCH_TIMES_LIMIT)
    protected int fetchTimesLimit;

    @Value(ValueKey.DATA_FETCH_RECORD_LIMIT)
    protected int fetchRecordLimit;

    @Value(ValueKey.DATA_FETCH_INTERVAL_IN_MINUTE)
    protected int fetchIntervalInMinute;

//    @Autowired
    protected CrudRepository<T, String> crudRepository;

    protected RepositoryImpl(EntityManager em, Class<T> clazz) {
        this.clazz = clazz;
        this.em = em;
    }

    @Override
    public T save(T entity) {
        return crudRepository.save(entity);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> entities) {
        return crudRepository.saveAll(entities);
    }

    @Override
    public T getByKey(String key) {
        return em.find(clazz, key);
    }

}
