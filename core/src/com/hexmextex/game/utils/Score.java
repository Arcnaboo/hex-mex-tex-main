package com.hexmextex.game.utils;

public class Score {


    private int currentPlayerScore;



    private static Score score = null;

    private Score() {
        currentPlayerScore = 0;
    }

    public static  Score initScore() {
        if (score == null) {
            score = new Score();
        }
        score.currentPlayerScore = 0;
        return  score;
    }


    public void addToScore(int points) {
        currentPlayerScore += points;
    }

    public int getCurrentPlayerScore() {
        return currentPlayerScore;
    }

    @Override
    public String toString() {
        return "Score{" +
                "currentPlayerScore=" + currentPlayerScore +
                '}';
    }
}
