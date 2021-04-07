package com.hexmextex.game;

import com.hexmextex.game.maps.GameMap;

public class Game {


    int currentLevel = 1;
    int pointsRequiredForNextLevel = (10000 * currentLevel);
    int movesRemaining = 50;
    int radius = 3;
    int colorCount = 5;
    int msize = 4;

    public GameMap current;


    public void start() {
        current = new GameMap(radius,colorCount, movesRemaining, msize);
    }

    public void levelUp() {
        currentLevel++;
        pointsRequiredForNextLevel = (10000 * currentLevel);
        if (currentLevel % 10 == 0) {
            radius++;
            msize++;
        }
        if (radius > 3) {
            radius = 3;
            msize = 6;
        }

        colorCount++;
        if (colorCount > 7) colorCount = 7;
        movesRemaining+=10;
        if (movesRemaining > 100) movesRemaining = 100;
        current = new GameMap(radius, colorCount, movesRemaining, msize);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getPointsRequiredForNextLevel() {
        return pointsRequiredForNextLevel;
    }
}
