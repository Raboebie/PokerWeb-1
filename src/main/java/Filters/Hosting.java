package Filters;

import com.google.inject.Inject;
import controllers.GameController;
import ninja.*;
import repositories.GameRepository;
import repositories.db.structure.Game;

import java.util.List;

/**
 * Created by Chris on 1/23/2015.
 */
public class Hosting implements Filter {
    @Inject GameRepository gameRepository;
    @Inject Router router;

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        List<Game> games = gameRepository.getAllUnfinishedGames();
        for(Game game: games){
            System.out.println("play/" + game.getGame_id() + "/lobby");
            if(game.getGame_owner().equals(context.getSession().get("username"))) return Results.redirect("/play/" + game.getGame_id() + "/lobby");
        }
        return filterChain.next(context);
    }
}
