package repositories.db.repositories;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by Chris on 1/20/2015.
 */
public class DBBaseRepository<T> {
    @Inject private Provider<EntityManager> entityManagerProvider;

    @Transactional
    public void persist(T entity){
        getEntityManager().persist(entity);
    }

    protected EntityManager getEntityManager(){
        return entityManagerProvider.get();
    }
}
