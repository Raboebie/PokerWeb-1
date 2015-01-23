package Filters;

import com.google.inject.Inject;
import controllers.GeneralController;
import ninja.*;

/**
 * Created by Chris on 1/19/2015.
 */
public class LoggedInFilter implements  Filter{
    public static final String USERNAME = "username";

    @Inject private Router router;

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        if(context.getSession().get(USERNAME) != null){
            context.getFlashScope().success("Already Logged In");
            return Results.redirect(router.getReverseRoute(GeneralController.class, "play"));
        }
        return filterChain.next(context);
    }
}
