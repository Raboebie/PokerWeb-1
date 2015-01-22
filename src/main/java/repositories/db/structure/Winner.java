package repositories.db.structure;

/**
 * Created by Chris on 1/21/2015.
 */
public class Winner {
    String name;
    int game_id;

    public Winner(String name, int game_id) {
        this.name = name;
        this.game_id = game_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }
}

