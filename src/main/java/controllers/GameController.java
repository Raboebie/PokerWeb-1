package controllers;

import Filters.GameDone;
import Filters.Playing;
import Filters.SecureFilter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.*;
import ninja.params.PathParam;
import services.GameService;

/**
 * Created by Chris on 1/23/2015.
 */
@Singleton
public class GameController {
    @Inject private GameService gameService;
    @Inject private Router router;

    @FilterWith({SecureFilter.class, Playing.class})
    public Result play(Context context){
        Result res = Results.html();
        gameService.play(context, res);
        return res;
    }

    @FilterWith({SecureFilter.class, GameDone.class})
    public Result playlobby(Context context, @PathParam("id") String game_id){
        Result res = Results.html();
        gameService.playLobby(context, res, game_id);
        return res;
    }

    @FilterWith({SecureFilter.class})
    public Result playhistory(Context context, @PathParam("id") String game_id){
        Result res = Results.html();
        gameService.playhistory(context, res, game_id);
        return res;
    }

    @FilterWith(SecureFilter.class)
    public Result cards(Context context){
        Result res = Results.html();
        gameService.populateCards(context, res);
        return res;
    }

    @FilterWith(SecureFilter.class)
    public Result resetDeckAndCommit(Context context){
        Result res = Results.html();
        gameService.resetDeckAndCommit(context, res);
        return res;
    }

    @FilterWith(SecureFilter.class)
    public synchronized Result newGame(Context context){
        gameService.newGame(context);
        return Results.redirect(router.getReverseRoute(GameController.class, "play"));
    }

    @FilterWith(SecureFilter.class)
    public Result addToGame(Context context, @PathParam("id") String game_id){
        gameService.addToGame(context.getSession().get("username"), game_id);
        return Results.redirect("/play/" + game_id + "/lobby");
    }

    @FilterWith(SecureFilter.class)
    public Result gamelist(Context context){
        Result res = Results.html();
        gameService.getGameList(context, res);
        return res;
    }

    @FilterWith(SecureFilter.class)
    public Result getplayers(Context context, @PathParam("id") String game_id){
        Result res = Results.html();
        gameService.getPlayers(context, res, game_id);
        return res;
    }
}
