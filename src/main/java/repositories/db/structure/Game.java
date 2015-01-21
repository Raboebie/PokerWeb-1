package repositories.db.structure;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Chris on 1/20/2015.
 */
@Entity
public class Game {
    @Id
    @Size(max=100)
    private String game_name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Game_User> gameUserList;

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public List<Game_User> getGameUserList() {
        return gameUserList;
    }

    public void setGameUserList(List<Game_User> gameUserList) {
        this.gameUserList = gameUserList;
    }
}
