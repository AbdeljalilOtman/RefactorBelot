package com.belote.domain;

/**
 * Represents a single match between two teams.
 */
public class MatchEntity {
    private int id;
    private int team1Id;
    private int team2Id;
    private int score1;
    private int score2;
    private int roundNumber;
    private boolean finished;

    public MatchEntity(int id, int team1Id, int team2Id,
                       int score1, int score2, int roundNumber, boolean finished) {
        this.id = id;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.score1 = score1;
        this.score2 = score2;
        this.roundNumber = roundNumber;
        this.finished = finished;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public int getTeam1Id() {
        return team1Id;
    }
    public int getTeam2Id() {
        return team2Id;
    }
    public int getScore1() {
        return score1;
    }
    public int getScore2() {
        return score2;
    }
    public int getRoundNumber() {
        return roundNumber;
    }
    public boolean isFinished() {
        return finished;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }
    public void setScore2(int score2) {
        this.score2 = score2;
    }
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return String.format("Match #%d [Team %d vs Team %d] => %d : %d (Round %d) Finished=%b",
                id, team1Id, team2Id, score1, score2, roundNumber, finished);
    }
}
