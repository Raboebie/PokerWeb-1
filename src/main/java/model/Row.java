package model;

/**
 * Created by Chris on 1/21/2015.
 */
public class Row{
    int number;
    String game;
    String user0;
    String user1;
    String user2;
    String user3;
    String date;

    public Row(int number, String game, String user0, String user1, String user2, String user3, String date) {
        this.number = number;
        this.game = game;
        this.user0 = user0;
        this.user1 = user1;
        this.user2 = user2;
        this.user3 = user3;
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUser0() {
        return user0;
    }

    public void setUser0(String user0) {
        this.user0 = user0;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getUser3() {
        return user3;
    }

    public void setUser3(String user3) {
        this.user3 = user3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

