package com.example.model;

/**
 * User: jitse
 * Date: 12/4/13
 * Time: 3:27 PM
 */
public class Stats {
    private int matchWins;
    private int matchLosses;

    private int gameWins;
    private int gameLosses;

    public int getMatchWins() {
        return matchWins;
    }

    public void setMatchWins(int matchWins) {
        this.matchWins = matchWins;
    }

    public int getMatchLosses() {
        return matchLosses;
    }

    public void setMatchLosses(int matchLosses) {
        this.matchLosses = matchLosses;
    }

    public int getGameWins() {
        return gameWins;
    }

    public void setGameWins(int gameWins) {
        this.gameWins = gameWins;
    }

    public int getGameLosses() {
        return gameLosses;
    }

    public void setGameLosses(int gameLosses) {
        this.gameLosses = gameLosses;
    }
}
