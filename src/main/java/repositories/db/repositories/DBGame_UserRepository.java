package repositories.db.repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.Row;
import repositories.db.structure.Game;
import repositories.db.structure.Game_User;
import repositories.db.structure.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 1/20/2015.
 */
@Singleton
public class DBGame_UserRepository extends DBBaseRepository<Game_User>{
    @Inject private DBGameRepository dbGameRepository;

    public List<Row> getAllGamesInRows(){
        List<Row> rows = new ArrayList<>();
        //List<Game_User> game_users = getEntityManager().createQuery("SELECT g FROM Game_User g").getResultList();
        List<Game> games = dbGameRepository.getDistinctGames();

        for(Game game : games){
            List<Game_User> game_users = getEntityManager().createQuery("SELECT g FROM Game_User g WHERE game_name = :game_name").setParameter("game_name", game.getGame_name()).getResultList();

            String gameName = game.getGame_name();
            List<String> usernames = new ArrayList<>();
            for(Game_User game_user : game_users){
                usernames.add(game_user.getUser().getUser_name());
            }

            rows.add(new Row(gameName, usernames.get(0), usernames.get(1), usernames.get(2), usernames.get(3)));
        }

        return rows;
    }
}
