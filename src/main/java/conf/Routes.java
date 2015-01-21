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


import Filters.LoggedInFilter;
import controllers.AuthenticationController;
import ninja.AssetsController;
import ninja.FilterWith;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;
import services.AuthenticationService;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {

        router.GET().route("/").with(ApplicationController.class, "index");
        router.GET().route("/reg").with(ApplicationController.class, "reg");
        router.GET().route("/play").with(ApplicationController.class, "play");
        router.GET().route("/test").with(ApplicationController.class, "test");
        router.GET().route("/viewgames").with(ApplicationController.class, "viewgames");

        //Helpers
        router.POST().route("/cards").with(ApplicationController.class, "cards");
        router.POST().route("/resetDeck").with(ApplicationController.class, "resetDeckAndCommit");

        router.GET().route("/logout").with(AuthenticationController.class, "logout");
        router.POST().route("/login").with(AuthenticationController.class, "login");
        router.POST().route("/register").with(AuthenticationController.class, "register");
        router.POST().route("/userExists").with(AuthenticationController.class, "userExists");

        //router.GET().route("/hello_world.json").with(ApplicationController.class, "helloWorldJson");
        
 
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
