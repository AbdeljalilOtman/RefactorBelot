package com.belote.domain;

/**
 * Represents a belote tournament.
 */
public class Tournament {
    private int id;
    private String name;
    private int status; // 0=Inscriptions, 1=Matches Generated, 2=Ongoing, 3=Finished
    private int nbMatches; // optional if needed

    public Tournament(int id, String name, int status, int nbMatches) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.nbMatches = nbMatches;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getNbMatches() {
        return nbMatches;
    }

    @Override
    public String toString() {
        return String.format("Tournament #%d [%s] status=%d, nbMatches=%d", id, name, status, nbMatches);
    }
}
