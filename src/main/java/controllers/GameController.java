package controllers;

import Filters.Hosting;
import Filters.SecureFilter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.*;
import ninja.params.PathParam;
import services.GameService;
import services.LobbyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 1/23/2015.
 */
@Singleton
public class GameController {
    @Inject private GameService gameService;
    @Inject private Router router;

    @FilterWith({SecureFilter.class, Hosting.class})
    public Result play(Context context){
        Result res = Results.html();
        gameService.play(context, res);
        return res;
    }

    @FilterWith(SecureFilter.class)
    public Result playlobby(Context context, @PathParam("id") String game_id){
        Result res = Results.html();
        gameService.playLobby(context, res, game_id);
        return res;
    }

    @FilterWith(SecureFilter.class)
    public Result playhistory(Context context){
        Result res = Results.html();
        gameService.play(context, res);
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
    public Result newGame(Context context){
        gameService.newGame(context);
        return Results.redirect(router.getReverseRoute(GameController.class, "play"));
    }

    @FilterWith(SecureFilter.class)
    public Result addToGame(Context context, @PathParam("id") String game_id){
        gameService.addToGame(context.getSession().get("username"), game_id);
        return Results.redirect("/play/" + game_id + "/lobby");
    }
}
