package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.cards.Card;
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
        List<Hand> hand = new ArrayList<>();
        for(String h : insertHands){
            hand.add(new Hand(h));
        }

        //Game doesn't exist
        gameRepository.commitGame(insertGameName, insertUsers, insertHands);

        res.render("Heading", "Game Completed:");
        res.render("Message",  deck.determineWinner(hand, insertUsers));

        insertUsers.clear();
        insertHands.clear();
        insertGameName = "";

        deck.resetDeck();
    }

    public void populateCards(Context context, Result res){
        Hand dealtHand = dealHand();
        insertGameName = context.getParameter("gamename");
        insertUsers.add(context.getParameter("username"));
        insertHands.add(dealtHand.toString());


        res.render("card0", dealtHand.getCards().get(0).toString());
        res.render("card1", dealtHand.getCards().get(1).toString());
        res.render("card2", dealtHand.getCards().get(2).toString());
        res.render("card3", dealtHand.getCards().get(3).toString());
        res.render("card4", dealtHand.getCards().get(4).toString());
        res.render("evaluation", evaluateHand(dealtHand));
        res.render("name", context.getParameter("username"));
    }

    public void play(Context context, Result res){
        List<String> users = gameRepository.getAllUserNames();
        List<String> loop = new ArrayList<>();
        if(context.getParameter("noplayers") != null)
            for (int i = 0; i < Integer.parseInt(context.getParameter("noplayers")); i++) loop.add(i + "");
        else
            for (int i = 0; i < 4; i++) loop.add(i + "");
        res.render("users", users);
        res.render("loops", loop);
    }

    public void viewGames(Result res){
        //res.render("rows", gameRepository.getAllGamesInRows());
        res.render("gameusers", gameRepository.getAllGame_Users());
        res.render("games", gameRepository.getAllGamesOrderedByDate());
        res.render("winners", gameRepository.getAllWinners());
    }
}
