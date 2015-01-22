package repositories.db.repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.cards.Hand;
import model.deck.Deck;
import ninja.jpa.UnitOfWork;
import repositories.db.structure.Game;
import repositories.db.structure.Game_User;
import repositories.db.structure.Winner;
import services.GameService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public List<Winner> getAllWinners(){
        List<Winner> winners = new ArrayList<>();
        List<Game> games = dbGameRepository.getAllGamesOrderedByDate();

        for(Game game : games){
            List<Game_User> game_users = getGame_UserByGame_ID(game.getGame_id() + "");
            List<String> hands = new ArrayList<>();
            List<String> users = new ArrayList<>();
            for(Game_User game_user : game_users){
                hands.add(game_user.getHand());
                users.add(game_user.getUser().getUser_name());
            }
            winners.add(new Winner(deck.determineWinner(deck.getHands(hands), users), game.getGame_id()));
        }
        return winners;
    }

    @UnitOfWork
    public List<Game_User> getGame_UserByGame_ID(String id){
        return getEntityManager().createQuery("SELECT g FROM Game_User g WHERE game_id = :game_id").setParameter("game_id", id).getResultList();
    }

    @UnitOfWork
    public List<Game_User> getAllGame_Users(){
        return getEntityManager().createQuery("SELECT g FROM Game_User g").getResultList();
    }


}
