package com.hexmextex.game.utils;

import java.util.Arrays;
import java.util.List;

public class HexMe {

    private static final HexMe[] DIRECTIONS = {
            new HexMe(1, 0, -1), new HexMe(1, -1, 0),
            new HexMe(0, -1, 1), new HexMe(-1, 0, 1),
            new HexMe(-1, 1, 0), new HexMe(0, 1, -1)
    };



    public final int q;
    public final int r;
    public final int s;

    public HexMe(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;

    }

    public HexMe(int q, int r) {
        this(q, r, (-q-r));
    }

    public static HexMe addition(HexMe a, HexMe b) {
        return new HexMe(a.q + b. q, a.r + b.r, a.s + b.s);
    }

    public static HexMe subtraction(HexMe a, HexMe b) {
        return new HexMe(a.q - b.q, a.r - b.r, a.s - b.s);
    }

    public static HexMe multiply(HexMe a, int k) {
        return new HexMe(a.q * k, a.r * k, a.s * k);
    }

    public int thisLength() {
        return HexMe.length(this);
    }

    public int distanceFrom(HexMe other) {
        return HexMe.distance(this, other);
    }

    public static int length(HexMe hexMe) {
        float q,r,s, n;
        q = hexMe.q;
        r = hexMe.r;
        s = hexMe.s;
        n = ((Math.abs(q) + Math.abs(r) + Math.abs(s)) / 2);
        return Math.round(n);
    }

    public static int distance(HexMe a, HexMe b) {
        return length(subtraction(a, b));
    }

    public static float fLength(HexMe hexMe) {
        float q,r,s, n;
        q = hexMe.q;
        r = hexMe.r;
        s = hexMe.s;
        n = ((Math.abs(q) + Math.abs(r) + Math.abs(s)) / 2);
        return n;
    }

    public static float fDistance(HexMe a, HexMe b) {
        return fLength(subtraction(a, b));
    }

    public static HexMe hexDirection(int direction) {

        return DIRECTIONS[direction];
    }

    public static HexMe neighbourOf(HexMe hexMe, int direction) {
        return addition(hexMe, hexDirection(direction));
    }

    public HexMe getNeighbour(int direction) {
        return HexMe.neighbourOf(this, direction);
    }

    public HexMe[] getNeighbours() {
        HexMe[] res = new HexMe[6];
        for (int d = 0; d < 6; d++) {
            res[d] = getNeighbour(d);
        }
        return res;
    }

    public List<HexMe> getNeighboursList() {
        return Arrays.asList(getNeighbours());
    }


    @Override
    public int hashCode() {
        int h = 0;
        int p = 13;

        h += 1 * q * p % 10001;
        h += 1 * r * p % 10001;
        h += 1 * s * p % 10001;
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HexMe) {
            HexMe o = (HexMe) obj;
            return q == o.q && r == o.r && s == o.s;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Hex(%d, %d, %d)", q, r, s);
    }
}
