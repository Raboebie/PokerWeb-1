package Filters;

import com.google.inject.Inject;
import ninja.*;
import ninja.params.ParamParsers;
import ninja.params.PathParam;
import repositories.GameRepository;
import repositories.db.structure.Game;
import services.GameService;

import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 1/23/2015.
 */
public class GameDone implements Filter {
    @Inject GameService gameService;

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        String id = context.getPathParameter("id");

        boolean contained = false;

        List<Game> unfinishedGameList = gameService.getUnfinishedGames();
        for(Game game: unfinishedGameList){
            if(game.getGame_id() == Integer.parseInt(id)){
                contained = true;
            }
        }

        if(!contained) return Results.redirect("/play/" + id + "/history");

        return filterChain.next(context);
    }
}
