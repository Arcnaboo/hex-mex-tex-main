package com.hexmextex.game.maps;

import com.badlogic.gdx.graphics.Color;
import com.hexmextex.game.utils.Hex;

public class MagentaHex extends GameHex {


    public MagentaHex(Hex position) {
        super(position);
    }

    @Override
    public Color getColour() {
        return Color.MAGENTA;
    }

    @Override
    public String getColourString() {
        return Color.MAGENTA.toString();
    }

    @Override
    public int spriteIndex() {
        return 5;
    }

    @Override
    public int points() {
        return super.points() + 3;
    }
}
