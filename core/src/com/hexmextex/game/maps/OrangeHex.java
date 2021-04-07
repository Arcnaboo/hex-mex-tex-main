package com.hexmextex.game.maps;

import com.badlogic.gdx.graphics.Color;
import com.hexmextex.game.utils.Hex;

public class OrangeHex extends GameHex {


    public OrangeHex(Hex position) {
        super(position);
    }

    @Override
    public Color getColour() {
        return Color.ORANGE;
    }

    @Override
    public String getColourString() {
        return Color.ORANGE.toString();
    }

    @Override
    public int spriteIndex() {
        return 7;
    }

    @Override
    public int points() {
        return super.points() + 1;
    }
}
