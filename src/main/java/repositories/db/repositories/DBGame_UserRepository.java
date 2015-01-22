package repositories.db.repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.Row;
import model.cards.Hand;
import model.deck.Deck;
import ninja.jpa.UnitOfWork;
import repositories.GameRepository;
import repositories.db.structure.Game;
import repositories.db.structure.Game_User;
import services.GameService;

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
    @Inject private GameService gameService;
    @Inject private Deck deck;

    @UnitOfWork
    public List<Row> getAllGamesInRows(){
        List<Row> rows = new ArrayList<>();
        List<Game> games = dbGameRepository.getDistinctGamesOrdered();

        int id = 1;

        for(Game game : games){
            List<Game_User> game_users = getEntityManager().createQuery("SELECT g FROM Game_User g WHERE game_id = :game_id").setParameter("game_id", game.getGame_id()).getResultList();

            List<String> usernames = new ArrayList<>();
            List<Hand> hands = new ArrayList<>();
            for(Game_User game_user : game_users){
                usernames.add(game_user.getUser().getUser_name());
                hands.add(new Hand(game_user.getHand()));
            }


            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            //Auto generated ID doesn't work
            rows.add(new Row(id++, game.getGame_name(), usernames.get(0), usernames.get(1), usernames.get(2), usernames.get(3), deck.determineWinner(hands, usernames) , df.format(game.getGame_date())));
        }

        return rows;
    }
}
