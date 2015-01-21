package repositories.db.structure;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Chris on 1/20/2015.
 */
@Entity
public class User {
    @Id
    @Size(max=100)
    private String user_name;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Game_User> gameUserList;

    public List<Game_User> getGameUserList() {
        return gameUserList;
    }

    public void setGameUserList(List<Game_User> gameUserList) {
        this.gameUserList = gameUserList;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
