package Filters;

import com.google.inject.Inject;
import controllers.GameController;
import ninja.*;
import repositories.GameRepository;
import repositories.db.structure.Game;
import services.GameService;

import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 1/23/2015.
 */
public class Playing implements Filter {
    @Inject GameRepository gameRepository;
    @Inject GameService gameService;
    @Inject Router router;

    @Override
    public Result filter(FilterChain filterChain, Context context) {

        List<Game> games = gameRepository.getAllUnfinishedGames();
        for(Game game: games){
            if(game.getGame_owner().equals(context.getSession().get("username"))) {
                return Results.redirect("/play/" + game.getGame_id() + "/lobby");
            }
        }

        Map<String, Integer> playingUsers = gameService.playingUsers();
        System.out.println(playingUsers);
        if(playingUsers.get(context.getSession().get("username")) != null){
            System.out.println("HERE");
            return Results.redirect("/play/" + playingUsers.get(context.getSession().get("username")) + "/lobby");
        }


        return filterChain.next(context);
    }
}
