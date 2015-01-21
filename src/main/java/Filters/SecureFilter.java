package Filters;

import com.google.inject.Inject;
import controllers.ApplicationController;
import ninja.*;
import services.AuthenticationService;

/**
 * Created by Chris on 1/16/2015.
 */
public class SecureFilter implements Filter {
    public static final String USERNAME = "username";

    @Inject private Router router;
    @Inject private AuthenticationService authenticationService;

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        if(!authenticationService.userExists(context.getSession().get(USERNAME))){
            context.getSession().clear();
        }
        if(context.getSession() == null || context.getSession().get(USERNAME) == null){
            context.getFlashScope().error("Not Logged In");
            return Results.redirect(router.getReverseRoute(ApplicationController.class, "index"));
        }
        return filterChain.next(context);
    }
}
