package com.hexmextex.game.utils;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hex {

    public Hex(int q, int r, int s)
    {
        this.q = q;
        this.r = r;
        this.s = s;
        if (q + r + s != 0) throw new IllegalArgumentException("q + r + s must be 0");
    }
    public final int q;
    public final int r;
    public final int s;


    @Override
    public int hashCode() {

        return q ^ (r + 0x9e3779b9 + (q << 6) + (q >> 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hex hex = (Hex) o;
        return q == hex.q &&
                r == hex.r &&
                s == hex.s;
    }

    public Hex add(Hex b)
    {
        return new Hex(q + b.q, r + b.r, s + b.s);
    }


    public Hex subtract(Hex b)
    {
        return new Hex(q - b.q, r - b.r, s - b.s);
    }


    public Hex scale(int k)
    {
        return new Hex(q * k, r * k, s * k);
    }


    public Hex rotateLeft()
    {
        return new Hex(-s, -q, -r);
    }


    public Hex rotateRight()
    {
        return new Hex(-r, -s, -q);
    }

    static public ArrayList<Hex> directions = new ArrayList<Hex>(){
        {add(new Hex(1, 0, -1));
        add(new Hex(1, -1, 0));
        add(new Hex(0, -1, 1));
        add(new Hex(-1, 0, 1));
        add(new Hex(-1, 1, 0));
        add(new Hex(0, 1, -1));}};

    static public Hex direction(int direction)
    {
        return Hex.directions.get(direction);
    }


    public Hex neighbor(int direction)
    {

        return add(Hex.direction(direction));
    }

    public List<Hex> neighbours() {

        return Arrays.asList(neighbor(0), neighbor(1),
                neighbor(2), neighbor(3), neighbor(4),
                neighbor(5));
    }

    static public ArrayList<Hex> diagonals = new ArrayList<Hex>() {
        {add(new Hex(2, -1, -1));
        add(new Hex(1, -2, 1));
        add(new Hex(-1, -1, 2));
        add(new Hex(-2, 1, 1));
        add(new Hex(-1, 2, -1));
        add(new Hex(1, 1, -2));}};

    public Hex diagonalNeighbor(int direction)
    {
        return add(Hex.diagonals.get(direction));
    }


    public int length()
    {
        return (int)((Math.abs(q) + Math.abs(r) + Math.abs(s)) / 2);
    }


    public int distance(Hex b)
    {
        return subtract(b).length();
    }

    @Override
    public String toString() {
        return "Hex{" +
                "q=" + q +
                ", r=" + r +
                ", s=" + s +
                '}';
    }
}
