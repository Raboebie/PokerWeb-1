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

    public String determineWinner(List<Hand> hands, List<String> users){
        List<Integer> handValues = new ArrayList<>();
        for(Hand hand : hands){
            handValues.add(deck.handStrength(deck.evaluation(hand)));
        }

        int min = 90;
        for(Integer value : handValues){
            if(value < min) min = value;
        }

        int count = 0;
        for(Integer value: handValues){
            if(value == min) count++;
        }

        if(count == 1){
            return "Winner: " + users.get(handValues.indexOf(min));
        }
        else{
            String temp = "";
            for(int i = 0; i < handValues.size(); i++){
                if(handValues.get(i) == min) temp += users.get(i) + "; ";
            }
            return "Tie Between: " + temp;
        }
    }

    public void resetDeckAndCommit(Result res){
        List<Hand> hand = new ArrayList<>();
        for(String h : insertHands){
            hand.add(new Hand(h));
        }

        //Game doesn't exist
        if(!gameRepository.gameExists(insertGameName)){
            gameRepository.commitGame(insertGameName, insertUsers, insertHands);

            res.render("Message", "Game Completed: " + determineWinner(hand, insertUsers));

            insertUsers.clear();
            insertHands.clear();
            insertGameName = "";
        }
        //Game exists, throw away
        else{
            res.render("Message", "Game Thrown Away - Not Unique Name: " + determineWinner(hand, insertUsers));

            insertUsers.clear();
            insertHands.clear();
            insertGameName = "";
        }

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
}
