package com.hexmextex.game.maps;

import com.badlogic.gdx.graphics.Color;
import com.hexmextex.game.utils.Hex;

public class YellowHex extends GameHex {


    public YellowHex(Hex position) {
        super(position);
    }

    @Override
    public Color getColour() {
        return Color.YELLOW;
    }

    @Override
    public String getColourString() {
        return Color.YELLOW.toString();
    }

    @Override
    public int spriteIndex() {
        return 10;
    }

    @Override
    public int points() {
        return super.points() + 1;
    }
}
