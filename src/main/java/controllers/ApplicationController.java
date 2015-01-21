/**
 * Copyright (C) 2013 the original author or authors.
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

package controllers;

import Filters.LoggedInFilter;
import Filters.SecureFilter;
import com.google.inject.Inject;
import ninja.*;

import com.google.inject.Singleton;
import repositories.GameRepository;
import services.GameService;

import java.util.List;


@Singleton
public class ApplicationController {

    private final String USERNAME = "username";

    @Inject GameRepository gameRepository;
    @Inject private GameService gameService;
    @Inject private Router router;

    @FilterWith(LoggedInFilter.class)
    public Result index(Context context) {
        return Results.html();
    }

    @FilterWith(LoggedInFilter.class)
    public Result reg(Context context){
        return Results.html();
    }

    @FilterWith(SecureFilter.class)
    public Result play(Context context){
        Result res = Results.html();
        List<String> users = gameRepository.getAllUserNames();
        res.render("users", users);
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

    @FilterWith(SecureFilter.class)
    public Result viewgames(){
        Result res = Results.html();
        res.render("rows", gameRepository.getAllGamesInRows());
        return res;
    }

    public Result test(){
        gameRepository.insertTestData();
        return Results.redirect("/");
    }

}