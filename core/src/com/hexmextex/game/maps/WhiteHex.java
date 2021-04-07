package com.hexmextex.game.maps;

import com.badlogic.gdx.graphics.Color;
import com.hexmextex.game.utils.Hex;

public class WhiteHex extends GameHex {


    public WhiteHex(Hex position) {
        super(position);
    }

    @Override
    public Color getColour() {
        return Color.WHITE;
    }

    @Override
    public String getColourString() {
        return Color.WHITE.toString();
    }

    @Override
    public int spriteIndex() {
        return 1;
    }

    @Override
    public int points() {
        return super.points() + 3;
    }

}
