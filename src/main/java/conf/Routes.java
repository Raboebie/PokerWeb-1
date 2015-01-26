/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;


import controllers.AuthenticationController;
import controllers.GameController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.GeneralController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {

        router.GET().route("/").with(GeneralController.class, "index");
        router.GET().route("/reg").with(GeneralController.class, "reg");
        router.GET().route("/test").with(GeneralController.class, "test");
        router.GET().route("/viewgames").with(GeneralController.class, "viewgames");
        router.GET().route("/viewgamesbyuser").with(GeneralController.class, "viewgamesbyuser");

        router.POST().route("/newgame").with(GameController.class, "newGame");
        router.GET().route("/play/{id}/add").with(GameController.class, "addToGame");

        router.GET().route("/play").with(GameController.class, "play");
        router.GET().route("/getgamelist").with(GameController.class, "gamelist");
        router.GET().route("/play/{id}/lobby").with(GameController.class, "playlobby");
        router.GET().route("/play/{id}/history").with(GameController.class, "playhistory");

        //Helpers
        router.POST().route("/cards").with(GameController.class, "cards");
        router.POST().route("/resetDeck").with(GameController.class, "resetDeckAndCommit");

        router.GET().route("/logout").with(AuthenticationController.class, "logout");
        router.POST().route("/login").with(AuthenticationController.class, "login");
        router.POST().route("/register").with(AuthenticationController.class, "register");
        router.POST().route("/userExists").with(AuthenticationController.class, "userExists");

        //router.GET().route("/hello_world.json").with(GeneralController.class, "helloWorldJson");
        
 
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(GeneralController.class, "index");
    }

}
