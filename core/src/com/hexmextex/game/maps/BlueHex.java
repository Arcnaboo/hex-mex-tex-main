package com.hexmextex.game.maps;

import com.badlogic.gdx.graphics.Color;
import com.hexmextex.game.utils.Hex;

public class BlueHex extends GameHex {


    public BlueHex(Hex position) {
        super(position);
    }

    @Override
    public Color getColour() {
        return Color.BLUE;
    }

    @Override
    public String getColourString() {
        return null;
    }

    @Override
    public int spriteIndex() {
        return 0;
    }

    @Override
    public int points() {
        return super.points() + 2;
    }
}
