package com.belote.domain;

/**
 * Represents a team with two players in the tournament.
 */
public class Team {
    private int id;
    private int number;
    private String player1;
    private String player2;

    public Team(int id, int number, String player1, String player2) {
        this.id = id;
        this.number = number;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int getId() {
        return id;
    }
    public int getNumber() {
        return number;
    }
    public String getPlayer1() {
        return player1;
    }
    public String getPlayer2() {
        return player2;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }
    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    @Override
    public String toString() {
        return String.format("Team #%d: [%s, %s]", number, player1, player2);
    }
}
