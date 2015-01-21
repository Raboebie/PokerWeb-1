package repositories.db.repositories;

import com.google.inject.Singleton;
import ninja.jpa.UnitOfWork;
import repositories.db.structure.Game;

import java.util.List;

/**
 * Created by Chris on 1/20/2015.
 */
@Singleton
public class DBGameRepository extends DBBaseRepository<Game>{
    @UnitOfWork
    public Game getGameByName(String name){
        try {
            return (Game) getEntityManager().createQuery("SELECT g FROM Game g WHERE game_name = :game_name").setParameter("game_name", name).getSingleResult();
        }
        catch(javax.persistence.NoResultException ex){
            return null;
        }
    }

    public List<Game> getDistinctGamesOrdered(){
        return getEntityManager().createQuery("SELECT DISTINCT g FROM Game g ORDER BY game_date").getResultList();
    }
}
