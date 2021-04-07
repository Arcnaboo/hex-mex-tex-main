package com.hexmextex.game.maps;

import com.badlogic.gdx.graphics.Color;
import com.hexmextex.game.utils.Hex;

public class RedHex extends GameHex {


    public RedHex(Hex position) {
        super(position);
    }

    @Override
    public Color getColour() {
        return Color.RED;
    }

    @Override
    public String getColourString() {
        return Color.RED.toString();
    }

    @Override
    public int spriteIndex() {
        return 9;
    }

    @Override
    public int points() {
        return super.points() + 2;
    }
}
