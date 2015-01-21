package model.deck;

import model.cards.Card;
import model.cards.Hand;
import model.cards.Rank;
import model.cards.Suit;
import model.evaluators.HandEvaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Chris on 1/11/2015.
 */
public class Deck {
    static List<Card> deck = new ArrayList<>();

    public Deck(){
        resetDeck();
    }

    public void resetDeck(){
        deck.clear();
        for(int i = 0; i < 13; i++){
            for(int j = 0; j < 4; j++){
                deck.add(new Card(Rank.fromOrdinal(i).displayValue + Suit.fromOrdinal(j).suitValue));
            }
        }
        Collections.shuffle(deck);
    }

    public Hand dealHand(){
        if(deck.size() == 0) resetDeck();
        if(deck.size() > 5){
            Hand hand = new Hand(deck.get(0), deck.get(1), deck.get(2), deck.get(3), deck.get(4));
            for(int i = 0; i < 5; i++) deck.remove(0);
            return hand;
        }
        return null;
    }

    public String evaluation(Hand hand){
        String evaluation = "";

        if(HandEvaluator.isStraightFlush(hand)) evaluation = "Straight Flush";
        else if(HandEvaluator.isFourOfAKind(hand)) evaluation = "Four Of A Kind";
        else if(HandEvaluator.isFullHouse(hand)) evaluation = "Full House";
        else if(HandEvaluator.isFlush(hand)) evaluation = "Flush";
        else if(HandEvaluator.isStraight(hand)) evaluation = "Straight";
        else if(HandEvaluator.isThreeOfAKind(hand)) evaluation = "Three Of A Kind";
        else if(HandEvaluator.isTwoPair(hand)) evaluation = "Two Pair";
        else if(HandEvaluator.isOnePair(hand)) evaluation = "One Pair";
        else evaluation = "High Card";

        return evaluation;
    }

    public int handStrength(String evaluation){
        if(evaluation.equals("Straight Flush")) return 0;
        else if(evaluation.equals("Four Of A Kind")) return 1;
        else if(evaluation.equals("Full House")) return 2;
        else if(evaluation.equals("Flush")) return 3;
        else if(evaluation.equals("Straight")) return 4;
        else if(evaluation.equals("Three Of A Kind")) return 5;
        else if(evaluation.equals("Two Pair")) return 6;
        else if(evaluation.equals("One Pair")) return 7;
        else if(evaluation.equals("High Card")) return 8;
        else return -1;
    }
}
