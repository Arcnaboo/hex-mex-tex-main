package com.hexmextex.game.maps;

import com.badlogic.gdx.graphics.Color;
import com.hexmextex.game.utils.Hex;

public class GreenHex extends GameHex {


    public GreenHex(Hex position) {
        super(position);
    }

    @Override
    public Color getColour() {
        return Color.GREEN;
    }

    @Override
    public String getColourString() {
        return null;
    }

    @Override
    public int spriteIndex() {
        return 3;
    }

    @Override
    public int points() {
        return super.points() + 2;
    }
}
