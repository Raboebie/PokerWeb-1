package controllers;

import Filters.SecureFilter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import services.GameService;

/**
 * Created by Chris on 1/23/2015.
 */
@Singleton
public class GameController {
    @Inject private GameService gameService;

    @FilterWith(SecureFilter.class)
    public Result play(Context context){
        Result res = Results.html();
        gameService.play(context, res);
        return res;
    }

    @FilterWith(SecureFilter.class)
    public Result playlobby(Context context){
        Result res = Results.html();
        //gameService.play(context, res);
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
    public Result resetDeckAndCommit(){
        Result res = Results.html();
        gameService.resetDeckAndCommit(res);
        return res;
    }
}
