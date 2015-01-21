package repositories.db.structure;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 1/20/2015.
 */
@Entity
public class Game {
    @Id
    @GeneratedValue
    private int game_id;

    private String game_name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date game_date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Game_User> gameUserList;

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public List<Game_User> getGameUserList() {
        return gameUserList;
    }

    public void setGameUserList(List<Game_User> gameUserList) {
        this.gameUserList = gameUserList;
    }

    public Date getGame_date() {
        return game_date;
    }

    public void setGame_date(Date game_date) {
        this.game_date = game_date;
    }
}
