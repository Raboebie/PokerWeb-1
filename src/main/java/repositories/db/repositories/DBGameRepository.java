package repositories.db.repositories;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.jpa.UnitOfWork;
import repositories.db.structure.Game;
import repositories.db.structure.Game_User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 1/20/2015.
 */
@Singleton
public class DBGameRepository extends DBBaseRepository<Game>{
    @Inject DBGame_UserRepository dbGame_userRepository;

    List<Game> retrievedByID = new ArrayList<>();

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
        return getEntityManager().createQuery("SELECT DISTINCT g FROM Game g ORDER BY game_date DESC").getResultList();
    }

    @UnitOfWork
    public List<Game> getAllGames(){
        return getEntityManager().createQuery("SELECT g FROM Game g").getResultList();
    }

    @UnitOfWork
    public List<Game> getAllGamesOrderedByDate(){
        return getEntityManager().createQuery("SELECT g FROM Game g ORDER BY game_date DESC").getResultList();
    }

    @UnitOfWork
    public List<Game> getAllGamesByUsernameOrderedByDate(String username){
        List<Game_User> game_users = dbGame_userRepository.getGame_UsersByUser_Name(username);
        List<Game> currentGames = getEntityManager().createQuery("SELECT g FROM Game g ORDER BY game_date DESC").getResultList();
        List<Game> games = new ArrayList<>();
        for(Game_User game_user : game_users){
            int index = listContains(currentGames, game_user.getId().getGame_id());
            if(index != -1){
                games.add(currentGames.get(index));
                currentGames.remove(index);
            }
        }
        games = Lists.reverse(games);
        return games;
    }

    public int listContains(List<Game> games, int id){
        for(int i = 0; i < games.size(); i++){
            if(games.get(i).getGame_id() == id) {
                return i;
            }
        }
        return -1;
    }

    @UnitOfWork
    public List<Game> getAllUnfinishedGames(){
        List<Game> all = getEntityManager().createQuery("SELECT g FROM Game g").getResultList();
        List<Game> returnable = new ArrayList<>();
        for(Game game : all){
            if(game.getGameUserList().size() == 0){
                returnable.add(game);
            }
        }
        return returnable;
    }

    @UnitOfWork
    public Game getGameByGameID(String id) {
        try {
            return (Game) getEntityManager().createQuery("SELECT g FROM Game g WHERE game_id = :game_id").setParameter("game_id", Integer.parseInt(id)).getSingleResult();
        }
        catch(javax.persistence.NoResultException ex){
            return null;
        }
    }
}
