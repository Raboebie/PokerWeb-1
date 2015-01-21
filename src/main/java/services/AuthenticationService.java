package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.Context;
import repositories.GameRepository;

/**
 * Created by Chris on 1/16/2015.
 */
@Singleton
public class AuthenticationService {

    private final String USERNAME = "username";

    @Inject
    private GameRepository gameRepository;

    public boolean register(Context context, String username, String password){
        if(!username.equals("") && !password.equals("") && !gameRepository.userExists(username)){
            gameRepository.insertUser(username, password);
            context.getSession().clear();
            context.getSession().put(USERNAME, username);
            return true;
        }
        return false;
    }

    public boolean login(Context context, String username, String password){
        if(!username.equals("") && !password.equals("")){
            boolean asd = gameRepository.comparePass(username, password);
            if(asd){
                context.getSession().clear();
                context.getSession().put(USERNAME, username);
            }
            return asd;
        }
        return false;
    }

    public void logout(Context context){
        context.getSession().clear();
    }

    public boolean userExists(String username){
        return gameRepository.userExists(username);
    }
}
