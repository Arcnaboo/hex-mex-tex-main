package com.hexmextex.game.maps;

import com.badlogic.gdx.graphics.Color;
import com.hexmextex.game.utils.Hex;

public abstract class GameHex{


    public Hex position;

    public GameHex() {
        position = new Hex(0,0, 0);
    }

    public GameHex(Hex position) {
        this.position = position;
    }

    public abstract Color getColour();

    public abstract String getColourString();

    public abstract int spriteIndex();

    public int points() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this == null || this.position == null || getClass() != o.getClass()) return false;
        GameHex gameHex = (GameHex) o;
        return position.equals(gameHex.position) && spriteIndex() == gameHex.spriteIndex() && getColour().equals(gameHex.getColour());
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +"{" +
                "position=" + position +
                '}';
    }
}
