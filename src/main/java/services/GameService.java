package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import controllers.GameController;
import model.cards.Card;
import model.cards.Hand;
import model.deck.Deck;
import ninja.*;
import repositories.GameRepository;
import repositories.db.repositories.DBGameRepository;
import repositories.db.repositories.DBGame_UserRepository;
import repositories.db.repositories.DBUserRepository;
import repositories.db.structure.Game;
import repositories.db.structure.Game_User;
import repositories.db.structure.Game_User_ID;
import repositories.db.structure.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 1/12/2015.
 */
@Singleton
public class GameService {

    @Inject private Deck deck;
    @Inject private GameRepository gameRepository;
    @Inject private Router router;

    private List<LobbyService> lobbyServices = new ArrayList<>();

    private String insertGameName = "";
    private List<String> insertUsers = new ArrayList<>();
    private List<String> insertHands = new ArrayList<>();

    private Hand dealHand(){
        return deck.dealHand();
    }

    private String evaluateHand(Hand hand){
        return deck.evaluation(hand);
    }

    public void resetDeckAndCommit(Context context, Result res){
        List<Hand> hand = new ArrayList<>();
        for(String h : insertHands){
            hand.add(new Hand(h));
        }

        //Game doesn't exist
        gameRepository.commitGame(gameRepository.getGameByID(context.getParameter("gameid")), insertUsers, insertHands);

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
        List<Game> activeGames = getUnfinishedGames();
        res.render("games", activeGames);
    }

    public void newGame(Context context) {
        Game game = new Game();
        game.setGame_name(context.getParameter("newgame"));
        game.setGame_date(new Date());
        game.setGame_owner(context.getSession().get("username"));
        gameRepository.newGame(game);
    }

    public void viewGames(Result res){
        //res.render("rows", gameRepository.getAllGamesInRows());
        res.render("gameusers", gameRepository.getAllGame_Users());
        res.render("games", gameRepository.getAllGamesOrderedByDate());
        res.render("winners", gameRepository.getAllWinners());
    }

    public void viewGamesByUser(Context context, Result res){
        //res.render("rows", gameRepository.getAllGamesInRows());
        res.render("gameusers", gameRepository.getGame_UsersByUser_Name(context.getSession().get("username")));
        res.render("games", gameRepository.getAllGamesByUsernameOrderedByDate(context.getSession().get("username")));
        res.render("winners", gameRepository.getAllWinners());
    }

    public void playLobby(Context context, Result res, String game_id) {
        LobbyService current = getLobbyByGameID(game_id);
        res.render("users", current.getUsers());
        res.render("username", context.getSession().get("username"));
        res.render("owner", current.getGame().getGame_owner());
        res.render("gameid", game_id);
    }

    public void addToGame(String username, String game_id){
        LobbyService current = getLobbyByGameID(game_id);
        current.addUser(username);
    }

    public List<Game> getUnfinishedGames(){
        List<Game> unfinishedGames = gameRepository.getAllUnfinishedGames();
        List<Game> activeGames = new ArrayList<>();
        for(Game game : unfinishedGames){
            activeGames.add(game);
            if(!LobbyService.containsGame(lobbyServices, game.getGame_id())) {
                lobbyServices.add(new LobbyService(game));
            }
        }
        return unfinishedGames;
    }

    public LobbyService getLobbyByGameID(String ID){
        getUnfinishedGames();
        for(LobbyService lobby: lobbyServices){
            if(lobby.gameID() == Integer.parseInt(ID)){
                return lobby;
            }
        }
        return null;
    }
}
