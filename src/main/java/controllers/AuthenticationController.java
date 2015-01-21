package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.Router;
import ninja.params.PathParam;
import services.AuthenticationService;

/**
 * Created by Chris on 1/16/2015.
 */
@Singleton
public class AuthenticationController {

    private final String USERNAME = "username";

    @Inject private AuthenticationService authenticationService;
    @Inject private Router router;

    public Result login(Context context){
        if(authenticationService.login(context, context.getParameter("username", ""), context.getParameter("password", "")))
            return Results.redirect(router.getReverseRoute(ApplicationController.class, "play"));
        context.getFlashScope().error("Login Failed - Invalid Username/Password Combination");
        return Results.redirect(router.getReverseRoute(ApplicationController.class, "index"));
    }

    public Result register(Context context){
        if(authenticationService.register(context, context.getParameter("username", ""), context.getParameter("password", "")))
            return Results.redirect(router.getReverseRoute(ApplicationController.class, "play"));
        context.getFlashScope().error("Registration Failed - Username Already In Use or Password/Username Too Short");
        return Results.redirect(router.getReverseRoute(ApplicationController.class, "index"));
    }

    public Result logout(Context context){
        context.getSession().clear();
        return Results.redirect(router.getReverseRoute(ApplicationController.class, "index"));
    }

    public Result userExists(Context context){
        Result res = Results.html();
        res.render("exists", authenticationService.userExists(context.getParameter("username")) + "");
        return res;
    }
}