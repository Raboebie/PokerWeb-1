package repositories.db.repositories;

import com.google.inject.Singleton;
import ninja.jpa.UnitOfWork;
import repositories.db.structure.User;

import java.util.List;

/**
 * Created by Chris on 1/20/2015.
 */

@Singleton
public class DBUserRepository extends DBBaseRepository<User> {

    @UnitOfWork
    public User getUserByName(String name){
        try {
            return (User) getEntityManager().createQuery("SELECT u FROM User u WHERE user_name = :user_name").setParameter("user_name", name).getSingleResult();
        }
        catch(javax.persistence.NoResultException ex){
            return null;
        }
    }

    @UnitOfWork
    public List<User> getAllUsers(){
        return getEntityManager().createQuery("SELECT u FROM User u").getResultList();
    }
}
