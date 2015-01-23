package services;

import repositories.db.structure.Game;
import repositories.db.structure.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 1/23/2015.
 */
public class LobbyService {
    private Game game;
    private List<String> users = new ArrayList<>();

    public LobbyService(Game game){
        this.game = game;
        users.add(game.getGame_owner());
    }
    public int gameID(){
        return game.getGame_id();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public void addUser(String user){
        if(!users.contains(user))
            users.add(user);
    }

    public static boolean containsGame(List<LobbyService> lobbyServices, int id){
        for(LobbyService lobbyService : lobbyServices)
            if(lobbyService.getGame().getGame_id() == id) return true;
        return false;
    }
}
