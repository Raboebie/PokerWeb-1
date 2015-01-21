package repositories.db.repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.Row;
import ninja.jpa.UnitOfWork;
import repositories.db.structure.Game;
import repositories.db.structure.Game_User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 1/20/2015.
 */
@Singleton
public class DBGame_UserRepository extends DBBaseRepository<Game_User>{
    @Inject private DBGameRepository dbGameRepository;

    @UnitOfWork
    public List<Row> getAllGamesInRows(){
        List<Row> rows = new ArrayList<>();
        List<Game> games = dbGameRepository.getDistinctGamesOrdered();

        for(Game game : games){
            List<Game_User> game_users = getEntityManager().createQuery("SELECT g FROM Game_User g WHERE game_name = :game_name").setParameter("game_name", game.getGame_name()).getResultList();

            String gameName = game.getGame_name();
            Date gameDate = game.getGame_date();
            List<String> usernames = new ArrayList<>();
            for(Game_User game_user : game_users){
                usernames.add(game_user.getUser().getUser_name());
            }

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            rows.add(new Row(gameName, usernames.get(0), usernames.get(1), usernames.get(2), usernames.get(3), df.format(gameDate)));
        }

        return rows;
    }
}
