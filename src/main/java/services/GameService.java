package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.cards.Hand;
import model.deck.Deck;
import ninja.*;
import org.h2.mvstore.ConcurrentArrayList;
import repositories.GameRepository;
import repositories.db.structure.Game;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Chris on 1/12/2015.
 */
@Singleton
public class GameService {

    @Inject private Deck deck;
    @Inject private GameRepository gameRepository;
    @Inject private Router router;

    private volatile ConcurrentArrayList<LobbyService> lobbyServices = new ConcurrentArrayList<>();
    private volatile ConcurrentHashMap<Integer, Deck> gameDeckMap = new ConcurrentHashMap<>();
    private volatile ConcurrentHashMap<Integer, List<String>> gameUserMap = new ConcurrentHashMap<>();
    private volatile ConcurrentHashMap<Integer, List<String>> gameHandMap = new ConcurrentHashMap<>();

    private volatile Instant lastNewGameUpdate = Instant.now();
    private volatile ConcurrentHashMap<Integer, Instant> gameInstantMap = new ConcurrentHashMap<>();

    private Hand dealHand(){
        return deck.dealHand();
    }

    private Hand dealHand(Deck deck){
        return deck.dealHand();
    }

    private String evaluateHand(Hand hand){
        return deck.evaluation(hand);
    }

    public void resetDeckAndCommit(Context context, Result res){
        Game game = gameRepository.getGameByID(context.getParameter("gameid"));
        if(game == null) System.out.println("Hier Kom Groot Kak v2");

        //System.out.println(gameHandMap.get(game).size());
        List<Hand> hand = new ArrayList<>();
        for(String h : gameHandMap.get(game.getGame_id())){
            hand.add(new Hand(h));
        }

        gameRepository.commitGame(gameRepository.getGameByID(context.getParameter("gameid")), gameUserMap.get(game.getGame_id()), gameHandMap.get(game.getGame_id()));

        LobbyService delete = null;
        for(Iterator<LobbyService> i = lobbyServices.iterator(); i.hasNext(); ) {
            LobbyService lobbyService = i.next();
            if(lobbyService.gameID() == game.getGame_id()){
                delete = lobbyService;
            }
        }

        res.render("Heading", "Game Completed:");
        res.render("Message",  deck.determineWinner(hand, gameUserMap.get(game.getGame_id())));

        if(delete != null)
            lobbyServices.removeFirst(delete);

        gameDeckMap.remove(game.getGame_id());
        gameHandMap.remove(game.getGame_id());
        gameUserMap.remove(game.getGame_id());

        lastNewGameUpdate = Instant.now();

        gameInstantMap.put(game.getGame_id(), Instant.now());
    }

    public void populateCards(Context context, Result res){
        Game game = gameRepository.getGameByID(context.getParameter("gameid"));
        if(game == null) System.out.println("Hier Kom Groot Kak");

        if(!gameDeckMap.containsKey(game.getGame_id())){
            gameDeckMap.put(game.getGame_id(), new Deck());
        }

        if(!gameUserMap.containsKey(game.getGame_id())) {
            gameUserMap.put(game.getGame_id(), new ArrayList<>());
        }

        if(!gameHandMap.containsKey(game.getGame_id())){
            gameHandMap.put(game.getGame_id(), new ArrayList<>());
        }

        Hand dealtHand = dealHand(gameDeckMap.get(game.getGame_id()));

        gameUserMap.get(game.getGame_id()).add(context.getParameter("username"));
        gameHandMap.get(game.getGame_id()).add(dealtHand.toString());

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

    public void playhistory(Context context, Result res, String GameID){
        res.render("gameusers", gameRepository.getAllGame_Users());
        res.render("games", gameRepository.getAllGamesByIDOrderedByDate(GameID));
        res.render("winners", gameRepository.getAllWinners());
    }

    public void newGame(Context context) {
        Game game = new Game();
        game.setGame_name(context.getParameter("newgame"));
        game.setGame_date(new Date());
        game.setGame_owner(context.getSession().get("username"));
        gameRepository.newGame(game);

        lastNewGameUpdate = Instant.now();
        gameInstantMap.put(game.getGame_id(), Instant.now());
    }

    public void viewGames(Result res){
        //res.render("rows", gameRepository.getAllGamesInRows());
        res.render("gameusers", gameRepository.getAllGame_Users());
        res.render("games", gameRepository.getAllFinishedGamesOrderedByDate());
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
        res.render("redirect", false);
    }

    public void addToGame(String username, String game_id){
        gameInstantMap.put(Integer.parseInt(game_id), Instant.now());
        LobbyService current = getLobbyByGameID(game_id);
        current.addUser(username);
    }

    public List<Game> getUnfinishedGames(){
        List<Game> unfinishedGames = gameRepository.getAllUnfinishedGames();
        for(Game game : unfinishedGames){
            if(!LobbyService.containsGame(lobbyServices, game.getGame_id())) {
                lobbyServices.add(new LobbyService(game));
            }
        }
        return unfinishedGames;
    }

    public Map<String, Integer> playingUsers(){
        getUnfinishedGames();
        Map<String, Integer> playingUsers = new HashMap<>();

        for(Iterator<LobbyService> i = lobbyServices.iterator(); i.hasNext(); ){
            LobbyService lobbyService = i.next();
            for(String user : lobbyService.getUsers()){
                playingUsers.put(user, lobbyService.gameID());
            }
        }

        return playingUsers;
    }

    public LobbyService getLobbyByGameID(String ID){
        getUnfinishedGames();
        for(Iterator<LobbyService> i = lobbyServices.iterator(); i.hasNext(); ){
            LobbyService lobby = i.next();
            if(lobby.gameID() == Integer.parseInt(ID)){
                return lobby;
            }
        }
        return null;
    }

    public void getGameList(Context context, Result res) {
        Instant lastNewGameUpdate = this.lastNewGameUpdate;

        while(lastNewGameUpdate == this.lastNewGameUpdate){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Game> activeGames = getUnfinishedGames();
        res.render("games", activeGames);
    }

    public void getPlayers(Context context, Result res, String game_id) {
        Instant lastNewGameUpdate = gameInstantMap.get(Integer.parseInt(game_id));

        while(lastNewGameUpdate == gameInstantMap.get(Integer.parseInt(game_id))){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Integer> gameids = getUnfinishedGames().stream().map(g -> g.getGame_id()).collect(Collectors.toList());
        if(!gameids.contains(Integer.parseInt(game_id))){
            res.render("users", new ArrayList<>());
            res.render("username", "");
            res.render("gameid", game_id);
            res.render("redirect", true);
        }
        else{
            LobbyService current = getLobbyByGameID(game_id);
            res.render("users", current.getUsers());
            res.render("username", context.getSession().get("username"));
            res.render("gameid", game_id);
            res.render("redirect", false);
        }
    }
}
