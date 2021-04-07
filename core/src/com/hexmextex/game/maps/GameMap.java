package com.hexmextex.game.maps;

import com.badlogic.gdx.Gdx;
import com.hexmextex.game.utils.Hex;
import com.hexmextex.game.utils.Layout;
import com.hexmextex.game.utils.Point;

import java.util.*;

public class GameMap {


    private int movesRemaining;
    private int origRemaining;
    private long randSeed;

    public Layout layout;
    public Set<Hex> hexes;
    public List<GameHex> gameHexes;

    private GameHex firstClick = null;
    private GameHex secondClick = null;
    private  int radius;
    private int colorCount;
    private int msize;
    public GameMap(int radius, int colorCount, int movesRemaining, int msize) {
        origRemaining = movesRemaining;
        this.colorCount = colorCount;
        this.radius = radius;
        randSeed = System.currentTimeMillis();
        this.msize = msize;
        reset();
    }

    public void reset() {
        movesRemaining = origRemaining;
        Random random = new Random(randSeed);
        //layout = new Layout(Layout.flat, new Point(20,20), new Point(100, 150));
        int w = Gdx.graphics.getWidth();
        double psize = (w * 39.0 / 1280.0);
        layout = new Layout(Layout.flat, new Point(psize,psize), new Point(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));//new Point(1280/2, 720 / 2));
        hexes = new HashSet<>();
        gameHexes = new LinkedList<>();
        for (int q = -radius; q <= radius; q++) {
            int r1 = Math.max(-radius, -q - radius);
            int r2 = Math.min(radius, -q + radius);
            for (int r = r1; r <= r2; r++) {
                hexes.add(new Hex(q, r, -q-r));

                Hex hex = new Hex(q, r, -q-r);
                gameHexes.add(getRandomGameHex(random, hex, colorCount));
            }
        }

        int y;

        do {
            y = step();
        } while (y != 0);
    }

    public boolean isGameHexClicked(GameHex gameHex) {
        return (firstClick != null && firstClick.equals(gameHex)) ||
                (secondClick != null && secondClick.equals(gameHex));

    }

    public int getMovesRemaining() {
        return movesRemaining;
    }

    private LinkedList<GameHex> getGameHexes() {

        return new LinkedList<>(gameHexes);
    }


    void gameHexNeighRecursive(GameHex gameHex, List<GameHex> hexes, Set<GameHex> visited) {
        List<Hex> neighbours = gameHex.position.neighbours();
        List<GameHex> ghNeughbours = new ArrayList<>();
        for (Hex hex : neighbours) {
            GameHex neighbour = getGameHexByPosition(hex);
            if (neighbour != null && gameHex.getClass().getSimpleName().equals(neighbour.getClass().getSimpleName())) {
                ghNeughbours.add(neighbour);
            }
        }
        visited.add(gameHex);

        for (GameHex gh : ghNeughbours) {
            if (!hexes.contains(gh)) hexes.add(gh);

            if (!visited.contains(gh)) {
                gameHexNeighRecursive(gh, hexes, visited);
            }
        }
    }

    List<GameHex> gameHexNeighboursThatMatch(GameHex gameHex) {
        List<GameHex> hexes = new ArrayList<>();
        gameHexNeighRecursive(gameHex, hexes, new HashSet<GameHex>());
        return hexes;
    }



    public int step() {
        LinkedList<GameHex> hexes = getGameHexes();
        List<GameHex> toBeRemoved = new ArrayList<>();
        int score = 0;
        while (!hexes.isEmpty()) {
            GameHex gameHex = hexes.removeFirst();

            List<GameHex> matchingNeighbours = gameHexNeighboursThatMatch(gameHex);

            if (matchingNeighbours.size() >= msize) {
                toBeRemoved.add(gameHex);
                toBeRemoved.addAll(matchingNeighbours);
                for (GameHex gh : matchingNeighbours) {
                    if (hexes.contains(gh)) {
                        hexes.remove(gh);
                    }
                }
                score += (matchingNeighbours.size() + 1) * gameHex.points() * (matchingNeighbours.size() + 1);
            }
        }

        Random random = new Random(System.currentTimeMillis());
        for (GameHex gameHex : toBeRemoved) {
            if (gameHexes.contains(gameHex)) {
                gameHexes.remove(gameHex);
                GameHex newInstead = getRandomGameHex(random, gameHex.position, colorCount);
                gameHexes.add(newInstead);
            }
        }

        return score;
    }

    public void click(GameHex gameHex) {
        if (gameHex == null) {
            firstClick = secondClick = null;
            return;
        }

        if (firstClick == null) {
            firstClick = gameHex;
            return;
        }
        if (firstClick != null && gameHex.equals(firstClick)) {
            firstClick = null;
            return;
        }

        if (secondClick == null) {
            secondClick = gameHex;
        }
        if (firstClick.equals(secondClick)) {
            return;
        }
        swapHexes(firstClick, secondClick);
        movesRemaining-= 1;
        if (movesRemaining < 0) {
            movesRemaining = 0;
        }
        click(null);
    }



    public boolean isOver() {
        return movesRemaining == 0;
    }
    public void swapHexes(GameHex gameHex, GameHex other) {
        Hex hex = gameHex.position;
        Hex ot = other.position;
        List<Hex> neighbours = hex.neighbours();

        if (neighbours.contains(ot)) {
            gameHex.position = ot;
            other.position = hex;
        }

    }

    public void clickedGameHex(GameHex gameHex) {
        if (gameHexes.contains(gameHex)) {

        }
    }


    public GameHex getGameHexByPosition(Hex hex) {
        for (GameHex gameHex : gameHexes) {
            if (gameHex.position.equals(hex)) {
                return gameHex;
            }
        }
        return null;
    }

    private GameHex getRandomGameHex(Random random, Hex hex, int coloutCount) {
        int n = random.nextInt(coloutCount);
        if (n == 0) {
            return new BlueHex(hex);
        }
        if (n == 1) {
            return new GreenHex(hex);
        }
        if (n == 2) {
            return new MagentaHex(hex);
        }
        if (n == 3) {
            return new OrangeHex(hex);
        }
        if (n == 4) {
            return new RedHex(hex);
        }
        if (n == 5) {
            return  new WhiteHex(hex);
        }

        return new YellowHex(hex);
    }

}
