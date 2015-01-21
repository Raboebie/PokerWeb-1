package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.cards.Hand;
import model.deck.Deck;
import ninja.Context;
import ninja.Result;
import repositories.GameRepository;
import repositories.db.repositories.DBGameRepository;
import repositories.db.repositories.DBGame_UserRepository;
import repositories.db.repositories.DBUserRepository;
import repositories.db.structure.Game;
import repositories.db.structure.Game_User;
import repositories.db.structure.Game_User_ID;
import repositories.db.structure.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 1/12/2015.
 */
@Singleton
public class GameService {

    @Inject private Deck deck;
    @Inject private GameRepository gameRepository;

    private String insertGameName = "";
    private List<String> insertUsers = new ArrayList<>();
    private List<String> insertHands = new ArrayList<>();

    private Hand dealHand(){
        return deck.dealHand();
    }

    private String evaluateHand(Hand hand){
        return deck.evaluation(hand);
    }

    public void resetDeckAndCommit(Result res){
        //Game doesn't exist
        if(!gameRepository.gameExists(insertGameName)){
            gameRepository.commitGame(insertGameName, insertUsers, insertHands);

            insertUsers.clear();
            insertHands.clear();
            insertGameName = "";

            res.render("Message", "Game Completed");
        }
        //Game exists, throw away
        else{
            insertUsers.clear();
            insertHands.clear();
            insertGameName = "";
            res.render("Message", "Game Thrown Away - Not Unique Name");
        }

        deck.resetDeck();
    }

    public void populateCards(Context context, Result res){
        Hand dealtHand = dealHand();
        insertGameName = context.getParameter("gamename");
        insertUsers.add(context.getParameter("username"));
        insertHands.add(dealHand().toString());


        res.render("card0", dealtHand.getCards().get(0).toString());
        res.render("card1", dealtHand.getCards().get(1).toString());
        res.render("card2", dealtHand.getCards().get(2).toString());
        res.render("card3", dealtHand.getCards().get(3).toString());
        res.render("card4", dealtHand.getCards().get(4).toString());
        res.render("evaluation", evaluateHand(dealtHand));
        res.render("name", context.getParameter("username"));
    }
}
